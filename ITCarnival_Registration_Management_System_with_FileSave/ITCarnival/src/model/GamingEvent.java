package model;

/**
 * Inheritance: GamingEvent inherits from Event.
 */
public class GamingEvent extends Event {

    private String platform; // specific to gaming events (PC, Mobile, PlayStation...)

    public GamingEvent(int eventId, String eventName, String venue,
                       int maxParticipants, double registrationFee, String platform) {
        // super keyword calls the parent class (Event) constructor
        super(eventId, eventName, "Gaming", venue, maxParticipants, registrationFee);
        this.platform = platform;
    }

    // Constructor Overloading: free gaming event (no fee)
    public GamingEvent(int eventId, String eventName, String venue,
                       int maxParticipants, String platform) {
        this(eventId, eventName, venue, maxParticipants, 0.0, platform);
    }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    // Method Overriding: implements the abstract method of Event
    @Override
    public String getRules() {
        return "Play on " + platform + ". Arrive 15 minutes early. No cheating tools allowed.";
    }

    // Method Overriding: adds gaming details to the parent's output
    @Override
    public void displayInfo() {
        super.displayInfo(); // super: print the common event info first
        System.out.println("   Platform: " + platform);
        System.out.println("   Rules: " + getRules());
    }

    // Method Overriding
    @Override
    public String toString() {
        return super.toString() + " on " + platform;
    }
}
