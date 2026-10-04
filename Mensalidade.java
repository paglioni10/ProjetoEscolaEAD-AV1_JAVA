/**
 * Classe Mensalidade - representa uma parcela do aluno (Parte 05).
 *
 * @author Nicolas
 * @version 1.0
 */
public class Mensalidade
{
    private double valor;
    private boolean pago;

    public Mensalidade(double valor)
    {
        setValor(valor);
        // parcela de valor zero (bolsa integral) já nasce quitada
        this.pago = (valor == 0);
    }

    public double getValor()
    {
        return valor;
    }

    public void setValor(double valor)
    {
        if (valor < 0) {
            throw new IllegalArgumentException("O valor da mensalidade não pode ser negativo.");
        }
        this.valor = valor;
    }

    public boolean isPago()
    {
        return pago;
    }

    public void setPago(boolean pago)
    {
        this.pago = pago;
    }

    /**
     * Marca a mensalidade como paga.
     */
    public void darBaixa()
    {
        this.pago = true;
    }
}
