package fr.aphp.referential.load.util;

public final class CamelUtils {
    // ROUTES
    public static final String INPUT_DIRECTORY_ROUTE_ID = "input-directory-route";

    public static String toCamelCron(String cron) {
        return cron.replace(' ', '+');
    }
}
