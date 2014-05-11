package com.fiuba.tdd.logger.internal;

import com.fiuba.tdd.logger.Logger.Level;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageFormatter {

    private final String separator;
    private final Level level;
    private SimpleDateFormat dateFormatter;

    private String format;

    public MessageFormatter(String format, String separator, Level logLevel){
        this.format = format;
        this.separator = separator;
        this.level = logLevel;

        this.dateFormatter = setDateFormatter();
    }

    private SimpleDateFormat setDateFormatter() {

        Pattern pattern = Pattern.compile("%d\\{(.*)\\}");
        Matcher matcher = pattern.matcher(format);

        String dateFormat = matcher.matches() ? matcher.group(1) : "";

        return dateFormat.isEmpty() ? new SimpleDateFormat() : new SimpleDateFormat(dateFormat);

    }


    public String formatMessage(LoggerInvoker invoker, String message){

        return format.replace("%p", getLevel())
                     .replace("%m", message)
                     .replace("%n", separator)
                     .replace("%d", dateFormatter.format(new Date()))
                     .replace("%L", invoker.getLine())
                     .replace("%F", invoker.getFilename())
                     .replace("%M", invoker.getMethodName())
                     .replace("%t", Thread.currentThread().getName())
                     .replace("%%", "%");
    }
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

    private String getLevel(){
        return level.name();
    }





}
