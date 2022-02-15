package uminho.dss.sistema_gestao.data;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uminho.dss.sistema_gestao.business.gestaoGestores.Gestor;

/**
 * @author Grupo20
 */

public class GestoresDAO implements Map<String, Gestor> {
    private static GestoresDAO singleton = null;

    private GestoresDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {

            // trajeto é string de 500 chars, aumentar se armazém for de grande dimensão
            String sql = "CREATE TABLE IF NOT EXISTS Gestores (" + "CodGestor varchar(45) NOT NULL PRIMARY KEY,"
                    + "Login varchar(45) DEFAULT NULL," + "Pwd varchar(45) DEFAULT NULL,"
                    + "Estado varchar(45) DEFAULT NULL)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static GestoresDAO getInstance() {
        if (GestoresDAO.singleton == null) {
            GestoresDAO.singleton = new GestoresDAO();
        }
        return GestoresDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT count(*) FROM Gestores")) {
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
                ResultSet rs = stm
                        .executeQuery("SELECT CodGestor FROM Gestores WHERE CodGestor='" + key.toString() + "'")) {
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
        Gestor p = (Gestor) value;
        return this.containsKey(p.getCodGestor());
    }

    @Override
    public Gestor get(Object key) {
        Gestor p = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM Gestores WHERE Login='" + key + "'")) {
            if (rs.next()) { // A chave existe na tabela

                // Reconstruir o Gestor com os dados obtidos da BD
                p = new Gestor(rs.getString("CodGestor"), rs.getString("Login"), rs.getString("Pwd"),
                        rs.getString("Estado"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    @Override
    public Gestor put(String key, Gestor value) {
        Gestor res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {

            // Actualizar a Gestor
            stm.executeUpdate(
                    "INSERT INTO Gestores VALUES ('" + value.getCodGestor() + "','" + key + "','" + value.getPwd()
                            + "','" + value.getEstado() + "')" + "ON DUPLICATE KEY UPDATE CodGestor=Values(CodGestor), "
                            + "Login=Values(Login), " + "Pwd=Values(Pwd), " + "Estado=Values(Estado)");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Gestor remove(Object key) {
        Gestor t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {

            // apagar a Gestor
            stm.executeUpdate("DELETE FROM Gestores WHERE CodGestor='" + key + "'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Gestor> m) {
        for (Gestor t : m.values()) {
            this.put(t.getCodGestor(), t);
        }
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE Gestores");
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
    public Collection<Gestor> values() {
        Collection<Gestor> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT CodGestor FROM Gestores")) { // ResultSet com os ids de todas as
                                                                                     // Gestores
            while (rs.next()) {
                String idt = rs.getString("CodGestor"); // Obtemos um id de Gestor do ResultSet
                Gestor t = this.get(idt); // Utilizamos o get para construir as Gestores uma a uma
                res.add(t); // Adiciona a Gestor ao resultado.
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Entry<String, Gestor>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Gestor>> entrySet() not implemented!");
    }

}
