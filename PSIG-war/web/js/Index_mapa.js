//********* Mapa / capa base / punto centro_mapa/zoom inicial*************************
var map = new OpenLayers.Map("map", {projection: new OpenLayers.Projection("EPSG:3857")});
map.addLayer(new OpenLayers.Layer.Google( "Google",{type: google.maps.MapTypeId.TERRAIN}));
var centro_mapa = new OpenLayers.LonLat( -56.1979688 ,-34.9077286 ).transform(new OpenLayers.Projection("EPSG:4326"), // transforma de WGS 1984
map.getProjectionObject() // a la Proyeccion Spherical Mercator
);
var zoom=13;

//******Capa inmueble************************************************** 
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

//******Capa comercios************************************************** 
var comercio = new OpenLayers.Layer.WMS(
    "-Comercios", "http://localhost:8081/geoserver/wms",
    {
        srs: "EPSG:4326",
        layers: 'TSIG:comercios',
        transparent: true,
        format:'image/png'
    }
);

comercio.visibility=false;
//******************************************************************************

//******Capa paradas************************************************** 
var paradas = new OpenLayers.Layer.WMS(
    "-Paradas", "http://localhost:8081/geoserver/wms",
    {
        srs: "EPSG:4326",
        layers: 'TSIG:paradas',
        transparent: true,
        format:'image/png'
    }
);

paradas.visibility=false;
//******************************************************************************

//***** Controles***************************************************************
map.addControl( new OpenLayers.Control.LayerSwitcher() );
map.addControl( new OpenLayers.Control.Navigation());
//map.addControl(  new OpenLayers.Control.PanZoomBar());
//new OpenLayers.Control.Permalink(),
//map.addControl(  new OpenLayers.Control.ScaleLine());
//new OpenLayers.Control.Permalink('permalink'),
map.addControl(  new OpenLayers.Control.MousePosition());
map.addControl(  new OpenLayers.Control.OverviewMap());
map.addControl(  new OpenLayers.Control.KeyboardDefaults());

//**** Centro mapa y zoom********************************
map.setCenter (centro_mapa, zoom);

//***********CAPA QUE VA A CONTENER LOS PUNTOS**********************************
var capa_punto = new OpenLayers.Layer.Vector("Inmueble");
var capa_super = new OpenLayers.Layer.Vector("Comercios");
var capa_parada = new OpenLayers.Layer.Vector("Paradas");
//***********FUNCION AL INICIO**************************************************
/*window.onload = function () {
    var coordenadas =document.getElementById('formulario:coordenadas').value;
    
    //alert(coordenadas);
    var string = coordenadas.split(',');
    var largo=string.length;
    if (largo>0){
        var index;
        //***********itero en el string de coordenadas *********************
        for (index = 0; index < string.length; ++index) {
            //*******PARSEO LA COORDENADA***********************************
            var valor_actual =string[index];
            valor_actual= valor_actual.trim();//alert (' Valor antes  ' + valor_actual);
            valor_actual=valor_actual.toString().replace('[','');
            valor_actual=valor_actual.toString().replace(']','');
            valor_actual=valor_actual.toString().replace(/,/g,'');
            valor_actual=valor_actual.toString().replace(' ',',');
            var lonlat = valor_actual.split(",");
           //************OBTENGO LOCATION***********************************
            var location = new OpenLayers.LonLat(lonlat[0],lonlat[1]).transform(new OpenLayers.Projection("EPSG:4326"),map.getProjectionObject()) ; 			
            //alert ('Location lon ' + location.lon + 'lat' + location.lat);
            //**************************************************************
            var marker = new OpenLayers.Feature.Vector(
                new OpenLayers.Geometry.Point(location.lon,location.lat),
                {description:'Inmueble'} ,
                {externalGraphic: 'https://cdn3.iconfinder.com/data/icons/map-markers-1/512/residence-512.png', 
                 graphicHeight: 35, graphicWidth: 35, graphicXOffset:-12, graphicYOffset:-25  }
            );    
            capa_punto.addFeatures(marker);
            map.addLayer(capa_punto);
            //*****Controles************************************************
            var controles = {
                selector: new OpenLayers.Control.SelectFeature(capa_punto, { onSelect: crearPopup, onUnselect: cerrarPopup })
            };
            //*****Desplegar Pop Up*****************************************
            function crearPopup(marker) {
                marker.popup = new OpenLayers.Popup.FramedCloud("pop",
                    marker.geometry.getBounds().getCenterLonLat(),
                    null,
                    "<div class='markerContent'>\n\
                        <h4>Inmueble</h4>\n\
                    <button type='button' onclick='verinmueble()'>Consultar</button>\n\
                    </div>",
                    null,
                    true,
                    function() { controles['selector'].unselectAll(); }
                );
        
                marker.popup.events.register('click', marker.popup, function verinmueble(event) {
                    var coords = marker.geometry.getBounds().getCenterLonLat();
                    var coord_x=coords.lon.toString();
                    var coord_y=coords.lat.toString();
                    var x = document.getElementById('formulario:coord_x');
                    var y=document.getElementById('formulario:coord_y');
                    
                    var coordenadas_vista=document.getElementById('formulario:coordenadas_String');
                    
                    //coordenadas_vista.value = JSON.stringify(coordenadas_vista.value);
                    
                    //coordenadas_vista.value=null;
                    x.value=coord_x;
                    y.value=coord_y;   
                    //alert(y.value);
                    document.getElementById('formulario:click').click();
                });
                    
                //feature.popup.closeOnMove = true;
                map.addPopup(marker.popup);
            }
            //******Cerrar Pop Up*******************************************
            function cerrarPopup(marker) {
                marker.popup.destroy();
                marker.popup = null;
            }
            //******Se agrega controles*************************************
            map.addControl(controles['selector']);
            controles['selector'].activate();
            
            //*****************************************************************
            //function verinmueble() {
               
           // }
            
        }    
    }            
};*/

