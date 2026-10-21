package service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import exception.EventNotFoundException;
import model.Event;
import model.Participant;
import model.Registration;

/**
 * The entire persistence layer: no database, just one plain text file.
 * Every participant and registration is written as one readable line, and
 * read back the same way the next time the program starts.
 */
public class FileStorageService {

    private static final String FILE_NAME = "itcarnival_data.txt";

    // Writes every participant and every registration as plain text, one per line.
    public void saveData(UserService userService, RegistrationService registrationService) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Participant p : userService.getAllParticipants()) {
                writer.println("PARTICIPANT|" + p.getUserId() + "|" + p.getName()
                        + "|" + p.getEmail() + "|" + p.getPhone());
            }
            for (Registration r : registrationService.getAllRegistrations()) {
                writer.println("REGISTRATION|" + r.getRegistrationId() + "|"
                        + r.getParticipant().getUserId() + "|" + r.getEvent().getEventId()
                        + "|" + r.getFeeAtRegistration());
            }
            System.out.println("[Data saved to " + FILE_NAME + "]");
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }

    // Reads the text file (if it exists yet) and rebuilds participants and registrations.
    public void loadData(UserService userService, EventService eventService,
                          RegistrationService registrationService) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; // first run: no save file yet, nothing to load
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split("\\|");
                if (parts[0].equals("PARTICIPANT")) {
                    int userId = Integer.parseInt(parts[1]);
                    String name = parts[2];
                    String email = parts[3];
                    String phone = parts[4];
                    userService.addUser(new Participant(userId, name, email, phone));
                } else if (parts[0].equals("REGISTRATION")) {
                    restoreRegistration(parts, userService, eventService, registrationService);
                }
            }
            System.out.println("[Data loaded from " + FILE_NAME + "]");
        } catch (IOException e) {
            System.out.println("Could not load data: " + e.getMessage());
        }
    }

    private void restoreRegistration(String[] parts, UserService userService,
                                      EventService eventService,
                                      RegistrationService registrationService) {
        int regId = Integer.parseInt(parts[1]);
        int participantId = Integer.parseInt(parts[2]);
        int eventId = Integer.parseInt(parts[3]);
        double fee = Double.parseDouble(parts[4]);
        try {
            Participant participant = userService.findParticipantById(participantId);
            Event event = eventService.findEventById(eventId);
            registrationService.restoreRegistration(
                    new Registration(regId, participant, event, fee));
        } catch (IllegalArgumentException | EventNotFoundException e) {
            System.out.println("Skipped a saved registration: " + e.getMessage());
        }
    }
}
