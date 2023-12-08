package com.uel.sistema_analise_crimes.DAO;

import com.uel.sistema_analise_crimes.models.Crimes;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public interface CrimesDAO extends DAO<Crimes> {
    public int getId(Crimes crime);

    public List<Map<String, Object>> get_todas_ocorrencias_crimes() throws SQLException;
}
