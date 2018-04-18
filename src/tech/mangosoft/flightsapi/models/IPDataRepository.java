package tech.mangosoft.flightsapi.models;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface IPDataRepository extends MongoRepository<IPData, String> {

}
