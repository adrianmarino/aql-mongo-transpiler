package com.nonosoft.query.transpiler;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
public class AQLToMongoTranspilerTest {

    private final AQLToMongoTranspiler transpiler = new AQLToMongoTranspiler();

    @Test
    void scenario1() {
        test("name = 'Adrian'", "{'name': 'Adrian'}");
    }

    @Test
    void scenario2() {
        test("age = 10", "{'age': 10}");
    }

    @Test
    void scenario3() {
        // Prepare
        var origin = "birth_date from ´1981-09-22´ to ´2020-01-01´";
        var expectedTarget = "{'birth_date': {$gte: new ISODate('1981-09-22'), $lte: new ISODate('2020-01-01')}}";

        test(origin, expectedTarget);
    }

    @Test
    void scenario4() {
        // Prepare
        var origin = "birth_date = ´1981-09-22´";
        var expectedTarget = "{'birth_date': {$eq: new ISODate('1981-09-22')}}";

        test(origin, expectedTarget);
    }

    @Test
    void scenario5() {
        // Prepare
        var origin = "birth_date != ´1981-09-22´";
        var expectedTarget = "{'birth_date': {$ne: new ISODate('1981-09-22')}}";

        test(origin, expectedTarget);
    }

    @Test
    void scenario6() {
        // Prepare
        var origin = "birth_date > ´1981-09-22´";
        var expectedTarget = "{'birth_date': {$gt: new ISODate('1981-09-22')}}";

        test(origin, expectedTarget);
    }

    @Test
    void scenario7() {
        // Prepare
        var origin = "birth_date >= ´1981-09-22´";
        var expectedTarget = "{'birth_date': {$gte: new ISODate('1981-09-22')}}";

        test(origin, expectedTarget);
    }


    @Test
    void scenario8() {
        // Prepare
        var origin = "birth_date < ´1981-09-22´";
        var expectedTarget = "{'birth_date': {$lt: new ISODate('1981-09-22')}}";

        test(origin, expectedTarget);
    }

    @Test
    void scenario9() {
        // Prepare
        var origin = "birth_date <= ´1981-09-22´";
        var expectedTarget = "{'birth_date': {$lte: new ISODate('1981-09-22')}}";

        test(origin, expectedTarget);
    }

    @Test
    void scenario10() {
        // Prepare
        var origin = "name = 'Adrian' and age = 30";
        var expectedTarget = "{ $and:[{'name': 'Adrian'}, {'age': 30}] }";

        test(origin, expectedTarget);
    }

    @Test
    void scenario11() {
        // Prepare
        var origin = "name = 'Adrian' or age = 30";
        var expectedTarget = "{ $or:[{'name': 'Adrian'}, {'age': 30}] }";

        test(origin, expectedTarget);
    }

    @Test
    void scenario12() {
        // Prepare
        var origin = "(name = 'Adrian')";
        var expectedTarget = "{ $and:[{'name': 'Adrian'}] }";

        test(origin, expectedTarget);
    }


    @Test
    void scenario13() {
        // Prepare
        var origin = "(name = 'Adrian' or age = 30)";
        var expectedTarget = "{ $and:[{ $or:[{'name': 'Adrian'}, {'age': 30}] }] }";

        test(origin, expectedTarget);
    }

    @Test
    void scenario14() {
        // Prepare
        var origin = "(name = 'Adrian' or age = 30) and name = 'Maria'";
        var expectedTarget = "{ $and:[{ $and:[{ $or:[{'name': 'Adrian'}, {'age': 30}] }] }, {'name': 'Maria'}] }";

        test(origin, expectedTarget);
    }


    @Test
    void scenario15() {
        // Prepare
        var origin = "name = 'Adrian' or age = 30 and name = 'Maria'";
        var expectedTarget = "{ $or:[{'name': 'Adrian'}, { $and:[{'age': 30}, {'name': 'Maria'}] }] }";

        test(origin, expectedTarget);
    }

    @Test()
    void scenario16() {
        assertThrows(AQLSyntaxException.class, () -> transpiler.transpile("name . 'Adrian"));
    }

    @Test
    void scenario17() {
        test("name like 'Adr*'", "{'name': new RegExp('Adr*')}");
    }

    private void test(String origin, String expectedTarget) {
        // Perform
        var targetQuery = transpiler.transpile(origin);

        // Asserts
        assertThat(targetQuery, is(equalTo(expectedTarget)));

        log.info("{} ====>> {}", origin, expectedTarget);
    }
}