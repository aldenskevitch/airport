package com.solvd.airport.service.impl;

import com.solvd.airport.domain.Passenger;
import com.solvd.airport.domain.exception.DeleteException;
import com.solvd.airport.domain.exception.InsertException;
import com.solvd.airport.domain.exception.ReadDatabaseException;
import com.solvd.airport.persistence.PassengerRepository;
import com.solvd.airport.persistence.impl.mybatis.PassengerMapperImpl;
import com.solvd.airport.service.PassengerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PassengerServiceImpl implements PassengerService {

    private static final Logger LOGGER = LogManager.getLogger();

    private final PassengerRepository passengerRepository;

    public PassengerServiceImpl() {
        this.passengerRepository = new PassengerMapperImpl();
//        this.passengerRepository = new PassengerRepositoryImpl();
    }

    @Override
    public Passenger create(Passenger passenger) {
        passenger.setId(null);
        try {
            passengerRepository.create(passenger);
        } catch (InsertException e) {
            LOGGER.debug(e.getMessage());
        }
        return passenger;
    }

    @Override
    public List<Passenger> createList(List<Passenger> passengerList) {
        passengerList.forEach(passenger -> {
            passenger.setId(null);
            try {
                passengerRepository.create(passenger);
            } catch (InsertException e) {
                LOGGER.debug(e.getMessage());
            }
        });
        return passengerList;
    }

    @Override
    public List<Passenger> deleteFromPassengersList(String name, String surname) {
        try {
            passengerRepository.deleteFromPassengersList(name, surname);
            return passengerRepository.getPassengerList();
        } catch (DeleteException | ReadDatabaseException e) {
            LOGGER.debug(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Passenger> getTickets() {
        try {
            return passengerRepository.getTickets();
        } catch (ReadDatabaseException e) {
            LOGGER.debug(e.getMessage());
        }
        return null;
    }
}
