package com.systelab.patient.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Embeddable
public class PhoneNumber implements Serializable {

    private final int areaCode;

    private final int number;

    @JsonIgnore
    private int hashCode;

    private PhoneNumber(int areaCode, int number) {
        this.areaCode = areaCode;
        this.number = number;
    }

    public static PhoneNumber of(int areaCode, int number) {
        checkArgument(areaCode > 100);
        checkArgument(number > 1000);
        return new PhoneNumber(areaCode, number);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof PhoneNumber) {
            PhoneNumber other = (PhoneNumber) obj;
            return Objects.equals(this.areaCode, other.areaCode) && Objects.equals(this.number, other.number);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = Objects.hash(this.areaCode, this.number);
        }
        this.hashCode = result;
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("areaCode", areaCode)
                .add("number", number)
                .toString();
    }
}