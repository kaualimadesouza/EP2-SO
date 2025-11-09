package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BaseDados {
    private final List<String> palavras;
    private ReadWriteLock lockVariavel;
    private int tamanhoBase;

    public BaseDados() {
        this.palavras = new ArrayList<String>();
        this.lockVariavel = new ReentrantReadWriteLock();
        this.tamanhoBase = 0;
    }

    public void entrarLeitura() {
        this.lockVariavel.readLock().lock();
    }

    public void sairLeitura() {
        this.lockVariavel.readLock().unlock();
    }

    public void entrarEscrita() {
        this.lockVariavel.writeLock().lock();
    }

    public void sairEscrita() {
        this.lockVariavel.writeLock().unlock();
    }

    // Leitura de palavras na lista
    public String read(int index) {
        return this.palavras.get(index);
    }

    // Escrita de palavras na lista
    public void write(int index, String value) {
        this.palavras.set(index, value);
    }

    public List<String> getPalavras() {
        return palavras;
    }

    public synchronized void addItemLista(String palavra) {
        this.palavras.add(palavra);
    }

    public int getTamanhoBase() {
        return tamanhoBase;
    }

    public void setTamanhoBase(int tamanho) {
        this.tamanhoBase = tamanho;
    }
}
