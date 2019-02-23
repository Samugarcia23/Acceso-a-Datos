/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestoras;

import Clases.Criaturitas;
import Clases.Regalos;
import java.util.ArrayList;
import java.util.InputMismatchException;
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

public class GestoraCriaturitas {
    
    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private static final Session session = sessionFactory.openSession();
    
    //Listar todas las criaturitas
    
    public static void listarTodasLasCriaturitas(){
        String hqlQuery = "FROM Criaturitas";
        Query query = session.createQuery(hqlQuery);
        ArrayList<Criaturitas> listado = new ArrayList<>(query.list());
        for(Criaturitas criaturita : listado)
            System.out.println("\n Id: " + criaturita.getId() + "\n Nombre: " + criaturita.getNombre() + "\n Regalitos: \n" + criaturita.getRegalitos() + "\n");     
    }
    
    //Recuperar una criaturita con todos sus regalos
    
    public static void recuperarCriaturitaConTodosSusRegalos(){
        String hqlQuery = "FROM Criaturitas WHERE Nombre = 'Adela'";
        Query query = session.createQuery(hqlQuery);
        ArrayList<Criaturitas> listado = new ArrayList<>(query.list());
        for(Criaturitas criaturita : listado)
            System.out.println("\n Id: " + criaturita.getId() + "\n Nombre: " + criaturita.getNombre() + "\n Regalitos: \n" + criaturita.getRegalitos() + "\n");
    }
    
    //Quitar un regalo a una criaturita (sin borrarlo)
    
    public void quitarRegaloAUnaCriaturita(){
        Transaction transaction = session.beginTransaction();
        Scanner sc = new Scanner(System.in);
        List<Criaturitas> listadoCriaturitas;
        
        int idRegalo = 0;
        byte idCriaturita = 0;
        
        //Listamos las criaturitas para que el usuario las vea
        
        listadoCriaturitas = (List<Criaturitas>)session.createQuery("FROM Criaturitas").list();
        listadoCriaturitas.stream().forEach((cr) -> {
            System.out.println("ID: "+cr.getId()+"\n Nombre:"+cr.getNombre());
        });

        do{ 
            System.out.println("Elige una Criaturita introduciendo su Id: ");
            idCriaturita = sc.nextByte(); 
            if (idCriaturita<0){
                System.out.println("Error, Introduce un Id Correcto!");
            }
        }while(idCriaturita<0);

        Query query = session.createQuery("FROM Criaturitas WHERE id = :id");
        query.setByte("id", idCriaturita);
        Criaturitas criaturitaSeleccionada = (Criaturitas)query.uniqueResult();
        
        //Listamos los regalos de la Criaturita seleccionada
        
        System.out.println(criaturitaSeleccionada.toString());
        criaturitaSeleccionada.getRegalitos().stream().forEach((regalo) -> {
            System.out.println("Id: " + regalo.getId() + " Denominacion: " + regalo.getDenominacion()); 
        });

        do{
            System.out.println("Selecciona el Regalito que le quieres quitar introduciendo su Id: ");
            idRegalo = sc.nextInt();
            if (idRegalo<0){
                System.out.println("Error, Introduce un Id Correcto!");
            }
        }while(idRegalo<0);
        
        //Quitamos el Regalito a la Criaturita :(
        
        for(int i=0; i<criaturitaSeleccionada.getRegalitos().size();i++)
        {
            Regalos regalo = criaturitaSeleccionada.getRegalitos().get(i);
            if(regalo.getId()==idRegalo) {
                criaturitaSeleccionada.getRegalitos().remove(i);
                break;
            }
        }
        
        //Quitamos el propietario del Regalito
        
        Query query = session.createQuery("FROM Regalos WHERE id = :id");
        query.setInteger("id", idRegalo);
        Regalos regalo = (Regalos)query.uniqueResult();
        regalo.setPropietario(null);

        transaction.commit();
        
        System.out.println("/////////////////// OPERACION REALIZADA CORRECTAMENTE ///////////////////");
    }
    
    //Asignar un regalo a una criaturita (o ninguna)
    
