package service;

import exception.EventNotFoundException;
import java.util.ArrayList;
import model.Event;

/**
 * Handles all event operations: add, view, find, update, remove.
 */
public class EventService {

    // final constant: the maximum number of events is a fixed rule of the system
    public static final int MAX_EVENTS = 20;

    // Polymorphism: the list type is the parent class Event,
    // but it holds GamingEvent and ITEvent objects.
    private final ArrayList<Event> events = new ArrayList<>();
    private int nextEventId = 1;

    public int generateEventId() {
        return nextEventId++;
    }

    public void addEvent(Event event) {
        if (events.size() >= MAX_EVENTS) {
            throw new IllegalStateException("Cannot add more than " + MAX_EVENTS + " events.");
        }
        events.add(event);
    }

    public ArrayList<Event> getAllEvents() {
        return events;
    }

    public Event findEventById(int eventId) throws EventNotFoundException {
        for (Event event : events) {
            if (event.getEventId() == eventId) {
                return event;
            }
        }
        throw new EventNotFoundException(eventId);
    }

    public void updateEvent(int eventId, String name, String venue,
                            int maxParticipants, double fee) throws EventNotFoundException {
        Event event = findEventById(eventId);
        if (maxParticipants < event.getRegisteredCount()) {
            throw new IllegalArgumentException("Maximum participants cannot be less than the "
                    + event.getRegisteredCount() + " already registered.");
        }
        event.setEventName(name);
        event.setVenue(venue);
        event.setMaxParticipants(maxParticipants);
        event.setRegistrationFee(fee);
    }

    // The admin can change the fee of an event at any time.
    // Registrations made earlier keep the fee they were made with.
    public void changeRegistrationFee(int eventId, double newFee) throws EventNotFoundException {
        Event event = findEventById(eventId);
        event.setRegistrationFee(newFee); // the setter rejects negative fees
    }

    public void removeEvent(int eventId) throws EventNotFoundException {
        Event event = findEventById(eventId);
        events.remove(event);
    }
}
