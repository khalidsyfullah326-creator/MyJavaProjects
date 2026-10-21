package model;

/**
 * Inheritance: ITEvent inherits from Event.
 */
public class ITEvent extends Event {

    private int teamSize; // specific to IT events (1 = individual)

    public ITEvent(int eventId, String eventName, String venue,
                   int maxParticipants, double registrationFee, int teamSize) {
        // super keyword calls the parent class (Event) constructor
        super(eventId, eventName, "IT", venue, maxParticipants, registrationFee);
        setTeamSize(teamSize);
    }

    // Constructor Overloading: individual (team size 1) IT event
    public ITEvent(int eventId, String eventName, String venue,
                   int maxParticipants, double registrationFee) {
        this(eventId, eventName, venue, maxParticipants, registrationFee, 1);
    }

    public int getTeamSize() { return teamSize; }

    public void setTeamSize(int teamSize) {
        if (teamSize < 1) {
            throw new IllegalArgumentException("Team size must be at least 1.");
        }
        this.teamSize = teamSize;
    }

    // Method Overriding: implements the abstract method of Event
    @Override
    public String getRules() {
        return "Team size: " + teamSize + ". Bring your own laptop. Internet use is limited.";
    }

    // Method Overriding: adds IT details to the parent's output
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("   Team size: " + teamSize);
        System.out.println("   Rules: " + getRules());
    }

    // Method Overriding
    @Override
    public String toString() {
        return super.toString() + " [team of " + teamSize + "]";
    }
}
