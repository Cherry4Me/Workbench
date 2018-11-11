let data = {

    executable: "min",
    clazz: "java.lang.Math",
    object: "null",
    paramsClasses:["java.lang.Integer", "java.lang.Integer"],
    params: ["12", "24"]


}


//let data =  "hallo"
$.ajax({
    type: 'POST',
    url: "/call",
    data: JSON.stringify(data),
    success: function(data) {
     console.log(  data);
     },
    contentType: "application/json",
    dataType: 'json'
});