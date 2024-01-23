
import java.util.*;
import java.io.*;

class member {
    int id;
    String name;
    String add;
    String dept;
    String phoneNo;
}

public class Main {

    static member stu = new member();
    static Scanner scanner = new Scanner(System.in);
    static File membersObjFile;
    private static final int ID_LENGTH = 5;

    public static void main(String[] args) {
        mainmenu();
    }

    static void mainmenu() {

        System.out.println(
                "\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println(
                "\\\\\\\\\\\\\\\\\\ " + " CLUB AND EVENT MANAGEMENT SYSTEM " + " \\\\\\\\\\\\\\\\\\\\\\\\\\");
        System.out
                .println("\\\\\\\\\\\\\\\\\\" + "\\\\\\\\\\\\ WElCOME \\\\\\\\\\\\" + "\\\\\\\\\\\\\\\\\\\\\\\\\\");

        System.out.println();
        System.out.println("Select an option from below");
        System.out.println("1. Events Management");
        System.out.println("2. Club Management");
        System.out.println("3. Exit");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                eventMenu();
                break;

            case 2:
                clubMenu();
                break;

            default:
                System.exit(0);
                break;
        }

    }

    public static void eventMenu() {
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ EVENT MENU \\\\\\\\\\\\\\\\\\\\\\\\");
        System.out.println();
        System.out.println("Select an option from below");
        System.out.println("1. Add Event");
        System.out.println("2. View Event");
        System.out.println("3. Delete Event");
        System.out.println("4. Exit");

        int choice = scanner.nextInt();

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

    public static void clubMenu() {
        int choice01;

        while (true) {
            System.out.println("________________CLUB MENU_________________");
            System.out.print("1. Create Club\n2. View clubs\n3. Add members for the club\n" + //
                    "4.View Club Members\n5.Exit\n");
            choice01 = scanner.nextInt();
            switch (choice01) {
                case 1:
                    create_clubs();
                    break;

                case 2:
                    club_members();
                    break;

                case 3:
                    viewClubList();
                    break;

                case 4:
                    viewClubmember();
                    break;

                default:
                    mainmenu();
            }
        }

    }

    public static void create_clubs() {
        System.out.println("Enter the name of the club");
        String clubName = scanner.next().toLowerCase();

        Set<String> existingClubs = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("clubs.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                existingClubs.add(line.trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!existingClubs.contains(clubName)) {
            try {
                writeToFile(clubName, new File("clubs.txt"));
                File clubObj = new File(clubName + ".txt");
                clubObj.createNewFile();
                System.out.println("File Created Successfully");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void club_members() {
        String clubName, fileName = "_members";
        Set<String> existingClubs = new HashSet<>();

        while (true) {
            System.out.println("enter the club name ");
            clubName = scanner.next();

            try (BufferedReader reader = new BufferedReader(new FileReader("clubs.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    existingClubs.add(line.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!existingClubs.contains(clubName)) {
                System.out.println("Club does not exists");
                System.out.println("Want to create club (Yes or No)");
                String inp = scanner.next();
                if (inp.toLowerCase().equals("yes")) {
                    create_clubs();
                    clubMenu();
                } else {
                    return;
                }
            }

            try {
                writeToFile(clubName + fileName, new File("clubsMemFile.txt"));
                File obj = new File(clubName + fileName + ".txt");
                obj.createNewFile();
                addmember(clubName + fileName + ".txt");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static void viewClubList() {
        ArrayList<String> clubList = new ArrayList<>();

        try (BufferedReader rd = new BufferedReader(new FileReader("clubs.txt"))) {
            String line;
            while ((line = rd.readLine()) != null)
                clubList.add(line.trim());

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("***********************Club List********************");
        for (String string : clubList) {
            System.out.println(string.toUpperCase() + " CLUB");
        }

    }

    public static String uniqueMember() {

        String uniqueID = generateUniqueID();
        storeMemberID(uniqueID);

        return uniqueID;

    }

    private static String generateUniqueID() {
        Set<String> existingIDs = readExistingIDs();

        String newID;

        do {
            newID = generateRandomID();

        } while (existingIDs.contains(newID));

        return newID;
    }

    private static String generateRandomID() {
        StringBuilder id = new StringBuilder();
        Random rd = new Random();

        for (int i = 0; i < ID_LENGTH; i++) {
            char digit = (char) ('O' + rd.nextInt(10));
            id.append(digit);
        }

        return id.toString();
    }

    private static Set<String> readExistingIDs() {
        Set<String> existingIDs = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("membersID.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                existingIDs.add(line.trim());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return existingIDs;
    }

    private static void storeMemberID(String newID) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("D:\\collegetuff_and_projects\\pbl2\\club_management_java\\membersID.txt", true))) {
            writer.write(newID);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(String content, File obj) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(obj, true))) {
            writer.write(content);
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Failed to add member");
            e.printStackTrace();
        }
    }

    static void addmember(String pathName) {
        membersObjFile = new File(pathName);
        StringBuilder concatString = new StringBuilder();
        ArrayList<String> arrLst = new ArrayList<>();
        String memID = uniqueMember();
        String more;
        ////////
        concatString.append(memID).append(" ");
        ///////
        System.out.println("Enter Name, Address, Department Name, Phone nummber");
        System.out.println("Enter 'exit' to stop");
        while (true) {
            String input = scanner.next();
            arrLst.add(input);

            if (input.equalsIgnoreCase("exit"))
                break;

            concatString.append(input).append(" ");
        }
        ///////

        System.out.println("Are the entered details correct");
        for (String string : arrLst) {
            System.out.println(string);
        }
        System.out.println("Enter 'Y|y' if details are correct");
        System.out.println("Enter 'N|n' if details are incorrect");
        String choice = scanner.next();

        if (choice.equals("Y") || choice.equals("y")) {
            writeToFile(concatString.toString().trim(), membersObjFile);
            System.out.println("Member details added successfully");
            System.out.println("Add more members Y|N");
            more = scanner.next().toLowerCase();
            if (more.equals("y"))
                addmember(pathName);
            else if (more.equals("n"))
                clubMenu();
            else {
                System.out.println("wrong input");
            }
        } else {
            addmember(pathName);
        }

    }

    private static String getMemDetails(String memID, String memFileName) {
        List<Map.Entry<String, List<String>>> memDetails = new ArrayList<>();
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(memFileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                String key = words[0];
                List<String> values = new ArrayList<>();

                for (int i = 1; i < words.length; i++) {
                    values.add(words[i]);
                }

                memDetails.add(new AbstractMap.SimpleEntry<>(key, values));
            }

        } catch (IOException e) {
            System.out.println("Failed to Obtain Details");
        }

        for (Map.Entry<String, List<String>> entry : memDetails) {
            String memIDKey = entry.getKey();
            if (memIDKey.equals(memID)) {
                for (String value : entry.getValue()) {
                    sb.append(value).append(" ");
                }
            }
        }

        return sb.toString();

    }

    private static List<Map.Entry<String, List<String>>> getMemDetails(String memFileName) {
        List<Map.Entry<String, List<String>>> memDetails = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(memFileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                String key = words[0];
                List<String> values = new ArrayList<>();

                for (int i = 1; i < words.length; i++) {
                    values.add(words[i]);
                }

                memDetails.add(new AbstractMap.SimpleEntry<>(key, values));
            }

        } catch (IOException e) {
            System.out.println("Failed to Obtain Details");
        }

        return memDetails;
    }

    private static Iterator<Map.Entry<String, List<String>>> deleteMemEntry(String memID,
            List<Map.Entry<String, List<String>>> memberDetails) {
        Iterator<Map.Entry<String, List<String>>> iterator = memberDetails.iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();
            String key = entry.getKey();

            if ((key.equals(memID)) && entry.getValue().isEmpty())
                iterator.remove();
        }
        return iterator;
    }

    static void deletemember() {
        Map<String, String> memDetails = new HashMap<>();
        System.out.println("Enter the club which member belongs");
        memDetails.put("clubName", scanner.next());
        System.out.println("Enter the memner Id");
        memDetails.put("memId", scanner.next());

        if (checkClubMemFile(memDetails.get("clubName") + "_members.txt")) {

            List<Map.Entry<String, List<String>>> memberDetails = getMemDetails(
                    memDetails.get("clubName") + "_members.txt");

            deleteMemEntry(memDetails.get("memId"), memberDetails);

            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(memDetails.get("clubName") + "_members.txt", false))) {

                for (Map.Entry<String, List<String>> memDtl : memberDetails) {
                    String eventString = listToString(memDtl);
                    writer.write(eventString);
                    writer.newLine();
                }
            } catch (Exception e) {

                System.out.println("Failed to Delete member");
                e.printStackTrace();
            }

        }

    }

    private static Boolean checkClubMemFile(String clubMemString) {

        ArrayList<String> clubMemFile = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("clubsMemFile.txt"))) {

            String line;
            while ((line = reader.readLine()) != null)
                clubMemFile.add(line);
        } catch (IOException e) {
            System.out.println("Failed To retrieve Club details");
            e.printStackTrace();
        }

        for (String string : clubMemFile) {
            System.out.println(string);
            if (string.equals(clubMemString)) {
                return true;
            }
        }

        return false;
    }

    static void viewClubmember() {
        String clubName, memDetails;

        System.out.println("Enter the club to which member belongs");
        clubName = scanner.next();

        String clubMemString = clubName + "_members";

        System.out.println("finalstr " + clubMemString);

        if (checkClubMemFile(clubMemString)) {
            System.out.println("Enter the Member Id");
            String memID = scanner.next();
            memDetails = getMemDetails(memID, clubMemString + ".txt");
            System.out.println("Member Details : " + memDetails);
        } else {
            System.out.println("Members List for the " + clubName + " club Does not exist");
            System.out.println("Want to ADD member (Y/N)");
            String choice = scanner.next();
            if (choice.equals("Y") || choice.equals("y")) {
                addmember(clubName);
            } else {
                clubMenu();
            }

        }

    }

    static void addevent() {
        String clubName, inpData, Line, arr[] = new String[4];
        Set<String> existingEvents = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        System.out.println("Enter the club whcih Hosts the event");
        clubName = scanner.next().toLowerCase();
        System.out.println("Enter the event Name, Date, co-ordinator, place ");
        // eventName = scanner.next().toLowerCase();

        for (int i = 0; i < 4; i++) {
            inpData = scanner.next();
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
            String choice = scanner.next();
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

    private static Iterator<Map.Entry<String, List<String>>> deleteEntries(List<Map.Entry<String, List<String>>> events,
            Map<String, String> eventDetails) {
        Iterator<Map.Entry<String, List<String>>> iterator = events.iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();
            String key = entry.getKey();
            if ((key.equals(eventDetails.get("eventName")) && checkValues(entry, eventDetails))
                    || entry.getValue().isEmpty()) {
                iterator.remove();
            }
        }
        return iterator;
    }

    private static String listToString(Map.Entry<String, List<String>> evnt) {
        StringBuilder sb = new StringBuilder();
        for (String value : evnt.getValue()) {
            sb.append(value).append(" ");
        }
        return sb.toString();
    }

    public static void deleteEvent() {
        String clubName;

        Map<String, String> eventDetails = new HashMap<>();

        System.out.println("Enter the club Name");
        clubName = scanner.next();

        List<Map.Entry<String, List<String>>> events = processWords(clubName + ".txt");

        System.out.println("Enter event and date when event was conducted");
        eventDetails.put("eventName", scanner.next());
        eventDetails.put("eventDate", scanner.next());

        deleteEntries(events, eventDetails);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(clubName + ".txt", false))) {

            for (Map.Entry<String, List<String>> evntDtl : events) {
                String eventString = listToString(evntDtl);
                writer.write(eventString);
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Failed to add member");
            e.printStackTrace();
        }
        eventMenu();
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
        String eventName = scanner.next();

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

    static void viewEvent() {

        System.out.println("Enter the Club which hosts the event");
        String clubName = scanner.next();

        System.out.println("Want to View all the Event details or Want to View particular Event");
        System.out.println("1. View All Events");
        System.out.println("2. View Particular Event");
        System.out.println("3. Exit");
        int option = scanner.nextInt();

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

}
