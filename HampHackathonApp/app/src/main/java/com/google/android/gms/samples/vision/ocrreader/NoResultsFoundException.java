package com.google.android.gms.samples.vision.ocrreader;

public class NoResultsFoundException extends GooglePlacesException {
    public NoResultsFoundException(String errorMessage) {
        super(Statuses.STATUS_ZERO_RESULTS, errorMessage);
    }

    public NoResultsFoundException() {
        this(null);
    }
}
