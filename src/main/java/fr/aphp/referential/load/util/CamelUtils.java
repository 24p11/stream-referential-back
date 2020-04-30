package fr.aphp.referential.load.util;

public final class CamelUtils {
    // ROUTES
    public static final String INPUT_DIRECTORY_ROUTE_ID = "input-directory-route";
    public static final String CIM10_ROUTE_ID = "cim10-route";

    // HEADERS
    public static final String SOURCE_TYPE = "sourceType";

    public static String toCamelCron(String cron) {
        return cron.replace(' ', '+');
    }
}
