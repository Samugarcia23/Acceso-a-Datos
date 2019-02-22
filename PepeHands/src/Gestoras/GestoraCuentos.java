/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestoras;

import Clases.Criaturitas;
import Clases.Cuento;
import Clases.Regalos;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

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
        String hqlQuery = "FROM Criaturitas WHERE Nombre = 'Adela'";
        Query query = session.createQuery(hqlQuery);
        ArrayList<Criaturitas> listado = new ArrayList<>(query.list());
        for(Criaturitas criaturita : listado)
            System.out.println("\n Id: " + criaturita.getId() + "\n Nombre: " + criaturita.getNombre() + "\n Cuentos: \n" + criaturita.getListaCuentos()+ "\n");
    }
    
    //RecuperarC un Cuento y su lista de lectores
    
    public static void recuperarCuentosYListaDeLectores(){
        String hqlQuery = "FROM Cuento Where Id = 1";
        Query query = session.createQuery(hqlQuery);
        ArrayList<Cuento> listado = new ArrayList<>(query.list());
        for(Cuento cuento : listado)
            System.out.println("\n Id: " + cuento.getId()+"\n Titulo: "+cuento.getTitulo()+"\n Autor: "+cuento.getAutor()+"\n Tema: "+cuento.getTema()+"\n Lectores: \n"+cuento.getListaLectores() + "\n");
    }
    
    //Quitar un cuento a una criaturita
    
    public static void quitarCuentoAUnaCriaturita(){
        
    }
    
    //Asignar un cuento a una criaturita
    
    public static void asignarCuentoAUnaCriaturita(){
        
    }
    
    //Crear un Cuento
    
    public static void crearCuento(){
        
    }
    
    //Borrar un cuento
    
    public static void borrarCuento(){
        
    }
}
