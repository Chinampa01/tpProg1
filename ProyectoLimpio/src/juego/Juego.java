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
    private Roca [] rocas;


    public Juego() {
        this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
        this.mago = new Mago(400, 300, 6, Math.PI / 4, 30); 
        this.imgFondo = Herramientas.cargarImagen("assets/fondo.png");

        if (mago.chocasteCon(entorno)){
            mago.rebotar();
        }

        this.menu = new Menu(
            200, 510, 400, 80,
            "assets/menu.png",
            "assets/hechizoFuego.png",
            "assets/hechizoAgua.png"
        );


        rocas = new Roca[6];
        rocas[0] = new Roca(100, 100, false);
        rocas[1] = new Roca(550, 150, false);
        rocas[2] = new Roca(250, 450, false);
        rocas[3] = new Roca(500, 250, false);
        rocas[4] = new Roca(150, 350, false);
        rocas[5] = new Roca(250, 50, false);

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

            

            for(int i=0;i<rocas.length;i++) {
             rocas[i].dibujarse(entorno);  
              }

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