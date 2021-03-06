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
    // GHMGHS
    public static final String GHMGHS_ROUTE_ID = "ghmghs-route";
    public static final String GHMGHS_F001_ROUTE_ID = "ghmghs-f001-route";
    public static final String GHMGHS_F001_CONCEPT_ROUTE_ID = "ghmghs-f001-concept-route";
    public static final String GHMGHS_F001_METADATA_ROUTE_ID = "ghmghs-f001-metadata-route";
    public static final String GHMGHS_F001_METADATA_PROCESSOR = "ghmghs-f001-metadata-processor";
    public static final String GHMGHS_F002_ROUTE_ID = "ghmghs-f002-route";
    public static final String GHMGHS_F002_CONCEPT_ROUTE_ID = "ghmghs-f002-concept-route";
    public static final String GHMGHS_F002_METADATA_ROUTE_ID = "ghmghs-f002-metadata-route";
    public static final String GHMGHS_F002_METADATA_PROCESSOR = "ghmghs-f002-metadata-processor";
    // DMI
    public static final String DMI_ROUTE_ID = "dmi-route";
    public static final String DMI_F001_ROUTE_ID = "dmi-f001-route";
    public static final String DMI_F001_CONCEPT_ROUTE_ID = "dmi-f001-concept-route";
    public static final String DMI_F001_METADATA_ROUTE_ID = "dmi-f001-metadata-route";
    public static final String DMI_F001_METADATA_PROCESSOR = "dmi-f001-metadata-processor";
    // MO REFERENTIAL
    public static final String MO_REFERENTIAL_ROUTE_ID = "mo-referential-route";
    public static final String MO_REFERENTIAL_F001_ROUTE_ID = "mo-referential-f001-route";
    public static final String MO_REFERENTIAL_F001_CONCEPT_ROUTE_ID = "mo-referential-f001-concept-route";
    public static final String MO_REFERENTIAL_F001_METADATA_ROUTE_ID = "mo-referential-f001-metadata-route";
    public static final String MO_REFERENTIAL_F001_METADATA_PROCESSOR = "mo-referential-f001-metadata-processor";
    // MO INDICATION
    public static final String MO_INDICATION_ROUTE_ID = "mo-indication-route";
    public static final String MO_INDICATION_F001_ROUTE_ID = "mo-indication-f001-route";
    public static final String MO_INDICATION_F001_CONCEPT_ROUTE_ID = "mo-indication-f001-concept-route";
    public static final String MO_INDICATION_F001_METADATA_ROUTE_ID = "mo-indication-f001-metadata-route";
    public static final String MO_INDICATION_F001_METADATA_PROCESSOR = "mo-indication-f001-metadata-processor";
    // LIST
    public static final String LIST_ROUTE_ID = "list-route";
    public static final String LIST_METADATA_ROUTE_REGEX = ".*(list-([a-z0-9]+)-metadata-route).*";
    public static final String LIST_F001_ROUTE_ID = "list-f001-route";
    public static final String LIST_F001_METADATA_ROUTE_ID = "list-f001-metadata-route";
    public static final String LIST_F001_METADATA_PROCESSOR = "list-f001-metadata-processor";
    public static final String LIST_F002_ROUTE_ID = "list-f002-route";
    public static final String LIST_F002_METADATA_ROUTE_ID = "list-f002-metadata-route";
    public static final String LIST_F002_METADATA_PROCESSOR = "list-f002-metadata-processor";
    public static final String LIST_F003_ROUTE_ID = "list-f003-route";
    public static final String LIST_F003_METADATA_ROUTE_ID = "list-f003-metadata-route";
    public static final String LIST_F003_METADATA_PROCESSOR = "list-f003-metadata-processor";
    // DB
    public static final String DISPATCH_ROUTE_ID = "to-db-dispatcher-route";
    public static final String TO_DB_CONCEPT_ROUTE_ID = "to-db-concept-route";
    public static final String TO_DB_CONCEPT_RELATIONSHIP_ROUTE_ID = "to-db-concept-relationship-route";
    public static final String TO_DB_METADATA_ROUTE_ID = "to-db-metadata-route";
    public static final String TO_DB_METADATA_END_DATE_ROUTE_ID = "to-db-metadata-end-date-route";
    public static final String TO_DB_METADATA_DICTIONARY_ROUTE_ID = "to-db-metadata-dictionary-route";
    public static final String TO_DB_LIST_DICTIONARY_ROUTE_ID = "to-db-list-dictionary-route";

    // HEADERS
    public static final String SOURCE_TYPE = "sourceType";
    public static final String FORMAT = "format";
    public static final String UPDATE_CONCEPT_BEAN = "updateConceptBean";
    public static final String VALIDITY_DATE = "validityDate";
    public static final String FILE_EXT_SEPARATOR = "_";
    public static final String UTILS_SPLIT_COMPLETE = "utilSplitComplete";

    // CONST
    public static final String DISABLE_END_DATE = "4000-12-31";
    public static final String CONCEPT_TABLE = "concept";
    public static final String METADATA_TABLE = "metadata";

    public static String toCamelCron(String cron) {
        return cron.replace(' ', '+');
    }
}
