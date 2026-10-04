/**
 * Classe ListaDeAlunos - gerencia os alunos em um array unidimensional (Parte 03).
 *
 * O vetor é do tipo Aluno[], então pode guardar tanto Aluno quanto
 * AlunoBolsista (polimorfismo).
 *
 * @author Nicolas
 * @version 1.0
 */
public class ListaDeAlunos
{
    private Aluno[] alunos;   // array unidimensional obrigatório
    private int totalAlunos;

    public ListaDeAlunos(int capacidade)
    {
        if (capacidade <= 0) {
            capacidade = 10;
        }
        alunos = new Aluno[capacidade];
        totalAlunos = 0;
    }

    /**
     * Adiciona um aluno, verificando duplicidade por código e capacidade.
     *
     * @return true se o aluno foi adicionado
     */
    public boolean adicionarAluno(Aluno a)
    {
        if (a == null) {
            return false;
        }
        if (totalAlunos >= alunos.length) {
            System.out.println("Lista cheia! Capacidade máxima: " + alunos.length + " alunos.");
            return false;
        }
        if (buscarPorCodigo(a.getCodigo()) != null) {
            System.out.println("Já existe um aluno com o código " + a.getCodigo() + ".");
            return false;
        }
        alunos[totalAlunos] = a;
        totalAlunos++;
        return true;
    }

    /**
     * Percorre o vetor e chama exibeDados() de cada aluno.
     * A versão de exibeDados() executada depende do tipo real do objeto.
     */
    public void exibirLista()
    {
        if (totalAlunos == 0) {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }
        for (int i = 0; i < totalAlunos; i++) {
            System.out.println("------------------------------");
            alunos[i].exibeDados();
        }
        System.out.println("------------------------------");
        System.out.println("Total: " + totalAlunos + " aluno(s).");
    }

    /**
     * Busca sequencial pelo código do aluno.
     *
     * @return o aluno encontrado ou null
     */
    public Aluno buscarPorCodigo(int codigo)
    {
        int i = 0;
        while (i < totalAlunos) {
            if (alunos[i].getCodigo() == codigo) {
                return alunos[i];
            }
            i++;
        }
        return null;
    }

    /**
     * Mostra os alunos matriculados em um curso.
     *
     * @return quantidade de alunos encontrados
     */
    public int exibirAlunosDoCurso(Curso curso)
    {
        int encontrados = 0;
        for (int i = 0; i < totalAlunos; i++) {
            if (alunos[i].getCursoMatriculado() == curso) {
                System.out.println("  - " + alunos[i].getNome() + " (código " + alunos[i].getCodigo() + ")");
                encontrados++;
            }
        }
        return encontrados;
    }

    public int getTotalAlunos()
    {
        return totalAlunos;
    }

    public int getCapacidade()
    {
        return alunos.length;
    }
}
