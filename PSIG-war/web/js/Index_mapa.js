// Mapa / capa base / punto centro_mapa/zoom inicial
var map = new OpenLayers.Map("map", {projection: new OpenLayers.Projection("EPSG:3857")});
map.addLayer(new OpenLayers.Layer.Google( "Google",{type: google.maps.MapTypeId.TERRAIN}));
var centro_mapa = new OpenLayers.LonLat( -56.1979688 ,-34.9077286 ).transform(new OpenLayers.Projection("EPSG:4326"), // transforma de WGS 1984
map.getProjectionObject() // a la Proyeccion Spherical Mercator
);
var zoom=13;

// borde la de la rambla
var rambla = new OpenLayers.Layer.WMS(
    "-Costa", "http://localhost:8081/geoserver/wms",
    {
        srs: "EPSG:32721",
        layers: 'TSIG:borde_rambla',
        transparent: true,
        format:'image/png'
    }

);
rambla.visibility=false;
map.addLayer(rambla);

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
zonas.visibility=false;
map.addLayer(zonas);

// calles
var calles = new OpenLayers.Layer.WMS(
    "-Calles", "http://localhost:8081/geoserver/wms",
    {
        srs: "EPSG:32721",
        layers: 'TSIG:calles',
        transparent: true,
        format:'image/png'
    }

);
calles.visibility=false;
map.addLayer(calles);

// Capa transporte
var transporte = new OpenLayers.Layer.WMS(
    "-Transporte", "http://localhost:8081/geoserver/wms",
    {
        srs: "EPSG:32721",
        layers: 'transporte',
        transparent: true,
        format:'image/png'
    }

);
transporte.visibility=false;
map.addLayer(transporte);

// Capa paradas
var paradas = new OpenLayers.Layer.WMS(
    "-Paradas", "http://localhost:8081/geoserver/wms",
    {
        srs: "EPSG:32721",
        layers: 'TSIG:paradas',
        transparent: true,
        format:'image/png'
    }

);
paradas.visibility=false;
map.addLayer(paradas);


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
map.addLayer(inmueble);



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