package gui;

import obj.Maze;

import java.awt.*;

public class DibujoMaze {
    public final static int ANCHO_PARED = 20;
    public final static int X0 = 50;
    public final static int Y0 = 50;
    private final int anchoCamino = 14;
    private Maze source;
    private int[][] camino;

    public DibujoMaze(Maze source, int[][] camino) {
        this.source = source;
        this.camino = camino;
    }

    public void dibujar(Graphics g) {
        int[][] matriz = source.getMatriz();
        int ancho = matriz.length;
        int alto = matriz[0].length;
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                if (matriz[i][j] == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(X0 + i * ANCHO_PARED, Y0 + j * ANCHO_PARED, ANCHO_PARED, ANCHO_PARED);
            }
        }
        if (camino != null) {
            dibujarCamino(g, ancho, alto, X0, Y0);
        }

        if (source.getInicioX() >= 0 && source.getInicioY() >= 0) {
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("I",X0 + source.getInicioX() * ANCHO_PARED + ANCHO_PARED / 2 - anchoCamino / 2,
                Y0 + (source.getInicioY() + 1) * ANCHO_PARED - 2);
        }

        if (source.getFinX() >= 0 && source.getFinY() >= 0) {
            g.setColor(Color.RED);
            g.drawString("F",X0 + source.getFinX() * ANCHO_PARED + ANCHO_PARED / 2 - anchoCamino / 2,
                Y0 + (source.getFinY() + 1) * ANCHO_PARED - 2);
        }
    }

    private void dibujarCamino(Graphics g, int ancho, int alto, int x0, int y0) {
        for (int i = 0; i < ancho; i++) {
            for (int j = 0; j < alto; j++) {
                if (camino[i][j] > 0) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x0 + i * ANCHO_PARED + ANCHO_PARED / 2 - anchoCamino / 2,
                            y0 + j * ANCHO_PARED + ANCHO_PARED / 2 - anchoCamino / 2,
                            anchoCamino,
                            anchoCamino);
                }
            }
        }
    }
}
