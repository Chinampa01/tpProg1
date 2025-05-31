package juego;

import java.awt.Image;
import entorno.Entorno;

public class HechizoFuego extends HechizoVisual {
    private Image imagenFinal;
    private int ticksExplosion;
    private Mago mago;
    private double xFinal, yFinal, anguloFinal;
    private boolean finalFlashPosicionado = false;

    public HechizoFuego(Mago mago, double angulo, Image imagenExplosion, Image imagenFinal, double escala, int tickCreacion, int duracionTicks, int ticksExplosion) {
        super(mago.getX(), mago.getY(), angulo, imagenExplosion, escala, tickCreacion, duracionTicks);
        this.imagenFinal = imagenFinal;
        this.ticksExplosion = ticksExplosion;
        this.mago = mago;
        this.anguloFinal = angulo;
    }

    public void dibujar(Entorno entorno, int tickActual) {
        if (tickActual - tickCreacion < ticksExplosion) {
            this.x = mago.getX();
            this.y = mago.getY();
            entorno.dibujarImagen(imagen, x, y, angulo, escala);
        } else {
            if (!finalFlashPosicionado) {
                double radioFinalFlash = mago.getRadio() + 10;
                double ajuste = (imagenFinal.getWidth(null) * escala) / 2.0;
                double distancia = radioFinalFlash + ajuste;
                xFinal = mago.getX() + Math.cos(anguloFinal) * distancia;
                yFinal = mago.getY() + Math.sin(anguloFinal) * distancia;
                finalFlashPosicionado = true;
            }
            entorno.dibujarImagen(imagenFinal, xFinal, yFinal, anguloFinal, escala);
        }
    }

    public int getTicksExplosion() {
        return ticksExplosion;
    }
    public double getXFinal() {
        return xFinal;
    }
    public double getYFinal() {
        return yFinal;
    }
}