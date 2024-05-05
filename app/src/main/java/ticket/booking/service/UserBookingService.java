package ticket.booking.service;

import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserBookingService {
    private User user;
    private List<User> userList;
    private static final String USERS_PATH = "/Users/ashrrith/Desktop/Learn/GradleProjects/IRCTC/app/src/main/java/ticket/booking/localDb/users.json";
    private ObjectMapper objectMapper = new ObjectMapper();
    public UserBookingService(User user) throws IOException {
        this.user = user;
        loadUsers();
    }

    public UserBookingService() throws IOException {
        loadUsers();
    }

    public void loadUsers() throws IOException {
        userList = objectMapper.readValue(new File(USERS_PATH), new TypeReference<List<User>>() {});
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }

    public void saveUserListToFile() throws IOException{
        File usersFile = new File(USERS_PATH);
        objectMapper.writeValue(usersFile, userList);
    }

    public void fetchBooking(){
        user.printTickets();
    }

    public Boolean cancelBooking(String ticketId){
        try {
            Optional<Ticket> ticketCancel = user.getTicketsBooked().stream()
                    .filter(ticket -> ticket.getTicketId().equals(ticketId))
                    .findFirst();
            if (ticketCancel.isPresent()) {
                user.getTicketsBooked().remove(ticketCancel.get());
                saveUserListToFile();
                return Boolean.TRUE;
            } else{
                return Boolean.FALSE;
            }
        } catch (IOException ex){
            return Boolean.FALSE;
        }
    }

    public List<Train> getTrains(String source, String dest){
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source,dest);
        } catch (IOException e){
            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(Train train){
        return train.getSeats();
    }

    public Boolean bookTrainSeat(Train train, int row, int seat){
        try{
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = train.getSeats();
            if(row>=0  && row< seats.size() && seat >= 0 && seat < seats.get(row).size()){
                if(seats.get(row).get(seat) == 0){
                    seats.get(row).set(seat, 1);
                    train.setSeats(seats);
                    trainService.addTrain(train);
                    return true; // Booking Successful
                } else {
                    return false; // Seat already booked
                }
            } else{
                return false; // Invalid row or seat index
            }
        } catch (IOException e){
            return Boolean.FALSE;
        }
    }

}
