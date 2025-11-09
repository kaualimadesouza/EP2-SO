package org.example;

import java.util.Random;

public class WriterThread extends Thread{
    private BaseDados baseDados;

    public WriterThread(BaseDados baseDados) {
        this.baseDados = baseDados;
    }

    @Override
    public void run() {
        // Entrar na seção crítica de escrita
        this.baseDados.entrarEscrita();

        try {
            Random numeroAleatorio = new Random();

            // Realizar exatamente 100 escritas aleatórias na base de dados
            for (int i = 0; i < 100; i++) {
                int posicaoAleatoriaEscrever = numeroAleatorio.nextInt(baseDados.getTamanhoBase());
                System.out.println("Escritor escreveu 'MODIFICADO' na posição: " + posicaoAleatoriaEscrever);
                baseDados.write(posicaoAleatoriaEscrever, "MODIFICADO");
            }

            // Sleep por 1ms após os 100 acessos (ainda dentro da região crítica)
            Thread.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Sair da seção crítica de escrita independentemente de sucesso ou falha
            this.baseDados.sairEscrita();
        }
    }
}
