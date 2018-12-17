
package mypackage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para PersonaType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="PersonaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="nombre" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sexo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sexoBuscado" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fechaNacimiento" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="ingresos" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="Preferencias" type="{}PreferenciasType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PersonaType", propOrder = {
    "id",
    "nombre",
    "sexo",
    "sexoBuscado",
    "fechaNacimiento",
    "ingresos",
    "preferencias"
})
public class PersonaType {

    @XmlElement(name = "ID")
    protected short id;
    @XmlElement(required = true)
    protected String nombre;
    @XmlElement(required = true)
    protected String sexo;
    @XmlElement(required = true)
    protected String sexoBuscado;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fechaNacimiento;
    protected float ingresos;
    @XmlElement(name = "Preferencias", required = true)
    protected PreferenciasType preferencias;

    /**
     * Obtiene el valor de la propiedad id.
     * 
     */
    public short getID() {
        return id;
    }

    /**
     * Define el valor de la propiedad id.
     * 
     */
    public void setID(short value) {
        this.id = value;
    }

    /**
     * Obtiene el valor de la propiedad nombre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el valor de la propiedad nombre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombre(String value) {
        this.nombre = value;
    }

    /**
     * Obtiene el valor de la propiedad sexo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * Define el valor de la propiedad sexo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSexo(String value) {
        this.sexo = value;
    }

    /**
     * Obtiene el valor de la propiedad sexoBuscado.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSexoBuscado() {
        return sexoBuscado;
    }

    /**
     * Define el valor de la propiedad sexoBuscado.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSexoBuscado(String value) {
        this.sexoBuscado = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaNacimiento.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Define el valor de la propiedad fechaNacimiento.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFechaNacimiento(XMLGregorianCalendar value) {
        this.fechaNacimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad ingresos.
     * 
     */
    public float getIngresos() {
        return ingresos;
    }

    /**
     * Define el valor de la propiedad ingresos.
     * 
     */
    public void setIngresos(float value) {
        this.ingresos = value;
    }

    /**
     * Obtiene el valor de la propiedad preferencias.
     * 
     * @return
     *     possible object is
     *     {@link PreferenciasType }
     *     
     */
    public PreferenciasType getPreferencias() {
        return preferencias;
    }

    /**
     * Define el valor de la propiedad preferencias.
     * 
     * @param value
     *     allowed object is
     *     {@link PreferenciasType }
     *     
     */
    public void setPreferencias(PreferenciasType value) {
        this.preferencias = value;
    }

}
