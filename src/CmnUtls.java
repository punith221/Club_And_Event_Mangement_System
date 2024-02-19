import java.util.*;
import java.io.*;

public class CmnUtls {

    public static void writeToFile(String content, File obj) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(obj, true))) {
            writer.write(content);
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Failed to add member");
            e.printStackTrace();
        }
    }

    public static String listToStringClubs(Map.Entry<String, List<String>> evnt) {
        StringBuilder sb = new StringBuilder();
        sb.append(evnt.getKey()).append(" ");
        for (String value : evnt.getValue()) {
            sb.append(value).append(" ");
        }
        return sb.toString().trim();
    }

    public static String listToStringEvents(Map.Entry<String, List<String>> evnt) {
        StringBuilder sb = new StringBuilder();

        for (String value : evnt.getValue()) {
            sb.append(value).append(" ");
        }
        return sb.toString().trim();
    }
}
