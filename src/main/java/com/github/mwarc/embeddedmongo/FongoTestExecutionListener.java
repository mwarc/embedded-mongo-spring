package com.github.mwarc.embeddedmongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

public class FongoTestExecutionListener extends AbstractEmbeddedMongoTestExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(FongoTestExecutionListener.class);

    public FongoTestExecutionListener() {
        super(new FongoServer());
    }

    @Override
    public void beforeTestClass(TestContext testContext) {
        startServer(testContext);
    }

    @Override
    public void afterTestMethod(TestContext testContext) {
        if (Boolean.TRUE.equals(testContext.getAttribute(DependencyInjectionTestExecutionListener.REINJECT_DEPENDENCIES_ATTRIBUTE))) {
            logger.debug("Cleaning and reloading server for test context [{}].", testContext);
            cleanServer();
            startServer(testContext);
        }
    }

    @Override
    public void afterTestClass(TestContext testContext) {
        cleanServer();
    }
}
