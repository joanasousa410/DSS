package uminho.dss.sistema_gestao.business.gestaoRobots;

import java.util.Map;

/**
 * @author Grupo20
 */

public interface ISubRobot {

    String checkEstado(String codRobot);

    boolean setEstadoRobot(String codRobot, String estado_antes, String estado_depois);

    void setTrajetoRobot(String codRobot, String trajeto);

    Map<String, Robot> getRobots();

    void setLocalizacaoRobot(String codRobot, String localizacao);

    boolean existeRobotsDisponiveis();

}
