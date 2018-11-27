package Gestoras;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestoraFormularios
{
    public static void asignarEnvioFormulario() throws SQLException
    {
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        int idAlmacen, idEnvio, idAlmacenCercano;
        char respuesta;
        ResultSet almacenesDistancia;
        boolean idEsValida, envioAsignado;
        GestoraEnvios gestoraEnvios = new GestoraEnvios();
        GestoraAlmacenes gestoraAlmacenes = new GestoraAlmacenes();

        //Mostramos env�os sin asignar
        System.out.println("Envios Sin Asignar: \n");
        gestoraEnvios.mostrarEnviosSinAsignar();

        //Validamos la ID que se ha introducido
        do{
            System.out.println("Introduce la ID de un envio sin asignar");
            idEnvio = sc.nextInt();
            idEsValida = gestoraEnvios.validarIdEnvioSinAsignar(idEnvio);
            if(!idEsValida)
                System.out.println("Id Err�nea, vuelve a intentarlo");
        }while(!idEsValida);

        //Obtenemos la ID del Almac�n Preferido
        idAlmacen = gestoraAlmacenes.obtenerIdAlmacenPreferido(idEnvio);


        if(gestoraAlmacenes.cabePedidoEnAlmacen(idAlmacen, idEnvio)) //Si el env�o cabe en el almac�n preferido
        {
            if (gestoraEnvios.asignarEnvioAlmacen(idEnvio, idAlmacen))
                //Asignamos el env�o al almac�n preferido y mostramos mensaje de �xito
                System.out.println("Se ha asignado el envio al almac�n preferido correctamente");
            else
                //Si ha ocurrido alg�n error al asignar el env�o, mostramos un mensaje de error
                System.out.println("Error, no se ha podido asignar el envio al almacen preferido");
        }
        else //Si el env�o no cabe en el almac�n preferido
        {
            //Obtenemos un listado de almacenes ordenado por distancia
            System.out.println("El envio no cabe en el almacen preferido, comprobando almacenes m�s cercanos...");
            almacenesDistancia = gestoraAlmacenes.obtenerListadoAlmacenesPorDistancia(idAlmacen);
            if (almacenesDistancia != null) //Si hay almacenes en los que mirar
            {
                envioAsignado = false;
                //Mientras haya almacenes que mirar y no se haya asignado ya el env�o
                while(almacenesDistancia.next() && !envioAsignado)
                {
                    //Comprobamos que la ID del almac�n m�s cercano no sea la misma que la del almac�n preferido
                    //Si es as�, cogemos la ID del almac�n 2
                    idAlmacenCercano = almacenesDistancia.getInt("IDAlmacen1");
                    if (idAlmacenCercano == idAlmacen){
                        idAlmacenCercano = almacenesDistancia.getInt("IDAlmacen2");
                    }

                    //Comprobamos si cabe en el almac�n
                    if (gestoraAlmacenes.cabePedidoEnAlmacen(idAlmacenCercano, idEnvio))
                    {
                        //Si cabe en el almac�n, se pregunta si desea aceptar ese almac�n
                        System.out.println("El pedido cabe en el almacen: " + almacenesDistancia.getString("Denominacion"));
                        do{
                            System.out.println("�Desea asignar el pedido a ese almacen? (y/n)");
                            respuesta = Character.toLowerCase(sc2.next().charAt(0));
                        }while(respuesta != 'y' && respuesta != 'n' );

                        //Si la respuesta es que s�
                        if (respuesta == 'y')
                        {
                            envioAsignado = true;
                            //Se asigna el env�o al almac�n elegido
                            if(gestoraEnvios.asignarEnvioAlmacen(idEnvio, idAlmacenCercano))
                                System.out.println("��Env�o asignado correctamente!!\n");
                            else
                                System.out.println("Env�o no asignado :( \n");
                        }
                    }
                }

                //Si no se ha podido asignar o no ha querido asignarlo a ning�n almac�n
                if (!envioAsignado)
                {
                    //Motrar mensaje no hay almacenes
                    System.out.println("Tu envio no se ha podido asignar en ningun almac�n");
                }
            }
        }
    }

    public static void agregarEnvioFormulario() throws SQLException{
        Scanner sc = new Scanner(System.in);
        Scanner sc2 = new Scanner(System.in);
        int numCont = 0, idAlmacenPreferido;
        char respuesta;
        boolean idEsValida;
        GestoraEnvios gestoraEnvios = new GestoraEnvios();
        GestoraAlmacenes gestoraAlmacenes = new GestoraAlmacenes();

        do{
            System.out.println("Introduce el numero de contenedores:");
            numCont = sc.nextInt();
        }while(numCont <= 0);

        System.out.println("Almacenes Disponibles: \n");
        gestoraAlmacenes.mostrarAlmacenes();

        do{
            System.out.println("Introduce el ID de tu almac�n preferido: ");
            idAlmacenPreferido = sc.nextInt();
            idEsValida = gestoraAlmacenes.validarIdAlmacen(idAlmacenPreferido);
            if(!idEsValida)
                System.out.println("Id Err�nea, vuelve a intentarlo");
        }while(!idEsValida);

        do{
            System.out.println("�Desea agregar el envio? (y/n)");
            respuesta = Character.toLowerCase(sc2.next().charAt(0));
        }while(respuesta != 'y' && respuesta != 'n' );

        if (respuesta == 'y'){
            if(gestoraEnvios.agregarEnvio(idAlmacenPreferido, numCont))
                System.out.println("��Env�o agregado correctamente!!\n");
            else
                System.out.println("Env�o no agregado :(\n");
        }
        else
            System.out.println("Okay, pos no se agrega na\n");
    }

}
