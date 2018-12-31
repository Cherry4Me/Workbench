// var JSONfn;
// if (!JSONfn) {
//     JSONfn = {};
// }

// (function () {
//   JSONfn.stringify = function(obj) {
//     return JSON.stringify(obj,function(key, value){
//             return (typeof value === 'function' ) ? value.toString() : value;
//         });
//   }

//   JSONfn.parse = function(str) {
//     return JSON.parse(str,function(key, value){
//         if(typeof value != 'string') return value;
//         return ( value.substring(0,8) == 'function') ? eval('('+value+')') : value;
//     });
//   }
// }());

// var array = [];
// for(i in document) array.push(i);
// array.filter(function(i){
//     return i.substring(0,2)=='on'&&(document[i]==null||typeof document[i]=='function');
// })



var site;
$(document).ready(function () {
    site = window.location.hash.substring(1);
    $.ajax({
        "async": true,
        "url": "http://localhost:9090/mapping?page=" + site,
        "method": "GET",
        "headers": {
            "content-type": "application/json",
            "cache-control": "no-cache",
        }
    }).done(function (response) {
        init(response);
    });
});


function init(data){
    

}