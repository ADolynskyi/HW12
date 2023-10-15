
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/time")
public class TimeServlets extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {

        TimezoneValidateFilter timezoneValidateFilter = new TimezoneValidateFilter(req);
        TimeHandler timeHandler = new TimeHandler();
        String httpResponse;

        if (timezoneValidateFilter.isTimeZoneParsed()) {
            int userTZ = timezoneValidateFilter.getTimeShift();
            String formattedDateTime = timeHandler.getTimeWithZone(userTZ);
            httpResponse = "<h1> " + formattedDateTime + " " + timezoneValidateFilter.getTimeZone() + " </h1>";
        } else {
            httpResponse = "<h1>" + timezoneValidateFilter.getError() + "</h1>";
            resp.setStatus(400);
        }
        resp.setHeader("ContentType", "html/text");
        resp.setCharacterEncoding("UTF-8");
        try {
            resp.getWriter().println(httpResponse);
            resp.getWriter().flush();
        }catch (IOException e){
            System.out.println("IO Exception");
        }

    }
}
