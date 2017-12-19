package com.zopa.loan.model;

import lombok.Value;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * <p>Immutable class representing a Lender.</p>
 *
 * @author Sumit Roy
 */
public class Lender {

    private String name;
    private BigDecimal rate;
    private Integer available;

    public Lender(
        String name,
        BigDecimal rate,
        Integer available
    ) {
        this.name = name;
        this.rate = rate;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public Integer getAvailable() {
        return available;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Lender)) {
            return false;
        }
        Lender lender = (Lender) o;
        return name == lender.name &&
                Objects.equals(rate, lender.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rate);
    }

    @Override
    public String toString() {
        return " " + name + " " + rate;
    }
}
