import javax.servlet.http.HttpServletRequest;

public class TimezoneValidateFilter {
    private String timeZone;
    private int timeShift;
    private boolean isParsed;
    private String error;

    public TimezoneValidateFilter(HttpServletRequest req) {
        timeZone = req.getParameter("timezone");
        if (timeZone == null || timeZone.equals("")) {
            timeZone = "UTC+0";
            timeShift = 0;
        }

        try {
            timeShift = Integer.parseInt(timeZone.replace("UTC", "").trim());
            isParsed = true;
            if (timeShift >= 0) {
                timeZone = "UTC+" + timeShift;
            } else {
                timeZone = "UTC" + timeShift;
            }
        } catch (Exception e) {
            isParsed = false;
            error = "Invalid Timezone";
        }
    }

    public String getError() {
        return error;
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
