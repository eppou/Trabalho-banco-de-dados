import React from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import { useState } from "react";
import './Tabela.css';

const containerStyle = {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    height: '130vh',
    backgroundColor: '#001f3f', // Azul marinho
    color: '#ffdc00', // Amarelo
};

const headerStyle = {
    display: 'flex',
    alignItems: 'top',
    justifyContent: 'top',
    width: '100%',
    height: '200%',
    padding: '3px',
    color: '001f3f', // 
    backgroundColor: '##ffdc00', 
};

const buttonStyle = {
    padding: '8px',
    marginBottom: '5px',
    width: '100%',
    fontSize: '16px',
    backgroundColor: '#ffdc00', // Amarelo
    color: '#001f3f', // Azul marinho
    borderRadius: '5px',
    cursor: 'pointer',
    alignItems: 'center',
    justifyContent: 'center',
};

const textStyle = {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%',
    height: '100%',
    padding: '10px',
    color: '#ffdc00', // Amarelo
};

const CrimeList = () => {
    const [crimes, setCrimes] = useState([]);
    const [currentPage, setCurrentPage] = useState(1);
    
    React.useEffect(() => {
        axios
        .get(`http://localhost:8080/api/crimes-list?page=` + currentPage + `&pageSize=10`)
        .then(response => {
            setCrimes(response.data);
        }
        );
    }, [currentPage]);
    
    const handlePageChange = newPage => {
        setCurrentPage(newPage);
    };

    return (
        <div style={containerStyle}>
            <div style={headerStyle}>
                <h1 style={textStyle}>Lista de crimes</h1>
            </div>
            <br />
            <table className="minha-tabela">
                <thead>
                    <tr>
                        <th>Tipo</th>
                        <th>Cidade</th>
                        <th>País</th>
                        <th>Descrição</th>
                        <th>Data</th>
                    </tr>
                </thead>
                <tbody>
                    {crimes.map(crime => (
                        <tr>
                            <td>{crime.tipo}</td>
                            <td>{crime.nome_cidade}</td>
                            <td>{crime.nome_pais}</td>
                            <td>{crime.descricao}</td>
                            <td>{crime.data_crime}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <div>
                <span>Página {currentPage}</span>
                <button style={buttonStyle} onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>Anterior</button>
                <button style={buttonStyle} onClick={() => handlePageChange(currentPage + 1)}>Próxima</button>
            </div>
            <br />
            <Link style={buttonStyle} to="/">Voltar</Link>
            </div>
        
    );
}

export default CrimeList;