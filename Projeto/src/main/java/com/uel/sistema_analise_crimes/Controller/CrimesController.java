package com.uel.sistema_analise_crimes.controllers;

import com.uel.sistema_analise_crimes.DAO.CrimesDAO;
import com.uel.sistema_analise_crimes.DAO.PgCrimesDAO;
import com.uel.sistema_analise_crimes.models.Crimes;

import java.sql.Connection;
import java.sql.SQLException;

public class CrimesController {
    private final CrimesDAO crimesDAO;

    public CrimesController(Connection connection) {
        this.crimesDAO = new PgCrimesDAO(connection);
    }

    public void criarNovoCrime(Crimes crime) {
        try {
            crimesDAO.create(crime);
            System.out.println("Crime inserido com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir crime no banco de dados: " + e.getMessage());
        }
    }
}
