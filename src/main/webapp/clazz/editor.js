//let className = "de.cherry.workbench.self.interpreter.Dog4Interpreter";
// let className = "java.lang.String";
//buildUi($("#interpreter"), className, undefined);

//window.location.hash = encodeURI(JSON.stringify({
//    clazz: "Test",
//    file: "/src/main/java/com/example/out/StudentResource.java"
//}))

let hash = window.location.hash.substring(1);

if(hash !== ""){
    $("#loader").hide();
    let urlData = JSON.parse(decodeURI(hash));
    init(urlData);
    $("#clazz").val(urlData.clazz);
    $("#file").val(urlData.file);
} 

$("#load").on("click", function(){
    $("#loader").hide();
    init({
        clazz: $("#clazz").val(),
        file: $("#file").val()
    })
})

function init(urlData){
    $.ajax({
        type: 'POST',
        url: "/clazzdata",
        data: JSON.stringify(urlData),
        success: function (data) {
            createUI(data)
        },
        contentType: "application/json",
        dataType: 'json'
    });
}

function createUI(urlData){
    console.log(urlData);
    var className = urlData.type;
    var data = urlData.state;
    getCtClass(className, function (structure) {
        let card = $("<div class='w3-panel w3-card w3-light-grey'>").attr("className", className).append($("<h3>").text(className));
        addFields(card, className, structure, data);
        $("#interpreter").append(card);
        window.parent.resizeIframe();
    });
    $("#saveButton").on("click", function () {
        var toExecute = {
            clazz: className,
            executable: "save",
            object: JSON.stringify(getObject(className)),
            params: [],
            paramsClasses: []
        }
    
        execute(toExecute, function (data) {
            console.log(data);
        }, function () {
            toastr.error("Can't save the Clazz changes.", 'Unsaved!')
        });
    });
}
