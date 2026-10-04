# ProjetoEscolaEAD

Sistema **console** para uma escola de Educação a Distância (EAD), desenvolvido em Java no BlueJ aplicando conceitos de Programação Orientada a Objetos. O projeto foi construído de forma evolutiva em 5 partes, todas integradas em um único menu.

## Como executar

**BlueJ:** abra a pasta `ProjetoEscolaEAD` (Project → Open Project), clique em **Compile**, depois clique com o botão direito em `SistemaEscolaEAD` → `void main(String[] args)`.

**Terminal:**

```bash
javac -encoding UTF-8 *.java
java SistemaEscolaEAD
```

O sistema já inicia com alguns cursos e alunos de exemplo (Carlos, Ana e o bolsista Bruno) para facilitar os testes.

## Menu

```
=== MENU ===
1 - Visualizar Lista de Alunos
2 - Adicionar Aluno
3 - Sair
4 - Verificar Notas do Aluno
5 - Verificar Financeiro do Aluno
6 - Lançar Notas do Aluno
7 - Cadastrar Cursos
8 - Exibir Cursos e Alunos Matriculados
```

## Classes

| Classe | Parte | Responsabilidade |
|---|---|---|
| `Aluno` | 01, 02, 04, 05 | Dados do aluno, curso matriculado, notas (`double[3]`), mensalidades (`Mensalidade[]`) |
| `Curso` | 02 | Código, nome e duração (horas) |
| `AlunoBolsista` | 03 | Subclasse de `Aluno` com `tipoBolsa`; sobrescreve `exibeDados()` e o desconto |
| `ListaDeAlunos` | 03 | Vetor `Aluno[]` com controle de duplicidade e busca por código |
| `Mensalidade` | 05 | Valor, status de pagamento e `darBaixa()` |
| `SistemaEscolaEAD` | 02–05 | Menu interativo (`Scanner`) e matriz `Curso[][]` |

## Conceitos aplicados

- **Encapsulamento:** todos os atributos são `private`, com getters/setters que validam os dados (código > 0, e-mail com `@`, data `dd/mm/aaaa`, senha com no mínimo 4 caracteres, nota entre 0 e 10).
- **Herança e polimorfismo:** `AlunoBolsista extends Aluno`, usa `super(...)` e sobrescreve `exibeDados()`, `getTipo()` e `getPercentualDesconto()`. O vetor `Aluno[]` guarda os dois tipos, e as mensalidades recebem desconto conforme o tipo real do objeto (Integral 100%, Parcial 50%, outros 25%).
- **Array unidimensional:** `Aluno[] alunos`, `double[] notas`, `boolean[] lancada`, `Mensalidade[] mensalidades`.
- **Array bidimensional:** `Curso[][] matrizCursos` — linhas = semestres, colunas = cursos de cada semestre.
- **Associação aluno–curso:** Opção A (atributo `Curso cursoMatriculado` em `Aluno`).
- **Repetição:** `for` (percorrer vetores e matriz), `while` (busca por código, leitura com validação), `do-while` (menu principal, pagamento de parcelas).
- **Seleção:** `switch` no menu, `if/else` nas validações e na situação do aluno (aprovado, recuperação, reprovado).
