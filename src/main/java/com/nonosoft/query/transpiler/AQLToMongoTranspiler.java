package com.nonosoft.query.transpiler;

import antlr4.AQLLexer;
import antlr4.AQLParser;
import com.nonosoft.query.transpiler.parser.AstToMongoQueryVisitor;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import static com.nonosoft.query.transpiler.parser.util.AntRLUtils.show;
import static org.antlr.v4.runtime.CharStreams.fromString;

public class AQLToMongoTranspiler {

    private final AstToMongoQueryVisitor visitor;

    private final ParseTreeWalker walker;

    public AQLToMongoTranspiler() {
        visitor = new AstToMongoQueryVisitor();
        walker = new ParseTreeWalker();
    }

    public void showAST(String query) {
        var parser = getParser(query);
        var tree = parser.query();
        show(tree, parser);
    }

    public String transpile(String query) {
        var parser = getParser(query);
        var tree = parser.query();

        walker.walk(visitor, tree);
        return visitor.getQuery();
    }


    private AQLParser getParser(String query) {
        var lexer = new AQLLexer(fromString(query));
        var tokens = new CommonTokenStream(lexer);
        return new AQLParser(tokens);
    }
}
