/**
 * Classe Curso - representa um curso oferecido pela Escola EAD (Parte 02).
 *
 * @author Nicolas
 * @version 1.0
 */
public class Curso
{
    private int codigo;
    private String nome;
    private int duracao; // em horas

    public Curso(int codigo, String nome, int duracao)
    {
        setCodigo(codigo);
        setNome(nome);
        setDuracao(duracao);
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        if (codigo <= 0) {
            throw new IllegalArgumentException("O código do curso deve ser maior que zero.");
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
            throw new IllegalArgumentException("O nome do curso não pode ficar em branco.");
        }
        this.nome = nome.trim();
    }

    public int getDuracao()
    {
        return duracao;
    }

    public void setDuracao(int duracao)
    {
        if (duracao <= 0) {
            throw new IllegalArgumentException("A duração deve ser maior que zero.");
        }
        this.duracao = duracao;
    }

    public void exibeDados()
    {
        System.out.println("Curso: " + nome + " | Código: " + codigo + " | Duração: " + duracao + "h");
    }
}
