# University IT Carnival Registration Management System

## 1. Project Description
A small **console-based Java application** that manages registrations for a university IT Carnival.
An **Admin** manages the events and participants, and a **Participant** can sign up, register for events and cancel registrations.
Participant and registration data is **saved to a plain text file** and reloaded automatically the next time the program starts.
The project is deliberately simple. Its main purpose is to **demonstrate Java OOP concepts** clearly, so it is easy to explain in a viva.

## 2. PrintMain Features
**Admin:** add event, view events, update event, **change the registration fee of any event at any time**, remove event, **add participant**, view participants, **delete participant**, view all registrations.

**Participant:** **sign up with ID, name, phone and email** (there is no pre-made participant list), log in with the ID, view events, register for an event, view own registrations, cancel a registration.

**Data saving:** participants and registrations are written to `itcarnival_data.txt` when the program ends, and read back automatically at the next start.

**Rules enforced:** unique participant IDs, valid phone and email, no duplicate registration for the same event, no registration for a full event, input is validated.

**Default events (10, loaded at every start, currency = taka):**

| ID | Event | Type | Fee |
|---|---|---|---|
| 1 | Hackathon | IT | Tk 500 |
| 2 | Programming Contest (ICPC) | IT | Tk 500 |
| 3 | Project Showcasing | IT | Free |
| 4 | IT Quiz | IT | Tk 50 |
| 5 | PUBG | Gaming | Tk 100 |
| 6 | Free Fire | Gaming | Tk 100 |
| 7 | eFootball | Gaming | Tk 100 |
| 8 | Chess | Gaming | Tk 50 |
| 9 | Ludo | Gaming | Tk 50 |
| 10 | Rubik's Cube | Gaming | Tk 50 |

IT events are listed above the gaming events. Venues, seat limits and team sizes are sensible defaults and can be changed by the admin (Update Event).

## 3. Technologies Used
- Java 17 or later (tested with Java 21)
- Console (text) interface only: no GUI, no database, no JDBC
- `ArrayList` for all data held in memory
- A plain text file (`java.io`) for saving data between runs

## 4. Project Structure
```
ITCarnival/
├── src/
│   ├── model/
│   │   ├── User.java
│   │   ├── Admin.java
│   │   ├── Participant.java
│   │   ├── Event.java            (abstract)
│   │   ├── GamingEvent.java
│   │   ├── ITEvent.java
│   │   └── Registration.java
│   ├── service/
│   │   ├── UserService.java
│   │   ├── EventService.java
│   │   ├── RegistrationService.java
│   │   └── FileStorageService.java
│   ├── exception/
│   │   ├── DuplicateRegistrationException.java
│   │   └── EventNotFoundException.java
│   └── main/
│       └── PrintMain.java
├── README.md
└── .gitignore
```
At run time the program also creates `itcarnival_data.txt` in the folder it is started from.

## 5. Description of Each Class
| Class | Purpose |
|---|---|
| `User` | Base class with ID, name, email, phone (private fields + getters/setters). |
| `Admin` | Child of `User`. Represents the carnival administrator. |
| `Participant` | Child of `User`. A person who signed up with ID, name, phone and email. |
| `Event` | **Abstract** parent of all events. Holds common data (ID, name, type, venue, max participants, fee, seats taken) and declares the abstract method `getRules()`. A fee of 0 is shown as "Free". |
| `GamingEvent` | Child of `Event`. Adds `platform` (PC, Mobile, PlayStation...). |
| `ITEvent` | Child of `Event`. Adds `teamSize`. |
| `Registration` | Links one `Participant` to one `Event`, with a registration ID. Remembers the fee at the time of registration. |
| `UserService` | Stores users in an `ArrayList<User>`. Signs up (validates) and removes participants, finds participants. |
| `EventService` | Stores events in an `ArrayList<Event>`. Add, find, update, change fee, remove. |
| `RegistrationService` | Stores registrations in an `ArrayList<Registration>`. Register, cancel, list, restore saved registrations, and clean up when an event or participant is removed. |
| `FileStorageService` | The persistence layer. Saves participants and registrations to `itcarnival_data.txt` and loads them back at startup. |
| `DuplicateRegistrationException` | Thrown when the same participant registers for the same event twice. |
| `EventNotFoundException` | Thrown when an event ID does not exist. |
| `PrintMain` | Menus, the 10 default events, keyboard input handling, and the load/save calls. |

## 6. The Four OOP Pillars

**Encapsulation**: Fields are `private` and accessed via getters/setters. Example: `User` (`getName()`, `setName()`) and `Event`, whose setters also validate data (for example, the fee cannot be negative).

**Inheritance**: A child class reuses the parent's code using `extends`.
- `User → Admin`, `User → Participant`
- `Event → GamingEvent`, `Event → ITEvent`

