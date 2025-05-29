package juego;

import java.util.ArrayList;
import java.util.Random;
import entorno.Entorno;

public class GestorMurcielagos {
    private final ArrayList<Murcielago> murcielagos;
    private int murcielagosMatados;
    private int murcielagosRestantes;
    private final int maxVivos;
    private final int total;
    private final Random random;

    public GestorMurcielagos(int maxVivos, int total) {
        this.maxVivos = maxVivos;
        this.total = total;
        this.murcielagosMatados = 0;
        this.murcielagosRestantes = total;
        this.murcielagos = new ArrayList<>();
        this.random = new Random();
        for (int i = 0; i < maxVivos && murcielagosRestantes > 0; i++) {
            murcielagos.add(crearMurcielagoFueraPantalla());
            murcielagosRestantes--;
        }
    }

    public void actualizar(Mago mago, boolean simularMuerte, Entorno entorno, boolean barreraActiva) {
        ArrayList<Murcielago> muertos = new ArrayList<>();
        for (Murcielago m : murcielagos) {
            m.cambiarAngulo(mago.getX(), mago.getY());
            m.mover();
            m.dibujarse(entorno);
            if (!barreraActiva && colision(mago.getX(), mago.getY(), m.x, m.y, 25)) {
                mago.recibirDaño(1);
            }
            if (simularMuerte || m.y < -30 || m.y > 630 || m.x < -30 || m.x > 830) {
                muertos.add(m);
            }
        }
        for (Murcielago m : muertos) {
            murcielagos.remove(m);
            murcielagosMatados++;
        }
        while (murcielagos.size() < maxVivos && murcielagosRestantes > 0) {
            murcielagos.add(crearMurcielagoFueraPantalla());
            murcielagosRestantes--;
        }
    }

    public int getMurcielagosMatados() { return murcielagosMatados; }
    public int getMurcielagosRestantes() { return murcielagosRestantes; }
    public int getCantidadVivos() { return murcielagos.size(); }
    public ArrayList<Murcielago> getMurcielagos() { return murcielagos; }

    private boolean colision(int x1, int y1, double x2, double y2, int distancia) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) < distancia * distancia;
    }

    private Murcielago crearMurcielagoFueraPantalla() {
        int borde = random.nextInt(4);
        int x = 0, y = 0;
        switch (borde) {
            case 0:
                x = random.nextInt(800);
                y = -20;
                break;
            case 1:
                x = random.nextInt(800);
                y = 470 + random.nextInt(80);
                break;
            case 2:
                x = -20;
                y = random.nextInt(500);
                break;
            case 3:
                x = 820;
                y = random.nextInt(500);
                break;
        }
        return new Murcielago(x, y);
    }
}
