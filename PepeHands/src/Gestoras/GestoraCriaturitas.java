/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestoras;

import Clases.Criaturitas;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 *
 * @author sgarcia
 */

public class GestoraCriaturitas {
    
    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private static final Session session = sessionFactory.openSession();
    
    //Listar todas las criaturitas
    
    public static void listarTodasLasCriaturitas()
    {
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
        
    }
    
    //Asignar un regalo a una criaturita (o ninguna)
    
    public void asignarRegaloAUnaCriaturita(){
        
    }
    
    //Crear una nueva criaturita
    
    public void crearNuevaCriaturita(){
        
    }
    
    //Borrar una criaturita y todos sus regalos
    
    public void borrarUnaCriaturitaYTodosSusRegalos(){
        
    }
}
