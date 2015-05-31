/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// Mapa / capa base / punto centro_mapa/zoom inicial
var map = new OpenLayers.Map("map", {projection: new OpenLayers.Projection("EPSG:3857")});
map.addLayer(new OpenLayers.Layer.Google( "Google",{type: google.maps.MapTypeId.TERRAIN}));
var centro_mapa = new OpenLayers.LonLat( -56.1979688 ,-34.9077286 ).transform(new OpenLayers.Projection("EPSG:4326"), // transforma de WGS 1984
map.getProjectionObject() // a la Proyeccion Spherical Mercator
);
var zoom=13;





//***********CAPA QUE VA A CONTENER LOS PUNTOS**********************************
var capa_punto = new OpenLayers.Layer.Vector("Inmueble");
//***********FUNCION AL INICIO**************************************************
/*window.onload = function () {
    
};*/

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