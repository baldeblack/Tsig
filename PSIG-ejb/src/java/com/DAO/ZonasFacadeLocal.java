
package com.DAO;

import com.entity.Zonas;
import java.util.List;
import javax.ejb.Local;


@Local
public interface ZonasFacadeLocal {

    public int crearZona(Zonas zon, String coordenadas);
    
    boolean editarZona(Zonas zon);
    
    List<Zonas> allZona();

    Zonas buscarZona(int gidzona);
    
    boolean borrar(Zonas zona);

    public boolean interseccionZonas(String coordenadas);

}
