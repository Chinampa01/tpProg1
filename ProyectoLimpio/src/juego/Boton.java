package juego;

import java.awt.Image;
import entorno.Entorno;

public class Boton {
    private int x, y, ancho, alto;
    private Image imagen;

    public Boton(int x, int y, int ancho, int alto, Image imagen) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.imagen = imagen;
    }

    public void dibujar(Entorno entorno) {
        if (imagen != null) {
            entorno.dibujarImagen(imagen, x + ancho/2, y + alto/2, 0, (double)ancho / imagen.getWidth(null));
        }
    }

    public boolean estaSobre(int mx, int my) {
        return mx >= x && mx <= x + ancho && my >= y && my <= y + alto;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public Image getImagen() { return imagen; }
}