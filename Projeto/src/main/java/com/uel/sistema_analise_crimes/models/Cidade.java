package com.uel.sistema_analise_crimes.models;

public class Cidade {
    private String nome;
    private String nome_estado;
    private String sigla_estado;
    private String nome_pais;
    private float area_cidade;
    private int populacao_cidade;
    private float rpc_cidade;

    public Cidade(String nome, String nome_estado, String sigla_estado, String nome_pais, float area_cidade, int populacao_cidade, float rpc_cidade) {
        this.nome = nome;
        this.nome_estado = nome_estado;
        this.sigla_estado = sigla_estado;
        this.nome_pais = nome_pais;
        this.area_cidade = area_cidade;
        this.populacao_cidade = populacao_cidade;
        this.rpc_cidade = rpc_cidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome_estado() {
        return nome_estado;
    }

    public void setNome_estado(String nome_estado) {
        this.nome_estado = nome_estado;
    }

    public String getSigla_estado() {
        return sigla_estado;
    }

    public void setSigla_estado(String sigla_estado) {
        this.sigla_estado = sigla_estado;
    }

    public String getNome_pais() {
        return nome_pais;
    }

    public void setNome_pais(String nome_pais) {
        this.nome_pais = nome_pais;
    }

    public float getArea_cidade() {
        return area_cidade;
    }

    public void setArea_cidade(float area_cidade) {
        this.area_cidade = area_cidade;
    }

    public int getPopulacao_cidade() {
        return populacao_cidade;
    }

    public void setPopulacao_cidade(int populacao_cidade) {
        this.populacao_cidade = populacao_cidade;
    }

    public float getRpc_cidade() {
        return rpc_cidade;
    }

    public void setRpc_cidade(float rpc_cidade) {
        this.rpc_cidade = rpc_cidade;
    }
}
