package org.devignite.reportBuilder.channel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileOutputChannel extends OutputStream {

    FileOutputStream _currentStream;

    public FileOutputChannel(String fileName) throws FileNotFoundException {
        _currentStream = new FileOutputStream(fileName);
    }

    @Override
    public void write(int b) throws IOException {
        this._currentStream.write(b);
    }

    @Override
    public void close() throws IOException {
        _currentStream.close();
    }
}
