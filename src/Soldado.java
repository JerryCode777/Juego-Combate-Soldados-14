public class Soldado {
    private String id;
    private int nivelVida;
    private int nivelAtaque;
    private int nivelDefensa;
    private int fila;
    private int columna;

    public Soldado(){

    }
    
    public Soldado(String id, int nivelVida) {
        this.id = id;
        this.nivelVida = nivelVida;
    }
    public Soldado(String id, int nivelVida, int nivelAtaque) {
        this.id = id;
        this.nivelVida = nivelVida;
        this.nivelAtaque = nivelAtaque;
    }

    public String getId() {
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public int getNivelVida() {
        return nivelVida;
    }

    public void setNivelVida(int nivelVida) {
        this.nivelVida = nivelVida;
    }

    public int getNivelAtaque() {
        return nivelAtaque;
    }

    public void setNivelAtaque(int nivelAtaque) {
        this.nivelAtaque = nivelAtaque;
    }

    public int getNivelDefensa() {
        return nivelDefensa;
    }

    public void setNivelDefensa(int nivelDefensa) {
        this.nivelDefensa = nivelDefensa;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}