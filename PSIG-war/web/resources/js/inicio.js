$( window ).load(function() {
     Snarl.addNotification({
        title: 'Bienvenido!!',
        text: 'GeoInmuebles le da bienvenida',
        icon: '<i class="fa fa-globe"></i>'
    });
    
    $('#carousel').flexslider({
    animation: "slide",
    controlNav: false,
    animationLoop: false,
    slideshow: false,
    itemWidth: 210,
    itemHeight: 210,
    itemMargin: 5,
    asNavFor: '#slider'
  });
 
  $('#slider').flexslider({
    animation: "slide",
    controlNav: false,
    animationLoop: false,
    slideshow: false,
    itemHeight: 400,
    sync: "#carousel"
  });
    
});


