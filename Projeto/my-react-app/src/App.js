import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './HomePage';
import CrimeForm from './CrimeForm';
import './App.css';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/CrimeForm" element={<CrimeForm />} />
      </Routes>
    </Router>
  );
};

export default App;
