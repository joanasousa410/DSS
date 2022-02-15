package uminho.dss.sistema_gestao.ui;

import uminho.dss.sistema_gestao.business.SistemaFacade.*;
import uminho.dss.sistema_gestao.business.gestaoArmazem.Palete;

import java.util.TreeMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Grupo20
 */
public class TextUI {
    // O model tem a 'lógica de negócio'.
    private ISistemaFacade model;

    // Scanner para leitura
    private Scanner scin;

    // O nome do gestor que efetuou login
    private String gestor;

    /**
     * Construtor.
     *
     * Cria os menus e a camada de negócio.
     */
    public TextUI() {
        this.gestor = null;
        this.model = new SistemaFacade();
        scin = new Scanner(System.in);
    }

    /**
     * Executa o menu principal e invoca o método correspondente à opção
     * seleccionada.
     */
    public void run() {
        System.out.println("⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜");
        System.out.println("⬜                                              ⬜");
        System.out.println("⬜  Bem vindo ao Sistema de Gestão do Armazém!  ⬜");
        System.out.println("⬜                                              ⬜");
        System.out.println("⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜");
        System.out.println("⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜ GRUPO 20 ⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜");
        System.out.println("⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜");
        System.out.println("⬜                                              ⬜");
        System.out.println("⬜  >>>>>>>>>| André Nunes - A85635 |<<<<<<<<<  ⬜");
        System.out.println("⬜  >>>>>>>>>| Inês Bastos - A89522 |<<<<<<<<<  ⬜");
        System.out.println("⬜  >>>>>>>>>| Joana Sousa - A83614 |<<<<<<<<<  ⬜");
        System.out.println("⬜  >>>>>>>>>| João André  - A83782 |<<<<<<<<<  ⬜");
        System.out.println("⬜  >>>>>>>>>| Tiago Gomes - A78141 |<<<<<<<<<  ⬜");
        System.out.println("⬜                                              ⬜");
        System.out.println("⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜");
        System.out.println("⬜⬜⬜⬜⬜⬜⬜    1ºSemestre DSS    ⬜⬜⬜⬜⬜⬜⬜");
        System.out.println("⬜⬜⬜⬜⬜⬜⬜ ANO LETIVO 2020/2021 ⬜⬜⬜⬜⬜⬜⬜");
        System.out.println("⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜⬜");
        // menu principal
        this.menuInicial();
        System.out.println("Até breve...");
    }

    // Métodos auxiliares - Estados da UI
    // ********************************************************************************
    // ESTADO: menu inicial
    private void menuInicial() {
        Menu menu = new Menu(new String[] { "OPÇÃO: Gestor", "OPÇÃO: Requisições" });

        // Registar os handlers
        menu.setHandler(1, () -> menuGestor());
        menu.setHandler(2, () -> menuRequisicoes());

        menu.run();
    }

    // ********************************************************************************
    // ESTADO: menu de gestão de requisições
    private void menuRequisicoes() {
        Menu menu = new Menu(
                new String[] { "REQUISIÇÃO: Registar uma requisição", "REQUISIÇÃO: Remover uma requisição" });

        // Registar pré-condições das transições
        // tem de existir paletes na lista de espera
        menu.setPreCondition(2, () -> this.model.existeRequisicoes());

        // Registar os handlers
        menu.setHandler(1, () -> adicionarRequisicao());
        menu.setHandler(2, () -> removerRequisicao());

        menu.run();
    }

