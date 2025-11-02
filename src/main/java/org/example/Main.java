package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        LeitorBD leitorBD = new LeitorBD("arquivos/bd.txt");
        List<String> arranjoPalavras = leitorBD.carregarArranjos();

        for (String palavra : arranjoPalavras) {
            System.out.println(palavra);
        }

        System.out.println(arranjoPalavras.size());
    }
}