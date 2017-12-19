package com.zopa.loan;

import com.zopa.loan.model.Lender;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class CsvFileToLendersServiceTest extends BaseTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void shouldCorrectlyReadCsvFile() throws IOException {

        // Given: CsvFileToLendersService and a path to a csv file containing a list of lenders
        final String testFilePath = createTestFileAndGetPath();
        final CsvFileToLendersService csvFileToLendersService = new CsvFileToLendersService();

        // When: processing csv
        final List<Lender> lenders = csvFileToLendersService.processCsvFile(testFilePath);
        final List<Lender> csvPoolLenders = CsvFilePoolLenders();

        // Then: Should return expected list of lenders
        assertThat(lenders.toString(), is(csvPoolLenders.toString()));

    }


    private List CsvFilePoolLenders() {
        List<Lender> csvPoolOfLenders = new ArrayList<Lender>();

        csvPoolOfLenders.add(new Lender("Bob",    BigDecimal.valueOf(0.075D),640));
        csvPoolOfLenders.add(new Lender("Jane",   BigDecimal.valueOf(0.069D),480));
        csvPoolOfLenders.add(new Lender("Fred",    BigDecimal.valueOf(0.071D),520));
        csvPoolOfLenders.add(new Lender("Mary",   BigDecimal.valueOf(0.104D),170));
        csvPoolOfLenders.add(new Lender("John",   BigDecimal.valueOf(0.081D),320));
        csvPoolOfLenders.add(new Lender("Dave",   BigDecimal.valueOf(0.074D),10));
        csvPoolOfLenders.add(new Lender("Angela",   BigDecimal.valueOf(0.071D),60));

        return csvPoolOfLenders;
    }

    private String createTestFileAndGetPath() throws IOException {

        File file = folder.newFile("market.csv");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write("Lender,Rate,Available");
            bw.newLine();
            bw.write("Bob,0.075,640");
            bw.newLine();
            bw.write("Jane,0.069,480");
            bw.newLine();
            bw.write("Fred,0.071,520");
            bw.newLine();
            bw.write("Mary,0.104,170");
            bw.newLine();
            bw.write("John,0.081,320");
            bw.newLine();
            bw.write("Dave,0.074,140");
            bw.newLine();
            bw.write("Angela,0.071,60");
            bw.close();
        }

        return file.getAbsolutePath();
    }
}
