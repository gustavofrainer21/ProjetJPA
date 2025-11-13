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

const EmpresaCRUD = () => {
  const [empresas, setEmpresas] = useState([]);
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [form, setForm] = useState({ nome: '', email: '', telefone: '', endereco: '' });

  const token = localStorage.getItem('token');
  const config = { headers: { Authorization: `Bearer ${token}` } };

  useEffect(() => {
    fetchEmpresas();
  }, []);

  const fetchEmpresas = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/empresas', config);
      setEmpresas(res.data);
    } catch (error) {
      console.error('Error fetching empresas:', error);
    }
  };

  const handleOpen = (empresa = null) => {
    setEditing(empresa);
    setForm(empresa ? { nome: empresa.nome, email: empresa.email, telefone: empresa.telefone, endereco: empresa.endereco } : { nome: '', email: '', telefone: '', endereco: '' });
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setEditing(null);
    setForm({ nome: '', email: '', telefone: '', endereco: '' });
  };

  const handleSave = async () => {
    try {
      if (editing) {
        await axios.put(`http://localhost:8080/api/empresas/${editing.id}`, form, config);
      } else {
        await axios.post('http://localhost:8080/api/empresas', form, config);
      }
      fetchEmpresas();
      handleClose();
    } catch (error) {
      console.error('Error saving empresa:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/empresas/${id}`, config);
      fetchEmpresas();
    } catch (error) {
      console.error('Error deleting empresa:', error);
    }
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Gerenciar Empresas
      </Typography>
      <Button variant="contained" startIcon={<Add />} onClick={() => handleOpen()}>
        Adicionar Empresa
      </Button>
      <TableContainer component={Paper} sx={{ mt: 2 }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Nome</TableCell>
              <TableCell>Email</TableCell>
              <TableCell>Telefone</TableCell>
              <TableCell>Endereço</TableCell>
              <TableCell>Ações</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {empresas.map((empresa) => (
              <TableRow key={empresa.id}>
                <TableCell>{empresa.id}</TableCell>
                <TableCell>{empresa.nome}</TableCell>
                <TableCell>{empresa.email}</TableCell>
                <TableCell>{empresa.telefone}</TableCell>
                <TableCell>{empresa.endereco}</TableCell>
                <TableCell>
                  <IconButton onClick={() => handleOpen(empresa)}>
                    <Edit />
                  </IconButton>
                  <IconButton onClick={() => handleDelete(empresa.id)}>
                    <Delete />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>{editing ? 'Editar Empresa' : 'Adicionar Empresa'}</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Nome"
            fullWidth
            value={form.nome}
            onChange={(e) => setForm({ ...form, nome: e.target.value })}
          />
          <TextField
            margin="dense"
            label="Email"
            fullWidth
            value={form.email}
            onChange={(e) => setForm({ ...form, email: e.target.value })}
          />
          <TextField
            margin="dense"
            label="Telefone"
            fullWidth
            value={form.telefone}
            onChange={(e) => setForm({ ...form, telefone: e.target.value })}
          />
          <TextField
            margin="dense"
            label="Endereço"
            fullWidth
            value={form.endereco}
            onChange={(e) => setForm({ ...form, endereco: e.target.value })}
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

export default EmpresaCRUD;
