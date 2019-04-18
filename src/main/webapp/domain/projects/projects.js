var modal = document.getElementById('id01');

window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

$("#newProjekt").on("click", function(){
    document.getElementById('id01').style.display = 'block';
});


$("#start").on("click", function(){
    var path = $("#path").val();
    var group = $("#group").val();
    var name = $("#name").val();
    var template = $("#template").children("option:selected").val();
    var newProject = {
        path: "default",
        group: "default",
        name: $("#name").val(),
        template: "Java-Maven-Spring-Boot-Web-Server"
    }
    
    $.ajax({
        "url": "/createProject",
        "method": "POST",
        data: JSON.stringify(newProject),
        success: function (data) {
            window.parent.location.href = "/project.html"
        },
        contentType: "application/json",
        dataType: 'json'
    });
    
    console.log("start");
})





$.ajax({
    "url": "/projects",
    "method": "GET",
}).done(function (projects) {
    for (let i = 0; i < projects.length; i++) {
        const project = projects[i];
        $("#content").append(
            $('<div class="column w3-theme">')
            .on("click", function () {
                console.log(project);
                $.ajax({
                    "url": "/currentProject",
                    "method": "POST",
                    data: JSON.stringify(project),
                    success: function (data) {
                        window.parent.location.href = "/project.html"
                    },
                    contentType: "application/json",
                    dataType: 'json'
                });
            })
            .append(
                $('<div class="card">')
                .append(
                    $('<p>').append($('<i class="fa fa-archive">'))
                )
                .append(
                    $('<h2>').text(project.name)
                )
                .append(
                    $('<p>').text(project.group)
                )
                .append(
                    $('<p style="font-size:12px;word-wrap:break-word;">').text(project.path)
                )
            )

        )
    }
    window.parent.resizeIframe();
});