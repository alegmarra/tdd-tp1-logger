package sample.app.root;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mulesoft, Inc
 */
public class StaticAppender implements com.fiuba.tdd.logger.appenders.Appendable {

    private static List<String> messages = new ArrayList<String>();

    @Override
    public void append(String message) throws IOException {

        messages.add(message);
    }

    public static List<String> getMessages(){return messages;}
}
