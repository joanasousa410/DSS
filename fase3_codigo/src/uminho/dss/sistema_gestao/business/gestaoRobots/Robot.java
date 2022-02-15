package uminho.dss.sistema_gestao.business.gestaoRobots;

/**
 * @author Grupo20
 */

public class Robot {
    // Código do robot
    private String codRobot;
    // varíavel de estado (Livre, Ocupado, Recolheu, Entregou)
    private String estado;
    // variável de estado do trajeto a realizar
    // (para permitir guardar o estado do trajeto)
    private String trajeto;
    // identificar qual o corredor em que se encontra?
    private String localizacao;

    public Robot() {
        this.codRobot = null;
        this.estado = null;
        this.trajeto = null;
        this.localizacao = null;
    }

    public Robot(String codRobot, String estado, String trajeto, String localizacao) {
        this.codRobot = codRobot;
        this.estado = estado;
        this.trajeto = trajeto;
        this.localizacao = localizacao;
    }

    // ------------GET'S------------

    public String getCodRobot() {
        return this.codRobot;
    }

    public String getEstado() {
        return this.estado;
    }

    public String getTrajeto() {
        return this.trajeto;
    }

    public String getLocalizacao() {
        return this.localizacao;
    }

    // ------------SET'S------------

    public void setCodRobot(String codRobot) {
        this.codRobot = codRobot;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setTrajeto(String trajeto) {
        this.trajeto = trajeto;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
