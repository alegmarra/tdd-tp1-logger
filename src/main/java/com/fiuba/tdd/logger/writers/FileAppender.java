package com.fiuba.tdd.logger.writers;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileAppender implements com.fiuba.tdd.logger.Appendable {

    private final Boolean appendToEnd = true;
    private File outputFile;

    public FileAppender(String fileName) throws InvalidArgumentException, IOException {

        this.outputFile = new File(fileName);

        if (!outputFile.createNewFile() && !outputFile.canWrite()){
            throw new InvalidArgumentException(new String[]{"The given file already exist and cannot be written by this application"});
        }
    }


    @Override
    public void write(String message) throws IOException {
        PrintWriter output = null;

        try {
            output = new PrintWriter(new FileWriter(this.outputFile, appendToEnd));
            output.println(message);

        } finally {
            if (output != null) output.close();
        }
    }
}
