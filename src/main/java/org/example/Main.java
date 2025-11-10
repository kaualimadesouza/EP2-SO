package org.example;

import java.io.*;
import java.util.*;


public class Main {
    public static final int TAMANHO_ARRANJO_THREADS = 100;

    /**
     * Inicializa e distribui aleatoriamente 100 threads no arranjo.
     * Cria ReaderThreads e WriterThreads de forma aleatória e os posiciona em posições aleatórias do arranjo de threads.
     */
    public static List<Thread> inicializarThreads(BaseDados baseDados, Boolean ehImplementacaoReaderAndWriters, int numReaders, int numWriters) {
        List<Thread> threads = new ArrayList<Thread>();

        // Inicializar posicoes vazias
        HashMap<Integer, Boolean> posicoesValidas = new HashMap<Integer, Boolean>();
        for (int i = 0; i < TAMANHO_ARRANJO_THREADS; i++) {
            threads.add(null);
            posicoesValidas.put(i, true);
        }

        int contReaders = 0;
        int contWriters = 0;

        for (int i = 0; i < TAMANHO_ARRANJO_THREADS; i++) {
            Random numeroAleatorio = new Random();

            Thread thread = null;
            if (contReaders < numReaders) {
                thread = new ReaderThread(baseDados, ehImplementacaoReaderAndWriters);
                contReaders++;
            } else if (contWriters < numWriters) {
                thread = new WriterThread(baseDados, ehImplementacaoReaderAndWriters);
                contWriters++;
            }

            int posicaoAleatoria;
            while(true) {
                posicaoAleatoria = numeroAleatorio.nextInt(TAMANHO_ARRANJO_THREADS);
                
                if (posicoesValidas.get(posicaoAleatoria)) {
                    // Adiciona Thread no arranjo
                    threads.set(posicaoAleatoria, thread);

                    // Torna a posicao invalida
                    posicoesValidas.put(posicaoAleatoria, false);
                    break;
                }
            }
        }

        return threads;
    }


    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.print("Escolhe o tipo de execução:\n1 - Implementação Readers and Writers\n2 - Implementação Sem Readers and Writers\nEscolha: ");
        Scanner scanner = new Scanner(System.in);
        int escolhaExecucao = scanner.nextInt();

        while (escolhaExecucao != 1 && escolhaExecucao != 2) {
            System.out.println("Escolha inválida. Por favor, escolha 1 ou 2:");
            escolhaExecucao = scanner.nextInt();
        }

        boolean ehImplementacaoReaderAndWriters = escolhaExecucao == 1;

        LeitorBD leitorBD = new LeitorBD("arquivos/bd.txt");
        BaseDados baseDados = leitorBD.carregarArranjo();

        // Preparar arquivo CSV para salvar resultados
        String nomeArquivoCSV = ehImplementacaoReaderAndWriters ?
            "resultados/resultados_readers_and_writers.csv" :
            "resultados/resultados_sem_readers_and_writers.csv";

        BufferedWriter csvWriter = new BufferedWriter(new FileWriter(nomeArquivoCSV));
        csvWriter.write("NumReaders,NumWriters,TempoMedio(ms),TempoTotal(ms)\n");

        for (int i = 0, j = 100; i <= 100 && j >= 0; i++, j--) {
            double tempoInicio = 0.0;
            double tempoFim = 0.0;
            double tempoTotal50Execucoes = 0.0;

            for (int k = 0; k < 50; k++) {
                int numReaders = i;
                int numWriters = j;

                // Inicializar threads de leitura e escrita no arranjo de tamanho 100
                List<Thread> arranjoThreads = inicializarThreads(baseDados, ehImplementacaoReaderAndWriters, numReaders, numWriters);

                if (arranjoThreads.size() != TAMANHO_ARRANJO_THREADS) {
                    throw new RuntimeException("Erro ao inicializar o arranjo de threads.");
                }

                // INÍCIO DA MEDIÇÃO: após povoamento do arranjo, antes de iniciar threads
                tempoInicio = System.currentTimeMillis();

                // Iniciar todas as threads
                for (Thread thread : arranjoThreads) {
                    thread.start();
                }

                // Esperar todas terminarem
                for (Thread thread : arranjoThreads) {
                    thread.join();
                }

                // FIM DA MEDIÇÃO: após término da última thread
                tempoFim = System.currentTimeMillis();

                tempoTotal50Execucoes += (tempoFim - tempoInicio);
            }
            String nomeArquivoTXT = "";
            if (ehImplementacaoReaderAndWriters) {
                nomeArquivoTXT = "resultados/output_readers_and_writers_%s_%s.txt".formatted(i, j);
            } else {
                nomeArquivoTXT = "resultados/output_sem_readers_and_writers_%s_%s.txt".formatted(i, j);
            }

            // Calcular tempo médio no final de 50 execucoes
            double tempoMedio = tempoTotal50Execucoes / 50.0;

            // Salvar dados em arquivo CSV
            csvWriter.write(String.format("%d,%d,%.6f,%.6f\n", i, j, tempoMedio, tempoTotal50Execucoes));
        }
        
        // Fechar arquivo CSV
        csvWriter.close();
    }
}