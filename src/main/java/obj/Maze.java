package obj;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.*;
import java.util.ArrayList;

public class Maze {

    private PropertyChangeSupport observed;
    private int[][] matriz;
    private final int tamanoMatrizPorDefecto = 10;
    private int inicioX;
    private int inicioY;
    private int finX;
    private int finY;
    private boolean matrizCargada;
    private final int[][] matrizPorDefecto = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 1, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 1, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 1, 1, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
            {1, 0, 1, 0, 0, 0, 1, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 1, 1, 0, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };
    private boolean noExisteSolucion;

    public Maze() {
        observed = new PropertyChangeSupport(this);
        noExisteSolucion = true;
        inicioX = -1;
        inicioY = -1;
        finX = -1;
        finY = -1;
        matrizCargada = false;
    }

    public void addObserver(PropertyChangeListener listener) {
        observed.addPropertyChangeListener(listener);
    }

    public void cargarMatriz(File file) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String lineaLeida = "";
        ArrayList<String> lineas = new ArrayList<>();
        int numLinea = 0;
        while(br.ready()) {
            lineaLeida = br.readLine();
            numLinea++;
            if (validarLinea(lineaLeida)) {
                lineas.add(lineaLeida);
            } else {
                throw new IllegalArgumentException("La matriz no es valida en la linea " + numLinea);
            }
        }

        matriz = new int[lineas.size()][lineas.get(0).length()];
        int i = 0;
        for (String linea: lineas) {
            int j = 0;
            for (char c: linea.toCharArray()){
                matriz[i][j] = Integer.parseInt(String.valueOf(c));
                j++;
            }
            i++;
        }

        matrizCargada = true;
        observed.firePropertyChange("MATRIZ", true, false);
    }

    public void caminoMasCortoBacktracking() {
        int[][] camino = new int[matriz.length][matriz[0].length];
        if (caminoMasCortoBacktracking(inicioX, inicioY, camino) == false) {
            noExisteSolucion = true;
        } else {
            noExisteSolucion = false;
        }
    }

    private boolean caminoMasCortoBacktracking(int i, int j, int[][] solucion) {
        if (i == finX && j == finY) {
            solucion[i][j] = 1;
            esperarYDispararEvento(solucion);
            return true;
        }
        if (esValido(i, j, solucion)) {
            solucion[i][j] = 1;
            esperarYDispararEvento(solucion);
            if (caminoMasCortoBacktracking(i + 1, j, solucion)) {
                return true;
            }
            if (caminoMasCortoBacktracking(i, j + 1, solucion)) {
                return true;
            }
            if (caminoMasCortoBacktracking(i - 1, j, solucion)) {
                return true;
            }
            if (caminoMasCortoBacktracking(i, j - 1, solucion)) {
                return true;
            }
            solucion[i][j] = 0;
            esperarYDispararEvento(solucion);
            return false;
        }
        return false;
    }

    private void esperarYDispararEvento(int[][] camino) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        observed.firePropertyChange("CAMINO", matriz, camino);
    }

    private boolean esValido(int i, int j, int[][] solucion) {
        int ancho = matriz.length;
        int alto = matriz[0].length;
        if (i < 0 || i >= ancho ||
                j < 0 || j >= alto) {
            return false;
        }
        if (matriz[i][j] == 1) {
            return false;
        }
        if (solucion[i][j] == 1) {
            return false;
        }
        return true;
    }

    private boolean validarLinea(String linea) {
        return true;
    }

    public void reset() {
        matriz = new int[tamanoMatrizPorDefecto][tamanoMatrizPorDefecto];
        int i = 0;
        for (int[] fila : matrizPorDefecto) {
            int j = 0;
            for (int celda : fila) {
                matriz[i][j] = celda;
                j++;
            }
            i++;
        }
        matrizCargada = true;
        observed.firePropertyChange("MATRIZ", true, false);
    }

    public boolean isMatrizCargada() {
        return matrizCargada;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public int getInicioX() {
        return inicioX;
    }

    public void setInicioX(int inicioX) {
        this.inicioX = inicioX;
    }

    public int getInicioY() {
        return inicioY;
    }

    public void setInicioY(int inicioY) {
        this.inicioY = inicioY;
    }

    public int getFinX() {
        return finX;
    }

    public void setFinX(int finX) {
        this.finX = finX;
    }

    public int getFinY() {
        return finY;
    }

    public void setFinY(int finY) {
        this.finY = finY;
    }
}
