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
    "url": "http://localhost:9090/pages",
    "method": "GET",
}).done(function (response) {
    // console.log(response);
    table.html("");
    search.val("");
    for (let i = 0; i < response.length; i++) {
        let package = response[i];
        let newRow = $("<tr>");
        newRow
            .append(
                $("<td>")
                .append(
                    $("<a>").attr("href", "./desinger.html#" + package).text(package))
                .append(
                    $('<a>')
                    .addClass("w3-button w3-margin-left w3-round-xxlarge w3-small")
                    .attr("href", "./mapping.html#" + package).text("mapping")
                    .hashColored()
                ));
        table.append(newRow);
    }

});