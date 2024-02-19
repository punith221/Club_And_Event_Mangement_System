import java.util.Scanner;

enum MainMenu {
    EVENTS_MANAGEMENT("Events Management"),
    CLUB_MANAGEMENT("Club Management"),
    EXIT("EXIT");

    private final String displayName;

    MainMenu(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void printOptions() {
        System.out.println("Select one of the Options");
        int i = 1;
        for (MainMenu option : MainMenu.values()) {
            System.out.println(i + ". " + option.getDisplayName());
            i++;
        }

    }

    public static void main(String[] args) throws Exception {
        System.out.println(
                "\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println(
                "\\\\\\\\\\\\\\\\\\ " + " CLUB AND EVENT MANAGEMENT SYSTEM " + " \\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out
                .println("\\\\\\\\\\\\\\\\\\" + "\\\\\\\\\\\\ WElCOME \\\\\\\\\\\\" + "\\\\\\\\\\\\\\\\\\\\\\\\\\");

        printOptions();

        int choice = scanner.nextInt();

        switch (MainMenu.values()[choice - 1]) {
            case EVENTS_MANAGEMENT:
                System.out.println("Selected: " + MainMenu.EVENTS_MANAGEMENT.getDisplayName());
                Events.eventMenu();
                break;

            case CLUB_MANAGEMENT:
                System.out.println("Selected: " + MainMenu.CLUB_MANAGEMENT.getDisplayName());
                Clubs.clubMenu();
                break;

            case EXIT:
                System.out.println("Selected: " + MainMenu.EXIT.getDisplayName());
                System.out.println("Exiting....");
                break;

            default:
                System.out.println("Invalid Option");
                break;
        }

    }
}
