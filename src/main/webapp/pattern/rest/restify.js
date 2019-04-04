function init(urlData) {
    $.ajax({
        type: 'POST',
        url: "/restify",
        data: JSON.stringify(urlData),
        success: function (data) {
            toastr.success("Restifyed", 'Succes!')
        },
        error: function(e){
            console.error(e);
        },
        contentType: "application/json",
        dataType: 'json'
    });
}