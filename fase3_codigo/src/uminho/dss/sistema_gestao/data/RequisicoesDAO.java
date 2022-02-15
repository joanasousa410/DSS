package uminho.dss.sistema_gestao.data;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uminho.dss.sistema_gestao.business.gestaoArmazem.Requisicao;

/**
 * @author Grupo20
 */

public class RequisicoesDAO implements Map<String, Requisicao> {
    private static RequisicoesDAO singleton = null;

    private RequisicoesDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS requisicoes (" + "CodRequisicao varchar(45) NOT NULL,"
                    + "CodPalete varchar(45) DEFAULT NULL," + "Estado varchar(45) DEFAULT NULL)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static RequisicoesDAO getInstance() {
        if (RequisicoesDAO.singleton == null) {
            RequisicoesDAO.singleton = new RequisicoesDAO();
        }
        return RequisicoesDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT count(*) FROM requisicoes")) {
            if (rs.next()) {
                i = rs.getInt(1);
            }
        } catch (Exception e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return i;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean r;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(
                        "SELECT CodRequisicao FROM requisicoes WHERE CodRequisicao='" + key.toString() + "'")) {
            r = rs.next();
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return r;
    }

    @Override
    public boolean containsValue(Object value) {
        Requisicao p = (Requisicao) value;
        return this.containsKey(p.getCodRequisicao());
    }

    @Override
    public Requisicao get(Object key) {
        Requisicao p = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM requisicoes WHERE CodRequisicao='" + key + "'")) {
            if (rs.next()) { // A chave existe na tabela

                // Reconstruir a Requisicao com os dados obtidos da BD
                p = new Requisicao(rs.getString("CodRequisicao"), rs.getString("CodPalete"), rs.getString("Estado"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    @Override
    public Requisicao put(String key, Requisicao value) {
        Requisicao res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {

            // Actualizar a Requisicao
            stm.executeUpdate("INSERT INTO requisicoes VALUES ('" + value.getCodRequisicao() + "', '"
                    + value.getPalete() + "', '" + value.getEstado() + "')"
                    + "ON DUPLICATE KEY UPDATE CodRequisicao=Values(CodRequisicao), " + "CodPalete=Values(CodPalete)");

        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Requisicao remove(Object key) {
        Requisicao t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {

            // apagar a Requisicao
            stm.executeUpdate("DELETE FROM requisicoes WHERE CodRequisicao='" + key + "'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Requisicao> m) {
        for (Requisicao t : m.values()) {
            this.put(t.getCodRequisicao(), t);
        }
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE requisicoes");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Set<String> keySet() {
        throw new NullPointerException("public Set<String> keySet() not implemented!");
    }

    @Override
    public Collection<Requisicao> values() {
        Collection<Requisicao> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT CodRequisicao FROM requisicoes")) { // ResultSet com os ids de
                                                                                            // todas as
            // requisicoes
            while (rs.next()) {
                String idt = rs.getString("CodRequisicao"); // Obtemos um id de Requisicao do ResultSet
                Requisicao t = this.get(idt); // Utilizamos o get para construir as requisicoes uma a uma
                res.add(t); // Adiciona a Requisicao ao resultado.
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Entry<String, Requisicao>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Requisicao>> entrySet() not implemented!");
    }

}
