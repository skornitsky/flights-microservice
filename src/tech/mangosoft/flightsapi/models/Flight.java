package tech.mangosoft.flightsapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Flight {

    private String id;

    @JsonProperty("line")
    private String airline;

    private String price;

    private IPData ipdata;

    private FlightDetails origin;

    private FlightDetails returning;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public IPData getIpdata() {
        return ipdata;
    }

    public void setIpdata(IPData ipdata) {
        this.ipdata = ipdata;
    }

    public FlightDetails getOrigin() {
        return origin;
    }

    public void setOrigin(FlightDetails origin) {
        this.origin = origin;
    }

    public FlightDetails getReturning() {
        return returning;
    }

    public void setReturning(FlightDetails returning) {
        this.returning = returning;
    }
}
