package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.Logger;
import com.fiuba.tdd.logger.internal.LoggerInvoker;
import com.fiuba.tdd.logger.internal.MessageFormatter;
import junit.framework.TestCase;
import org.junit.Test;

public class MessageFormatterTestCases extends TestCase{

    private final String noFormat = "None format string";
    private final String withLevel = "show level :: %p";
    private final String withThread = "show thread :: %t";
    private final String withMessage = "show message :: %m";
    private final String withPercent = "show percent :: %%";
    private final String withSeparator = "show separator :: %n";
    private final String withFileName = "show invoker filename :: %F";
    private final String withLineNumber = "show invoker line :: %L";
    private final String withMethodName = "show invoker method :: %M";
    private final String withDate = "show invoker method :: %d{dd/mm/aa}";

    private LoggerInvoker asInvoker;
    /*
        Permitir si se desea definir el formato de todos los mensajes logueados por algun patron:
        ● %d{xxxxxxx} debería aceptar cualquier formato válido de SimpleDateFormat.
        ● %p debería mostrar el Nivel del mensaje.
        ● %t deberia mostrar el nombre del thread actual.
        ● %m debería mostrar el contenido del mensaje logueado por el usuario.
        ● %% debería mostrar un % (es el escape de %).
        ● %n debería mostrar el “separador” con el que el usuario configuró la herramienta o un default a elección.
        ● %L line number.
        ● %F filename.
        ● %M method name.
    */


    @Test
    public void testFormatMessage_NoFormat(){

        this.asInvoker = new LoggerInvoker(Thread.currentThread().getStackTrace()[0]);

        MessageFormatter mf = new MessageFormatter(noFormat, ",", Logger.Level.INFO);

        assertEquals("Formatter modified an unformatted string", noFormat, mf.formatMessage(this.asInvoker, ""));

    }


    @Test
    public void testFormatMessage_WithLine(){

        MessageFormatter mf = new MessageFormatter(withLineNumber, ",", Logger.Level.INFO);

        Integer currentLine = Thread.currentThread().getStackTrace()[1].getLineNumber();
        String expected = "show invoker line :: " + (currentLine + 2 );
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");

        assertEquals("Formatter modified an unformatted string", expected, formatted);
    }

    @Test
    public void testFormatMessage_WithFileName(){

        MessageFormatter mf = new MessageFormatter(withFileName, ",", Logger.Level.INFO);

        String fileName = Thread.currentThread().getStackTrace()[1].getFileName();
        String expected = "show invoker filename :: " + fileName;
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");

        assertEquals("Formatter modified an unformatted string", expected, formatted);
    }

    @Test
    public void testFormatMessage_WithLevel(){

        MessageFormatter mf = new MessageFormatter(withLevel, ",", Logger.Level.INFO);

        String expected = "show level :: INFO";
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");

        assertEquals("Formatter modified an unformatted string", expected, formatted);
    }

    @Test
    public void testFormatMessage_WithMethodName(){

        MessageFormatter mf = new MessageFormatter(withMethodName, ",", Logger.Level.INFO);

        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        String expected = "show invoker method :: " + methodName;
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");

        assertEquals("Formatter modified an unformatted string", expected, formatted);
    }

    @Test
    public void testFormatMessage_WithMessage(){

        MessageFormatter mf = new MessageFormatter(withMessage, ",", Logger.Level.INFO);

        String message = "My_Custom Message";
        String expected = "show message :: " + message;
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), message);

        assertEquals("Formatter modified an unformatted string", expected, formatted);
    }

    @Test
    public void testFormatMessage_WithPercent(){

        MessageFormatter mf = new MessageFormatter(withPercent, ",", Logger.Level.INFO);

        String expected = "show percent :: %";
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");

        assertEquals("Formatter modified an unformatted string", expected, formatted);
    }

    @Test
    public void testFormatMessage_WithSeparator(){

        MessageFormatter mf = new MessageFormatter(withSeparator, ",", Logger.Level.INFO);

        String expected = "show separator :: ,";
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");

        assertEquals("Formatter modified an unformatted string", expected, formatted);
    }

    @Test
    public void testFormatMessage_WithThread(){

        MessageFormatter mf = new MessageFormatter(withThread, ",", Logger.Level.INFO);

        String thread = Thread.currentThread().getName();
        String expected = "show thread :: " + thread;
        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");

        assertEquals("Formatter modified an unformatted string", expected, formatted);
    }

    @Test
    public void testFormatMessage_WithDate(){

        // TODO

//        MessageFormatter mf = new MessageFormatter(withDate, ",", Logger.Level.INFO);
//
//        Date date = new Date();
//        String expected = "show invoker method :: ";
//        String formatted = mf.formatMessage(new LoggerInvoker(Thread.currentThread().getStackTrace()[1]), "");
//
//        assertEquals("Formatter modified an unformatted string", expected, formatted);
    }


}
