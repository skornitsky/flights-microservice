package tech.mangosoft.flightsapi.models;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightDetailsRepository extends MongoRepository<FlightDetails, String> {
}
