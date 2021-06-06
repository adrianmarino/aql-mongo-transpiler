package com.nonosoft.query.transpiler.parser.util;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.List;
import java.util.Set;

import static com.nonosoft.query.transpiler.parser.util.NodeUtils.getNodeChildren;

public class NodeWrapper {

    private final RuleContext ctx;

    private final Set<String> excluded;

    private List<ParseTree> brothers;

    private List<ParseTree> children;


    private NodeWrapper(RuleContext ctx, Set<String> excluded) {
        this.ctx = ctx;
        this.excluded = excluded;
    }

    public static NodeWrapper wrap(RuleContext ctx) {
        return new NodeWrapper(ctx, Set.of("and", "or", "(", ")"));
    }

    public static NodeWrapper wrap(RuleContext ctx, Set<String> excluded) {
        return new NodeWrapper(ctx, excluded);
    }

    public List<ParseTree> children() {
        return children == null ? children = getNodeChildren(ctx, excluded) : children;
    }

    public ParseTree firstBrother() {
        return brothers().get(0);
    }

    public ParseTree lastBrother() {
        return brothers().get(brothers().size() - 1);
    }

    public List<ParseTree> brothers() {
        return brothers == null ? brothers = getNodeChildren(ctx.getParent(), excluded) : brothers;
    }
}
