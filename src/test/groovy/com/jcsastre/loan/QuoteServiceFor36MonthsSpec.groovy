package com.jcsastre.loan

import com.zopa.loan.ContributionsCalculator
import com.zopa.loan.QuoteServiceFor36Months
import com.zopa.loan.model.Contribution
import com.zopa.loan.model.Lender
import com.zopa.loan.model.Quote
import spock.lang.Shared
import spock.lang.Specification

class QuoteServiceFor36MonthsSpec extends Specification {

    static final int LOAN_AMOUNT_OF_1000   = 1000
    static final int LOAN_AMOUNT_OF_1060   = 1060
    static final int LOAN_AMOUNT_OF_1070   = 1070
    static final int LOAN_AMOUNT_OF_500000 = 500000

    @Shared
    private static Optional<Quote> expectedOptQuoteForLoanOf1000,
                                   expectedOptQuoteForLoanOf1060,
                                   expectedOptQuoteForLoanOf1070,
                                   expectedOptQuoteForLoanOf500000

    @Shared
    List<Lender> poolOfLenders

    @Shared
    Optional<List<Contribution>> expectedOptionalContributionsFor1000Loan,
                                 expectedOptionalContributionsFor1060Loan,
                                 expectedOptionalContributionsFor1070Loan,
                                 expectedOptionalContributionsFor500000Loan

    @Shared
    ContributionsCalculator contributionsCalculator

    def setupSpec() {

        def lenderBob    = new Lender("Bob",    BigDecimal.valueOf(0.075D),640)
        def lenderJane   = new Lender("Jane",   BigDecimal.valueOf(0.069D),480)
        def lenderFred   = new Lender("Fred",   BigDecimal.valueOf(0.071D),520)
        def lenderMary   = new Lender("Mary",   BigDecimal.valueOf(0.104D),170)
        def lenderJohn   = new Lender("John",   BigDecimal.valueOf(0.081D),320)
        def lenderDave   = new Lender("Dave",   BigDecimal.valueOf(0.074D),140)
        def lenderAngela = new Lender("Angela", BigDecimal.valueOf(0.071D),60)

        poolOfLenders = [lenderBob, lenderJane, lenderFred, lenderMary, lenderJohn, lenderDave, lenderAngela]

        expectedOptQuoteForLoanOf1000 =
            Optional.of(
                new Quote(
                    new BigDecimal("7.0"),
                    new BigDecimal("30.78"),
                    new BigDecimal("1108.10")
                )
            )

        expectedOptQuoteForLoanOf1060 =
            Optional.of(
                new Quote(
                    new BigDecimal("7.0"),
                    new BigDecimal("32.63"),
                    new BigDecimal("1174.68")
                )
            )

        expectedOptQuoteForLoanOf1070 =
            Optional.of(
                new Quote(
                    new BigDecimal("7.0"),
                    new BigDecimal("32.94"),
                    new BigDecimal("1185.82")
                )
            )

        expectedOptQuoteForLoanOf500000 =
            Optional.empty()

        expectedOptionalContributionsFor1000Loan =
                Optional.of([
                        new Contribution(lenderJane, 480),
                        new Contribution(lenderFred, 520)
                ])

        expectedOptionalContributionsFor1060Loan =
                Optional.of([
                        new Contribution(lenderJane, 480),
                        new Contribution(lenderFred, 520),
                        new Contribution(lenderAngela, 60)
                ])

        expectedOptionalContributionsFor1070Loan =
                Optional.of([
                        new Contribution(lenderJane, 480),
                        new Contribution(lenderFred, 520),
                        new Contribution(lenderAngela, 60),
                        new Contribution(lenderDave, 10)
                ])

        expectedOptionalContributionsFor500000Loan =
                Optional.empty()

        contributionsCalculator = Stub(ContributionsCalculator)
        contributionsCalculator.calculate(LOAN_AMOUNT_OF_1000, poolOfLenders) >>> expectedOptionalContributionsFor1000Loan
        contributionsCalculator.calculate(LOAN_AMOUNT_OF_1060, poolOfLenders) >>> expectedOptionalContributionsFor1060Loan
        contributionsCalculator.calculate(LOAN_AMOUNT_OF_1070, poolOfLenders) >>> expectedOptionalContributionsFor1070Loan
        contributionsCalculator.calculate(LOAN_AMOUNT_OF_500000, poolOfLenders) >>> expectedOptionalContributionsFor500000Loan
    }

    def "Should return correct quote"(
        int loanAmount,
        List<Lender> lenders,
        Optional<Quote> optQuote
    ) {

        given: "a QuoteServiceFor36Months"
        def quoteServiceFor36Months = new QuoteServiceFor36Months(contributionsCalculator)

        expect: "returns correct quotes"
        quoteServiceFor36Months.calculateQuote(loanAmount, lenders) == optQuote

        where: "loans amounts and lenders are"
        loanAmount            | lenders       || optQuote
        LOAN_AMOUNT_OF_1000   | poolOfLenders || expectedOptQuoteForLoanOf1000
        LOAN_AMOUNT_OF_1060   | poolOfLenders || expectedOptQuoteForLoanOf1060
        LOAN_AMOUNT_OF_1070   | poolOfLenders || expectedOptQuoteForLoanOf1070
        LOAN_AMOUNT_OF_500000 | poolOfLenders || expectedOptQuoteForLoanOf500000
    }
}
