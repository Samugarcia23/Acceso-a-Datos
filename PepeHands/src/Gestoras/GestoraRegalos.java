/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestoras;

import Clases.Regalos;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

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
            System.out.println("Id: " + regalo.getId()+" Denominacion: "+regalo.getDenominacion()+" Ancho: "+regalo.getAncho()+" Largo: "+regalo.getLargo()+" Alto: "+regalo.getAlto()+" Tipo: "+regalo.getTipo()+" Edad mínima: "+regalo.getEdadMinima()+" Precio: "+regalo.getPrecio() + "\n");
    }
    
    //Crear un nuevo regalo
    
    public void crearUnNuevoRegalo(){
        
    }
    
    //Borrar un regalo
    
    public void borrarUnRegalo(){
        
    }
    
}
