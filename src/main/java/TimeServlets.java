import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet("/time")
public class TimeServlets extends HttpServlet {

    private static final String LAST_TIMEZONE = "lastTimezone";
    private TemplateEngine engine;

    @Override
    public void init() {
        engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("D:\\Java study\\JavaCourse\\JavaDev\\HW12\\Servlets\\templates\\");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        TimezoneValidateFilter timezoneValidateFilter = new TimezoneValidateFilter(req);
        TimeHandler timeHandler = new TimeHandler();
        Cookie[] cookies = req.getCookies();
        Map<String, String> cookiesMap = new HashMap<>();
        String timezone = "UTC 0";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookiesMap.put(cookie.getName(), cookie.getValue());
            }
        }

        String formattedDateTime;
        if (timezoneValidateFilter.isTimeZoneParsed()) {
            int userTZ = timezoneValidateFilter.getTimeShift();
            formattedDateTime = timeHandler.getTimeWithZone(userTZ);
            Cookie cookie = new Cookie(LAST_TIMEZONE, timezoneValidateFilter.getTimeZone().replace(" ", ""));
            cookie.setHttpOnly(true);
            resp.addCookie(cookie);
            timezone = timezoneValidateFilter.getTimeZone();
        } else {
            if (cookiesMap.get(LAST_TIMEZONE) == null) {
                formattedDateTime = timeHandler.getTimeWithZone(0);
            } else {
                int timeShift = timezoneValidateFilter.parseTimezone(cookiesMap.get(LAST_TIMEZONE));
                formattedDateTime = timeHandler.getTimeWithZone(timeShift);
                timezone = cookiesMap.get(LAST_TIMEZONE);
            }
        }

        resp.setHeader("ContentType", "html/text");
        resp.setCharacterEncoding("UTF-8");

        Map<String, Object> params = new LinkedHashMap<>();
        params.put("time", formattedDateTime);
        params.put("timezone", timezone);

        Context simleContext = new Context(
                req.getLocale(),
                params
        );

        engine.process("time", simleContext, resp.getWriter());
        resp.getWriter().close();
    }

}

