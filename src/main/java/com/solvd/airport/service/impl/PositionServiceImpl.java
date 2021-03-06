package com.solvd.airport.service.impl;

import com.solvd.airport.domain.Position;
import com.solvd.airport.domain.exception.InsertException;
import com.solvd.airport.persistence.PositionRepository;
import com.solvd.airport.persistence.impl.mybatis.PositionMapperImpl;
import com.solvd.airport.service.PositionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PositionServiceImpl implements PositionService {

    private static final Logger LOGGER = LogManager.getLogger();

    private final PositionRepository positionRepository;

    public PositionServiceImpl() {
        this.positionRepository = new PositionMapperImpl();
//        this.positionRepository = new PositionRepositoryImpl();
    }

    @Override
    public Position create(Position position) {
        position.setId(null);
        try {
            positionRepository.create(position);
        } catch (InsertException e) {
            LOGGER.debug(e.getMessage());
        }
        return position;
    }

    @Override
    public List<Position> createList(List<Position> positionList) {
        positionList.forEach(position -> {
            position.setId(null);
            try {
                positionRepository.create(position);
            } catch (InsertException e) {
                LOGGER.debug(e.getMessage());
            }
        });
        return positionList;
    }
}
