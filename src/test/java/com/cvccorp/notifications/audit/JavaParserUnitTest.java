package com.cvccorp.notifications.audit;

import antlr4.Java8Lexer;
import antlr4.Java8Parser;
import com.cvccorp.notifications.audit.parser.UppercaseMethodListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.antlr.v4.runtime.CharStreams.fromString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class JavaParserUnitTest {

    @Test
    public void whenOneMethodStartsWithUpperCase_thenOneErrorReturned() throws Exception {
        // Prepare
        var input = "public class SampleClass { void DoSomething(){} }";

        var lexer = new Java8Lexer(fromString(input));

        var tokens = new CommonTokenStream(lexer);

        var parser = new Java8Parser(tokens);

        var tree = parser.compilationUnit();

        var walker = new ParseTreeWalker();

        var uppercaseMethodListener = new UppercaseMethodListener();


        walker.walk(uppercaseMethodListener, tree);

        assertThat(uppercaseMethodListener.getErrors().size(), is(1));
        assertThat(uppercaseMethodListener.getErrors().get(0), is("Method DoSomething is uppercased!"));
    }
}