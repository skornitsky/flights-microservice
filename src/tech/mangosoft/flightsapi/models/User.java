package tech.mangosoft.flightsapi.models;



public class User {

    private String id;
    private String email;
    private String name;

    // Public methods

    public User() { }

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
