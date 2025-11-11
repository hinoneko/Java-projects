package task3.io;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class DecryptReader extends FilterReader {
    private final int key;

    public DecryptReader(Reader in, int key) {
        super(in);
        this.key = key;
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        return (c == -1) ? -1 : (c - key);
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int result = super.read(cbuf, off, len);
        if (result == -1) return -1;

        for (int i = 0; i < result; i++) {
            cbuf[off + i] = (char) (cbuf[off + i] - key);
        }
        return result;
    }
}