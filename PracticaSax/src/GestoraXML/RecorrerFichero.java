package GestoraXML;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RecorrerFichero {

    XMLReader lectorXML;
    InputSource archivoXML;
    GestorContenido gestor;

    public RecorrerFichero (String nombreArchivo){

        try {
            lectorXML = XMLReaderFactory.createXMLReader();
        } catch (org.xml.sax.SAXException e) {
            Logger.getLogger(RecorrerFichero.class.getName()).log(Level.SEVERE, null, e);
        }

        gestor = new GestorContenido();
        lectorXML.setContentHandler(gestor);
        archivoXML = new InputSource(nombreArchivo);
    }

    public void recorrer(){
        try {
            lectorXML.parse(archivoXML);
        } catch (IOException ex) {
            Logger.getLogger(RecorrerFichero.class.getName()).log(Level.SEVERE, null, ex);
        }catch (org.xml.sax.SAXException e) {
            Logger.getLogger(RecorrerFichero.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
