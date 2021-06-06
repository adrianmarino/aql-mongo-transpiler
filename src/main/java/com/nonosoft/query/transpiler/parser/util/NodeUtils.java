package com.nonosoft.query.transpiler.parser.util;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NodeUtils {

    private NodeUtils() {
    }

    public static List<ParseTree> getNodeChildren(RuleContext ctx, Set<String> excluded) {
        int childCount = ctx.getChildCount();

        var result = new ArrayList<ParseTree>();
        for (int i = 0; i < childCount; i++) {
            var child = ctx.getChild(i);
            if (!excluded.contains(ctx.getChild(i).getText())) {
                result.add(child);
            }
        }
        return result;
    }
}
