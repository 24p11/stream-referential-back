package fr.aphp.referential.load.processor.ccam.f001;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;

import org.apache.camel.Message;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import fr.aphp.referential.load.message.ccam.f001.CcamMessage;
import io.vavr.control.Try;

import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;
import static java.lang.String.format;

public class CcamProcessor {
    public static Iterator<Row> xlsRows(File ccam) {
        return Try.withResources(() -> new FileInputStream(ccam))
                .of(CcamProcessor::getXlsRowIterator)
                .getOrElseThrow(() -> new RuntimeException(format("Unable to read '%s'", ccam.getName())));
    }

    public static Optional<CcamMessage> optionalCcamMessage(Message message) {
        Row row = message.getBody(Row.class);
        if (isValidRow(row)) {
            CcamMessage ccamMessage = ccamMessage(row)
                    .startDate(message.getHeader(VALIDITY_DATE, Date.class))
                    .build();
            return Optional.of(ccamMessage);
        } else {
            return Optional.empty();
        }
    }

    private static Iterator<Row> getXlsRowIterator(FileInputStream ccamStream) throws IOException {
        Workbook workbook = new XSSFWorkbook(ccamStream);
        Sheet secondSheet = workbook.getSheetAt(1);
        Iterator<Row> iterator = secondSheet.iterator();

        // Skip the first row
        iterator.next();
        workbook.close();
        return iterator;
    }

    private static String getCellAsString(Row row, int cellId) {
        return new DataFormatter().formatCellValue(row.getCell(cellId));
    }

    /**
     * Filter invalid row, when first cell is empty or length < 7
     */
    private static boolean isValidRow(Row row) {
        short firstCellNum = row.getFirstCellNum();
        if (0 > firstCellNum) {
            return false;
        } else {
            Cell firstCell = row.getCell(firstCellNum);
            return CellType.STRING == firstCell.getCellType() && 7 == firstCell.getStringCellValue().length();
        }
    }

    private static CcamMessage.Builder ccamMessage(Row row) {
        int firstCellNum = row.getFirstCellNum();
        Function<Integer, String> getCell = cellId -> getCellAsString(row, cellId);
        return CcamMessage.builder()
                .conceptCode(getCell.apply(firstCellNum))
                .extensionPmsi(getCell.apply(firstCellNum + 1))
                .codePmsi(getCell.apply(firstCellNum + 2))
                .conceptName(getCell.apply(firstCellNum + 5))
                .compHas(getCell.apply(firstCellNum + 6))
                .consignPmsi(getCell.apply(firstCellNum + 7))
                .modificationType(getCell.apply(firstCellNum + 8))
                .modificationVersion(getCell.apply(firstCellNum + 9))
                .rsc(getCell.apply(firstCellNum + 13))
                .ap(getCell.apply(firstCellNum + 14))
                .etm(getCell.apply(firstCellNum + 15))
                .rgt(getCell.apply(firstCellNum + 16))
                .classifying(getCell.apply(firstCellNum + 17))
                .billingList(getCell.apply(firstCellNum + 27))
                .icr(getCell.apply(firstCellNum + 28))
                .icrPrivate(getCell.apply(firstCellNum + 29))
                .icrA4(getCell.apply(firstCellNum + 30))
                .icrAnapath(getCell.apply(firstCellNum + 31))
                .icrRea(getCell.apply(firstCellNum + 32))
                .modifier(getCell.apply(firstCellNum + 34))
                .gestComp(getCell.apply(firstCellNum + 35))
                .gestCompAnes(getCell.apply(firstCellNum + 39))
                .denom(getCell.apply(firstCellNum + 39));
    }
}
