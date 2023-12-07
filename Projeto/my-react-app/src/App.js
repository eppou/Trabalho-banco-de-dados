import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './HomePage';
import CrimeForm from './CrimeForm';
import CrimeList from './CrimeList';
import './App.css';
import AdicionarCidade from './AdicionarCidade';
import CityOptions from './CityOptions';

const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/CrimeForm" element={<CrimeForm />} />
                <Route path="/CrimeList" element={<CrimeList />} />
                <Route path="/AdicionarCidade" element={<AdicionarCidade />} />
                <Route path="/City-options" element={<CityOptions />} />
            </Routes>
        </Router>
    );
};

export default App;
