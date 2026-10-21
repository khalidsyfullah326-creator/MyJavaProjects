import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main - entry point of the console application.
 * Takes monthly targets from the user, loops to collect daily
 * reports, then prints the final month-end progress summary.
 *
 * This class ties together all 4 OOP pillars plus exception
 * handling:
 *  - Encapsulation      : all data access goes through getters/setters
 *  - Abstraction        : we work with "displayDetails()" without
 *                         caring how each class implements it
 *  - Inheritance        : MonthlyTarget & DailyReport reuse ActivityTarget
 *  - Polymorphism       : method overriding + method overloading below
 *  - Exception Handling : try / catch / finally is used at TWO levels:
 *                         (1) an outer safety-net around the whole
 *                             program, and (2) inner, targeted blocks
 *                             around each risky operation (bad number
 *                             format, negative values).
 */
public class SelfReportMain {

    // ================================================
    // FINAL KEYWORD
    // ================================================
    // Fixed configuration values that never change while the
    // program runs.
    public static final String APP_NAME = "Personal Self-Report System";
    public static final int MAX_DAYS_IN_MONTH = 31;

    public static void main(String[] args) {
        // FINAL: this Scanner reference is created once and is
        // never reassigned to a different object afterwards.
        final Scanner sc = new Scanner(System.in);

        // ================================================
        // EXCEPTION HANDLING: OUTER try / catch / finally
        // ================================================
        // TRY   : run the entire program's logic.
        // CATCH : if ANY unexpected exception slips through every
        //         inner check below, we catch it here instead of
        //         letting the program crash with a stack trace.
        // FINALLY: this block ALWAYS runs - whether the try block
        //         finished normally or an exception was thrown -
        //         so it is the safest place to release resources
        //         like the Scanner.
        try {
            runApp(sc);
        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
        } finally {
            sc.close();
            System.out.println("\nScanner closed. Program ended.");
        }
    }

    // All the actual program logic lives here so the outer
    // try/catch/finally in main() stays short and readable.
    private static void runApp(Scanner sc) {
        System.out.println("=== " + APP_NAME + " Setup ===");
        System.out.println("Enter your monthly targets:");

        System.out.print("Month Name: ");
        String monthName = sc.next();

        // readDouble()/readInt() below already guard against
        // wrong-TYPE input (e.g. typing "abc" for a number).
        double acadTarget = readDouble(sc, "Academic Study Target (Hours): ");
        double skillTarget = readDouble(sc, "Skill Learning Target (Hours): ");
        double quranTarget = readDouble(sc, "Quran Reading Target (Hours): "); // Quran tracked in HOURS, not minutes
        int hadithTarget = readInt(sc, "Hadith Reading Target (Count): ");
        double socialTarget = readDouble(sc, "Social Work Target (Hours): ");
        double familyTarget = readDouble(sc, "Family Time Target (Hours): ");

        MonthlyTarget target = null;

        // ================================================
        // EXCEPTION HANDLING: TRY / CATCH (business-rule check)
        // ================================================
        // TRY  : attempt to build the MonthlyTarget - the
        //        constructor throws InvalidTargetException if any
        //        value is negative.
        // CATCH: if that happens, we don't crash - we tell the user
        //        what went wrong and fall back to a safe default.
        try {
            target = new MonthlyTarget(monthName, acadTarget, skillTarget, quranTarget,
                    hadithTarget, socialTarget, familyTarget);
        } catch (InvalidTargetException e) {
            System.out.println("Could not create monthly target: " + e.getMessage());
            System.out.println("Falling back to default target values (all 0)...");
            try {
                target = new MonthlyTarget(monthName, 0, 0, 0, 0, 0, 0);
            } catch (InvalidTargetException neverHappens) {
                // 0 values always pass validation, so this branch is unreachable,
                // but the compiler still requires us to catch the checked exception.
            }
        }

        target.displayDetails();
        target.displayDetails(true);

        ProgressTracker tracker = new ProgressTracker(target);

        int dayCounter = 1;
        while (dayCounter <= MAX_DAYS_IN_MONTH) {
            System.out.println("\n--- Submit Daily Report for Day " + dayCounter + " ---");
            System.out.print("Do you want to add a report for Day " + dayCounter + "? (yes/no): ");
            String choice = sc.next();

            if (choice.equalsIgnoreCase("no")) {
                break;
            }

            double aHours = readDouble(sc, "Academic Hours Today: ");
            double sHours = readDouble(sc, "Skill Hours Today: ");
            double qHours = readDouble(sc, "Quran Reading Hours Today: "); // Quran tracked in HOURS, not minutes
            int hCount = readInt(sc, "Hadith Count Today: ");
            double socHours = readDouble(sc, "Social Work Hours Today: ");
            double famHours = readDouble(sc, "Family Hours Today: ");

            // ================================================
            // EXCEPTION HANDLING: TRY / CATCH (per-day validation)
            // ================================================
            // TRY  : build and record this day's DailyReport.
            // CATCH: if any value is negative, InvalidTargetException
            //        is thrown - we reject only THIS day's data and
            //        let the user re-enter it, without losing any
            //        previously saved days.
            try {
                DailyReport daily = new DailyReport(dayCounter, aHours, sHours, qHours, hCount, socHours, famHours);
                tracker.addDailyReport(daily);
                daily.displayDetails("Log");
                dayCounter++; // only move to the next day if this report was valid
            } catch (InvalidTargetException e) {
                System.out.println("Report rejected: " + e.getMessage());
                System.out.println("Please re-enter Day " + dayCounter + "'s data.");
            }
        }

        // Show the final month-end report.
        tracker.generateMonthlyProgress();
    }

    // ================================================
    // EXCEPTION HANDLING: TRY / CATCH (input-format errors)
    // ================================================
    // Keeps asking until the user types an actual decimal number.
    // TRY  : attempt to read a double from the Scanner.
    // CATCH: InputMismatchException is thrown by Scanner when the
    //        typed text can't be converted to the requested type
    //        (e.g. "abc" instead of a number). We warn the user,
    //        discard the bad token with sc.next() (otherwise the
    //        loop would spin forever on the same invalid input),
    //        and ask again.
    private static double readDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid number, please try again.");
                sc.next();
            }
        }
    }

    // Same idea as readDouble(), but for whole-number fields
    // like the Hadith count.
    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid number, please try again.");
                sc.next();
            }
        }
    }
}
