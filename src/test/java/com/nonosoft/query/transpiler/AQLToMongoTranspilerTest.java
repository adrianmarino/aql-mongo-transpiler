package com.nonosoft.query.transpiler;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
public class AQLToMongoTranspilerTest {

    /**
     * A simply query language
     */
    private final String AQL_QUERY = "(user.id=1 || user.age=20) and (user.name='adrian' or user.birth from ´1981-09-22 21:15:00´ to ´2020-01-01´)";

    private final String MONGO_QUERY = "$and:[$and:[$or:[{'user.id': 1}, {'user.age': 20}]], $and:[$or:[{'user.name': 'adrian'}, {'user.birth': {$from:'1981-09-22 21:15:00', $to:'2020-01-01'}}]]]";

    @Test
    @DisplayName("WHEN transpile a valid AQL query IT returns an equivalent mongodb query")
    void scenario1() {
        // Prepare
        var transpiler = new AQLToMongoTranspiler();

        // Perform
        var query = transpiler.transpile(AQL_QUERY);

        // Asserts
        assertThat(query, is(equalTo(MONGO_QUERY)));

        log.info("AQL: {}", AQL_QUERY);
        log.info("MongoDB: {}", MONGO_QUERY);
    }
}