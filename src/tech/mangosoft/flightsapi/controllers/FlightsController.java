package tech.mangosoft.flightsapi.controllers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;
import tech.mangosoft.flightsapi.Application;
import tech.mangosoft.flightsapi.models.Flight;
import tech.mangosoft.flightsapi.models.FlightRepository;
import tech.mangosoft.flightsapi.models.IPData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/flight")

public class FlightsController {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private FlightRepository flightRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Iterable<Flight> findAll() {
        return flightRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Flight findOne(@PathVariable("id") String id) {
        return flightRepository.findById( id ).get();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String create(@RequestBody Flight resource) {
        Preconditions.checkNotNull(resource);
        return flightRepository.save(resource).getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) String id, @RequestBody Flight resource) {
        Preconditions.checkNotNull(resource);
        if (flightRepository.findById(resource.getId()).isPresent()) {
            flightRepository.save(resource);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") String id) {
        flightRepository.deleteById(id);
    }


    @RequestMapping(method = RequestMethod.POST, path = "multiple")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public List<String> multiple(@RequestBody String resource) throws IOException {
        Preconditions.checkNotNull(resource);

        ObjectMapper jsonObjectMapper = new ObjectMapper();
        jsonObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        JsonNode productNode = jsonObjectMapper.readTree(resource);

        IPData ipData = new IPData();
        List<String> flights = new LinkedList<>();


            for (Iterator it = productNode.iterator();
                 it.hasNext();) {
                ObjectNode oNode = (ObjectNode) it.next();
                if (oNode.has("ip")) {
                    //let's cast it to ipData
                    ipData = jsonObjectMapper.treeToValue(oNode, IPData.class);
                }
                if (oNode.has("line")) {
                    //let's cast it to flight
                    Flight f = jsonObjectMapper.treeToValue(oNode, Flight.class);
                    f.setIpdata(ipData);
                    flights.add( flightRepository.save(f).getId() );
                }
                log.debug(oNode.asText());
            }
        log.debug("Flights were added from " + ipData.getCountryName() + "[" + ipData.getCountryCode() + "]");
        flights.forEach(log::debug);
        log.debug("\n Total: " + flights.size());

        return flights;
    }

    @ExceptionHandler
    void handleIllegalArgumentException(
            IllegalArgumentException e,
            HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.BAD_REQUEST.value());

    }

}
