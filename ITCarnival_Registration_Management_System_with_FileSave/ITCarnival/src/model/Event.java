package model;

/**
 * Abstraction: Event is an abstract class. It cannot be created with 'new Event(...)'.
 * It holds the common data; GamingEvent and ITEvent add their own details.
 */
public abstract class Event {

    // final constant: the currency label is fixed for the whole program and must never change
    public static final String CURRENCY = "Tk";

    // Encapsulation: private fields
    private int eventId;
    private String eventName;
    private String eventType;
    private String venue;
    private int maxParticipants;
    private double registrationFee;
    private int registeredCount; // how many seats are already taken

    public Event(int eventId, String eventName, String eventType,
                 String venue, int maxParticipants, double registrationFee) {
        this.eventId = eventId;
        this.eventType = eventType;
        // The setters contain validation, so reuse them
        setEventName(eventName);
        setVenue(venue);
        setMaxParticipants(maxParticipants);
        setRegistrationFee(registrationFee);
    }

    // Constructor Overloading: a free event (fee = 0) without a fee parameter
    public Event(int eventId, String eventName, String eventType,
                 String venue, int maxParticipants) {
        this(eventId, eventName, eventType, venue, maxParticipants, 0.0);
    }

    // Abstraction: abstract method has no body. Every child class MUST implement it.
    public abstract String getRules();

    // ---- Getters ----
    public int getEventId() { return eventId; }
    public String getEventName() { return eventName; }
    public String getEventType() { return eventType; }
    public String getVenue() { return venue; }
    public int getMaxParticipants() { return maxParticipants; }
    public double getRegistrationFee() { return registrationFee; }
    public int getRegisteredCount() { return registeredCount; }

    // ---- Setters (with simple validation) ----
    public void setEventName(String eventName) {
        if (eventName == null || eventName.trim().isEmpty()) {
            throw new IllegalArgumentException("Event name cannot be empty.");
        }
        this.eventName = eventName.trim();
    }

    public void setVenue(String venue) {
        if (venue == null || venue.trim().isEmpty()) {
            throw new IllegalArgumentException("Venue cannot be empty.");
        }
        this.venue = venue.trim();
    }

    public void setMaxParticipants(int maxParticipants) {
        if (maxParticipants < 1) {
            throw new IllegalArgumentException("Maximum participants must be at least 1.");
        }
        this.maxParticipants = maxParticipants;
    }

    public void setRegistrationFee(double registrationFee) {
        if (registrationFee < 0) {
            throw new IllegalArgumentException("Registration fee cannot be negative.");
        }
        this.registrationFee = registrationFee;
    }

    // ---- Seat handling ----
    public boolean isFull() {
        return registeredCount >= maxParticipants;
    }

    public void addRegisteredParticipant() {
        registeredCount++;
    }

    public void removeRegisteredParticipant() {
        if (registeredCount > 0) {
            registeredCount--;
        }
    }

    // Shows "Free" for a zero fee, otherwise the amount in taka
    public String getFeeText() {
        if (registrationFee == 0) {
            return "Free";
        }
        return CURRENCY + " " + String.format("%.0f", registrationFee);
    }

    // Common display code. Child classes override it and add their own lines.
    public void displayInfo() {
        System.out.println("[" + eventId + "] " + eventName + " (" + eventType + ")");
        System.out.println("   Venue: " + venue
                + " | Seats: " + registeredCount + "/" + maxParticipants
                + " | Fee: " + getFeeText());
    }

    // Method Overriding: replaces Object.toString()
    @Override
    public String toString() {
        return eventName + " (" + eventType + ", ID " + eventId + ")";
    }
}
