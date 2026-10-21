/**
 * ================================================
 * INHERITANCE
 * ================================================
 * MonthlyTarget IS-A ActivityTarget. It reuses (inherits) all
 * the fields, getters and setters from the parent class instead
 * of rewriting them, and only adds what is unique to a monthly
 * target (the month name).
 */
public class MonthlyTarget extends ActivityTarget {

    // ================================================
    // FINAL KEYWORD
    // ================================================
    // Once a MonthlyTarget object is created, its month name
    // must never change again, so it is declared final and can
    // only be assigned once (inside the constructor).
    private final String monthName;

    /**
     * ================================================
     * EXCEPTION HANDLING
     * ================================================
     * This constructor calls super(...), which can throw
     * InvalidTargetException if any value is negative. Since we
     * don't want to swallow that error here, we simply re-declare
     * "throws InvalidTargetException" and let it propagate up to
     * whoever creates a MonthlyTarget (ultimately Main).
     */
    public MonthlyTarget(String monthName, double academicHours, double skillHours, double quranHours,
                          int hadithCount, double socialWorkHours, double familyTimeHours)
            throws InvalidTargetException {
        // ================================================
        // SUPER KEYWORD
        // ================================================
        // Calls the parent (ActivityTarget) constructor to
        // initialize the 6 shared fields. A call to super(...)
        // must always be the FIRST statement inside a child
        // class constructor.
        super(academicHours, skillHours, quranHours, hadithCount, socialWorkHours, familyTimeHours);
        this.monthName = monthName;
    }

    public String getMonthName() { return monthName; }

    // ================================================
    // POLYMORPHISM (Method Overriding - runtime polymorphism)
    // ================================================
    // @Override tells the compiler this method REPLACES the
    // parent's abstract version for this specific class. When
    // called through a parent-type reference
    // (ActivityTarget ref = new MonthlyTarget(...)), Java
    // decides at RUNTIME which version actually executes.
    @Override
    public void displayDetails() {
        System.out.println("\n--- Monthly Target Summary (" + monthName + ") ---");
        System.out.println("Academic Hours Goal   : " + getAcademicHours());
        System.out.println("Skill Learning Goal   : " + getSkillHours() + " hours");
        System.out.println("Quran Reading Goal    : " + getQuranHours() + " hours"); // CHANGED: was mins
        System.out.println("Hadith Study Goal     : " + getHadithCount());
        System.out.println("Social Work Goal      : " + getSocialWorkHours() + " hours");
        System.out.println("Family Time Goal      : " + getFamilyTimeHours() + " hours");
    }
}
