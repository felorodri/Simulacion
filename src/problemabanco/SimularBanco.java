package problemabanco;

import com.sun.org.apache.bcel.internal.generic.RETURN;
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
    int limite = 0;
    String MensajeResultados="";

    public void inicializarSimulacion(int CantidadCajeros, int limite) {
        this.limite=limite;
        Cajeros = new Cajero[CantidadCajeros];
        Cajas = new Cliente[CantidadCajeros];
        for (int k = 0; k < Cajeros.length; k++) {
            Cajeros[k] = new Cajero();
        }
    }

    public void simulaAtencion() {
        Cliente sgt;
        Cliente cli;
        Cliente stt;
        while (reloj <= limite) {

            for (int w = 0; w < Cajeros.length; w++) {

                if (Cajeros[w].getOcupacion() < reloj && Cajeros[w].getOcupacion() != 0) {
                    Cajeros[w].setOcupado(false);
                    Cajas[w].setTiempoSalida(reloj);
                    atendidos.add(Cajas[w]);
                }

            }

            while (!fila.isEmpty() && cLibre()) {

                int libre = cajerolibre();
                sgt = (Cliente) fila.elementAt(0);
                sgt.setTiempoInicioAtencion(reloj);
                Cajas[libre] = sgt;
                fila.removeElementAt(0);

                Cajeros[libre].setOcupado(true);
                Cajeros[libre].setOcupacion(reloj + sgt.getTiempoAtencion());
                Cajeros[libre].setTiempoTotalOcupado(sgt.getTiempoAtencion());
                Cajeros[libre].setClientesAtendidos(Cajeros[libre].getClientesAtendidos() + 1);
                System.out.println("cajero"+libre+" ocupa: " + Cajeros[libre].getOcupacion());
            }


            for (int f = 0; f < ordenLLegada.size(); f++) {

                cli = (Cliente) ordenLLegada.elementAt(0);

                if (cli.getTiempoLLegada() < reloj + 1) {

                    if (fila.size() < 7) {
                        fila.add(cli);
                        ordenLLegada.removeElementAt(0);

                        if (cLibre() == true && fila.size() == 1) {

                            cli = (Cliente) fila.elementAt(0);
                            fila.removeElementAt(0);
                            int libre = cajerolibre();
                            cli.setTiempoInicioAtencion(reloj);
                            Cajas[libre] = cli;
                            Cajeros[libre].setOcupado(true);
                            Cajeros[libre].setOcupacion(reloj + cli.getTiempoAtencion());
                            Cajeros[libre].setTiempoTotalOcupado(cli.getTiempoAtencion());
                            Cajeros[libre].setClientesAtendidos(Cajeros[libre].getClientesAtendidos() + 1);
                            System.out.println("cajero"+libre+" ocupa: " + Cajeros[libre].getOcupacion());
                        }

                    } else {
                        ordenLLegada.removeElementAt(0);
                        ClientesPerdidos++;
                    }

                } else {
                    break;
                }

            }

            reloj++;
        }

        while (!fila.isEmpty()) {
            for (int w = 0; w < Cajeros.length; w++) {

                if (Cajeros[w].getOcupacion() < reloj && Cajeros[w].getOcupacion() != 0) {
                    Cajeros[w].setOcupado(false);                    
                    Cajas[w].setTiempoSalida(reloj);
                    atendidos.add(Cajas[w]);
                }

            }

            while (!fila.isEmpty() && cLibre()) {

                int libre = cajerolibre();
                stt = (Cliente) fila.elementAt(0);
                stt.setTiempoInicioAtencion(reloj);
                Cajas[libre] = stt;
                fila.removeElementAt(0);

                Cajeros[libre].setOcupado(true);
                Cajeros[libre].setOcupacion(reloj + stt.getTiempoAtencion());
                Cajeros[libre].setTiempoTotalOcupado(stt.getTiempoAtencion());
                Cajeros[libre].setClientesAtendidos(Cajeros[libre].getClientesAtendidos() + 1);
            }
            reloj++;
        }

        if (fila.isEmpty() && reloj >= limite) {            
            while (todosLibres()) {
                for (int w = 0; w < Cajeros.length; w++) {

                    if (Cajeros[w].getOcupacion() < reloj && Cajeros[w].getOcupacion() != 0) {
                        Cajeros[w].setOcupado(false);
                        System.out.println("cajero"+w+" ocupa: " + Cajeros[w].getOcupacion());
                        Cajas[w].setTiempoSalida(reloj);
                        atendidos.add(Cajas[w]);
                    }
                }
                reloj++;
            }

        }
        
        Cajero tm;
        int n;
        for (int r = 0; r < Cajeros.length; r++) {
            tm = Cajeros[r];
            n=r+1;
            MensajeResultados+="El cajero "+n+" atendio " + tm.getClientesAtendidos()+" clientes \n";
            MensajeResultados+="El cajero "+n+" obtuvo una ocupacion del " + Math.round((tm.getTiempoTotalOcupado()/limite)*100)+"% \n";
            ClienteAtendido += tm.getClientesAtendidos();
        }
        MensajeResultados+="Cantidad de clientes perdidos: " + ClientesPerdidos+"\n";
        MensajeResultados+="cantidad de clientes atendidos: " + ClienteAtendido+"\n";
        MensajeResultados+="porcentaje de clientes perdidos: "+(ClientesPerdidos/(ClientesPerdidos+ClienteAtendido))*100+"%";
        
    }

    public void generarLlegadas() {
        Cliente clt;
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
                            clt = (Cliente) ordenLLegada.elementAt(k);
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
        Cliente t;
        for(int w=0;w<ordenLLegada.size();w++){
            t=(Cliente)ordenLLegada.elementAt(w);
            System.out.println("tiempos de llegada: "+t.getTiempoLLegada());
            System.out.println("tiempos de atencion: "+t.getTiempoAtencion());
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

    private boolean todosLibres() {
        int Cdesocupados=0;
        boolean tmp=true;
        for(int z=0;z<Cajeros.length;z++){
                        if(Cajeros[z].isOcupado()==false){
                            Cdesocupados++;
                        }
                    }
                    if(Cdesocupados==Cajeros.length){
                        tmp=false;
                    }
                
    
        return tmp;
    }

    public String getMensajeResultados() {
        return MensajeResultados;
    }
    
}
