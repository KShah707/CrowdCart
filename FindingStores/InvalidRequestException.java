package NeededFiles;

import NeededFiles.GooglePlacesException;

public class InvalidRequestException extends GooglePlacesException {
    public InvalidRequestException(String errorMessage) {
        super(Statuses.STATUS_INVALID_REQUEST, errorMessage);
    }

    public InvalidRequestException() {
        this(null);
    }
}
