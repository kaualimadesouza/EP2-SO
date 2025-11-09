package org.example;

import java.util.Random;

public class ReaderThread extends Thread {
    private BaseDados baseDados;
    private String palavraAtualLida;

    public ReaderThread(BaseDados baseDados) {
        this.baseDados = baseDados;
        this.palavraAtualLida = "";
    }

    @Override
    public void run() {
        // Entrar na seção crítica de leitura
        this.baseDados.entrarLeitura();

        try {
            Random numeroAleatorio = new Random();

            // Realizar leituras aleatórias na base de dados
            for (int i = 0; i < 100; i++) {
                int posicaoAleatoriaLer = numeroAleatorio.nextInt(baseDados.getTamanhoBase());
                String palavraLida = baseDados.read(posicaoAleatoriaLer);
                this.setPalavraAtualLida(palavraLida);
                System.out.println("Leitor leu a palavra: " + palavraLida + " na posição: " + posicaoAleatoriaLer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Sair da seção crítica de escrita independentemente de sucesso ou falha
            this.baseDados.sairLeitura();
        }
    }

    public String getPalavraAtualLida() {
        return palavraAtualLida;
    }

    public void setPalavraAtualLida(String palavraAtualLida) {
        this.palavraAtualLida = palavraAtualLida;
    }
}
