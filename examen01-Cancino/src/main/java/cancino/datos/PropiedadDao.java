package cancino.datos;

import java.util.List;

public interface PropiedadDao<T> {
    public List<T> listar(String texto,int totalPorPagina,int numPagina);
    public boolean insertar(T obj);
    public boolean actualizar(T obj);
    public boolean eliminar(int id);
    public int total();
    public int existe(String texto);
}
