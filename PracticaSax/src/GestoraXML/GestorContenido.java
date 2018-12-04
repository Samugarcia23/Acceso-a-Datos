package GestoraXML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GestorContenido extends DefaultHandler {

    public GestorContenido(){ super(); }
    @Override
    public void startDocument(){
        System.out.println("Comienzo del documento XML");
    }
    @Override
    public void endDocument(){
        System.out.println("Fin del documento XML");
    }
    @Override
    public void startElement(String uri, String nombre, String nombreC, Attributes att){

        System.out.println("\t< "+nombre +">");
        
        int length = att.getLength();
        for (int i=0; i<length; i++) {
            String name = att.getQName(i);
            String value = att.getValue(i);
        }
    }
    @Override
    public void endElement(String uri, String nombre, String nombreC){

        System.out.println("\t</ "+nombre +">");

    }
    @Override
    public void characters (char[] ch, int inicio, int longitud)
            throws SAXException {
        String cad = new String(ch, inicio, longitud);
        cad = cad.replaceAll("[\t\n]",""); // Quitamos tabuladores y saltos de linea
        System.out.println("\t\t" + cad);
    }
}
