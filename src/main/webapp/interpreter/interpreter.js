let data = {
    executable: "min",
    clazz: "java.lang.Math",
    object: "null",
    paramsClasses:["int", "int"],
    params: ["12", "24"]
}

$("#in").text(JSON.stringify(data));


//let data =  "hallo"
$.ajax({
    type: 'POST',
    url: "/call",
    data: JSON.stringify(data),
    success: function(data) {
      $("#out").text(JSON.stringify(data));
      console.log(  data);
     },
    contentType: "application/json",
    dataType: 'json'
});