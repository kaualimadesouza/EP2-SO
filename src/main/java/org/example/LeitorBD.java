package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe responsável por ler as palavras de arquivos de texto.
 * Usada especificamente para ler o arquivo bd.txt em 'arquivos/bd.txt'
 */
public class LeitorBD {

    private final String pathArquivo;

    /**
     * Construtor da classe LeitorBD.
     * @param pathArquivo o caminho para o arquivo de texto a ser lido
     */
    public LeitorBD(String pathArquivo) {
        this.pathArquivo = pathArquivo;
    }

    /**
     * Carrega todas as linhas do arquivo especificado no construtor.
     *
     * @return uma lista contendo todas as linhas do arquivo como strings
     */
    public BaseDados carregarArranjo() {
        // Usar try catch para ler o arquivo e depois fechar o BufferedReader automaticamente
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.pathArquivo))) {
            BaseDados baseDados = new BaseDados();
            String linha;
            int contadorLinhas = 0;

            // Ler cada linha do arquivo e adicionar à lista
            while ((linha = bufferedReader.readLine()) != null) {
                baseDados.addItemLista(linha);
                contadorLinhas++;
            }
            baseDados.setTamanhoBase(contadorLinhas);
            return baseDados;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
