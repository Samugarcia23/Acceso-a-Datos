package Main;

import Gestoras.GestoraCriaturitas;
import Gestoras.GestoraCuentos;
import Gestoras.GestoraMenu;
import Gestoras.GestoraRegalos;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sgarcia
 */
public class MainMenu {
    
    public static void main(String[] args){
        
        int opcion;
        Scanner sc = new Scanner(System.in);
        
        do{
            
            do{
                GestoraMenu.mostrarMenu();
                opcion = sc.nextInt();
                if (opcion < 0 && opcion >9){
                    System.out.println("¡Solo entre 1 o 9, o 0 para salir!");
                }
            }while(opcion < 0 && opcion >9); 
            
            switch(opcion){

                case 1:
                    GestoraCriaturitas.listarTodasLasCriaturitas();
                    break;

                case 2: 
                    GestoraRegalos.listarTodosLosRegalos();
                    break;

                case 3:
                    GestoraCriaturitas.recuperarCriaturitaConTodosSusRegalos();
                    break;

                case 4:
                    System.out.println("EN PROCESO");
                    break;

                case 5:
                    System.out.println("EN PROCESO");
                    break;

                case 6:
                    System.out.println("EN PROCESO");
                    break;

                case 7:
                    System.out.println("EN PROCESO");
                    break;

                case 8:
                    System.out.println("EN PROCESO");
                    break;

                case 9:
                    System.out.println("EN PROCESO");
                    break;
                    
                case 10:
                    GestoraCuentos.listarTodosLosCuentos();
                    break;

                case 11:
                    GestoraCuentos.recuperarCriaturitaConTodosSusCuentos();
                    break;

                case 12:
                    GestoraCuentos.recuperarCuentosYListaDeLectores();
                    break;

                case 13:
                    System.out.println("EN PROCESO");
                    break;

                case 14:
                    System.out.println("EN PROCESO");
                    break;

                case 15:
                    System.out.println("EN PROCESO");
                    break;

                case 16:
                    System.out.println("EN PROCESO");
                    break;
                    
            }

             
            
        }while(opcion != 0);
    }
    
}
