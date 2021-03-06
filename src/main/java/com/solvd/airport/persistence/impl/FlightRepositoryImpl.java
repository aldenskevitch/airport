package com.solvd.airport.persistence.impl;

import com.solvd.airport.domain.Aircraft;
import com.solvd.airport.domain.Direction;
import com.solvd.airport.domain.Flight;
import com.solvd.airport.domain.exception.InsertException;
import com.solvd.airport.persistence.ConnectionPool;
import com.solvd.airport.persistence.FlightRepository;

import java.sql.*;

public class FlightRepositoryImpl implements FlightRepository {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String FLIGHT_ENTRY_FIELD = "insert into Flights(aircraft_id, direction_from, direction_to, employee_id, number) values (?,?,?,?,?)";

    @Override
    public void create(Flight flight, Long employeeId) throws InsertException {
        Connection connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(FLIGHT_ENTRY_FIELD, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, flight.getAircraft().getId());
            preparedStatement.setLong(2, flight.getFrom().getId());
            preparedStatement.setLong(3, flight.getTo().getId());
            preparedStatement.setLong(4, employeeId);
            preparedStatement.setInt(5, flight.getNumber());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                flight.setId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new InsertException("Employee entry failed", e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
