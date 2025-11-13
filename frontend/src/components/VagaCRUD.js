import React, { useState, useEffect } from 'react';
import {
  Container,
  Typography,
  Button,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  IconButton,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
} from '@mui/material';
import { Edit, Delete, Add } from '@mui/icons-material';
import axios from 'axios';

const VagaCRUD = ({ user }) => {
  const [vagas, setVagas] = useState([]);
  const [empresas, setEmpresas] = useState([]);
  const [areas, setAreas] = useState([]);
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [form, setForm] = useState({ titulo: '', descricao: '', empresaId: '', areaInteresseId: '' });

  const token = localStorage.getItem('token');
  const config = { headers: { Authorization: `Bearer ${token}` } };

  useEffect(() => {
    fetchVagas();
    fetchEmpresas();
    fetchAreas();
  }, []);

  const fetchVagas = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/vagas', config);
      setVagas(res.data);
    } catch (error) {
      console.error('Error fetching vagas:', error);
    }
  };

  const fetchEmpresas = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/empresas', config);
      setEmpresas(res.data);
    } catch (error) {
      console.error('Error fetching empresas:', error);
    }
  };

  const fetchAreas = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/areaInteresse', config);
      setAreas(res.data);
    } catch (error) {
      console.error('Error fetching areas:', error);
    }
  };

  const handleOpen = (vaga = null) => {
    setEditing(vaga);
    setForm(vaga ? { titulo: vaga.titulo, descricao: vaga.descricao, empresaId: vaga.empresa.id, areaInteresseId: vaga.areaInteresse.id } : { titulo: '', descricao: '', empresaId: '', areaInteresseId: '' });
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setEditing(null);
    setForm({ titulo: '', descricao: '', empresaId: '', areaInteresseId: '' });
  };

  const handleSave = async () => {
    try {
      const payload = {
        titulo: form.titulo,
        descricao: form.descricao,
        empresa: { id: form.empresaId },
        areaInteresse: { id: form.areaInteresseId },
      };
      if (editing) {
        await axios.put(`http://localhost:8080/api/vagas/${editing.id}`, payload, config);
      } else {
        await axios.post('http://localhost:8080/api/vagas', payload, config);
      }
      fetchVagas();
      handleClose();
    } catch (error) {
      console.error('Error saving vaga:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/vagas/${id}`, config);
      fetchVagas();
    } catch (error) {
      console.error('Error deleting vaga:', error);
    }
  };

  // Filter vagas based on user role
  const filteredVagas = user.roles.includes('ROLE_EMPRESA') ? vagas.filter(v => v.empresa.id === user.id) : vagas;

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Gerenciar Vagas
      </Typography>
      <Button variant="contained" startIcon={<Add />} onClick={() => handleOpen()}>
        Adicionar Vaga
      </Button>
      <TableContainer component={Paper} sx={{ mt: 2 }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Título</TableCell>
              <TableCell>Descrição</TableCell>
              <TableCell>Empresa</TableCell>
              <TableCell>Área</TableCell>
              <TableCell>Ações</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {filteredVagas.map((vaga) => (
              <TableRow key={vaga.id}>
                <TableCell>{vaga.id}</TableCell>
                <TableCell>{vaga.titulo}</TableCell>
                <TableCell>{vaga.descricao}</TableCell>
                <TableCell>{vaga.empresa.nome}</TableCell>
                <TableCell>{vaga.areaInteresse.nome}</TableCell>
                <TableCell>
                  <IconButton onClick={() => handleOpen(vaga)}>
                    <Edit />
                  </IconButton>
                  <IconButton onClick={() => handleDelete(vaga.id)}>
                    <Delete />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>{editing ? 'Editar Vaga' : 'Adicionar Vaga'}</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Título"
            fullWidth
            value={form.titulo}
            onChange={(e) => setForm({ ...form, titulo: e.target.value })}
          />
          <TextField
            margin="dense"
            label="Descrição"
            fullWidth
            multiline
            rows={4}
            value={form.descricao}
            onChange={(e) => setForm({ ...form, descricao: e.target.value })}
          />
          <FormControl fullWidth margin="dense">
            <InputLabel>Empresa</InputLabel>
            <Select
              value={form.empresaId}
              onChange={(e) => setForm({ ...form, empresaId: e.target.value })}
            >
              {empresas.map((empresa) => (
                <MenuItem key={empresa.id} value={empresa.id}>
                  {empresa.nome}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
          <FormControl fullWidth margin="dense">
            <InputLabel>Área de Interesse</InputLabel>
            <Select
              value={form.areaInteresseId}
              onChange={(e) => setForm({ ...form, areaInteresseId: e.target.value })}
            >
              {areas.map((area) => (
                <MenuItem key={area.id} value={area.id}>
                  {area.nome}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancelar</Button>
          <Button onClick={handleSave}>Salvar</Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default VagaCRUD;
