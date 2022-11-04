package cancino.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import cancino.datos.impl.PropiedadDaoImpl;
import cancino.dominio.Propiedad;

public class PropiedadControl {
     private final PropiedadDaoImpl DATOS;
    @SuppressWarnings("unused")
	private Propiedad obj;
    private DefaultTableModel modeloTabla;
    private int registrosMostrados;

    public PropiedadControl() {
        DATOS = new PropiedadDaoImpl();
        obj = new Propiedad();
        
    }

    public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina) {
        this.registrosMostrados = 0;
        List<Propiedad> lista = new ArrayList<Propiedad>();
        lista.addAll(DATOS.listar(texto, totalPorPagina, numPagina));
        String[] titulos = {"ID", "NOMBRE", "DIRECCION", "CARACTERISTICAS  ", "ESTADO", "PRECIO ALQUILER"};
        String[] registro = new String[10];
        

        this.modeloTabla = new DefaultTableModel(null, titulos);
        for (Propiedad item : lista) {
            registro[0] = Integer.toString(item.getId());
            registro[1] = item.getNombre();
            registro[2] = item.getDireccion();
            registro[3] = item.getCaracteristicas();
            registro[4] = item.getEstado();
            registro[5] = Double.toString(item.getPrecioAlquiler());
 
            this.modeloTabla.addRow(registro);
            this.registrosMostrados++;
        }
        return this.modeloTabla;
    }

    public String insertar(Propiedad obj) {
        int id = DATOS.existe(obj.getNombre());
        if (id != 0) {
            return "La propiedad ya existe";
        } else {
            if (DATOS.insertar(obj)) {
                return "OK";
            } else {
                return "Error en el ingreso de datos.";
            }
        }
    }

    public String actualizar(Propiedad obj) {
        int id = DATOS.existe(obj.getNombre());
        if (id != 0 && id != obj.getId()) {
            return "La propiedad ya existe";
        } else {
            if (DATOS.actualizar(obj)) {
                return "OK";
            } else {
                return "Error en la actualización de datos.";
            }
        }
    }

    public String eliminar(int id) {
        if (DATOS.eliminar(id)) {
            return "OK";
        } else {
            return "Error la eliminación de datos.";
        }
    }

    public int total() {
        return DATOS.total();
    }

    public int totalRegistrosMostrados() {
        return this.registrosMostrados;
    }
}