package uminho.dss.sistema_gestao.business.gestaoArmazem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uminho.dss.sistema_gestao.business.gestaoArmazem.Dijkstra.*;

import uminho.dss.sistema_gestao.data.PaletesDAO;
import uminho.dss.sistema_gestao.data.PrateleirasDAO;
import uminho.dss.sistema_gestao.data.RequisicoesDAO;

/**
 * @author Grupo20
 */

public class SubArmazem implements ISubArmazem {

    // profs fizeram assim para tratar disto
    private Map<String, Palete> paletes;
    private Map<String, Prateleira> prateleiras;
    private Map<String, Requisicao> requisicoes;

    // planta do armazém
    // private List<String> corredores; // listagem de corredores que existem
    // private Map<String, Double> comprimentos; // comprimento dos corredores
    // private Map<String, List<String>> cruzamentos; // um cruzamento e a lista de
    // corredores adjacentes
    private Grafo_Armazem plantaArmazem;// grafo do armazém

    // lista de prateleiras de um corredor
    private Map<String, List<Prateleira>> prateleirasCorredor;

    public SubArmazem() {
        this.paletes = PaletesDAO.getInstance();
        this.prateleiras = PrateleirasDAO.getInstance();
        this.requisicoes = RequisicoesDAO.getInstance();
        // planta do armazém depois para ter as prateleiras
        PlantaArmazem();
    }

