// Mapa / capa base / punto centro_mapa/zoom inicial
var map = new OpenLayers.Map("map", {projection: new OpenLayers.Projection("EPSG:3857")});
map.addLayer(new OpenLayers.Layer.Google( "Google",{type: google.maps.MapTypeId.TERRAIN}));
var centro_mapa = new OpenLayers.LonLat( -56.1979688 ,-34.9077286 ).transform(new OpenLayers.Projection("EPSG:4326"), // transforma de WGS 1984
map.getProjectionObject() // a la Proyeccion Spherical Mercator
);
var zoom=13;


// Capa inmueble
var inmueble = new OpenLayers.Layer.WMS(
    "-Inmuebles", "http://localhost:8081/geoserver/wms",
    {
        srs: "EPSG:4326",
        layers: 'TSIG:inmueble',

        transparent: true,
        format:'image/png'
    }

);
inmueble.visibility=false;
//map.addLayer(inmueble);

map.addControl( new OpenLayers.Control.LayerSwitcher() );
map.addControl( new OpenLayers.Control.Navigation());
//map.addControl(  new OpenLayers.Control.PanZoomBar());
//new OpenLayers.Control.Permalink(),
//map.addControl(  new OpenLayers.Control.ScaleLine());
//new OpenLayers.Control.Permalink('permalink'),
map.addControl(  new OpenLayers.Control.MousePosition());
map.addControl(  new OpenLayers.Control.OverviewMap());
map.addControl(  new OpenLayers.Control.KeyboardDefaults());

// Centro mapaa y zoom
map.setCenter (centro_mapa, zoom);

// Capa punto
var capa_punto = new OpenLayers.Layer.Markers("inmuebles");   	
//var capa_punto = new OpenLayers.Vector("-Inmuebles");
map.addLayer(capa_punto);


window.onload = function () {
  var coordenadas =document.getElementById('formulario:coordenadas').value;
   
  
   var string = coordenadas.split(',');
   var largo=string.length;
    if (largo>0){
        
           // string=string.toString().replace("[","");
           // string=string.toString().replace("]","");
          // var string2 = string;//
           //alert ("Valor " + string2);
           var index;
           //alert (' Largo  ' + largo);
           for (index = 0; index < string.length; ++index) {
                
                var valor_actual =string[index];
                valor_actual= valor_actual.trim();//alert (' Valor antes  ' + valor_actual);
                //valor_actual=valor_actual.toString().replace(' ','');
                valor_actual=valor_actual.toString().replace('[','');
                valor_actual=valor_actual.toString().replace(']','');
                valor_actual=valor_actual.toString().replace(/,/g,'');
                valor_actual=valor_actual.toString().replace(' ',',');

                // alert (' Valor actual  ' + valor_actual);

                var lonlat = valor_actual.split(",");
                //alert (' Valor lon  ' + lonlat[0]+ 'lat='+ lonlat[1]);
                //********************
                //var lonlat = new GetWebMercatorLonLatFromWGS84GeographicCoordinates(valor);
                //var punto = new OpenLayers.Geometry.Point(lonlat.lon,lonlat.lat);
                //************************
                //alert ('Location lon=' + lonlat[0] + '-lat=' + lonlat[1]);
                var location = new OpenLayers.LonLat(lonlat[0],lonlat[1])
                       .transform(new OpenLayers.Projection("EPSG:4326"),map.getProjectionObject()) ; 			
                //alert ('Location lon ' + location.lon + 'lat' + location.lat);
                var size = new OpenLayers.Size(60,60);
                var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
                var icon = new OpenLayers.Icon('https://cdn3.iconfinder.com/data/icons/map-markers-1/512/residence-512.png',size,offset);
                //var icon = new OpenLayers.Icon('https://www.openlayers.org/dev/img/marker.png',size,offset);
                capa_punto.addMarker(new OpenLayers.Marker(location,icon));
                // alert ("Valor final " + valor.toString());
           }
    }
    //alert ("Coordenadas" + coordenadas.value);
};
