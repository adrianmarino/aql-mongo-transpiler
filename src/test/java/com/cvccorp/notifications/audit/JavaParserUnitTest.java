package com.cvccorp.notifications.audit;

import antlr4.AQLLexer;
import antlr4.AQLParser;
import com.cvccorp.notifications.audit.parser.ASTVisitor;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.antlr.v4.runtime.CharStreams.fromString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
public class JavaParserUnitTest {

/*    @Test
    public void whenOneMethodStartsWithUpperCase_thenOneErrorReturned() throws Exception {
        // Prepare
        var input = "public class SampleClass { void DoSomething(){} }";

        var lexer = new Aq(fromString(input));

        var tokens = new CommonTokenStream(lexer);

        var parser = new Java8Parser(tokens);

        ParseTree tree = parser.compilationUnit();


        show(tree, parser);

        var walker = new ParseTreeWalker();

        var uppercaseMethodListener = new UppercaseMethodListener();


        walker.walk(uppercaseMethodListener, tree);

        assertThat(uppercaseMethodListener.getErrors().size(), is(1));
        assertThat(uppercaseMethodListener.getErrors().get(0), is("Method DoSomething is uppercased!"));
    }
*/

    @Test
    void test2() {
        // Prepare
        var input = "field1=1 and field3='hello3'";

        var lexer = new AQLLexer(fromString(input));

        var tokens = new CommonTokenStream(lexer);

        var parser = new AQLParser(tokens);

        var tree = parser.query();

        var listener = new ASTVisitor();
        var walker = new ParseTreeWalker();
        walker.walk(listener, tree);

        // show(tree, parser);
    }
}