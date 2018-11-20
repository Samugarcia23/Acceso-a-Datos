package Gestoras;

import Conexion.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Scanner;

public class GestoraEnvios {
    private Connection conexion;

    public GestoraEnvios() throws SQLException {
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

    public void obtenerEnviosAsignados(){
        String sentencia = "SELECT A.Denominacion, E.FechaAsignacion, E.NumeroContenedores FROM Envios AS E INNER JOIN Asignaciones AS ASI ON E.ID = ASI.IDEnvio INNER JOIN Almacenes AS A  ON ASI.IDAlmacen = A.ID WHERE FechaAsignacion IS NOT NULL";
        ResultSet rs = ejecutarQuery(sentencia);
        if(rs!=null)
        {
            try
            {
                while(rs.next())
                {
                    System.out.println("Fecha de Asignacion: " + rs.getDate("FechaAsignacion").toString() + " | Nº Contenedores: "+rs.getInt("NumeroContenedores") + " | Nombre de Almacen: " + rs.getString("Denominacion"));
                    System.out.println("");
                }
            }catch(SQLException e) { e.printStackTrace(); }
        }
    }
    
    public ResultSet obtenerEnviosSinAsignar(){
        String sentencia = "SELECT E.FechaAsignacion, E.NumeroContenedores, E.ID, E.AlmacenPreferido FROM Envios AS E INNER JOIN Asignaciones AS ASI ON E.ID = ASI.IDEnvio INNER JOIN Almacenes AS A  ON ASI.IDAlmacen = A.ID WHERE FechaAsignacion IS NULL";
        ResultSet rs = ejecutarQuery(sentencia);
        return rs;
    }
    
    public boolean cabePedidoEnAlmacen(int idEnvio, int idAlmacen) throws SQLException{
        boolean cabe = false;
        String sql = "SELECT * FROM fnCabePedidoEnAlmacen(?, ?)";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, idEnvio);
        sentencia.setInt(2, idAlmacen);
        ResultSet rs = sentencia.executeQuery();
        cabe = rs.getBoolean(sql);
        return cabe;
    }
    
    public void mostrarAlmacenes(){
        String sql = "SELECT * FROM Almacenes";
        ResultSet rs = ejecutarQuery(sql);
        if(rs!=null)
        {
            try
            {
                while(rs.next())
                {
                    System.out.println(rs.getInt("ID") + " | " + rs.getString("Denominacion") + " | " + rs.getString("Direccion") + " | " + rs.getInt("Capacidad"));
                    System.out.println("");
                }
            }catch(SQLException e) { e.printStackTrace(); }
        }   
    }
    
    public boolean validarIdAlmacen(int idAlmacen) throws SQLException{
        boolean existe = false;
        String sql = "EXECUTE fnValidarIdAlmacen ?";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, idAlmacen);
        ResultSet rs = sentencia.executeQuery();
        existe = rs.getBoolean(sql);
        return existe;
    }
    
    public void agregarEnvioFormulario() throws SQLException{
        Scanner sc = new Scanner(System.in);
        int numCont = 0, idAlmacenPreferido;
        char respuesta;
        boolean idEsValida;
        
        do{
            System.out.println("Introduce el numero de contenedores:");
            numCont = sc.nextInt();
        }while(numCont <= 0);
        
        System.out.println("Almacenes Disponibles: \n");
        mostrarAlmacenes();
        
        do{
            System.out.println("Introduce el ID de tu almacén preferido: ");
            idAlmacenPreferido = sc.nextInt();
            idEsValida = validarIdAlmacen(idAlmacenPreferido);
            if(!idEsValida)
                System.out.println("Id Errónea, vuelve a intentarlo");
        }while(!idEsValida);
        
        do{
            System.out.println("¿Desea agregar el envio? (y/n)");
            respuesta=Character.toLowerCase(sc.next(".").charAt(0));
        }while(respuesta != 'y' || respuesta != 'n' );
        if (respuesta == 'y'){
            if(agregarEnvio(idAlmacenPreferido, numCont))
                System.out.println("Envío agregado correctamente!!");  
            else
                System.out.println("Envío no agregado :( ");
        }   
    }
    
    public boolean agregarEnvio(int idAlmacen, int nContenedores) throws SQLException
    {
        boolean creado;
        int filasAfectadas;
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        String sql = "INSERT INTO Envios(NumeroContenedores, FechaCreacion, AlmacenPreferido) VALUES (?, ?, ?)";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        
        sentencia.setInt(1, nContenedores);
        sentencia.setDate(2, date);
        sentencia.setInt(3, idAlmacen);
        
        filasAfectadas = sentencia.executeUpdate();
        
        if (filasAfectadas == 1){
            creado = true;
        }else
            creado = false;
        
        return creado;
    }
    
}