    // ----------GERAR MAPA DO ARMAZÉM----------
    private void PlantaArmazem() {
        // gerar o grafo
        this.plantaArmazem = new Grafo_Armazem(true);
        this.prateleirasCorredor = new HashMap<>();
        // gerar os vértices do grafo
        // cruzamentos
        Cruzamento cruzamento1 = new Cruzamento("cruzamento1", "cruzamento1");
        Cruzamento cruzamento2 = new Cruzamento("cruzamento2", "cruzamento2");
        Cruzamento cruzamento3 = new Cruzamento("cruzamento3", "cruzamento3");
        Cruzamento cruzamento4 = new Cruzamento("cruzamento4", "cruzamento4");
        // pontos de descarga/recolha
        Cruzamento ponto_recolha = new Cruzamento("ponto_recolha", "ponto_recolha");
        Cruzamento descarga1 = new Cruzamento("descarga1", "descarga1");
        Cruzamento descarga2 = new Cruzamento("descarga2", "descarga2");
        Cruzamento ponto_entrega = new Cruzamento("ponto_entrega", "ponto_entrega");

        // corredores (é necessário definir a orientação)
        // ponto recolha <-> cruzamento1
        plantaArmazem.addEdge(ponto_recolha, cruzamento1, 2);
        plantaArmazem.addEdge(cruzamento1, ponto_recolha, 2);
        // cruzamento1 <-> cruzamento2
        plantaArmazem.addEdge(cruzamento1, cruzamento2, 5);
        plantaArmazem.addEdge(cruzamento2, cruzamento1, 5);
        //
        plantaArmazem.addEdge(cruzamento1, descarga1, 10);
        plantaArmazem.addEdge(descarga1, cruzamento1, 10);
        //
        plantaArmazem.addEdge(descarga1, cruzamento3, 10);
        plantaArmazem.addEdge(cruzamento3, descarga1, 10);
        //
        plantaArmazem.addEdge(cruzamento3, cruzamento4, 5);
        plantaArmazem.addEdge(cruzamento4, cruzamento3, 5);
        //
        plantaArmazem.addEdge(cruzamento3, ponto_entrega, 4.5);
        plantaArmazem.addEdge(ponto_entrega, cruzamento3, 4.5);
        //
        plantaArmazem.addEdge(cruzamento4, ponto_entrega, 4.5);
        plantaArmazem.addEdge(ponto_entrega, cruzamento4, 4.5);
        //
        plantaArmazem.addEdge(cruzamento2, descarga2, 10);
        plantaArmazem.addEdge(descarga2, cruzamento2, 10);
        //
        plantaArmazem.addEdge(descarga2, cruzamento4, 10);
        plantaArmazem.addEdge(cruzamento4, descarga2, 10);

        // adicionar prateleiras aos corredores
        String codPrateleira = "Prateleira";
        List<Prateleira> prateleiras_descarga1 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            // criar string
            StringBuilder sb = new StringBuilder();
            sb.append(codPrateleira).append("Descarga1_").append(i);
            String res = sb.toString();
            // inserir
            prateleiras_descarga1.add(new Prateleira(res, "descarga1"));
            this.prateleiras.put(res, new Prateleira(res, "descarga1"));
        }
        List<Prateleira> prateleiras_descarga2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            // criar string
            StringBuilder sb = new StringBuilder();
            sb.append(codPrateleira).append("Descarga2_").append(i);
            String res = sb.toString();
            // inserir
            prateleiras_descarga2.add(new Prateleira(res, "descarga2"));
            this.prateleiras.put(res, new Prateleira(res, "descarga2"));
        }
        prateleirasCorredor.put("descarga1", prateleiras_descarga1);
        prateleirasCorredor.put("descarga2", prateleiras_descarga2);
    }

    public boolean existePrateleiras() {
        return !this.prateleiras.isEmpty();
    }

    public boolean existePaletes() {
        return !this.paletes.isEmpty();
    }

    public void adicionaPalete(String qrcode) {
        this.paletes.put(qrcode, new Palete(qrcode, "ponto_recolha"));
    }

    public Map<String, Prateleira> getPrateleiras() {
        return this.prateleiras;
    }

    public void removePaletePrateleira(String qrcode, String codPrateleira) {
        // aceder à prateleira
        if (prateleiras.containsKey(codPrateleira)) {
            // verificar se é o qr code certo
            if (prateleiras.get(codPrateleira).getPalete().equals(qrcode)) {
                // criar prateleira nova a atualizar na base de dados
                Prateleira p = new Prateleira(codPrateleira, prateleiras.get(codPrateleira).getLocalizacao());

                // método put atualiza base de dados
                this.prateleiras.put(codPrateleira, p);
            }
        }
    }

    public void atualizaLocalizacaoPalete(String qrcode, String localizacao) {
        if (this.paletes.containsKey(qrcode)) {
            // criar palete nova a atualizar na base de dados
            Palete p = new Palete(qrcode, localizacao);
            // método put atualiza base de dados
            this.paletes.put(qrcode, p);
        }
    }

    public Map<String, Palete> getPaletes() {
        return this.paletes;
    }

    @Override
    public void removePalete(String qrcode) {
        this.paletes.remove(qrcode);
    }

    @Override
    public Prateleira getPrateleira(String codPonto_Entrega) {
        // pegar nas prateleiras
        for (Prateleira prateleira : this.prateleiras.values()) {
            // verifica se o ponto de entrega é uma prateleira
            if (prateleira.getLocalizacao().equals(codPonto_Entrega)) {
                // se encontrar, devolve a prateleira
                return prateleira;
            }
        }
        // caso atravesse o mapa de prateleiras e não encontrar, null
        return null;
    }

    @Override
    public Palete getPalete(String qrcode) {
        return this.paletes.get(qrcode);
    }

    @Override
    // algoritmo para calcular o trajeto mais curto destes 3 locais
    public String calculaTrajetoTotal(String local_robot, String local_palete, String local_entrega) {
        // trajeto: local_robot -> local_palete -> local_entrega
        Cruzamento posicao_robot = this.plantaArmazem.getNode(local_robot);
        Cruzamento posicao_palete = this.plantaArmazem.getNode(local_palete);
        Cruzamento posicao_entrega = this.plantaArmazem.getNode(local_entrega);

        // sb para a string final
        StringBuilder sb = new StringBuilder();
        // reset aos vértices antes de calcular trajeto
        this.plantaArmazem.resetNodesVisited();
        String trajeto1 = calculaTrajeto(posicao_robot, posicao_palete);
        this.plantaArmazem.resetNodesVisited();
        String trajeto2 = calculaTrajeto(posicao_palete, posicao_entrega);

        sb.append(trajeto1).append(trajeto2);

        String trajeto = sb.toString();

        return trajeto;
    }

    private String calculaTrajeto(Cruzamento inicial, Cruzamento destino) {
        return this.plantaArmazem.DijkstraShortestPath(inicial, destino);
    }

    @Override
    public boolean existePrateleira(String entrega) {
        // ver todas as prateleiras que existem
        for (List<Prateleira> prateleiras : prateleirasCorredor.values()) {
            for (Prateleira prateleira : prateleiras) {
                // verificar o cod da prateleira
                if (prateleira.getCodPrateleira().equals(entrega)) {
                    // se for o mesmo do destino de entrega
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void adicionaPaletePrateleira(String qrcode, String codPonto_Entrega) {
        if (this.prateleiras.containsKey(codPonto_Entrega)) {
            // criar prateleira nova a atualizar na base de dados
            Prateleira p = new Prateleira(codPonto_Entrega, this.prateleiras.get(codPonto_Entrega).getLocalizacao(),
                    qrcode);
            // método put atualiza base de dados
            this.prateleiras.put(codPonto_Entrega, p);
        }
    }

    @Override
    public boolean existeLocalEntrega(String codPonto_Entrega) {
        // tem de ser local de descarga ou pontos de recolha/entrega
        boolean res = false;
        switch (codPonto_Entrega) {
            case "ponto_recolha":
                res = true;
                break;
            case "ponto_entrega":
                res = true;
                break;
            // TODO: ao adicionar locais de descarga, é necessário atualizar isto
            case "descarga1":
                res = true;
                break;
            case "descarga2":
                res = true;
                break;
            default:
        }
        // iterou mapa todo e não existe
        return res;
    }

    @Override
    public void adicionaRequisicao(String codReq, String palete) {
        Requisicao req = new Requisicao(codReq, palete);
        this.requisicoes.put(codReq, req);
    }

    @Override
    public Map<String, Requisicao> getRequisicoes() {
        return this.requisicoes;
    }

    @Override
    public void removeRequisicao(String codReq) {
        this.requisicoes.remove(codReq);
    }

    @Override
    public boolean existeRequisicoes() {
        return !this.requisicoes.isEmpty();
    }

    @Override
    public boolean existePaletePrateleira(String codPonto_Entrega) {
        // se for ponto_entrega, a prateleira não existe
        if (codPonto_Entrega.equals("ponto_entrega")) {
            return false;
        }
        // procurar a prateleira
        for (Prateleira p : prateleiras.values()) {
            // verificar o ponto de entrega
            if (p.getCodPrateleira().equals(codPonto_Entrega)) {
                // verificar se tem a palete
                if (p.getPalete() != null) {
                    return true;
                }
                // encontrou a prateleira, então break do ciclo
                break;
            }
        }
        return false;
    }
}
