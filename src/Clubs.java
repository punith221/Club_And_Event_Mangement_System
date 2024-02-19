import java.util.*;
import java.io.*;

public class Clubs {

    static Scanner sc = new Scanner(System.in);
    static File membersObjFile;
    private static final int ID_LENGTH = 5;

    public static void clubMenu() {
        int choice01;

        while (true) {
            System.out.println("________________ CLUB MENU _________________");
            System.out.print("1. Create Club\n2. View clubs\n3. Add members for the club\n" + //
                    "4. View Club Members\n5. Delete Club members \n6.Exit\n");
            choice01 = sc.nextInt();
            switch (choice01) {
                case 1:
                    create_clubs();
                    break;

                case 2:
                    viewClubList();
                    break;

                case 3:
                    checkClub();
                    break;

                case 4:
                    viewClubmember();
                    break;

                case 5:
                    deletemember();
                    break;

                default:
                    return;
            }
        }

    }

    public static void create_clubs() {
        System.out.println("Enter the name of the club");
        String clubName = sc.next().toLowerCase();

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
                CmnUtls.writeToFile(clubName, new File("clubs.txt"));
                File clubObj = new File(clubName + ".txt");
                clubObj.createNewFile();
                System.out.println("File Created Successfully");
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

    ///////////////////////////// begining of add_member proce ///

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
            String input = sc.next();
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
        String choice = sc.next();

        if (choice.equals("Y") || choice.equals("y")) {
            CmnUtls.writeToFile(concatString.toString().trim(), membersObjFile);
            System.out.println("Member details added successfully");
            System.out.println("Add more members Y|N");
            more = sc.next().toLowerCase();
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

    public static String uniqueMember() {

        String uniqueID = generateUniqueID();
        storeMemberID(uniqueID);

        return uniqueID;

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
                new FileWriter("membersID.txt", true))) {
            writer.write(newID);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static void checkClub() {
        String clubName, fileName = "_members";
        Set<String> existingClubs = new HashSet<>();

        while (true) {
            System.out.println("enter the club name ");
            clubName = sc.next();

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
                String inp = sc.next();
                if (inp.toLowerCase().equals("yes")) {
                    create_clubs();
                    checkClub();
                } else {
                    return;
                }
            }

            try {
                CmnUtls.writeToFile(clubName + fileName, new File("clubsMemFile.txt"));
                File obj = new File(clubName + fileName + ".txt");
                obj.createNewFile();
                addmember(clubName + fileName + ".txt");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    ///////////////// view club procedure //////////

    static void viewClubmember() {
        String clubName, memDetails;

        System.out.println("Enter the club to which member belongs");
        clubName = sc.next();

        String clubMemString = clubName + "_members";

        if (checkClubMemFile(clubMemString)) {
            System.out.println("Enter the Member Id");
            String memID = sc.next();
            memDetails = getMemDetails(memID, clubMemString + ".txt");
            System.out.println("Member Details : " + memDetails);
        } else {
            System.out.println("Members List for the " + clubName + " club Does not exist");
            System.out.println("Want to ADD member (Y/N)");
            String choice = sc.next();
            if (choice.equals("Y") || choice.equals("y")) {
                addmember(clubName);
            } else {
                clubMenu();
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
            if (string.equals(clubMemString)) {
                return true;
            }
        }

        return false;
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

    /////////////////////////// delete club members//////////////

    static void deletemember() {
        Map<String, String> memDetails = new HashMap<>();
        System.out.println("Enter the club which member belongs");
        memDetails.put("clubName", sc.next());
        System.out.println("Enter the memner Id");
        memDetails.put("memId", sc.next());

        if (checkClubMemFile(memDetails.get("clubName") + "_members")) {

            List<Map.Entry<String, List<String>>> memberDetails = getMemDetails(
                    memDetails.get("clubName") + "_members.txt");

            for (Map.Entry<String, List<String>> entry : memberDetails) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }

            deleteMemEntry(memDetails.get("memId"), memberDetails);

            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(memDetails.get("clubName") + "_members.txt", false))) {

                for (Map.Entry<String, List<String>> memDtl : memberDetails) {
                    String eventString = CmnUtls.listToStringClubs(memDtl);
                    writer.write(eventString);
                    writer.newLine();
                }
            } catch (Exception e) {

                System.out.println("Failed to Delete member");
                e.printStackTrace();
            }

        } else {
            System.out.println("Members List for the " + memDetails.get("clubName") + " club does not exist");
            System.out.println("Want to add member (Y/N)");
            String choice = sc.next();
            if (choice.equalsIgnoreCase("Y")) {
                addmember(memDetails.get("clubName") + "_members.txt");
            } else {
                clubMenu();
            }
        }

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

    private static void deleteMemEntry(String memID,
            List<Map.Entry<String, List<String>>> memberDetails) {
        Iterator<Map.Entry<String, List<String>>> iterator = memberDetails.iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, List<String>> entry = iterator.next();
            String key = entry.getKey();

            if (key.equals(memID)) {
                iterator.remove();
                break;
            }

        }
        for (Map.Entry<String, List<String>> entry : memberDetails) {
            System.out.println(entry.getKey() + entry.getValue());
        }

    }
}
