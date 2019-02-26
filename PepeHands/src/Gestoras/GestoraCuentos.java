/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestoras;

import Clases.Criaturitas;
import Clases.Cuento;
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
public class GestoraCuentos {
    
    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private static final Session session = sessionFactory.openSession();
    
    //Listar todos los cuentos
    
    public static void listarTodosLosCuentos(){
        String hqlQuery = "FROM Cuento";
        Query query = session.createQuery(hqlQuery);
        ArrayList<Cuento> listado = new ArrayList<>(query.list());
        for(Cuento cuento : listado)
            System.out.println("\n Id: " + cuento.getId()+"\n Titulo: "+cuento.getTitulo()+"\n Autor: "+cuento.getAutor()+"\n Tema: "+cuento.getTema()+"\n Lectores: \n"+cuento.getListaLectores() + "\n");
    }
    
    //Recuperar una criaturita con todos sus cuentos
    
    public static void recuperarCriaturitaConTodosSusCuentos(){
        Scanner sc = new Scanner(System.in);
        List<Criaturitas> listadoCriaturitas;
        byte idCriaturita = 0;
   
        //Listamos las criaturitas para que el usuario las vea
        
        listadoCriaturitas = (List<Criaturitas>)session.createQuery("FROM Criaturitas").list();
        listadoCriaturitas.stream().forEach((cr) -> {
            System.out.println("ID: "+cr.getId()+"\n Nombre:"+cr.getNombre() + "\n Cuento/s: " + cr.getListaCuentos());
        });
        
        //El usuario introduce el Id de la criaturita por teclado

        do{ 
            System.out.println("Elige una Criaturita introduciendo su Id: ");
            idCriaturita = sc.nextByte(); 
            if (idCriaturita<0){
                System.out.println("Error, Introduce un Id Correcto!");
            }
        }while(idCriaturita<0);
        
        //Seleccionamos la Criaturita mediante el ID introducido

        Query query = session.createQuery("FROM Criaturitas WHERE id = :id");
        query.setByte("id", idCriaturita);
        Criaturitas criaturitaSeleccionada = (Criaturitas)query.uniqueResult();
        
        System.out.println("\n ID: "+criaturitaSeleccionada.getId()+"\n Nombre:"+criaturitaSeleccionada.getNombre() + "\n Cuento/s: " + criaturitaSeleccionada.getListaCuentos() + "\n");
    }
    
    //Recuperar un Cuento y su lista de lectores
    
    public static void recuperarCuentosYListaDeLectores(){
        Scanner sc = new Scanner(System.in);
        List<Cuento> listadoCuentos;
        int idCuento;
   
        //Listamos las criaturitas para que el usuario las vea
        
        listadoCuentos = (List<Cuento>)session.createQuery("FROM Cuento").list();
        listadoCuentos.stream().forEach((cuento) -> {
            System.out.println("\n Id: " + cuento.getId()+"\n Titulo: "+cuento.getTitulo()+"\n Autor: "+cuento.getAutor()+"\n Tema: "+cuento.getTema()+"\n Lectores: \n"+cuento.getListaLectores() + "\n");
        });
        
        //El usuario introduce el Id del cuento por teclado

        do{ 
            System.out.println("Elige un Cuento introduciendo su Id: ");
            idCuento = sc.nextByte(); 
            if (idCuento<0){
                System.out.println("Error, Introduce un Id Correcto!");
            }
        }while(idCuento<0);
        
        //Seleccionamos el Cuento mediante el ID introducido

        Query query = session.createQuery("FROM Cuento WHERE id = :id");
        query.setInteger("id", idCuento);
        Cuento cuentoSeleccionado = (Cuento)query.uniqueResult();
        
        //Imprimimos el Cuento
        
        System.out.println("\n Id: " + cuentoSeleccionado.getId() + "\n Titulo: "+cuentoSeleccionado.getTitulo() + "\n Lectores: \n" + cuentoSeleccionado.getListaLectores() + "\n");       
    }
    
    //Quitar un cuento a una criaturita
    
