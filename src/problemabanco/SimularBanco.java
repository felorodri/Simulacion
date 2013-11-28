package problemabanco;

import java.util.Stack;
import java.util.Vector;

public class SimularBanco {

    boolean[] CajeroOcupado;
    Vector line = new Vector();
    Vector buffer = new Vector();
    double Tiempollegada, TiempoAtencion;
    Cliente client;
    Cliente siguiente;
    int Cola;
    int ClientePerdido;
    int ClienteAtendido;
    int NumCajeroLibre;
    int reloj = 0;
    

    public void inicializarSimulacion(int CantidadCajeros) {
        CajeroOcupado = new boolean[CantidadCajeros];
        for (int i = 0; i < CajeroOcupado.length; i++) {
            CajeroOcupado[i] = false;
        }
    }

    public void atenderCliente(int reloj) {
        generarLlegada();
        siguiente = (Cliente) line.elementAt(0);
        line.removeElementAt(0);
        NumCajeroLibre=cajerolibre();
        siguiente.setCajeroAtiende(NumCajeroLibre);
        siguiente.setTiempoSalida(reloj+siguiente.getTiempoAtencion());
        ClienteAtendido++;
        


    }

    public void eventoSalida() {
    }

    public void generarLlegada() {

        Tiempollegada = (-1) * Math.log(Math.random());
        TiempoAtencion = (10 - 2) * Math.random() + 2;
        client = new Cliente(Tiempollegada, TiempoAtencion);

        if (line.size() > 7) {
            ClientePerdido++;
        } else {
            if(line.isEmpty()){
                line.add(client);
            }else{
            for (int i = 0; i <= line.size(); i++) {

                Cliente nClient = (Cliente) line.elementAt(0);

                if (nClient.getTiempoLLegada() <= client.getTiempoLLegada()) {

                    buffer.add(nClient);
                    line.removeElementAt(0);

                } else {

                    buffer.add(client);
                    for (int k = 0; k <= line.size(); k++) {
                        buffer.add((Cliente) line.elementAt(k));
                    }
                    line.removeAllElements();
                    line = buffer;
                    buffer.removeAllElements();
                    break;
                }

            }
            }
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
