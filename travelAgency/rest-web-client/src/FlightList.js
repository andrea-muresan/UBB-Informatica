import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

const FlightList = () => {

    const [flights, setFlights] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);

        fetch('/app/flights')
            .then(response => response.json())
            .then(data => {
                console.log('Flight data:', data);  // Log the data for debugging
                setFlights(Array.isArray(data) ? data : []);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching flights:', error);
                setLoading(false);
            });
    }, []);

    const remove = async (id) => {
        await fetch(`/app/flights/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedFlights = [...flights].filter(i => i.id !== id);
            setFlights(updatedFlights);
        });
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    if (!Array.isArray(flights)) {
        return <p>Data format error</p>;
    }

    const flightList = flights.map(flight => (
        <tr key={flight.id}>
            <td>{flight.destination}</td>
            <td>{flight.date}</td>
            <td>{flight.time}</td>
            <td>{flight.airport}</td>
            <td>{flight.noSeats}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={`/app/flights/${flight.id}`}>Edit</Button>
                    <Button size="sm" color="danger" onClick={() => remove(flight.id)}>Delete</Button>
                </ButtonGroup>
            </td>
        </tr>
    ));

    return (
        <div>
            <AppNavbar />
            <Container fluid>
                <div className="float-end">
                    <Button color="success" tag={Link} to="/app/flights/new">Add Flight</Button>
                </div>
                <h3>Flight List</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th>Destination</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Airport</th>
                        <th>No. of Seats</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {flightList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
};

export default FlightList;
