# Juego de Combate entre Soldados

Este proyecto es un juego de combate basado en Java donde dos ejércitos de soldados se enfrentan en un tablero de 10x10. Cada soldado tiene un nivel de vida y puede moverse en distintas direcciones. Cuando dos soldados enemigos coinciden en la misma casilla, ocurre una batalla y el ganador ocupa la casilla del perdedor. El juego continúa hasta que uno de los ejércitos se queda sin soldados.

## Características

- **Tablero**: Representado por una matriz de 10x10.
- **Ejércitos**: Dos ejércitos (Ejército 1 y Ejército 2) con 10 soldados cada uno.
- **Movimiento**: Los soldados se pueden mover en las direcciones arriba, abajo, izquierda y derecha.
- **Batalla**: Si un soldado intenta moverse a una casilla ocupada por un enemigo, se produce una batalla. El soldado con mayor nivel de vida gana y ocupa la casilla del perdedor. Si el atacante pierde, simplemente desaparece del tablero.
- **Fin del juego**: El juego termina cuando uno de los ejércitos no tiene más soldados en el tablero.

## Estructura de Código

### Clases Principales

- **App**: Contiene el método `main` para iniciar el juego. Gestiona el flujo principal y alterna los turnos entre los ejércitos.
- **Soldado**: Representa un soldado con propiedades de nivel de vida, ataque, defensa, posición en el tablero y un identificador único.

### Métodos Clave

- **moverSoldado**: Controla el movimiento de un soldado. Verifica si el movimiento está dentro de los límites y maneja el combate si la casilla de destino está ocupada por un enemigo.
- **batalla**: Determina el ganador en caso de que dos soldados enemigos coincidan en una casilla. El ganador ocupa la casilla del perdedor si es el atacante; si pierde, simplemente desaparece.
- **eliminarSoldado**: Elimina un soldado del ejército después de una batalla perdida.
- **mostrarTablero**: Muestra el estado actual del tablero en la consola.
- **crearSoldados**: Inicializa los soldados de cada ejército con posiciones y atributos aleatorios.

## Ejecución

1. **Compilación**:
   Asegúrate de tener configurado un entorno Java. Compila el archivo principal `App.java` con el siguiente comando:

   ```bash
   javac App.java
