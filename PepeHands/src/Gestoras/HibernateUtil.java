/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestoras;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author sgarcia
 */
public class HibernateUtil {
 
    public static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {

            System.err.println("Initial SessionFactory creation failed." + ex);
            // Log the exception. 
            System.err.println("No se ha podido crear el SessionFactory inicial. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
}
