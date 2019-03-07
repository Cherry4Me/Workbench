$(document).ready(function(){
    $(".nav .mask").on("touchstart click", function(e) {
        e.preventDefault(), $(this).parent().toggleClass("active")
    })
    
    $(".nav .nav-item").on("click", function(e) {
        e.preventDefault();
        var t = $(this).attr("href").replace("#", "");
        $(".html").removeClass("visible");
        $(".html." + t).addClass("visible");
        $(".nav").toggleClass("active");
    })
})
