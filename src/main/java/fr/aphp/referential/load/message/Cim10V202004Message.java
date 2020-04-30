package fr.aphp.referential.load.message;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import fr.aphp.referential.load.domain.type.SourceType;

import static fr.aphp.referential.load.domain.type.SourceType.CIM10;

@CsvRecord(separator = "\\|")
public final class Cim10V202004Message extends Message {
    private final SourceType type = CIM10;

    @DataField(pos = 1, trim = true)
    private String domainId;

    @DataField(pos = 2, trim = true)
    private String typeMcoHad;

    @DataField(pos = 3, trim = true)
    private String ssr;

    @DataField(pos = 4, trim = true)
    private String psy;

    @DataField(pos = 5, trim = true)
    private String shortLabel;

    @DataField(pos = 6, trim = true)
    private String label;
}
