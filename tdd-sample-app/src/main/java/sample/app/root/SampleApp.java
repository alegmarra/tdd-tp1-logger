package sample.app.root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleApp {

    public static void main(String[] args) throws Exception {

        Logger sampleLog = LoggerFactory.getLogger(SampleApp.class);
        sampleLog.info("Hola TDD, soy SampleApp");
        sampleLog.debug("No debería leer este mensaje");

        Logger demoLog = LoggerFactory.getLogger("demo");
        demoLog.debug("Hola TDD, soy Demo debug");
        demoLog.info("Existe un archivo con este log");

        Thread.sleep(2*1000);
        sampleLog.info("Done");
    }
}
