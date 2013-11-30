/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problemabanco;

/**
 *
 * @author Marx
 */
public class Cajero {
    boolean Ocupado=false;
     double TiempoTotalOcupado=0;
     double Ocupacion=0; 
    int clientesAtendidos=0;

    public boolean isOcupado() {
        return Ocupado;
    }

    public void setOcupado(boolean Ocupado) {
        this.Ocupado = Ocupado;
    }

    public double getTiempoTotalOcupado() {
        return TiempoTotalOcupado;
    }

    public void setTiempoTotalOcupado(double TiempoTotalOcupado) {
        this.TiempoTotalOcupado = this.TiempoTotalOcupado+TiempoTotalOcupado;
    }

    public double getOcupacion() {
        return Ocupacion;
    }

    public void setOcupacion(double Ocupacion) {
        this.Ocupacion = Ocupacion;
    }

    public int getClientesAtendidos() {
        return clientesAtendidos;
    }

    public void setClientesAtendidos(int clientesAtendidos) {
        this.clientesAtendidos = clientesAtendidos;
    }
   
    
    
    
}
