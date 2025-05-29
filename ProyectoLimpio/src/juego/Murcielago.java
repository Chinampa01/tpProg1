package juego;

import java.awt.Color;
import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Murcielago {
    double x;
    double y;
    private Image imagenDer;
    private Image imagenIzq;
    private double angulo;
    private double velocidad = 1;
    private boolean mirandoDer;

    public Murcielago(double x, double y) {
        this.x = x;
        this.y = y;
        this.angulo = 0;

        this.imagenDer = Herramientas.cargarImagen("assets/mago.izq.png");
        this.imagenIzq = Herramientas.cargarImagen("assets/mago.der.png"); 

        this.mirandoDer = true;
    }

    public void dibujarse(Entorno entorno) {
        entorno.dibujarCirculo(this.x, this.y, 4, Color.RED);

        if (mirandoDer) {
            entorno.dibujarImagen(imagenDer, x, y, 0, 0.04);
        } else {
            entorno.dibujarImagen(imagenIzq, x, y, 0, 0.04);
        }
    }

    public void cambiarAngulo(double x2, double y2) {
        this.angulo = Math.atan2(y2 - this.y, x2 - this.x);
        
        // Cambia si mira a la derecha o izquierda
        if (x2 > this.x) {
            this.mirandoDer = true;
        } else {
            this.mirandoDer = false;
        }
    }

    public void mover() {
        this.x += Math.cos(this.angulo) * velocidad;
        this.y += Math.sin(this.angulo) * velocidad;
    }

    public boolean isMirandoDer() {
        return mirandoDer;
    }

    public void setMirandoDer(boolean mirandoDer) {
        this.mirandoDer = mirandoDer;
    }

}
