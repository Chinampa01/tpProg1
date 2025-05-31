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
    private double velocidad;
    private boolean mirandoDer;
    private boolean esGolbat;
    private int golpesRecibidos;

    public Murcielago(double x, double y, boolean esGolbat) {
        this.x = x;
        this.y = y;
        this.angulo = 0;
        this.esGolbat = esGolbat;
        this.golpesRecibidos = 0;
        if (esGolbat) {
            this.imagenDer = Herramientas.cargarImagen("assets/golbatDer.png");
            this.imagenIzq = Herramientas.cargarImagen("assets/golbatIzq.PNG");
            this.velocidad = 1.3;
        } else {
            this.imagenDer = Herramientas.cargarImagen("assets/zubatDer.png");
            this.imagenIzq = Herramientas.cargarImagen("assets/zubatIzq.PNG");
            this.velocidad = 1.0;
        }
        this.mirandoDer = true;
    }

    public void dibujarse(Entorno entorno) {
        if (esGolbat) {
            entorno.dibujarCirculo(this.x, this.y, 8, Color.MAGENTA);
            if (mirandoDer) {
                entorno.dibujarImagen(imagenDer, x, y, 0, 0.08);
            } else {
                entorno.dibujarImagen(imagenIzq, x, y, 0, 0.08);
            }
        } else {
            entorno.dibujarCirculo(this.x, this.y, 4, Color.RED);
            if (mirandoDer) {
                entorno.dibujarImagen(imagenDer, x, y, 0, 0.04);
            } else {
                entorno.dibujarImagen(imagenIzq, x, y, 0, 0.04);
            }
        }
    }

    public void cambiarAngulo(double x2, double y2) {
        this.angulo = Math.atan2(y2 - this.y, x2 - this.x);
        this.mirandoDer = x2 > this.x;
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

    public void recibirGolpe(boolean esRafagaAgua, boolean esFuego) {
        if (esGolbat) {
            if (esFuego) {
                golpesRecibidos = 2; // Muere instantáneamente con fuego
            } else if (esRafagaAgua) {
                golpesRecibidos++;
            }
            // Otros hechizos no afectan a Golbat
        } else {
            golpesRecibidos = 1; // Para Zubat, cualquier golpe lo mata
        }
    }

    public boolean esGolbat() {
        return esGolbat;
    }

    public boolean estaMuerto() {
        return esGolbat ? golpesRecibidos >= 2 : golpesRecibidos >= 1;
    }
}
