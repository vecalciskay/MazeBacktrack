package gui;

import obj.Maze;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MazeBtPanel extends JPanel implements PropertyChangeListener,
        MouseListener {

    private final static Logger logger = LogManager.getRootLogger();
    private Maze modelo;
    private int[][] camino;

    public MazeBtPanel() {
        super();
        setPreferredSize(new Dimension(500, 500));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (modelo != null) {
            DibujoMaze dibujo = new DibujoMaze(modelo, camino);
            dibujo.dibujar(g);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        modelo = (Maze) propertyChangeEvent.getSource();
        if (propertyChangeEvent.getPropertyName().equals("CAMINO")) {
            camino = (int[][]) propertyChangeEvent.getNewValue();
        } else {
            camino = null;
        }
        repaint();
    }

    public void habilitarInicioYFin() {
        if (modelo != null && modelo.isMatrizCargada()) {
            logger.info("Habilitando el mouse listener para panel para colocar inicio y fin");
            addMouseListener(this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();
        logger.info("Mouse released en x: " + x + " y: " + y);
        int i = (x - DibujoMaze.X0) / DibujoMaze.ANCHO_PARED;
        int j = (y - DibujoMaze.Y0) / DibujoMaze.ANCHO_PARED;
        logger.info("Clic en Fila: " + i + " Columna: " + j);

        if (modelo.getMatriz()[i][j] == 1) {
            logger.info("No se puede colocar inicio o fin en pared");
            JOptionPane.showMessageDialog(this, "No se puede colocar inicio o fin en pared");
            return;
        }

        // Determinamos si es inicio o fin
        if (modelo.getInicioX() < 0) {
            modelo.setInicioX(i);
            modelo.setInicioY(j);
            logger.info("Se coloca el inicio en Fila: " + i + " Columna: " + j);
        } else {
            if (modelo.getInicioX() == i && modelo.getInicioY() == j) {
                logger.info("No se puede colocar fin en el mismo lugar que inicio");
                JOptionPane.showMessageDialog(this, "No se puede colocar fin en el mismo lugar que inicio");
                return;
            }
            modelo.setFinX(i);
            modelo.setFinY(j);
            logger.info("Se coloca el fin en Fila: " + i + " Columna: " + j);
            this.removeMouseListener(this);
            logger.info("Ya no se puede colocar inicio y fin");
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
