package com.zopa.loan.model;

import lombok.Value;

/**
 * <p>Immutable class representing a Contribution.</p>
 *
 * <p>When calculating a {@link Quote}, the calculation is based on a list of
 * {@code Contribution}.</p>
 *
 * <p>A Contribution contains a single {@link Lender} and the contribution
 * amount.</p>
 *

 * @author Sumit Roy
 */
@Value
public class Contribution {

    private Lender lender;
    private Integer amount;


    public Lender getLender() {
        return lender;
    }

    public Integer getAmount() {
        return amount;
    }

    public Contribution(Lender lender, Integer amount) {
        this.lender = lender;
        this.amount = amount;

    }
}
