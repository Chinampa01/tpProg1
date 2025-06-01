package juego;

import entorno.Entorno;
import java.awt.Image;

public class PanelHechizosLateral {
    private final Boton botonCuracion, botonBarrera;
    private int cooldownCuracion, cooldownBarrera;
    private static final int COOLDOWN_MURCIELAGOS = 20;
    private final Image imgCuracion, imgBarrera;
    private boolean curando = false;
    private int ticksCurando = 0;
    private int vidaInicial = 0;
    private int vidaFinal = 0;
    private static final int CURACION_DURACION = 240;

    public PanelHechizosLateral(int x, int y, Image imgCuracion, Image imgBarrera) {
        try {
            this.imgCuracion = imgCuracion;
            this.imgBarrera = imgBarrera;
            this.botonCuracion = new Boton(x - 30, y, 60, 60, imgCuracion);
            this.botonBarrera = new Boton(x + 40, y, 60, 60, imgBarrera);
            this.cooldownCuracion = 0;
            this.cooldownBarrera = 0;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear PanelHechizosLateral: no se pudo inicializar el panel o los botones", e);
        }
    }

    public void dibujar(Entorno entorno, boolean barreraActiva, int magoX, int magoY) {
        try {
            botonCuracion.dibujar(entorno);
            botonBarrera.dibujar(entorno);
            if (cooldownCuracion > 0) {
                entorno.cambiarFont("Arial", 18, java.awt.Color.WHITE);
                entorno.escribirTexto(cooldownCuracion+"", botonCuracion.getX()+20, botonCuracion.getY()+55);
            }
            if (cooldownBarrera > 0) {
                entorno.cambiarFont("Arial", 18, java.awt.Color.WHITE);
                entorno.escribirTexto(cooldownBarrera+"", botonBarrera.getX()+20, botonBarrera.getY()+55);
            }
            if (curando) {
                entorno.dibujarImagen(imgCuracion, magoX, magoY, 0, 0.15);
            }
            if (barreraActiva) {
                entorno.dibujarImagen(imgBarrera, magoX, magoY, 0, 0.15);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al dibujar el PanelHechizosLateral: fallo en renderizado o acceso a recursos", e);
        }
    }

    public boolean puedeCurar() {
        return cooldownCuracion == 0 && !curando;
    }
    public boolean puedeBarrera() {
        return cooldownBarrera == 0;
    }
    public void activarCuracion(int vidaActual, int vidaMax) {
        if (puedeCurar()) {
            curando = true;
            ticksCurando = 0;
            vidaInicial = vidaActual;
            vidaFinal = Math.min(vidaActual + vidaMax/2, vidaMax);
            cooldownCuracion = COOLDOWN_MURCIELAGOS;
        }
    }
    public void activarBarrera() {
        if (puedeBarrera()) {
            cooldownBarrera = COOLDOWN_MURCIELAGOS;
        }
    }
    public void notificarMurcielagoMatado() {
        if (cooldownCuracion > 0) cooldownCuracion--;
        if (cooldownBarrera > 0) cooldownBarrera--;
    }
    public boolean clickCuracion(int mouseX, int mouseY, boolean click) {
        return botonCuracion.estaSobre(mouseX, mouseY) && click;
    }
    public boolean clickBarrera(int mouseX, int mouseY, boolean click) {
        return botonBarrera.estaSobre(mouseX, mouseY) && click;
    }
    public int procesarCuracionTick(int vidaActual) {
        if (!curando) return 0;
        ticksCurando++;
        int totalCurar = vidaFinal - vidaInicial;
        if (ticksCurando < CURACION_DURACION && vidaActual < vidaFinal) {
            int vidaPorTick = totalCurar / CURACION_DURACION;
            if (vidaPorTick < 1) vidaPorTick = 1;
            if (vidaActual + vidaPorTick > vidaFinal) vidaPorTick = vidaFinal - vidaActual;
            return vidaPorTick;
        } else {
            curando = false;
            return vidaFinal - vidaActual > 0 ? vidaFinal - vidaActual : 0;
        }
    }
    public boolean estaCurando() { return curando; }
}
