import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Chart from 'chart.js/auto';
import axios from "axios";

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

const labelStyle = {
  marginBottom: '8px',
};

const containerStyle = {
  display: 'flex',
  flexDirection: 'column',
  alignItems: 'center',
  justifyContent: 'center',
  height: '190vh',
  backgroundColor: '#001f3f', // Azul marinho
  color: '#ffdc00', // Amarelo
};

const linkStyle = {
  textDecoration: 'none',
  color: '#ffdc00', // Amarelo
};

const headerStyle = {
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
  width: '100%',
  height: '100%',
  padding: '10px',
  color: '#ffdc00', // Amarelo
};

const RpcChart = () => {
    const [crimes, setCrimes] = useState([]);

    React.useEffect(() => {
        axios
          .get(`http://localhost:8080/api/relatorio_crime_rpc_cidade`)
          .then(response => {
            setCrimes(response.data);
          });
      }, []);

    useEffect(() => {
        if(crimes.length > 0) {
            const ctx = document.getElementById('myChart').getContext('2d');
            const cidade = crimes.map(crime => crime.nome_cidade);
            const rendaPerCapita = crimes.map(crime => crime.rpc_cidade);
            const total_crimes = crimes.map(crime => crime.total_crimes);

            // Create a bar chart
            let myChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: cidade,
                    datasets: [
                        {
                            label: 'Renda per capita',
                            data: rendaPerCapita,
                            backgroundColor: 'green', // Amarelo
                            borderColor: '#001f3f', // Azul marinho
                            borderWidth: 1,
                        },
                        {
                            label: 'Total de crimes',
                            data: total_crimes,
                            backgroundColor: 'red', // Azul marinho
                            borderColor: '#ffdc00', // Amarelo
                            borderWidth: 1,
                        },
                    ],
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true,
                        },
                    },
                },
            });
            return () => {
                myChart.destroy();
            };
        }
    }
    , [crimes]);



    return (
        <div style={containerStyle}>
            <h1 style={headerStyle}>Relatório de Crimes por Renda per capita</h1>
            <Link to="/Relatorios">
                <button style={buttonStyle}>Voltar</button>
            </Link>
            <br />
            <div style={{ width: '80%', height: '80%', padding: '10px' }}>
            <canvas id="myChart" width="200" height="200"></canvas>
            </div>
        </div>
    );

}

export default RpcChart;