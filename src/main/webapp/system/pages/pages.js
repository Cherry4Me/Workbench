const table = $("#myTable tbody");
const search = $("#myInput");

var modal = document.getElementById('id01');

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

$("#createFile").on("click", function() {
    var name = $("#fileName").val();
    console.log(name)
    $.ajax({
        "url": "/createPage",
        "method": "POST",
        contentType: "application/json",
        dataType: 'json',
        "data": JSON.stringify({ filename: name })
    }).done(function() {
        toastr.success('Saved')
    });
    modal.style.display = 'none';
})


$("#saveButton").on("click", function() {
    modal.style.display = 'block';
})

search.on("keyup", function() {
    let input = $(this);
    let filter = input.val().toUpperCase();
    table.find("tr").each(function(i, element) {
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
    "url": "/pages",
    "method": "GET",
}).done(function(response) {
    table.html("");
    search.val("");
    for (let i = 0; i < response.length; i++) {
        let package = response[i];
        let newRow = $("<tr>");
        newRow
            .append(
                $("<td>")
                .append(
                    $("<a>").attr("href", "/pattern/desinger.html#" + package).text(package))
            );
        table.append(newRow);
    }
    window.parent.resizeIframe();
});