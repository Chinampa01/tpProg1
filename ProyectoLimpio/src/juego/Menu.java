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
    private String hechizoSeleccionado = "agua";

    // Posiciones para los botones
    private static final int FUEGO_X = 316;
    private static final int FUEGO_Y = 545;
    private static final int AGUA_X = 229;
    private static final int AGUA_Y = 545;

    public Menu(int x, int y, int ancho, int alto, String rutaFondo, String rutaFuego, String rutaAgua,
                int anchoBotonFuego, int altoBotonFuego, int anchoBotonAgua, int altoBotonAgua) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.imagenFondo = Herramientas.cargarImagen(rutaFondo);
        Image imgFuego = Herramientas.cargarImagen(rutaFuego);
        Image imgAgua = Herramientas.cargarImagen(rutaAgua);
        this.botonFuego = new Boton(FUEGO_X, FUEGO_Y, anchoBotonFuego, altoBotonFuego, imgFuego);
        this.botonAgua = new Boton(AGUA_X, AGUA_Y, anchoBotonAgua, altoBotonAgua, imgAgua);
        this.barraVida = new Barra(400, 558, 180, 15, new Color(60, 30, 0), Color.RED);
        this.barraMana = new Barra(400, 580, 180, 15, new Color(60, 30, 0), Color.BLUE);
    }

    public void setHechizoSeleccionado(String hechizo) {
        this.hechizoSeleccionado = hechizo;
    }

    public void dibujar(Entorno entorno, Mago mago) {
        if (imagenFondo != null) {
            entorno.dibujarImagen(imagenFondo, x + ancho/2, y + alto/2, 0, (double)ancho / imagenFondo.getWidth(null));
        }
        // Resaltado de botones
        if ("fuego".equals(hechizoSeleccionado)) {
            entorno.dibujarRectangulo(
            botonFuego.getX() + botonFuego.getAncho()/2,
            botonFuego.getY() + botonFuego.getAlto()/2,
            botonFuego.getAncho()+8, botonFuego.getAlto()+8, 0, new Color(255, 80, 0));
        }
        if ("agua".equals(hechizoSeleccionado)) {
            entorno.dibujarRectangulo(
            botonAgua.getX() + botonAgua.getAncho()/2,
            botonAgua.getY() + botonAgua.getAlto()/2,
            botonAgua.getAncho()+8, botonAgua.getAlto()+8, 0, new Color(0, 180, 255));
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