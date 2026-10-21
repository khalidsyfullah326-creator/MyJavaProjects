package service;

import java.util.ArrayList;
import model.Participant;
import model.User;

/**
 * Stores and finds users. Data lives only in memory (ArrayList).
 */
public class UserService {

    // final reference: the list object is never replaced, only its contents change
    private final ArrayList<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    /**
     * Signs up a new participant after validating every field.
     * Used by both the participant sign-up and the admin "Add Participant" option.
     */
    public Participant registerParticipant(int userId, String name, String phone, String email) {
        if (userId < 1) {
            throw new IllegalArgumentException("ID must be a positive number.");
        }
        if (isIdTaken(userId)) {
            throw new IllegalArgumentException("ID " + userId + " is already in use.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        if (phone == null || !phone.trim().matches("\\+?[0-9]{10,14}")) {
            throw new IllegalArgumentException("Phone must be 10 to 14 digits (a leading + is allowed).");
        }
        if (email == null || !email.trim().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
            throw new IllegalArgumentException("Email is not valid (example: name@mail.com).");
        }
        Participant participant = new Participant(userId, name.trim(), email.trim(), phone.trim());
        users.add(participant);
        return participant;
    }

    public void removeParticipant(Participant participant) {
        users.remove(participant);
    }

    // Returns only the Participants from the list of Users
    public ArrayList<Participant> getAllParticipants() {
        ArrayList<Participant> participants = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Participant) {
                participants.add((Participant) user);
            }
        }
        return participants;
    }

    public Participant findParticipantById(int userId) {
        for (Participant participant : getAllParticipants()) {
            if (participant.getUserId() == userId) {
                return participant;
            }
        }
        throw new IllegalArgumentException("Participant with ID " + userId + " was not found.");
    }

    private boolean isIdTaken(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return true;
            }
        }
        return false;
    }
}
