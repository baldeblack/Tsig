$( window ).load(function() {
    
    /*
    |--------------------------------------------------------------------------
    | PRELOADER
    |--------------------------------------------------------------------------
    */ 
    $('#status').delay(3000).fadeOut(); // will first fade out the loading animation
    $('#preloader').delay(3300).fadeOut('slow'); // will fade out the white DIV that covers the website.
    $('body').delay(3300).css({'overflow':'visible'});
    /*Snarl.addNotification({
        title: 'Bienvenido!!',
        text: 'GeoInmuebles le da bienvenida',
        icon: '<i class="fa fa-globe"></i>'
    });*/
    
    $('#carousel').flexslider({
    animation: "slide",
    controlNav: false,
    animationLoop: false,
    slideshow: false,
    itemWidth: 210,
    itemMargin: 5,
    asNavFor: '#slider'
  });
 
  $('#slider').flexslider({
    animation: "slide",
    controlNav: false,
    animationLoop: false,
    slideshow: false,
    sync: "#carousel"
  });
  $('#form-validator').validator();  
});


