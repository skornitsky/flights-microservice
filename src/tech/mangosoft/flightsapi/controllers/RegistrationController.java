package tech.mangosoft.flightsapi.controllers;

import com.google.common.base.Preconditions;
import tech.mangosoft.flightsapi.Application;
import tech.mangosoft.flightsapi.models.User;
import tech.mangosoft.flightsapi.models.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


@RestController
@RequestMapping("/user")
public class RegistrationController {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User findOne(@PathVariable("id") String id) {
        return userRepository.findById( id ).get();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String create(@RequestBody User resource) {
        Preconditions.checkNotNull(resource);
        return userRepository.save(resource).getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) String id, @RequestBody User resource) {
        Preconditions.checkNotNull(resource);
        if (userRepository.findById(resource.getId()).isPresent()) {
            userRepository.save(resource);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") String id) {
        userRepository.deleteById(id);
    }


    @ExceptionHandler
    void handleIllegalArgumentException(
            IllegalArgumentException e,
            HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.BAD_REQUEST.value());

    }
}
