package juego;

import entorno.Entorno;
import java.awt.Color;

public class Barra {
    private int x, y, ancho, alto;
    private Color colorFondo;
    private Color colorRelleno;

    public Barra(int x, int y, int ancho, int alto, Color colorFondo, Color colorRelleno) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.colorFondo = colorFondo;
        this.colorRelleno = colorRelleno;
    }

    
    public void dibujar(Entorno entorno, double porcentaje) {
        // Fondo
        entorno.dibujarRectangulo(x + ancho/2, y + alto/2, ancho, alto, 0, colorFondo);
        // Relleno
        int anchoRelleno = (int)(ancho * porcentaje);
        entorno.dibujarRectangulo(x + anchoRelleno/2, y + alto/2, anchoRelleno, alto, 0, colorRelleno);
    }
}