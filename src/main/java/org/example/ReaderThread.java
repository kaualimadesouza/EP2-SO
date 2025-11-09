package org.example;

import java.util.Random;

public class ReaderThread extends Thread {
    private BaseDados baseDados;

    public ReaderThread(BaseDados baseDados, tamanhoBase) {
        this.baseDados = baseDados;
    }

    @Override
    public void run() {
        this.baseDados.entrarEscrita();

        try {
            Random numeroAleatorio = new Random();
            for (int i = 0; i < 100; i++) {
                int posicaoAleatoriaLer = numeroAleatorio.nextInt(1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.baseDados.sairEscrita();
        }
    }
}
