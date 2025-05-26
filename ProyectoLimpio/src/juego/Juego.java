package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;
import java.util.Random;


public class Juego extends InterfaceJuego {
    private Entorno entorno;
    private Menu menu;
    private Image imgFondo;
    private Mago mago;
    private Rock [] rocas;


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

        Random rand=new Random();
        rocas=new Rock[6];
        for(int j=0;j<rocas.length;j++) {
            int x=rand.nextInt(500)+50;            
            int y=rand.nextInt(500)+50;
            rocas[j]=new Rock(x,y,false);        
        }

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