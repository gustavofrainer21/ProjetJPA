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

const EstudanteCRUD = () => {
  const [estudantes, setEstudantes] = useState([]);
  const [open, setOpen] = useState(false);
  const [editing, setEditing] = useState(null);
  const [form, setForm] = useState({ nome: '', email: '', telefone: '', endereco: '', curso: '' });

  const token = localStorage.getItem('token');
  const config = { headers: { Authorization: `Bearer ${token}` } };

  useEffect(() => {
    fetchEstudantes();
  }, []);

  const fetchEstudantes = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/estudantes', config);
      setEstudantes(res.data);
    } catch (error) {
      console.error('Error fetching estudantes:', error);
    }
  };

  const handleOpen = (estudante = null) => {
    setEditing(estudante);
    setForm(estudante ? { nome: estudante.nome, email: estudante.email, telefone: estudante.telefone, endereco: estudante.endereco, curso: estudante.curso } : { nome: '', email: '', telefone: '', endereco: '', curso: '' });
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setEditing(null);
    setForm({ nome: '', email: '', telefone: '', endereco: '', curso: '' });
  };

  const handleSave = async () => {
    try {
      if (editing) {
        await axios.put(`http://localhost:8080/api/estudantes/${editing.id}`, form, config);
      } else {
        await axios.post('http://localhost:8080/api/estudantes', form, config);
      }
      fetchEstudantes();
      handleClose();
    } catch (error) {
      console.error('Error saving estudante:', error);
    }
  };

  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/estudantes/${id}`, config);
      fetchEstudantes();
    } catch (error) {
      console.error('Error deleting estudante:', error);
    }
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Gerenciar Estudantes
      </Typography>
      <Button variant="contained" startIcon={<Add />} onClick={() => handleOpen()}>
        Adicionar Estudante
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
              <TableCell>Curso</TableCell>
              <TableCell>Ações</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {estudantes.map((estudante) => (
              <TableRow key={estudante.id}>
                <TableCell>{estudante.id}</TableCell>
                <TableCell>{estudante.nome}</TableCell>
                <TableCell>{estudante.email}</TableCell>
                <TableCell>{estudante.telefone}</TableCell>
                <TableCell>{estudante.endereco}</TableCell>
                <TableCell>{estudante.curso}</TableCell>
                <TableCell>
                  <IconButton onClick={() => handleOpen(estudante)}>
                    <Edit />
                  </IconButton>
                  <IconButton onClick={() => handleDelete(estudante.id)}>
                    <Delete />
                  </IconButton>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>{editing ? 'Editar Estudante' : 'Adicionar Estudante'}</DialogTitle>
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
          <TextField
            margin="dense"
            label="Curso"
            fullWidth
            value={form.curso}
            onChange={(e) => setForm({ ...form, curso: e.target.value })}
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

export default EstudanteCRUD;
