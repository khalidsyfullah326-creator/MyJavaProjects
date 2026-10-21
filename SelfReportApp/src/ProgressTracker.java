import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * ENCAPSULATION
 * ================================================
 * ProgressTracker hides its internal list of daily reports and
 * the monthly target behind private fields, and only exposes
 * controlled behavior through its public methods.
 */
public class ProgressTracker {

    // FINAL: these two references are set once in the constructor
    // and are never reassigned afterwards (the LIST CONTENTS can
    // still change via add(), but the reference itself cannot).
    private final MonthlyTarget monthlyTarget;
    private final List<DailyReport> dailyReports;

    public ProgressTracker(MonthlyTarget target) {
        this.monthlyTarget = target;
        this.dailyReports = new ArrayList<>();
    }

    public void addDailyReport(DailyReport report) {
        dailyReports.add(report);
        // POLYMORPHISM: "report" is typed as DailyReport, and its
        // overridden displayDetails() runs automatically.
        report.displayDetails();
    }

    public void generateMonthlyProgress() {
        double totalAcademic = 0, totalSkill = 0, totalQuran = 0, totalSocial = 0, totalFamily = 0; // CHANGED: totalQuran is now double (hours)
        int totalHadith = 0;

        for (DailyReport r : dailyReports) {
            totalAcademic += r.getAcademicHours();
            totalSkill += r.getSkillHours();
            totalQuran += r.getQuranHours();   // CHANGED: was getQuranMinutes()
            totalHadith += r.getHadithCount();
            totalSocial += r.getSocialWorkHours();
            totalFamily += r.getFamilyTimeHours();
        }

        System.out.println("\n==========================================");
        System.out.println("       MONTH-END PROGRESS REPORT          ");
        System.out.println("==========================================");

        // Calling the 3-argument overload (achieved vs target, with %)
        printProgress("Academic Study (Hrs)", totalAcademic, monthlyTarget.getAcademicHours());
        printProgress("Skill Learning (Hrs)", totalSkill, monthlyTarget.getSkillHours());
        printProgress("Quran Reading (Hrs)", totalQuran, monthlyTarget.getQuranHours()); // CHANGED: label + hours
        printProgress("Hadith Reading (Count)", totalHadith, monthlyTarget.getHadithCount());
        printProgress("Social Work (Hrs)", totalSocial, monthlyTarget.getSocialWorkHours());
        printProgress("Family Time (Hrs)", totalFamily, monthlyTarget.getFamilyTimeHours());

        // Calling the 2-argument overload (just the raw achieved total)
        System.out.println("\n--- Extra: Achieved-only quick view ---");
        printProgress("Total Academic Hours", totalAcademic);
        printProgress("Total Family Hours", totalFamily);
    }

    // ================================================
    // METHOD OVERLOADING
    // ================================================
    // Version 1: compares "achieved" against a "target" and also
    // prints the percentage completed.
    private void printProgress(String category, double achieved, double target) {
        double percentage = (target > 0) ? (achieved / target) * 100 : 0;
        System.out.printf("%-22s: Achieved %.1f / Target %.1f (%.1f%%)\n", category, achieved, target, percentage);
    }

    // Version 2 (OVERLOADED): same method name "printProgress", but
    // only TWO parameters - no target, no percentage, just the raw
    // total. Java tells the two apart purely by their different
    // parameter lists (this is the whole idea of overloading).
    private void printProgress(String category, double achieved) {
        System.out.printf("%-22s: %.1f\n", category, achieved);
    }
}
