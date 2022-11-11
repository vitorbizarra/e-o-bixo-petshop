# É o Bixo Veterinária

Projeto desenvolvido como forma avaliativa da matéria de Linguagens de Programação no 2º semestre do curso de Análise e Desenvolvimento de Sistemas pela Facens.

## Integrantes:

- Mateus Maranhão Macedo. RA: 222349
- Raphael Vicentini de Souza. RA: 223740
- Victor Augusto Francisco Brotto. RA: 222554
- Vitor Pinto Bizarra. RA: 212066

## Resumo

É o Bixo Veterinária é uma clínica veterinária prestadora de serviços que envia seus veterinários para atender seus clientes, como grandes granjas, fazendas, zoológicos entre outros.

Esse software foi desenvolvido para que os gerentes da empresa pudessem ter um painel para gerenciar os clientes e funcionários, além de poder ver os serviços prestados.

## Instalação e Configuração:

- Fazer o pull do projeto na pasta de preferência.
- Executar o script projeto_veterinaria.sql, que está dentro da pasta "Database", no MySQL Workbench.
- Alterar as credenciais de acesso do banco de dados em conexoes/MySQL.java:

```java
 public class MySQL {

    //atributos de conexão com o banco
    private Connection conn = null; //variável de conexão com o banco
    private Statement statement; //variável de manipulação do SQL
    private ResultSet resultSet;

    private String servidor = "localhost:3306";
    private String nomeDoBanco = "banco_loja";
    private String usuario = "root";
    private String senha = "facens";

    [...]
```
