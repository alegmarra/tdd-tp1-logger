package com.fiuba.tdd.logger.slf4j.binding;

import java.util.Objects;

public class ParametrizedMessageInterpreter {
    private final static String parametrizeToken = "{}";
    private final static String escapeToken = "\\";
    private final static String escapedParametrizeToken = escapeToken + parametrizeToken;
    private final static String nullObjectString = "null";

    public static String replaceMessageWithArgs(String msg, Object arg) {
        return replaceMessageWithArgs(msg, new Object[] {arg});
    }

    public static String replaceMessageWithArgs(String msg,  Object arg1, Object arg2) {
        return replaceMessageWithArgs(msg, new Object[] {arg1, arg2});
    }

    public static String replaceMessageWithArgs(String msg, Object... args) {
        if (args == null || args.length == 0) return msg;
        String msgWithReplacedArgs = "",
                replaceArgument;
        int indexOfEscapeToken = msg.indexOf(escapedParametrizeToken),
            indexOfParametrizeToken = msg.indexOf(parametrizeToken),
            indexOf,
            replacedLength,
            i = 0;

        while ( (indexOfEscapeToken != -1 ||
                 indexOfParametrizeToken != -1) &&
                i < args.length ) {

            // Then there's a escaped token that should be replaced first
            // without increasing i (args' index)
            if (indexOfEscapeToken < indexOfParametrizeToken &&
                indexOfEscapeToken > -1 ) {
                replaceArgument = parametrizeToken;
                replacedLength = escapedParametrizeToken.length();
                indexOf = indexOfEscapeToken;
            } else {
                replaceArgument = args[i] == null ? nullObjectString : args[i].toString();
                replacedLength = parametrizeToken.length();
                indexOf = indexOfParametrizeToken;
                ++i;
            }

            msgWithReplacedArgs = msgWithReplacedArgs
                    .concat(msg.substring(0, indexOf))
                    .concat(replaceArgument);
            msg = msg.substring(indexOf + replacedLength, msg.length());

            indexOfEscapeToken = msg.indexOf(escapedParametrizeToken);
            indexOfParametrizeToken = msg.indexOf(parametrizeToken);
        }

        return msgWithReplacedArgs;
    }
}
