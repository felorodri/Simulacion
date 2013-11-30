package problemabanco;

import java.util.Stack;
import java.util.Vector;

public class SimularBanco {

    Cajero[] Cajeros;
    Cliente[] Cajas;
    Vector fila = new Vector();
    Vector buffer = new Vector();
    Vector ordenLLegada = new Vector();
    Vector atendidos = new Vector();
    double Tiempollegada, TiempoAtencion;
    Cliente client;
    Cliente siguiente;
    int Cola;
    int ClientePerdido;
    int ClienteAtendido;
    int ClientesPerdidos;
    int NumCajeroLibre;
    int reloj = 0;
    int limite = 120;

    public void inicializarSimulacion(int CantidadCajeros) {
        Cajeros =new Cajero[CantidadCajeros];
        Cajas   =new Cliente[CantidadCajeros];
        for (int k=0; k<Cajeros.length; k++){
            Cajeros[k]= new Cajero();
        }
    }

    public void simulaAtencion(){
        
    while(reloj<=limite){
                    
        for(int w=0; w<Cajeros.length;w++){
            
            if(Cajeros[w].getOcupacion()<reloj && Cajeros[w].getOcupacion()!=0){
                Cajeros[w].setOcupado(false);
                Cajas[w].setTiempoSalida(reloj);
                atendidos.add(Cajas[w]);
            }
            
        }
                       
        while(!fila.isEmpty()&& cLibre()){
            
            int libre=cajerolibre();
            Cliente siguiente = (Cliente)fila.elementAt(0);
            siguiente.setTiempoInicioAtencion(reloj);
            Cajas[libre]=siguiente;
            fila.removeElementAt(0);
            
            Cajeros[libre].setOcupado(true);
            Cajeros[libre].setOcupacion(reloj+siguiente.getTiempoAtencion());
            Cajeros[libre].setTiempoTotalOcupado(siguiente.getTiempoAtencion());
            Cajeros[libre].setClientesAtendidos(Cajeros[libre].getClientesAtendidos()+1);                                    
        }
        
        
        for(int f=0;f<ordenLLegada.size();f++){
            
            Cliente cli = (Cliente)ordenLLegada.elementAt(0);
            
            if(cli.getTiempoLLegada()<reloj+1){
                
                if(fila.size()<7){
                    fila.add(cli);
                    ordenLLegada.removeElementAt(0);
                    
                    if(cLibre()==true && fila.size()==1){
                        
                        cli = (Cliente)fila.elementAt(0);
                        fila.removeElementAt(0);
                        int libre=cajerolibre();
                        cli.setTiempoInicioAtencion(reloj);
                        Cajas[libre]=cli;
                        Cajeros[libre].setOcupado(true);
                        Cajeros[libre].setOcupacion(reloj+cli.getTiempoAtencion());
                        Cajeros[libre].setTiempoTotalOcupado(cli.getTiempoAtencion());
                        Cajeros[libre].setClientesAtendidos(Cajeros[libre].getClientesAtendidos()+1);
                           
                    }
                    
                }else{
                    ordenLLegada.removeElementAt(0);
                    ClientesPerdidos++;
                }
                           
            }else{
                break; 
            }
    
        }
       
        reloj++;
      }
    }
  

    public void generarLlegadas() {

        while (reloj <= limite) {

            
            Tiempollegada = ((-1) * Math.log(Math.random())) + reloj;
            
            TiempoAtencion = (10 - 2) * Math.random() + 2;
            client = new Cliente(Tiempollegada, TiempoAtencion);

            if (ordenLLegada.isEmpty() && reloj == 0) {

                ordenLLegada.add(client);

            } else {

                for (int i = 0; i < ordenLLegada.size(); i++) {

                    Cliente nClient = (Cliente) ordenLLegada.elementAt(i);


                    if (nClient.getTiempoLLegada() <= client.getTiempoLLegada()) {
                        buffer.add(nClient);
                        if ((ordenLLegada.size() == 1) || (i + 1) == ordenLLegada.size()) {
                            buffer.add(client);
                            ordenLLegada.removeAllElements();
                            ordenLLegada = (Vector) buffer.clone();
                            buffer.removeAllElements();
                            
                            break;
                        }
                    } else {
                        buffer.add(client);
                        for (int k = i; k < ordenLLegada.size(); k++) {
                            Cliente clt = (Cliente) ordenLLegada.elementAt(k);
                            buffer.add(clt);
                        }
                        ordenLLegada.removeAllElements();
                        ordenLLegada = (Vector) buffer.clone();
                        buffer.removeAllElements();
                        break;
                    }
                }
            }
            reloj++;
            
        }
        reloj = 0;
    }

   
    private int cajerolibre() {
        int libre = 0;
        for (int i = 0; i < Cajeros.length; i++) {
            if (Cajeros[i].isOcupado() == false) {
                Cajeros[i].setOcupado(true);
                libre = i;
                break;
            }
        }
        return libre;
    }
    
    private boolean cLibre() {
        
        boolean libre = false;
        for (int i = 0; i < Cajeros.length; i++) {
            if (Cajeros[i].isOcupado() == false) {
                libre = true;
                break;
            }
        }
        return libre;
    }
}
