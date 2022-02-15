package uminho.dss.sistema_gestao.business.gestaoGestores;

/**
 * @author Grupo20
 */

public class Gestor {
    // código identificador do gestor
    private String codGestor;
    // login escolhido pelo gestor
    private String login;
    // password escolhido pelo gestor
    private String pwd;
    // estado do gestor ("ONLINE" || "OFFLINE")
    private String estado;

    public Gestor() {
        this.codGestor = null;
        this.login = null;
        this.pwd = null;
        this.estado = null;
    }

    public Gestor(String codGestor, String login, String pwd, String estado) {
        this.codGestor = codGestor;
        this.login = login;
        this.pwd = pwd;
        this.estado = estado;
    }

    // ------------GET'S------------

    public String getCodGestor() {
        return this.codGestor;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPwd() {
        return this.pwd;
    }

    public String getEstado() {
        return this.estado;
    }

    // ------------SET'S------------

    public void setCodGestor(String codGestor) {
        this.codGestor = codGestor;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
