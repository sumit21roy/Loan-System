package com.zopa.loan;

import com.zopa.loan.model.Lender;
import lombok.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>Services that produces a list of {@link Lender} instances
 * based on a CSV file.</p>
 *
 * @author Sumit Roy
 */
class CsvFileToLendersService {

    private static final String SEPARATOR = ",";

    /**
     * <p>Parses a CSV file, and returns a list of {@link Lender} instances.</p>
     *
     * @param filePath Path to the csv file.
     * @return lenders List of {@link Lender} instances
     * @throws IOException if a problem has been produced reading the file.
     */
    List<Lender> processCsvFile(@NonNull String filePath) throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {

            return
                br.lines()
                    .skip(1)
                    .map(lineToLender)
                    .collect(Collectors.toList());
        }
    }

    final private Function<String, Lender> lineToLender =
        (line) -> {
            String[] tokens = line.split(SEPARATOR);

            return
                new Lender(
                    tokens[0],
                    new BigDecimal(tokens[1]),
                    new Integer(tokens[2])
                );
        };
}
