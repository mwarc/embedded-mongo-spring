package com.github.mwarc.embeddedmongo;

import com.github.fakemongo.Fongo;
import com.mongodb.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public final class FongoServer implements MongoServer {

    private static Logger logger = LoggerFactory.getLogger(FongoServer.class);

    private DB db;

    @Override
    public void start(String dbName) {
        logger.debug("Starting mongo...");
        db = new Fongo("InMemoryMongo").getDB(dbName);

    }

    @Override
    public void clean() {
        if (db != null) {
            logger.debug("Cleaning mongo...");
            db.dropDatabase();
        }
    }
}
