// Mapa / capa base / punto centro_mapa/zoom inicial
var map = new OpenLayers.Map("map", {projection: new OpenLayers.Projection("EPSG:3857")});
map.addLayer(new OpenLayers.Layer.Google( "Google",{type: google.maps.MapTypeId.TERRAIN}));
var centro_mapa = new OpenLayers.LonLat( -56.1979688 ,-34.9077286 ).transform(new OpenLayers.Projection("EPSG:4326"), // transforma de WGS 1984
map.getProjectionObject() // a la Proyeccion Spherical Mercator
);
var zoom=13;

// Capa zonas
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



// Editar punto
var capa_zona = new OpenLayers.Layer.Vector("Zona");
map.addLayer(capa_zona);

var editar_punto = new OpenLayers.Control.DrawFeature(
    capa_zona,
    OpenLayers.Handler.Polygon
);

editar_punto.handler.callbacks.create = function(data) {
    if(capa_zona.features.length > 1)
    {
         for ( var i = 0; i < capa_zona.features.length; i++) {
            capa_zona.removeFeatures(capa_zona.features[i]);
        }
    }
}

editar_punto.handler.callbacks.point = function(pt){
    if(capa_zona.features.length > 1)
    {
         //capa_zona.features[0].remove;
         for ( var i = 0; i < capa_zona.features.length; i++) {
            capa_zona.removeFeatures(capa_zona.features[i]);
        }
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


function GuardarZona() {
    var zona = document.getElementById("formulario:zona_puntos");

        var coordenadas = new Array();
        for ( var i = 0; i < capa_zona.features.length; i++) {
                var zonaArray = capa_zona.features[i].geometry.getVertices(false);
                var jsonArr = new Array();
                for ( var j = 0; j < zonaArray.length; j++) {
                    var punto= zonaArray[j].transform(map.getProjectionObject(), new OpenLayers.Projection("EPSG:4326"));
                    //alert(zona);
                    jsonArr[j] =  punto.x + " " +  punto.y;
                }
                jsonArr[jsonArr.length] = zonaArray[0].x + " " + zonaArray[0].y;
                coordenadas[i] = jsonArr;
        }
        
        //alert(coordenadas);
        //zona.value = coordenadas;
        zona.value = JSON.stringify(coordenadas);


}