**Abstraction**: `Event` is an `abstract` class. It cannot be instantiated and declares the abstract method `getRules()`. `GamingEvent` and `ITEvent` must provide their own versions.

**Polymorphism**: A parent reference can hold a child object, and Java chooses the child's method at runtime.
- `Event hackathon = new ITEvent(...);` and `Event pubg = new GamingEvent(...);` in `Main.loadDefaultEvents()`
- `ArrayList<Event>` in `EventService` holds both event types.
- In `Main.viewEvents()`, `event.displayInfo()` runs `GamingEvent`'s or `ITEvent`'s version depending on the real object.

## 7. Method Overloading (same name, different parameters)
- `RegistrationService.register(Participant, Event)` and `RegistrationService.register(int participantId, int eventId)`. The second version looks up the objects and then calls the first.
- Constructors, see "Constructor overloading" below.

**Constructor overloading:** `User(id, name, email, phone)` and `User(id, name)`; `Participant` (2 constructors); `Event` (with and without fee); `GamingEvent` (with and without fee); `ITEvent` (with and without team size, e.g. Project Showcasing); `Registration` (a normal one that reads the event's current fee, and one that takes the saved fee when restoring from the file).

## 8. Method Overriding (child replaces/extends a parent method)
| Method | Where |
|---|---|
| `getRules()` | `GamingEvent`, `ITEvent` (implement the abstract method) |
| `displayInfo()` | `Participant` (overrides `User`); `GamingEvent`, `ITEvent` (override `Event`) |
| `toString()` | `User`, `Event`, `GamingEvent`, `ITEvent`, `Registration` |
| `getRole()` | `Admin`, `Participant` (override `User`) |

## 9. The `this` Keyword
- `this.name = name;` in constructors: separates the field from the parameter.
- `this(userId, name, "N/A", "N/A");` in `User`: one constructor calls another.
- Also in `Event`, `GamingEvent`, `ITEvent`, `Registration`, `RegistrationService`.

## 10. The `super` Keyword
- `super(userId, name, email, phone);` in `Admin` and `Participant`: calls the parent constructor.
- `super(eventId, eventName, "Gaming", ...)` in `GamingEvent`; the same idea in `ITEvent`.
- `super.displayInfo();` in `Participant`, `GamingEvent`, `ITEvent`: reuses the parent's printing code.
- `super(message)` in the custom exceptions.

## 11. The `final` Keyword
- `Event.CURRENCY` (`public static final String`): a fixed label that must not change.
- `EventService.MAX_EVENTS` (`public static final int`): a fixed system rule (max 20 events).
- `Registration`: `registrationId`, `participant`, `event`, `feeAtRegistration` are `final` because a registration record never changes after creation. This is also why an admin fee change does not affect people who already registered.
- `FileStorageService.FILE_NAME` (`private static final String`): the save file name is fixed.
- The `ArrayList` fields in the services are `final`: the list is never replaced, only its contents change.

## 12. Exception Handling
Exceptions use `try-catch`, and error messages are shown to the user without crashing the program.

| Situation | How it is handled |
|---|---|
| Invalid menu choice | `IllegalArgumentException` thrown in the `switch` default, caught in the menu loop |
| Invalid numeric input | `NumberFormatException` caught in `readInt()` / `readDouble()`; the user is asked again |
| Empty input | `readText()` repeats the question; setters also validate |
| Event not found | Custom `EventNotFoundException` |
| Participant not found, duplicate participant ID, invalid phone or email | `IllegalArgumentException` from `UserService` |
| Duplicate registration | Custom `DuplicateRegistrationException` |
| Event full | `IllegalStateException` from `RegistrationService` |
| Cancelling a missing registration | `IllegalStateException` from `RegistrationService` |
| Cannot read or write the save file | `IOException` caught in `FileStorageService`; a message is printed |
| Saved registration points to a missing participant or event | Caught while loading; the registration is skipped and a message is printed |
| Input stream ends (for example, piped input) | `NoSuchElementException` caught in `main`; the program exits normally |

Only **two custom exceptions** are used to keep things simple. Other cases use standard Java exceptions.

## 13. Why ArrayList?
`ArrayList` is simple, resizable, keeps insertion order and is easy to loop through. It is perfect for a small project where the goal is learning OOP, not performance.

## 14. Data Saving (Plain Text File)
**Why a text file, and not a database or serialization?** A database would add JDBC code, SQL, drivers and setup that would hide the OOP concepts this project is meant to show. Java serialization produces unreadable binary data. A plain text file needs no libraries, can be opened in any editor, and is easy to explain.

**When it happens:**
- On startup, `PrintMain` creates the default events, then calls `loadData()`. If `itcarnival_data.txt` does not exist (first run), nothing is loaded.
- When the program ends, `saveData()` runs inside a `finally` block, so the data is saved whichever menu option ends the program, or if the input stream ends. It will not run if the process is force-killed.

**File format:** one record per line, fields separated by `|`.
```
PARTICIPANT|userId|name|email|phone
REGISTRATION|registrationId|participantId|eventId|feeAtRegistration
```
Example:
```
PARTICIPANT|5|Ali Khan|ali@mail.com|01712345678
REGISTRATION|1|5|1|500.0
```
Registrations are restored with `RegistrationService.restoreRegistration()`, which keeps the original fee, updates the seat count and continues the registration ID numbering after the highest saved ID.

## 15. Important Limitations
- **Events are not saved.** The 10 default events are re-created on every start. If the admin changes a fee, updates an event, adds an event or removes an event, that change is lost when the program closes.
- Because of this, a saved registration for an admin-added event (ID 11 or higher) is skipped at the next start, since that event no longer exists. If the admin removes a default event, its registrations are deleted before saving, and the event comes back empty at the next start.
- The `|` character is the field separator. A name containing `|` would be read back incorrectly, because the code does not escape it.
- A damaged or hand-edited line in the save file is not fully protected against. A participant line with missing fields can cause an error while loading.
- There is no password for the Admin. This is a demo.

## 16. How to Compile
You need a JDK (Java 17 or later). Open a terminal in the `ITCarnival` folder.

**Linux / macOS**
```bash
mkdir -p out
javac -d out $(find src -name "*.java")
```
**Windows (Command Prompt)**
```bat
mkdir out
dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
```
(In an IDE such as IntelliJ, Eclipse or VS Code, open the folder and mark `src` as the source root.)

## 17. How to Run
```bash
java -cp out main.PrintMain
```
Run it from the `ITCarnival` folder every time. The save file `itcarnival_data.txt` is created in the folder you run the command from, so starting the program from a different folder will not find your earlier data. To start fresh, delete that file.

## 18. Sample Console Output (trimmed)
The first run starts with events 1 to 10 and **no participants**. The admin ID is 0, so a participant can never clash with it.

```
=== Welcome to the University IT Carnival Registration System ===

===== SELECT ROLE =====
1. Admin
2. Participant
3. Exit
Enter choice: 2

===== PARTICIPANT =====
1. Sign up (new participant)
2. Log in with my ID
3. Back
Enter choice: 1
Participant ID: 5
Name: Ali Khan
Phone: 01712345678
Email: ali@mail.com
Sign-up successful! Welcome, Ali Khan.

===== PARTICIPANT MENU =====
Enter choice: 1

--- All Events ---
[1] Hackathon (IT)
   Venue: Computer Lab 1 | Seats: 0/30 | Fee: Tk 500
   Team size: 3
   Rules: Team size: 3. Bring your own laptop. Internet use is limited.
[3] Project Showcasing (IT)
   Venue: PrintMain Hall | Seats: 0/40 | Fee: Free
...
[10] Rubik's Cube (Gaming)
   Venue: Indoor Games Room | Seats: 0/20 | Fee: Tk 50
   Platform: Rubik's Cube
   Rules: Play on Rubik's Cube. Arrive 15 minutes early. No cheating tools allowed.

Enter choice: 2
Enter event ID to register: 1
Success! Registration #1: Ali Khan (ID 5) -> Hackathon (IT) | Fee: Tk 500

Enter choice: 2
Enter event ID to register: 1
Error: Ali Khan is already registered for Hackathon.

===== ADMIN MENU =====
1. Add Event
2. View Events
3. Update Event
4. Change Registration Fee
5. Remove Event
6. Add Participant
7. View Participants
8. Delete Participant
9. View Registrations
10. Exit
Enter choice: 4
Enter event ID: 1
Current fee of Hackathon: Tk 500
New registration fee (Tk, 0 = free): 600
Fee updated: Hackathon is now Tk 600. (People who already registered keep their old fee.)

Enter choice: 6
Participant ID: 5
Error: ... ID 5 is already in use.

Enter choice: 8
Enter participant ID to delete: 5
Participant deleted: Ali Khan (1 registration(s) removed).
```

When the program ends it prints:
```
[Data saved to itcarnival_data.txt]
```
and on the next start, if the file exists:
```
[Data loaded from itcarnival_data.txt]
```

## 19. Possible Future Improvements
- Save the events too (fees, added, updated and removed events), so admin changes survive a restart and registrations for added events are not lost.
- Escape or reject the `|` character in text fields, and make loading safe against damaged lines.
- Add a real login system with passwords.
- Add more event types and event dates/times.
- Add payment tracking (who has actually paid the registration fee).
- Add a GUI (JavaFX/Swing) or a web interface.
- Add unit tests (JUnit).
