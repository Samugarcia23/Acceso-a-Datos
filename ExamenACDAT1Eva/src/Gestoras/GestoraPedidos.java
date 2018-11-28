package Gestoras;

import Conexion.MyConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class GestoraPedidos {

    private Connection conexion;

    //Constructor. Abrimos una nueva conexion
    public GestoraPedidos() throws SQLException {
        conexion = MyConnection.getConexion();
    }

    //Metodo para poder ejecutar las sentencias SQL
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

    //Funcion que muestra un listado con los envios pendientes de servir
    public void mostrarPedidosPendientes(){
        String sentencia = "SELECT P.IDPedido, P.FechaPedido, C.Nombre, C.SegundoNombre, C.Apellidos, C.NombreEmpresa FROM Pedidos AS P INNER JOIN Clientes AS C ON P.IDCliente = C.ID WHERE FechaServido IS NULL ORDER BY FechaPedido";
        ResultSet rs = ejecutarQuery(sentencia);

        if(rs != null){
            try{
                    while(rs.next()){
                        System.out.println("ID Pedido: " + rs.getInt("IDPedido") + " || Fecha Pedido: " + rs.getDate("FechaPedido").toString() + " || Nombre: " + rs.getString("Nombre") + " || Segundo Nombre: " + rs.getString("SegundoNombre") + " || Apellidos: " + rs.getString("Apellidos") + " || Nombre Empresa: " + rs.getString("NombreEmpresa")) ;
                        System.out.println("");
                    }
            }catch (SQLException ex){
                ex.getMessage();
            }
        }
    }

    //Funcion que valida si la ID del pedido sin servir es correcta
    public boolean validarIDPedidoSinServir(int IDPedido) throws SQLException {
        boolean esValido = false;
        String sql = "SELECT dbo.fnIDPedidoEsValida(?) AS ret";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setInt(1, IDPedido);
        ResultSet rs = sentencia.executeQuery();

        if(rs != null){
            rs.next();
            esValido = rs.getBoolean("ret");
        }

        return  esValido;
    }

    public void formularioServirPedido() throws SQLException {
        Scanner sc1 = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        char respuesta;
        int idPedidoSinAsignar, idProducto, idProductoSust;
        boolean idPedidoEsValida, hayStock;
        ResultSet listadoIDPedidos;

        System.out.println("Pedidos sin Servir: \n");
        mostrarPedidosPendientes();

        //Comprobamos que la id sea correcta
        do{
            System.out.println("Introduce la ID de uno de los pedidos sin servir: ");
            idPedidoSinAsignar = sc2.nextInt();
            idPedidoEsValida = validarIDPedidoSinServir(idPedidoSinAsignar);
            if (!idPedidoEsValida)
                System.out.println("ID Erronea, vuelve a intentarlo...");
        }while(!idPedidoEsValida);

        //Obtenemos la ID del producto
        listadoIDPedidos = obtenerListadoIDProductosEnPedidos(idPedidoSinAsignar);
        if (listadoIDPedidos != null){
            hayStock = true;
            while (listadoIDPedidos.next() && hayStock) {
                idProducto = listadoIDPedidos.getInt("IDProducto");
                //Comprobamos si hay stock
                hayStock = hayStockSuficiente(idPedidoSinAsignar, idProducto);
                System.out.println("El producto '" + listadoIDPedidos.getString("Nombre") + "' está disponible \n");
                if (!hayStock) {
                    System.out.println("El producto '" + listadoIDPedidos.getString("Nombre") + "' no está disponible");

                    do{
                        System.out.println("¿Quiere elegir un producto sustituto?");
                        respuesta = Character.toLowerCase(sc2.next().charAt(0));
                    }while(respuesta != 'y' && respuesta != 'n' );

                    if (respuesta == 'y'){

                        //Mostrar productos sustitutivos
                        idProductoSust = obtenerProductoSustitutivo(idProducto);
                        if (idProductoSust != 0){
                            System.out.println("El producto con id " + idProductoSust + " es sustituto del producto '" + listadoIDPedidos.getString("Nombre"));
                            do{
                                System.out.println("¿Quiere cambiar por dicho producto?");
                                respuesta = Character.toLowerCase(sc2.next().charAt(0));
                            }while(respuesta != 'y' && respuesta != 'n' );
                            if (respuesta == 'y'){
                                idProducto = idProductoSust;
                            }
                        }

                    }
                }
            }
            //Insertar el nuevo pedido servido
            servirPedido2(idPedidoSinAsignar);
        }
    }

    //Funcion que devuelve un boolean true si hay stock del producto especificado y false si no hay
    public boolean hayStockSuficiente(int IDPedido, int IDProducto) throws SQLException {
        String sql = "SELECT dbo.fnHayStock(?, ?) AS ret";
        boolean hayStock = false;

        PreparedStatement sentencia = conexion.prepareStatement(sql);

        sentencia.setInt(1, IDPedido);
        sentencia.setInt(2, IDProducto);

        ResultSet rs = sentencia.executeQuery();

        if(rs != null){
            rs.next();
            hayStock = rs.getBoolean("ret");
        }

        return hayStock;
    }

    public int obtenerProductoSustitutivo(int IDProducto) throws SQLException {

        int idProductoSust = 0;
        String sql = "SELECT Sustitutivo FROM Productos WHERE IDProducto = ?";
        PreparedStatement sentencia = conexion.prepareStatement(sql);

        sentencia.setInt(1, IDProducto);
        ResultSet rs = sentencia.executeQuery();
        if (rs != null){
            rs.next();
            idProductoSust = rs.getInt("Sustitutivo");
        }

        return idProductoSust;
    }

    //Funcion que devuelve un listado de idProductos segun la idPedido
    public ResultSet obtenerListadoIDProductosEnPedidos(int idPedido) throws SQLException {
        String sql = "SELECT P.IDProducto, P.Nombre FROM PRODUCTOS AS P INNER JOIN LineasPedidos L ON P.IDProducto = L.IDProducto WHERE L.IDPedido = " + idPedido;
        ResultSet rs = ejecutarQuery(sql);

        return rs;
    }

    //Funcion que sirve un nuevo pedido (No Funciona de esta forma)
    public boolean servirPedido(int IDPedido) throws SQLException {
        boolean servido, filaEncontrada = false, insertValido;
        LocalDate date = LocalDate.now();

        String sql = "INSERT INTO Pedidos (FechaServido) VALUES (?)";
        PreparedStatement sentencia = conexion.prepareStatement(sql);
        sentencia.setDate(1, java.sql.Date.valueOf(date));

        //Se comprueba si el insert es valido, si lo es, manda true
        insertValido = sentencia.executeUpdate() == 1;

        sql = "SELECT IDPedido, FechaServido FROM Pedidos";
        Statement sentencia2 = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = sentencia2.executeQuery(sql);
        while (rs.next() && !filaEncontrada){
            if(rs.getInt("IDPedido") == IDPedido) {
                filaEncontrada = true;
                rs.updateDate("FechaServido", java.sql.Date.valueOf(date));
                rs.updateRow();
            }
        }

        if(insertValido && filaEncontrada){
            servido = true;
            System.out.println("Todos los productos están disponibles, se ha servido un nuevo pedido");
        }else{
            servido = false;
            System.out.println("Error, no se han podido insertar los pedidos");
        }

        return servido;
    }

    public boolean servirPedido2(int IDPedido) throws SQLException {
        boolean servido, filaEncontrada = false, updateValido;
        LocalDate date = LocalDate.now();

        String sql = "UPDATE Pedidos SET FechaServido = ? WHERE IDPedido = ?";
        PreparedStatement sentencia = conexion.prepareStatement(sql);

        sentencia.setDate(1, java.sql.Date.valueOf(date));
        sentencia.setInt(2, IDPedido);


        //Se comprueba si el insert es valido, si lo es, manda true
        updateValido = sentencia.executeUpdate() == 1;

        sql = "SELECT IDPedido, FechaServido FROM Pedidos";
        Statement sentencia2 = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = sentencia2.executeQuery(sql);
        while (rs.next() && !filaEncontrada){
            if(rs.getInt("IDPedido") == IDPedido) {
                filaEncontrada = true;
                rs.updateDate("FechaServido", java.sql.Date.valueOf(date));
                rs.updateRow();
            }
        }

        if (updateValido && filaEncontrada){
            servido = true;
            System.out.println("Todos los productos están disponibles, se ha servido un nuevo pedido");
        }else{
            servido = false;
            System.out.println("Error, no se han podido insertar los pedidos");
        }

        return servido;
    }


    //Funcion que muestra los pedidos ya servidos
    public void mostrarPedidosServidos(){
        String sentencia = "SELECT P.IDPedido, P.FechaPedido, P.FechaServido, C.Nombre, C.SegundoNombre, C.Apellidos, C.NombreEmpresa FROM Pedidos AS P INNER JOIN Clientes AS C ON P.IDCliente = C.ID WHERE FechaServido IS NOT NULL ORDER BY P.FechaServido";
        ResultSet rs = ejecutarQuery(sentencia);

        if(rs != null){
            try{
                while(rs.next()){
                    System.out.println("ID Pedido: " + rs.getInt("IDPedido") + " || Fecha Pedido: " + rs.getDate("FechaPedido").toString() + " || Fecha Servido: " + rs.getDate("FechaServido").toString() + " || Nombre: " + rs.getString("Nombre") + " || Segundo Nombre: " + rs.getString("SegundoNombre") + " || Apellidos: " + rs.getString("Apellidos") + " || Nombre Empresa: " + rs.getString("NombreEmpresa")) ;
                    System.out.println("");
                }
            }catch (SQLException ex){
                ex.getMessage();
            }
        }
    }

}
