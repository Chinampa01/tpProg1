package juego;

public class Hechizo {
    private String nombre;
    private int dano;
    private int usoMana;

    public Hechizo(String nombre, int dano, int usoMana) {
        this.nombre = nombre;
        this.dano = dano;
        this.usoMana = usoMana;
    }

    public String getNombre() { return nombre; }
    public int getDano() { return dano; }
    public int getUsoMana() { return usoMana; }
}