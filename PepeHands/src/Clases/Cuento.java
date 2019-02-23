/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author sgarcia
 */
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

@Entity
@Table(name="Cuentos")
public class Cuento implements Serializable{
    @Id
    @Column(name="Id")
    private int id;
    @Column(name="Titulo")
    private String titulo;
    @Column(name="Autor")
    private String autor;
    @Column(name="Tema")
    private String tema;
    
//    @ManyToMany(targetEntity = Criaturita.class,cascade = {CascadeType.ALL},mappedBy="listaCuentos")
    @ManyToMany(mappedBy = "listaCuentos")
    private List<Criaturitas> listaLectores = new ArrayList();

    public Cuento() {
    }
    
    public Cuento(int id, String titulo, String autor, String tema) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.tema = tema;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getTema() {
        return tema;
    }

    public List<Criaturitas> getListaLectores() {
        return listaLectores;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public void setListaLectores(List<Criaturitas> listaLectores) {
        this.listaLectores = listaLectores;
    }

    @Override
    public String toString() {
        return "Cuento{" + "id=" + id + ", titulo=" + titulo + ", autor=" + autor + ", tema=" + tema + ", listaLectores=" + listaLectores + '}';
    }
}

