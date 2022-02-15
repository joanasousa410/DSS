package uminho.dss.sistema_gestao.business.SistemaFacade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import uminho.dss.sistema_gestao.business.gestaoArmazem.*;
import uminho.dss.sistema_gestao.business.gestaoRobots.*;
import uminho.dss.sistema_gestao.business.gestaoGestores.*;

/**
 * @author Grupo20
 */

public class SistemaFacade implements ISistemaFacade {

    // lista de paletes a transportar
    private List<String> paletesTransportar;

    // subsystems
    private ISubArmazem gestaoArmazem;
    private ISubRobot gestaoRobots;
    private ISubGestor gestaoGestores;

    // criar os subsistemas ao criar o sistemafacade
    public SistemaFacade() {
        // criar os subsistemas
        this.gestaoArmazem = new SubArmazem();
        this.gestaoRobots = new SubRobot();
        this.gestaoGestores = new SubGestor();

        // listagem de paletes a transportar no sistema (depois de passarem o tapete
        // rolante)
        this.paletesTransportar = new ArrayList<>();
        // ler as paletes do armazém
        for (Palete p : this.gestaoArmazem.getPaletes().values()) {
            // caso a localização dela seja no ponto de recolha
            if (p.getLocalizacao().equals("ponto_recolha")) {
                // adiciona a paletesTransportar
                this.paletesTransportar.add(p.getCodPalete());
            }
        }
    }

    // métodos das pré condições
    public boolean existePaletes() {
        return this.gestaoArmazem.existePaletes();
    }

    public boolean existePrateleiras() {
        return this.gestaoArmazem.existePrateleiras();
    }

    public boolean existeGestores() {
        return this.gestaoGestores.existeGestores();
    }

    public boolean robotEntregou() {
        // iterar todos os robots do sistema para verificar o seu código
        for (Robot r : this.gestaoRobots.getRobots().values()) {
            // verifica se o estado é o de "Entregou"
            if (r.getEstado().equals("Entregou")) {
                // retorna se encontrar
                return true;
            }
        }
        // percorreu todo o mapa e não encontrou nenhum
        return false;
    }

    public boolean robotRecolheu() {
        // iterar todos os robots do sistema para verificar o seu código
        for (Robot r : this.gestaoRobots.getRobots().values()) {
            // verifica se o estado é o de "Entregou"
            if (r.getEstado().equals("Recolheu")) {
                // retorna se encontrar
                return true;
            }
        }
        // percorreu todo o mapa e não encontrou nenhum
        return false;
    }

    public boolean existePaletesTransportar() {
        return (paletesTransportar.size() > 0);
    }

    // métodos

    public void adicionaPalete(String qrcode) {
        this.gestaoArmazem.adicionaPalete(qrcode);
    }

    public Map<String, Prateleira> getPrateleiras() {
        return this.gestaoArmazem.getPrateleiras();
    }

    // dá return de um boolean para avisar se alterou o estado do robot
    public boolean setEstadoRobot(String codRobot, String estado_antes, String estado_depois) {
        return this.gestaoRobots.setEstadoRobot(codRobot, estado_antes, estado_depois);
    }

    public void adicionaPaleteTransportar(String qrcode) {
        paletesTransportar.add(qrcode);
    }

    public void removePaleteTransportar(String qrcode) {
        paletesTransportar.remove(qrcode);
    }

    public void removePaletePrateleira(String qrcode, String codPrateleira) {
        this.gestaoArmazem.removePaletePrateleira(qrcode, codPrateleira);
    }

    public void atualizaLocalizacaoPalete(String qrcode, String localizacao) {
        this.gestaoArmazem.atualizaLocalizacaoPalete(qrcode, localizacao);
    }

    public Map<String, Palete> getPaletes() {
        return this.gestaoArmazem.getPaletes();
    }

    @Override
    public void removePalete(String qrcode) {
        this.gestaoArmazem.removePalete(qrcode);
    }

    @Override
    public Palete getPalete(String qrcode) {
        return this.gestaoArmazem.getPalete(qrcode);
    }

    @Override
    public String calculaTrajeto(String codRobot, String qrcode, String local_entrega) {
        String local_Robot = this.gestaoRobots.getRobots().get(codRobot).getLocalizacao();
        String local_palete = this.gestaoArmazem.getPalete(qrcode).getLocalizacao();
        return this.gestaoArmazem.calculaTrajetoTotal(local_Robot, local_palete, local_entrega);
    }

    @Override
    public void setTrajetoRobot(String codRobot, String trajeto) {
        this.gestaoRobots.setTrajetoRobot(codRobot, trajeto);
    }

    @Override
    public boolean existePrateleira(String entrega) {
        return this.gestaoArmazem.existePrateleira(entrega);
    }

    @Override
    public void adicionaPaletePrateleira(String qrcode, String codPonto_Entrega) {
        this.gestaoArmazem.adicionaPaletePrateleira(qrcode, codPonto_Entrega);
    }

    @Override
    public boolean existeLocalEntrega(String codPonto_Entrega) {
        return this.gestaoArmazem.existeLocalEntrega(codPonto_Entrega);
    }

    @Override
    public Map<String, Robot> getRobots() {
        return this.gestaoRobots.getRobots();
    }

    @Override
    public void setLocalizacaoRobot(String codRobot, String localizacao) {
        this.gestaoRobots.setLocalizacaoRobot(codRobot, localizacao);
    }

    @Override
    public boolean existeRobotsDisponiveis() {
        return this.gestaoRobots.existeRobotsDisponiveis();
    }

    @Override
    public Map<String, Gestor> getGestores() {
        return this.gestaoGestores.getGestores();
    }

    @Override
    public void setEstadoGestor(String login, String estado_antes, String estado_depois) {
        this.gestaoGestores.setEstadoGestor(login, estado_antes, estado_depois);
    }

    @Override
    public String loginGestor(String login, String pwd) {
        return this.gestaoGestores.loginGestor(login, pwd);
    }

    @Override
    public void registarGestor(String codGestor, String login, String pwd) {
        this.gestaoGestores.registarGestor(codGestor, login, pwd);
    }

    @Override
    public void adicionaRequisicao(String codReq, String palete) {
        this.gestaoArmazem.adicionaRequisicao(codReq, palete);
    }

    @Override
    public Map<String, Requisicao> getRequisicoes() {
        return this.gestaoArmazem.getRequisicoes();
    }

    @Override
    public void removeRequisicao(String codReq) {
        this.gestaoArmazem.removeRequisicao(codReq);
    }

    @Override
    public boolean existeRequisicoes() {
        return this.gestaoArmazem.existeRequisicoes();
    }

    @Override
    public boolean existePaletePrateleira(String codPonto_Entrega) {
        return this.gestaoArmazem.existePaletePrateleira(codPonto_Entrega);
    }

}
