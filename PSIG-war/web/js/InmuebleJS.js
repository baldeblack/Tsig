// Mapa / capa base / punto centro_mapa/zoom inicial
var map = new OpenLayers.Map("map", {projection: new OpenLayers.Projection("EPSG:3857")});
map.addLayer(new OpenLayers.Layer.Google( "Google",{type: google.maps.MapTypeId.TERRAIN}));
var centro_mapa = new OpenLayers.LonLat( -56.1979688 ,-34.9077286 ).transform(new OpenLayers.Projection("EPSG:4326"), // transforma de WGS 1984
map.getProjectionObject() // a la Proyeccion Spherical Mercator
);
var zoom=13;

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
        srs: "EPSG:32721",
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

editar_punto.handler.callbacks.point = function(pt){
    console.log(pt)
}
map.addControl(editar_punto);
//editar_punto.activate();   
// Editar punto




// Controles
map.addControl( new OpenLayers.Control.LayerSwitcher() );
map.addControl(  new OpenLayers.Control.OverviewMap());
// Centro mapaa y zoom
map.setCenter (centro_mapa, zoom);


OpenLayers.Control.Click = OpenLayers.Class(OpenLayers.Control, {                
                defaultHandlerOptions: {
                    'single': true,
                    'double': false,
                    'pixelTolerance': 0,
                    'stopSingle': false,
                    'stopDouble': false
                },

                initialize: function(options) {
                    this.handlerOptions = OpenLayers.Util.extend(
                        {}, this.defaultHandlerOptions
                    );
                    OpenLayers.Control.prototype.initialize.apply(
                        this, arguments
                    ); 
                    this.handler = new OpenLayers.Handler.Click(
                        this, {
                            'click': this.trigger
                        }, this.handlerOptions
                    );
                }, 

                trigger: function(e) {
				var proj = {
			"google": new OpenLayers.Projection("EPSG:32721"),
			"latlng": new OpenLayers.Projection("EPSG:4326")
		}; 

alert("Hiciste click en " + lonlat.lat + " N, " +
								  + lonlat.lon + " E");
                }

            });
//map.addControl(click);
//click.activate();
 
 map.events.register('click', map, handleMapClick);
   

    function handleMapClick(evt)
    {
       var lonlat = map.getLonLatFromViewPortPx(evt.xy);
        document.getElementById("form:punto_select_x").value=lonlat.lat;
        document.getElementById("form:punto_select_y").value=lonlat.lon;
       // use lonlat
       //punto_x.value=lonlat.lat;
      // punto_y.value=lonlat.lon;
       
       alert(lonlat);
    }  
 
 
function GuardarPunto() {
	// this should work
 var lonlat = map.getLonLatFromViewPortPx(e.xy);
 
 var punto = document.getElementById("form:punto_select").value;
 var zonas = drawControls['polygon'].layer;
	
	zona.value = JSON.stringify(jsonResult);
 
 
 
 
 alert("Hiciste click en " + lonlat.lat + " N, " +
                                          + lonlat.lon + " E");
                                  
                                  
                                  
}
