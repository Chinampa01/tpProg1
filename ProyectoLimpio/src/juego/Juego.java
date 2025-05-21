package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
    private Entorno entorno;
    private Image imgFondo;
    private Mago mago;

    public Juego() {
        this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
        this.mago = new Mago(400, 300, 6);
        this.imgFondo = Herramientas.cargarImagen("assets/entorno.png");

        this.entorno.iniciar();
    }

    public void tick() {
        // Dibuja fondo
    	entorno.dibujarImagen(imgFondo, 400, 300, 0, 0.8);

        // Movimiento
        if (entorno.estaPresionada(entorno.TECLA_DERECHA)) {
            mago.moverDerecha();
        }
        if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
            mago.moverIzquierda();
        }
        if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
            mago.moverArriba();
        }
        if (entorno.estaPresionada(entorno.TECLA_ABAJO)) {
            mago.moverAbajo();
        }

        // Dibuja el mago
        mago.dibujarse(entorno);
    }

    public static void main(String[] args) {
        new Juego();
    }
}
