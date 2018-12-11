package GestoraXML;

import Clases.Disco;
import GestoraBD.GestoraDiscos;

import java.sql.SQLException;

public class MainPracticaSax {

    public static void main(String[] args) throws SQLException {

        String autor, titulo, formato, localizacion;
        GestoraDiscos gestoraDiscos = new GestoraDiscos();
        GestorContenido gestorContenido = new GestorContenido();
        Disco disco = new Disco();
        
        String nombreArchivo = "src\\discos.xml";
        RecorrerFichero recorrerFichero = new RecorrerFichero(nombreArchivo);
        recorrerFichero.recorrer();
        
    }

}
