package com.github.mwarc.embeddedmongo;

import com.google.common.base.Preconditions;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

public class AbstractEmbeddedMongoTestExecutionListener extends AbstractTestExecutionListener {

    private MongoServer server;

    public AbstractEmbeddedMongoTestExecutionListener(MongoServer server) {
        this.server = server;
    }

    protected void startServer(TestContext testContext) {
        EmbeddedMongo embeddedMongo = Preconditions.checkNotNull(
            AnnotationUtils.findAnnotation(testContext.getTestClass(), EmbeddedMongo.class),
            "EmbeddedMongoTestExecutionListener must be used with @EmbeddedMongo on "
                    + testContext.getTestClass()
        );
        String dbName = Preconditions.checkNotNull(embeddedMongo.dbName(), "@EmbeddedMongo dbName must not be null");

        server.start(dbName);
    }

    protected void cleanServer() {
        server.clean();
    }
}
