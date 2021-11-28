package com.solvd.airport.service.impl;

import com.solvd.airport.domain.Airport;
import com.solvd.airport.domain.Direction;
import com.solvd.airport.domain.exception.InsertException;
import com.solvd.airport.persistence.DirectionRepository;
import com.solvd.airport.persistence.impl.DirectionRepositoryImpl;
import com.solvd.airport.service.AirportService;
import com.solvd.airport.service.DirectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DirectionServiceImpl implements DirectionService {

    private static final Logger LOGGER = LogManager.getLogger();

    private final DirectionRepository directionRepository;
    private final AirportService airportService;

    public DirectionServiceImpl() {
        this.directionRepository = new DirectionRepositoryImpl();
        this.airportService = new AirportServiceImpl();
    }

    @Override
    public Direction insert(Direction direction, Long airportId) {
        direction.setId(null);
        if (direction.getAirport() != null) {
            Airport airport = airportService.insert(direction.getAirport(), direction.getAirport().getAddress().getId());
            direction.setAirport(airport);
        }
        try {
            directionRepository.insert(direction, airportId);
        } catch (InsertException e) {
            e.printStackTrace();
        }
        return direction;
    }
}
