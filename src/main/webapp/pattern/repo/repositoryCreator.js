function init(urlData) {
    $.ajax({
        type: 'POST',
        url: "/repositoryTemplate",
        data: JSON.stringify(urlData),
        success: function (data) {
            toastr.success("Repository created", 'Succes!')
        },
        error: function(e){
            console.error(e);
        },
        contentType: "application/json",
        dataType: 'json'
    });
}