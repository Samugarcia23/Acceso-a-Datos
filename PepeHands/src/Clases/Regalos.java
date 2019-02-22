package Clases;
// Generated 01-feb-2019 14:10:32 by Hibernate Tools 4.3.1


import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="Regalos")
public class Regalos implements Serializable {
    @Id 

    @Column(name="Id", nullable=false)	    
    private int id;
    
    @Column(name="Denominacion")
    private String denominacion;
    
    @Column(name="Ancho")
    private int ancho;
    
    @Column(name="Largo")
    private int largo;
    
    @Column(name="Alto")
    private int alto;
    
    @Column(name="Tipo")
    private char tipo;
    
    @Column(name="EdadMinima")
    private int edadMinima;
    
    @Column(name="Precio")
    private BigDecimal precio;
    
    @ManyToOne
    @JoinColumn(name = "GoesTo",
            foreignKey = @ForeignKey(name = "FK_RegalosCriaturitas")
    )
    private Criaturitas propietario;

    public Regalos() {
    }
    
    public int getId() {
        return id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public int getAncho() {
        return ancho;
    }

    public int getLargo() {
        return largo;
    }

    public int getAlto() {
        return alto;
    }

    public char getTipo() {
        return tipo;
    }

    public int getEdadMinima() {
        return edadMinima;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public Criaturitas getPropietario() {
        return propietario;
    }
// Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public void setLargo(int largo) {
        this.largo = largo;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public void setEdadMinima(int edadMinima) {
        this.edadMinima = edadMinima;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public void setPropietario(Criaturitas propietario) {
        this.propietario = propietario;
    }
    
    @Override
    public String toString(){
        return "Regalo: Denominacion: "+denominacion+"\nAncho: "+ancho+"\nLargo: "+alto+"\nTipo: "+tipo+"\nEdad mínima: "+edadMinima+"\nPrecio: "+precio;
    }
}