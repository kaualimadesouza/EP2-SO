# Exercício Programa 2 | Sistemas Operacionais | Implementação de Readers e Writers

## Descrição do Projeto

Este é o segundo exercício-programa da disciplina de Sistemas Operacionais que tem como objetivo implementar e avaliar soluções para o problema clássico de **Leitores e Escritores** usando threads em Java.

## Objetivo

O projeto visa ilustrar a importância prática das soluções para o problema de Leitores e Escritores através de:

1. Implementação de uma estrutura de dados compartilhada contendo palavras do arquivo "bd.txt"
2. Criação de threads concorrentes (leitores e escritores) que acessam essa estrutura
3. Comparação de desempenho entre implementações com e sem controle de Readers/Writers
4. Análise de diferentes proporções de leitores e escritores

## Estado Atual da Implementação

### ✅ Implementado
- **BaseDados**: Estrutura compartilhada com `ReentrantReadWriteLock`
- **LeitorBD**: Carrega arquivo `bd.txt` e inicializa base de dados
- **ReaderThread**: Thread de leitura que faz 100 acessos aleatórios + sleep 1ms
- **WriterThread**: Thread de escrita que escreve "MODIFICADO" em 100 posições aleatórias + sleep 1ms
- **Main**: Cria 100 threads aleatoriamente distribuídas e executa todas

### 🚧 Pendente
- Implementar versão com Readers-Writers
- Sistema de medição de tempo de execução
- Testes com diferentes proporções de leitores/escritores (0R/100W até 100R/0W)
- Implementação sem Readers/Writers (exclusão mútua total)
- Execução de 50 testes por proporção e coleta de estatísticas

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
├── pom.xml                    # Configuração Maven
└── README.md                  # Este arquivo
```

## Arquivo de Dados

O projeto utiliza o texto filosófico **"A Treatise Concerning the Principles of Human Knowledge"** de George Berkeley (1710), formatado com:
- Uma palavra por linha
- Total de 36.242 linhas
- Pontuação satélite incluída

## Implementação Atual

### 1. BaseDados
Estrutura thread-safe usando `ReentrantReadWriteLock`:
- **Lista de 36.242 palavras** carregada do arquivo `bd.txt`
- **Métodos de controle**: `entrarLeitura()`, `sairLeitura()`, `entrarEscrita()`, `sairEscrita()`
- **Operações de acesso**: `read(int index)` e `write(int index, String value)`
- **Política**: Múltiplos leitores simultâneos, escritores exclusivos

### 2. Sistema de Threads
- **100 threads** criadas e distribuídas aleatoriamente no arranjo
- **Distribuição aleatória** de `ReaderThread` e `WriterThread` (50/50 aproximadamente)
- **Execução sequencial**: Todas as threads são iniciadas e aguardadas com `join()`

### 3. Comportamento das Threads Implementado
- **ReaderThread**: 
  - Adquire `readLock()` 
  - Executa 100 leituras de posições aleatórias
  - Armazena palavra lida em variável local
  - Sleep de 1ms após os 100 acessos (ainda dentro da região crítica)
  - Libera lock no bloco `finally`

- **WriterThread**:
  - Adquire `writeLock()`
  - Executa 100 escritas de "MODIFICADO" em posições aleatórias  
  - Sleep de 1ms após os 100 acessos (ainda dentro da região crítica)
  - Libera lock no bloco `finally`


### 4. Política de Concorrência Atual
- **Readers/Writers implementado** com `ReentrantReadWriteLock`
- **Múltiplos leitores** podem executar simultaneamente
- **Escritores têm acesso exclusivo** (não executam com leitores ou outros escritores)
- **Prioridade aos leitores** (padrão do `ReentrantReadWriteLock`)

## Próximas Etapas

### Funcionalidades Pendentes
1. **Medição de tempo**: Implementar cronometragem das execuções com `System.currentTimeMillis()`
2. **Proporções configuráveis**: Permitir especificar quantidade exata de leitores/escritores
3. **Implementação sem Readers/Writers**: Versão com exclusão mútua total (`synchronized`)
4. **Sistema de testes automatizado**: Executar 50 vezes cada proporção (0R/100W até 100R/0W)

### Estrutura de Testes Planejada
- **101 proporções**: 0R/100W, 1R/99W, 2R/98W, ..., 99R/1W, 100R/0W
- **50 execuções por proporção**
- **Medição**: Tempo entre fim da criação das threads e término da última thread
- **Comparação**: Implementação com vs sem Readers/Writers

## Como Executar

### Pré-requisitos
- Java 24+
- Maven

### Execução
```bash
mvn compile exec:java -Dexec.mainClass="org.example.Main"
```

## Classes Implementadas

### BaseDados
Estrutura de dados thread-safe:
- `entrarLeitura()` / `sairLeitura()`: Controle de acesso para leitores
- `entrarEscrita()` / `sairEscrita()`: Controle de acesso para escritores  
- `read(int index)`: Lê palavra na posição especificada
- `write(int index, String value)`: Escreve palavra na posição especificada
- `addItemLista(String palavra)`: Adiciona palavra na inicialização (thread-safe)

### LeitorBD
Carrega arquivo de texto:
- **Construtor**: Recebe caminho do arquivo
- **`carregarArranjos()`**: Retorna `BaseDados` inicializada com todas as palavras
- **Funcionalidade**: Lê arquivo linha por linha e popula a estrutura

### ReaderThread / WriterThread
Threads que implementam o padrão Reader/Writer:
- **100 acessos aleatórios** cada thread
- **Região crítica**: Todo o processo de acessos ocorre dentro do lock
- **Tratamento de exceções**: Lock liberado no `finally`

### Main
- Inicializa `BaseDados` carregando arquivo `bd.txt`
- Cria 100 threads com distribuição aleatória de tipos
- Popula arranjo de threads em posições aleatórias
- Executa todas as threads e aguarda conclusão com `join()`

