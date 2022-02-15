package uminho.dss.sistema_gestao.business.gestaoArmazem;

/**
 * @author Grupo20
 */

public class Requisicao {
    // identificar a requisicao
    private String codRequisicao;
    // identificar o código da palete associada à requisicao
    private String codPalete;
    // identificar o estado da requisicao ("PENDENTE" || "ENTREGUE")
    private String estado;

    public Requisicao() {
        this.codRequisicao = null;
        this.codPalete = null;
        this.estado = null;
    }

    public Requisicao(String codRequisicao, String codPalete) {
        this.codRequisicao = codRequisicao;
        this.codPalete = codPalete;
        this.estado = "Pendente";
    }

    public Requisicao(String codRequisicao, String codPalete, String estado) {
        this.codRequisicao = codRequisicao;
        this.codPalete = codPalete;
        this.estado = estado;
    }

    // ------------GET'S------------

    public String getCodRequisicao() {
        return this.codRequisicao;
    }

    public String getPalete() {
        return this.codPalete;
    }

    public String getEstado() {
        return this.estado;
    }

    // ------------SET'S------------

    public void setCodRequisicao(String codRequisicao) {
        this.codRequisicao = codRequisicao;
    }

    public void setCodPalete(String codPalete) {
        this.codPalete = codPalete;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
