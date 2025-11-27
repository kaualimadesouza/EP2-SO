# Exercício Programa 2 | Sistemas Operacionais | Implementação de Readers e Writers

## Descrição do Projeto

Este é o segundo exercício-programa da disciplina de Sistemas Operacionais que implementa e avalia soluções para o problema clássico de **Leitores e Escritores** usando threads em Java.

## Objetivo

O projeto visa ilustrar a importância prática das soluções para o problema de Leitores e Escritores através de:

1. Implementação de uma estrutura de dados compartilhada contendo palavras do arquivo "bd.txt"
2. Criação de threads concorrentes (leitores e escritores) que acessam essa estrutura
3. Comparação de desempenho entre implementações com e sem controle de Readers/Writers
4. Análise de diferentes proporções de leitores e escritores

## Implementação Completa

### ✅ Implementado
- **BaseDados**: Estrutura compartilhada com `ReentrantReadWriteLock` e `ReentrantLock` para ambas as implementações
- **LeitorBD**: Carrega arquivo `bd.txt` e inicializa base de dados  
- **ReaderThread**: Thread de leitura que faz 100 acessos aleatórios + sleep 1ms
- **WriterThread**: Thread de escrita que escreve "MODIFICADO" em 100 posições aleatórias + sleep 1ms
- **Main**: Cria 100 threads com proporções variadas (de 0R/100W a 100R/0W), executa 50 vezes cada proporção e salva resultados em CSV
- **Sistema de medição de tempo**: Cronometra execução das threads com `System.currentTimeMillis()`
- **Implementação sem Readers/Writers**: Usa exclusão mútua total com `ReentrantLock`
- **Testes automatizados**: 101 proporções, 50 execuções cada, coleta de estatísticas

## Estrutura do Projeto

```
EP2_SO/
├── src/main/java/org/example/
│   ├── BaseDados.java         # Estrutura de dados compartilhada com locks
│   ├── LeitorBD.java          # Classe para leitura do arquivo de dados
│   ├── ReaderThread.java      # Thread de leitura
│   ├── WriterThread.java      # Thread de escrita
│   └── Main.java              # Classe principal
├── arquivos/
│   ├── bd.txt                 # Texto "A Treatise Concerning the Principles of Human Knowledge" (George Berkeley, 1710)
│   └── ep02.pdf               # Especificação completa do exercício
├── resultados/
│   ├── resultados_readers_and_writers.csv    # Resultados com implementação Readers/Writers
│   └── resultados_sem_readers_and_writers.csv # Resultados sem implementação Readers/Writers
├── pom.xml                    # Configuração Maven
└── README.md                  # Este arquivo
```

## Arquivo de Dados

O projeto utiliza o texto filosófico **"A Treatise Concerning the Principles of Human Knowledge"** de George Berkeley (1710), formatado com:
- Uma palavra por linha
- Total de 36.242 linhas
- Pontuação satélite incluída

## Implementação Detalhada

### 1. BaseDados
Estrutura thread-safe com duas opções:
- **Com Readers/Writers**: Usa `ReentrantReadWriteLock` para múltiplos leitores simultâneos e escritores exclusivos
- **Sem Readers/Writers**: Usa `ReentrantLock` para exclusão mútua total
- **Operações**: `read(int index)` e `write(int index, String value)`

### 2. Sistema de Threads
- **100 threads** por execução, distribuídas conforme proporção especificada
- **Proporções testadas**: 0R/100W, 1R/99W, ..., 99R/1W, 100R/0W (101 combinações)
- **Execução sequencial**: Todas as threads são iniciadas e aguardadas com `join()`

### 3. Comportamento das Threads
- **ReaderThread**: 
  - Adquire lock apropriado (readLock ou mutex)
  - Executa 100 leituras de posições aleatórias
  - Sleep de 1ms após os 100 acessos (dentro da região crítica)
  - Libera lock no bloco `finally`

- **WriterThread**:
  - Adquire lock apropriado (writeLock ou mutex)
  - Executa 100 escritas de "MODIFICADO" em posições aleatórias  
  - Sleep de 1ms após os 100 acessos (dentro da região crítica)
  - Libera lock no bloco `finally`

### 4. Política de Concorrência
- **Com Readers/Writers**: Múltiplos leitores simultâneos, escritores exclusivos, prioridade aos leitores
- **Sem Readers/Writers**: Exclusão mútua total (apenas uma thread por vez)

## Como Executar

1. Certifique-se de ter o Maven instalado.
2. Compile o projeto: `mvn compile`
3. Execute a classe principal: `mvn exec:java -Dexec.mainClass="org.example.Main"`
4. Escolha a implementação:
   - 1: Com Readers and Writers
   - 2: Sem Readers and Writers
5. Aguarde a execução completa (pode levar alguns minutos devido às 50 execuções por proporção).
6. Verifique os resultados nos arquivos CSV em `resultados/`.

## Resultados

Os arquivos CSV contêm:
- `NumReaders`: Número de threads de leitura
- `NumWriters`: Número de threads de escrita
- `TempoMedio(ms)`: Tempo médio das 50 execuções
- `TempoTotal(ms)`: Tempo total das 50 execuções

Use esses dados para comparar o desempenho entre as implementações.
