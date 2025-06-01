package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import java.lang.Math;

public class Mago {
    private int x;
    private int y;
    private int desplazamiento;
    private double angulo;
    private int radio;
    private Image imagenDer;
    private Image imagenIzq;
    private boolean mirandoDerecha;
    private int vida;
    private int vidaMax;
    private int mana;
    private int manaMax;

    public Mago(int x, int y, int desplazamiento, double angulo, int radio) {
        try {
            this.x = x;
            this.y = y;
            this.desplazamiento = desplazamiento;
            this.angulo = angulo;
            this.radio = radio;
            this.imagenDer = Herramientas.cargarImagen("assets/mago.der.png");
            this.imagenIzq = Herramientas.cargarImagen("assets/mago.izq.png");
            this.mirandoDerecha = true;
            this.vida = 10;
            this.vidaMax = 10;
            this.mana = 30; // Solo permite 3 hechizos de fuego (10 de maná cada uno)
            this.manaMax = 30;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear Mago: no se pudo cargar la imagen o inicializar el objeto", e);
        }
    }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getDesplazamiento() {return desplazamiento;}
    public int getRadio() { return radio; }
    public int getVida() { return vida; }
    public int getVidaMax() { return vidaMax; }
    public int getMana() { return mana; }
    public int getManaMax() { return manaMax; }
    public void usarMana(int cantidad) { mana = Math.max(0, mana - cantidad); }

    public void dibujarse(Entorno entorno) {
        try {
            if (mirandoDerecha) {
                entorno.dibujarImagen(imagenDer, x, y, 0, 0.07);
            } else {
                entorno.dibujarImagen(imagenIzq, x, y, 0, 0.07);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al dibujar el Mago: fallo en renderizado o acceso a la imagen", e);
        }
    }

    public void moverIzquierda() {
        x -= desplazamiento;
        mirandoDerecha = false;
    }
    public void moverDerecha() {
        x += desplazamiento; 
        mirandoDerecha = true;}
    public void moverArriba() {y -= desplazamiento;}
    public void moverAbajo() {y += desplazamiento;}

    public void recibirDaño(int cantidad) {
        vida = Math.max(0, vida - cantidad);
    }
}