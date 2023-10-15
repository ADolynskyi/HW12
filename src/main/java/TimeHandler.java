import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimeHandler {
    public String getTimeWithZone(int hoursShift) {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime customTime = now.plusHours(hoursShift);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return customTime.format(formatter);
    }
}
