package GestoraXML;

import GestoraBD.GestoraDiscos;

import java.sql.SQLException;

public class MainPracticaSax {

    public static void main(String[] args) throws SQLException {

        String autor, titulo, formato, localizacion;
        GestoraDiscos gestoraDiscos = new GestoraDiscos();
        GestorContenido gestorContenido = new GestorContenido();
        
        String nombreArchivo = "src\\discos.xml";
        RecorrerFichero recorrerFichero = new RecorrerFichero(nombreArchivo);
        recorrerFichero.recorrer();

        autor = gestorContenido.autor.get(0);
        titulo = gestorContenido.titulo.get(0);
        formato = gestorContenido.formato.get(0);
        localizacion = gestorContenido.localizacion.get(0);

        System.out.println(autor);
        System.out.println(titulo);
        System.out.println(formato);
        System.out.println(localizacion);

        //gestoraDiscos.agregarDiscos(autor, titulo, formato, localizacion);
    }

}
