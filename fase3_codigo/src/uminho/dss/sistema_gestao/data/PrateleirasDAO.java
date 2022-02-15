package uminho.dss.sistema_gestao.data;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uminho.dss.sistema_gestao.business.gestaoArmazem.Prateleira;

/**
 * @author Grupo20
 */

public class PrateleirasDAO implements Map<String, Prateleira> {
    private static PrateleirasDAO singleton = null;

    private PrateleirasDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS prateleiras (" + "CodPrateleira varchar(45) NOT NULL PRIMARY KEY,"
                    + "Localizacao varchar(45) DEFAULT NULL," + "CodPalete varchar(45) DEFAULT NULL)";
            // + "codPalete varchar(45) DEFAULT NULL)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static PrateleirasDAO getInstance() {
        if (PrateleirasDAO.singleton == null) {
            PrateleirasDAO.singleton = new PrateleirasDAO();
        }
        return PrateleirasDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT count(*) FROM prateleiras")) {
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
                        "SELECT CodPrateleira FROM prateleiras WHERE CodPrateleira='" + key.toString() + "'")) {
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
        Prateleira p = (Prateleira) value;
        return this.containsKey(p.getCodPrateleira());
    }

    @Override
    public Prateleira get(Object key) {
        Prateleira p = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM prateleiras WHERE CodPrateleira='" + key + "'")) {
            if (rs.next()) { // A chave existe na tabela
                // Reconstruir a Prateleira com os dados obtidos da BD
                p = new Prateleira(rs.getString("CodPrateleira"), rs.getString("Localizacao"),
                        rs.getString("CodPalete"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    @Override
    public Prateleira put(String key, Prateleira value) {
        Prateleira res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {
            stm.executeUpdate("INSERT INTO prateleiras VALUES ('" + value.getCodPrateleira() + "','"
                    + value.getLocalizacao() + "','" + value.getPalete() + "')"
                    + "ON DUPLICATE KEY UPDATE CodPrateleira=Values(CodPrateleira), "
                    + "Localizacao=Values(Localizacao), " + "CodPalete=Values(CodPalete)");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Prateleira remove(Object key) {
        Prateleira t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {

            // apagar a turma
            stm.executeUpdate("DELETE FROM prateleiras WHERE CodPrateleira='" + key + "'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Prateleira> m) {
        for (Prateleira t : m.values()) {
            this.put(t.getCodPrateleira(), t);
        }
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE prateleiras");
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
    public Collection<Prateleira> values() {
        Collection<Prateleira> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT CodPrateleira FROM prateleiras")) { // ResultSet com os ids de
                                                                                            // todas as
            // prateleiras
            while (rs.next()) {
                String idt = rs.getString("CodPrateleira"); // Obtemos um id de Prateleira do ResultSet
                Prateleira t = this.get(idt); // Utilizamos o get para construir as prateleiras uma a uma
                res.add(t); // Adiciona a Prateleira ao resultado.
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Entry<String, Prateleira>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Prateleira>> entrySet() not implemented!");
    }
}