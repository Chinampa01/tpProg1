package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Rock {
    private int x;    private int y;
    private boolean colision;    private Image imagen;
    public Rock(int x, int y, boolean colision) {
        this.x=x;        this.y=y;
        this.colision=colision;        this.imagen=Herramientas.cargarImagen("assets/roca.png");
    }    public void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(imagen, x,y,0.07,0.07);    }
}