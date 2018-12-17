
package mypackage;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mypackage package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Corazoncitos_QNAME = new QName("", "Corazoncitos");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mypackage
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CorazoncitosType }
     * 
     */
    public CorazoncitosType createCorazoncitosType() {
        return new CorazoncitosType();
    }

    /**
     * Create an instance of {@link GustoType }
     * 
     */
    public GustoType createGustoType() {
        return new GustoType();
    }

    /**
     * Create an instance of {@link PreferenciasType }
     * 
     */
    public PreferenciasType createPreferenciasType() {
        return new PreferenciasType();
    }

    /**
     * Create an instance of {@link PersonaType }
     * 
     */
    public PersonaType createPersonaType() {
        return new PersonaType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CorazoncitosType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Corazoncitos")
    public JAXBElement<CorazoncitosType> createCorazoncitos(CorazoncitosType value) {
        return new JAXBElement<CorazoncitosType>(_Corazoncitos_QNAME, CorazoncitosType.class, null, value);
    }

}
