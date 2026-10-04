import java.util.Scanner;

/**
 * Classe SistemaEscolaEAD - sistema console integrado da Escola EAD.
 *
 * Junta as 5 partes do projeto em um menu interativo:
 *  - Parte 02: cadastro de cursos em uma matriz bidimensional Curso[][]
 *              (linhas = semestres, colunas = cursos do semestre)
 *  - Parte 03: lista de alunos (Aluno[]) com Aluno e AlunoBolsista
 *  - Parte 04: lançamento e consulta de notas
 *  - Parte 05: controle financeiro (mensalidades)
 *
 * Para executar no BlueJ: clique com o botão direito na classe
 * SistemaEscolaEAD e escolha "void main(String[] args)".
 *
 * @author Nicolas
 * @version 1.0
 */
public class SistemaEscolaEAD
{
    private static final int SEMESTRES = 2;
    private static final int CURSOS_POR_SEMESTRE = 3;
    private static final int CAPACIDADE_ALUNOS = 50;

    // array bidimensional obrigatório: [semestre][posição do curso]
    private Curso[][] matrizCursos;
    private ListaDeAlunos listaDeAlunos;
    private Scanner entrada;

    public SistemaEscolaEAD()
    {
        matrizCursos = new Curso[SEMESTRES][CURSOS_POR_SEMESTRE];
        listaDeAlunos = new ListaDeAlunos(CAPACIDADE_ALUNOS);
        entrada = new Scanner(System.in);
        carregarDadosIniciais();
    }

    public static void main(String[] args)
    {
        SistemaEscolaEAD sistema = new SistemaEscolaEAD();
        sistema.executar();
    }

    /**
     * Laço principal do menu (do-while + switch).
     */
    public void executar()
    {
        int opcao;
        System.out.println("==========================================");
        System.out.println("     BEM-VINDO AO SISTEMA ESCOLA EAD");
        System.out.println("==========================================");

        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");
            System.out.println();

            switch (opcao) {
                case 1:
                    visualizarListaDeAlunos();
                    break;
                case 2:
                    adicionarAluno();
                    break;
                case 3:
                    System.out.println("Encerrando o sistema. Até logo!");
                    break;
                case 4:
                    verificarNotas();
                    break;
                case 5:
                    verificarFinanceiro();
                    break;
                case 6:
                    lancarNotas();
                    break;
                case 7:
                    cadastrarCursos();
                    break;
                case 8:
                    exibirCursosEAlunos();
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 3);
    }

    private void exibirMenu()
    {
        System.out.println();
        System.out.println("=== MENU ===");
        System.out.println("1 - Visualizar Lista de Alunos");
        System.out.println("2 - Adicionar Aluno");
        System.out.println("3 - Sair");
        System.out.println("4 - Verificar Notas do Aluno");
        System.out.println("5 - Verificar Financeiro do Aluno");
        System.out.println("6 - Lançar Notas do Aluno");
        System.out.println("7 - Cadastrar Cursos");
        System.out.println("8 - Exibir Cursos e Alunos Matriculados");
    }

    // ===================== OPÇÃO 1 =====================

    private void visualizarListaDeAlunos()
    {
        System.out.println("=== LISTA DE ALUNOS ===");
        listaDeAlunos.exibirLista();
    }

    // ===================== OPÇÃO 2 =====================

    private void adicionarAluno()
    {
        System.out.println("=== ADICIONAR ALUNO ===");
        System.out.println("1 - Aluno regular");
        System.out.println("2 - Aluno bolsista");
        int tipo = lerInteiro("Tipo do aluno: ");
        if (tipo != 1 && tipo != 2) {
            System.out.println("Tipo inválido. Cadastro cancelado.");
            return;
        }

        int codigo = lerInteiro("Código: ");
        if (listaDeAlunos.buscarPorCodigo(codigo) != null) {
            System.out.println("Já existe um aluno com esse código. Cadastro cancelado.");
            return;
        }
        String nome = lerTexto("Nome: ");
        String dataNascimento = lerTexto("Data de nascimento (dd/mm/aaaa): ");
        String email = lerTexto("E-mail: ");
        String senha = lerTexto("Senha (mínimo 4 caracteres): ");

        Aluno novo;
        try {
            if (tipo == 2) {
                System.out.println("Tipos de bolsa: Integral (100%), Parcial (50%) ou outro nome (25%)");
                String tipoBolsa = lerTexto("Tipo de bolsa: ");
                novo = new AlunoBolsista(codigo, nome, dataNascimento, email, senha, tipoBolsa);
            } else {
                novo = new Aluno(codigo, nome, dataNascimento, email, senha);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage() + " Cadastro cancelado.");
            return;
        }

        // matrícula em um curso da matriz
        if (contarCursos() > 0) {
            listarCursosResumido();
            int codCurso = lerInteiro("Código do curso para matrícula (0 para nenhum): ");
            if (codCurso != 0) {
                Curso curso = buscarCurso(codCurso);
                if (curso != null) {
                    novo.setCursoMatriculado(curso);
                } else {
                    System.out.println("Curso não encontrado. Aluno ficará sem matrícula.");
                }
            }
        } else {
            System.out.println("Nenhum curso cadastrado. Aluno ficará sem matrícula.");
        }

        // mensalidades
        int parcelas;
        do {
            parcelas = lerInteiro("Quantidade de parcelas (1 a " + Aluno.MAX_PARCELAS + "): ");
        } while (parcelas < 1 || parcelas > Aluno.MAX_PARCELAS);

        double valorMensal;
        do {
            valorMensal = lerDouble("Valor da mensalidade (R$): ");
        } while (valorMensal < 0);

        double[] valores = new double[parcelas];
        for (int i = 0; i < parcelas; i++) {
            valores[i] = valorMensal;
        }
        novo.adicionarMensalidades(valores);

        if (listaDeAlunos.adicionarAluno(novo)) {
            System.out.println("Aluno " + novo.getNome() + " cadastrado com sucesso!");
        }
    }

