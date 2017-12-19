package com.zopa.loan;

import com.zopa.loan.model.Contribution;
import com.zopa.loan.model.Lender;
import lombok.NonNull;

import java.util.*;

/**
 * <p>Interface representing a service that produces a list of {@link Contribution} instances.</p>
 *
 * <p>Because its only purpose is to get a list of {@link Contribution} from an amount and a list
 * of {@link Lender}, and has not other dependencies, it makes sense to define it as a functional
 * interface.</p>
 *
 * <p>In this interface is defined {@link #bestRate}, that implements the criteria of best
 * possible rate required for the technical tests. But can define other kinds of criteria, or
 * event pass lambdas to classes or methods expecting a {@code ConstributionsCalculator}.</p>
 *
 * @author Sumit Roy
 */
public interface ContributionsCalculator {

    /**
     * <p>Calculates an optional list of {@link Contribution} based on loan amount and
     * a list of {@link Lender}.</p>
     *
     * @param loanAmount The amount of the loan.
     * @param lenders List of {@link Lender} instances.
     * @return an optional list of {@link Contribution} instances.
     */
    Optional<List<Contribution>> calculate(
        Integer loanAmount,
        List<Lender> lenders
    );

    ContributionsCalculator bestRate =
        (@NonNull Integer loanAmount, @NonNull List<Lender> lenders) -> {

            if (loanAmount <= 100)
                throw new IllegalArgumentException("Parameter loanAmount should be 100 or greater.");
            if (lenders.isEmpty())
                throw new IllegalArgumentException("Parameter lenders should be a non empty list.");

            final List<Contribution> contributions = new ArrayList<>();

            lenders.sort(Comparator.comparing(Lender::getRate));

            int accAmount = 0;
            final Iterator<Lender> lendersIterator = lenders.iterator();
            while (accAmount < loanAmount && lendersIterator.hasNext()) {

                final Lender lender = lendersIterator.next();

                final Integer lenderAvailable = lender.getAvailable();

                int lenderContributedAmount =
                    (accAmount + lenderAvailable <= loanAmount) ?
                        lenderAvailable :
                        loanAmount - accAmount;

                final Contribution contribution = new Contribution(
                    lender,
                    lenderContributedAmount
                );

                contributions.add(contribution);

                accAmount += lenderContributedAmount;
            }

            if (accAmount == loanAmount)
                return Optional.of(contributions);
            else
                return Optional.empty();
        };
}
