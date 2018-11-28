import Gestoras.GestoraMenu;
import Gestoras.GestoraPedidos;

import java.sql.SQLException;
import java.util.Scanner;

public class Amazon {
    public static void main(String[] args) throws SQLException {

        GestoraPedidos gestoraPedidos = null;
        try {
            gestoraPedidos = new GestoraPedidos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        int opcion;

        do{
            do{
                GestoraMenu.MostrarMenu();
                opcion = sc.nextInt();
                if(opcion < 0 || opcion > 3)
                    System.out.println("Error, introduce una opcion correcta!");
            }while(opcion < 0 || opcion >2);

            switch (opcion)
            {
                case 1:
                    gestoraPedidos.formularioServirPedido();
                    break;

                case 2:
                    System.out.println("Pedidos Servidos \n");
                    gestoraPedidos.mostrarPedidosServidos();
                break;
            }


        }while(opcion!=0);

    }
}
