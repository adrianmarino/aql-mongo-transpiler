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
    private final String AQL_QUERY = "(name='Adrian' or document.number='29042902') and birth_date from ´1981-09-22´ to ´2020-01-01´";

    private final String MONGO_QUERY = "{ $and:[{ $and:[{ $or:[{'name': 'Adrian'}, {'document.number': '29042902'}] }] }, {'birth_date': {$gte:'1981-09-22', $lte:'2020-01-01'}}] }";

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