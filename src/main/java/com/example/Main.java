package com.example;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new com.example.View.SistemaManutencaoGUI().setVisible(true);
        });
    }
}