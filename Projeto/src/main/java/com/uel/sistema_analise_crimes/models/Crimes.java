package com.uel.sistema_analise_crimes.models;
import java.util.Date;
public class Crimes {
    private int id_crime;
    private String tipo;
    private String descricao;
    private Date data_crime;
    private float latitude;
    private float longitude;
    private String nome_cidade;
    private String nome_estado;
    private String sigla_estado;
    private String nome_pais;

    public Crimes(){

    }

    public Crimes(int id_crime, String tipo, String descricao, Date data_crime, float latitude, float longitude, String nome_cidade, String nome_estado, String sigla_estado, String nome_pais) {
        this.id_crime = id_crime;
        this.tipo = tipo;
        this.descricao = descricao;
        this.data_crime = data_crime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nome_cidade = nome_cidade;
        this.nome_estado = nome_estado;
        this.sigla_estado = sigla_estado;
        this.nome_pais = nome_pais;
    }

    public int getId_crime() {
        return id_crime;
    }

    public void setId_crime(int id_crime) {
        this.id_crime = id_crime;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData_crime() {
        return data_crime;
    }

    public void setData_crime(Date data_crime) {
        this.data_crime = data_crime;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getNome_cidade() {
        return nome_cidade;
    }

    public void setNome_cidade(String nome_cidade) {
        this.nome_cidade = nome_cidade;
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
}
