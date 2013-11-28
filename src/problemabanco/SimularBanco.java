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

public class SimularBanco {
    Stack<Object> ListadoEventos;
    boolean[] CajeroOcupado;
    double Tiempollegada,TiempoAtencion;
    Evento evento;
    int Cola;
    int ClientePerdido;
    int ClienteAtendido;
    int NumCajeroLibre;
    
    public void inicializarSimulacion(int CantidadCajeros){
        CajeroOcupado=new boolean[CantidadCajeros];
        for(int i=0;i<CajeroOcupado.length;i++){
            CajeroOcupado[i]=false;
        }
    }
    
    public  void eventollegada(){
        generarLlegada();
        if(Cola>7){
            ClientePerdido++;
        }else{
            if(haycajerolibre()==true){
                if()
            }
        }
    }
    public void eventoSalida(){
        
    }
    public void generarLlegada(){
        Tiempollegada=(-1)*Math.log(Math.random());
        evento=new Evento("llegada",Tiempollegada);
        ListadoEventos.push(evento);        
    }
    public void generarSalida(){
        TiempoAtencion=(10-2)*Math.random()+2;
        evento=new Evento("atencion",TiempoAtencion);
        ListadoEventos.push(evento);        
    }

    private boolean haycajerolibre() {
       boolean libre=false;
        for(int i=0;i<CajeroOcupado.length;i++){
           if(CajeroOcupado[i]==false){
               NumCajeroLibre=i;
               libre=true;
           }
       }
        return libre;
    }
}
