package com.cvccorp.notifications.audit;

import antlr4.AQLLexer;
import antlr4.AQLParser;
import com.cvccorp.notifications.audit.parser.ASTVisitor;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import static com.cvccorp.notifications.audit.parser.AntRLUtils.show;
import static org.antlr.v4.runtime.CharStreams.fromString;

public class MainClass {

    public static void main(String[] args) {
        // Prepare
        var input = " user.id = 1 and user.name = 'adrian' or user.age = 20 and user.birth between '1981-09-22 21:15:00' and '2020-01-01 10:05:05'";

        var lexer = new AQLLexer(fromString(input));

        var tokens = new CommonTokenStream(lexer);

        var parser = new AQLParser(tokens);

        var tree = parser.query();

        var visitor = new ASTVisitor();
        var walker = new ParseTreeWalker();
        walker.walk(visitor, tree);

        show(tree, parser);
    }
}
