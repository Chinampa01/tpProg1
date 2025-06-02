package juego;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import entorno.Entorno;

public class GestorMurcielagos {
    private final Murcielago[] murcielagos;
    private int murcielagosMatados;
    private int murcielagosRestantes;
    private final int maxVivos;
    private final Random random;
    private int totalGolbatsEnPantalla = 0;
    private final ArrayList<PocionMana> pociones = new ArrayList<>();
    private static final double PROB_POCION = 0.20;

    public GestorMurcielagos(int maxVivos) {
        try {
            this.maxVivos = maxVivos;
            this.murcielagosMatados = 0;
            this.murcielagosRestantes = maxVivos;
            this.murcielagos = new Murcielago[maxVivos];
            this.random = new Random();
            for (int i = 0; i < maxVivos && murcielagosRestantes > 0; i++) {
                murcielagos[i] = crearMurcielagoFueraPantalla(false);
                murcielagosRestantes--;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al crear GestorMurcielagos: fallo en la inicialización de murciélagos", e);
        }
    }

    public GestorMurcielagos(int maxVivos, int totalMurcielagos) {
        this.maxVivos = maxVivos;
        this.murcielagosMatados = 0;
        this.murcielagosRestantes = totalMurcielagos;
        this.murcielagos = new Murcielago[maxVivos];
        this.random = new Random();
        for (int i = 0; i < maxVivos && murcielagosRestantes > 0; i++) {
            murcielagos[i] = crearMurcielagoFueraPantalla(false);
            murcielagosRestantes--;
        }
    }

    public void actualizar(Mago mago, boolean simularMuerte, Entorno entorno, boolean barreraActiva) {
        for (int i = 0; i < murcielagos.length; i++) {
            Murcielago m = murcielagos[i];
            if (m == null) continue;
            m.cambiarAngulo(mago.getX(), mago.getY());
            m.mover();
            m.dibujarse(entorno);
            int radioColision = m.esGolbat() ? 30 : 20;
            int dano = m.esGolbat() ? 2 : 1;
            if (!barreraActiva && colision(mago.getX(), mago.getY(), m.x, m.y, radioColision)) {
                mago.recibirDaño(dano);
                murcielagos[i] = null;
            } else if (simularMuerte || m.y < -30 || m.y > 630 || m.x < -30 || m.x > 830 || (m.esGolbat() && m.estaMuerto())) {
                if (m.esGolbat() && m.estaMuerto()) {
                    if (random.nextDouble() < PROB_POCION) {
                        pociones.add(new PocionMana(m.x, m.y));
                    }
                }
                murcielagos[i] = null;
                murcielagosMatados++;
            }
        }
        // Reponer murciélagos
        for (int i = 0; i < murcielagos.length; i++) {
            if (murcielagos[i] == null) {
                boolean esGolbat = murcielagosMatados >= 25;
                murcielagos[i] = crearMurcielagoFueraPantalla(esGolbat);
            }
        }
        dibujarYRecolectarPociones(mago, entorno);
    }

    private void dibujarYRecolectarPociones(Mago mago, Entorno entorno) {
        List<PocionMana> recogidas = new ArrayList<>();
        for (PocionMana p : pociones) {
            if (p.estaActiva()) {
                p.dibujar(entorno);
                // Solo permite recoger la poción si el mago no tiene el maná al máximo
                if (mago.getMana() < mago.getManaMax() && p.colisionaCon(mago.getX(), mago.getY(), mago.getRadio())) {
                    mago.usarMana(-p.getManaRestaurado()); // restaura maná
                    p.recoger();
                    recogidas.add(p);
                }
            }
        }
        pociones.removeAll(recogidas);
    }

    public int getMurcielagosMatados() { return murcielagosMatados; }
    public int getMurcielagosRestantes() { return murcielagosRestantes; }
    public int getCantidadVivos() {
        int vivos = 0;
        for (Murcielago m : murcielagos) if (m != null) vivos++;
        return vivos;
    }
    public Murcielago[] getMurcielagos() { return murcielagos; }

    public void incrementarMatados() {
        murcielagosMatados++;
    }

    public void colisionesHechizo(HechizoVisual hechizoActivo, Mago mago, int tickActual, PanelHechizosLateral panelHechizosLateral) {
        double radioHechizo = 0;
        double xHechizo = 0;
        double yHechizo = 0;
        if (hechizoActivo instanceof HechizoFuego) {
            HechizoFuego hf = (HechizoFuego) hechizoActivo;
            boolean esExplosion = tickActual - hf.tickCreacion < hf.getTicksExplosion();
            if (esExplosion) {
                xHechizo = mago.getX();
                yHechizo = mago.getY();
                radioHechizo = 130;
            } else {
                xHechizo = hf.getXFinal();
                yHechizo = hf.getYFinal();
                radioHechizo = 80;
            }
        } else if (hechizoActivo != null) {
            xHechizo = hechizoActivo.x;
            yHechizo = hechizoActivo.y;
            radioHechizo = 40;
        }
        for (int i = 0; i < murcielagos.length; i++) {
            Murcielago m = murcielagos[i];
            if (m == null) continue;
            double dx = m.x - xHechizo;
            double dy = m.y - yHechizo;
            double dist = Math.sqrt(dx*dx + dy*dy);
            boolean esFuego = hechizoActivo instanceof HechizoFuego;
            boolean esRafagaAgua = !(hechizoActivo instanceof HechizoFuego);
            if (dist < radioHechizo && !m.estaMuerto()) {
                m.recibirGolpe(esRafagaAgua, esFuego);
                if (m.estaMuerto()) {
                    if (m.esGolbat()) {
                        if (random.nextDouble() < PROB_POCION) {
                            pociones.add(new PocionMana(m.x, m.y));
                        }
                    }
                    murcielagos[i] = null;
                    murcielagosMatados++;
                    panelHechizosLateral.notificarMurcielagoMatado();
                }
            }
        }
    }

    private boolean colision(double x1, double y1, double x2, double y2, int radio) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx*dx + dy*dy) < radio;
    }

    private Murcielago crearMurcielagoFueraPantalla(boolean esGolbat) {
        int borde = random.nextInt(4);
        int x = 0, y = 0;
        switch (borde) {
            case 0: x = -30; y = random.nextInt(600); break;
            case 1: x = 830; y = random.nextInt(600); break;
            case 2: x = random.nextInt(800); y = -30; break;
            case 3: x = random.nextInt(800); y = 630; break;
        }
        return new Murcielago(x, y, esGolbat);
    }
}
