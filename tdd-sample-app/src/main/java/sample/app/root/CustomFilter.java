package sample.app.root;

import com.fiuba.tdd.logger.filters.Filter;
import com.fiuba.tdd.logger.utils.Configurable.Level;
import com.fiuba.tdd.logger.utils.LoggerConfig;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mulesoft, Inc
 */
public class CustomFilter implements Filter {

    private final Pattern pattern;
    private Level level;
    private String separator;
    private String format;

    public CustomFilter(String pattern, String format, String level, String separator){

        this.pattern = Pattern.compile(pattern);

        this.level = level.isEmpty() || level == null ? null : Level.valueOf(level);
        this.separator = separator.isEmpty() || separator == null ? null : separator;
        this.format = format.isEmpty() || format == null ? null : format;
    }

    @Override
    public Boolean allows(String msg, LoggerConfig config) {

        if (config != null){
            if (this.level != null && !this.level.equals(config.level)){
                return false;
            }
        }

        Matcher matcher = pattern.matcher(msg);
        return !matcher.matches();

    }
}
