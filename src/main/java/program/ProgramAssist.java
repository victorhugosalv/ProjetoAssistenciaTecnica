package program;

import entities.Cliente;
import entities.Pedido;
import entities.Tecnico;
import models.SistemaAssistencia;
import models.exceptions.*;

import java.util.Locale;
import java.util.Scanner;

public class ProgramAssist {
    public static void main(String[] args) throws ProgramException, NumberFormatException {
        Locale.setDefault(Locale.US);

        SistemaAssistencia sistema = new SistemaAssistencia();
        sistema.iniciarSistema();
        Scanner in = new Scanner(System.in);
        boolean acabo = true;
        while (acabo) {
            try {
                System.out.println(sistema.getMenu());
                System.out.print("Digite o número da opção: ");
                int indiceSwitch = Integer.parseInt(in.nextLine());


                switch (indiceSwitch) {
                    case 1:
                        System.out.print("Nome do cliente: ");
                        String nomeCliente = in.nextLine();
                        System.out.print("CPF do cliente: ");
                        String cpfCliente = in.nextLine();
                        Cliente novoCliente = new Cliente(nomeCliente, cpfCliente);
                        System.out.print("DESCRIÇÃO do pedido: ");
                        String descricao = in.nextLine();
                        System.out.print("TIPO DO PEDIDO: ");
                        String tipoDoPedido = in.nextLine();
                        System.out.print("VALOR do pedido: ");
                        double valor = Double.parseDouble(in.nextLine());

                        Pedido novoPedido = new Pedido(descricao, tipoDoPedido, novoCliente, valor);
                        sistema.cadastraPedidoECliente(novoPedido, novoCliente);

                        System.out.println("Pedido cadastrado com sucesso!");
                        break;

                    case 2:
                        System.out.print("Código do pedido: ");
                        int novoCodigoProcuraPedido = Integer.parseInt(in.nextLine());
                        Pedido pedidoAchado = sistema.procurarPedidoPorCodigo(novoCodigoProcuraPedido);
                        System.out.println("O pedido procurado foi: \n" + pedidoAchado);;
                        break;

                    case 3:
                        //tc
                        System.out.print("Código do pedido a ser alterado: ");
                        int novoCodigoEstadoPedido = Integer.parseInt(in.nextLine());
                        System.out.println(
                                           "----------------------\n" +
                                           "1 --> EM ANÁLISE\n" +
                                           "2 --> SOLUCIONANDO\n" +
                                           "3 --> FINALIZADO\n" +
                                           "----------------------");
                        System.out.print("Digite o número correspondente a opção do Estado: ");
                        int indiceEstado = Integer.parseInt(in.nextLine());
                        Pedido pedidoMudarEstado = sistema.procurarPedidoPorCodigo(novoCodigoEstadoPedido);
                        sistema.alterarEstadoDoPedido(pedidoMudarEstado, indiceEstado);
                        System.out.println("Estado modificado!");

                        break;

                    case 4:
                        System.out.println("Lista de Pedidos: ");
                        if(sistema.listaDePedidos().isEmpty()){
                            System.out.println("Não há pedidos cadastrados!");
                        }else{
                            for(Pedido pedidoX :sistema.listaDePedidos()){
                            System.out.println(pedidoX);
                        }
                            System.out.println("Fim da lista!");
                        }
                        break;

                    case 5:
                        System.out.println("Lista de pedidos em espera: ");
                        if(sistema.listaPedidosEmEspera().isEmpty()){
                            System.out.println("Não existem pedidos em espera!");
                        }else{
                            for(Pedido pedidoX : sistema.listaPedidosEmEspera()){
                                System.out.println(pedidoX);
                            }
                            System.out.println("Fim da lista.");
                        }


                        break;

                    case 6:
                        //tc
                        System.out.print("Digite o código do pedido a ser adicionado o tecnico: ");
                        int codigoPedidoAtribuirTec = Integer.parseInt(in.nextLine());
                        Pedido pedidoAtribuirTec = sistema.procurarPedidoPorCodigo(codigoPedidoAtribuirTec);
                        sistema.atribuirTecnico(pedidoAtribuirTec);
                        System.out.println("Técnico adicionado!");

                        break;
                    case 7:
                        //tc
                        System.out.print("Nome do técnico: ");
                        String nomeTecnico = in.nextLine();
                        System.out.print("CPF do técnico: ");
                        String cpfTecnico = in.nextLine();
                        System.out.print("Especialidade do técnico: ");
                        String especialidadeTecnico = in.nextLine();
                        Tecnico novoTecnico = new Tecnico(nomeTecnico, cpfTecnico, especialidadeTecnico);
                        sistema.cadastrarTecnico(novoTecnico);
                        System.out.println("Técnico cadastrado com sucesso!");

                        break;
                    case 8:
                        System.out.print("CPF do tecnico:");
                        String cpfTecnicoBuscar = in.nextLine();
                        System.out.println(sistema.pesquisarTecnicoPorCPF(cpfTecnicoBuscar));

                        break;
                    case 9:
                        System.out.println("Lista de técnicos:");
                        if(sistema.listaTecnicos().isEmpty()){
                            System.out.println("Não há técnicos cadastrados!");
                        }else{for(Tecnico tecnicoX : sistema.listaTecnicos()){
                            System.out.println(tecnicoX);}
                        }
                        break;
                    case 10:
                        acabo = false;
                        in.close();
                        sistema.finalizarSistema();
                        break;

                    default:
                        System.out.println("ERRO: OPÇÃO INVÁLIDA");
                        break;

                }
            }catch(ProgramException e){
                System.out.println(e.getMessage());
            }catch(NumberFormatException e){
                System.out.println("ERRO: VALOR INVÁLIDO");
            }

        }
    }
}