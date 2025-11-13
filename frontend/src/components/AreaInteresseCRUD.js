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
} from '@mui/material';
import { Edit, Delete, Add } from '@mui/icons-material';
import axios from 'axios';

const AreaInteresseCRUD = () => {
  const [areas, setAreas] = useState([]);
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [form, setForm] = useState({ nome: '' });

  const token = localStorage.getItem('token');
  const config = { headers: { Authorization: `Bearer ${token}` } };

  useEffect(() => {
    fetchAreas();
  }, []);

  const fetchAreas = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/areaInteresse', config);
      setAreas(res.data);
    } catch (error) {
      console.error('Error fetching areas:', error);
    }
  };

  const handleOpen = (area = null) => {
    setEditing(area);
    setForm(area ? { nome: area.nome } : { nome: '' });
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setEditing(null);
    setForm({ nome: '' });
  };

  const handleSave = async () => {
    try {
      if (editing) {
        await axios.put(`http://localhost:8080/api/areaInteresse/${editing.id}`, form, config);
      } else {
        await axios.post('http://localhost:8080/api/areaInteresse', form, config);
      }
      fetchAreas();
      handleClose();
    } catch (error) {
      console.error('Error saving area:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/areaInteresse/${id}`, config);
      fetchAreas();
    } catch (error) {
      console.error('Error deleting area:', error);
    }
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Gerenciar Áreas de Interesse
      </Typography>
      <Button variant="contained" startIcon={<Add />} onClick={() => handleOpen()}>
        Adicionar Área
      </Button>
      <TableContainer component={Paper} sx={{ mt: 2 }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Nome</TableCell>
              <TableCell>Ações</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {areas.map((area) => (
              <TableRow key={area.id}>
                <TableCell>{area.id}</TableCell>
                <TableCell>{area.nome}</TableCell>
                <TableCell>
                  <IconButton onClick={() => handleOpen(area)}>
                    <Edit />
                  </IconButton>
                  <IconButton onClick={() => handleDelete(area.id)}>
                    <Delete />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>{editing ? 'Editar Área' : 'Adicionar Área'}</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Nome"
            fullWidth
            value={form.nome}
            onChange={(e) => setForm({ ...form, nome: e.target.value })}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancelar</Button>
          <Button onClick={handleSave}>Salvar</Button>
        </DialogActions>
      </Dialog>
    </Container>
  );
};

export default AreaInteresseCRUD;
