# Simulador de Memória Cache

Este projeto é um simulador de memória cache que implementa diferentes estratégias de mapeamento e substituição. Ele permite simular o comportamento de memória cache com base em configurações fornecidas e arquivos de teste.

## Funcionalidades

O simulador suporta os seguintes tipos de mapeamento e substituição:

### Tipos de Mapeamento
- **Mapeamento Direto**: Implementado na classe [`mapDireto`](src/trabalhopratico1/service/mapDireto.java).
- **Mapeamento Associativo**: Implementado na classe [`mapAssociativo`](src/trabalhopratico1/service/mapAssociativo.java).
- **Mapeamento Associativo por Conjunto**: Implementado na classe [`mapAssociativoConjunto`](src/trabalhopratico1/service/mapAssociativoConjunto.java).

### Métodos de Substituição
- **Random**
- **FIFO**
- **LRU**
- **LFU**

## Configuração

Os arquivos de configuração estão localizados na pasta `dados/`. Eles definem os parâmetros da memória, como tamanho da cache, tamanho da palavra, e outros.

Exemplo de configuração (`config.txt`):

## Como Usar

1. **Configuração**: Certifique-se de que o arquivo de configuração e o arquivo de teste estão configurados corretamente.
2. **Execução**: Execute a interface gráfica localizada em [`trabalhoInterface`](src/trabalhopratico1/ui/trabalhoInterface.java).
3. **Seleção**: Escolha o tipo de mapeamento, método de substituição e os arquivos de configuração e teste.
4. **Simulação**: Clique em "Iniciar" para executar a simulação e visualizar os resultados.

## Dependências

- **Java SE 8 ou superior**
- **Bibliotecas padrão do Java**

## Classes Principais

### [`mapeamento`](src/trabalhopratico1/model/mapeamento.java)
Classe base que define os atributos e métodos comuns para os diferentes tipos de mapeamento.

### [`FileManager`](src/trabalhopratico1/model/FileManager.java)
Classe utilitária para leitura e escrita de arquivos.

### [`trabalhoInterface`](src/trabalhopratico1/ui/trabalhoInterface.java)
Interface gráfica para interação com o simulador.
<div>
  <img src="https://github.com/user-attachments/assets/8353db14-82ba-450d-8742-cd7396c6f1db" />
</div>
