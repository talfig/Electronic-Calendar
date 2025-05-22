// File: AppointmentManager.java
import java.util.*;

public class AppointmentManager {
    private final Map<String, List<String>> appointments = new TreeMap<>();

    public List<String> getAppointments(String dateKey) {
        return appointments.getOrDefault(dateKey, new ArrayList<>());
    }

    public void saveAppointments(String dateKey, List<String> items) {
        if (items.isEmpty()) appointments.remove(dateKey);
        else appointments.put(dateKey, new ArrayList<>(items));
    }
}