    public static void quitarCuentoAUnaCriaturita(){
        Transaction transaction = session.beginTransaction();
        Scanner sc = new Scanner(System.in);
        List<Criaturitas> listadoCriaturitas;
        
        int idCuento;
        byte idCriaturita = 0;
        
        //Listamos las criaturitas para que el usuario las vea
        
        listadoCriaturitas = (List<Criaturitas>)session.createQuery("FROM Criaturitas").list();
        listadoCriaturitas.stream().forEach((cr) -> {
            System.out.println("ID: "+cr.getId()+"\n Nombre:"+cr.getNombre() + "\n Cuento/s: " + cr.getListaCuentos());
        });
        
        //El usuario introduce el Id de la criaturita por teclado

        do{ 
            System.out.println("Elige una Criaturita introduciendo su Id: ");
            idCriaturita = sc.nextByte(); 
            if (idCriaturita<0){
                System.out.println("Error, Introduce un Id Correcto!");
            }
        }while(idCriaturita<0);
        
        //Seleccionamos la Criaturita mediante el ID introducido

        Query query = session.createQuery("FROM Criaturitas WHERE id = :id");
        query.setByte("id", idCriaturita);
        Criaturitas criaturitaSeleccionada = (Criaturitas)query.uniqueResult();
        
        //Listamos los cuentos de la Criaturita seleccionada
        
        if (!criaturitaSeleccionada.getListaCuentos().isEmpty()){
            
            System.out.println(criaturitaSeleccionada.toString());
            
            System.out.println("Cuento/s: ");
            criaturitaSeleccionada.getListaCuentos().stream().forEach((cuento) -> {
                System.out.println("Id: " + cuento.getId() + " Titulo: " + cuento.getTitulo()); 
            });
            
            //El usuario introduce el Id del cuento por teclado

            do{
                System.out.println("Selecciona el Cuento que le quieres quitar introduciendo su Id: ");
                idCuento = sc.nextInt();
                if (idCuento<0){
                    System.out.println("Error, Introduce un Id Correcto!");
                }
            }while(idCuento<0);

            //Quitamos el Cuento a la Criaturita :(

            for(int i=0; i<criaturitaSeleccionada.getListaCuentos().size();i++)
            {
                Cuento cuento = criaturitaSeleccionada.getListaCuentos().get(i);
                if(cuento.getId()==idCuento) {
                    criaturitaSeleccionada.getListaCuentos().remove(i);
                    break;
                }
            }

            //Quitamos el lector del Cuento

            query = session.createQuery("FROM Cuento WHERE id = :id");
            query.setInteger("id", idCuento);
            Cuento cuento = (Cuento)query.uniqueResult();
            for (int i=0; i<cuento.getListaLectores().size();i++){
                if(cuento.getId() == idCuento) {
                    cuento.getListaLectores().remove(i);
                }
            }

            System.out.println("\n /////////////////// OPERACION REALIZADA CORRECTAMENTE /////////////////// \n");
        }else
            System.out.println("La Criaturita no tiene Cuentos :( \n");
        
        transaction.commit();
        
    }
    
    //Asignar un cuento a una criaturita
    
    public static void asignarCuentoAUnaCriaturita(){
        Transaction transaction = session.beginTransaction();
        Scanner sc = new Scanner(System.in);
        List<Criaturitas> listadoCriaturitas;
        List<Cuento> listadoCuentosSinCriaturitaLectora;
        
        int idCuento = 0;
        byte idCriaturita = 0;
        
        //Listamos las criaturitas para que el usuario las vea
        
        listadoCriaturitas = (List<Criaturitas>)session.createQuery("FROM Criaturitas").list();
        listadoCriaturitas.stream().forEach((cr) -> {
            System.out.println("ID: "+cr.getId()+"\n Nombre:"+cr.getNombre() + "\n Cuento/s: " + cr.getListaCuentos());
        });
        
        //El usuario introduce el Id de la criaturita por teclado

        do{ 
            System.out.println("Elige una Criaturita introduciendo su Id: ");
            idCriaturita = sc.nextByte(); 
            if (idCriaturita<0){
                System.out.println("Error, Introduce un Id Correcto!");
            }
        }while(idCriaturita<0);
        
        //Seleccionamos la Criaturita mediante el ID introducido

        Query query = session.createQuery("FROM Criaturitas WHERE id = :id");
        query.setByte("id", idCriaturita);
        Criaturitas criaturitaSeleccionada = (Criaturitas)query.uniqueResult();
        
        //Listamos los Cuentos sin la criaturita como lector
        
        listadoCuentosSinCriaturitaLectora = (List<Cuento>)session.createQuery("FROM Cuento").list();
        
        for(Cuento cuento:listadoCuentosSinCriaturitaLectora){
            for (int i=0; i<cuento.getListaLectores().size();i++){
                if(cuento.getListaLectores().get(i).getId() != idCriaturita) {
                    System.out.println(cuento.toString() + "\n");
                }
            }     
        }

        //El usuario introduce el Id del cuento por teclado
        
        do{
            System.out.println("Selecciona el Cuento que le quieres asignar introduciendo su Id: ");
            idCuento = sc.nextInt();
            if (idCuento<0){
                System.out.println("Error, Introduce un Id Correcto!");
            }
        }while(idCuento<0);
        
        //Asignamos el Regalito a la Criaturita Seleccionada :)
        
        query = session.createQuery("FROM Cuento WHERE id = :id");
        query.setInteger("id", idCuento);
        
        Cuento cuento = (Cuento)query.uniqueResult();

        criaturitaSeleccionada.getListaCuentos().add(cuento);
        cuento.getListaLectores().add(criaturitaSeleccionada);

        transaction.commit();
        
        System.out.println("\n /////////////////// OPERACION REALIZADA CORRECTAMENTE /////////////////// \n");
        
    }
    
