package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
    private Entorno entorno;
    private Menu menu;
    private Image imgFondo;
    private Mago mago;

    public Juego() {
        this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
        this.mago = new Mago(400, 300, 6);
        this.imgFondo = Herramientas.cargarImagen("assets/fondo.png");

        this.menu = new Menu(
            200, 510, 400, 80,
            "assets/menu.png",
            "assets/hechizoFuego.png",
            "assets/hechizoAgua.png"
        );

        this.entorno.iniciar();
    }

    public void tick() {
        try {
            // Dibuja fondo y menu
            entorno.dibujarImagen(imgFondo, 400, 300, 0, 0.8);
            menu.dibujar(entorno, mago);

            // Movimiento
            if (entorno.estaPresionada(entorno.TECLA_DERECHA)) mago.moverDerecha();
            if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) mago.moverIzquierda();
            if (entorno.estaPresionada(entorno.TECLA_ARRIBA)) mago.moverArriba();
            if (entorno.estaPresionada(entorno.TECLA_ABAJO)) mago.moverAbajo();

            // Dibuja el mago
            mago.dibujarse(entorno);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Juego();
    }
}