package uminho.dss.sistema_gestao.data;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import uminho.dss.sistema_gestao.business.gestaoRobots.Robot;

/**
 * @author Grupo20
 */

public class RobotsDAO implements Map<String, Robot> {
    // tamanho da string do trajeto. Aumentar dependendo do tamanho do Armazém
    private static Integer max_char = 150;

    private static RobotsDAO singleton = null;

    private RobotsDAO() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS Robots (" + "CodRobot varchar(45) NOT NULL PRIMARY KEY,"
                    + "Estado varchar(45) DEFAULT NULL," + "Trajeto varchar(" + max_char + ") DEFAULT NULL,"
                    + "Localizacao varchar(45) DEFAULT NULL)";
            stm.executeUpdate(sql);
        } catch (SQLException e) {
            // Erro a criar tabela...
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    public static RobotsDAO getInstance() {
        if (RobotsDAO.singleton == null) {
            RobotsDAO.singleton = new RobotsDAO();
        }
        return RobotsDAO.singleton;
    }

    @Override
    public int size() {
        int i = 0;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT count(*) FROM Robots")) {
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
                        .executeQuery("SELECT CodRobot FROM Robots WHERE CodRobot='" + key.toString() + "'")) {
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
        Robot p = (Robot) value;
        return this.containsKey(p.getCodRobot());
    }

    @Override
    public Robot get(Object key) {
        Robot p = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT * FROM Robots WHERE CodRobot='" + key + "'")) {
            if (rs.next()) { // A chave existe na tabela

                // Reconstruir a Robot com os dados obtidos da BD
                p = new Robot(rs.getString("CodRobot"), rs.getString("Estado"), rs.getString("Trajeto"),
                        rs.getString("Localizacao"));
            }
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return p;
    }

    @Override
    public Robot put(String key, Robot value) {
        Robot res = null;
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {

            // Actualizar a Robot
            stm.executeUpdate("INSERT INTO Robots VALUES ('" + value.getCodRobot() + "','" + value.getEstado() + "','"
                    + value.getTrajeto() + "','" + value.getLocalizacao() + "')"
                    + "ON DUPLICATE KEY UPDATE CodRobot=Values(CodRobot), " + "Estado=Values(Estado), "
                    + "Trajeto=Values(Trajeto), " + "Localizacao=Values(Localizacao)");
        } catch (SQLException e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Robot remove(Object key) {
        Robot t = this.get(key);
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {

            // apagar a Robot
            stm.executeUpdate("DELETE FROM Robots WHERE CodRobot='" + key + "'");
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return t;
    }

    @Override
    public void putAll(Map<? extends String, ? extends Robot> m) {
        for (Robot t : m.values()) {
            this.put(t.getCodRobot(), t);
        }
    }

    @Override
    public void clear() {
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement()) {
            stm.executeUpdate("TRUNCATE Robots");
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
    public Collection<Robot> values() {
        Collection<Robot> res = new HashSet<>();
        try (Connection conn = DriverManager.getConnection(DAOconfig.URL, DAOconfig.USERNAME, DAOconfig.PASSWORD);
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery("SELECT CodRobot FROM Robots")) { // ResultSet com os ids de todas as
                                                                                  // Robots
            while (rs.next()) {
                String idt = rs.getString("CodRobot"); // Obtemos um id de Robot do ResultSet
                Robot t = this.get(idt); // Utilizamos o get para construir as Robots uma a uma
                res.add(t); // Adiciona a Robot ao resultado.
            }
        } catch (Exception e) {
            // Database error!
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
        return res;
    }

    @Override
    public Set<Entry<String, Robot>> entrySet() {
        throw new NullPointerException("public Set<Map.Entry<String,Robot>> entrySet() not implemented!");
    }

}
