package NeededFiles;

import NeededFiles.GooglePlacesException;

public class RequestDeniedException extends GooglePlacesException {
    public RequestDeniedException(String errorMessage) {
        super(Statuses.STATUS_REQUEST_DENIED, errorMessage);
    }

    public RequestDeniedException() {
        this(null);
    }
}