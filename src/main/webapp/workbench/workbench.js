$.ajax({
    type: 'GET',
    url: "/project",
    success: function (response) {
        buildUi($("#content"), response.className, response.state);
    },
    contentType: "application/json",
    dataType: 'json'
});