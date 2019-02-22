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
            System.out.println("\n Id: " + regalo.getId()+"\n Denominacion: "+regalo.getDenominacion()+"\n Ancho: "+regalo.getAncho()+"\n Largo: "+regalo.getLargo()+"\n Alto: "+regalo.getAlto()+"\n Tipo: "+regalo.getTipo()+"\n Edad mínima: "+regalo.getEdadMinima()+"\n Precio: "+regalo.getPrecio() + "\n");
    }
    
    //Crear un nuevo regalo
    
    public void crearUnNuevoRegalo(){
        
    }
    
    //Borrar un regalo
    
    public void borrarUnRegalo(){
        
    }
    
}
