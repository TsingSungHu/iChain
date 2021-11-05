package com.qs.iChain.exception;

/**
 * Represents order mismatch of resource entry and resource exit (pair mismatch).
 *
 * @author TsingSungHu
 */
public class ErrorEntryFreeException extends RuntimeException {

    public ErrorEntryFreeException(String s) {
        super(s);
    }
}
