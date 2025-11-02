# Exercício Programa 2 | Sistemas Operacionais | Implementação de Readers e Writers

## Descrição do Projeto

Este é o segundo exercício-programa da disciplina de Sistemas Operacionais que tem como objetivo implementar e avaliar soluções para o problema clássico de **Leitores e Escritores** usando threads em Java.

## Objetivo

O projeto visa ilustrar a importância prática das soluções para o problema de Leitores e Escritores através de:

1. Implementação de uma estrutura de dados compartilhada contendo palavras do arquivo "bd.txt"
2. Criação de threads concorrentes (leitores e escritores) que acessam essa estrutura
3. Comparação de desempenho entre implementações com e sem controle de Readers/Writers
4. Análise de diferentes proporções de leitores e escritores

## Estrutura do Projeto

```
EP2_SO/
├── src/main/java/org/example/
│   ├── LeitorBD.java          # Classe para leitura do arquivo de dados
│   └── Main.java              # Classe principal
├── arquivos/
│   ├── bd.txt                 # Texto "A Treatise Concerning the Principles of Human Knowledge" (George Berkeley, 1710)
│   └── ep02.pdf               # Especificação completa do exercício
├── pom.xml                    # Configuração Maven
└── README.md                  # Este arquivo
```

## Arquivo de Dados

O projeto utiliza o texto filosófico **"A Treatise Concerning the Principles of Human Knowledge"** de George Berkeley (1710), formatado com:
- Uma palavra por linha
- Total de 36.242 linhas
- Pontuação satélite incluída

## Implementação Requerida

### 1. Estrutura de Dados
- Carregar todas as palavras do arquivo `bd.txt` em memória
- Criar uma lista/arranjo com 36.242 elementos (uma palavra por elemento)
- Transformar essa estrutura em região crítica para acesso concorrente

### 2. Threads Concorrentes
- Criar arranjo com exatamente **100 threads**
- Dois tipos de threads:
  - **Leitores**: Apenas leem palavras da base de dados
  - **Escritores**: Escrevem "MODIFICADO" em posições aleatórias
- Distribuição aleatória dos tipos no arranjo

### 3. Comportamento das Threads
Cada thread deve:
1. Fazer **100 acessos** a posições aleatórias da base
2. **Leitores**: Ler palavra e armazenar em variável local
3. **Escritores**: Escrever "MODIFICADO" na posição
4. Dormir por **1ms** após os 100 acessos (simulando validação)
5. Todo o processo (100 acessos + sleep) ocorre dentro da região crítica

### 4. Implementações a Desenvolver

#### A) Com Readers/Writers
- Implementar solução que prioriza sempre os **leitores**
- Escritores esperam até não haver mais leitores na base
- Múltiplos leitores podem acessar simultaneamente

#### B) Sem Readers/Writers
- Sistema bloqueia qualquer acesso quando alguém está na base
- Acesso mutuamente exclusivo para todos (leitores e escritores)

### 5. Testes e Medições
- Testar diferentes proporções: 0R/100W, 1R/99W, 2R/98W, ..., 100R/0W
- Para cada proporção: **50 execuções**
- Calcular tempo médio de execução
- Medir tempo entre fim da criação das threads e término da última thread
- Usar `System.currentTimeMillis()` para medição


### Pré-requisitos
- Java 24+
- Maven

## Classes Implementadas

### LeitorBD
Classe responsável por carregar o arquivo de texto:
- Construtor recebe caminho do arquivo
- Método `carregarArranjos()` retorna lista de strings com todas as palavras

### Main
Classe principal que atualmente:
- Carrega as palavras do arquivo `bd.txt`
- Exibe todas as palavras
- Mostra o total de palavras carregadas

