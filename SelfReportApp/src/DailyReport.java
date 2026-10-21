/**
 * ================================================
 * INHERITANCE
 * ================================================
 * DailyReport IS-A ActivityTarget too, but it adds ONE extra
 * field (dayNumber) that only makes sense for a single day's
 * entry, on top of everything it inherits from the parent.
 */
public class DailyReport extends ActivityTarget {

    // FINAL: the day number of a submitted report should never
    // be edited after the object is created.
    private final int dayNumber;

    /**
     * EXCEPTION HANDLING: this constructor also declares
     * "throws InvalidTargetException" because its super(...)
     * call can throw it (e.g. someone tries to log -2 Quran
     * hours for a day). The exception then propagates up to
     * Main, where it is caught and handled gracefully.
     */
    public DailyReport(int dayNumber, double academicHours, double skillHours, double quranHours,
                        int hadithCount, double socialWorkHours, double familyTimeHours)
            throws InvalidTargetException {
        // SUPER: reuse the parent constructor to set up the
        // 6 shared fields instead of duplicating that logic here.
        super(academicHours, skillHours, quranHours, hadithCount, socialWorkHours, familyTimeHours);
        this.dayNumber = dayNumber;
    }

    public int getDayNumber() { return dayNumber; }

    // ================================================
    // POLYMORPHISM (Method Overriding)
    // ================================================
    // DailyReport's own version of displayDetails() - completely
    // different output from MonthlyTarget's version, even though
    // both share the exact same method signature.
    @Override
    public void displayDetails() {
        System.out.println("Day " + dayNumber + " Report Submitted -> "
                + "Academic=" + getAcademicHours() + "h, Skill=" + getSkillHours()
                + "h, Quran=" + getQuranHours() + "h, Hadith=" + getHadithCount()   // CHANGED: was mins
                + ", Social=" + getSocialWorkHours() + "h, Family=" + getFamilyTimeHours() + "h");
    }

    // ================================================
    // METHOD OVERLOADING
    // ================================================
    // Same method name "displayDetails" but a NEW parameter list
    // (a String prefix) that only exists on DailyReport - separate
    // from the boolean overload already declared in the parent
    // class. This proves overloading works across different
    // parameter types, not just different counts.
    public void displayDetails(String prefix) {
        System.out.print(prefix + " -> ");
        displayDetails(); // reuses the overridden no-arg version above
    }
}
