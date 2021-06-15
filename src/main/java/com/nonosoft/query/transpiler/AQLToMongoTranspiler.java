package com.nonosoft.query.transpiler;

import antlr4.AQLLexer;
import antlr4.AQLParser;
import com.nonosoft.query.transpiler.parser.AQLErrorListener;
import com.nonosoft.query.transpiler.parser.AstToMongoQueryVisitor;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.util.ArrayList;

import static com.nonosoft.query.transpiler.parser.util.AntRLUtils.showTree;
import static org.antlr.v4.runtime.CharStreams.fromString;

public class AQLToMongoTranspiler {

    private final AstToMongoQueryVisitor visitor;

    private final ParseTreeWalker walker;

    public AQLToMongoTranspiler() {
        visitor = new AstToMongoQueryVisitor();
        walker = new ParseTreeWalker();
    }

    public void showAST(String query) {
        var parser = getParser(query, new AQLErrorListener(new ArrayList<String>()));
        var tree = parser.query();
        showTree(tree, parser);
    }

    public String transpile(String query) {
        var errors = new ArrayList<String>();

        var parser = getParser(query, new AQLErrorListener(errors));
        var tree = parser.query();

        if (!errors.isEmpty())
            throw new AQLSyntaxException(errors);

        walker.walk(visitor, tree);
        return visitor.getQuery();
    }

    private AQLParser getParser(String query, BaseErrorListener errorListener) {
        var lexer = new AQLLexer(fromString(query));
        lexer.removeErrorListeners();
        lexer.addErrorListener(errorListener);

        var tokens = new CommonTokenStream(lexer);
        var parser = new AQLParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);

        return parser;
    }
}
