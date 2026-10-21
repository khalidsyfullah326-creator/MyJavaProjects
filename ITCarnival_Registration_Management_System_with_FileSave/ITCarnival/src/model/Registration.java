package model;

/**
 * Represents one participant registering for one event.
 */
public class Registration {

    private final int registrationId; // final: an ID must never change after it is created
    private final Participant participant;
    private final Event event; // parent type: can hold a GamingEvent or an ITEvent (polymorphism)
    // final: the fee is fixed at the moment of registration, so a later fee change by the
    // admin does not alter what this participant agreed to pay
    private final double feeAtRegistration;

    public Registration(int registrationId, Participant participant, Event event) {
        this.registrationId = registrationId;
        this.participant = participant;
        this.event = event;
        this.feeAtRegistration = event.getRegistrationFee();
    }

    // Constructor Overloading: used only when restoring a registration from the save file,
    // so it keeps the exact fee that was charged originally, even if the fee changed later.
    public Registration(int registrationId, Participant participant, Event event,
                         double feeAtRegistration) {
        this.registrationId = registrationId;
        this.participant = participant;
        this.event = event;
        this.feeAtRegistration = feeAtRegistration;
    }

    public int getRegistrationId() { return registrationId; }
    public Participant getParticipant() { return participant; }
    public Event getEvent() { return event; }
    public double getFeeAtRegistration() { return feeAtRegistration; }

    @Override
    public String toString() {
        String fee = feeAtRegistration == 0
                ? "Free" : Event.CURRENCY + " " + String.format("%.0f", feeAtRegistration);
        return "Registration #" + registrationId + ": " + participant.getName()
                + " (ID " + participant.getUserId() + ") -> " + event.getEventName()
                + " (" + event.getEventType() + ") | Fee: " + fee;
    }
}
