package tech.mangosoft.flightsapi.models;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface UserRepository extends MongoRepository<User, String> {

    public List<User> findByName(String name);
    public List<User> findByEmail(String email);
}
