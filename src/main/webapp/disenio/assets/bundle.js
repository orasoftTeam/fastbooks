jQuery(function($){
    $(".mm-toggle").click(function(e) {
        e.preventDefault();
        $(".mm-container").toggleClass("active");
        $("body").toggleClass("menu-open");
    });

});