
import java.util.*;
import java.io.*;

public class Events {

    static Scanner sc = new Scanner(System.in);

    public static void eventMenu() {
        System.out.println("___________________ EVENT MENU ___________________");
        System.out.println();
        System.out.println("Select an option from below");
        System.out.println("1. Add Event");
        System.out.println("2. View Event");
        System.out.println("3. Delete Event");
        System.out.println("4. Exit");

        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                addevent();
                break;

            case 2:
                viewEvent();
                break;

            case 3:
                deleteEvent();
                break;

            default:
                System.exit(0);
                break;
        }

    }

    ///////////////////////////////////////////////////

    static void addevent() {
        String clubName, inpData, Line, arr[] = new String[4];
        Set<String> existingEvents = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        System.out.println("Enter the club whcih Hosts the event");
        clubName = sc.next().toLowerCase();
        System.out.println("Enter the event Name, Date, co-ordinator, place ");
        // eventName = scanner.next().toLowerCase();

        for (int i = 0; i < 4; i++) {
            inpData = sc.next();
            arr[i] = inpData;
            sb.append(inpData).append(" ");
        }

        try (BufferedReader read = new BufferedReader(new FileReader(clubName + ".txt"))) {
            while ((Line = read.readLine()) != null) {
                existingEvents.add(Line.trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (existingEvents.contains(arr[0])) {
            System.out.println("Event already exists...");
            System.out.println("View Event " + arr[0] + " [yes] or [no]?");
            String choice = sc.next();
            if (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y")) {
                viewEvent();
            } else {
                eventMenu();
            }
        }

        try (BufferedWriter wr = new BufferedWriter(new FileWriter((new File(clubName + ".txt")), true))) {

            wr.write(sb.toString().trim());
            wr.newLine();

        } catch (Exception e) {
            System.out.println("Failed to add Event to " + clubName);
            e.printStackTrace();
        }
        eventMenu();
    }

    /////////////////////////////////////////////////////////////////

    static void viewEvent() {

        System.out.println("Enter the Club which hosts the event");
        String clubName = sc.next();

        System.out.println("Want to View all the Event details or Want to View particular Event");
        System.out.println("1. View All Events");
        System.out.println("2. View Particular Event");
        System.out.println("3. Exit");
        int option = sc.nextInt();

        switch (option) {
            case 1:
                viewAllEvents(clubName);
                break;

            case 2:
                viewParEvent(clubName);
                break;

            default:
                eventMenu();
        }

    }

    private static void viewAllEvents(String clubName) {
        System.out.println("EventName\tDate\tco-ordinator\tplace");
        try (BufferedReader reader = new BufferedReader(new FileReader(clubName + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        eventMenu();
    }

    private static void viewParEvent(String clubName) {

        System.out.println("Enter the Event Name");
        String eventName = sc.next();

        List<Map.Entry<String, List<String>>> mappedWords = processWords(clubName + ".txt");

        System.out.println("EventName  Date  co-ordinator  place");
        for (Map.Entry<String, List<String>> entry : mappedWords) {
            String keyValue = entry.getKey();
            if (keyValue.equals(eventName)) {

                for (String values : entry.getValue()) {
                    System.out.print(values + " ");
                }
                System.out.println();
            }
        }
        eventMenu();
    }

    static List<Map.Entry<String, List<String>>> processWords(String fileName) {
        List<Map.Entry<String, List<String>>> wordMap = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");

                if (words.length > 0) {
                    String key = words[0];

                    List<String> values = new ArrayList<>();
                    values.add(key);

                    for (int i = 1; i < words.length; i++) {
                        values.add(words[i]);
                    }

                    wordMap.add(new AbstractMap.SimpleEntry<>(key, values));
                }

            }
        } catch (Exception e) {
            System.out.println("Failed to Get club details");
            e.printStackTrace();
        }

        return wordMap;

    }

    /////////////////////////////////////////////////////////

    public static void deleteEvent() {
        String clubName;

        Map<String, String> eventDetails = new HashMap<>();

        System.out.println("Enter the club Name");
        clubName = sc.next();

        List<Map.Entry<String, List<String>>> events = processWords(clubName + ".txt");

        System.out.println("Enter event and date when event was conducted");
        eventDetails.put("eventName", sc.next());
        eventDetails.put("eventDate", sc.next());

        deleteEntries(events, eventDetails);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(clubName + ".txt", false))) {

            for (Map.Entry<String, List<String>> evntDtl : events) {
                String eventString = CmnUtls.listToStringEvents(evntDtl);
                writer.write(eventString);
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Failed to add member");
            e.printStackTrace();
        }
        eventMenu();
    }

    private static void deleteEntries(List<Map.Entry<String, List<String>>> events,
            Map<String, String> eventDetails) {
        Iterator<Map.Entry<String, List<String>>> iterator = events.iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();
            String key = entry.getKey();
            if (key.equals(eventDetails.get("eventName")) && checkValues(entry, eventDetails)) {
                iterator.remove();
            }
        }
    }

    private static Boolean checkValues(Map.Entry<String, List<String>> entry, Map<String, String> eventDetails) {
        String key = entry.getKey();

        if (key.equals(eventDetails.get("eventName"))) {
            for (String value : entry.getValue()) {
                if (value.equals(eventDetails.get("eventDate"))) {
                    return true;
                }
            }
        }

        return false;
    }

}
