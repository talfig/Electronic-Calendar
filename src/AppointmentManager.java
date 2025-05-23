import java.util.*;

/**
 * Manages appointments organized by date.
 * Appointments are stored in a TreeMap to maintain chronological order.
 * Each date key maps to a list of appointment descriptions (strings).
 */
public class AppointmentManager {
    // A sorted map from date strings (e.g., "2025-05-23") to a list of appointment entries
    private final Map<String, List<String>> appointments = new TreeMap<>();

    /**
     * Retrieves a list of appointments for a given date.
     * If no appointments are found for the date, an empty list is returned.
     *
     * @param dateKey the date key in string format (e.g., "2025-05-23")
     * @return a list of appointment descriptions for that date
     */
    public List<String> getAppointments(String dateKey) {
        return appointments.getOrDefault(dateKey, new ArrayList<>());
    }

    /**
     * Saves or updates the list of appointments for the given date.
     * If the provided list is empty, the entry for the date is removed.
     *
     * @param dateKey the date key in string format (e.g., "2025-05-23")
     * @param items a list of appointment descriptions for that date
     */
    public void saveAppointments(String dateKey, List<String> items) {
        if (items.isEmpty()) appointments.remove(dateKey);
        else appointments.put(dateKey, new ArrayList<>(items));
    }
}