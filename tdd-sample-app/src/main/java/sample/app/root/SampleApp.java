package sample.app.root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleApp {

    public static void main(String[] args) throws Exception {

//        Logger sampleLog = LoggerFactory.getLogger(SampleApp.class);
//        sampleLog.info("Hola TDD, soy SampleApp");
//        sampleLog.debug("No debería leer este mensaje");
//
//        Logger demoLog = LoggerFactory.getLogger("demo");
//        demoLog.debug("Hola TDD, soy Demo debug");
//        demoLog.info("Existe un archivo con este log");
//
//        Thread.sleep(2*1000);
//        sampleLog.info("Done");

        Logger logger1 = LoggerFactory.getLogger("logger1");
        Logger logger2 = LoggerFactory.getLogger("logger2");
        Logger logger3 = LoggerFactory.getLogger("logger3");

        logger1.info("Hello world");
        logger1.info("Hola Mundo");
        logger1.info("Hallo Welt");

        logger2.info("tp no deberia loggearse");
        logger2.debug("tp deberia loggearse");

        for(String msg : StaticAppender.getMessages()){
            System.out.println(msg);
        }

        logger3.trace("No debería loggearse");
        logger3.warn("esto deberia loggearse como excepcion ", new NullPointerException("NULL POINTER"));
        logger3.error("esto deberia loggearse tambien");
    }
}
