package entities;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.time.LocalDateTime;

public class Pedido implements Comparable<Pedido> {
    private final Integer codigo;
    private String descricao;
    private final String tipoDoPedido;
    private String dataDeChegada;
    private String dataDeSaida;
    private Cliente cliente;
    private double valor;
    private Tecnico tecnico;
    private Estado estado;

    LocalDateTime dataMomento = LocalDateTime.now();
    DateTimeFormatter fmt1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    public Pedido(String descricao, String tipoDoPedido, Cliente user, Double valor){
        this.descricao = descricao;
        this.tipoDoPedido = tipoDoPedido;
        this.dataDeChegada = dataMomento.format(fmt1);
        this.estado = Estado.NAO_INICIADO;
        this.cliente = user;
        this.codigo = hashCode();
        this.valor = valor;

    }

    public Pedido(Integer codigo, String descricao, String tipoDoPedido, String dataDeChegada, String dataDeSaida, Cliente user, Double valor, Tecnico tecnico, Estado estado) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.tipoDoPedido = tipoDoPedido;
        this.dataDeChegada = dataDeChegada;
        this.dataDeSaida = dataDeSaida;
        this.cliente = user;
        this.valor = valor;
        this.tecnico = tecnico;
        this.estado = estado;
    }

    public Integer getCodigo() {
        return this.codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoDoPedido() {
        return tipoDoPedido;
    }


    public String getDataDeChegada() {
        return dataDeChegada;
    }

    public void setDataDeChegada(String dataDeChegada) {
        this.dataDeChegada = dataDeChegada;
    }

    public String getDataDeSaida() {
        return dataDeSaida;
    }

    public void setDataDeSaida(String dataDeSaida) {
        this.dataDeSaida = dataDeSaida;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public void setTecnico(Tecnico tecnico) {
        this.tecnico = tecnico;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }


    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String mostraAtributos(){
        if (tecnico != null){
            return  (codigo) + "\n"
                    + descricao + "\n"
                    + tipoDoPedido + "\n"
                    + dataDeChegada + "\n"
                    + dataDeSaida + "\n"
                    + cliente.getNome() + "\n"
                    + cliente.getCpf() + "\n"
                    + String.format("%.2f", valor) + "\n"
                    + tecnico.getCpf() + "\n"
                    + estado;
        }
         return (codigo) + "\n"
                + descricao + "\n"
                + tipoDoPedido + "\n"
                + dataDeChegada + "\n"
                + dataDeSaida + "\n"
                + cliente.getNome() + "\n"
                + cliente.getCpf() + "\n"
                + String.format("%.2f", valor) + "\n"
                + tecnico + "\n"
                + estado;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(codigo, pedido.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataDeChegada, descricao);
    }

    @Override
    public String toString(){
        return
                  "----------------------------------------------------------\n"+
                  "Codigo do Pedido: " + getCodigo() + "\n"
                + "Descrição do Pedido: " + getDescricao() + "\n"
                + "Tipo do Pedido: " + getTipoDoPedido() + "\n"
                + "Data de Chegada: " + getDataDeChegada() + "\n"
                + "Data de Saida: " + getDataDeSaida() + "\n"
                + cliente + "\n"
                + "Valor: " + String.format("%.2f R$",getValor()) + "\n" + "Técnico responsável:\n"
                + tecnico+ "\n"
                + "Situação do pedido: " + estado + "\n"
                + "----------------------------------------------------------";
    }
//:P

    @Override
    public int compareTo(Pedido other) {
        return LocalDateTime.parse(dataDeChegada,fmt1).compareTo(LocalDateTime.parse(other.getDataDeChegada(),fmt1));
    }

}




