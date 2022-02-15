package uminho.dss.sistema_gestao.business.gestaoArmazem;

/**
 * @author Grupo20
 */

public class Palete {
    private String codPalete;
    private String localizacao;

    public Palete(String qrcode, String localizacao) {
        this.codPalete = qrcode;
        this.localizacao = localizacao;
    }

    public Palete(String qrcode) {
        this.codPalete = qrcode;
    }

    public String getCodPalete() {
        return this.codPalete;
    }

    public String getLocalizacao() {
        return this.localizacao;
    }

    public void setCodPrateleira(String codPalete) {
        this.codPalete = codPalete;
    }

    public void setLocalizacao(String newlocalizacao) {
        this.localizacao = newlocalizacao;
    }
}
