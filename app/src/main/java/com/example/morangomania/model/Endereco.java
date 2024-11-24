package com.example.morangomania.model;

public class Endereco {

    private String cep;
    private String rua;
    private String numero;
    private  String bairro;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    @Override
    public String toString(){
        return "Rua: " + rua + ", Bairro: " + bairro + ", Cidade: " + ", CEP: " + cep;
    }
}
