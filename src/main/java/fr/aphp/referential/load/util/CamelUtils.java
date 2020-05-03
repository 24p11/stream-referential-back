package fr.aphp.referential.load.util;

public final class CamelUtils {
    // ROUTES
    public static final String INPUT_DIRECTORY_ROUTE_ID = "input-directory-route";
    public static final String CIM10_ROUTE_ID = "cim10-route";
    public static final String CCAM_ROUTE_ID = "ccam-route";
    public static final String TO_DB_REFERENTIAL_ROUTE_ID = "to-db-referential-route";

    // HEADERS
    public static final String UPDATE_REFERENTIAL_BEAN = "updateReferentialBean";
    public static final String VALIDITY_DATE = "validityDate";
    public static final String FILE_EXT_SEPARATOR = "_";

    // CONST
    public static final String DISABLE_END_DATE = "4000-12-31";

    public static String toCamelCron(String cron) {
        return cron.replace(' ', '+');
    }
}
