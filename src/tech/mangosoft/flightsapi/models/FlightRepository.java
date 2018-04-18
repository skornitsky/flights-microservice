package tech.mangosoft.flightsapi.models;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface FlightRepository extends MongoRepository<Flight, String> {

    public List<Flight> findByAirline(String airline);
    public List<Flight> findByPrice(String price);
}
