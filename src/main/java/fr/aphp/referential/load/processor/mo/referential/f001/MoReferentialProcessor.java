package fr.aphp.referential.load.processor.mo.referential.f001;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;

import org.apache.camel.Message;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import fr.aphp.referential.load.domain.type.mo.referential.MoReferentialEventType;
import fr.aphp.referential.load.message.mo.referential.f001.MoReferentialMessage;
import io.vavr.control.Try;

import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class MoReferentialProcessor {
    public static Iterator<Row> xlsRows(File mo) {
        return Try.withResources(() -> new FileInputStream(mo))
                .of(MoReferentialProcessor::getXlsRowIterator)
                .get();
    }

    public static Optional<MoReferentialMessage> optionalMoReferentialMessage(Message message) {
        Row row = message.getBody(Row.class);
        if (isValidRow(row)) {
            int firstCellNum = row.getFirstCellNum();
            Function<Integer, String> getCell = cellId -> getCellAsString(row, cellId);
            Date startDate = toDateOptional(getCell.apply(firstCellNum)).orElse(toDateOptional(getCell.apply(firstCellNum + 1))
                    .orElse(message.getHeader(VALIDITY_DATE, Date.class)));
            MoReferentialMessage moReferentialMessage = MoReferentialMessage.builder()
                    .startDate(startDate)
                    .ucdLabel(getCell.apply(firstCellNum + 2))
                    .ucd7(getCell.apply(firstCellNum + 3))
                    .ucd13(getCell.apply(firstCellNum + 4))
                    .moEventType(MoReferentialEventType.fromIdentifier(getCell.apply(firstCellNum + 5)))
                    .price(getCell.apply(firstCellNum + 6))
                    .priceTtc(getCell.apply(firstCellNum + 7))
                    .dci(getCell.apply(firstCellNum + 11))
                    .atc(getCell.apply(firstCellNum + 12))
                    .build();
            return Optional.of(moReferentialMessage);
        } else {
            return Optional.empty();
        }
    }

    private static Iterator<Row> getXlsRowIterator(FileInputStream ccamStream) throws IOException {
        Workbook workbook = new HSSFWorkbook(ccamStream);
        Sheet secondSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = secondSheet.iterator();

        // Skip the first row
        iterator.next();

        return iterator;
    }

    private static boolean isValidRow(Row row) {
        short firstCellNum = row.getFirstCellNum();
        if (0 > firstCellNum) {
            return false;
        } else {
            Optional<Cell> labelCell = Optional.ofNullable(row.getCell(firstCellNum));
            return labelCell.isPresent() && CellType.NUMERIC == labelCell.get().getCellType();
        }
    }

    private static String getCellAsString(Row row, int cellId) {
        return new DataFormatter().formatCellValue(row.getCell(cellId));
    }

    private static Optional<Date> toDateOptional(String date) {
        return Try.of(() -> new SimpleDateFormat("M/d/yyyy").parse(date)).toJavaOptional();
    }
}
