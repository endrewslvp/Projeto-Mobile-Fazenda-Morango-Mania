package com.example.morangomania.model;

import java.io.Serializable;

public class Cliente implements Serializable {

    // Atributos do cliente
    private int id;  // ID único do cliente
    private String nome;  // Nome do cliente
    private String telefone;  // Número de telefone do cliente
    private String email;  // E-mail do cliente
    private String cpf;  // CPF do cliente
    private Endereco endereco;  // Objeto Endereco associado ao cliente
    private String senha;  // Senha para login do cliente

    // Métodos de acesso (getters) para obter informações do endereço do cliente
    public String getCep() {
        return endereco.getCep();  // Retorna o CEP do endereço associado ao cliente
    }

    public String getCidade() {
        return endereco.getCidade();  // Retorna a cidade do endereço associado ao cliente
    }

    public String getRua() {
        return endereco.getRua();  // Retorna a rua do endereço associado ao cliente
    }

    public String getNumero() {
        return endereco.getNumero();  // Retorna o número do endereço associado ao cliente
    }

    public String getBairro() {
        return endereco.getBairro();  // Retorna o bairro do endereço associado ao cliente
    }

    // Métodos de acesso para os atributos do cliente
    public int getId() {
        return id;  // Retorna o ID do cliente
    }

    public void setId(int id) {
        this.id = id;  // Define o ID do cliente
    }

    public String getNome() {
        return nome;  // Retorna o nome do cliente
    }

    public void setNome(String nome) {
        this.nome = nome;  // Define o nome do cliente
    }

    public String getTelefone() {
        return telefone;  // Retorna o telefone do cliente
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;  // Define o telefone do cliente
    }

    public String getEmail() {
        return email;  // Retorna o e-mail do cliente
    }

    public void setEmail(String email) {
        this.email = email;  // Define o e-mail do cliente
    }

    public String getCpf() {
        return cpf;  // Retorna o CPF do cliente
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;  // Define o CPF do cliente
    }

    public Endereco getEndereco() {
        return endereco;  // Retorna o objeto Endereco do cliente
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;  // Define o objeto Endereco do cliente
    }

    public String getSenha() {
        return senha;  // Retorna a senha do cliente
    }

    public void setSenha(String senha) {
        this.senha = senha;  // Define a senha do cliente
    }
}
