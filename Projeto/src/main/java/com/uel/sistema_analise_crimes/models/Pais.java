package com.uel.sistema_analise_crimes.models;

public class Pais {
    private String pais;
    private float area;
    private int populacao;
    private float idh;
    private float rpc_pais;

    public String getPais() {
        return pais;
    }

    public Pais(String pais, float area, int populacao, float idh, float rpc_pais) {
        this.pais = pais;
        this.area = area;
        this.populacao = populacao;
        this.idh = idh;
        this.rpc_pais = rpc_pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public int getPopulacao() {
        return populacao;
    }

    public void setPopulacao(int populacao) {
        this.populacao = populacao;
    }

    public float getIdh() {
        return idh;
    }

    public void setIdh(float idh) {
        this.idh = idh;
    }

    public float getRpc_pais() {
        return rpc_pais;
    }

    public void setRpc_pais(float rpc_pais) {
        this.rpc_pais = rpc_pais;
    }
}