    // adicionar uma requisição
    private void adicionarRequisicao() {
        try {
            // receber qual o número da requisição
            System.out.println("Inserir Código da Requisição: ");
            String codReq = scin.nextLine();
            // receber qual o número qr da palete
            System.out.println("Inserir Código QR da Palete: ");
            String palete = scin.nextLine();
            // verifica que a paelte existe
            if (this.model.getPaletes().containsKey(palete)) {
                // adiciona a requisição ao sistema
                this.model.adicionaRequisicao(codReq, palete);
                // mensagem de login sucedido
                System.out.println("\nRequisição registada no sistema!");
            } else {
                System.out.println("\nPalete Indicada não existe!");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    // remover uma requisição
    private void removerRequisicao() {
        try {
            // receber qual o número da requisição
            System.out.println("Inserir Código da Requisição: ");
            String codReq = scin.nextLine();
            // verifica se a requisição existe no sistema
            if (this.model.getRequisicoes().containsKey(codReq)) {
                // adiciona a requisição ao sistema
                this.model.removeRequisicao(codReq);
                // mensagem de login sucedido
                System.out.println("\nRequisição removida do sistema!");
            } else {
                System.out.println("\nRequisição Indicada não existe!");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    // ********************************************************************************
    // ESTADO: menu de gestão de gestores
    private void menuGestor() {
        Menu menu = new Menu(new String[] { "GESTOR: Efetuar Login", "GESTOR: Terminar Sessão", "GESTOR: Registar" });

        // Registar pré-condições das transições
        // tem de existir paletes na lista de espera
        menu.setPreCondition(1, () -> this.model.existeGestores());
        // tem de existir um gestor online na interface
        menu.setPreCondition(2, () -> this.gestor != null);

        // Registar os handlers
        menu.setHandler(1, () -> efetuarLoginGestor());
        menu.setHandler(2, () -> terminarSessaoGestor());
        menu.setHandler(3, () -> registarGestor());

        menu.run();
    }

    // efetuar o login de um gestor
    private void efetuarLoginGestor() {
        try {
            // receber qual o login
            System.out.println("Inserir Login: ");
            String login = scin.nextLine();
            // receber qual a password
            System.out.println("Inserir Password: ");
            String pwd = scin.nextLine();
            // verificar qual é o gestor
            String codGestor = this.model.loginGestor(login, pwd);
            if (codGestor != null) {
                // altera o estado do gestor na interface
                this.gestor = codGestor;
                // altera o estado do gestor na base de dados
                this.model.setEstadoGestor(codGestor, "OFFLINE", "ONLINE");
                // mensagem de login sucedido
                System.out.println("\n🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉");
                System.out.println("🎉 🎉 🎉 🎉 SEJA BEM-VINDO <" + this.gestor + "> 🎉 🎉 🎉 🎉");
                System.out.println("🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉");
                // vai para o menu de gestão do sistema
                menuPrincipal();
            } else {
                System.out.println("\n❗❗❗❗ Dados incorretos! ❗❗❗❗");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    // meter o gestor que iniciou sessão em offline
    private void terminarSessaoGestor() {
        try {
            String codGestor = this.model.getGestores().get(this.gestor).getLogin();
            if (codGestor != null) {
                // altera o estado do gestor na base de dados
                this.model.setEstadoGestor(this.model.getGestores().get(codGestor).getLogin(), "ONLINE", "OFFLINE");
                // mensagem de terminar sessão sucedido
                System.out.println("\n🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉");
                System.out.println("🎉 🎉 🎉 🎉 Até à próxima  <" + this.gestor + "> 🎉 🎉 🎉 🎉");
                System.out.println("🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉");
                // altera o estado do gestor na interface
                this.gestor = null;
            } else {
                System.out.println("\n❗❗❗❗ Nenhum gestor está com a sessão iniciada! ❗❗❗❗");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    // receber dados de um gestor e adicionar à tabela
    private void registarGestor() {
        try {
            // receber o login do gestor
            System.out.println("Inserir o seu login: ");
            String login = scin.nextLine();
            // verificar que login não existe
            if (this.model.getGestores().get(login) == null) {
                // receber qual o código de gestor
                System.out.println("Inserir o seu código de Gestor: ");
                String codGestor = scin.nextLine();
                // receber qual a password
                System.out.println("Inserir a sua password: ");
                String pwd = scin.nextLine();
                // registar o gestor
                this.model.registarGestor(codGestor, login, pwd);
                // mensagem de registo sucedido
                System.out.println("\n🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉");
                System.out.println("🎉  >>>>>> Código de Gestor <" + codGestor + ">");
                System.out.println("🎉  >>>>>> Login: <" + login + ">");
                System.out.println("🎉  >>>>>> Password: <" + pwd + ">");
                System.out.println("🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉 🎉");
            } else {
                System.out.println("\nO login inserido já existe!");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    // ********************************************************************************
    // ESTADO: menu principal
    private void menuPrincipal() {
        Menu menu = new Menu(new String[] { "Use Case 1 - Comunicar código QR",
                "Use Case 2 - Comunicar ordem de transporte", "Use Case 3 - Notificar recolha de paletes",
                "Use Case 4 - Notificar entrega de paletes", "Use Case 5 - Consultar listagem de localizações",
                "Estado Robot: Recolheu", "Estado Robot: Entregou", "SIMULAÇÃO DO PROCESSO(não implementado ainda)" });

        // Registar pré-condições das transições
        // tem de existir paletes na lista de espera
        menu.setPreCondition(2, () -> this.model.existePaletesTransportar() && this.model.existeRobotsDisponiveis());
        menu.setPreCondition(3, () -> this.model.robotRecolheu());
        menu.setPreCondition(4, () -> this.model.robotEntregou());
        // existir paletes no sistema
        menu.setPreCondition(5, () -> this.model.existePaletes());

        // Registar os handlers
        menu.setHandler(1, () -> useCase1_comunicarQR());
        menu.setHandler(2, () -> useCase2_comunicarTransporte());
        menu.setHandler(3, () -> useCase3_notificarRecolha());
        menu.setHandler(4, () -> useCase4_notificarEntrega());
        menu.setHandler(5, () -> usecase5_listarLocalizacoes());
        // opcionais
        menu.setHandler(6, () -> estadoRobotRecolheu());
        menu.setHandler(7, () -> estadoRobotEntregou());
        menu.setHandler(8, () -> opcional1_simulacao());

        menu.run();
    }

    // USE CASE 1: Comunicar código QR
    private void useCase1_comunicarQR() {
        try {
            // receber qual o código qr a inserir na bd
            System.out.println("Inserir código QR: ");
            String qrcode = scin.nextLine();
            if (this.model.getPaletes().get(qrcode) == null) {
                // adicionar palete à base de dados
                this.model.adicionaPalete(qrcode);
                // adicionar palete à lista de espera para o robot pegar
                this.model.adicionaPaleteTransportar(qrcode);
                System.out.println(
                        "Palete com QR-Code " + qrcode + " adicionada à base de dados! Está no local de recolha!");
            } else {
                System.out.println("Palete com QR-Code " + qrcode + " já existe no sistema! Indicar outro código QR.");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    // USE CASE 2: Comunicar ordem de transporte
    private void useCase2_comunicarTransporte() {
        try {
            // receber qual o código do robot
            System.out.println("Inserir código do Robot: ");
            String codRobot = scin.nextLine();
            if (this.model.getRobots().get(codRobot) != null) {
                // receber qual o código qr da palete
                System.out.println("Inserir código QR: ");
                String qrcode = scin.nextLine();
                if (this.model.getPalete(qrcode) != null) {
                    // receber qual destino de entrega
                    System.out.println("Inserir local de entrega: ");
                    String entrega = scin.nextLine();
                    // verificar se é um local de entrega válido
                    if (this.model.existeLocalEntrega(entrega)) {
                        // calcula o trajeto do Robot
                        String trajeto = this.model.calculaTrajeto(codRobot, qrcode, entrega);
                        // envia o trajeto ao Robot
                        this.model.setTrajetoRobot(codRobot, trajeto);
                        // atualiza o estado do robot para ocupado
                        this.model.setEstadoRobot(codRobot, "Livre", "Ocupado");
                        // notificação da operação
                        System.out.println("---->Trajeto calculado e enviado para o robot: "
                                + this.model.getRobots().get(codRobot).getTrajeto());
                    } else {
                        System.out.println("Local não válido!");
                    }
                } else {
                    System.out.println("Código QR não válido!");
                }
            } else {
                System.out.println("Código Robot não válido!");
            }

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    // USE CASE 3: Notificar recolha de paletes
    private void useCase3_notificarRecolha() {
        try {
            // receber codRobot para o caso de serem vários robots
            System.out.println("Inserir código do Robot: ");
            String codRobot = scin.nextLine();
            if (this.model.getRobots().containsKey(codRobot)) {// verificar que o robot existe
                // recebe o código qr da palete
                System.out.println("Código QR da palete: ");
                String qrcode = scin.nextLine();
                if (this.model.getPalete(qrcode) != null) {
                    // 'estado' devolve se o setEstadoRobot foi bem sucedido dado os parâmetros
                    String estado_antes = "Recolheu";
                    String estado_depois = "Ocupado";
                    // setEstadoRobot altera o estado do robot para o estado_depois
                    boolean estado = this.model.setEstadoRobot(codRobot, estado_antes, estado_depois);
                    if (estado) {
                        // 1) Nesta fase, é preciso ver em que lugar está a palete. só há 2 opções:
                        // --> se a palete se encontra num ponto de recolha:
                        if (this.model.getPalete(qrcode).getLocalizacao().equals("ponto_recolha")) {
                            // lê qr code da palete e retira da lista de espera do tapete rolante
                            this.model.removePaleteTransportar(qrcode);
                        }
                        // --> se a palete se encontra num local de descarga prateleira
                        else {
                            String codPrateleira = this.model.getPalete(qrcode).getLocalizacao();
                            this.model.removePaletePrateleira(qrcode, codPrateleira);
                        }
                        // 2) atualiza localização do robot
                        this.model.setLocalizacaoRobot(codRobot, this.model.getPalete(qrcode).getLocalizacao());
                        // 3) atualiza localização da palete para avisar que fica no robot
                        this.model.atualizaLocalizacaoPalete(qrcode, codRobot);
                        // 4) notifica que a palete foi recolhida
                        System.out.println(codRobot + " recolheu!");
                    }
                } else {
                    System.out.println("Código QR inválido!");
                }
            } else {
                System.out.println("O Robot indicado não existe no sistema!");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    // USE CASE 4: Notificar entrega de paletes
    private void useCase4_notificarEntrega() {
        try {
            // receber codRobot para o caso de serem vários robots
            System.out.println("Inserir código do Robot: ");
            String codRobot = scin.nextLine();
            // verificar que o robot existe
            if (this.model.getRobots().containsKey(codRobot)) {
                // recebe o código qr da palete
                System.out.println("Código QR da palete: ");
                String qrcode = scin.nextLine();
                // verificar que palete está no robot
                if (this.model.getPalete(qrcode).getLocalizacao().equals(codRobot)) {
                    // verificar que a palete existe
                    if (this.model.getPalete(qrcode) != null) {
                        // recebe o destino de entrega
                        System.out.println("Local de Entrega: ");
                        String codPonto_Entrega = scin.nextLine();
                        // local de entrega pode ser prateleira ou ponto_entrega
                        if (this.model.existePrateleira(codPonto_Entrega) || codPonto_Entrega.equals("ponto_entrega")) {
                            // verificar que a prateleira está livre ou ponto de entrega novamente
                            if (this.model.existePaletePrateleira(codPonto_Entrega)
                                    || codPonto_Entrega.equals("ponto_entrega")) {
                                String estado_antes = "Entregou";
                                String estado_depois = "Livre";
                                // setEstadoRobot altera o estado do robot para o estado_depois
                                boolean estado = this.model.setEstadoRobot(codRobot, estado_antes, estado_depois);
                                // como existe tudo, altera o estado do robot
                                if (estado) {
                                    // notifica que entregou
                                    System.out.println(codRobot + " entregou!");
                                    // concluiu o trajeto, então apaga o trajeto
                                    this.model.setTrajetoRobot(codRobot, null);
                                    // verificar o local de entrega: local de entrega só pode ser dois casos
                                    // 1) local é o ponto de entrega
                                    if (codPonto_Entrega.equals("ponto_entrega")) {
                                        // atualiza posição do robot
                                        this.model.setLocalizacaoRobot(codRobot, "ponto_entrega");
                                        // remove a palete do sistema (sai do sistema)
                                        this.model.removePalete(qrcode);
                                        // 2) local é uma prateleira
                                    } else {
                                        // atualiza localização do robot para o corredor da prateleira
                                        this.model.setLocalizacaoRobot(codRobot,
                                                this.model.getPrateleiras().get(codPonto_Entrega).getLocalizacao());
                                        // adicionar palete à prateleira
                                        this.model.adicionaPaletePrateleira(qrcode, codPonto_Entrega);
                                        // atualizar a localização da palete
                                        this.model.atualizaLocalizacaoPalete(qrcode, codPonto_Entrega);
                                    }
                                } else {
                                    System.out.println("Estado do " + codRobot + " incorreto !");
                                }
                            } else {
                                System.out.println("Prateleira " + codPonto_Entrega + " ocupada !");
                            }
                        } else {
                            System.out.println("Local de entrega não existe!");
                        }
                    } else {
                        System.out.println("Código QR inválido!");
                    }
                } else {
                    System.out.println("A palete não está no robot!");
                }

            } else {
                System.out.println("O Robot indicado não existe no sistema!");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    // USE CASE 5: Consultar listagem de localizações
    private void usecase5_listarLocalizacoes() {
        // usar um map para não conter qr codes repetidos
        // como uma palete é única, assume-se que qr code é único
        // assim, caso haja erro na base de dados, só pega na localização do primeiro
        // que encontrou

        // Map<qr_code : String, localizacao : String>
        Map<String, String> localizacoes = new TreeMap<>();

        // pegar nas paletes
        for (Palete p : model.getPaletes().values()) {
            // inserir no mapa com key = qr_code; value = localizacao
            localizacoes.put(p.getCodPalete(), p.getLocalizacao());
        }

        System.out.println(localizacoes);
    }

    // OPCIONAL 1: alterar estado de um robot para recolheu
    private void estadoRobotRecolheu() {
        try {
            // receber qual o código do robot a alterar estado
            System.out.println("Inserir código do Robot: ");
            String codRobot = scin.nextLine();
            if (this.model.getRobots().get(codRobot) != null) {
                this.model.setEstadoRobot(codRobot, "Ocupado", "Recolheu");
            } else {
                System.out.println(
                        "Robot com código " + codRobot + " não existe no sistema! Indicar outro código de robot!");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    // OPCIONAL 2: alterar estado de um robot para entregou
    private void estadoRobotEntregou() {
        try {
            // receber qual o código do robot a alterar estado
            System.out.println("Inserir código do Robot: ");
            String codRobot = scin.nextLine();
            if (this.model.getRobots().get(codRobot) != null) {
                this.model.setEstadoRobot(codRobot, "Ocupado", "Entregou");
            } else {
                System.out.println(
                        "Robot com código " + codRobot + " não existe no sistema! Indicar outro código de robot!");
            }
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    // Opcional 3: Simulação do transporte de uma palete
    private void opcional1_simulacao() {
        // TODO: simulação
        // Opção 8: Demonstrar processo de transporte de paletes
        // - Comunicar um código QR qualquer
        // - Calcular trajeto
        // - Meter um sleep de 10secs ou algo do género
        // - Notificar que recolheu
        // - Outro sleep
        // - Notificar que entregou
        // - Outro sleep para simular trajeto ao ponto de início

        try {
            Thread.sleep(10 * 1000); // 10 segundos de sleep
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
