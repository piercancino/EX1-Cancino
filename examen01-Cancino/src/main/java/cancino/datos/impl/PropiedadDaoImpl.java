package cancino.datos.impl;

import cancino.database.Conexion;
import cancino.datos.PropiedadDao;
import cancino.dominio.Propiedad;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PropiedadDaoImpl implements PropiedadDao<Propiedad> {
    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    
    public PropiedadDaoImpl(){
        CON=Conexion.getInstancia();
    }
    
    @Override
    public List<Propiedad> listar(String texto,int totalPorPagina, int numPagina) {
        List<Propiedad> lista = new ArrayList<Propiedad>();
        try {
            ps=CON.conectar().prepareStatement("select p.id, p.nombre, p.direccion, p.caracteristicas, p.estado, p.precioalquiler from propiedades p where p.nombre Like ? order by p.id asc limit ?,?");
            ps.setString(1,"%"+texto+"%");
            ps.setInt(2, (numPagina-1)*totalPorPagina);
            ps.setInt(3, totalPorPagina);
            rs=ps.executeQuery();
            while(rs.next()){
                lista.add(new Propiedad(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6)));
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return lista;
    }
   

    @Override
    public boolean insertar(Propiedad obj) {
        boolean resp=false;
        try {
            ps = CON.conectar().prepareStatement("INSERT INTO propiedades (nombre,direccion,caracteristicas,estado,precioalquiler) VALUES (?,?,?,?,?)");
            ps.setString(1, obj.getNombre());
            ps.setString(2, obj.getDireccion());
            ps.setString(3, obj.getCaracteristicas());
            ps.setString(4, obj.getEstado()); 
            ps.setDouble(5, obj.getPrecioAlquiler());

            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            ps = null;
            CON.desconectar();
        }
        return resp;
    }

    @Override
    public boolean actualizar(Propiedad obj) {
        boolean resp=false;
        try {
            ps = CON.conectar().prepareStatement("UPDATE propiedades set nombre=?,direccion=?,caracteristicas=?,estado=?,precioalquiler=? WHERE id=?");
            ps.setString(1, obj.getNombre());
            ps.setString(2, obj.getDireccion());
            ps.setString(3, obj.getCaracteristicas());
            ps.setString(4, obj.getEstado());
            ps.setDouble(5, obj.getPrecioAlquiler());    
            ps.setInt(6, obj.getId());

            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            ps = null;
            CON.desconectar();
        }
        return resp;
    }
    
    @Override
    public boolean eliminar(int id) {
        boolean resp=false;
        try {
            ps = CON.conectar().prepareStatement("DELETE FROM propiedades WHERE id=?");
            ps.setInt(1, id);
            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            ps = null;
            CON.desconectar();
        }
        return resp;
    }


    @Override
    public int existe(String texto) {
        int id=0;
        try {
            ps=CON.conectar().prepareStatement("select id from propiedades where nombre=?");
            ps.setString(1, texto);
            rs=ps.executeQuery();
            while(rs.next()){
                id=rs.getInt(1);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            ps=null;
            rs=null;
            CON.desconectar();
        }
        return id;
    }

	@Override
	public int total() {
		// TODO Auto-generated method stub
		return 0;
	}
}