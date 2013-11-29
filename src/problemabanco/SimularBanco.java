package problemabanco;

import java.util.Stack;
import java.util.Vector;

public class SimularBanco {

    boolean[] CajeroOcupado;
    Vector line = new Vector();
    Vector buffer = new Vector();
    Vector ordenLLegada = new Vector();
    double Tiempollegada, TiempoAtencion;
    Cliente client;
    Cliente siguiente;
    int Cola;
    int ClientePerdido;
    int ClienteAtendido;
    int NumCajeroLibre;
    int reloj = 0;
    int limite = 120;
    

    public void inicializarSimulacion(int CantidadCajeros) {
        CajeroOcupado = new boolean[CantidadCajeros];
        for (int i = 0; i < CajeroOcupado.length; i++) {
            CajeroOcupado[i] = false;
        }
    }

 /*   public void atenderCliente(int reloj) {
        this.reloj = reloj;
        generarLlegadas();
        
        if(line.size()==0){
            
            System.out.println("No hay clientes por atender");
            
        }else{
            
            siguiente = (Cliente) line.elementAt(0);
            line.removeElementAt(0);
            //NumCajeroLibre=cajerolibre();
            //siguiente.setCajeroAtiende(NumCajeroLibre);
            //siguiente.setTiempoSalida(reloj+siguiente.getTiempoAtencion());
            //ClienteAtendido++;  
        }
           
    }*/

    public void eventoSalida() {
    }

    public void generarLlegadas() {
        
        while(reloj<=limite){
            
           // System.out.println("\n Tamano ordenLLegada: "+ordenLLegada.size());
            Tiempollegada = ((-1) * Math.log(Math.random()))+reloj;
            //System.out.println(Tiempollegada);
            TiempoAtencion = (10 - 2) * Math.random() + 2;
            client = new Cliente(Tiempollegada, TiempoAtencion);
            
            if(ordenLLegada.isEmpty()&&reloj==0){
                
                ordenLLegada.add(client);
                
            }else{
                if(ordenLLegada.size()>7){
                    ClientePerdido++;
                }else{
                for (int i=0; i<ordenLLegada.size(); i++){
                    
                    Cliente nClient = (Cliente) ordenLLegada.elementAt(i);
                    System.out.println("cliente en cola Tllegada= "+nClient.getTiempoLLegada()+"<= cliente nuevo Tllegada="+client.getTiempoLLegada());
                    
                    if (nClient.getTiempoLLegada() <= client.getTiempoLLegada()) {//si oredenllegada.size es 1... no añade el nuevo cliente

                        buffer.add(nClient);
                        if((ordenLLegada.size()==1)||(i+1)==ordenLLegada.size()){
                            buffer.add(client);
                            ordenLLegada.removeAllElements();
                        
                        ordenLLegada = (Vector) buffer.clone();
                        //System.out.print("buferdespues: "+buffer.size());
                        buffer.removeAllElements();                        
                        System.out.println("agrege el nuevo");
                        break;
                            
                        }
                        //ordenLLegada.removeElementAt(0);
//                        if(ordenLLegada.isEmpty()){
//                            buffer.add(client);
//                            ordenLLegada=buffer;
//                        }
                    }else{
                                     
                        buffer.add(client);
                        
                        for (int k = i; k < ordenLLegada.size(); k++) {
                            Cliente clt=(Cliente) ordenLLegada.elementAt(k);
                            buffer.add(clt);
                        }
                        
                        ordenLLegada.removeAllElements();
                       
                        ordenLLegada = (Vector) buffer.clone();
                        
                        buffer.removeAllElements();                        
                        break;
                        
                    }
                    
                    
                }
                }
            }
                  
        reloj++;
     
        

        System.out.print("BUFFER:\n");
//        for(int i=0;i<ordenLLegada.size(); i++){
//            Cliente nuevoK = (Cliente) ordenLLegada.elementAt(i);
//            System.out.println("El tamano de orden llegada es: "+ordenLLegada.size()+"\n");
//            System.out.println("Tiempo llegada cliente "+ i+" es: "+nuevoK.getTiempoLLegada()+"\n");
//        }
    }
        
    }

    /* public void generarSalida(){
        
     evento=new Evento("atencion",TiempoAtencion);
       
     }*/
    private int cajerolibre() {
        int libre = 0;
        for (int i = 0; i < CajeroOcupado.length; i++) {
            if (CajeroOcupado[i] == false) {
                CajeroOcupado[i]=true;
                libre = i;
                break;
            }
        }
        return libre;
    }
}
