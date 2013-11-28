/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problemabanco;

/**
 * 
 * @author maraes
 */
public class Cliente {

	double tiempoLLegada;
	double tiempoSalida;
	double tiempoInicioAtencion;
	double tiempoAtencion;
	int cajeroAtiende;

	public Cliente(double tiempoLLega, double tiempoA) {
		this.tiempoLLegada = tiempoLLega;
		this.tiempoAtencion = tiempoA;
	}

    public double getTiempoLLegada() {
        return tiempoLLegada;
    }

    public void setTiempoLLegada(double tiempoLLegada) {
        this.tiempoLLegada = tiempoLLegada;
    }

    public double getTiempoSalida() {
        return tiempoSalida;
    }

    public void setTiempoSalida(double tiempoSalida) {
        this.tiempoSalida = tiempoSalida;
    }

    public double getTiempoInicioAtencion() {
        return tiempoInicioAtencion;
    }

    public void setTiempoInicioAtencion(double tiempoInicioAtencion) {
        this.tiempoInicioAtencion = tiempoInicioAtencion;
    }

    public double getTiempoAtencion() {
        return tiempoAtencion;
    }

    public void setTiempoAtencion(double tiempoAtencion) {
        this.tiempoAtencion = tiempoAtencion;
    }

    public int getCajeroAtiende() {
        return cajeroAtiende;
    }

    public void setCajeroAtiende(int cajeroAtiende) {
        this.cajeroAtiende = cajeroAtiende;
    }

	
	
	
}
