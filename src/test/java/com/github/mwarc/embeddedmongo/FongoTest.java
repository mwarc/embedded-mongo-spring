package com.github.mwarc.embeddedmongo;

import com.github.mwarc.embeddedmongo.testutils.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.concurrent.Callable;

import static com.jayway.awaitility.Awaitility.await;
import static com.jayway.awaitility.Duration.ONE_SECOND;
import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TestExecutionListeners({FongoTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
@EmbeddedMongo(dbName = "myDbName")
public class FongoTest {

    private final String username = "username";

    @Autowired
    private MongoTemplate mongoTemplate;

    @After
    public void tearDown() {
        Query query = new Query(Criteria.where("username").is(username));
        mongoTemplate.remove(query, User.class);
    }

    @Test
    public void shouldNotFindUserInMongo() {
        //when
        Query query = new Query(Criteria.where("username").is(username));
        User user = mongoTemplate.findOne(query, User.class);

        //then
        assertEquals(null, user);
    }

    @Test
     public void shouldSaveUserAndRetrieveItFromMongo() {
        //given
        User user = new User(username, "password");

        //when
        mongoTemplate.save(user);

        //then
        await().atMost(ONE_SECOND).until(isUserInMongo(user));
    }

    @Test
    public void shouldDeleteUserFromMongo() {
        //given
        User user = new User(username, "password");
        mongoTemplate.save(user);

        //when
        mongoTemplate.remove(user);

        //then
        await().atMost(ONE_SECOND).until(isUserInMongo(null));
    }

    private Callable<Boolean> isUserInMongo(final User user) {
        return new Callable<Boolean>() {
            public Boolean call() throws Exception {
                Query query = new Query(Criteria.where("username").is(username));
                User user = mongoTemplate.findOne(query, User.class);
                return (user == null ? user == null : user.equals(user));
            }
        };
    }
}
