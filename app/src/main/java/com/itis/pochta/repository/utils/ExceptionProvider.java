/* =========================
   Author: Tamerlan Shakirov
   ========================= */

package com.itis.pochta.repository.utils;

import android.support.annotation.Nullable;

public class ExceptionProvider {
    private String[] serverExceptions;

    //Constructor
    public ExceptionProvider(@Nullable String[] serverExceptions) {
        if (serverExceptions != null)
            this.serverExceptions = serverExceptions;
        else
            this.serverExceptions = new String[]{"Success. No errors", "Wrong form", "No rights", "Not found"};
    }

    //Get server exception by code
    public Exception getServerException(int code) {
        return ((serverExceptions.length - 1) >= code) ? new Exception(serverExceptions[code]) : new Exception("Exception code not found...");
    }
}