window.onload = function () {
    var coordenadas =document.getElementById('formulario:coordenadas').value;
    
    //alert(coordenadas);
    var string = coordenadas.split(':');
    var largo=string.length;
    if (largo>0){
        var index;
        //***********itero en el string de coordenadas *********************
        for (index = 0; index < string.length; ++index) {
            //*******PARSEO LA COORDENADA***********************************
            var valor_actual =string[index];
            valor_actual= valor_actual.trim();
            var lonlat = valor_actual.split(",");
           //************OBTENGO LOCATION***********************************
            var location = new OpenLayers.LonLat(lonlat[1],lonlat[2]).transform(new OpenLayers.Projection("EPSG:4326"),map.getProjectionObject()) ; 			
           // alert ('Location lon ' + location.lon + 'lat' + location.lat);
            //**************************************************************           
            var marker; 
            var markercomercios;
            var markerparada;
            
            if(lonlat[0] === "casa" || lonlat[0] === "apartamento"){
                //alert ('Tipo: ' + lonlat[0] + 'x:' + lonlat[1]);
                marker  = new OpenLayers.Feature.Vector(
                new OpenLayers.Geometry.Point(location.lon,location.lat),
                {description:'Inmueble'} ,
                {externalGraphic: 'https://cdn3.iconfinder.com/data/icons/map-markers-1/512/residence-512.png', 
                 graphicHeight: 35, graphicWidth: 35, graphicXOffset:-12, graphicYOffset:-25  }
                );  
                capa_punto.addFeatures(marker);
                map.addLayer(capa_punto);
            }            
            if(lonlat[0] === "Supermercado"){
               // alert ('Tipo: ' + lonlat[0] + 'x:' + lonlat[1]);
                markercomercios  = new OpenLayers.Feature.Vector(
                new OpenLayers.Geometry.Point(location.lon,location.lat),
                {description:'Comercios'} ,
                {externalGraphic: 'https://cdn3.iconfinder.com/data/icons/map-markers-1/512/supermarket-512.png', 
                 graphicHeight: 35, graphicWidth: 35, graphicXOffset:-12, graphicYOffset:-25  }
                );  
                capa_super.addFeatures(markercomercios);    
                map.addLayer(capa_super);
            }
            if(lonlat[0] === "parada"){
                //alert ('Tipo: ' + lonlat[0] + 'x:' + lonlat[1]);
                markerparada  = new OpenLayers.Feature.Vector(
                new OpenLayers.Geometry.Point(location.lon,location.lat),
                {description:'Paradas'} ,
                {externalGraphic: 'https://cdn0.iconfinder.com/data/icons/IdealSpace/128/Bus_Stop.png', 
                 graphicHeight: 35, graphicWidth: 35, graphicXOffset:-12, graphicYOffset:-25  }
                );  
                capa_parada.addFeatures(markerparada);    
                map.addLayer(capa_parada);
            }
               
           // capa_punto.addFeatures(marker);
            
           // map.addLayer(capa_punto);
            //*****Controles************************************************
            var controles = {
                selector: new OpenLayers.Control.SelectFeature(capa_punto, { onSelect: crearPopup, onUnselect: cerrarPopup })
            };
            //*****Desplegar Pop Up*****************************************
            function crearPopup(marker) {
                marker.popup = new OpenLayers.Popup.FramedCloud("pop",
                    marker.geometry.getBounds().getCenterLonLat(),
                    null,
                    "<div class='markerContent'>\n\
                        <h4>Inmueble</h4>\n\
                    <button type='button' onclick='verinmueble()'>Consultar</button>\n\
                    </div>",
                    null,
                    true,
                    function() { controles['selector'].unselectAll(); }
                );
        
                marker.popup.events.register('click', marker.popup, function verinmueble(event) {
                    var coords = marker.geometry.getBounds().getCenterLonLat();
                    var coord_x=coords.lon.toString();
                    var coord_y=coords.lat.toString();
                    var x = document.getElementById('formulario:coord_x');
                    var y=document.getElementById('formulario:coord_y');
                    
                    var coordenadas_vista=document.getElementById('formulario:coordenadas_String');
                    
                    //coordenadas_vista.value = JSON.stringify(coordenadas_vista.value);
                    
                    //coordenadas_vista.value=null;
                    x.value=coord_x;
                    y.value=coord_y;   
                    //alert(y.value);
                    document.getElementById('formulario:click').click();
                });
                    
                //feature.popup.closeOnMove = true;
                map.addPopup(marker.popup);
            }
            //******Cerrar Pop Up*******************************************
            function cerrarPopup(marker) {
                marker.popup.destroy();
                marker.popup = null;
            }
            //******Se agrega controles*************************************
            map.addControl(controles['selector']);
            controles['selector'].activate();
            
            //*****************************************************************
            //function verinmueble() {
               
           // }
            
        }    
    }            
};