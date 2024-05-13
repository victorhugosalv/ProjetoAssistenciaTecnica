package entities;

public class Tecnico {
    private String nome;
    private String cpf;
    private String especialidade;
    private Pedido pedido;


    public Tecnico(String nome, String cpf, String especialidade) {
        this.nome = nome;
        this.cpf = cpf;
        this.especialidade = especialidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String mostraAtributosDoTecnico(){
        return   nome + "\n" +
                 cpf + "\n" +
                 especialidade;
    }

    @Override
    public String toString(){
        return  "--------------------------------------\n" +
                "Nome do Tecnico: " + nome + "\n" +
                "CPF do Tecnico: " + cpf + "\n" +
                "Especialidade: " + especialidade + "\n" +
                "--------------------------------------";

    }

}
