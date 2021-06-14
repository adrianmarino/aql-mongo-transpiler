package com.nonosoft.query.transpiler.parser;

import com.nonosoft.query.transpiler.parser.util.NodeWrapper;
import org.antlr.v4.runtime.RuleContext;

import static java.lang.String.format;

public class MongoQueryBuilder {

    private final StringBuilder query;

    public MongoQueryBuilder() {
        query = new StringBuilder();
    }

    public String getQuery() {
        return query.toString();
    }

    public void add(RuleContext ctx, String operator) {
        var node = NodeWrapper.wrap(ctx);

        if (node.brothers().size() > 1)
            query.append(node.lastBrother().getText().equals(ctx.getText()) ? ", " : "");

        query.append(operator);
    }


    /*
     * ------------------------------------------------------------------------
     * Boolean expressions
     * ------------------------------------------------------------------------
     */

    public void openAnd(RuleContext ctx) {
        open(ctx, "$and");
    }

    public void openOr(RuleContext ctx) {
        open(ctx, "$or");
    }

    public void open(RuleContext ctx, String operator) {
        add(ctx, format("{ %s:[", operator));
    }


    public void close() {
        query.append("] }");
    }

    /*
     * ------------------------------------------------------------------------
     * String/Numeric expressions
     * ------------------------------------------------------------------------
     */

    public void eq(RuleContext ctx, String property, String value) {
        eqExpression(ctx, property, value);
    }

    public void ne(RuleContext ctx, String property, String value) {
        expression(ctx, property, "$ne", value);
    }

    public void gt(RuleContext ctx, String property, String value) {
        expression(ctx, property, "$gt", value);
    }

    public void lt(RuleContext ctx, String property, String value) {
        expression(ctx, property, "$lt", value);
    }

    public void gte(RuleContext ctx, String property, String value) {
        expression(ctx, property, "$gte", value);
    }

    public void lte(RuleContext ctx, String property, String value) {
        expression(ctx, property, "$lte", value);
    }

    public void between(RuleContext ctx, String property, String from, String to) {
        add(ctx, format("{'%s': {$gte: %s, $lte: %s}}", property, from, to));
    }


    /*
     * ------------------------------------------------------------------------
     * Datetime expressions
     * ------------------------------------------------------------------------
     */


    public void datetimeEq(RuleContext ctx, String property, String value) {
        eqExpression(ctx, property, getDateTime(value));
    }

    public void datetimeNe(RuleContext ctx, String property, String value) {
        expression(ctx, property, "$ne", getDateTime(value));
    }

    public void datetimeGt(RuleContext ctx, String property, String value) {
        expression(ctx, property, "$gt", getDateTime(value));
    }

    public void datetimeLt(RuleContext ctx, String property, String value) {
        expression(ctx, property, "$lt", getDateTime(value));
    }

    public void datetimeGte(RuleContext ctx, String property, String value) {
        expression(ctx, property, "$gte", getDateTime(value));
    }

    public void datetimeLte(RuleContext ctx, String property, String value) {
        expression(ctx, property, "$lte", getDateTime(value));
    }

    public void dateTimeBetween(RuleContext ctx, String property, String from, String to) {
        between(ctx, property, getDateTime(from), getDateTime(to));
    }


    /*
     * ------------------------------------------------------------------------
     * Like expression
     * ------------------------------------------------------------------------
     */

    public void like(RuleContext ctx, String property, String value) {
        add(ctx, format("{'%s': new RegExp(%s)}", property, value));
    }


    public void expression(RuleContext ctx, String property, String operator, String value) {
        add(ctx, format("{'%s': {%s: %s} }", property, operator, value));
    }

    public void eqExpression(RuleContext ctx, String property, String value) {
        add(ctx, format("{'%s': %s}", property, value));
    }

    private String getDateTime(String datetime) {
        var value = datetime.replace("´", "");

        var parts = value.split(" ");
        var date = parts[0];
        var time = parts.length > 1 ? parts[1] : "00:00:00";

        return format("new ISODate('%sT%sZ')", date, time);
    }
}
