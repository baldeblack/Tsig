/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


 var map = new OpenLayers.Map( 'map' );
 var layer = new OpenLayers.Layer.WMS( "Montevideo","http://localhost:8080/geoserver/TSIG/wms", {layers : 'TSIG:manzanas'});
 map.addLayer(layer);
 map.setCenter(new OpenLayers.LonLat(-56.229473170625, -34.818454350979), 12);
 map.addControl( new OpenLayers.Control.LayerSwitcher() );