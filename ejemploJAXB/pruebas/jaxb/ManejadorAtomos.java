
package pruebas.jaxb;


import java.io.*;
import javax.xml.bind.*;
import beans.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leo
 */
public class ManejadorAtomos {
    Atomos listaAtomos;
    public void abrirListaAtomosJAXB (File archivoXML){
        JAXBContext contexto;
        try {
            contexto = JAXBContext.newInstance(Atomos.class);
            Unmarshaller u = contexto.createUnmarshaller();
            listaAtomos = (Atomos) u.unmarshal(archivoXML);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void recorreListaAtomos(){
        AtomoEscribible atomoTuneao;
        List<Atomo> miListaAtomos = listaAtomos.getAtomo();
        for(Atomo unAtomo:miListaAtomos){
            atomoTuneao = new AtomoEscribible(unAtomo);
            System.out.println("\nSiguiente elemento\n----------------------------------------------");
            System.out.println(atomoTuneao.getTodo());
        }
    }
    public void anadirAtomo(Atomo nuevo){
        listaAtomos.getAtomo().add(nuevo);
    }
    public void guardarListaAtomos(File archivoXML){
        JAXBContext contexto;
        try {
            contexto = JAXBContext.newInstance(Atomos.class);
            Marshaller marshalero = contexto.createMarshaller();
            marshalero.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter escribiente = new StringWriter();
            marshalero.marshal(listaAtomos, archivoXML);
            // ahora lo marshaleamos a un stream para visualizarlo
            marshalero.marshal(listaAtomos, escribiente);
            System.out.println("-----------------");
            System.out.println("Object2XML:");
            System.out.println(escribiente.toString());
            System.out.println("-----------------");
        } catch (JAXBException ex) {
            Logger.getLogger(ManejadorAtomos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
