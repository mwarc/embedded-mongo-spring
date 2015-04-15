package com.github.mwarc.embeddedmongo;

public interface MongoServer {

    void start(String dbName);

    void clean();
}
