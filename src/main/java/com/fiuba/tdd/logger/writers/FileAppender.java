package com.fiuba.tdd.logger.writers;

import com.fiuba.tdd.logger.internal.InvalidArgumentException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Clase utilizada como interfaz entre un archivo y el logger
 */
public class FileAppender implements com.fiuba.tdd.logger.Appendable {

    private final Boolean appendToEnd = true;
    private File outputFile;

    public FileAppender(String fileName) throws InvalidArgumentException, IOException {

        if (fileName == null || fileName.isEmpty())
            throw new InvalidArgumentException("The given filename was null or the string was empty");

        this.outputFile = new File(fileName);

        if (!outputFile.createNewFile() && !outputFile.canWrite()){
            throw new InvalidArgumentException("The given file already exist and cannot be written by this application");
        }
    }


    @Override
    public void append(String message) throws IOException {
        PrintWriter output = null;

        try {
            output = new PrintWriter(new FileWriter(this.outputFile, appendToEnd));
            output.println(message);

        } finally {
            if (output != null) output.close();
        }
    }
}
