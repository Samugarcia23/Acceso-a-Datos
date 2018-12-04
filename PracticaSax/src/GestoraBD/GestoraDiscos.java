package GestoraBD;

import Conexion.GeneradorConexiones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestoraDiscos {

    private Connection conexion;

    public GestoraDiscos() throws SQLException {
        conexion = GeneradorConexiones.getConexion();
    }

    public ResultSet ejecutarQuery(String sentenciaSQL) {
        ResultSet resultado;
        Statement sentencia;
        try{
            sentencia=conexion.createStatement();
            resultado=sentencia.executeQuery(sentenciaSQL);
        } catch(SQLException sql){
            System.out.println(sql.getMessage());
            return null;
        }
        return resultado;
    }

    public void agregarDiscos(String autor, String titulo, String formato, String localizacion) throws SQLException {

        String sql = "SELECT autor, titulo, formato, localizacion FROM Discos";
        Statement sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = sentencia.executeQuery(sql);

        rs.moveToInsertRow();
        rs.updateString("autor", autor);
        rs.updateString("titulo", titulo);
        rs.updateString("formato", formato);
        rs.updateString("localizacion", localizacion);
        rs.insertRow();
        rs.moveToCurrentRow();

    }

}
