package fr.aphp.referential.load.util;

public final class CamelUtils {
    // ROUTES
    public static final String INPUT_DIRECTORY_ROUTE_ID = "input-directory-route";
    // CIM10
    public static final String CIM10_ROUTE_ID = "cim10-route";
    public static final String CIM10_F001_ROUTE_ID = "cim10-f001-route";
    public static final String CIM10_F001_CONCEPT_ROUTE_ID = "cim10-f001-concept-route";
    public static final String CIM10_F001_METADATA_ROUTE_ID = "cim10-f001-metadata-route";
    public static final String CIM10_F001_METADATA_PROCESSOR = "cim10-f001-metadata-processor";
    // CCAM
    public static final String CCAM_ROUTE_ID = "ccam-route";
    public static final String CCAM_F001_ROUTE_ID = "ccam-f001-route";
    public static final String CCAM_F001_CONCEPT_ROUTE_ID = "ccam-f001-concept-route";
    public static final String CCAM_F001_METADATA_ROUTE_ID = "ccam-f001-metadata-route";
    public static final String CCAM_F001_METADATA_PROCESSOR = "ccam-f001-metadata-processor";
    public static final String CCAM_F002_ROUTE_ID = "ccam-f002-route";
    public static final String CCAM_F002_CONCEPT_ROUTE_ID = "ccam-f002-concept-route";
    public static final String CCAM_F002_METADATA_ROUTE_ID = "ccam-f002-metadata-route";
    public static final String CCAM_F002_METADATA_PROCESSOR = "ccam-f002-metadata-processor";
    public static final String CCAM_F003_ROUTE_ID = "ccam-f003-route";
    public static final String CCAM_F003_METADATA_ROUTE_ID = "ccam-f003-metadata-route";
    public static final String CCAM_F003_METADATA_PROCESSOR = "ccam-f003-metadata-processor";
    // DB
    public static final String TO_DB_DISPATCHER_ROUTE_ID = "to-db-dispatcher-route";
    public static final String TO_DB_CONCEPT_ROUTE_ID = "to-db-concept-route";
    public static final String TO_DB_METADATA_ROUTE_ID = "to-db-metadata-route";
    // END
    public static final String END_PROCESSING_ROUTE_ID = "end-processing-route";

    // HEADERS
    public static final String SOURCE_TYPE = "sourceType";
    public static final String FORMAT = "format";
    public static final String UPDATE_CONCEPT_BEAN = "updateConceptBean";
    public static final String VALIDITY_DATE = "validityDate";
    public static final String FILE_EXT_SEPARATOR = "_";
    public static final String FILE_SPLIT_COMPLETE = "fileSplitComplete";

    // CONST
    public static final String DISABLE_END_DATE = "4000-12-31";
    public static final String CONCEPT_TABLE = "concept";
    public static final String METADATA_TABLE = "metadata";

    public static String toCamelCron(String cron) {
        return cron.replace(' ', '+');
    }
}
