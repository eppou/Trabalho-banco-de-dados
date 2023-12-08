import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './HomePage';
import CrimeForm from './CrimeForm';
import CrimeList from './CrimeList';
import './App.css';
import AdicionarCidade from './AdicionarCidade';
import CityOptions from './CityOptions';
import Relatorios from './Relatorios';
import Relatorio_cidade from './Relatorio_cidade';
import Relatorio_populacao from './Relatorio_populacao';
import Relatorio_rpc from './Relatorio_rpc';

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/CrimeForm" element={<CrimeForm />} />
                <Route path="/CrimeList" element={<CrimeList />} />
                <Route path="/AdicionarCidade" element={<AdicionarCidade />} />
                <Route path="/City-options" element={<CityOptions />} />
                <Route path="/Relatorios" element={<Relatorios />} />
                <Route path="/Relatorio_cidade" element={<Relatorio_cidade />} />
                <Route path="/Relatorio_populacao" element={<Relatorio_populacao />} />
                <Route path="/Relatorio_rpc" element={<Relatorio_rpc />} />
            </Routes>
        </Router>
    );
};

export default App;
