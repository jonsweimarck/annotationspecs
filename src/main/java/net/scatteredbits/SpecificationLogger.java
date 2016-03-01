package net.scatteredbits;


import org.apache.log4j.Logger;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

import static net.scatteredbits.SpecificationLogger.Singelton.INSTANCE;

public class SpecificationLogger extends TestWatcher {

    // enum singelton ...
    public enum Singelton {
        INSTANCE;

        public boolean shutdownHookAdded = false;

        public String specName;
        public String specOverview;
        public List<String> succeded = new ArrayList<String>();
        public List<String> failed = new ArrayList<String>();
    }

    private Logger logger = Logger.getLogger(SpecificationLogger.class);

    @Override
    protected void starting(Description description) {
        Specification so = description.getTestClass().getAnnotation(Specification.class);
        if(so != null) {
            INSTANCE.specName = so.name();
            INSTANCE.specOverview = so.description();
        }
        super.starting(description);
    }

    @Override
    public Statement apply(Statement base, Description description) {
        Statement stmt = super.apply(base, description);
        return stmt;
    }

    public SpecificationLogger(){
        super();
        if(! INSTANCE.shutdownHookAdded) {
            INSTANCE.shutdownHookAdded = true;

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {

                    logResults();
                }
            });
        }

    }


    @Override
    protected void succeeded(Description description) {
        KeyExample ke = description.getAnnotation(KeyExample.class);
        if(ke != null) {
            INSTANCE.succeded.add(ke.description());
        }
        super.succeeded(description);
    }

    @Override
    protected void failed(Throwable t, Description description) {
        KeyExample ke = description.getAnnotation(KeyExample.class);
        if(ke != null) {
            INSTANCE.failed.add(ke.description());
        }
        super.failed(t, description);
    }

    public void logResults(){
        logger.info("****************************************************************");
        logger.info(INSTANCE.specName);
        logger.info(INSTANCE.specOverview);
        logger.info("");
        logger.info("Succeeded key examples:");
        for(String s: INSTANCE.succeded){
            logger.info( "* " + s);
        }
        logger.info("");
        logger.info("failed key examples:");
        for(String s: INSTANCE.failed){
            logger.info( "* " + s + "\n");
        }
        logger.info("****************************************************************");
    }

}
