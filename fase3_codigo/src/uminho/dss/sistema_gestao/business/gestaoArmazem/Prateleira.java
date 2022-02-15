package uminho.dss.sistema_gestao.business.gestaoArmazem;

/**
 * @author Grupo20
 */

public class Prateleira {
    // codigo da prateleira
    private String codPrateleira;

    // localizacao da prateleira
    private String localizacao;

    // lista de códigos qr das paletes na prateleira (só um neste momento)
    private String qr_palete; //

    public Prateleira(String codPrateleira, String localizacao) {
        this.codPrateleira = codPrateleira;
        this.localizacao = localizacao;
        this.qr_palete = null;
    }

    public Prateleira(String codPrateleira, String localizacao, String qr_palete) {
        this.codPrateleira = codPrateleira;
        this.localizacao = localizacao;
        this.qr_palete = qr_palete;
    }

    // ------------GET'S------------

    public String getCodPrateleira() {
        return this.codPrateleira;
    }

    public String getLocalizacao() {
        return this.localizacao;
    }

    public String getPalete() {
        return this.qr_palete;
    }

    // ------------SET'S------------

    public void setCodPrateleira(String codPrateleira) {
        this.codPrateleira = codPrateleira;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setPalete(String palete) {
        this.qr_palete = palete;
    }

}
