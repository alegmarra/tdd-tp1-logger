package com.fiuba.tdd.logger.writers;

import com.fiuba.tdd.logger.internal.LoggerInvoker;
import com.fiuba.tdd.logger.internal.MessageFormatter;

import java.io.*;

public class FileAppender implements com.fiuba.tdd.logger.Appendable {

    private File outputFile;
    private MessageFormatter formatter;

    public FileAppender(String fileName, MessageFormatter formatter) throws IOException {

        this.outputFile = new File(fileName);
        this.formatter = formatter;
    }


    @Override
    public void write(LoggerInvoker invoker, String message){
        PrintWriter output = null;
        try {
            output = new PrintWriter(new FileWriter(this.outputFile, true));
            output.println(formatter.formatMessage(invoker, message));

        } catch (IOException e) {
            e.printStackTrace();
            //FIXME

        } finally {

            if (output != null) output.close();
        }


    }
}
