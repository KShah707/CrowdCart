package com.google.android.gms.samples.vision.ocrreader;

/**
 * Represents the scope in which a place resides.
 */
public enum Scope {
    /**
     * The place is only visible to the app that created it.
     */
    APP,
    /**
     * The place has been approved by the moderation process and is visible to the public.
     */
    GOOGLE
}
