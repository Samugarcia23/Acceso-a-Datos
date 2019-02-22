package Clases;
// Generated 01-feb-2019 14:10:32 by Hibernate Tools 4.3.1


import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="Criaturitas"
)
public class Criaturitas  implements java.io.Serializable {

    @Id 

    @Column(name="Id", nullable=false)	
     private byte id;
     
    @Column(name="Nombre")     
     private String nombre;

    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="GoesTo")
    private List<Regalos> regalitos = new ArrayList<>();
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
     private List<Cuento> listaCuentos=new ArrayList();

    public Criaturitas() {
    }

    public Criaturitas(byte id) {
        this.id = id;
    }
    public Criaturitas(byte id, String nombre) {
       this.id = id;
       this.nombre = nombre;
    }
   
    public byte getId() {
        return this.id;
    }
    
    public void setId(byte id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Regalos> getRegalitos() {
        return regalitos;
    }

    public void setRegalitos(List<Regalos> regalitos) {
        this.regalitos = regalitos;
    }
    public List<Cuento> getListaCuentos() {
        return listaCuentos;
    }

    public void setListaCuentos(List<Cuento> listaCuentos) {
        this.listaCuentos = listaCuentos;
    }
    @Override
    public String toString() {
        return "Criaturita{" + "id=" + id + ", nombre=" + nombre + '}';
    }
}