    //Crear un Cuento
    
    public static void crearCuento(){
        Transaction transaction = session.beginTransaction();
        Scanner sc = new Scanner(System.in);
        String titulo, autor, tema;
        int idCuento;
        
        do{
            System.out.println("Introduce el titulo de tu nuevo Cuento: ");
            titulo = sc.nextLine();
            if (titulo.equals(""))
                System.out.println("Error, el nombre no puede estar vacio!");
        }while(titulo.equals(""));
        
        do{
            System.out.println("Introduce el autor de tu nuevo Cuento: ");
            autor = sc.nextLine();
            if (autor.equals(""))
                System.out.println("Error, el nombre no puede estar vacio!");
        }while(autor.equals(""));
        
        do{
            System.out.println("Introduce el tema de tu nuevo Cuento: ");
            tema = sc.nextLine();
            if (tema.equals(""))
                System.out.println("Error, el nombre no puede estar vacio!");
        }while(tema.equals(""));
        
        //El id de la Criaturita es el siguiente al mas alto
        
        idCuento = (int) session.createQuery("SELECT MAX(id) FROM Cuento").uniqueResult();
        
        idCuento++;
        
        Cuento cuento = new Cuento(idCuento, titulo, autor, tema); 
        session.save(cuento);
        transaction.commit(); 
        
        System.out.println("\n /////////////////// OPERACION REALIZADA CORRECTAMENTE /////////////////// \n");
    }
    
    //Borrar un cuento
    
    public static void borrarCuento(){
        Transaction transaction = session.beginTransaction();
        Scanner sc = new Scanner(System.in);
        List<Cuento> listadoCuentos;

        int idCuento;
        
        //Listamos los Cuentos para que el usuario las vea
        
        listadoCuentos = (List<Cuento>)session.createQuery("FROM Cuento").list();
        listadoCuentos.stream().forEach((cuento) -> {
            System.out.println("ID: "+cuento.getId()+"\n Titulo: "+cuento.getTitulo());
        });
        
        //El usuario introduce el Id del cuento por teclado

        do{ 
            System.out.println("Elige el Cuento a borrar introduciendo su Id: ");
            idCuento = sc.nextByte(); 
            if (idCuento<0){
                System.out.println("Error, Introduce un Id Correcto!");
            }
        }while(idCuento<0);
        
        //Listamos todos los Criaturitas lectoras del cuento y las eliminamos
        
        Query query = session.createQuery("FROM Criaturitas");
        List<Criaturitas> listadoCriaturitas = (List<Criaturitas>)query.list();
        
        for (Criaturitas criaturita : listadoCriaturitas){
            for (int i=0; i<criaturita.getListaCuentos().size();i++){
                if(criaturita.getListaCuentos().get(i).getId() == idCuento) {
                    criaturita.getListaCuentos().remove(i);
                }
            }
        }
        
        //Borramos la Criaturita (T.T)
      
        Cuento cuento = (Cuento)session.get(Cuento.class,idCuento);
        session.delete(cuento);
        
        transaction.commit(); 
        
        System.out.println("\n /////////////////// OPERACION REALIZADA CORRECTAMENTE /////////////////// \n");
    }
}
