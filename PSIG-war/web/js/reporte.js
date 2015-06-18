function reporte() {
    var reporte =document.getElementById('form-validator:output').value;
    $("#tabla-reporte").hide();
    var result = "";
    var retorno = $("#retorno");
    retorno.html('');
    //alert(coordenadas);
    if(reporte!='[]' || reporte!=""){
        var string = reporte.split(':');
        var largo=string.length;
        if (largo>0){
            var index;
            //***********itero en el string de coordenadas *********************
            var data1 = [];
            for (index = 0; index < string.length; ++index) {
                //*******PARSEO LA COORDENADA***********************************
                var valor_actual =string[index];
                valor_actual= valor_actual.trim();
                valor_actual=valor_actual.toString().replace('[','');
                valor_actual=valor_actual.toString().replace(']','');
                var lonlat = valor_actual.split(",");
                
                var prueba = {
                        "barrio": lonlat[1],
                        "porc": lonlat[2],
                        "demanda": lonlat[3]
                };
                
                //var serie = new Array(prueba.barrio, prueba.porc,prueba.demanda);
                data1.push(prueba);
            }  

            $.each(data1, function (id, item) {
                    result += '<tr>' + 
                              '<td>' + item.barrio + '</td>' +
                              '<td>' + item.porc + '</td>' +
                              '<td>' + item.demanda + '</td>' +
                              '</tr>';

            });

            if (result != "") {
                retorno.html(result);
                $("#tabla-reporte").show();
            }

        }
    }
    
};


