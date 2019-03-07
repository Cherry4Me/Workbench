

function init(urlData){
    $.ajax({
        type: 'POST',
        url: "/clazzdata",
        data: JSON.stringify(urlData),
        success: function (data) {
            createUI(data);
        },
        contentType: "application/json",
        dataType: 'json'
    });
}

function createUI(urlData){
    var className = urlData.type;
    var data = urlData.state;
    getCtClass(className, function (structure) {
        let card = $("<div class='w3-panel w3-card w3-light-grey'>").attr("className", className).append($("<h3>").text(className));
        addFields(card, className, structure, data);
        $("#interpreter").append(card);
        window.parent.resizeIframe();
    });
}