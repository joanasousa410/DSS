package uminho.dss.sistema_gestao.business.gestaoArmazem;

import java.util.Map;

/**
 * @author Grupo20
 */

public interface ISubArmazem {

    boolean existePrateleiras();

    boolean existePaletes();

    void adicionaPalete(String qrcode);

    Map<String, Prateleira> getPrateleiras();

    void removePaletePrateleira(String qrcode, String codPrateleira);

    void atualizaLocalizacaoPalete(String qrcode, String localizacao);

    Map<String, Palete> getPaletes();

    void removePalete(String qrcode);

    Prateleira getPrateleira(String codPonto_Entrega);

    Palete getPalete(String qrcode);

    String calculaTrajetoTotal(String local_Robot, String local_palete, String local_entrega);

    boolean existePrateleira(String entrega);

    void adicionaPaletePrateleira(String qrcode, String codPonto_Entrega);

    boolean existeLocalEntrega(String codPonto_Entrega);

    void adicionaRequisicao(String codReq, String palete);

    Map<String, Requisicao> getRequisicoes();

    void removeRequisicao(String codReq);

    boolean existeRequisicoes();

    boolean existePaletePrateleira(String codPonto_Entrega);
}
