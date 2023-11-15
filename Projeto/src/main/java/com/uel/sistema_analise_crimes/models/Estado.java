package com.uel.sistema_analise_crimes.models;

public class Estado {
    private String sigla;
    private String nome;
    private String nome_pais;
    private float rpc_estado;
    private float area_estado;
    private int populacao_estado;

    public Estado(String sigla, String nome, String nome_pais, float rpc_estado, float area_estado, int populacao_estado) {
        this.sigla = sigla;
        this.nome = nome;
        this.nome_pais = nome_pais;
        this.rpc_estado = rpc_estado;
        this.area_estado = area_estado;
        this.populacao_estado = populacao_estado;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome_pais() {
        return nome_pais;
    }

    public void setNome_pais(String nome_pais) {
        this.nome_pais = nome_pais;
    }

    public float getRpc_estado() {
        return rpc_estado;
    }

    public void setRpc_estado(float rpc_estado) {
        this.rpc_estado = rpc_estado;
    }

    public float getArea_estado() {
        return area_estado;
    }

    public void setArea_estado(float area_estado) {
        this.area_estado = area_estado;
    }

    public int getPopulacao_estado() {
        return populacao_estado;
    }

    public void setPopulacao_estado(int populacao_estado) {
        this.populacao_estado = populacao_estado;
    }
}
