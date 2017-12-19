package com.zopa.loan;

import com.zopa.loan.model.Contribution;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ContributionsCalculatorBestRateTest extends BaseTest {

    @Test
    public void shouldReturnACorrectListOfContributionsForLoanOf1000() {

        // Given: A loan of 1000 and a pool of lenders
        // When: Calculating contributions
        final Optional<List<Contribution>> optContributions =
            ContributionsCalculator.bestRate.calculate(
                LOAN_AMOUNT_OF_1000,
                    poolOfLenders
            );

        // Then: Return correct list of contributions
        assertThat(optContributions.toString(), is(expectedOptionalContributionsFor1000Loan.toString()));
    }

    @Test
    public void returnsACorrectListOfContributionsForLoanOf1060() {

        // Given: A loan of 1060 and a pool of lenders
        // When: Calculating contributions
        final Optional<List<Contribution>> optContributions1060 =
            ContributionsCalculator.bestRate.calculate(
                LOAN_AMOUNT_OF_1060,
                poolOfLenders
            );

        // Then: Return correct list of contributions
        assertThat(optContributions1060.toString(), is(expectedOptionalContributionsFor1060Loan.toString()));
    }

    @Test
    public void returnsACorrectListOfContributionsForLoanOf1070() {

        // Given: A loan of 1070 and a pool of lenders
        // When: Calculating contributions
        final Optional<List<Contribution>> optContributions =
            ContributionsCalculator.bestRate.calculate(
                LOAN_AMOUNT_OF_1070,
                poolOfLenders
            );

        // Then: Return correct list of contributions
        assertThat(optContributions, is(expectedOptionalContributionsFor1070Loan));
    }

    @Test
    public void shouldReturnNoListOfContributionsForA500000Loan() {

        // Given: A loan of 500000 and a pool of lenders
        // When: Calculating contributions
        final Optional<List<Contribution>> optContributions =
            ContributionsCalculator.bestRate.calculate(
                LOAN_AMOUNT_OF_500000,
                poolOfLenders
            );

        // Then: Return empty
        assertThat(optContributions, isEmpty());
    }
}
