import java.util.*;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] columnas = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        String[] filas = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        
        Soldado[] Ejercito1 = new Soldado[10];
        Soldado[] Ejercito2 = new Soldado[10];

        crearSoldados(Ejercito1, 1);
        crearSoldados(Ejercito2, 2);

        boolean[][] Ocupados = new boolean[10][10];
        String[][] tablero = new String[10][10];

        posicionesEnTablero(Ejercito1, Ocupados);
        posicionesEnTablero(Ejercito2, Ocupados);
        generarTablero(tablero);

        for (int i = 0; i < 10; i++) {
            ubicarSoldado(tablero, Ejercito1[i]);
            ubicarSoldado(tablero, Ejercito2[i]);
        }
        mostrarTablero(tablero, columnas, filas);

        boolean turnoEjercito1 = true;

        while (!ejercitoVacio(Ejercito1) && !ejercitoVacio(Ejercito2)) {
            System.out.println("\nTurno de " + (turnoEjercito1 ? "Ejercito1" : "Ejercito2"));
            Soldado[] ejercitoActual = turnoEjercito1 ? Ejercito1 : Ejercito2;
            
            int ind = seleccionarSoldado(ejercitoActual, sc);
            int mov = seleccionarMovimiento(sc);

            Soldado soldado = ejercitoActual[ind];
            moverSoldado(soldado, mov, tablero, Ejercito1, Ejercito2);

            mostrarTablero(tablero, columnas, filas);

            turnoEjercito1 = !turnoEjercito1;
        }
        
        System.out.println(ejercitoVacio(Ejercito1) ? "Ejercito 2 gana!" : "Ejercito 1 gana!");
        mostrarEstadisticasFinales(Ejercito1, Ejercito2);
    }

    public static int seleccionarSoldado(Soldado[] ejercito, Scanner sc) {
        System.out.println("Seleccione el soldado que desea mover (0-9): ");
        return sc.nextInt();
    }

    public static int seleccionarMovimiento(Scanner sc) {
        System.out.println("Seleccione movimiento: \nArriba(1) \nAbajo(2) \nDerecha(3) \nIzquierda(4)");
        return sc.nextInt();
    }

    public static boolean ejercitoVacio(Soldado[] ejercito) {
        for (Soldado s : ejercito) {
            if (s != null) return false;
        }
        return true;
    }

    public static void moverSoldado(Soldado soldado, int mov, String[][] tablero, Soldado[] Ejercito1, Soldado[] Ejercito2) {
        int nuevaFila = soldado.getFila();
        int nuevaCol = soldado.getColumna();

        switch (mov) {
            case 1 -> nuevaFila--; // Arriba
            case 2 -> nuevaFila++; // Abajo
            case 3 -> nuevaCol++; // Derecha
            case 4 -> nuevaCol--; // Izquierda
        }

        // Comprobar límites del tablero
        if (nuevaFila < 0 || nuevaFila >= tablero.length || nuevaCol < 0 || nuevaCol >= tablero[0].length) {
            System.out.println("Movimiento fuera del tablero.");
            return;
        }

        // Verificar si hay un soldado enemigo en la nueva posición
        Soldado oponente = obtenerSoldadoEnPosicion(nuevaFila, nuevaCol, Ejercito1, Ejercito2);
        
        if (oponente != null) {
            // Si hay un soldado enemigo, se realiza la batalla en la nueva posición
            if (oponente.getId().charAt(1) != soldado.getId().charAt(1)) {
                batalla(soldado, oponente, tablero, Ejercito1, Ejercito2);
                return; // Detener el movimiento aquí, ya que la batalla resuelve la posición
            } else {
                System.out.println("Posición ocupada por un soldado del mismo ejército.");
                return;
            }
        }

        // Si no hay soldado enemigo, se mueve el soldado
        tablero[soldado.getFila()][soldado.getColumna()] = "_"; // Limpia la posición original
        soldado.setFila(nuevaFila);
        soldado.setColumna(nuevaCol);
        tablero[nuevaFila][nuevaCol] = soldado.getId(); // Ubica al soldado en la nueva posición
    }


    public static Soldado obtenerSoldadoEnPosicion(int fila, int columna, Soldado[] Ejercito1, Soldado[] Ejercito2) {
        for (Soldado s : Ejercito1) {
            if (s != null && s.getFila() == fila && s.getColumna() == columna) return s;
        }
        for (Soldado s : Ejercito2) {
            if (s != null && s.getFila() == fila && s.getColumna() == columna) return s;
        }
        return null;
    }

    public static void batalla(Soldado soldado1, Soldado soldado2, String[][] tablero, Soldado[] Ejercito1, Soldado[] Ejercito2) {
        Soldado ganador, perdedor;

        // Determinar el ganador y el perdedor basados en el nivel de vida
        if (soldado1.getNivelVida() >= soldado2.getNivelVida()) {
            ganador = soldado1;
            perdedor = soldado2;
        } else {
            ganador = soldado2;
            perdedor = soldado1;
        }

        System.out.println("Batalla entre " + soldado1.getId() + " y " + soldado2.getId());
        System.out.println("Ganador: " + ganador.getId());

        // Incrementar la vida del ganador (opcional)
        ganador.setNivelVida(ganador.getNivelVida() + 1);

        // Actualizar el tablero:
        if (ganador == soldado1) {
            // Si el atacante gana, limpia su posición original y ocupa la posición del defensor
            tablero[ganador.getFila()][ganador.getColumna()] = "_";
            tablero[perdedor.getFila()][perdedor.getColumna()] = ganador.getId();
            ganador.setFila(perdedor.getFila());
            ganador.setColumna(perdedor.getColumna());
        } else {
            // Si el defensor gana, el atacante simplemente desaparece
            tablero[soldado1.getFila()][soldado1.getColumna()] = "_";
        }

        // Eliminar al soldado perdedor del ejército
        eliminarSoldado(perdedor, Ejercito1, Ejercito2);
    }
    

    public static void eliminarSoldado(Soldado soldado, Soldado[] Ejercito1, Soldado[] Ejercito2) {
        for (int i = 0; i < Ejercito1.length; i++) {
            if (Ejercito1[i] == soldado) {
                Ejercito1[i] = null;
                return;
            }
        }
        for (int i = 0; i < Ejercito2.length; i++) {
            if (Ejercito2[i] == soldado) {
                Ejercito2[i] = null;
                return;
            }
        }
    }

    public static void crearSoldados(Soldado[] Ejercito, int n) {
        for (int i = 0; i < Ejercito.length; i++) {
            Ejercito[i] = new Soldado("Soldado" + i + "X" + n, (int) (Math.random() * 5 + 1));
            Ejercito[i].setNivelAtaque((int) (Math.random() * 5 + 1));
            Ejercito[i].setNivelDefensa((int) (Math.random() * 5 + 1));
            Ejercito[i].setId("E" + n + "-s" + i);
        }
    }

    public static void posicionesEnTablero(Soldado[] Ejercito, boolean[][] ocupados) {
        for (Soldado soldado : Ejercito) {
            int fila, col;
            do {
                fila = (int) (Math.random() * 10);
                col = (int) (Math.random() * 10);
            } while (ocupados[fila][col]);
            
            soldado.setFila(fila);
            soldado.setColumna(col);
            ocupados[fila][col] = true;
        }
    }

    public static void generarTablero(String[][] tablero) {
        for (String[] fila : tablero) {
            Arrays.fill(fila, "_");
        }
    }

    public static void mostrarTablero(String[][] tablero, String[] columnas, String[] filas) {
        System.out.print("*\t");
        for (String col : columnas) {
            System.out.print(col + "\t");
        }
        System.out.println();
        for (int i = 0; i < tablero.length; i++) {
            System.out.print(filas[i] + "\t");
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print(tablero[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void ubicarSoldado(String[][] tablero, Soldado soldado) {
        tablero[soldado.getFila()][soldado.getColumna()] = soldado.getId();
    }

    public static void mostrarEstadisticasFinales(Soldado[] Ejercito1, Soldado[] Ejercito2) {
        System.out.println("Ranking del Ejército 1:");
        ordenarPorNivelVidaBurbuja(Ejercito1);
        for (Soldado s : Ejercito1) {
            if (s != null) System.out.println(s.getId() + " - Vida: " + s.getNivelVida());
        }
        System.out.println("Promedio de vida del Ejército 1: " + promedioVida(Ejercito1));

        System.out.println("Ranking del Ejército 2:");
        ordenarPorNivelVidaSort(Ejercito2);
        for (Soldado s : Ejercito2) {
            if (s != null) System.out.println(s.getId() + " - Vida: " + s.getNivelVida());
        }
        System.out.println("Promedio de vida del Ejército 2: " + promedioVida(Ejercito2));
    }

    public static void ordenarPorNivelVidaBurbuja(Soldado[] ejercito) {
        for (int i = 0; i < ejercito.length - 1; i++) {
            for (int j = 0; j < ejercito.length - i - 1; j++) {
                if (ejercito[j] != null && ejercito[j + 1] != null &&
                    ejercito[j].getNivelVida() < ejercito[j + 1].getNivelVida()) {
                    Soldado temp = ejercito[j];
                    ejercito[j] = ejercito[j + 1];
                    ejercito[j + 1] = temp;
                }
            }
        }
    }

    public static void ordenarPorNivelVidaSort(Soldado[] ejercito) {
        Arrays.sort(ejercito, (s1, s2) -> {
            if (s1 == null || s2 == null) return 0;
            return Integer.compare(s2.getNivelVida(), s1.getNivelVida());
        });
    }

    public static double promedioVida(Soldado[] ejercito) {
        int sumaVida = 0, count = 0;
        for (Soldado s : ejercito) {
            if (s != null) {
                sumaVida += s.getNivelVida();
                count++;
            }
        }
        return count == 0 ? 0 : (double) sumaVida / count;
    }
}

