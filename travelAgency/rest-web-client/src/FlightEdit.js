import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

const FlightEdit = () => {
    const initialFormState = {
        destination: '',
        date: '',
        time: '',
        airport: '',
        noSeats: 0
    };
    const [flight, setFlight] = useState(initialFormState);
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect(() => {
        if (id !== 'new') {
            fetch(`/app/flights/${id}`)
                .then(response => response.json())
                .then(data => setFlight(data));
        }
    }, [id, setFlight]);

    const handleChange = (event) => {
        const { name, value } = event.target;
        setFlight({ ...flight, [name]: value });
    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Validate the number of seats
        if (flight.noSeats <= 0) {
            alert('Number of seats must be greater than 0');
            return;
        }

        // Validate that the flight date is after the current date - 1
        const currentDate = new Date();
        const selectedDate = new Date(flight.date);
        const oneDayBack = new Date(currentDate);
        oneDayBack.setDate(oneDayBack.getDate() - 1); // Get the date for tomorrow
        if (selectedDate.getDate() < oneDayBack.setDate()) {
            alert('Flight date must not be before the current date - 1');
            return;
        }
        // Append ':00' to the time if it doesn't already contain seconds
        const formattedTime = flight.time.split(':').length === 3 ? flight.time : `${flight.time}:00`;

        // Convert flight data to strings
        const flightToString = {
            ...flight,
            destination: String(flight.destination),
            date: String(flight.date),
            time: formattedTime, // Use the formatted time
            airport: String(flight.airport),
            noSeats: flight.noSeats
        };

        await fetch(`/app/flights${flight.id ? `/${flight.id}` : ''}`, {
            method: (flight.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(flightToString) // Send the stringified flight data
        });
        setFlight(initialFormState);
        navigate('/app/flights');
    };

    const title = <h2>{flight.id ? 'Edit Flight' : 'Add Flight'}</h2>;

    return (
        <div>
            <AppNavbar />
            <Container>
                {title}
                <Form onSubmit={handleSubmit}>
                    <FormGroup>
                        <Label for="destination">Destination</Label>
                        <Input type="text" name="destination" id="destination" value={flight.destination || ''}
                               onChange={handleChange} autoComplete="destination"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="date">Date</Label>
                        <Input type="date" name="date" id="date" value={flight.date || ''}
                               onChange={handleChange} autoComplete="date"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="time">Time</Label>
                        <Input type="time" name="time" id="time" value={flight.time || ''}
                               onChange={handleChange} autoComplete="time"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="airport">Airport</Label>
                        <Input type="text" name="airport" id="airport" value={flight.airport || ''}
                               onChange={handleChange} autoComplete="airport"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="noSeats">Number of Seats</Label>
                        <Input type="number" name="noSeats" id="noSeats" value={flight.noSeats || ''}
                               onChange={handleChange} autoComplete="noSeats"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/app/flights">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    );
};

export default FlightEdit;
