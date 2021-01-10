package com.systelab.patient.model;

import com.google.common.collect.ForwardingList;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class PhoneNumbers extends ForwardingList<PhoneNumber> {

    private final List<PhoneNumber> delegate;

    private PhoneNumbers(List<PhoneNumber> delegate) {
        this.delegate = delegate;
    }

    public static PhoneNumbers of(List<PhoneNumber> delegate) {
        checkNotNull(delegate);
        return new PhoneNumbers(delegate);
    }

    @Override
    protected List<PhoneNumber> delegate() {
        return delegate;
    }

}
