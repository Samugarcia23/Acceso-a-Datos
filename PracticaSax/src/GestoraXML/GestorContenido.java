package GestoraXML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class GestorContenido extends DefaultHandler {

    public boolean esAutor, esTitulo, esFormato, esLocalizacion;
    public String[] autor, titulo, formato, localizacion;

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

        if (nombre.equals("autor")){
            esAutor = false;
        }
        if (nombre.equals("titulo")){
            esTitulo = false;
        }
        if (nombre.equals("formato")){
            esFormato = false;
        }
        if (nombre.equals("localizacion")){
            esLocalizacion = false;
        }

    }
    @Override
    public void characters (char[] ch, int inicio, int longitud)
            throws SAXException {

        if (esAutor){
            autor[0] = new String(ch, inicio, longitud);
        }
        if (esTitulo){
            titulo.add(new String(ch, inicio, longitud));
        }
        if (esFormato){
            formato.add(new String(ch, inicio, longitud));
        }
        if (esLocalizacion){
            localizacion.add(new String(ch, inicio, longitud));
        }
    }

    @Override
    public void endDocument(){System.out.println("Fin del documento XML");}
}
