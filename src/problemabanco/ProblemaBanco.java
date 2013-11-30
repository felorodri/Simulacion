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
       
       SimularBanco abrirBanco=new SimularBanco();
       abrirBanco.inicializarSimulacion(4);
       abrirBanco.generarLlegadas();
       abrirBanco.simulaAtencion();
       
    }
    
    
}
