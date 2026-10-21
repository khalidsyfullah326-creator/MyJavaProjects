package main;

import exception.DuplicateRegistrationException;
import exception.EventNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import model.Admin;
import model.Event;
import model.GamingEvent;
import model.ITEvent;
import model.Participant;
import model.Registration;
import service.EventService;
import service.FileStorageService;
import service.RegistrationService;
import service.UserService;

/**
 * Entry point of the IT Carnival Registration Management System.
 * No login is used: the user simply picks a role (Admin or Participant).
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();
    private static final EventService eventService = new EventService();
    private static final RegistrationService registrationService =
            new RegistrationService(userService, eventService);
    private static final FileStorageService fileStorageService = new FileStorageService();

    // Sample Admin object (no authentication needed for this demo)
    private static final Admin admin =
            new Admin(0, "Carnival Admin", "admin@carnival.com", "01700000000");

    public static void main(String[] args) {
        loadDefaultEvents();
        // Load previously saved participants/registrations from the text file (if any)
        fileStorageService.loadData(userService, eventService, registrationService);
        System.out.println("=== Welcome to the University IT Carnival Registration System ===");
        try {
            runRoleMenu();
        } catch (NoSuchElementException e) {
            // Happens only if the input stream ends (e.g. when input is piped from a file)
            System.out.println("\nInput ended. Exiting program.");
        } finally {
            // Runs no matter which menu option ends the program, so data is always saved
            fileStorageService.saveData(userService, registrationService);
        }
    }

    // ------------------------------------------------------------------
    // Default events (there is NO pre-made participant list: participants sign up themselves)
    // ------------------------------------------------------------------
    private static void loadDefaultEvents() {
        // Polymorphism: parent reference (Event) refers to child objects (ITEvent / GamingEvent)

        // ----- IT events (listed first): ITEvent(id, name, venue, seats, fee, teamSize) -----
        Event hackathon = new ITEvent(eventService.generateEventId(), "Hackathon",
                "Computer Lab 1", 30, 500.0, 3);
        Event icpc = new ITEvent(eventService.generateEventId(), "Programming Contest (ICPC)",
                "Computer Lab 2", 30, 500.0, 3);
        // Constructor overloading: this ITEvent constructor has no team size (individual)
        Event showcase = new ITEvent(eventService.generateEventId(), "Project Showcasing",
                "Main Hall", 40, 0.0);
        Event itQuiz = new ITEvent(eventService.generateEventId(), "IT Quiz",
                "Seminar Room", 50, 50.0, 1);

        // ----- Gaming events: GamingEvent(id, name, venue, seats, fee, platform) -----
        Event pubg = new GamingEvent(eventService.generateEventId(), "PUBG",
                "Gaming Zone", 20, 100.0, "Mobile");
        Event freeFire = new GamingEvent(eventService.generateEventId(), "Free Fire",
                "Gaming Zone", 20, 100.0, "Mobile");
        Event eFootball = new GamingEvent(eventService.generateEventId(), "eFootball",
                "Gaming Zone", 16, 100.0, "Mobile");
        Event chess = new GamingEvent(eventService.generateEventId(), "Chess",
                "Indoor Games Room", 16, 50.0, "Chess Board");
        Event ludo = new GamingEvent(eventService.generateEventId(), "Ludo",
                "Indoor Games Room", 16, 50.0, "Ludo Board");
        Event rubiks = new GamingEvent(eventService.generateEventId(), "Rubik's Cube",
                "Indoor Games Room", 20, 50.0, "Rubik's Cube");

        eventService.addEvent(hackathon);
        eventService.addEvent(icpc);
        eventService.addEvent(showcase);
        eventService.addEvent(itQuiz);
        eventService.addEvent(pubg);
        eventService.addEvent(freeFire);
        eventService.addEvent(eFootball);
        eventService.addEvent(chess);
        eventService.addEvent(ludo);
        eventService.addEvent(rubiks);

        userService.addUser(admin); // only the admin exists at the start
    }

    // ------------------------------------------------------------------
    // Role selection
    // ------------------------------------------------------------------
    private static void runRoleMenu() {
        while (true) {
            System.out.println("\n===== SELECT ROLE =====");
            System.out.println("1. Admin");
            System.out.println("2. Participant");
            System.out.println("3. Exit");
            int choice = readInt("Enter choice: ");

            if (choice == 1) {
                runAdminMenu();
            } else if (choice == 2) {
                runParticipantEntryMenu();
            } else if (choice == 3) {
                System.out.println("Goodbye!");
                return;
            } else {
                System.out.println("Error: Invalid menu choice. Please enter 1, 2 or 3.");
            }
        }
    }

    private static void runParticipantEntryMenu() {
        while (true) {
            System.out.println("\n===== PARTICIPANT =====");
            System.out.println("1. Sign up (new participant)");
            System.out.println("2. Log in with my ID");
            System.out.println("3. Back");
            int choice = readInt("Enter choice: ");

            try {
                if (choice == 1) {
                    Participant participant = signUpParticipant();
                    System.out.println("Sign-up successful! Welcome, " + participant.getName() + ".");
                    runParticipantMenu(participant);
                } else if (choice == 2) {
                    int id = readInt("Enter your participant ID: ");
                    runParticipantMenu(userService.findParticipantById(id));
                } else if (choice == 3) {
                    return;
                } else {
                    throw new IllegalArgumentException("Invalid menu choice. Please enter 1, 2 or 3.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Asks for ID, name, phone and email, then creates the participant.
    // Used by participant sign-up and by the admin's "Add Participant".
    private static Participant signUpParticipant() {
        int id = readInt("Participant ID: ");
        String name = readText("Name: ");
        String phone = readText("Phone: ");
        String email = readText("Email: ");
        return userService.registerParticipant(id, name, phone, email);
    }

    // ------------------------------------------------------------------
    // Admin menu
    // ------------------------------------------------------------------
    private static void runAdminMenu() {
        System.out.println("\nLogged in as: " + admin);
        while (true) {
            System.out.println("\n===== ADMIN MENU =====");
            System.out.println("1. Add Event");
            System.out.println("2. View Events");
            System.out.println("3. Update Event");
            System.out.println("4. Change Registration Fee");
            System.out.println("5. Remove Event");
            System.out.println("6. Add Participant");
            System.out.println("7. View Participants");
            System.out.println("8. Delete Participant");
            System.out.println("9. View Registrations");
            System.out.println("10. Exit");
            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> addEvent();
                    case 2 -> viewEvents();
                    case 3 -> updateEvent();
                    case 4 -> changeRegistrationFee();
                    case 5 -> removeEvent();
                    case 6 -> addParticipant();
                    case 7 -> viewParticipants();
                    case 8 -> deleteParticipant();
                    case 9 -> viewAllRegistrations();
                    case 10 -> { return; }
                    default -> throw new IllegalArgumentException("Invalid menu choice. Please enter 1-10.");
                }
            } catch (EventNotFoundException | IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addEvent() {
        System.out.println("Event type: 1. Gaming Event   2. IT Event");
        int type = readInt("Choose type: ");
        if (type != 1 && type != 2) {
            throw new IllegalArgumentException("Invalid event type. Please choose 1 or 2.");
        }

        String name = readText("Event name: ");
        String venue = readText("Venue: ");
        int maxParticipants = readInt("Maximum participants: ");
        double fee = readDouble("Registration fee (Tk, 0 = free): ");
        int id = eventService.generateEventId();

        Event event; // Polymorphism: one parent variable, two possible child objects
        if (type == 1) {
            String platform = readText("Platform (e.g. PC, Mobile): ");
            event = new GamingEvent(id, name, venue, maxParticipants, fee, platform);
        } else {
            int teamSize = readInt("Team size: ");
            event = new ITEvent(id, name, venue, maxParticipants, fee, teamSize);
        }
        eventService.addEvent(event);
        System.out.println("Event added: " + event);
    }

    private static void viewEvents() {
        ArrayList<Event> events = eventService.getAllEvents();
        if (events.isEmpty()) {
            System.out.println("No events available.");
            return;
        }
        System.out.println("\n--- All Events ---");
        for (Event event : events) {
            // Polymorphism: Java calls GamingEvent.displayInfo() or ITEvent.displayInfo()
            // depending on the real object type at runtime.
            event.displayInfo();
        }
    }

    private static void updateEvent() throws EventNotFoundException {
        int id = readInt("Enter event ID to update: ");
        Event event = eventService.findEventById(id);
        System.out.println("Current: " + event);

        String name = readText("New name: ");
        String venue = readText("New venue: ");
        int maxParticipants = readInt("New maximum participants: ");
        double fee = readDouble("New registration fee (Tk): ");
        eventService.updateEvent(id, name, venue, maxParticipants, fee);
        System.out.println("Event updated successfully.");
    }

    private static void removeEvent() throws EventNotFoundException {
        int id = readInt("Enter event ID to remove: ");
        Event event = eventService.findEventById(id);
        registrationService.removeRegistrationsForEvent(event); // remove its registrations too
        eventService.removeEvent(id);
        System.out.println("Event removed: " + event.getEventName());
    }

    private static void changeRegistrationFee() throws EventNotFoundException {
        int id = readInt("Enter event ID: ");
        Event event = eventService.findEventById(id);
        System.out.println("Current fee of " + event.getEventName() + ": " + event.getFeeText());
        double newFee = readDouble("New registration fee (Tk, 0 = free): ");
        eventService.changeRegistrationFee(id, newFee);
        System.out.println("Fee updated: " + event.getEventName() + " is now " + event.getFeeText()
                + ". (People who already registered keep their old fee.)");
    }

    private static void addParticipant() {
        Participant participant = signUpParticipant();
        System.out.println("Participant added: " + participant);
    }

    private static void viewParticipants() {
        ArrayList<Participant> participants = userService.getAllParticipants();
        if (participants.isEmpty()) {
            System.out.println("No participants have signed up yet.");
            return;
        }
        System.out.println("\n--- All Participants ---");
        for (Participant participant : participants) {
            participant.displayInfo();
        }
    }

    private static void deleteParticipant() {
        int id = readInt("Enter participant ID to delete: ");
        Participant participant = userService.findParticipantById(id);
        int removed = registrationService.removeRegistrationsForParticipant(participant);
        userService.removeParticipant(participant);
        System.out.println("Participant deleted: " + participant.getName()
                + " (" + removed + " registration(s) removed).");
    }

    private static void viewAllRegistrations() {
        ArrayList<Registration> registrations = registrationService.getAllRegistrations();
        if (registrations.isEmpty()) {
            System.out.println("No registrations yet.");
            return;
        }
        System.out.println("\n--- All Registrations ---");
        for (Registration registration : registrations) {
            System.out.println(registration);
        }
    }

    // ------------------------------------------------------------------
    // Participant menu
    // ------------------------------------------------------------------
    private static void runParticipantMenu(Participant participant) {
        System.out.println("\nLogged in as: " + participant);
        while (true) {
            System.out.println("\n===== PARTICIPANT MENU =====");
            System.out.println("1. View Events");
            System.out.println("2. Register for Event");
            System.out.println("3. View My Registrations");
            System.out.println("4. Cancel Registration");
            System.out.println("5. Exit");
            int choice = readInt("Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> viewEvents();
                    case 2 -> registerForEvent(participant);
                    case 3 -> viewMyRegistrations(participant);
                    case 4 -> cancelRegistration(participant);
                    case 5 -> { return; }
                    default -> throw new IllegalArgumentException("Invalid menu choice. Please enter 1-5.");
                }
            } catch (EventNotFoundException | DuplicateRegistrationException
                     | IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void registerForEvent(Participant participant)
            throws EventNotFoundException, DuplicateRegistrationException {
        int eventId = readInt("Enter event ID to register: ");
        // Uses the overloaded register(int, int) method
        Registration registration = registrationService.register(participant.getUserId(), eventId);
        System.out.println("Success! " + registration);
    }

    private static void viewMyRegistrations(Participant participant) {
        ArrayList<Registration> mine = registrationService.getRegistrationsByParticipant(participant);
        if (mine.isEmpty()) {
            System.out.println("You have no registrations.");
            return;
        }
        System.out.println("\n--- My Registrations ---");
        for (Registration registration : mine) {
            System.out.println(registration);
        }
    }

    private static void cancelRegistration(Participant participant) {
        int registrationId = readInt("Enter registration ID to cancel: ");
        registrationService.cancelRegistration(participant, registrationId);
        System.out.println("Registration cancelled.");
    }

    // ------------------------------------------------------------------
    // Input helpers (exception handling for invalid input)
    // ------------------------------------------------------------------
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Error: Input cannot be empty.");
        }
    }
}
