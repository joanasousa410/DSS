package uminho.dss.sistema_gestao.business.gestaoGestores;

import java.util.Map;

/**
 * @author Grupo20
 */

public interface ISubGestor {
    Map<String, Gestor> getGestores();

    String checkEstado(String codgestor);

    boolean setEstadoGestor(String codgestor, String estado_antes, String estado_depois);

    boolean existeGestores();

    String loginGestor(String login, String pwd);

    void registarGestor(String codGestor, String login, String pwd);

}
