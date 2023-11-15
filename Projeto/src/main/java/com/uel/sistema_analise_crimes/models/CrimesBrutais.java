package com.uel.sistema_analise_crimes.models;

public class CrimesBrutais {
    private int id_crime;
    private int num_vitimas;

    public int getId_crime() {
        return id_crime;
    }

    public void setId_crime(int id_crime) {
        this.id_crime = id_crime;
    }

    public int getNum_vitimas() {
        return num_vitimas;
    }

    public void setNum_vitimas(int num_vitimas) {
        this.num_vitimas = num_vitimas;
    }

    public CrimesBrutais(int id_crime, int num_vitimas) {
        this.id_crime = id_crime;
        this.num_vitimas = num_vitimas;
    }
}
