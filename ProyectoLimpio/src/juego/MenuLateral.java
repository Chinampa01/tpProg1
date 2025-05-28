package juego;

import java.awt.Image;
import entorno.Entorno;

public class MenuLateral {
    private int x,y;
    private Image img;


    public MenuLateral(int x, int y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public void dibujar(Entorno entorno) {
        if (img != null) {
            entorno.dibujarImagen(img, x, y, 0, 0.20);
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Image getImg() { return img; }
}
