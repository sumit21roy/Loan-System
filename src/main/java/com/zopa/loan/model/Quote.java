package com.zopa.loan.model;

import lombok.Value;

import java.math.BigDecimal;

/**
 * <p>Immutable class representing a Quote.</p>
 *
 * <p>A Quote represents the information given to the borrower.</p>
 *
 * @author Sumit Roy
 */
@Value
public class Quote {

    private BigDecimal rate;
    private BigDecimal monthlyRepayment;
    private BigDecimal totalRepayment;

    public Quote(BigDecimal rate, BigDecimal monthlyRepayment, BigDecimal totalRepayment) {
        this.rate = rate;
        this.monthlyRepayment = monthlyRepayment;
        this.totalRepayment = totalRepayment;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public BigDecimal getMonthlyRepayment() {
        return monthlyRepayment;
    }

    public BigDecimal getTotalRepayment() {
        return totalRepayment;
    }

}
