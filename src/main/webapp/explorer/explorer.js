const table = $("#myTable tbody");
const search = $("#myInput");

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

function createMethodeTable(response) {
    console.log(response);
    table.html("");
    search.val("");
    for (let i = 0; i < response.length; i++) {
        let method = response[i];
        let paramTd = $("<td>");
        let params = method.params;
        for (let j = 0; j < params.length; j++) {
            const param = params[j];
            paramTd
                .append(
                    $("<button>")
                    .addClass("w3-button w3-small w3-margin w3-pale-yellow w3-round-xxlarge")
                    .text(param.key + ":" + param.value.name)
                )
        }

        let newRow = $("<tr>")
            .append($("<td>").text(method.className))
            .append($("<td>").text(method.mehtodeName))
            .append($("<td>").append(paramTd))
            .on("click", function() {
                console.log();
            });
        table.append(newRow);
    }

}


function createClassesTable(response) {
    // console.log(response);
    table.html("");
    search.val("");
    for (let i = 0; i < response.length; i++) {
        let simpleClass = response[i];
        let newRow = $("<tr>");
        newRow
            .append($("<td>").text(simpleClass.packageName))
            .append($("<td>").text(simpleClass.name))
            .on("click", function() {
                let quallifiedClassName = simpleClass.packageName + "." + simpleClass.name;
                // console.log(quallifiedClassName);
                $.ajax({
                    "async": true,
                    "url": "./methode?class=" + quallifiedClassName,
                    "method": "GET",
                }).done(createMethodeTable);
            });
        table.append(newRow);
    }
}


$.ajax({
    "async": true,
    "url": "./package",
    "method": "GET",
}).done(function(response) {
    // console.log(response);
    table.html("");
    search.val("");
    for (let i = 0; i < response.length; i++) {
        let package = response[i];
        let newRow = $("<tr>");
        newRow
            .append($("<td>").text(package))
            .on("click", function() {
                $.ajax({
                    "async": true,
                    "url": "./class?package=" + package,
                    "method": "GET",
                }).done(createClassesTable);
            });
        table.append(newRow);
    }

});





// function myFunction() {
//     console.log("was geht1");
//     var input, filter, table, tr, td, i;
//     input = document.getElementById("myInput");
//     filter = input.value.toUpperCase();
//     table = document.getElementById("myTable");
//     tr = table.getElementsByTagName("tr");
//     for (i = 0; i < tr.length; i++) {
//         td = tr[i].getElementsByTagName("td")[0];
//         if (td) {
//             if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
//                 tr[i].style.display = "";
//             } else {
//                 tr[i].style.display = "none";
//             }
//         }
//     }
// }