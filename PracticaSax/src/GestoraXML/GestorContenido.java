package GestoraXML;

import Clases.Disco;
import GestoraBD.GestoraDiscos;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.SQLException;
import java.util.ArrayList;

public class GestorContenido extends DefaultHandler {

    public boolean esAutor, esTitulo, esFormato, esLocalizacion;
    GestoraDiscos gestoraDiscos;
    Disco disco = new Disco();

    {
        try {
            gestoraDiscos = new GestoraDiscos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public GestorContenido(){ super(); }
    @Override
    public void startDocument(){System.out.println("Comienzo del documento XML");}
    @Override
    public void startElement(String uri, String nombre, String nombreC, Attributes att){

        if (nombre.equals("autor")){
            esAutor = true;
        }
        if (nombre.equals("titulo")){
            esTitulo = true;
        }
        if (nombre.equals("formato")){
            esFormato = true;
        }
        if (nombre.equals("localizacion")){
            esLocalizacion = true;
        }

    }
    @Override
    public void endElement(String uri, String nombre, String nombreC){

        if (nombre.equals("album")){
            try {
                gestoraDiscos.agregarDiscos(disco.getAutor(), disco.getTitulo(), disco.getFormato(), disco.getLocalizacion());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public void characters (char[] ch, int inicio, int longitud)
            throws SAXException {
        if (esAutor){
            disco.setAutor(new String(ch, inicio, longitud));
        }
        if (esTitulo){
            disco.setTitulo(new String(ch, inicio, longitud));
        }
        if (esFormato){
            disco.setFormato(new String(ch, inicio, longitud));
        }
        if (esLocalizacion){
            disco.setLocalizacion(new String(ch, inicio, longitud));
        }
    }

    @Override
    public void endDocument(){System.out.println("Fin del documento XML");}
}
