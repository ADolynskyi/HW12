import javax.servlet.http.HttpServletRequest;

public class TimezoneValidateFilter {
    private final String timeZone;
    private int timeShift;
    private boolean isParsed;

    public TimezoneValidateFilter(HttpServletRequest req) {
        timeZone = req.getParameter("timezone");
        parseTimezone(timeZone);
    }

    public int parseTimezone(String timeZone) {
        try {
            timeShift = Integer.parseInt(timeZone.replace("UTC", "").trim());
            isParsed = true;

        } catch (Exception e) {
            isParsed = false;

        }
        return timeShift;
    }

    public int getTimeShift() {
        return timeShift;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public boolean isTimeZoneParsed() {
        return isParsed;
    }
}
