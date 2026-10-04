/**
 * Classe AlunoBolsista - subclasse de Aluno (Parte 03).
 *
 * Demonstra herança (extends + super) e sobrescrita de métodos
 * (exibeDados, getTipo e getPercentualDesconto).
 *
 * @author Nicolas
 * @version 1.0
 */
public class AlunoBolsista extends Aluno
{
    private String tipoBolsa;

    /**
     * Construtor que reaproveita o construtor da superclasse com super(...).
     */
    public AlunoBolsista(int codigo, String nome, String dataNascimento,
                         String email, String senha, String tipoBolsa)
    {
        super(codigo, nome, dataNascimento, email, senha);
        setTipoBolsa(tipoBolsa);
    }

    public String getTipoBolsa()
    {
        return tipoBolsa;
    }

    public void setTipoBolsa(String tipoBolsa)
    {
        if (tipoBolsa == null || tipoBolsa.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo de bolsa não pode ficar em branco.");
        }
        this.tipoBolsa = tipoBolsa.trim();
    }

    @Override
    public String getTipo()
    {
        return "Bolsista";
    }

    /**
     * O desconto depende do tipo de bolsa.
     * Integral = 100%, Parcial = 50%, qualquer outro tipo = 25%.
     */
    @Override
    public double getPercentualDesconto()
    {
        String tipo = tipoBolsa.toLowerCase();
        if (tipo.startsWith("integral")) {
            return 1.0;
        } else if (tipo.startsWith("parcial")) {
            return 0.5;
        }
        return 0.25;
    }

    /**
     * Sobrescreve exibeDados() para incluir o tipo de bolsa.
     */
    @Override
    public void exibeDados()
    {
        super.exibeDados();
        System.out.printf("Tipo de bolsa: %s (desconto de %.0f%%)%n",
                          tipoBolsa, getPercentualDesconto() * 100);
    }
}
