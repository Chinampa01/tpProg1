package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import java.awt.Color;

public class Menu {
    private int x, y, ancho, alto;
    private Image imagenFondo;
    private Boton botonFuego, botonAgua;
    private Barra barraVida, barraMana;

    // Posiciones y tamaños fijos para los botones
    private static final int FUEGO_X = 229;
    private static final int FUEGO_Y = 535;
    private static final int FUEGO_ANCHO = 70;
    private static final int FUEGO_ALTO = 70;

    private static final int AGUA_X = 316;
    private static final int AGUA_Y = 535;
    private static final int AGUA_ANCHO = 70;
    private static final int AGUA_ALTO = 70;

    public Menu(int x, int y, int ancho, int alto, String rutaFondo, String rutaFuego, String rutaAgua) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.imagenFondo = Herramientas.cargarImagen(rutaFondo);
        Image imgFuego = Herramientas.cargarImagen(rutaFuego);
        Image imgAgua = Herramientas.cargarImagen(rutaAgua);
        this.botonFuego = new Boton(FUEGO_X, FUEGO_Y, FUEGO_ANCHO, FUEGO_ALTO, imgFuego);
        this.botonAgua = new Boton(AGUA_X, AGUA_Y, AGUA_ANCHO, AGUA_ALTO, imgAgua);
        this.barraVida = new Barra(400, 558, 180, 15, new Color(60, 30, 0), Color.RED);
        this.barraMana = new Barra(400, 580, 180, 15, new Color(60, 30, 0), Color.BLUE);
    }

    public void dibujar(Entorno entorno, Mago mago) {
        if (imagenFondo != null) {
            entorno.dibujarImagen(imagenFondo, x + ancho/2, y + alto/2, 0, (double)ancho / imagenFondo.getWidth(null));
        }
        if (botonFuego != null) {
            botonFuego.dibujar(entorno);
        }
        if (botonAgua != null) {
            botonAgua.dibujar(entorno);
        }
        // Barras
        double porcentajeVida = (double)mago.getVida() / mago.getVidaMax();
        double porcentajeMana = (double)mago.getMana() / mago.getManaMax();
        barraVida.dibujar(entorno, porcentajeVida);
        barraMana.dibujar(entorno, porcentajeMana);
    }

    public Boton getBotonFuego() { return botonFuego; }
    public Boton getBotonAgua() { return botonAgua; }
}