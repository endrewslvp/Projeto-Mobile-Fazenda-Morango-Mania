package com.example.morangomania.services;

import android.database.SQLException;
import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoSQL {

    // Metodo estático para estabelecer uma conexão com o banco de dados SQL Server
    public static Connection conectar(){

        Connection conn = null;  // Declaração da variável para armazenar a conexão

        try{

            // Adicionando uma política para permitir a execução de operações de rede na thread principal (necessário em Android)
            StrictMode.ThreadPolicy politica;
            politica = new  StrictMode.ThreadPolicy.Builder().permitAll().build();  // Permite todas as operações de thread
            StrictMode.setThreadPolicy(politica);  // Aplica a política definida

            // Verifica se o driver JDBC necessário está disponível no projeto
            Class.forName("net.sourceforge.jtds.jdbc.Driver");  // O driver JDBC para SQL Server é carregado dinamicamente

            // Definindo os parâmetros da conexão: IP, banco de dados, usuário e senha
            String ip = "192.168.0.11:1433";  // IP do servidor e a porta
            String db = "MorangoManiaMobile";  // Nome do banco de dados
            String user = "userMobile";  // Nome do usuário
            String senha = "1234";  // Senha do usuário

            // Construção da string de conexão com base nos parâmetros definidos acima
            String connString = "jdbc:jtds:sqlserver://"+ip+";databaseName="+db+";user="+user+";password="+senha+";";

            // Estabelecendo a conexão com o banco de dados utilizando a string de conexão
            conn = DriverManager.getConnection(connString);  // Tenta estabelecer a conexão com o banco de dados

        }catch (SQLException e){
            // Se houver erro de SQL (como falha de conexão), captura a exceção e pode tratar ou registrar o erro
            e.getMessage();  // Aqui, o erro é capturado mas não tratado, apenas acessando a mensagem da exceção
        } catch (ClassNotFoundException e) {
            // Se o driver JDBC não for encontrado, captura a exceção e lança uma exceção de runtime
            throw new RuntimeException(e);  // Caso ocorra erro ao carregar o driver JDBC
        } catch (java.sql.SQLException e) {
            // Captura erros específicos de SQL (deve ser redundante, pois já está coberto acima)
            throw new RuntimeException(e);  // Lança uma exceção de runtime caso ocorra algum erro com o SQL
        }

        return conn;  // Retorna a conexão estabelecida (ou null se houve falha)
    }
}
