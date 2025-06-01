package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class PocionMana {
    private double x;
    private double y;
    private Image imagen;
    private boolean activa;
    private static final int RADIO = 12;
    private static final int MANA_RESTAURADO = 10; 

    public PocionMana(double x, double y) {
        try {
            this.x = x;
            this.y = y;
            this.imagen = Herramientas.cargarImagen("assets/pocionMana.png");
            this.activa = true;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear PocionMana: no se pudo cargar la imagen o inicializar la poción", e);
        }
    }

    public void dibujar(Entorno entorno) {
        if (activa && imagen != null) {
            entorno.dibujarImagen(imagen, x, y, 0, 0.06);
        }
    }

    public boolean estaActiva() {
        return activa;
    }

    public void recoger() {
        activa = false;
    }

    public boolean colisionaCon(double px, double py, int radioJugador) {
        double dx = x - px;
        double dy = y - py;
        return Math.sqrt(dx*dx + dy*dy) < RADIO + radioJugador;
    }

    public int getManaRestaurado() {
        return MANA_RESTAURADO;
    }
}