import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";


const AdicionarCidade = () => {
    const [nome, setNome] = useState("");
    const [nomeEstado, setNomeEstado] = useState("");
    const [siglaEstado, setSiglaEstado] = useState("");
    const [nomePais, setNomePais] = useState("");
    const [rendaPerCapita, setRendaPerCapita] = useState("");
    const [areaCidade, setAreaCidade] = useState("");
    const [populacaoCidade, setPopulacaoCidade] = useState("");
    const [rendaPerCapitaEstado, setRendaPerCapitaEstado] = useState("");
    const [areaEstado, setAreaEstado] = useState("");
    const [populacaoEstado, setPopulacaoEstado] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        // Lógica para enviar os dados para o servidor
        // ...
        const data = {
            nome,
            nomeEstado,
            siglaEstado,
            nomePais,
            rendaPerCapita,
            areaCidade,
            populacaoCidade,
            rendaPerCapitaEstado,
            areaEstado,
            populacaoEstado
        };

        sendDataToServer(data);

        setNome("");
        setNomeEstado("");
        setSiglaEstado("");
        setNomePais("");
        setRendaPerCapita("");
        setAreaCidade("");
        setPopulacaoCidade("");
        setRendaPerCapitaEstado("");
        setAreaEstado("");
        setPopulacaoEstado("");
    };

    const sendDataToServer = () => {
        // Criar um objeto com a estrutura desejada
        const crimeData = {
            nome: nome,
            nomeEstado: nomeEstado,
            siglaEstado: siglaEstado,
            nomePais: nomePais,
            rendaPerCapita: rendaPerCapita,
            areaCidade: areaCidade,
            populacaoCidade: populacaoCidade,
            rendaPerCapitaEstado: rendaPerCapitaEstado,
            areaEstado: areaCidade,
            populacaoEstado: populacaoEstado,
        };

        // Enviar os dados para o servidor
        axios.post('http://localhost:8080/api/upload-cidade', JSON.stringify(crimeData), {
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(response => {
                console.log('Resposta do servidor:', response.data);
            })
            .catch(error => {
                console.error('Erro na requisição:', error);
                console.error('Dados que não foram enviados:', JSON.stringify(crimeData));
                // Lógica para lidar com erros
            });
    };




    const formStyle = {
        width: '300px', // Ajuste o tamanho conforme necessário
        margin: '20px',
    };

    const labelStyle = {
        marginBottom: '8px',
    };

    const linkStyle = {
        textDecoration: 'none',
        color: '#ffdc00', // Amarelo
    };

    const inputStyle = {
        padding: '10px 20px',
        fontSize: '16px',
        backgroundColor: '#ffdc00', // Amarelo
        color: '#001f3f', // Azul marinho
        borderRadius: '5px',
        cursor: 'pointer',
    };

    const buttonStyle = {
        padding: '8px',
        marginBottom: '16px',
        width: '100%',
        fontSize: '16px',
        backgroundColor: '#ffdc00', // Amarelo
        color: '#001f3f', // Azul marinho
        borderRadius: '5px',
        cursor: 'pointer',
    };


    const selectStyle = {
        padding: '8px',
        marginBottom: '16px',
        width: '100%',
        fontSize: '16px',
        backgroundColor: '#ffdc00', // Amarelo
        color: '#001f3f', // Azul marinho
        borderRadius: '5px',
        cursor: 'pointer',
    };


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
        alignItems: 'center',
        justifyContent: 'center',
        width: '100%',
        height: '100%',
        padding: '10px',
        color: '#ffdc00', // Amarelo
    };

    return (
        <div style={containerStyle}>
            <div style={headerStyle}>
                <Link to="/" style={linkStyle}>
                    <img
                        src="/home_logo.png"
                        alt="Home"
                        style={{ width: '50px', height: '50px', cursor: 'pointer' }}
                    />
                </Link>
                <h1>Adicionar Cidade</h1>
            </div>
            <div style={formStyle}>
                <form onSubmit={handleSubmit}>
                    <label style={labelStyle}>Nome:</label>
                    <br />
                    <input
                        type="text"
                        value={nome}
                        onChange={(e) => setNome(e.target.value)}
                        style={inputStyle}
                    />
                    <br />


                    <label style={labelStyle}>Nome do Estado:</label>
                    <input
                        type="text"
                        value={nomeEstado}
                        onChange={(e) => setNomeEstado(e.target.value)}
                        style={inputStyle}
                    />
                    <br />


                    <label style={labelStyle}>Sigla do Estado:</label>
                    <input
                        type="text"
                        value={siglaEstado}
                        onChange={(e) => setSiglaEstado(e.target.value)}
                        style={inputStyle}
                    />
                    <br />


                    <label style={labelStyle}>Nome do País:</label>
                    <input
                        type="text"
                        value={nomePais}
                        onChange={(e) => setNomePais(e.target.value)}
                        style={inputStyle}
                    />
                    <br />


                    <label style={labelStyle}>Renda Per Capita:</label>
                    <input
                        type="text"
                        value={rendaPerCapita}
                        onChange={(e) => setRendaPerCapita(e.target.value)}
                        style={inputStyle}
                    />
                    <br />


                    <label style={labelStyle}>Área da Cidade:</label>
                    <input
                        type="text"
                        value={areaCidade}
                        onChange={(e) => setAreaCidade(e.target.value)}
                        style={inputStyle}
                    />

                    <br />

                    <label style={labelStyle}>População da Cidade:</label>
                    <input
                        type="text"
                        value={populacaoCidade}
                        onChange={(e) => setPopulacaoCidade(e.target.value)}
                        style={inputStyle}
                    />
                    <br />


                    <label style={labelStyle}>Renda Per Capita do Estado:</label>
                    <input
                        type="text"
                        value={rendaPerCapitaEstado}
                        onChange={(e) => setRendaPerCapitaEstado(e.target.value)}
                        style={inputStyle}
                    />
                    <br />


                    <label style={labelStyle}>Área do Estado:</label>
                    <input
                        type="text"
                        value={areaEstado}
                        onChange={(e) => setAreaEstado(e.target.value)}
                        style={inputStyle}
                    />
                    <br />


                    <label style={labelStyle}>População do Estado:</label>
                    <input
                        type="text"
                        value={populacaoEstado}
                        onChange={(e) => setPopulacaoEstado(e.target.value)}
                        style={inputStyle}
                    />
                    <br />
                    <br />
                    <button type="submit" style={buttonStyle}>

                        Adicionar Cidade
                    </button>
                </form>
            </div>
        </div>
    );

};

export default AdicionarCidade;
