var modal = document.getElementById('id01');

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

$.ajax({
    "url": "/getclazzes",
    "method": "GET",
}).done(function(clazzes) {
    $.ajax({
        "url": "/clazzes4files",
        "method": "GET",
    }).done(function(response) {
        build(response, $("#content").html(""), clazzes);
        $(".item").hashColored();

        $('div.selectize-input div.item').on('click', function(e) {
            $("#myTable").html("");
            var count = getCount(e);
            var elem = $(this).parent().parent().parent();
            var text = elem.children().first().text();
            var clazz = $(this).attr("data-value");


            document.getElementById('id01').style.display = 'block';
            $("#modal_header").html(text + "-" + $(this).text());


            do {
                elem = elem.parent().prev();
                text = elem.text() + "/" + text;
            } while (elem.parent().attr("id") != "content");

            text = text + "$" + count;

            $.ajax({
                type: 'GET',
                url: "/getSystems/" + $(this).text(),
                success: function(systems) {
                    for (let i = 0; i < systems.length; i++) {
                        const system = systems[i];
                        var link = $('<a target="myiframe" class="w3-bar-item">')
                            .attr("href", system.url + "#" + encodeURI(JSON.stringify({
                                clazz: clazz,
                                file: "/" + text
                            })))
                            .text(system.name);

                        $("#myTable")
                            .append(
                                $("<tr>").append(
                                    $("<td>").append(link)));
                    }
                },
                contentType: "application/json",
                dataType: 'json'
            });

            /*
            window.location = "./clazzEditor.html#" + encodeURI(JSON.stringify({
                clazz: $(this).attr("data-value"),
                file: "/" + text
            }));
            */
        });
        window.parent.resizeIframe();
        $('#input-tags').val()
    });
});

function build(res, elem, clazzes) {
    var files = res.inner;
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        let felem = $("<div class='container3'>");
        felem.append($("<span class='file'>").text(file.base))
        elem.append(felem)
        if (file.inner.length > 0) {
            var innerElem = $("<div>")
                .addClass("w3-padding-small")
                .css("border-left", "1px solid"); //    
            build(file, innerElem, clazzes);
            elem.append(innerElem);
        } else {
            if (file.base.indexOf('.') > 0) {
                addclazzes(file.clazzes, felem, clazzes)

            }
        }
    }

}

function getCount(e) {
    var selected = $(e.currentTarget);
    var elemName = selected.text();
    var childs = selected.parent().children();
    var count = 0;

    for (let i = 0; i < childs.length; i++) {
        const element = $(childs[i]);
        if (element.text() === elemName) {
            if (element.hasClass("active"))
                return count;
            count++;
        }
    }
}



function addclazzes(theselected, felem, clazzes) {
    var clazzelem = $('<input type="text" class="input-tags">');
    felem.append(clazzelem);
    var theSelectedPritty = [];
    for (let i = 0; i < theselected.length; i++) {
        const clazz = theselected[i];
        theSelectedPritty[i] = {
            clazzName: clazz
        };
    }
    clazzelem
        .selectize({
            items: theselected,
            create: false,
            mode: "multi",
            duplicates: true,
            enableDuplicate: true,
            enableCreateDuplicate: true,
            hideSelected: false,
            valueField: 'clazzName',
            labelField: 'clazzName',
            searchField: 'clazzName',
            options: clazzes
        })
        .on('change', function() {
            $(".item").hashColored();
        });

}