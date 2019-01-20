
let classAndClazz = JSON.parse(decodeURI(window.location.hash.substring(1)));
console.log(classAndClazz);


$.ajax({
    type: 'POST',
    url: "/getState",
    data: JSON.stringify( classAndClazz),
    success: function (response) {
        buildUi($("#content"), response.className, response.state);
    },
    contentType: "application/json",
    dataType: 'json'
});
