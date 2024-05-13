package models;

import entities.Cliente;
import entities.Pedido;
import models.exceptions.*;
import java.util.List;

public interface SistemaPedidos {
    void cadastraPedidoECliente(Pedido pedido, Cliente cliente) throws PedidoJaCadastradoException, NaoTemPedidosEmEsperaException, OpcaoInvalidaException, PedidoJaTemTecnicoException, CampoVazioException, CpfInvalidoException, NaoEncontrouTecnicoException;

    Pedido procurarPedidoPorCodigo(Integer codigo) throws CodigoInvalidoException;

    void alterarEstadoDoPedido(Pedido pedido, int numero) throws OpcaoInvalidaException, PedidoJaTemTecnicoException, NaoEncontrouTecnicoException;
    void ordenarFilaDePedido();
    List<Pedido> listaDePedidos();
}
