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
        this.x = x;
        this.y = y;
        this.desplazamiento = desplazamiento;
        this.angulo = angulo;
        this.radio = radio;
        this.imagenDer = Herramientas.cargarImagen("assets/mago.der.png");
        this.imagenIzq = Herramientas.cargarImagen("assets/mago.izq.png");
        this.mirandoDerecha = true;
        this.vida = 100;
        this.vidaMax = 100;
        this.mana = 50;
        this.manaMax = 50;
    }

    public int getVida() { return vida; }
    public int getVidaMax() { return vidaMax; }
    public int getMana() { return mana; }
    public int getManaMax() { return manaMax; }
    public void usarMana(int cantidad) { mana = Math.max(0, mana - cantidad); }

    public void dibujarse(Entorno entorno) {
        if (mirandoDerecha) {
            entorno.dibujarImagen(imagenDer, x, y, 0, 0.07);
        } else {
            entorno.dibujarImagen(imagenIzq, x, y, 0, 0.07);
        }
    }

    public boolean chocasteCon (Entorno e){
        return x <= radio || y <= radio || x >= e.ancho() - radio || y >= e.alto() - radio;
    }

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

    public void rebotar (){
        angulo += Math.PI/2;
    }
}