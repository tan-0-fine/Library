package Server;

import java.io.*;
import java.util.*;

public class UserManager {
    private Map<String, String> users = new HashMap<>();
    private String filePath;

    public UserManager(String filePath) {
        this.filePath = filePath;
        loadUsers();
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    users.put(parts[0], parts[1]);
                }
            }
        } catch (IOException e) {
            System.out.println("Chưa có file users.txt, tạo mới.");
        }
    }

    private void saveUsers() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                writer.println(entry.getKey() + ":" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }

    public boolean register(String username, String password) {
        if (users.containsKey(username)) return false;
        users.put(username, password);
        saveUsers();
        return true;
    }

    public boolean resetPassword(String username, String newPassword) {
        if (!users.containsKey(username)) return false;
        users.put(username, newPassword);
        saveUsers();
        return true;
    }

    public boolean validate(String username, String password) {
        return authenticate(username, password);
}

}