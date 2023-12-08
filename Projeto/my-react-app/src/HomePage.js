import React from 'react';
import './App.css';
import { Link } from 'react-router-dom';

function App() {
  const containerStyle = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    height: '100vh',
    backgroundColor: '#001f3f', // Azul marinho
    color: '#ffdc00', // Amarelo
  };

  const imageStyle = {
    width: '480px', // Ajuste o tamanho conforme necessário
    marginBottom: '20px',
  };

  const buttonStyle = {
    padding: '10px 20px',
    fontSize: '16px',
    backgroundColor: '#ffdc00', // Amarelo
    color: '#001f3f', // Azul marinho
    borderRadius: '5px',
    cursor: 'pointer',
  };

  return (
    <div style={containerStyle}>
      <img src="/crime_watch.png" alt="Crime Watch" style={imageStyle} />
      <p>Bem-vindo ao nosso aplicativo de relato de crimes.</p>
      <Link to="/CrimeForm">
        <button style={buttonStyle}>Relatar Crime</button>
      </Link>
      <br />
      <Link to="/CrimeList">
        <button style={buttonStyle}>Ver Crimes</button>
      </Link>
      <br />
      <Link to="/City-options">
        <button style={buttonStyle}>Configuraçoes de Cidade</button>
      </Link>
      <br />
      <Link to="/Relatorios">
        <button style={buttonStyle}>Relatorios</button>
      </Link>
    </div>
  );
}

export default App;