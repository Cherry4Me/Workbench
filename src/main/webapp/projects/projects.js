$.ajax({
    "url": "/projects",
    "method": "GET",
}).done(function (projects) {
    for (let i = 0; i < projects.length; i++) {
        const project = projects[i];
        $("#content").append(
            $('<div class="column w3-theme">')
            .on("click", function(){
                
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