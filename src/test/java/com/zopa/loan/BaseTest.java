package com.zopa.loan;

import com.zopa.loan.model.Contribution;
import com.zopa.loan.model.Lender;
import org.junit.BeforeClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BaseTest {

    static final int LOAN_AMOUNT_OF_1000   = 1000;
    static final int LOAN_AMOUNT_OF_1060   = 1060;
    static final int LOAN_AMOUNT_OF_1070   = 1070;
    static final int LOAN_AMOUNT_OF_500000 = 500000;

    public static List<Lender> poolOfLenders = new ArrayList<Lender>();


    static Optional<List<Contribution>> expectedOptionalContributionsFor1000Loan,
                                        expectedOptionalContributionsFor1060Loan,
                                        expectedOptionalContributionsFor1070Loan;

    @BeforeClass
    public static void baseSetUp() {

        Lender lenderBob    = new Lender("Bob",    BigDecimal.valueOf(0.075D),640);
        Lender lenderJane   = new Lender("Jane",   BigDecimal.valueOf(0.069D),480);
        Lender lenderFred   = new Lender("Fred",   BigDecimal.valueOf(0.071D),520);
        Lender lenderMary   = new Lender("Mary",   BigDecimal.valueOf(0.104D),170);
        Lender lenderJohn   = new Lender("John",   BigDecimal.valueOf(0.081D),320);
        Lender lenderDave   = new Lender("Dave",   BigDecimal.valueOf(0.074D),140);
        Lender lenderAngela = new Lender("Angela", BigDecimal.valueOf(0.071D),60);


        poolOfLenders.add(lenderBob);
        poolOfLenders.add(lenderJane);
        poolOfLenders.add(lenderFred);
        poolOfLenders.add(lenderMary);
        poolOfLenders.add(lenderJohn);
        poolOfLenders.add(lenderDave);
        poolOfLenders.add(lenderAngela);


        expectedOptionalContributionsFor1000Loan =
            Optional.of(
                Arrays.asList(
                    new Contribution(lenderJane, 480),
                    new Contribution(lenderFred, 520)
                )
            );

       expectedOptionalContributionsFor1060Loan =
            Optional.of(
                Arrays.asList(
                    new Contribution(lenderJane, 480),
                    new Contribution(lenderFred, 520),
                    new Contribution(lenderAngela, 60)
                )
            );

        expectedOptionalContributionsFor1070Loan =
            Optional.of(
                Arrays.asList(
                    new Contribution(lenderJane, 480),
                    new Contribution(lenderFred, 520),
                    new Contribution(lenderAngela, 60),
                    new Contribution(lenderDave, 10)
                )
            );

    }

    public List<Lender> getPoolOfLenders() {
        return poolOfLenders;
    }

    public static void setPoolOfLenders(List<Lender> poolOfLenders) {
        BaseTest.poolOfLenders = poolOfLenders;
    }
}
