package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Roca {
    private int x;    
    private int y;
    private boolean colision;    
    private Image imagen;
    
    public int getX() { return x; }
    public int getY() { return y; }
    
    public Roca(int x, int y, boolean colision) {
        this.x=x;        
        this.y=y;
        this.colision=colision;        
        this.imagen=Herramientas.cargarImagen("assets/roca.png");
    }    
    
    public void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(imagen, x,y,0.07,0.05);    
    }
}