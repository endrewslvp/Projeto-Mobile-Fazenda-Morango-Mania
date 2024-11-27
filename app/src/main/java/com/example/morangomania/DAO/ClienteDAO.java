package com.example.morangomania.DAO;

import com.example.morangomania.services.ConexaoSQL;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    // Estabelece uma conexão com o banco de dados
    Connection conn = ConexaoSQL.conectar();

    // Metodo para selecionar um cliente com base no CPF e senha
    public Cliente selecionarCliente(String cpf, String senha) throws SQLException {
        // Estabelece uma nova conexão com o banco
        Connection conn = ConexaoSQL.conectar();

        // Comando SQL para buscar o cliente baseado no CPF e senha
        String comando = "SELECT * FROM dbo.Clientes WHERE CPF = ? and Senha = ?";
        PreparedStatement pst = conn.prepareStatement(comando);

        // Definindo os parâmetros da consulta (CPF e senha)
        pst.setString(1, cpf);
        pst.setString(2, senha);

        // Executa a consulta no banco de dados
        ResultSet rs = pst.executeQuery();

        // Se um cliente for encontrado, cria o objeto Cliente e Endereco
        while (rs.next()) {
            Cliente cliente = new Cliente();
            Endereco endereco = new Endereco();

            // Preenche os dados do cliente
            cliente.setId(rs.getInt(1));
            cliente.setNome(rs.getString(2));
            cliente.setTelefone(rs.getString(3));
            cliente.setEmail(rs.getString(4));
            cliente.setCpf(rs.getString(5));

            // Preenche os dados do endereço
            endereco.setCep(rs.getString(6));
            endereco.setRua(rs.getString(7));
            endereco.setNumero(rs.getString(8));
            endereco.setBairro(rs.getString(9));
            cliente.setSenha(rs.getString(10));
            endereco.setCidade(rs.getString(11));

            // Fecha a conexão
            conn.close();

            // Atribui o endereço ao cliente
            cliente.setEndereco(endereco);

            // Retorna o cliente preenchido
            return cliente;
        }

        // Caso nenhum cliente seja encontrado, retorna null
        return null;
    }

    // Método para inserir um novo cliente no banco de dados
    public int inserirCliente(Cliente cliente) throws SQLException {
        // Comando SQL para inserir o cliente
        String comando = "INSERT INTO dbo.Clientes (Nome,Telefone,Email,cpf,CEP,Rua,Numero,Bairro,senha,Cidade) VALUES (?,?,?,?,?,?,?,?,?,?)";

        // Prepara a consulta
        PreparedStatement pst = conn.prepareStatement(comando);

        // Define os parâmetros para inserção
        pst.setString(1, cliente.getNome());
        pst.setString(2, cliente.getTelefone());
        pst.setString(3, cliente.getEmail());
        pst.setString(4, cliente.getCpf());
        pst.setString(5, cliente.getCep());
        pst.setString(6, cliente.getRua());
        pst.setString(7, cliente.getNumero());
        pst.setString(8, cliente.getBairro());
        pst.setString(9, cliente.getSenha());
        pst.setString(10, cliente.getCidade());

        // Executa a inserção e retorna o número de linhas afetadas
        return pst.executeUpdate();
    }

    // Método para atualizar os dados de um cliente
    public void atualizarUsuario(Cliente cliente) throws SQLException {
        // Comando SQL para atualizar os dados do cliente
        String comando = "UPDATE dbo.Clientes SET Nome = ?, Telefone = ?, Email = ?, cpf = ?, CEP = ?, Rua = ?, Numero = ?, Bairro = ?, senha = ?, Cidade = ? WHERE Id = ?";

        // Prepara a consulta
        PreparedStatement pst = conn.prepareStatement(comando);

        // Define os parâmetros da consulta de atualização
        pst.setString(1, cliente.getNome());
        pst.setString(2, cliente.getTelefone());
        pst.setString(3, cliente.getEmail());
        pst.setString(4, cliente.getCpf());
        pst.setString(5, cliente.getCep());
        pst.setString(6, cliente.getRua());
        pst.setString(7, cliente.getNumero());
        pst.setString(8, cliente.getBairro());
        pst.setString(9, cliente.getSenha());
        pst.setString(10, cliente.getCidade());
        pst.setInt(11, cliente.getId());

        // Executa a atualização no banco
        pst.executeUpdate();
    }

    // Método para selecionar um cliente apenas pelo CPF
    public Cliente selecionarClientePorCPF(String cpf) throws SQLException {
        // Estabelece uma nova conexão com o banco
        Connection conn = ConexaoSQL.conectar();

        // Comando SQL para buscar o cliente apenas pelo CPF
        String comando = "SELECT * FROM dbo.Clientes WHERE CPF = ?";
        PreparedStatement pst = conn.prepareStatement(comando);

        // Define o parâmetro para a consulta (CPF)
        pst.setString(1, cpf);

        // Executa a consulta
        ResultSet rs = pst.executeQuery();

        // Se o cliente for encontrado, preenche o objeto Cliente e Endereco
        while (rs.next()) {
            Cliente cliente = new Cliente();
            Endereco endereco = new Endereco();

            // Preenche os dados do cliente
            cliente.setId(rs.getInt(1));
            cliente.setNome(rs.getString(2));
            cliente.setTelefone(rs.getString(3));
            cliente.setEmail(rs.getString(4));
            cliente.setCpf(rs.getString(5));

            // Preenche os dados do endereço
            endereco.setCep(rs.getString(6));
            endereco.setRua(rs.getString(7));
            endereco.setNumero(rs.getString(8));
            endereco.setBairro(rs.getString(9));
            cliente.setSenha(rs.getString(10));
            endereco.setCidade(rs.getString(11));

            // Fecha a conexão
            conn.close();

            // Atribui o endereço ao cliente
            cliente.setEndereco(endereco);

            // Retorna o cliente preenchido
            return cliente;
        }

        // Caso não encontre o cliente, retorna null
        return null;
    }
}
