package com.zopa.loan;

import com.zopa.loan.model.Contribution;
import com.zopa.loan.model.Lender;
import com.zopa.loan.model.Quote;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * <p>Class implementing the {@link QuoteService} interface for loans of 36 months.</p>
 *
 * <p>Requires an instance of {@link ContributionsCalculator}, so it can calculate
 * a list of {@link Contribution}.</p>
 *
 * @author Sumit Roy
 */
public class QuoteServiceFor36Months implements QuoteService {

    private static final int NUMBER_OF_PERIODS = 36;
    private static final int PERIODS_PER_YEAR = 12;

    private ContributionsCalculator contributionsCalculator;

    QuoteServiceFor36Months(ContributionsCalculator contributionsCalculator) {
        this.contributionsCalculator = contributionsCalculator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Quote> calculateQuote(
        @NonNull Integer loanAmount,
        @NonNull List<Lender> lenders
    ) {

        final Optional<List<Contribution>> optContributions =
            contributionsCalculator.calculate(
                loanAmount,
                lenders
            );

        return optContributions.map(this::calculateQuoteBasedOnContributions);
    }

    /**
     * <p>Returns a {@link Quote} based on a list of {@link Contribution} instances.</p>
     *
     * @param contributions List of {@link Contribution} instances.
     * @return a {@link Quote}
     */
    private Quote calculateQuoteBasedOnContributions(
        @NonNull List<Contribution> contributions
    ) {

        final BigDecimal rate = calculateRate(contributions);
        final BigDecimal monthlyRepayment = calculateMonthlyRepayment(contributions);
        final BigDecimal totalRepayment = calculateTotalRepayment(monthlyRepayment);

        final BigDecimal quoteRate =
            rate
                .multiply(new BigDecimal("100"))
                .setScale(1,BigDecimal.ROUND_HALF_UP);

        final BigDecimal quoteMonthlyRepayment = monthlyRepayment.setScale(2,BigDecimal.ROUND_HALF_UP);

        final BigDecimal quoteTotalRepayment = totalRepayment.setScale(2,BigDecimal.ROUND_HALF_UP);

        return
            new Quote(
                quoteRate,
                quoteMonthlyRepayment,
                quoteTotalRepayment
            );
    }

    /**
     * <p>Returns the monthly repayment based on a list of {@link Contribution} instances.</p>
     *
     * @param contributions The {@link Contribution} instances.
     * @return the monthly repayment
     */
    private BigDecimal calculateMonthlyRepayment(
        @NonNull List<Contribution> contributions
    ) {

        return contributions.stream()
            .map(this::calculateMonthlyRepaymentForContribution)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * <p>Returns the total repayment.</p>
     *
     * @param monthlyRepayment The monthly repayment
     * @return the total repayment
     */
    private BigDecimal calculateTotalRepayment(
        @NonNull BigDecimal monthlyRepayment
    ) {

        return
            monthlyRepayment.multiply(BigDecimal.valueOf(NUMBER_OF_PERIODS));
    }

    /**
     * <p>Returns the combined rate of a list of {@link Contribution} instances.</p>
     *
     * @param contributions List of contributions
     * @return combined rate
     */
    private BigDecimal calculateRate(
        @NonNull List<Contribution> contributions
    ) {

        final int totalAmount =
            contributions.stream()
                .mapToInt(Contribution::getAmount)
                .sum();

        return
            contributions.stream()
                .map(
                    (Contribution c) -> calculateContributionToRateOfLoanForContribution(totalAmount, c)
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * <p>Returns the contribution of a {@link Contribution} to the global
     * rate of the loan.</p>
     *
     * @param loanAmount The total amount of the loan
     * @param contribution The contribution
     * @return the contribution to the global rate of the loan.
     */
    private BigDecimal calculateContributionToRateOfLoanForContribution(
        @NonNull Integer loanAmount,
        @NonNull Contribution contribution
    ) {
        return
            BigDecimal.valueOf(contribution.getAmount())
                .divide(BigDecimal.valueOf(loanAmount), 10, BigDecimal.ROUND_HALF_UP)
                .multiply(contribution.getLender().getRate());
    }

    /**
     * <p>Returns the monthly repayment for a {@link Contribution}.</p>
     *
     * @param contribution a {@link Contribution}
     * @return the monthly repayment
     */
    private BigDecimal calculateMonthlyRepaymentForContribution(
        @NonNull Contribution contribution
    ) {

        final BigDecimal rate = contribution.getLender().getRate();

        final Integer amount = contribution.getAmount();

        final BigDecimal monthlyRate =
            BigDecimal.valueOf(
                Math.pow(
                    rate.add(BigDecimal.ONE).doubleValue(),
                    1 / (double)PERIODS_PER_YEAR
                )
            )
                .subtract(BigDecimal.ONE);

        return
            monthlyRate
                .multiply(BigDecimal.valueOf(amount))
                .divide(
                    BigDecimal.ONE
                        .subtract(
                            BigDecimal.valueOf(
                                Math.pow(
                                    BigDecimal.ONE.add(monthlyRate).doubleValue(),
                                    -NUMBER_OF_PERIODS
                                )
                            )
                        ),
                    10,
                    BigDecimal.ROUND_HALF_UP
                );
    }
}
