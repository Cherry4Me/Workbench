$(document).on("ready", function(){
    var test = "hallo";
    $.ajax({
        type: 'GET',
        url: "/api/" + test,
        success: function (data) {
           console.log(data);

        },
        contentType: "application/json",
        dataType: 'json'
    });
});

$.ajax({
    type: 'GET',
    url: "/api",
    success: function (data) {
        console.log(data);

    },
    contentType: "application/json",
    dataType: 'json'
});