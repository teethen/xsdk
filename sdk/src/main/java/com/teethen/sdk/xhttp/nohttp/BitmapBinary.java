package com.teethen.sdk.xhttp.nohttp;

import android.graphics.Bitmap;

import com.teethen.sdk.xhttp.nohttp.tools.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A default implementation of Binary.
 * All the methods are called in Son thread.
 *
 * @deprecated use {@link FileBinary} instead.
 */
@Deprecated
public class BitmapBinary extends BasicBinary {

    private Bitmap mBitmap;

    /**
     * An input stream {@link Binary}.
     *
     * @param bitmap   image.
     * @param fileName file name. Had better pass this value, unless the server tube don't care about the file name.
     */
    public BitmapBinary(Bitmap bitmap, String fileName) {
        this(bitmap, fileName, null);
    }

    /**
     * An input stream {@link Binary}.
     *
     * @param bitmap   image.
     * @param fileName file name. Had better pass this value, unless the server tube don't care about the file name.
     * @param mimeType such as: image/png.
     */
    public BitmapBinary(Bitmap bitmap, String fileName, String mimeType) {
        super(fileName, mimeType);
        if (bitmap == null)
            throw new IllegalArgumentException("Bitmap is null: " + fileName);
        if (bitmap.isRecycled())
            throw new IllegalArgumentException("Bitmap is recycled: " + fileName + ", bitmap must be not recycled.");
        this.mBitmap = bitmap;
    }

    @Override
    protected InputStream getInputStream() throws IOException {
        if (mBitmap.isRecycled()) return null;
        return new ByteArrayInputStream(bitmap2ByteArray(mBitmap));
    }

    @Override
    public long getBinaryLength() {
        if (mBitmap.isRecycled()) return 0;
        return bitmap2ByteArray(mBitmap).length;
    }

    public static byte[] bitmap2ByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        IOUtils.closeQuietly(outputStream);
        return outputStream.toByteArray();
    }
}