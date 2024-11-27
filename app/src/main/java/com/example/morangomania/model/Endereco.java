package com.example.morangomania.model;

import java.io.Serializable;

public class Endereco implements Serializable {

    // Atributos que representam o endereço
    private String cep;  // Código de Endereçamento Postal
    private String cidade;  // Cidade do endereço
    private String rua;  // Nome da rua
    private String numero;  // Número da residência
    private String bairro;  // Bairro do endereço

    // Metodos de acesso para os atributos
    public String getCep() {
        return cep;  // Retorna o CEP
    }

    public void setCep(String cep) {
        this.cep = cep;  // Define o CEP
    }

    public String getRua() {
        return rua;  // Retorna o nome da rua
    }

    public void setRua(String rua) {
        this.rua = rua;  // Define o nome da rua
    }

    public String getNumero() {
        return numero;  // Retorna o número da residência
    }

    public void setNumero(String numero) {
        this.numero = numero;  // Define o número da residência
    }

    public String getBairro() {
        return bairro;  // Retorna o bairro
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;  // Define o bairro
    }

    public String getCidade() {
        return cidade;  // Retorna a cidade
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;  // Define a cidade
    }

    // Metodo para representar o endereço como uma string
    @Override
    public String toString(){
        // Retorna o endereço completo em formato legível
        return getRua() + ", " + getNumero() + " - " + getBairro() + " - " + getCep() + " - " + getCidade();
    }
}
