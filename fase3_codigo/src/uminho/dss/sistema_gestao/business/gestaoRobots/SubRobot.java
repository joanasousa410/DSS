package uminho.dss.sistema_gestao.business.gestaoRobots;

import java.util.Map;

import uminho.dss.sistema_gestao.data.RobotsDAO;

/**
 * @author Grupo20
 */

public class SubRobot implements ISubRobot {
    // implementado para vários robots
    private Map<String, Robot> robots;

    public SubRobot() {
        this.robots = RobotsDAO.getInstance();
        adicionarRobots();
    }

    private void adicionarRobots() {
        // criar robots
        Robot robot1 = new Robot();
        robot1.setCodRobot("RobotCode_1");
        robot1.setEstado("Livre");
        robot1.setLocalizacao("ponto_recolha");
        robot1.setTrajeto(null);
        // criar robots
        Robot robot2 = new Robot();
        robot2.setCodRobot("RobotCode_2");
        robot2.setEstado("Livre");
        robot2.setLocalizacao("ponto_recolha");
        robot2.setTrajeto(null);
        // criar robots
        Robot robot3 = new Robot();
        robot3.setCodRobot("RobotCode_3");
        robot3.setEstado("Livre");
        robot3.setLocalizacao("ponto_recolha");
        robot3.setTrajeto(null);
        // criar robots
        Robot robot4 = new Robot();
        robot4.setCodRobot("RobotCode_4");
        robot4.setEstado("Livre");
        robot4.setLocalizacao("ponto_recolha");
        robot4.setTrajeto(null);
        // criar robots
        Robot robot5 = new Robot();
        robot5.setCodRobot("RobotCode_5");
        robot5.setEstado("Livre");
        robot5.setLocalizacao("ponto_recolha");
        robot5.setTrajeto(null);

        // adicionar robot à lista
        // estes puts substituem os robots
        this.robots.put("RobotCode_1", robot1);
        this.robots.put("RobotCode_2", robot2);
        this.robots.put("RobotCode_3", robot3);
        this.robots.put("RobotCode_4", robot4);
        this.robots.put("RobotCode_5", robot5);
    }

    // check ao estado do referido robot
    public String checkEstado(String codRobot) {
        return robots.get(codRobot).getEstado();
    }

    public boolean setEstadoRobot(String codRobot, String estado_antes, String estado_depois) {
        // verificar que o robot existe
        if (robots.containsKey(codRobot)) {
            // verificar que de facto o estado do robot é o desejado
            if (robots.get(codRobot).getEstado().equals(estado_antes)) {
                // alterar estado do robot
                Robot r = new Robot(codRobot, estado_depois, robots.get(codRobot).getTrajeto(),
                        robots.get(codRobot).getLocalizacao());
                robots.put(codRobot, r);
                // alterou estado, então return true
                return true;
            }
        }
        // estado do robot requerido é o pedido
        return false;

    }

    @Override
    public void setTrajetoRobot(String codRobot, String trajeto) {
        // verificar que o robot existe
        if (robots.containsKey(codRobot)) {
            // alterar o trajeto do robot
            Robot r = new Robot(codRobot, robots.get(codRobot).getEstado(), trajeto,
                    robots.get(codRobot).getLocalizacao());
            robots.put(codRobot, r);
        }
    }

    @Override
    public Map<String, Robot> getRobots() {
        return this.robots;
    }

    @Override
    public void setLocalizacaoRobot(String codRobot, String localizacao) {
        // verificar que o robot existe
        if (robots.containsKey(codRobot)) {
            // criar robot novo
            Robot r = new Robot(codRobot, robots.get(codRobot).getEstado(), robots.get(codRobot).getTrajeto(),
                    localizacao);
            // alterar a localizacao do robot
            robots.put(codRobot, r);
        }
    }

    @Override
    public boolean existeRobotsDisponiveis() {
        // iterar todos os robots
        for (Robot r : robots.values()) {
            // verificar se o robot está livre
            if (r.getEstado().equals("Livre")) {
                // se encontra, está livre
                return true;
            }
        }
        // percorreu todos os robots e não encontrou
        return false;
    }
}
