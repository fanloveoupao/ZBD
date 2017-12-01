package com.tgnet.app.utils.ShortcutBadger;

public class ShortcutBadgeException extends Exception {
    public ShortcutBadgeException(String message) {
        super(message);
    }

    public ShortcutBadgeException(String message, Exception e) {
        super(message, e);
    }

}
