package juego;

import java.awt.Image;
import entorno.Entorno;

public class HechizoVisual {
    protected double x, y, angulo, escala;
    protected Image imagen;
    protected int tickCreacion, duracionTicks;

    public HechizoVisual(double x, double y, double angulo, Image imagen, double escala, int tickCreacion, int duracionTicks) {
        this.x = x;
        this.y = y;
        this.angulo = angulo;
        this.imagen = imagen;
        this.escala = escala;
        this.tickCreacion = tickCreacion;
        this.duracionTicks = duracionTicks;
    }

    public void dibujar(Entorno entorno, int tickActual) {
        entorno.dibujarImagen(imagen, x, y, angulo, escala);
    }

    public boolean estaActivo(int tickActual) {
        return tickActual - tickCreacion < duracionTicks;
    }
}