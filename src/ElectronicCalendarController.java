// File: src/calendar/ElectronicCalendarController.java

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

public class ElectronicCalendarController {
    @FXML private ChoiceBox<String> monthChoice;
    @FXML private ChoiceBox<Integer> yearChoice;
    @FXML private GridPane table;

    private final AppointmentManager manager = new AppointmentManager();

    @FXML
    public void initialize() {
        Month[] months = Month.values();
        for (Month m : months) {
            monthChoice.getItems().add(m.getDisplayName(TextStyle.FULL, Locale.getDefault()));
        }
        monthChoice.setValue(months[LocalDate.now().getMonthValue() - 1]
                .getDisplayName(TextStyle.FULL, Locale.getDefault()));

        int currentYear = LocalDate.now().getYear();
        for (int y = currentYear - 10; y <= currentYear + 10; y++) {
            yearChoice.getItems().add(y);
        }
        yearChoice.setValue(currentYear);

        monthChoice.setOnAction(e -> refresh());
        yearChoice.setOnAction(e -> refresh());

        buildHeaders();
        refresh();
    }

    private void buildHeaders() {
        String[] days = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        for (int col = 0; col < 7; col++) {
            Text t = new Text(days[col]);
            table.add(t, col, 0);
            GridPane.setHalignment(t, HPos.CENTER);
        }
    }

    private void refresh() {
        table.getChildren().removeIf(node -> {
            Integer row = GridPane.getRowIndex(node);
            return row != null && row > 0;
        });

        int year = yearChoice.getValue();
        int month = monthChoice.getItems().indexOf(monthChoice.getValue()) + 1;
        LocalDate first = LocalDate.of(year, month, 1);
        int offset = first.getDayOfWeek().getValue() % 7; // Sunday=0
        int daysInMonth = first.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            int col = (offset + day - 1) % 7;
            int row = (offset + day - 1) / 7 + 1;
            Button btn = new Button(String.valueOf(day));
            btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            String key = String.format("%04d-%02d-%02d", year, month, day);
            btn.setOnAction(e -> openDialog(key));
            table.add(btn, col, row);
        }
    }

    private void openDialog(String key) {
        List<String> items = manager.getAppointments(key);
        TextArea area = new TextArea();
        items.forEach(i -> area.appendText(i + "\n"));

        Alert alert = new Alert(AlertType.NONE, "", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Appointments for " + key);
        ScrollPane sp = new ScrollPane(area);
        sp.setPrefSize(300,200);
        alert.getDialogPane().setContent(sp);
        Optional<ButtonType> res = alert.showAndWait();
        if (res.orElse(ButtonType.CANCEL) == ButtonType.OK) {
            String[] lines = area.getText().split("\n");
            List<String> newList = new ArrayList<>();
            for (String l : lines) if (!l.trim().isEmpty()) newList.add(l);
            manager.saveAppointments(key, newList);
        }
    }
}
