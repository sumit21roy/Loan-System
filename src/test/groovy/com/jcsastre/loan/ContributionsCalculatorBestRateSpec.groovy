package com.jcsastre.loan

import com.zopa.loan.ContributionsCalculator
import com.zopa.loan.model.Contribution
import com.zopa.loan.model.Lender
import spock.lang.Shared
import spock.lang.Specification


class ContributionsCalculatorBestRateSpec extends Specification {

    @Shared
    Optional<List<Contribution>> expectedOptionalContributionsFor1000Loan,
                                 expectedOptionalContributionsFor1060Loan,
                                 expectedOptionalContributionsFor1070Loan,
                                 expectedOptionalContributionsFor500000Loan

    @Shared
    private Lender lenderBob, lenderJane, lenderFred, lenderMary, lenderJohn, lenderDave, lenderAngela

    @Shared
    List<Lender> poolOfLenders

    def setupSpec() {

        lenderBob    = new Lender("Bob",    BigDecimal.valueOf(0.075D),640)
        lenderJane   = new Lender("Jane",   BigDecimal.valueOf(0.069D),480)
        lenderFred   = new Lender("Fred",   BigDecimal.valueOf(0.071D),520)
        lenderMary   = new Lender("Mary",   BigDecimal.valueOf(0.104D),170)
        lenderJohn   = new Lender("John",   BigDecimal.valueOf(0.081D),320)
        lenderDave   = new Lender("Dave",   BigDecimal.valueOf(0.074D),140)
        lenderAngela = new Lender("Angela", BigDecimal.valueOf(0.071D),60)

        poolOfLenders = [lenderBob, lenderJane, lenderFred, lenderMary, lenderJohn, lenderDave, lenderAngela]

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
    }

    def "Should return correct contributions"(
            int loanAmount,
            List<Lender> lenders,
            Optional<List<Contribution>> optContributions
    ) {

        expect: "returns correct contributions"
        ContributionsCalculator.bestRate.calculate(loanAmount, lenders) == optContributions

        where: "loans amounts and lenders are"
        loanAmount | lenders       || optContributions
        1000       | poolOfLenders || expectedOptionalContributionsFor1000Loan
        1060       | poolOfLenders || expectedOptionalContributionsFor1060Loan
        1070       | poolOfLenders || expectedOptionalContributionsFor1070Loan
        500000     | poolOfLenders || expectedOptionalContributionsFor500000Loan
        500000     | poolOfLenders || Optional.empty()
    }
}
