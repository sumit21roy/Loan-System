package com.zopa.loan;

import com.zopa.loan.model.Lender;
import com.zopa.loan.model.Quote;

import java.util.List;
import java.util.Optional;

/**
 * <p>Interface representing a service that produces an optional {@link Quote} based on loan amount
 * and a list of {@link Lender}.</p>
 *
 * @author Sumit Roy
 */
public interface QuoteService {

    /**
     * <p>Returns an optional {@link Quote} based on loan amount and
     * a list of {@link Lender} instances.</p>
     *
     * <p>If it is not possible to calculate a {@link Quote} then
     * an Optional.empty() is returned.</p>
     *
     * @param loanAmount The amount of the loan.
     * @param lenders List of {@link Lender} instances.
     * @return optional {@link Quote} instances.
     */
    Optional<Quote> calculateQuote(
        Integer loanAmount,
        List<Lender> lenders
    );
}