    // ===================== OPÇÃO 4 =====================

    private void verificarNotas()
    {
        System.out.println("=== VERIFICAR NOTAS ===");
        Aluno aluno = buscarAlunoPeloCodigo();
        if (aluno == null) {
            return;
        }
        aluno.exibirNotas();
    }

    // ===================== OPÇÃO 5 =====================

    private void verificarFinanceiro()
    {
        System.out.println("=== FINANCEIRO DO ALUNO ===");
        Aluno aluno = buscarAlunoPeloCodigo();
        if (aluno == null) {
            return;
        }

        int parcela;
        do {
            System.out.println();
            aluno.exibirMensalidades();
            if (aluno.getNumParcelas() == 0) {
                return;
            }
            parcela = lerInteiro("Número da parcela para pagar (0 para voltar): ");
            if (parcela != 0) {
                aluno.pagarMensalidade(parcela - 1);
            }
        } while (parcela != 0);
    }

    // ===================== OPÇÃO 6 =====================

    private void lancarNotas()
    {
        System.out.println("=== LANÇAR NOTAS ===");
        Aluno aluno = buscarAlunoPeloCodigo();
        if (aluno == null) {
            return;
        }

        int avaliacao = lerInteiro("Qual avaliação deseja lançar (1 a 3) ou 0 para todas: ");
        if (avaliacao == 0) {
            for (int i = 1; i <= Aluno.QTD_NOTAS; i++) {
                double nota = lerDouble("Nota " + i + ": ");
                aluno.lancarNotas(i, nota);
            }
        } else {
            double nota = lerDouble("Nota " + avaliacao + ": ");
            aluno.lancarNotas(avaliacao, nota);
        }
        System.out.println();
        aluno.exibirNotas();
    }

    // ===================== OPÇÃO 7 (PARTE 02) =====================

