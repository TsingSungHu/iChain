package com.qs.iChain.spi;

/**
 * Error thrown when something goes wrong while loading Provider via {@link SpiLoader}.
 *
 * @author TsingSungHu
 */
public class SpiLoaderException extends RuntimeException {

    public SpiLoaderException() {
        super();
    }

    public SpiLoaderException(String message) {
        super(message);
    }

    public SpiLoaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