    public void asignarRegaloAUnaCriaturita(){
        Transaction transaction = session.beginTransaction();
        Scanner sc = new Scanner(System.in);
        List<Criaturitas> listadoCriaturitas;
        List<Regalos> listadoRegalosSinCriaturita;

        
        int idRegalo = 0;
        byte idCriaturita = 0;
        
        //Listamos las criaturitas para que el usuario las vea
        
        listadoCriaturitas = (List<Criaturitas>)session.createQuery("FROM Criaturitas").list();
        listadoCriaturitas.stream().forEach((cr) -> {
            System.out.println("ID: "+cr.getId()+"\n Nombre:"+cr.getNombre());
        });

        do{ 
            System.out.println("Elige una Criaturita introduciendo su Id: ");
            idCriaturita = sc.nextByte(); 
            if (idCriaturita<0){
                System.out.println("Error, Introduce un Id Correcto!");
            }
        }while(idCriaturita<0);

        Query query = session.createQuery("FROM Criaturitas WHERE id = :id");
        query.setByte("id", idCriaturita);
        Criaturitas criaturitaSeleccionada = (Criaturitas)query.uniqueResult();
        
        //Listamos los regalos sin propietario
        
        listadoRegalosSinCriaturita = (List<Regalos>)session.createQuery("FROM Regalos").list();
        
        for(Regalos regalo:listadoRegalosSinCriaturita){
            if(regalo.getPropietario()==null)
                System.out.println(regalo.toString());
        }

        do{
            System.out.println("Selecciona el Regalito que le quieres asignar introduciendo su Id: ");
            idRegalo = sc.nextInt();
            if (idRegalo<0){
                System.out.println("Error, Introduce un Id Correcto!");
            }
        }while(idRegalo<0);

        //Asignamos el Regalito a la Criaturita Seleccionada :)
        
        query = session.createQuery("FROM Regalos WHERE id = :id");
        query.setInteger("id", idRegalo);
        
        Regalos regalo = (Regalos)query.uniqueResult();

        criaturitaSeleccionada.getRegalitos().add(regalo);
        regalo.setPropietario(criaturitaSeleccionada);

        transaction.commit();
        
        System.out.println("/////////////////// OPERACION REALIZADA CORRECTAMENTE ///////////////////");
    }
    
    //Crear una nueva criaturita
    
    public void crearNuevaCriaturita(){
        Transaction transaction = session.beginTransaction();
        Scanner sc = new Scanner(System.in);
        String nombreCriaturita;
        Byte idCriaturita = 0;
        
        do{
            System.out.println("Introduce el nombre de tu nueva Criaturita: ");
            nombreCriaturita = sc.nextLine();
            if (nombreCriaturita.equals(""))
                System.out.println("Error, el nombre no puede estar vacio!");
        }while(nombreCriaturita.equals(""));
        
        //El id de la Criaturita es el siguiente al mas alto
        
        Query query = session.createQuery("SELECT MAX(id) FROM Criaturitas");
        query.setByte("id", idCriaturita + 1);
        
        Criaturitas criaturita = new Criaturitas(idCriaturita, nombreCriaturita); 
        session.save(criaturita);
        transaction.commit(); 
        
        System.out.println("/////////////////// OPERACION REALIZADA CORRECTAMENTE ///////////////////");
    }
    
    //Borrar una criaturita y todos sus regalos
    
    public void borrarUnaCriaturitaYTodosSusRegalos(){
        Transaction transaction = session.beginTransaction();
        Scanner sc = new Scanner(System.in);
        List<Criaturitas> listadoCriaturitas;

        byte idCriaturita;
        
        //Listamos las criaturitas para que el usuario las vea
        
        listadoCriaturitas = (List<Criaturitas>)session.createQuery("FROM Criaturitas").list();
        listadoCriaturitas.stream().forEach((criaturita) -> {
            System.out.println("ID: "+criaturita.getId()+"\n Nombre:"+criaturita.getNombre());
        });

        do{ 
            System.out.println("Elige la Criaturita a borrar introduciendo su Id: ");
            idCriaturita = sc.nextByte(); 
            if (idCriaturita<0){
                System.out.println("Error, Introduce un Id Correcto!");
            }
        }while(idCriaturita<0);
        
        //Listamos todos los regalos y asignamos a null su propietario
        
        Query query = session.createQuery("FROM Regalos WHERE GoesTo = :idPropietario");
        query.setInteger("idPropietario", idCriaturita);
        List<Regalos> listadoRegalosCriaturita = (List<Regalos>)query.list();
        
        listadoRegalosCriaturita.stream().forEach((regalo) -> {
            regalo.setPropietario(null);
        });
        
        //Borramos la Criaturita (T.T)
      
        Criaturitas criaturita = (Criaturitas)session.get(Criaturitas.class,idCriaturita);
        session.delete(criaturita);
        
        transaction.commit(); 
        
        System.out.println("/////////////////// OPERACION REALIZADA CORRECTAMENTE ///////////////////");
    }
}
