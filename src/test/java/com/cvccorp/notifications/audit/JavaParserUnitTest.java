package com.cvccorp.notifications.audit;

import antlr4.AQLLexer;
import antlr4.AQLParser;
import com.cvccorp.notifications.audit.parser.ASTVisitor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.antlr.v4.runtime.CharStreams.fromString;

@Slf4j
@SpringBootTest
public class JavaParserUnitTest {

    @Test
    void test1() {
        // Prepare
        var query = " user.id = 1 and user.name = 'adrian' or user.age = 20 and user.birth from '1981-09-22 21:15:00' to '2020-01-01 10:05:05' ";
        log.info("Query: {}", query);

        var lexer = new AQLLexer(fromString(query));

        var tokens = new CommonTokenStream(lexer);

        var parser = new AQLParser(tokens);

        var tree = parser.query();

        var visitor = new ASTVisitor();
        var walker = new ParseTreeWalker();
        walker.walk(visitor, tree);
    }
}