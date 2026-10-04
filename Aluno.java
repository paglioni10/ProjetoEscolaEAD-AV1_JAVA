/**
 * Classe Aluno - representa um aluno da Escola EAD.
 *
 * Parte 01: atributos básicos, construtor, getters/setters e exibeDados().
 * Parte 02: associação com o curso (Opção A - atributo cursoMatriculado).
 * Parte 04: notas (double[3]) e controle de lançamento (boolean[3]).
 * Parte 05: mensalidades (Mensalidade[]) e controle financeiro.
 *
 * @author Nicolas
 * @version 1.0
 */
public class Aluno
{
    // ---------- Parte 01 ----------
    private int codigo;
    private String nome;
    private String dataNascimento;
    private String email;
    private String senha;

    // ---------- Parte 02 (Opção A) ----------
    private Curso cursoMatriculado;

    // ---------- Parte 04 ----------
    public static final int QTD_NOTAS = 3;
    private double[] notas = new double[QTD_NOTAS];
    private boolean[] lancada = new boolean[QTD_NOTAS];

    // ---------- Parte 05 ----------
    public static final int MAX_PARCELAS = 12;
    private Mensalidade[] mensalidades;
    private int numParcelas;

    /**
     * Construtor com todos os parâmetros básicos do aluno.
     */
    public Aluno(int codigo, String nome, String dataNascimento, String email, String senha)
    {
        setCodigo(codigo);
        setNome(nome);
        setDataNascimento(dataNascimento);
        setEmail(email);
        setSenha(senha);
        this.cursoMatriculado = null;
        this.mensalidades = new Mensalidade[0];
        this.numParcelas = 0;
    }

