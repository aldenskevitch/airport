package com.solvd.airport.service;

import com.solvd.airport.domain.Position;

import java.util.List;

public interface PositionService {

    Position insert(Position position);

    List<Position> insertList(List<Position> positionList);

}
