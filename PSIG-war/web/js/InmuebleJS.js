// Mapa / capa base / punto centro_mapa/zoom inicial
var map = new OpenLayers.Map("map", {projection: new OpenLayers.Projection("EPSG:3857")});
map.addLayer(new OpenLayers.Layer.Google( "Google",{type: google.maps.MapTypeId.TERRAIN}));
var centro_mapa = new OpenLayers.LonLat( -56.1979688 ,-34.9077286 ).transform(new OpenLayers.Projection("EPSG:4326"), // transforma de WGS 1984
map.getProjectionObject() // a la Proyeccion Spherical Mercator
);
var zoom=5;


var zonas = new OpenLayers.Layer.WMS(
    "-Zonas de demanda", "http://localhost:8081/geoserver/wms",
    {
        srs: "EPSG:4326",
        layers: 'TSIG:zonas',
        transparent: true,
        format:'image/png'
    },
     {   opacity: 0.7}        
);
zonas.visibility=true;
map.addLayer(zonas);


var puertas = new OpenLayers.Layer.WMS(
    "-Zonas de demanda", "http://localhost:8081/geoserver/wms",
    {
        srs: "EPSG:32721",
        layers: 'TSIG:puertas',
        transparent: true,
        format:'image/png'
    },
     {   opacity: 0.7}        
);
zonas.visibility=true;
map.addLayer(puertas);





// Capa manzanas
var manzanas = new OpenLayers.Layer.WMS(
    "-Manzanas", "http://localhost:8081/geoserver/wms",
    {
        srs: "EPSG:32721",
        layers: 'TSIG:manzanas',
        transparent: true,
        format:'image/png'
    }
);
//inmueble.visibility=false;
//map.addLayer(manzanas);

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
inmueble.visibility=true;
map.addLayer(inmueble);


// Editar punto
var capa_punto = new OpenLayers.Layer.Vector("Puntos");
map.addLayer(capa_punto);

var editar_punto = new OpenLayers.Control.DrawFeature(
    capa_punto,
    OpenLayers.Handler.Point
);
//*************************************

    editar_punto.handler.callbacks.create = function(data) {
    if(capa_punto.features.length > 1)
    {
        // capa_punto.features[0].remove;
         capa_punto.removeFeatures(capa_punto.features[0]);
        
    }
    
    buscodireccion();
    
    
    
}

function buscodireccion(){
    
    var valor=capa_punto.features[0].geometry.getVertices()[0];
    valor2= valor.transform(map.getProjectionObject(), new OpenLayers.Projection("EPSG:4326"));
    var x =document.getElementById('formulario:punto_select_x');
    var y=document.getElementById('formulario:punto_select_y');
   
    x.value=valor.x;
    y.value=valor.y;
    
   document.getElementById('formulario:get_direccion').click();
    
}


editar_punto.handler.callbacks.point = function(pt){
    if(capa_punto.features.length > 1)
    {
         //capa_punto.features[0].remove;
         capa_punto.removeFeatures(capa_punto.features[0]);
         
    }
    
}
map.addControl(editar_punto);
editar_punto.activate();   
// Editar punto




// Controles
map.addControl( new OpenLayers.Control.LayerSwitcher() );
map.addControl(  new OpenLayers.Control.OverviewMap());
// Centro mapaa y zoom
map.setCenter (centro_mapa, zoom);


//map.addControl(click);
//click.activate();
 
// map.events.register('click', map, handleMapClick);
   

 
 
function GuardarPunto() {
	// this should work
var valor=capa_punto.features[0].geometry.getVertices()[0];
//var valor1=valor
valor2= valor.transform(map.getProjectionObject(), new OpenLayers.Projection("EPSG:4326"));

//document.getElementById('formulario:punto_select_x');y

//var toProjection = new OpenLayers.Projection("EPSG:4326");
//var lonLat = valor.transform(map.getProjectionObject(), toProjection);

//alert ("valor x "+ valor.x + " valor y "+ valor.y);

var x =document.getElementById('formulario:punto_select_x');
var y=document.getElementById('formulario:punto_select_y');

x.value=valor.x;
y.value=valor.y;


}
