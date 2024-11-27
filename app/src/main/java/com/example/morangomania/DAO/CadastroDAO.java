package com.example.morangomania.DAO;

import com.example.morangomania.services.ConexaoSQL;
import com.example.morangomania.model.Cadastro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CadastroDAO {

    public Cadastro selecionarUsuario(String cpf, String senha) throws SQLException {
        Connection conn = ConexaoSQL.conectar();

        String comando = "SELECT * FROM dbo.Cadastro WHERE CPF = ? and Senha = ?";
        PreparedStatement pst = conn.prepareStatement(comando);

        // Definindo os valores dos parâmetros
        pst.setString(1, cpf);
        pst.setString(2, senha);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Cadastro usuario = new Cadastro();
            usuario.setNome(rs.getString(2));
            usuario.setCpf(rs.getString(3));
            usuario.setCargo(rs.getString(4));
            usuario.setEmail(rs.getString(5));
            usuario.setSenha(rs.getString(6));
            conn.close();
            return usuario;
        }

        conn.close();

        return null;
    }
}
