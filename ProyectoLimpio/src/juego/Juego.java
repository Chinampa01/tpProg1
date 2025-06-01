package juego;

import java.awt.Image;
import java.util.ArrayList;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
    private final Entorno entorno;
    private final Menu menu;
    private final Image imgFondo, imgFondoMenu, imgBombaFuego, imgFinalFlash, imgRafagaAgua;
    private final Mago mago;
    private final Roca[] rocas;
    private HechizoVisual hechizoActivo;
    private String hechizoSeleccionado = "agua";
    private int tickActual = 0;
    private final MenuLateral menuLateralIzq, menuLateralDer;
    private final PanelHechizosLateral panelHechizosLateral;
    private final GestorMurcielagos gestorMurcielagos;
    private boolean barreraActiva = false;
    private int tickBarreraInicio = 0;
    private static final int BARRERA_DURACION = 300;
    private static final int MAX_MURCIELAGOS_VIVOS = 10;
    private static final int TOTAL_MURCIELAGOS = 60;

    public Juego() {
        try {
            this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
            this.mago = new Mago(400, 300, 2, Math.PI / 4, 30);
            this.imgFondo = Herramientas.cargarImagen("assets/fondo.png");
            this.imgFondoMenu = Herramientas.cargarImagen("assets/fondoMenu.png");
            imgRafagaAgua = Herramientas.cargarImagen("assets/rafagaDeAgua.png");
            imgBombaFuego = Herramientas.cargarImagen("assets/explosionDeFuego.png");
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
            menuLateralIzq = new MenuLateral(100, 570, Herramientas.cargarImagen("assets/menuLateral.png"));
            menuLateralDer = new MenuLateral(700, 570, Herramientas.cargarImagen("assets/menuLateral.png"));
            Image imgCuracion = Herramientas.cargarImagen("assets/curacion.png");
            Image imgBarrera = Herramientas.cargarImagen("assets/barrera.png");
            panelHechizosLateral = new PanelHechizosLateral(menuLateralIzq.getX()-40, 530, imgCuracion, imgBarrera);
            rocas = new Roca[6];
            rocas[0] = new Roca(100, 100, false);
            rocas[1] = new Roca(600, 200, false);
            rocas[2] = new Roca(350, 400, false); 
            rocas[3] = new Roca(550, 380, false);
            rocas[4] = new Roca(150, 350, false);
            rocas[5] = new Roca(450, 50, false);
            gestorMurcielagos = new GestorMurcielagos(MAX_MURCIELAGOS_VIVOS, TOTAL_MURCIELAGOS);
            this.entorno.iniciar();
        } catch (Exception e) {
            throw new RuntimeException("Error al crear Juego: fallo en la inicialización de recursos, entidades o entorno gráfico", e);
        }
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
            if (mago.getVida() <= 0) {
                Image imgGameOver = Herramientas.cargarImagen("assets/gameOver.png");
                entorno.dibujarImagen(imgGameOver, 400, 300, 0, 0.7);
                return;
            }
            if (gestorMurcielagos.getMurcielagosMatados() > TOTAL_MURCIELAGOS - 1) {
                Image imgCongrats = Herramientas.cargarImagen("assets/congratulations.png");
                entorno.dibujarImagen(imgCongrats, 400, 400, 0, 0.8);
                return;
            }
            entorno.dibujarImagen(imgFondo, 400, 300, 0, 0.8);
            entorno.dibujarImagen(imgFondoMenu, 400, 560, 0, 1.5);
            menu.dibujar(entorno, mago);
            menuLateralIzq.dibujar(entorno);
            menuLateralDer.dibujar(entorno);
            for (Roca roca : rocas) {
                roca.dibujarse(entorno);
            }
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
            gestorMurcielagos.actualizar(mago, false, entorno, barreraActiva);
            entorno.cambiarFont("Arial", 22, java.awt.Color.YELLOW);
            entorno.escribirTexto("Murciélagos: "+gestorMurcielagos.getMurcielagosMatados(), menuLateralDer.getX()-70, menuLateralDer.getY()+10);
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
                int botonFuegoX = 316, botonFuegoY = 535, botonFuegoAncho = 70, botonFuegoAlto = 70;
                int botonAguaX = 229, botonAguaY = 535, botonAguaAncho = 70, botonAguaAlto = 70;
                boolean clickEnFuego = mouseX >= botonFuegoX && mouseX <= botonFuegoX + botonFuegoAncho && mouseY >= botonFuegoY && mouseY <= botonFuegoY + botonFuegoAlto;
                boolean clickEnAgua = mouseX >= botonAguaX && mouseX <= botonAguaX + botonAguaAncho && mouseY >= botonAguaY && mouseY <= botonAguaY + botonAguaAlto;
                boolean clickEnMenuLateralIzq = mouseX >= menuLateralIzq.getX() - 60 && mouseX <= menuLateralIzq.getX() + 60 && mouseY >= menuLateralIzq.getY() - 60 && mouseY <= menuLateralIzq.getY() + 60;
                boolean clickEnMenuLateralDer = mouseX >= menuLateralDer.getX() - 60 && mouseX <= menuLateralDer.getX() + 60 && mouseY >= menuLateralDer.getY() - 60 && mouseY <= menuLateralDer.getY() + 60;
                if (clickEnFuego) {
                    hechizoSeleccionado = "fuego";
                    menu.setHechizoSeleccionado("fuego");
                } else if (clickEnAgua) {
                    hechizoSeleccionado = "agua";
                    menu.setHechizoSeleccionado("agua");
                } else if (puedeMover && !clickEnMenuLateralIzq && !clickEnMenuLateralDer && mouseY < 510) {
                    double dx = mouseX - mago.getX();
                    double dy = mouseY - mago.getY();
                    double angulo = Math.atan2(dy, dx);
                    double distancia = mago.getRadio() + 10;
                    double x = mago.getX() + Math.cos(angulo) * distancia;
                    double y = mago.getY() + Math.sin(angulo) * distancia;
                    if (hechizoSeleccionado.equals("agua")) {
                        hechizoActivo = new HechizoVisual(x, y, angulo, imgRafagaAgua, 0.2, tickActual, 20);
                    }
                    if (hechizoSeleccionado.equals("fuego")) {
                        if (mago.getMana() >= 10) { 
                            hechizoActivo = new HechizoFuego(mago, angulo, imgBombaFuego, imgFinalFlash, 0.35, tickActual, 25, 12);
                            mago.usarMana(10); 
                        }
                    }
                }
            }
            panelHechizosLateral.dibujar(entorno, barreraActiva, mago.getX(), mago.getY());
            boolean clickCuracion = panelHechizosLateral.clickCuracion(entorno.mouseX(), entorno.mouseY(), entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO));
            boolean clickBarrera = panelHechizosLateral.clickBarrera(entorno.mouseX(), entorno.mouseY(), entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO));
            boolean teclaCuracion = entorno.estaPresionada('q');
            boolean teclaBarrera = entorno.estaPresionada('e');
            if ((clickCuracion || teclaCuracion) && panelHechizosLateral.puedeCurar()) {
                panelHechizosLateral.activarCuracion(mago.getVida(), mago.getVidaMax());
            }
            if (panelHechizosLateral.estaCurando()) {
                int vidaAgregar = panelHechizosLateral.procesarCuracionTick(mago.getVida());
                if (vidaAgregar > 0) {
                    mago.recibirDaño(-vidaAgregar);
                }
            }
            if ((clickBarrera || teclaBarrera) && panelHechizosLateral.puedeBarrera() && !barreraActiva) {
                barreraActiva = true;
                tickBarreraInicio = tickActual;
                panelHechizosLateral.activarBarrera();
            }
            if (barreraActiva && tickActual - tickBarreraInicio < BARRERA_DURACION) {
                entorno.dibujarCirculo(mago.getX(), mago.getY(), mago.getRadio()+20, new java.awt.Color(0,200,255,120));
            } else if (barreraActiva) {
                barreraActiva = false;
            }
            if (hechizoActivo != null && hechizoActivo.estaActivo(tickActual)) {
                gestorMurcielagos.colisionesHechizo(hechizoActivo, mago, tickActual, panelHechizosLateral);
            }
            if (hechizoActivo != null && hechizoActivo.estaActivo(tickActual)) {
                hechizoActivo.dibujar(entorno, tickActual);
            } else {
                hechizoActivo = null;
            }
            mago.dibujarse(entorno);
            menu.dibujar(entorno, mago);
        } catch (Exception e) {
            throw new RuntimeException("Error en el ciclo de juego (tick): fallo en la lógica principal, renderizado o interacción de entidades", e);
        }
    }

    public static void main(String[] args) {
        new Juego();
    }
}