    /**
     * Cadastra até N cursos na matriz bidimensional.
     */
    private void cadastrarCursos()
    {
        System.out.println("=== CADASTRAR CURSOS ===");
        int vagas = SEMESTRES * CURSOS_POR_SEMESTRE - contarCursos();
        if (vagas == 0) {
            System.out.println("A matriz de cursos está cheia.");
            return;
        }
        System.out.println("Vagas disponíveis na matriz: " + vagas);

        int n = lerInteiro("Quantos cursos deseja cadastrar? ");
        if (n <= 0) {
            System.out.println("Nenhum curso cadastrado.");
            return;
        }
        if (n > vagas) {
            System.out.println("Só há espaço para " + vagas + " curso(s).");
            n = vagas;
        }

        for (int i = 1; i <= n; i++) {
            System.out.println();
            System.out.println("Curso " + i + " de " + n);

            int semestre = lerInteiro("Semestre (1 a " + SEMESTRES + "): ");
            if (semestre < 1 || semestre > SEMESTRES) {
                System.out.println("Semestre inválido. Curso ignorado.");
                continue;
            }
            int coluna = posicaoLivre(semestre - 1);
            if (coluna == -1) {
                System.out.println("O semestre " + semestre + " já está cheio. Curso ignorado.");
                continue;
            }

            int codigo = lerInteiro("Código do curso: ");
            if (buscarCurso(codigo) != null) {
                System.out.println("Já existe um curso com esse código. Curso ignorado.");
                continue;
            }
            String nome = lerTexto("Nome do curso: ");
            int duracao = lerInteiro("Duração (em horas): ");

            try {
                matrizCursos[semestre - 1][coluna] = new Curso(codigo, nome, duracao);
                System.out.println("Curso cadastrado no semestre " + semestre + ".");
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage() + " Curso ignorado.");
            }
        }
    }

    // ===================== OPÇÃO 8 (PARTE 02) =====================

    /**
     * Percorre a matriz e exibe cada curso com seus alunos matriculados.
     */
    private void exibirCursosEAlunos()
    {
        System.out.println("=== CURSOS E ALUNOS MATRICULADOS ===");
        if (contarCursos() == 0) {
            System.out.println("Nenhum curso cadastrado.");
            return;
        }

        for (int s = 0; s < matrizCursos.length; s++) {
            System.out.println();
            System.out.println(">>> " + (s + 1) + "º SEMESTRE");
            boolean temCurso = false;
            for (int c = 0; c < matrizCursos[s].length; c++) {
                Curso curso = matrizCursos[s][c];
                if (curso != null) {
                    temCurso = true;
                    System.out.println();
                    System.out.println("Curso: " + curso.getNome() + " | Duração: " + curso.getDuracao() + "h");
                    System.out.println("Alunos matriculados:");
                    if (listaDeAlunos.exibirAlunosDoCurso(curso) == 0) {
                        System.out.println("  (nenhum aluno)");
                    }
                }
            }
            if (!temCurso) {
                System.out.println("  (nenhum curso neste semestre)");
            }
        }
    }

    // ===================== MÉTODOS AUXILIARES =====================

    private Aluno buscarAlunoPeloCodigo()
    {
        int codigo = lerInteiro("Código do aluno: ");
        Aluno aluno = listaDeAlunos.buscarPorCodigo(codigo);
        if (aluno == null) {
            System.out.println("Aluno com código " + codigo + " não encontrado.");
        }
        return aluno;
    }

    private Curso buscarCurso(int codigo)
    {
        for (int s = 0; s < matrizCursos.length; s++) {
            for (int c = 0; c < matrizCursos[s].length; c++) {
                if (matrizCursos[s][c] != null && matrizCursos[s][c].getCodigo() == codigo) {
                    return matrizCursos[s][c];
                }
            }
        }
        return null;
    }

    private int contarCursos()
    {
        int total = 0;
        for (int s = 0; s < matrizCursos.length; s++) {
            for (int c = 0; c < matrizCursos[s].length; c++) {
                if (matrizCursos[s][c] != null) {
                    total++;
                }
            }
        }
        return total;
    }

    private int posicaoLivre(int semestre)
    {
        for (int c = 0; c < matrizCursos[semestre].length; c++) {
            if (matrizCursos[semestre][c] == null) {
                return c;
            }
        }
        return -1;
    }

    private void listarCursosResumido()
    {
        System.out.println("Cursos disponíveis:");
        for (int s = 0; s < matrizCursos.length; s++) {
            for (int c = 0; c < matrizCursos[s].length; c++) {
                if (matrizCursos[s][c] != null) {
                    System.out.print("  [" + (s + 1) + "º sem] ");
                    matrizCursos[s][c].exibeDados();
                }
            }
        }
    }

    private int lerInteiro(String mensagem)
    {
        while (true) {
            System.out.print(mensagem);
            String linha = entrada.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido! Digite um número inteiro.");
            }
        }
    }

    private double lerDouble(String mensagem)
    {
        while (true) {
            System.out.print(mensagem);
            String linha = entrada.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(linha);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido! Digite um número (ex: 7.5).");
            }
        }
    }

    private String lerTexto(String mensagem)
    {
        String texto;
        do {
            System.out.print(mensagem);
            texto = entrada.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Este campo é obrigatório.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    /**
     * Cadastra alguns dados de exemplo para facilitar os testes.
     */
    private void carregarDadosIniciais()
    {
        Curso javaBasico = new Curso(101, "Java Básico", 40);
        Curso bancoDados = new Curso(102, "Banco de Dados", 60);
        Curso estruturas = new Curso(201, "Estrutura de Dados", 80);
        matrizCursos[0][0] = javaBasico;
        matrizCursos[0][1] = bancoDados;
        matrizCursos[1][0] = estruturas;

        double[] valoresPadrao = new double[6];
        for (int i = 0; i < valoresPadrao.length; i++) {
            valoresPadrao[i] = 350.00;
        }

        Aluno carlos = new Aluno(1, "Carlos", "15/03/2001", "carlos@email.com", "1234");
        carlos.setCursoMatriculado(javaBasico);
        carlos.adicionarMensalidades(valoresPadrao);
        carlos.lancarNotas(1, 8.0);
        carlos.lancarNotas(2, 7.5);
        carlos.lancarNotas(3, 9.0);
        carlos.pagarMensalidade(0);

        Aluno ana = new Aluno(2, "Ana", "22/07/2000", "ana@email.com", "abcd");
        ana.setCursoMatriculado(javaBasico);
        ana.adicionarMensalidades(valoresPadrao);
        ana.lancarNotas(1, 6.0);

        Aluno bruno = new AlunoBolsista(3, "Bruno", "02/11/1999", "bruno@email.com", "senha1", "Parcial");
        bruno.setCursoMatriculado(estruturas);
        bruno.adicionarMensalidades(valoresPadrao);

        listaDeAlunos.adicionarAluno(carlos);
        listaDeAlunos.adicionarAluno(ana);
        listaDeAlunos.adicionarAluno(bruno);
    }
}
