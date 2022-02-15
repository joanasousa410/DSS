package uminho.dss.sistema_gestao.business.gestaoGestores;

import java.util.Map;

import uminho.dss.sistema_gestao.data.GestoresDAO;

/**
 * @author Grupo20
 */

public class SubGestor implements ISubGestor {
    // lista de gestores (neste caso só iremos ter um)
    private Map<String, Gestor> gestores;

    public SubGestor() {
        this.gestores = GestoresDAO.getInstance();
        adicionarGestores();
    }

    private void adicionarGestores() {
        // criar gestors
        Gestor gestor1 = new Gestor();
        gestor1.setCodGestor("GestorCode_1");
        gestor1.setLogin("gestor1");
        gestor1.setPwd("pass1");
        gestor1.setEstado("OFFLINE");
        // criar gestors
        Gestor gestor2 = new Gestor();
        gestor2.setCodGestor("GestorCode_2");
        gestor2.setLogin("gestor2");
        gestor2.setPwd("pass2");
        gestor2.setEstado("OFFLINE");
        // criar gestors
        Gestor gestor3 = new Gestor();
        gestor3.setCodGestor("GestorCode_3");
        gestor3.setLogin("gestor3");
        gestor3.setPwd("pass3");
        gestor3.setEstado("OFFLINE");
        // criar gestors
        Gestor gestor4 = new Gestor();
        gestor4.setCodGestor("GestorCode_4");
        gestor4.setLogin("gestor4");
        gestor4.setPwd("pass4");
        gestor4.setEstado("OFFLINE");
        // criar gestors
        Gestor gestor5 = new Gestor();
        gestor5.setCodGestor("GestorCode_5");
        gestor5.setLogin("gestor5");
        gestor5.setPwd("pass5");
        gestor5.setEstado("OFFLINE");

        // adicionar gestor à lista
        // estes puts atualizam os gestores (comentar se não se quiser atualizar)
        this.gestores.put(gestor1.getLogin(), gestor1);
        this.gestores.put(gestor2.getLogin(), gestor2);
        this.gestores.put(gestor3.getLogin(), gestor3);
        this.gestores.put(gestor4.getLogin(), gestor4);
        this.gestores.put(gestor5.getLogin(), gestor5);
    }

    // check ao estado do referido gestor
    public String checkEstado(String login) {
        return gestores.get(login).getEstado();
    }

    public boolean setEstadoGestor(String login, String estado_antes, String estado_depois) {
        // verifica se o gestor existe (não itera mapa)
        if (this.gestores.get(login) != null && this.gestores.get(login).getEstado().equals(estado_antes)) {
            // login sucedido e atualiza bd
            Gestor gestor = new Gestor(this.gestores.get(login).getCodGestor(), login,
                    this.gestores.get(login).getPwd(), estado_depois);
            this.gestores.put(login, gestor);
            return true;
        }
        // gestor não existe
        return false;

    }

    @Override
    public Map<String, Gestor> getGestores() {
        return this.gestores;
    }

    @Override
    public boolean existeGestores() {
        return !this.gestores.isEmpty();
    }

    @Override
    public String loginGestor(String login, String pwd) {
        // verifica se o gestor existe (não itera mapa)
        if (this.gestores.get(login) != null && this.gestores.get(login).getPwd().equals(pwd)) {
            // login sucedido
            return this.gestores.get(login).getLogin();
        }
        // não encontrou, retorna null
        return null;
    }

    @Override
    public void registarGestor(String codGestor, String login, String pwd) {
        // como verificação já foi feita na interface, é só criar gestor e adicionar
        Gestor gestor = new Gestor(codGestor, login, pwd, "OFFLINE");
        this.gestores.put(login, gestor);
    }

}
