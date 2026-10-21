package service;

import exception.DuplicateRegistrationException;
import exception.EventNotFoundException;
import java.util.ArrayList;
import model.Event;
import model.Participant;
import model.Registration;

/**
 * Handles registering and cancelling. All registrations are kept in one ArrayList.
 */
public class RegistrationService {

    private final ArrayList<Registration> registrations = new ArrayList<>();
    private final UserService userService;
    private final EventService eventService;
    private int nextRegistrationId = 1;

    public RegistrationService(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    // Method Overloading (version 1): register using objects
    public Registration register(Participant participant, Event event)
            throws DuplicateRegistrationException {
        if (isAlreadyRegistered(participant, event)) {
            throw new DuplicateRegistrationException(participant.getName()
                    + " is already registered for " + event.getEventName() + ".");
        }
        if (event.isFull()) {
            throw new IllegalStateException(event.getEventName() + " is full.");
        }
        Registration registration = new Registration(nextRegistrationId++, participant, event);
        registrations.add(registration);
        event.addRegisteredParticipant();
        return registration;
    }

    // Method Overloading (version 2): same method name, but takes IDs (different parameters)
    public Registration register(int participantId, int eventId)
            throws EventNotFoundException, DuplicateRegistrationException {
        Participant participant = userService.findParticipantById(participantId);
        Event event = eventService.findEventById(eventId);
        return register(participant, event); // reuses version 1
    }

    public void cancelRegistration(Participant participant, int registrationId) {
        for (Registration registration : registrations) {
            if (registration.getRegistrationId() == registrationId
                    && registration.getParticipant() == participant) {
                registrations.remove(registration);
                registration.getEvent().removeRegisteredParticipant();
                return;
            }
        }
        throw new IllegalStateException("You have no registration with ID " + registrationId + ".");
    }

    public ArrayList<Registration> getAllRegistrations() {
        return registrations;
    }

    // Used only by FileStorageService when loading saved data at startup:
    // adds a registration that already exists (from the file) without re-running
    // the duplicate/seat checks, and keeps the seat count correct.
    public void restoreRegistration(Registration registration) {
        registrations.add(registration);
        registration.getEvent().addRegisteredParticipant();
        if (registration.getRegistrationId() >= nextRegistrationId) {
            nextRegistrationId = registration.getRegistrationId() + 1;
        }
    }

    public ArrayList<Registration> getRegistrationsByParticipant(Participant participant) {
        ArrayList<Registration> result = new ArrayList<>();
        for (Registration registration : registrations) {
            if (registration.getParticipant() == participant) {
                result.add(registration);
            }
        }
        return result;
    }

    // Called when an admin removes an event, so no registration points to a deleted event
    public void removeRegistrationsForEvent(Event event) {
        registrations.removeIf(registration -> registration.getEvent() == event);
    }

    // Called when an admin deletes a participant: removes their registrations and frees the seats.
    // Returns how many registrations were removed.
    public int removeRegistrationsForParticipant(Participant participant) {
        int removed = 0;
        for (int i = registrations.size() - 1; i >= 0; i--) {
            Registration registration = registrations.get(i);
            if (registration.getParticipant() == participant) {
                registration.getEvent().removeRegisteredParticipant();
                registrations.remove(i);
                removed++;
            }
        }
        return removed;
    }

    private boolean isAlreadyRegistered(Participant participant, Event event) {
        for (Registration registration : registrations) {
            if (registration.getParticipant() == participant
                    && registration.getEvent() == event) {
                return true;
            }
        }
        return false;
    }
}
