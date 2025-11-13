import React, { useEffect, useState } from 'react';
import { Container, Typography, Grid, Card, CardContent, Button, Tabs, Tab } from '@mui/material';
import axios from 'axios';
import MyCalendar from './Calendar';
import AreaInteresseCRUD from './AreaInteresseCRUD';
import EmpresaCRUD from './EmpresaCRUD';
import EstudanteCRUD from './EstudanteCRUD';
import VagaCRUD from './VagaCRUD';

const Dashboard = ({ user, onLogout }) => {
  const [vagas, setVagas] = useState([]);
  const [inscricoes, setInscricoes] = useState([]);
  const [tabValue, setTabValue] = useState(0);

  // Determine available tabs based on user role
  const getTabs = () => {
    const tabs = [];
    if (user.roles.includes('ROLE_ADMIN')) {
      tabs.push({ label: 'Áreas', component: <AreaInteresseCRUD /> });
      tabs.push({ label: 'Empresas', component: <EmpresaCRUD /> });
      tabs.push({ label: 'Estudantes', component: <EstudanteCRUD /> });
      tabs.push({ label: 'Vagas', component: <VagaCRUD user={user} /> });
    } else if (user.roles.includes('ROLE_EMPRESA')) {
      tabs.push({ label: 'Vagas', component: <VagaCRUD user={user} /> });
    } else if (user.roles.includes('ROLE_ESTUDANTE')) {
      tabs.push({ label: 'Vagas', component: <VagaCRUD user={user} /> });
    }
    tabs.push({ label: 'Calendário', component: <MyCalendar user={user} /> });
    return tabs;
  };

  const tabs = getTabs();

  useEffect(() => {
    const fetchData = async () => {
      const token = localStorage.getItem('token');
      const config = { headers: { Authorization: `Bearer ${token}` } };

      if (user.roles.includes('ROLE_ESTUDANTE')) {
        const vagasRes = await axios.get('http://localhost:8080/api/vagas', config);
        setVagas(vagasRes.data);
        const inscricoesRes = await axios.get('http://localhost:8080/api/inscricoes', config);
        setInscricoes(inscricoesRes.data);
      } else if (user.roles.includes('ROLE_EMPRESA')) {
        const vagasRes = await axios.get('http://localhost:8080/api/vagas', config);
        setVagas(vagasRes.data);
      }
    };
    fetchData();
  }, [user]);

  const handleInscricao = async (vagaId) => {
    const token = localStorage.getItem('token');
    const config = { headers: { Authorization: `Bearer ${token}` } };
    await axios.post('http://localhost:8080/api/inscricoes', { vagaId }, config);
    // Refresh data
    const inscricoesRes = await axios.get('http://localhost:8080/api/inscricoes', config);
    setInscricoes(inscricoesRes.data);
  };

  const handleTabChange = (event, newValue) => {
    setTabValue(newValue);
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Dashboard - {user.username}
      </Typography>
      <Button onClick={onLogout}>Logout</Button>

      <Tabs value={tabValue} onChange={handleTabChange} aria-label="dashboard tabs">
        {tabs.map((tab, index) => (
          <Tab key={index} label={tab.label} />
        ))}
      </Tabs>

      {tabs[tabValue] && tabs[tabValue].component}
    </Container>
  );
};

export default Dashboard;
