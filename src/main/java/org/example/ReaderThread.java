package org.example;

import java.util.Random;

/**
 * Thread de leitura que implementa o padrão Reader no problema Readers-Writers.
 * Executa 100 acessos aleatórios de leitura à base de dados compartilhada,
 * seguido por um sleep de 1ms, tudo dentro da região crítica.
 */
public class ReaderThread extends Thread {
    private BaseDados baseDados;
    private String palavraAtualLida;
    private Boolean ehImplementacaoReaderAndWriters;

    /**
     * Construtor da classe ReaderThread.
     */
    public ReaderThread(BaseDados baseDados, Boolean ehImplementacaoReaderAndWriters) {
        this.baseDados = baseDados;
        this.palavraAtualLida = "";
        this.ehImplementacaoReaderAndWriters = ehImplementacaoReaderAndWriters;
    }

    /**
     * Executa a thread de leitura.
     */
    @Override
    public void run() {
        // Entrar na seção crítica de leitura dependendo da implementação escolhida
        if (ehImplementacaoReaderAndWriters) {
            this.baseDados.entrarLeituraReadersAndWriters();
        }
        else {
            this.baseDados.entrarRegiaoCriticaSemReadersAndWriters();
        }

        try {
            Random numeroAleatorio = new Random();

            // Realizar exatamente 100 leituras aleatórias na base de dados
            for (int i = 0; i < 100; i++) {
                int posicaoAleatoriaLer = numeroAleatorio.nextInt(baseDados.getTamanhoBase());
                String palavraLida = baseDados.read(posicaoAleatoriaLer);
                this.setPalavraAtualLida(palavraLida);
            }

            // Sleep por 1ms após os 100 acessos
            Thread.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Sair da seção crítica de leitura independentemente de sucesso ou falha
            if (ehImplementacaoReaderAndWriters) {
                this.baseDados.sairLeituraReadersAndWriters();
            }
            else {
                this.baseDados.sairRegiaoCriticaSemReadersAndWriters();
            }
        }
    }

    /**
     * Obtém a última palavra lida pela thread.
     */
    public String getPalavraAtualLida() {
        return palavraAtualLida;
    }

    /**
     * Define a palavra atualmente lida pela thread.
     */
    public void setPalavraAtualLida(String palavraAtualLida) {
        this.palavraAtualLida = palavraAtualLida;
    }
}
