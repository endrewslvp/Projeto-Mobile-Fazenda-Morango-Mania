package com.example.morangomania.model;

import java.io.Serializable;

public class Cliente implements Serializable {

    private int id;
    private String nome;
    private String telefone;
    private String email;
    private String cpf;
    private Endereco endereco;
    private String senha;

    public String getCep() {
        return endereco.getCep();
    }

    public String getCidade(){
        return endereco.getCidade();
    }

    public String getRua() {
        return endereco.getRua();
    }

    public String getNumero() {
        return endereco.getNumero();
    }

    public String getBairro() {
        return endereco.getBairro();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
