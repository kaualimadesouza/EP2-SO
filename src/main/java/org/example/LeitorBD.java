package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public List<String> carregarArranjos() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(this.pathArquivo))) {
            List<String> arranjoPalavras = new ArrayList<>();
            String linha;
            while ((linha = bufferedReader.readLine()) != null) {
                arranjoPalavras.add(linha);
            }
            return arranjoPalavras;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
