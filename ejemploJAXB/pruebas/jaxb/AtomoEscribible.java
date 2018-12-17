package pruebas.jaxb;

import beans.*;

/**
 *
 * @author Leo
 */
// Derivamos la clase Atomo para añadirle un método que devuelva una cadena con todos los datos del Atomo
public class AtomoEscribible extends Atomo {
    
    public AtomoEscribible (Atomo uno){
        super();
        this.setEstado(uno.getEstado());
        this.setDensidad(uno.getDensidad());
        this.setNombre(uno.getNombre());
        this.setNumeroAtomico(uno.getNumeroAtomico());
        this.setPesoAtomico(uno.getPesoAtomico());
        this.setPuntoEbullicion(uno.getPuntoEbullicion());
        this.setSimbolo(uno.getSimbolo());
    }
    // Este es el método
    public String getTodo (){
        String cadAtomo;
        cadAtomo = "Nombre: "+this.getNombre();
        cadAtomo +="\nSímbolo: "+this.getSimbolo();
        cadAtomo +="\nNúmero Atómico: "+this.getNumeroAtomico();
        cadAtomo +="\nPeso atómico: "+this.getPesoAtomico();
        cadAtomo +="\nEstado: "+this.getEstado();
        cadAtomo +="\nDensidad: "+this.getDensidad().getValue();
        cadAtomo +="\nPunto de Ebullición: "+this.getPuntoEbullicion().getUnidad();
        return cadAtomo;
    }
}
