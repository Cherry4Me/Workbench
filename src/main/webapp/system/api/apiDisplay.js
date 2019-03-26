
$(document).ready(function(){
    $.ajax({
        type: 'GET',
        url: "/api",
        success: function (data) {
            buildTable($("#rests"), "de.cherry.workbench.clazz.rest.RestClazz", function ($inner, structure) {
                for (let i = 0; i < data.length; i++) {
                    let api = data[i];
                    let split = api.file.split("/");
                    api.file = split[split.length - 1];
                    addRow($("#rests"), api);
                }
            })
    
        },
        contentType: "application/json",
        dataType: 'json'
    });       
});