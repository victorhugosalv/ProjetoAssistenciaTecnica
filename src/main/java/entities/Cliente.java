package entities;

import java.util.ArrayList;
import java.util.List;
public class Cliente {
    private String nome;
    private String cpf;

    public Cliente(String nome, String cpf){
        this.nome = nome;
        this.cpf = cpf;
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


    public String toString(){
        return "Nome Do Usuário: " + getNome() + "\n"
                + "CPF do Usuario: " + getCpf() ;
    }


}
