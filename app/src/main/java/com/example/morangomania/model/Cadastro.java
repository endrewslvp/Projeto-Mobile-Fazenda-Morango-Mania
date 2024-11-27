package com.example.morangomania.model;

public class Cadastro {

    // Atributos privados da classe Cadastro
    private String nome;  // Nome do usuário
    private String cpf;   // CPF do usuário
    private String cargo; // Cargo do usuário (exemplo: "Gerente", "Vendedor", etc.)
    private String email; // E-mail do usuário
    private String senha; // Senha do usuário

    // Metodo para obter o nome
    public String getNome() {
        return nome;  // Retorna o nome armazenado
    }

    // Metodo para definir o nome
    public void setNome(String nome) {
        this.nome = nome;  // Define o valor do nome
    }

    // Metodo para obter o CPF
    public String getCpf() {
        return cpf;  // Retorna o CPF armazenado
    }

    // Metodo para definir o CPF
    public void setCpf(String cpf) {
        this.cpf = cpf;  // Define o valor do CPF
    }

    // Metodo para obter o cargo
    public String getCargo() {
        return cargo;  // Retorna o cargo armazenado
    }

    // Metodo para definir o cargo
    public void setCargo(String cargo) {
        this.cargo = cargo;  // Define o valor do cargo
    }

    // Metodo para obter o e-mail
    public String getEmail() {
        return email;  // Retorna o e-mail armazenado
    }

    // Metodo para definir o e-mail
    public void setEmail(String email) {
        this.email = email;  // Define o valor do e-mail
    }

    // Metodo para obter a senha
    public String getSenha() {
        return senha;  // Retorna a senha armazenada
    }

    // Metodo para definir a senha
    public void setSenha(String senha) {
        this.senha = senha;  // Define o valor da senha
    }
}
