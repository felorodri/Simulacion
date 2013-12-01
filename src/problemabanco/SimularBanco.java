package problemabanco;


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

            for (int w = 0; w < Cajeros.length; w++) { // ESTE CICLO PERMITE LIBERAR CAJEROS QUE PARA EL TIEMPO DEL RELOJ DEBEN ESTAR LIBRES

                if (Cajeros[w].getOcupacion() < reloj && Cajeros[w].getOcupacion() != 0) {
                    Cajeros[w].setOcupado(false);
                    Cajas[w].setTiempoSalida(reloj);
                    atendidos.add(Cajas[w]);
                }

            }

            while (!fila.isEmpty() && cLibre()) { //ESTE CICLO PERMITE ATENDER AL PRIMERO DE LA FILA  SI LA FILA NO ESTA 
                                                   //VACIA Y HAY UN CAJERO LIBRE PARA EL TIEMPO DEL RELOJ

                int libre = cajerolibre();
                sgt = (Cliente) fila.elementAt(0);
                sgt.setTiempoInicioAtencion(reloj);
                Cajas[libre] = sgt;
                fila.removeElementAt(0);

                Cajeros[libre].setOcupado(true);
                Cajeros[libre].setOcupacion(reloj + sgt.getTiempoAtencion());
                Cajeros[libre].setTiempoTotalOcupado(sgt.getTiempoAtencion());
                Cajeros[libre].setClientesAtendidos(Cajeros[libre].getClientesAtendidos() + 1);               
            }


            for (int f = 0; f < ordenLLegada.size(); f++) {// ESTE CICLO PERMITE GESTIONAR LA LLEGADA DE LOS CLIENTES Y PONERLOS EN LA FILA
                                                          // SI LA FILA ESTÁ VACIA Y HAY UN CAJERO LIBRE PASA A ATENCION DE UNA VEZ

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

        while (!fila.isEmpty()) { // ESTE CICLO PERMITE FINALIZAR LA ATENCIÓN DE LOS CAJEROS QUE TEMINAN JUSTO EN EL TIEMPO EN QUE FINALIZA EL TURNO
            for (int w = 0; w < Cajeros.length; w++) {

                if (Cajeros[w].getOcupacion() < reloj && Cajeros[w].getOcupacion() != 0) {
                    Cajeros[w].setOcupado(false);                    
                    Cajas[w].setTiempoSalida(reloj);
                    atendidos.add(Cajas[w]);
                }

            }

            while (!fila.isEmpty() && cLibre()) { // ESTE CICLO PERMITE ATENDER LOS CLIENTES QUE A LA HORA DE FINALIZAR EL TURNO AÚN PERMANECEN EN LA FILA

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

        if (fila.isEmpty() && reloj >= limite) { // ESTE CICLO PERMITE FINALIZAR LA ATENCIÓN DE LOS CLIENTES QUE PARA LA HORA DEL FINAL DEL TURNO
                                                 //SE ENCUENTRAN EN LA CAJA
            while (todosLibres()) {
                for (int w = 0; w < Cajeros.length; w++) {

                    if (Cajeros[w].getOcupacion() < reloj && Cajeros[w].getOcupacion() != 0) {
                        Cajeros[w].setOcupado(false);                        
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
        MensajeResultados+="Cantidad de clientes atendidos: " + ClienteAtendido+"\n";
        MensajeResultados+="Porcentaje de clientes perdidos: "+Math.round(((double)ClientesPerdidos/(ClientesPerdidos+ClienteAtendido))*100)+"%\n";
        Cliente t;
        double y=0;
        double y2=0;
        for(int p=0;p<atendidos.size();p++){
            t=(Cliente)atendidos.elementAt(p);
            y=y+(t.getTiempoSalida()-t.getTiempoLLegada());
            y2=y2+(t.getTiempoInicioAtencion()-t.getTiempoLLegada());
        }
        MensajeResultados+="El promedio de estadia del cliente en el banco es: "+Math.round(y/ClienteAtendido)+"\n";
        MensajeResultados+="El promedio de tiempo en la fila del cliente es: "+Math.round(y2/ClienteAtendido)+" min";
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
