package com.example.morangomania.model;

import java.io.Serializable;

public class Endereco implements Serializable {

    private String cep;
    private String cidade;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString(){
        return getRua()+", "+getNumero()+" - "+getBairro()+" - "+getCep()+" - "+getCidade();
    }
}
