package ticket.booking.entities;

import java.util.List;
import java.util.Optional;

public class User {
    private String name;
    private String hashPassword;
    private String password;
    private List<Ticket> ticketsBooked;
    private String userId;

    public User(String name, String hashPassword, String password, List<Ticket> ticketsBooked, String userId) {
        this.name = name;
        this.hashPassword = hashPassword;
        this.password = password;
        this.ticketsBooked = ticketsBooked;
        this.userId = userId;
    }

    public User(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;
    }

    public void setTicketsBooked(List<Ticket> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void printTickets() {
        for(int i = 0; i<ticketsBooked.size(); i++){
            System.out.println(ticketsBooked.get(i).getTicketInfo());
        }
    }
}
