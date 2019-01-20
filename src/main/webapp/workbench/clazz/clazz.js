$(document).ready(function () {

})

const table = $("#myTable tbody");
const search = $("#myInput");

search.on("keyup", function () {
    let input = $(this);
    let filter = input.val().toUpperCase();
    table.find("tr").each(function (i, element) {
        let tr = $(element);
        if (tr.text().toUpperCase().indexOf(filter) > -1) {
            tr.show();
        } else {
            tr.hide();
        }
    });
});

$.ajax({
    "async": true,
    "url": "http://localhost:9090/clazz",
    "method": "GET",
}).done(function (response) {
    // console.log(response);
    table.html("");
    search.val("");
    console.log(response);
    for (var aClass in response) {
        let clazzs = response[aClass];

        let clazzes = $("<td>");
        for (let i in clazzs ){
            let clazz = clazzs[i];
            clazzes.append(
                $("<a>")
                .addClass("w3-button w3-round-xxlarge w3-small")
                .text(clazz)
                .hashColored()
                .attr("href", "./aClazz.html#" + encodeURI(JSON.stringify({aClass: aClass, aClazz: clazz})))
            )
        }

        let newRow = $("<tr>");
        newRow
            .append(
                $("<td>")
                .append(
                    $("<a>").text(aClass))
            )
            .append(
                clazzes
            );
        table.append(newRow);
    }

});