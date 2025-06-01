package juego;

import java.awt.Image;
import entorno.Entorno;

public class MenuLateral {
    private int x,y;
    private Image img;


    public MenuLateral(int x, int y, Image img) {
        try {
            this.x = x;
            this.y = y;
            this.img = img;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear MenuLateral: no se pudo inicializar el menú lateral o su imagen", e);
        }
    }

    public void dibujar(Entorno entorno) {
        try {
            if (img != null) {
                entorno.dibujarImagen(img, x, y, 0, 0.20);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al dibujar el MenuLateral: fallo en renderizado o acceso a la imagen", e);
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public Image getImg() { return img; }
}
