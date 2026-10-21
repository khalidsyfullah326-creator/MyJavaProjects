/**
 * ================================================
 * ABSTRACTION
 * ================================================
 * This is the abstract parent class of the whole project.
 * It defines the COMMON structure/behavior that every
 * "target" or "report" type object must have.
 * We cannot create an object directly from this class
 * (new ActivityTarget(...) is NOT allowed) - it can only
 * be used through its child classes (MonthlyTarget, DailyReport).
 */
public abstract class ActivityTarget {

    // ================================================
    // ENCAPSULATION
    // ================================================
    // All fields are PRIVATE so no other class can directly
    // access or modify them. Access is only possible through
    // the public getter/setter methods defined below.
    private double academicHours;
    private double skillHours;
    private double quranHours;   // CHANGED: Quran reading is now tracked in HOURS (double), not minutes.
    private int hadithCount;
    private double socialWorkHours;
    private double familyTimeHours;

    // ================================================
    // FINAL KEYWORD
    // ================================================
    // A constant that can NEVER be changed once assigned.
    // Used here to store how many categories this app tracks.
    public static final int TOTAL_CATEGORIES = 6;

    /**
     * Constructor - initializes all 6 tracked fields.
     * Every child class must call this via super(...).
     *
     * ================================================
     * EXCEPTION HANDLING
     * ================================================
     * This constructor throws a CHECKED custom exception
     * (InvalidTargetException) if ANY value is negative, since
     * negative hours/minutes/counts make no real-world sense.
     * Declaring "throws InvalidTargetException" forces every
     * caller (MonthlyTarget, DailyReport, and ultimately Main)
     * to handle the possibility of bad data.
     */
    public ActivityTarget(double academicHours, double skillHours, double quranHours,
                           int hadithCount, double socialWorkHours, double familyTimeHours)
            throws InvalidTargetException {
        validateNonNegative("Academic Hours", academicHours);
        validateNonNegative("Skill Hours", skillHours);
        validateNonNegative("Quran Hours", quranHours);
        validateNonNegative("Hadith Count", hadithCount);
        validateNonNegative("Social Work Hours", socialWorkHours);
        validateNonNegative("Family Time Hours", familyTimeHours);

        this.academicHours = academicHours;
        this.skillHours = skillHours;
        this.quranHours = quranHours;
        this.hadithCount = hadithCount;
        this.socialWorkHours = socialWorkHours;
        this.familyTimeHours = familyTimeHours;
    }

    // ================================================
    // EXCEPTION HANDLING (helper)
    // ================================================
    // Small reusable validation helper: if the value is negative,
    // we THROW our custom checked exception with a clear message
    // instead of silently accepting bad data.
    private static void validateNonNegative(String fieldName, double value) throws InvalidTargetException {
        if (value < 0) {
            throw new InvalidTargetException(fieldName + " cannot be negative (got " + value + ")");
        }
    }

    // ================================================
    // ABSTRACTION (Abstract Method)
    // ================================================
    // Declares WHAT every subclass must do, without saying HOW.
    // Each child class (MonthlyTarget, DailyReport) is FORCED
    // to give its own implementation of this method.
    public abstract void displayDetails();

    // ================================================
    // METHOD OVERLOADING (compile-time polymorphism)
    // ================================================
    // Same method name "displayDetails" but a DIFFERENT
    // parameter list (a boolean flag) compared to the abstract
    // method above. This is a normal, already-implemented
    // (non-abstract) method, so it is NOT overriding - it is
    // overloading. Java decides which version to run based on
    // the arguments the caller passes, at COMPILE time.
    public void displayDetails(boolean shortForm) {
        if (shortForm) {
            System.out.println("[Short] Academic=" + academicHours + "h, Skill=" + skillHours
                    + "h, Quran=" + quranHours + "h");
        } else {
            // Calls whichever no-arg version the actual runtime
            // object provides (this is where OVERRIDING kicks in).
            displayDetails();
        }
    }

    // ================================================
    // ENCAPSULATION: Getters and Setters
    // ================================================
    public double getAcademicHours() { return academicHours; }
    public double getSkillHours() { return skillHours; }
    public double getQuranHours() { return quranHours; }          // CHANGED: was getQuranMinutes()
    public int getHadithCount() { return hadithCount; }
    public double getSocialWorkHours() { return socialWorkHours; }
    public double getFamilyTimeHours() { return familyTimeHours; }

    // Setters also validate through the same helper, so bad data
    // can never sneak in even after the object is constructed.
    public void setAcademicHours(double academicHours) throws InvalidTargetException {
        validateNonNegative("Academic Hours", academicHours);
        this.academicHours = academicHours;
    }
    public void setSkillHours(double skillHours) throws InvalidTargetException {
        validateNonNegative("Skill Hours", skillHours);
        this.skillHours = skillHours;
    }
    public void setQuranHours(double quranHours) throws InvalidTargetException {   // CHANGED: was setQuranMinutes()
        validateNonNegative("Quran Hours", quranHours);
        this.quranHours = quranHours;
    }
    public void setHadithCount(int hadithCount) throws InvalidTargetException {
        validateNonNegative("Hadith Count", hadithCount);
        this.hadithCount = hadithCount;
    }
    public void setSocialWorkHours(double socialWorkHours) throws InvalidTargetException {
        validateNonNegative("Social Work Hours", socialWorkHours);
        this.socialWorkHours = socialWorkHours;
    }
    public void setFamilyTimeHours(double familyTimeHours) throws InvalidTargetException {
        validateNonNegative("Family Time Hours", familyTimeHours);
        this.familyTimeHours = familyTimeHours;
    }
}
