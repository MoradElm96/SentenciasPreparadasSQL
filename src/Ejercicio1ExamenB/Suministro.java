/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ejercicio1ExamenB;

/**
 *
 * @author Morad
 */
public class Suministro {
    
   private String numprov,numparte,numproyecto;
   private  int cantidad;
   private String operacion;

    public Suministro(String numprov, String numparte, String numproyecto, int cantidad, String operacion) {
        this.numprov = numprov;
        this.numparte = numparte;
        this.numproyecto = numproyecto;
        this.cantidad = cantidad;
        this.operacion = operacion;
    }
    //importante constructor vacio

    public Suministro() {
    }
    
    
    
    public String getNumprov() {
        return numprov;
    }

    public void setNumprov(String numprov) {
        this.numprov = numprov;
    }

    public String getNumparte() {
        return numparte;
    }

    public void setNumparte(String numparte) {
        this.numparte = numparte;
    }

    public String getNumproyecto() {
        return numproyecto;
    }

    public void setNumproyecto(String numproyecto) {
        this.numproyecto = numproyecto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    @Override
    public String toString() {
        return "Suministro{" + "numprov=" + numprov + ", numparte=" + numparte + ", numproyecto=" + numproyecto + ", cantidad=" + cantidad + ", operacion=" + operacion + '}';
    }
    
    
}
