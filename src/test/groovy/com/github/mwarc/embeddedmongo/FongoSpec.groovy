package com.github.mwarc.embeddedmongo

import com.github.mwarc.embeddedmongo.testutils.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static com.jayway.awaitility.Awaitility.await
import static com.jayway.awaitility.Duration.ONE_SECOND
import static com.jayway.awaitility.Duration.TWO_SECONDS

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@EmbeddedMongo
class FongoSpec extends Specification {

    def username = "name"

    @Autowired
    MongoTemplate mongoTemplate

    def cleanup() {
        Query query = new Query(Criteria.where("username").is(username))
        mongoTemplate.remove(query, User.class)
    }

    def "should not find user in mongo"() {
        when:
        Query query = new Query(Criteria.where("username").is(username))
        def user = mongoTemplate.findOne(query, User.class)

        then:
        !user
    }

    def "should save user and retrieve it from mongo"() {
        given:
        def user = new User(username, "password")

        when:
        mongoTemplate.save(user)

        then:
        await().atMost(ONE_SECOND).until {
            assert mongoTemplate.findOne(new Query(Criteria.where("username").is(username)), User.class)
        }
    }

    def "should delete user from mongo" () {
        given:
        def user = new User(username, "password")
        mongoTemplate.save(user)

        when:
        mongoTemplate.remove(user)

        then:
        await().atMost(TWO_SECONDS).until {
            assert !mongoTemplate.findOne(new Query(Criteria.where("username").is(username)), User.class)
        }
    }
}
