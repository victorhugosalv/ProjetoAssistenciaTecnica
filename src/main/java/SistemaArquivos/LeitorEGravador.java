package SistemaArquivos;

import entities.Cliente;
import entities.Estado;
import entities.Pedido;
import entities.Tecnico;
import models.SistemaAssistencia;
import models.exceptions.NaoEncontrouTecnicoException;

import java.io.*;
import java.util.List;

public class LeitorEGravador{


    public void recuperaPedidos(List<Pedido> pedidos, List<Cliente> clientes, String nomeDoArquivo, SistemaAssistencia sistema){
        String[] separador;
        try(BufferedReader br = new BufferedReader(new FileReader(nomeDoArquivo))){
            String line = br.readLine();
            while (line != null){

                separador = line.split(";");

                int codigo = Integer.parseInt(separador[0]);
                String descricao = separador[1];
                String tipo = separador[2];
                String dataDeChegada = separador[3];
                String dataDeSaida = separador[4];
                String nomeDoCliente = separador[5];
                String cpfDoCliente = separador[6];
                double valor = Double.parseDouble(separador[7]);
                String cpfDoTecnico = separador[8];
                String estado = separador[9];

                Cliente c = new Cliente(nomeDoCliente, cpfDoCliente);
                clientes.add(c);
                Pedido pedido;
                Tecnico tecnico =  null;

                try {
                    tecnico = sistema.pesquisarTecnicoPorCPF(cpfDoTecnico);
                } catch(NaoEncontrouTecnicoException ignored) {}
                pedido = new Pedido(codigo, descricao, tipo, dataDeChegada, dataDeSaida, c, valor, tecnico, Estado.valueOf(estado));
                if(tecnico != null){
                    tecnico.setPedido(pedido);
                }
                pedidos.add(pedido);

                line = br.readLine();

            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }


    public void escrevePedidos(List<Pedido> pedidos, String nomeDoArquivo){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeDoArquivo))){
            for (Pedido p : pedidos) {
                String[] pedido;
                pedido = p.mostraAtributos().split("\n");
                bw.write(pedido[0]);
                for (int i = 1; i < pedido.length; i++) {
                    bw.write(";" + pedido[i]);
                }
                bw.newLine();
            }
        } catch (IOException e){
            e.getStackTrace();
        }
    }

    public void recuperaTecnicos(List<Tecnico> tecnicos, String nomeDoArquivo){
        try(BufferedReader br = new BufferedReader(new FileReader(nomeDoArquivo))){
            String line = br.readLine();
            String[] separador;
            while (line != null){

                separador = line.split(";");

                String nome = separador[0];
                String cpf = separador[1];
                String especialidade = separador[2];

                tecnicos.add(new Tecnico(nome,cpf,especialidade));

                line = br.readLine();
            }
        }catch( IOException e){
            e.getStackTrace();
        }
    }

    public void escreveTecnicos(List<Tecnico> tecnicos, String nomeDoArquivo){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomeDoArquivo))){
            for (Tecnico t: tecnicos){
                String[] tecnico = t.mostraAtributosDoTecnico().split("\n");
                bw.write(tecnico[0]);
                for(int i = 1; i < tecnico.length; i++){
                    bw.write(";" + tecnico[i]);
                }
                bw.newLine();
            }
        } catch (IOException e){
            e.getStackTrace();
        }
    }

}
