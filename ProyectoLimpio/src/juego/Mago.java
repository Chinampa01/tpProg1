package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Mago {
    private int x;
    private int y;
    private int desplazamiento;
    private Image imagenDer;
    private Image imagenIzq;
    private boolean mirandoDerecha;

    public Mago(int x, int y, int desplazamiento) {
        this.x = x;
        this.y = y;
        this.desplazamiento = desplazamiento;
        this.imagenDer = Herramientas.cargarImagen("assets/mago.der.png");
        this.imagenIzq = Herramientas.cargarImagen("assets/mago.izq.png");
        this.mirandoDerecha = true; // Por defecto, mira a la derecha
    }
//imagen de mago 
    public void dibujarse(Entorno entorno) {
        if (mirandoDerecha) {
            entorno.dibujarImagen(imagenDer, x, y, 0, 0.07      );
        } else {
            entorno.dibujarImagen(imagenIzq, x, y, 0, 0.07);
        }
    }
//moviento del mago
    public void moverIzquierda() {
        x -= desplazamiento;
        mirandoDerecha = false;
    }

    public void moverDerecha() {
        x += desplazamiento;
        mirandoDerecha = true;
    }

    public void moverArriba() {
        y -= desplazamiento;
    }

    public void moverAbajo() {
        y += desplazamiento;
    }
}

