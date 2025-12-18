/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca;

/**
 *
 * @author Joshelyn
 */
public class Horario {
    private String diaApertura;
    private String horaApertura;
    private String horaCierre;
    // constructor
    public Horario(String diaApertura, String horaApertura, String horaCierre) {
        this.diaApertura = diaApertura;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
    }

    public void mostrarHorario(){
        System.out.println("Día de apertura: " + diaApertura + ", Hora de apertura: " + horaApertura + ", Hora de cierre: " + horaCierre);
    }
}
