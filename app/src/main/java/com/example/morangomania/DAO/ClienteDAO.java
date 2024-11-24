package com.example.morangomania.DAO;

import android.content.ContentValues;

import com.example.morangomania.conexao.ConexaoSQL;
import com.example.morangomania.model.Cadastro;
import com.example.morangomania.model.Cliente;
import com.example.morangomania.model.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    Connection conn = ConexaoSQL.conectar();

    public Cliente selecionarCliente(String cpf, String senha) throws SQLException {
        Connection conn = ConexaoSQL.conectar();

        String comando = "SELECT * FROM dbo.Clientes WHERE CPF = ? and Senha = ?";
        PreparedStatement pst = conn.prepareStatement(comando);

        // Definindo os valores dos parâmetros
        pst.setString(1, cpf);
        pst.setString(2, senha);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Cliente cliente = new Cliente();
            Endereco endereco = new Endereco();

            cliente.setId(rs.getInt(1));
            cliente.setNome(rs.getString(2));
            cliente.setTelefone(rs.getString(3));
            cliente.setEmail(rs.getString(4));
            cliente.setCpf(rs.getString(5));
            endereco.setCep(rs.getString(6));
            endereco.setRua(rs.getString(7));
            endereco.setNumero(rs.getString(8));
            endereco.setBairro(rs.getString(9));
            cliente.setSenha(rs.getString(10));

            cliente.setEndereco(endereco);

            conn.close();
            return cliente;
        }

        return null;
    }

    // Inserir cliente
    public int inserirCliente(Cliente cliente) throws SQLException {

        String comando = "INSERT INTO dbo.Clientes (Nome,Telefone,Email,cpf,CEP,Rua,Numero,Bairro,senha) VALUES (?,?,?,?,?,?,?,?,?)";

        PreparedStatement pst = conn.prepareStatement(comando);

        // Definindo os valores dos parâmetros
        pst.setString(1, cliente.getNome());
        pst.setString(2, cliente.getTelefone());
        pst.setString(3, cliente.getEmail());
        pst.setString(4, cliente.getCpf());
        pst.setString(5, cliente.getCep());
        pst.setString(6, cliente.getRua());
        pst.setString(7, cliente.getNumero());
        pst.setString(8, cliente.getBairro());
        pst.setString(9, cliente.getSenha());

        return pst.executeUpdate();
    }
}
