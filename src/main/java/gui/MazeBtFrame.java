package gui;

import obj.Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MazeBtFrame extends JFrame {

    private Maze modelo;
    private MazeBtPanel panel;

    public MazeBtFrame() {
        super("Maze");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        init();

        pack();
        setVisible(true);
    }

    private void init() {
        JMenuBar bar = new JMenuBar();

        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem mi = new JMenuItem("Cargar matriz");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                itemCargarMatriz();
            }
        });
        fileMenu.add(mi);

        mi = new JMenuItem("Colocar inicio y fin");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                itemColocarInicioYFin();
            }
        });
        fileMenu.add(mi);

        mi = new JMenuItem("Buscar camino");
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                itemBuscarCamino();
            }
        });
        fileMenu.add(mi);

        bar.add(fileMenu);
        setJMenuBar(bar);

        panel = new MazeBtPanel();
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);

        modelo = new Maze();
        modelo.addObserver(panel);
        modelo.reset();
    }

    private void itemBuscarCamino() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                modelo.caminoMasCortoBacktracking();
            }
        });
        t.start();
    }

    private void itemColocarInicioYFin() {
        panel.habilitarInicioYFin();
    }

    private void itemCargarMatriz() {
        JFileChooser fc = new JFileChooser("./data");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setMultiSelectionEnabled(false);
        fc.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(java.io.File file) {
                return file.isDirectory() || file.getName().endsWith(".txt");
            }

            @Override
            public String getDescription() {
                return "Archivos de texto";
            }
        });

        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fc.getSelectedFile();
            try {
                modelo.cargarMatriz(file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar el archivo: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        new MazeBtFrame();
    }
}
