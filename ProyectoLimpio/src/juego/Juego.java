package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
    private Entorno entorno;
    private Menu menu;
    private Image imgFondo, imgBombaFuego, imgFinalFlash, imgRafagaAgua;
    private Mago mago;
    private Roca[] rocas;
    private HechizoVisual hechizoActivo;
    private String hechizoSeleccionado = "agua";
    private int tickActual = 0;

    public Juego() {
        this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
        this.mago = new Mago(400, 300, 2, Math.PI / 4, 30);
        this.imgFondo = Herramientas.cargarImagen("assets/fondo.png");
        imgBombaFuego = Herramientas.cargarImagen("assets/explosionDeFuego.png");
        imgRafagaAgua = Herramientas.cargarImagen("assets/rafagaDeAgua.png");
        imgFinalFlash = Herramientas.cargarImagen("assets/finalFlash.png");
        hechizoActivo = null;


        this.menu = new Menu(
            200, 510, 400, 100,
            "assets/menu.png",
            "assets/hechizoFuego.png",
            "assets/hechizoAgua.png",
            70, 70, 
            70, 70  
        );

        rocas = new Roca[6];
        rocas[0] = new Roca(100, 100, false);
        rocas[1] = new Roca(600, 200, false);
        rocas[2] = new Roca(350, 470, false);
        rocas[3] = new Roca(550, 380, false);
        rocas[4] = new Roca(150, 350, false);
        rocas[5] = new Roca(450, 50, false);

        this.entorno.iniciar();
    }

    private boolean puedeMoverMago(int nuevoX, int nuevoY) {
    int radio = mago.getRadio();
    if (nuevoX - radio < 0 || nuevoX + radio > 800) return false;
    if (nuevoY + radio > 500) return false;
    if (nuevoY - radio < 0) return false;

    for (Roca roca : rocas) {
        int dx = nuevoX - roca.getX();
        int dy = nuevoY - roca.getY();
        double distancia = Math.sqrt(dx*dx + dy*dy);
        if (distancia < radio + 15) return false;
    }
    return true;
}

    public void tick() {
        try {
            tickActual = entorno.numeroDeTick();

            entorno.dibujarImagen(imgFondo, 400, 300, 0, 0.8);
            menu.dibujar(entorno, mago);

            // Solo permitir movimiento si NO hay hechizo activo
            boolean puedeMover = hechizoActivo == null || !hechizoActivo.estaActivo(tickActual);

            if (puedeMover) {
                int nuevoX = mago.getX();
                int nuevoY = mago.getY();

                if (entorno.estaPresionada('d')) nuevoX += mago.getDesplazamiento();
                if (entorno.estaPresionada('a')) nuevoX -= mago.getDesplazamiento();
                if (entorno.estaPresionada('w')) nuevoY -= mago.getDesplazamiento();
                if (entorno.estaPresionada('s')) nuevoY += mago.getDesplazamiento();

                if (puedeMoverMago(nuevoX, nuevoY)) {
                    if (nuevoX > mago.getX()) mago.moverDerecha();
                    if (nuevoX < mago.getX()) mago.moverIzquierda();
                    if (nuevoY < mago.getY()) mago.moverArriba();
                    if (nuevoY > mago.getY()) mago.moverAbajo();
                }
            }

            for (int i = 0; i < rocas.length; i++) {
                rocas[i].dibujarse(entorno);
            }

            if (entorno.sePresiono('1')) {
                hechizoSeleccionado = "agua";
                menu.setHechizoSeleccionado("agua");
            }
            if (entorno.sePresiono('2')) {
                hechizoSeleccionado = "fuego";
                menu.setHechizoSeleccionado("fuego");
            }

            if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
                int mouseX = entorno.mouseX();
                int mouseY = entorno.mouseY();

                int botonFuegoX = 316;
                int botonFuegoY = 535;
                int botonFuegoAncho = 70;
                int botonFuegoAlto = 70;

                int botonAguaX = 229;
                int botonAguaY = 535;
                int botonAguaAncho = 70;
                int botonAguaAlto = 70;

                boolean clickEnFuego = mouseX >= botonFuegoX && mouseX <= botonFuegoX + botonFuegoAncho &&
                                       mouseY >= botonFuegoY && mouseY <= botonFuegoY + botonFuegoAlto;
                boolean clickEnAgua = mouseX >= botonAguaX && mouseX <= botonAguaX + botonAguaAncho &&
                                      mouseY >= botonAguaY && mouseY <= botonAguaY + botonAguaAlto;

                if (clickEnFuego) {
                    hechizoSeleccionado = "fuego";
                    menu.setHechizoSeleccionado("fuego");
                } else if (clickEnAgua) {
                    hechizoSeleccionado = "agua";
                    menu.setHechizoSeleccionado("agua");
                } else if (puedeMover) { // Solo lanzar si puede mover (no hay hechizo activo)
                    double dx = entorno.mouseX() - mago.getX();
                    double dy = entorno.mouseY() - mago.getY();
                    double angulo = Math.atan2(dy, dx);
                    double distancia = mago.getRadio() + 10;
                    double x = mago.getX() + Math.cos(angulo) * distancia;
                    double y = mago.getY() + Math.sin(angulo) * distancia;

                    if (hechizoSeleccionado.equals("agua")) {
                        hechizoActivo = new HechizoVisual(x, y, angulo, imgRafagaAgua, 0.2, tickActual, 20);
                    }
                    if (hechizoSeleccionado.equals("fuego")) {
                        hechizoActivo = new HechizoFuego(mago, angulo, imgBombaFuego, imgFinalFlash, 0.35, tickActual, 25, 12);
                    }
                }
            }

            if (hechizoActivo != null && hechizoActivo.estaActivo(tickActual)) {
                hechizoActivo.dibujar(entorno, tickActual);
            } else {
                hechizoActivo = null;
            }

            mago.dibujarse(entorno);
            menu.dibujar(entorno, mago);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Juego();
    }
}