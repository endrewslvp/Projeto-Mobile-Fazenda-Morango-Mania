package com.example.morangomania.DAO;

import com.example.morangomania.services.ConexaoSQL;
import com.example.morangomania.model.Cadastro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CadastroDAO {

    // Metodo para selecionar um usuário com base no CPF e senha
    public Cadastro selecionarUsuario(String cpf, String senha) throws SQLException {
        // Conecta ao banco de dados usando o serviço de conexão
        Connection conn = ConexaoSQL.conectar();

        // Comando SQL para buscar o usuário pelo CPF e senha
        String comando = "SELECT * FROM dbo.Cadastro WHERE CPF = ? and Senha = ?";
        PreparedStatement pst = conn.prepareStatement(comando);

        // Definindo os valores dos parâmetros no comando SQL
        pst.setString(1, cpf); // Primeiro parâmetro (CPF)
        pst.setString(2, senha); // Segundo parâmetro (Senha)

        // Executa a consulta e obtém o resultado
        ResultSet rs = pst.executeQuery();

        // Se houver um usuário com o CPF e senha fornecidos
        while (rs.next()) {
            Cadastro usuario = new Cadastro();
            usuario.setNome(rs.getString(2)); // Acessa o nome do usuário (coluna 2)
            usuario.setCpf(rs.getString(3)); // Acessa o CPF do usuário (coluna 3)
            usuario.setCargo(rs.getString(4)); // Acessa o cargo do usuário (coluna 4)
            usuario.setEmail(rs.getString(5)); // Acessa o e-mail do usuário (coluna 5)
            usuario.setSenha(rs.getString(6)); // Acessa a senha do usuário (coluna 6)

            // Fecha a conexão com o banco de dados
            conn.close();
            // Retorna o objeto de usuário preenchido
            return usuario;
        }

        // Se não encontrar nenhum usuário, fecha a conexão e retorna null
        conn.close();
        return null;
    }
}
