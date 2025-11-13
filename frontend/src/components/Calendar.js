import React, { useState, useEffect } from 'react';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import { Container, Typography, Button, Dialog, DialogTitle, DialogContent, DialogActions, TextField } from '@mui/material';
import axios from 'axios';

const localizer = momentLocalizer(moment);

const MyCalendar = ({ user }) => {
  const [events, setEvents] = useState([]);
  const [open, setOpen] = useState(false);
  const [selectedSlot, setSelectedSlot] = useState(null);
  const [title, setTitle] = useState('');

  useEffect(() => {
    const fetchEntrevistas = async () => {
      const token = localStorage.getItem('token');
      const config = { headers: { Authorization: `Bearer ${token}` } };
      const response = await axios.get('http://localhost:8080/api/entrevistas', config);
      const entrevistas = response.data.map(ent => ({
        id: ent.id,
        title: ent.titulo,
        start: new Date(ent.dataHora),
        end: new Date(new Date(ent.dataHora).getTime() + 60 * 60 * 1000), // 1 hour
        resource: ent
      }));
      setEvents(entrevistas);
    };
    fetchEntrevistas();
  }, []);

  const handleSelectSlot = (slotInfo) => {
    setSelectedSlot(slotInfo);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setTitle('');
  };

  const handleSave = async () => {
    const token = localStorage.getItem('token');
    const config = { headers: { Authorization: `Bearer ${token}` } };
    await axios.post('http://localhost:8080/api/entrevistas', {
      titulo: title,
      dataHora: selectedSlot.start,
      inscricaoId: 1 // Assuming a default or selected inscricao
    }, config);
    // Refresh events
    const response = await axios.get('http://localhost:8080/api/entrevistas', config);
    const entrevistas = response.data.map(ent => ({
      id: ent.id,
      title: ent.titulo,
      start: new Date(ent.dataHora),
      end: new Date(new Date(ent.dataHora).getTime() + 60 * 60 * 1000),
      resource: ent
    }));
    setEvents(entrevistas);
    handleClose();
  };

  return (
    <Container>
      <Typography variant="h4" gutterBottom>Calendário de Entrevistas</Typography>
      <Calendar
        localizer={localizer}
        events={events}
        startAccessor="start"
        endAccessor="end"
        style={{ height: 500 }}
        selectable
        onSelectSlot={handleSelectSlot}
      />
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Agendar Entrevista</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Título"
            fullWidth
            value={title}
            onChange={(e) => setTitle(e.target.value)}
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

export default MyCalendar;
