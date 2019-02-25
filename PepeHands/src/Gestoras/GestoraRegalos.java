/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestoras;

import Clases.Criaturitas;
import Clases.Regalos;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.Transaction;

/**
 *
 * @author sgarcia
 */
public class GestoraRegalos {

    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private static final Session session = sessionFactory.openSession();
    
    //Listar todos los regalos
    
    public static void listarTodosLosRegalos()
    {
        String hqlQuery = "FROM Regalos";
        Query query = session.createQuery(hqlQuery);
        ArrayList<Regalos> listado = new ArrayList<>(query.list());
        for(Regalos regalo : listado)
            System.out.println("\n Id: " + regalo.getId()+"\n Denominacion: "+regalo.getDenominacion()+"\n Ancho: "+regalo.getAncho()+"\n Largo: "+regalo.getLargo()+"\n Alto: "+regalo.getAlto()+"\n Tipo: "+regalo.getTipo()+"\n Edad mínima: "+regalo.getEdadMinima()+"\n Precio: "+regalo.getPrecio() + "\n");
    }
    
    //Crear un nuevo regalo
    
    public static void crearUnNuevoRegalo(){
        Transaction transaction = session.beginTransaction();
        Scanner sc = new Scanner(System.in);
        String nombreRegalo;
        int idRegalo, ancho, largo, alto, edadMinima;
        char tipo;
        BigDecimal precio;
        
        do{
            System.out.println("Introduce el nombre de tu nuevo Regalito: ");
            nombreRegalo = sc.nextLine();
            if (nombreRegalo.equals(""))
                System.out.println("Error, el nombre no puede estar vacio!");
        }while(nombreRegalo.equals(""));
            
        do{
            System.out.println("Introduce el ancho de tu nuevo Regalito: ");
            ancho = sc.nextInt();
            if (ancho < 0 || ancho == 0)
                System.out.println("Error, introduce un valor correcto!");
        }while(ancho < 0 || ancho == 0);
        
        do{
            System.out.println("Introduce el largo de tu nuevo Regalito: ");
            largo = sc.nextInt();
            if (largo < 0 || largo == 0)
                System.out.println("Error, introduce un valor correcto!");
        }while(largo < 0 || largo == 0);

        do{
            System.out.println("Introduce el alto de tu nuevo Regalito: ");
            alto = sc.nextInt();
            if (alto < 0 || alto == 0)
                System.out.println("Error, introduce un valor correcto!");
        }while(alto < 0 || alto == 0);
        
        do{
            System.out.println("Introduce la edad minima de tu nuevo Regalito: ");
            edadMinima = sc.nextInt();
            if (edadMinima < 0)
                System.out.println("Error, introduce un valor correcto!");
        }while(edadMinima < 0);

        do{
            System.out.println("Introduce el precio de tu nuevo Regalito: ");
            precio = sc.nextBigDecimal();
            if (precio.compareTo(BigDecimal.ZERO) == 0 || precio.compareTo(BigDecimal.ZERO) < 0)
                System.out.println("Error, introduce un valor correcto!");
        }while(precio.compareTo(BigDecimal.ZERO) == 0 || precio.compareTo(BigDecimal.ZERO) < 0);
        
        do{
            System.out.println("Introduce el tipo de tu nuevo Regalito: ");
            tipo = sc.next().charAt(0);
            tipo = Character.toUpperCase(tipo);
            if (tipo == ' ')
                System.out.println("Error, introduce un valor correcto!");
        }while(tipo == ' ');

        //El id del Regalito es el siguiente al mas alto
        
        idRegalo = (int) session.createQuery("SELECT MAX(id) FROM Regalos").uniqueResult();
        
        idRegalo++;
        
        Regalos regalo = new Regalos(idRegalo, nombreRegalo, ancho, largo, alto, edadMinima, precio, tipo); 
        session.save(regalo);
        transaction.commit(); 
        
        System.out.println("/////////////////// OPERACION REALIZADA CORRECTAMENTE ///////////////////");
    }
    
    //Borrar un regalo
    
    public static void borrarUnRegalo(){
        Transaction transaction = session.beginTransaction();
        Scanner sc = new Scanner(System.in);
        List<Regalos> listadoRegalos;

        int idRegalo;
        
        //Listamos los Regalos para que el usuario los vea
        
        listadoRegalos = (List<Regalos>)session.createQuery("FROM Regalos").list();
        listadoRegalos.stream().forEach((regalo) -> {
            System.out.println("ID: "+regalo.getId()+"\n Denominacion:"+regalo.getDenominacion());
        });

        do{ 
            System.out.println("Elige el Regalo a borrar introduciendo su Id: ");
            idRegalo = sc.nextByte(); 
            if (idRegalo<0)
                System.out.println("Error, Introduce un Id Correcto!");
        }while(idRegalo<0);

        //Borramos el Regalito (T.T)
      
        Regalos regalo = (Regalos)session.get(Regalos.class, idRegalo);
        session.delete(regalo);
        
        transaction.commit(); 
        
        System.out.println("/////////////////// OPERACION REALIZADA CORRECTAMENTE ///////////////////");
    }
    
}
