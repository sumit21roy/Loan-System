package com.zopa.loan;

import com.zopa.loan.model.Lender;
import com.zopa.loan.model.Quote;
import lombok.NonNull;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;

/**
 * A command line based tool to produce a quote based on a csv file
 * containing a list of lenders and a loan amount.
 *
 * @author  Sumit Roy
 */
public class GetQuoteApp {

    public static void main(String[] args) {

        checkThereAreExactlyTwoArgumentsOrExit1(args);

        List<Lender> lenders =
                getLendersOrExit1(args[0]);

        final Integer loanAmount =
                getLoanAmountOrExit1(args[1]);

        tryToProvideAQuoteAndExit0(lenders, loanAmount);
    }

    private static void tryToProvideAQuoteAndExit0(
            @NonNull List<Lender> lenders,
            @NonNull Integer loanAmount
    ) {

        final QuoteService quoteService36MonthsBestRate =
                new QuoteServiceFor36Months(
                        ContributionsCalculator.bestRate
                );

        final Optional<Quote> optQuote =
                quoteService36MonthsBestRate.calculateQuote(loanAmount, lenders);

        if (optQuote.isPresent())
            displayQuote(loanAmount, optQuote.get());
        else
            System.out.println("Sorry, currently we can't provide a quote at that time.");

        System.exit(0);
    }

    private static void displayQuote(
            @NonNull Integer loanAmount,
            Quote quote
    ) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.UK);
        System.out.println("Requested amount: " + nf.format(loanAmount));
        System.out.println("Rate: " + quote.getRate() + "%");
        System.out.println("Monthly repayment: " + nf.format(quote.getMonthlyRepayment()));
        System.out.println("total repayment: " + nf.format(quote.getTotalRepayment()));
    }

    private static Integer getLoanAmountOrExit1(@NonNull String loanAmountParam) {

        Integer loanAmount;

        loanAmount = tryParseStringToInteger(loanAmountParam);

        if (
                null == loanAmount ||
                        loanAmount < 1000 ||
                        loanAmount > 15000 ||
                        ((loanAmount % 100) != 0)
                ) {
            System.out.println("Loan amount should be between 1000 and 15000 (100 increments).");
            System.out.println("Usage: GetQuoteApp [path_to_csv_file] [loan_amount]");
            System.exit(1);
        }

        return loanAmount;
    }

    private static Integer tryParseStringToInteger(@NonNull String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static List<Lender> getLendersOrExit1(@NonNull String cvsFilePathAsString) {

        List<Lender> lenders = new ArrayList<>();

        try {
            lenders = new CsvFileToLendersService().processCsvFile(cvsFilePathAsString);
        } catch (IOException e) {
            System.out.println("The specified file doesn't exist or can't be read.");
            System.out.println("Usage: GetQuoteApp [path_to_csv_file] [loan_amount]");
            System.exit(1);
        }

        return lenders;
    }

    private static void checkThereAreExactlyTwoArgumentsOrExit1(@NonNull String[] args) {

        if (args.length != 2) {
            System.out.println("Usage: GetQuoteApp [path_to_csv_file] [loan_amount]");
            System.exit(1);
        }
    }
}