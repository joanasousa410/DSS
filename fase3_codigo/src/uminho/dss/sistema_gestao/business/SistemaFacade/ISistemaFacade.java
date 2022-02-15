package uminho.dss.sistema_gestao.business.SistemaFacade;

import java.util.Map;

import uminho.dss.sistema_gestao.business.gestaoArmazem.Palete;
import uminho.dss.sistema_gestao.business.gestaoArmazem.Prateleira;
import uminho.dss.sistema_gestao.business.gestaoArmazem.Requisicao;
import uminho.dss.sistema_gestao.business.gestaoRobots.Robot;
import uminho.dss.sistema_gestao.business.gestaoGestores.Gestor;

/**
 * @author Grupo20
 */

public interface ISistemaFacade {
    boolean existePaletes();

    boolean existePaletesTransportar();

    boolean robotRecolheu();

    boolean robotEntregou();

    void adicionaPalete(String qrcode);

    void adicionaPaleteTransportar(String qrcode);

    boolean setEstadoRobot(String codRobot, String estado_antes, String estado_depois);

    void removePaleteTransportar(String qrcode);

    void removePaletePrateleira(String qrcode, String codPrateleira);

    void atualizaLocalizacaoPalete(String qrcode, String codRobot);

    Map<String, Palete> getPaletes();

    void removePalete(String qrcode);

    Palete getPalete(String qrcode);

    String calculaTrajeto(String codRobot, String qrcode, String entrega);

    void setTrajetoRobot(String codRobot, String trajeto);

    boolean existePrateleira(String entrega);

    void adicionaPaletePrateleira(String qrcode, String codPonto_Entrega);

    boolean existeLocalEntrega(String codPonto_Entrega);

    Map<String, Robot> getRobots();

    void setLocalizacaoRobot(String codRobot, String localizacao);

    boolean existeRobotsDisponiveis();

    Map<String, Prateleira> getPrateleiras();

    boolean existeGestores();

    Map<String, Gestor> getGestores();

    void setEstadoGestor(String login, String estado_antes, String estado_depois);

    String loginGestor(String login, String pwd);

    void registarGestor(String codGestor, String login, String pwd);

    void adicionaRequisicao(String codReq, String palete);

    Map<String, Requisicao> getRequisicoes();

    void removeRequisicao(String codReq);

    boolean existeRequisicoes();

    boolean existePaletePrateleira(String codPonto_Entrega);

}
