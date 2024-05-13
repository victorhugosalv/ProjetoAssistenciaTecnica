package models;

import SistemaArquivos.LeitorEGravador;
import entities.Cliente;
import entities.ESTADO;
import entities.Pedido;
import entities.Tecnico;
import models.exceptions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SistemaAssistencia implements SistemaPedidos{


    private final List<Cliente> clientes;
    private final List<Tecnico> tecnicos;
    private final List<Pedido> pedidos;
    private final LeitorEGravador leitorEGravador;
    private final String menu = "===================================================" + "\n" +
                          "PROGRAMA SISTEMA ASSISTENCIA" + "\n" +
                          "1 --> Cadastrar novo pedido" + "\n" +
                          "2 --> Buscar pedido pelo código" + "\n" +
                          "3 --> Alterar estado do pedido" + "\n" +
                          "4 --> Ver lista de pedidos" + "\n" +
                          "5 --> Ver lista de pedidos em espera" + "\n" +
                          "6 --> Atribuir técnico a um pedido" + "\n" +
                          "7 --> Cadastrar técnico" + "\n" +
                          "8 --> Pesquisar técnico por CPF" + "\n" +
                          "9 --> Ver lista de técnicos" + "\n" +
                          "10 --> Finalizar programa" + "\n" +
                          "===================================================";


    public SistemaAssistencia(){
        this.clientes = new ArrayList<Cliente>();
        this.tecnicos = new ArrayList<Tecnico>();
        this.pedidos = new ArrayList<Pedido>();
        this.leitorEGravador = new LeitorEGravador();
    }
    private static final String ARQUIVO_PEDIDOS = "pedidos.txt";
    private static final String ARQUIVO_TECNICOS = "tecnicos.txt";




    public void iniciarSistema()  {
        leitorEGravador.recuperaTecnicos(tecnicos, ARQUIVO_TECNICOS);
        leitorEGravador.recuperaPedidos(pedidos, clientes, ARQUIVO_PEDIDOS, this);
        ordenarFilaDePedido();
        System.out.println("SISTEMA INICIALIZADO");
    }

    public void finalizarSistema() throws PedidoJaTemTecnicoException, OpcaoInvalidaException, NaoEncontrouTecnicoException {
        atualizaPedidos();
        leitorEGravador.escreveTecnicos(tecnicos, ARQUIVO_TECNICOS);
        leitorEGravador.escrevePedidos(pedidos,ARQUIVO_PEDIDOS);
        System.out.println("SISTEMA FINALIZADO");
    }
    public List<Tecnico> listaTecnicos(){
        return this.tecnicos;
    }

    public List<Pedido> listaPedidosEmEspera() {
        List<Pedido> pedidosEmEspera = new ArrayList<>();
        ordenarFilaDePedido();
        for(Pedido p: pedidos){
            if(p.getEstado() == ESTADO.NAO_INICIADO){
                pedidosEmEspera.add(p);
            }
        }
        return pedidosEmEspera;
    }


    public void cadastrarTecnico(Tecnico tecnico) throws TecnicoJaCadastradoException{
        boolean existe = false;
        for(Tecnico tecnicoX : tecnicos){
            if(tecnicoX.getCpf().equals((tecnico.getCpf()))){
                existe = true;
                break;
            }
        }

        if(!existe){
            this.tecnicos.add(tecnico);
        }else{
            throw new TecnicoJaCadastradoException("ERRO: TÉCNICO JÁ CADASTRADO");}

    }


    public boolean atribuirTecnico(Pedido pedido) throws PedidoJaTemTecnicoException, OpcaoInvalidaException, NaoEncontrouTecnicoException {

        if (pedido.getTecnico() != null) {
            throw new PedidoJaTemTecnicoException("ERRO: PEDIDO JÁ ATRIBUÍDO A OUTRO TÉCNICO.");
        }

        for (Tecnico tecn : listaTecnicos()) {
            if (tecn.getPedido() == null){
                pedido.setTecnico(tecn);
                tecn.setPedido(pedido);
                alterarEstadoDoPedido(pedido, 1);
                return true;
            }
        } return false;
    }

    public void cadastraPedidoECliente(Pedido pedido, Cliente cliente) throws PedidoJaCadastradoException, NaoTemPedidosEmEsperaException, OpcaoInvalidaException, PedidoJaTemTecnicoException, CampoVazioException, CpfInvalidoException, NaoEncontrouTecnicoException {
        boolean existePedido = false;
        boolean existeCliente = false;
        if(cliente.getNome().equalsIgnoreCase("")){
            throw new CampoVazioException("ERRO: \"Nome\" NÃO PODE SER VAZIO");
        }else if(cliente.getCpf().equalsIgnoreCase("")){
            throw new CampoVazioException("ERRO: \"CPF\" NÃO PODE SER VAZIO");
        } else if (!(cliente.getCpf().matches("[0-9]+") && cliente.getCpf().length() == 11)) {
            throw new CpfInvalidoException("ERRO: CPF INVÁLIDO");
            
        } else if (pedido.getDescricao().equalsIgnoreCase("")){
            throw new CampoVazioException("ERRO: \"Descrição\n NÃO PODE SER VAZIO");
        } else if (pedido.getTipoDoPedido().equalsIgnoreCase("")) {
            throw new CampoVazioException("ERRO: \"Tipo do pedido\" NÃO PODE SER VAZIO");
        }


        for (Pedido pedidoX : pedidos) {
            if (pedidoX.equals(pedido)) {
                existePedido = true;
                break;
            }
        }
        for (Cliente clienteX : clientes) {
            if (clienteX.getCpf().equalsIgnoreCase(cliente.getCpf())) {
                existeCliente = true;
                break;
            }
        }
        if (existePedido) {
            throw new PedidoJaCadastradoException("ERRO: PEDIDO JÁ CADASTRADO");
        } else if (existeCliente) {
            this.pedidos.add(pedido);
        } else {

            this.pedidos.add(pedido);
            this.clientes.add(cliente);

            if (atribuirTecnico(pedido)) {
                alterarEstadoDoPedido(pedido, 1);
            }
        }
    }


    public Pedido procurarPedidoPorCodigo(Integer codigo) throws CodigoInvalidoException {
        for (Pedido p:this.pedidos) {
            if(p.getCodigo().equals(codigo)){
                return p;
            }
        }
        throw new CodigoInvalidoException("ERRO: CÓDIGO INVÁLIDO");
    }

    public Tecnico pesquisarTecnicoPorCPF(String cpf) throws NaoEncontrouTecnicoException{
        for (Tecnico t : tecnicos) {
            if (t.getCpf().equals(cpf)) {
                return t;
            }
        }
        throw new NaoEncontrouTecnicoException("NÃO ENCONTRAMOS UM TÉCNICO COM ESTE CPF");
    }

    @Override
    public void alterarEstadoDoPedido(Pedido pedido, int opcao) throws OpcaoInvalidaException, PedidoJaTemTecnicoException, NaoEncontrouTecnicoException {
        ESTADO novoEstado;
        switch(opcao){
            case 1:
                novoEstado = ESTADO.EM_ANALISE;

                break;
            case 2:
                novoEstado = ESTADO.SOLUCIONANDO;
                break;
            case 3:
                DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                novoEstado = ESTADO.FINALIZADO;
                Tecnico tecN = pesquisarTecnicoPorCPF(pedido.getTecnico().getCpf());
                tecN.setPedido(null);
                pedido.setTecnico(null);
                if(!listaPedidosEmEspera().isEmpty()){
                    for (Pedido p : listaPedidosEmEspera()) {
                            atribuirTecnico(p);
                    }
                }
                pedido.setDataDeSaida(LocalDateTime.now().format(fmt1));
                break;
            default:
                throw new OpcaoInvalidaException("ERRO: OPÇÃO INVÁLIDA!");

        }
        pedido.setEstado(novoEstado);



    }

    public String getMenu() {
        return menu;
    }

    @Override
    public void ordenarFilaDePedido() {Collections.sort(this.pedidos);
    }

    @Override
    public List<Pedido> listaDePedidos() {
        ordenarFilaDePedido();
        return pedidos;
    }
    public void atualizaPedidos() throws PedidoJaTemTecnicoException, OpcaoInvalidaException, NaoEncontrouTecnicoException {
        ordenarFilaDePedido();
        for(Pedido pedidoX : this.listaPedidosEmEspera()){
            atribuirTecnico(pedidoX);
        }
    }
}