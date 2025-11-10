package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implementa o padrão Readers-Writers usando ReentrantReadWriteLock para permitir múltiplos leitores simultâneos ou um único escritor por vez se bem implementado.
 */
public class BaseDados {
    private final List<String> palavras;

    // Lock para o cado de Readers-Writers
    private ReadWriteLock lockVariavelReadersAndWriters;

    // Lock para o caso sem Readers-Writers
    private ReentrantLock lockVariavelSemReadersAndWriters;
    private int tamanhoBase;

    /**
     * Construtor da classe BaseDados.
     * Inicializa a lista de palavras, o lock de leitura/escrita e o tamanho da base.
     */
    public BaseDados() {
        this.palavras = new ArrayList<String>();
        this.lockVariavelReadersAndWriters = new ReentrantReadWriteLock();
        this.lockVariavelSemReadersAndWriters = new ReentrantLock();
        this.tamanhoBase = 0;
    }

    /**
     * Adquire o lock de leitura para permitir acesso concorrente de múltiplos leitores.
     * Deve ser chamado antes de qualquer operação de leitura.
     */
    public void entrarLeituraReadersAndWriters() {
        this.lockVariavelReadersAndWriters.readLock().lock();
    }

    /**
     * Libera o lock de leitura após completar operações de leitura.
     */
    public void sairLeituraReadersAndWriters() {
        this.lockVariavelReadersAndWriters.readLock().unlock();
    }

    /**
     * Adquire o lock de escrita para acesso exclusivo de um único escritor.
     * Deve ser chamado antes de qualquer operação de escrita.
     */
    public void entrarEscritaReadersAndWriters() {
        this.lockVariavelReadersAndWriters.writeLock().lock();
    }

    /**
     * Libera o lock de escrita após completar operações de escrita.
     */
    public void sairEscritaReadersAndWriters() {
        this.lockVariavelReadersAndWriters.writeLock().unlock();
    }

    public void entrarRegiaoCriticaSemReadersAndWriters() {
        this.lockVariavelSemReadersAndWriters.lock();
    }

    public void sairRegiaoCriticaSemReadersAndWriters() {
        this.lockVariavelSemReadersAndWriters.unlock();
    }

    /**
     * Lê uma palavra na posição especificada da base de dados.
     */
    public String read(int index) {
        return this.palavras.get(index);
    }

    /**
     * Escreve uma palavra na posição especificada da base de dados.
     */
    public void write(int index, String value) {
        this.palavras.set(index, value);
    }


    public List<String> getPalavras() {
        return palavras;
    }

    /**
     * Adiciona uma nova palavra à base de dados.
     */
    public void addItemLista(String palavra) {
        this.palavras.add(palavra);
    }

    /**
     * Obtém o tamanho total da base de dados.
     */
    public int getTamanhoBase() {
        return tamanhoBase;
    }

    /**
     * Define o tamanho da base de dados.
     */
    public void setTamanhoBase(int tamanho) {
        this.tamanhoBase = tamanho;
    }
}
