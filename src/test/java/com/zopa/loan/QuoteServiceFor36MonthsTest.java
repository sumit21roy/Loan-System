package com.zopa.loan;

import com.zopa.loan.model.Quote;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Optional;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class QuoteServiceFor36MonthsTest extends BaseTest {

    @Mock
    private static ContributionsCalculator contributionsCalculator;

    private static Optional<Quote>  expectedOptQuoteForLoanOf1000,
                                    expectedOptQuoteForLoanOf1060,
                                    expectedOptQuoteForLoanOf1070;

    @BeforeClass
    public static void setUp() {

        contributionsCalculator = mock(ContributionsCalculator.class);

        when(
            contributionsCalculator.calculate(
                LOAN_AMOUNT_OF_1000,
                poolOfLenders
            )
        ).thenReturn(expectedOptionalContributionsFor1000Loan);

        when(
            contributionsCalculator.calculate(
                LOAN_AMOUNT_OF_1060,
                poolOfLenders
            )
        ).thenReturn(expectedOptionalContributionsFor1060Loan);

        when(
            contributionsCalculator.calculate(
                LOAN_AMOUNT_OF_1070,
                poolOfLenders
            )
        ).thenReturn(expectedOptionalContributionsFor1070Loan);

        when(
            contributionsCalculator.calculate(
                LOAN_AMOUNT_OF_500000,
                poolOfLenders
            )
        ).thenReturn(Optional.empty());

        expectedOptQuoteForLoanOf1000 =
            Optional.of(
                new Quote(
                    new BigDecimal("7.0"),
                    new BigDecimal("30.78"),
                    new BigDecimal("1108.10")
                )
            );

        expectedOptQuoteForLoanOf1060 =
            Optional.of(
                new Quote(
                    new BigDecimal("7.0"),
                    new BigDecimal("32.63"),
                    new BigDecimal("1174.68")
                )
            );

        expectedOptQuoteForLoanOf1070 =
            Optional.of(
                new Quote(
                    new BigDecimal("7.0"),
                    new BigDecimal("32.94"),
                    new BigDecimal("1185.82")
                )
            );
    }

    @Test
    public void calculationOfQuoteFor1000IsCorrect() {

        // Given: A loan of 1000 and a pool of lenders
        // When: Calculating quote
        final Optional<Quote> optQuote =
            new QuoteServiceFor36Months(contributionsCalculator)
                .calculateQuote(
                    LOAN_AMOUNT_OF_1000,
                    poolOfLenders
                );

        // Then: Return correct quote
        assertThat(optQuote, is(expectedOptQuoteForLoanOf1000));
    }

    @Test
    public void calculationOfQuoteFor1060IsCorrect() {

        // Given: A loan of 1060 and a pool of lenders
        // When: Calculating quote
        final Optional<Quote> optQuote =
            new QuoteServiceFor36Months(contributionsCalculator)
                .calculateQuote(
                    LOAN_AMOUNT_OF_1060,
                    poolOfLenders
                );

        // Then: Return correct quote
        assertThat(optQuote, is(expectedOptQuoteForLoanOf1060));
    }

    @Test
    public void calculationOfQuoteFor1070IsCorrect() {

        // Given: A loan of 1070 and a pool of lenders
        // When: Calculating quote
        final Optional<Quote> optQuote =
            new QuoteServiceFor36Months(contributionsCalculator)
                .calculateQuote(
                    LOAN_AMOUNT_OF_1070,
                    poolOfLenders
                );

        // Then: Return correct quote
        assertThat(optQuote, is(expectedOptQuoteForLoanOf1070));
    }

    @Test
    public void shouldReturnNoQuoteForALoanOf500000() {

        // Given: A loan of 500000 and a pool of lenders
        // When: Calculating quote
        final Optional<Quote> optQuote =
            new QuoteServiceFor36Months(contributionsCalculator)
                .calculateQuote(
                    LOAN_AMOUNT_OF_500000,
                    poolOfLenders
                );

        // Then: Return empty
        assertThat(optQuote, isEmpty());
    }
}
