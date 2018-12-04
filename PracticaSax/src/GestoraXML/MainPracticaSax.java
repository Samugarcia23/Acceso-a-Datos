package GestoraXML;

import GestoraBD.GestoraDiscos;

import java.sql.SQLException;

public class MainPracticaSax {

    public static void main(String[] args) throws SQLException {

        GestoraDiscos gestoraDiscos = new GestoraDiscos();
        
        String nombreArchivo = "src\\discos.xml";
        RecorrerFichero recorrerFichero = new RecorrerFichero(nombreArchivo);
        recorrerFichero.recorrer();

        gestoraDiscos.agregarDiscos();
    }

}