    // ===================== GETTERS E SETTERS =====================

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        if (codigo <= 0) {
            throw new IllegalArgumentException("O código do aluno deve ser maior que zero.");
        }
        this.codigo = codigo;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ficar em branco.");
        }
        this.nome = nome.trim();
    }

    public String getDataNascimento()
    {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento)
    {
        // formato esperado: dd/mm/aaaa
        if (dataNascimento == null || !dataNascimento.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new IllegalArgumentException("Data de nascimento inválida. Use o formato dd/mm/aaaa.");
        }
        this.dataNascimento = dataNascimento;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("E-mail inválido.");
        }
        this.email = email.trim();
    }

    public String getSenha()
    {
        return senha;
    }

    public void setSenha(String senha)
    {
        if (senha == null || senha.length() < 4) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 4 caracteres.");
        }
        this.senha = senha;
    }

    public Curso getCursoMatriculado()
    {
        return cursoMatriculado;
    }

    public void setCursoMatriculado(Curso cursoMatriculado)
    {
        this.cursoMatriculado = cursoMatriculado;
    }

    public int getNumParcelas()
    {
        return numParcelas;
    }

    /**
     * Retorna o tipo do aluno. É sobrescrito em AlunoBolsista (polimorfismo).
     */
    public String getTipo()
    {
        return "Regular";
    }

    /**
     * Percentual de desconto aplicado nas mensalidades.
     * O aluno regular não tem desconto; AlunoBolsista sobrescreve este método.
     */
    public double getPercentualDesconto()
    {
        return 0.0;
    }

    // ===================== EXIBIÇÃO =====================

    /**
     * Imprime todos os dados do aluno.
     */
    public void exibeDados()
    {
        System.out.println("Código: " + codigo + " | Tipo: " + getTipo());
        System.out.println("Nome: " + nome);
        System.out.println("Data de nascimento: " + dataNascimento);
        System.out.println("E-mail: " + email);
        System.out.println("Senha: " + mascararSenha());
        if (cursoMatriculado != null) {
            System.out.println("Curso: " + cursoMatriculado.getNome());
        } else {
            System.out.println("Curso: (não matriculado)");
        }
    }

    /**
     * Mostra a senha mascarada, para não expor o conteúdo no console.
     */
    private String mascararSenha()
    {
        String mascara = "";
        for (int i = 0; i < senha.length(); i++) {
            mascara += "*";
        }
        return mascara;
    }

    // ===================== PARTE 04 - NOTAS =====================

    /**
     * Lança a nota de uma avaliação.
     *
     * @param numeroAvaliacao número da avaliação (1 a 3)
     * @param nota valor da nota (0 a 10)
     */
    public void lancarNotas(int numeroAvaliacao, double nota)
    {
        if (numeroAvaliacao < 1 || numeroAvaliacao > QTD_NOTAS) {
            System.out.println("Avaliação inválida! Escolha de 1 a " + QTD_NOTAS + ".");
            return;
        }
        if (nota < 0 || nota > 10) {
            System.out.println("Nota inválida! A nota deve estar entre 0 e 10.");
            return;
        }
        int indice = numeroAvaliacao - 1;
        if (lancada[indice]) {
            System.out.println("Atenção: a nota " + numeroAvaliacao + " já havia sido lançada e foi atualizada.");
        }
        notas[indice] = nota;
        lancada[indice] = true;
    }

    /**
     * Retorna a média aritmética das 3 notas.
     * Notas ainda não lançadas contam como zero.
     */
    public double calcularMedia()
    {
        double soma = 0;
        for (int i = 0; i < notas.length; i++) {
            soma += notas[i];
        }
        return soma / notas.length;
    }

    /**
     * Verifica se todas as notas já foram lançadas.
     */
    public boolean todasNotasLancadas()
    {
        for (int i = 0; i < lancada.length; i++) {
            if (!lancada[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Mostra as notas e a média do aluno.
     */
    public void exibirNotas()
    {
        System.out.println("Notas de " + nome + " (código " + codigo + "):");
        for (int i = 0; i < notas.length; i++) {
            if (lancada[i]) {
                System.out.printf("  Nota %d: %.2f%n", (i + 1), notas[i]);
            } else {
                System.out.println("  Nota " + (i + 1) + ": não lançada");
            }
        }
        double media = calcularMedia();
        System.out.printf("  Média: %.2f%n", media);

        if (!todasNotasLancadas()) {
            System.out.println("  Situação: EM ANDAMENTO (faltam notas)");
        } else if (media >= 7) {
            System.out.println("  Situação: APROVADO");
        } else if (media >= 5) {
            System.out.println("  Situação: RECUPERAÇÃO");
        } else {
            System.out.println("  Situação: REPROVADO");
        }
    }

    // ===================== PARTE 05 - FINANCEIRO =====================

    /**
     * Inicializa o vetor de mensalidades a partir dos valores informados.
     * O desconto é obtido por getPercentualDesconto(), que muda conforme
     * o tipo do aluno (polimorfismo).
     */
    public void adicionarMensalidades(double[] valores)
    {
        if (valores == null || valores.length == 0) {
            System.out.println("Nenhum valor de mensalidade informado.");
            return;
        }
        int quantidade = valores.length;
        if (quantidade > MAX_PARCELAS) {
            System.out.println("Máximo de " + MAX_PARCELAS + " parcelas. Apenas as primeiras serão usadas.");
            quantidade = MAX_PARCELAS;
        }

        mensalidades = new Mensalidade[quantidade];
        numParcelas = quantidade;
        double fator = 1 - getPercentualDesconto();

        for (int i = 0; i < quantidade; i++) {
            mensalidades[i] = new Mensalidade(valores[i] * fator);
        }
    }

    /**
     * Mostra o valor e o status de cada parcela.
     */
    public void exibirMensalidades()
    {
        System.out.println("Financeiro de " + nome + " (código " + codigo + ")");
        if (numParcelas == 0) {
            System.out.println("  Nenhuma mensalidade cadastrada.");
            return;
        }
        if (getPercentualDesconto() > 0) {
            System.out.printf("  Desconto aplicado: %.0f%%%n", getPercentualDesconto() * 100);
        }

        double totalPago = 0;
        double totalAberto = 0;
        for (int i = 0; i < numParcelas; i++) {
            Mensalidade m = mensalidades[i];
            String status = m.isPago() ? "PAGO" : "EM ABERTO";
            System.out.printf("  Parcela %2d: R$ %8.2f | %s%n", (i + 1), m.getValor(), status);
            if (m.isPago()) {
                totalPago += m.getValor();
            } else {
                totalAberto += m.getValor();
            }
        }
        System.out.printf("  Total pago: R$ %.2f | Total em aberto: R$ %.2f%n", totalPago, totalAberto);
    }

    /**
     * Dá baixa na parcela da posição informada.
     *
     * @param indice posição no vetor (0 a numParcelas - 1)
     */
    public void pagarMensalidade(int indice)
    {
        if (indice < 0 || indice >= numParcelas) {
            System.out.println("Parcela inexistente!");
            return;
        }
        if (mensalidades[indice].isPago()) {
            System.out.println("Esta parcela já está paga.");
            return;
        }
        mensalidades[indice].darBaixa();
    }
}
