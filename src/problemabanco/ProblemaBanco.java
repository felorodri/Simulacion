/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problemabanco;

import java.util.Stack;

/**
 *
 * @author maraes
 */
public class ProblemaBanco {

    /**
     * @param args the command line arguments
     */
    
    
    
    public static void main(String[] args) {
       //int reloj=0;
       //int limite=120;
       SimularBanco abrirBanco=new SimularBanco();
       abrirBanco.inicializarSimulacion(1);
       abrirBanco.generarLlegadas();
       abrirBanco.simulaAtencion();
       //abrirBanco.generarLlegada();
       //while(reloj<=limite){
         //System.out.println("Tiempo reloj: "+reloj+"\n");
         //abrirBanco.atenderCliente(reloj);
         //reloj++;
         
       //}
    }
    
    
}
