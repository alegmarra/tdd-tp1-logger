package com.fiuba.tdd.logger.format;

import com.fiuba.tdd.logger.utils.LoggerConfig;
import com.google.gson.Gson;

import java.util.*;

public class JSONFormatter extends MessageFormatter {
    
    
    public JSONFormatter(LoggerConfig loggerConfig) {
        super(loggerConfig);
    }

    @Override
    public String formatMessage(LoggerInvoker invoker, String message) {
        Map<PatternName, String> patternValues = getValuesByPatternName(invoker, message);
        Gson gson = new Gson();
        return gson.toJson(createResponse(patternValues));
    }


    private Map<String, String> createResponse(Map<PatternName,String> patternValues){
        Map<String, String> response = new TreeMap<>();

        if (format.contains(dateFormatter.toPattern()))
            response.put(PatternName.dateTime.name(), patternValues.get(PatternName.dateTime));

        for (Map.Entry entry: patternKeys.entrySet()){
            String pattern = (String) entry.getKey();
            PatternName jsonKey = (PatternName) entry.getValue();

            if (format.contains(pattern))
                response.put(jsonKey.name(), patternValues.get(jsonKey));
        }

        return response;
    }

}
