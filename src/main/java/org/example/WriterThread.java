package org.example;

import java.util.Random;

/**
 * Thread de escrita que implementa o padrão Writer no problema Readers-Writers.
 * Executa 100 acessos aleatórios de escrita à base de dados compartilhada,
 * escrevendo "MODIFICADO" em posições aleatórias, seguido por um sleep de 1ms,
 * tudo dentro da região crítica.
 */
public class WriterThread extends Thread{
    private BaseDados baseDados;
    private Boolean ehImplementacaoReaderAndWriters;

    /**
     * Construtor da classe WriterThread.
     */
    public WriterThread(BaseDados baseDados, Boolean ehImplementacaoReaderAndWriters) {
        this.baseDados = baseDados;
        this.ehImplementacaoReaderAndWriters = ehImplementacaoReaderAndWriters;
    }

    /**
     * Executa a thread de escrita.
     */
    @Override
    public void run() {
        if (ehImplementacaoReaderAndWriters) {
            // Entrar na seção crítica de escrita dependendo da implementação escolhida
            if (ehImplementacaoReaderAndWriters) {
                this.baseDados.entrarEscritaReadersAndWriters();
            }
            else {
                this.baseDados.entrarRegiaoCriticaSemReadersAndWriters();
            }


            try {
                Random numeroAleatorio = new Random();

                // Realizar exatamente 100 escritas aleatórias na base de dados
                for (int i = 0; i < 100; i++) {
                    int posicaoAleatoriaEscrever = numeroAleatorio.nextInt(baseDados.getTamanhoBase());
                    baseDados.write(posicaoAleatoriaEscrever, "MODIFICADO");
                }

                // Sleep por 1ms após os 100 acessos (ainda dentro da região crítica)
                Thread.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Sair da seção crítica de escrita independentemente de sucesso ou falha
                System.out.println("Thread " + Thread.currentThread().getId() + " terminou escrita.");

                if (ehImplementacaoReaderAndWriters) {
                    this.baseDados.sairEscritaReadersAndWriters();
                }
                else {
                    this.baseDados.sairRegiaoCriticaSemReadersAndWriters();
                }
            }
        }
    }
}
