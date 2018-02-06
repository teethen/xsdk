package com.teethen.sdk.xhttp.nohttp;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * A default implementation of Binary.
 * All the methods are called in Son thread.
 * </p>
 *
 * @deprecated use {@link FileBinary} instead.
 */
@Deprecated
public class ByteArrayBinary extends BasicBinary {

    private byte[] byteArray;

    /**
     * A byte array of {@link Binary}.
     *
     * @param byteArray byte array.
     * @param fileName  file name.  Had better pass this value, unless the server tube don't care about the file name.
     */
    public ByteArrayBinary(byte[] byteArray, String fileName) {
        this(byteArray, fileName, null);
    }

    /**
     * A byte array of {@link Binary}.
     *
     * @param byteArray byte array.
     * @param fileName  file name.  Had better pass this value, unless the server tube don't care about the file name.
     * @param mimeType  content type.
     */
    public ByteArrayBinary(byte[] byteArray, String fileName, String mimeType) {
        super(fileName, mimeType);
        if (byteArray == null)
            throw new IllegalArgumentException("ByteArray is null: " + fileName);
        this.byteArray = byteArray;
    }

    @Override
    public long getBinaryLength() {
        return byteArray.length;
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(byteArray);
    }
}
