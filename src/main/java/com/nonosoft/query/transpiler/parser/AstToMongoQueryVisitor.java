package com.nonosoft.query.transpiler.parser;

import antlr4.AQLBaseListener;
import antlr4.AQLParser.*;
import com.nonosoft.query.transpiler.parser.util.NodeWrapper;
import org.antlr.v4.runtime.RuleContext;

import static java.lang.String.format;

public class AstToMongoQueryVisitor extends AQLBaseListener {

    private final StringBuilder query;

    public AstToMongoQueryVisitor() {
        query = new StringBuilder();
    }

    public String getQuery() {
        return query.toString();
    }

    private void open(RuleContext ctx, String value) {
        var node = NodeWrapper.wrap(ctx);

        if (node.brothers().size() > 1)
            query.append(node.lastBrother().getText().equals(ctx.getText()) ? ", " : "");

        query.append(value);
    }

    private void close(RuleContext ctx, String value) {
        query.append(value);
    }

    private void addExpression(RuleContext ctx, String property, String value) {
        open(ctx, format("{'%s': %s}", property, value));
    }

    @Override
    public void enterParExpression(ParExpressionContext ctx) {
        open(ctx, "{ $and:[");
        super.enterParExpression(ctx);
    }

    @Override
    public void enterOrExpression(OrExpressionContext ctx) {
        open(ctx, "{ $or:[");
        super.enterOrExpression(ctx);
    }

    @Override
    public void enterAndExpression(AndExpressionContext ctx) {
        open(ctx, "{ $and:[");
        super.enterAndExpression(ctx);
    }

    @Override
    public void exitParExpression(ParExpressionContext ctx) {
        close(ctx, "] }");
        super.exitParExpression(ctx);
    }

    @Override
    public void exitOrExpression(OrExpressionContext ctx) {
        close(ctx, "] }");
        super.exitOrExpression(ctx);
    }

    @Override
    public void exitAndExpression(AndExpressionContext ctx) {
        close(ctx, "] }");
        super.exitAndExpression(ctx);
    }

    @Override
    public void enterStrEqExpression(StrEqExpressionContext ctx) {
        addExpression(ctx, ctx.PROPERTY().getText(), ctx.STRING().getText());
        super.enterStrEqExpression(ctx);
    }

    @Override
    public void enterStrNotEqExpression(StrNotEqExpressionContext ctx) {
        addExpression(
                ctx,
                ctx.PROPERTY().getText(),
                format("{$ne: %s}", ctx.STRING().getText())
        );
        super.enterStrNotEqExpression(ctx);
    }

    @Override
    public void enterNumEqExpression(NumEqExpressionContext ctx) {
        addExpression(ctx, ctx.PROPERTY().getText(), ctx.NUMBER().getText());
        super.enterNumEqExpression(ctx);
    }

    @Override
    public void enterNumNotEqExpression(NumNotEqExpressionContext ctx) {
        addExpression(
                ctx,
                ctx.PROPERTY().getText(),
                format("{$ne: %s}", ctx.NUMBER().getText())
        );
        super.enterNumNotEqExpression(ctx);
    }

    @Override
    public void enterNumGTExpression(NumGTExpressionContext ctx) {
        addExpression(
                ctx,
                ctx.PROPERTY().getText(),
                format("{$gt: %s}", ctx.NUMBER().getText())
        );
        super.enterNumGTExpression(ctx);
    }

    @Override
    public void enterNumGTEqExpression(NumGTEqExpressionContext ctx) {
        addExpression(
                ctx,
                ctx.PROPERTY().getText(),
                format("{$gte: %s}", ctx.NUMBER().getText())
        );
        super.enterNumGTEqExpression(ctx);
    }

    @Override
    public void enterNumLTExpression(NumLTExpressionContext ctx) {
        addExpression(
                ctx,
                ctx.PROPERTY().getText(),
                format("{$lt: %s}", ctx.NUMBER().getText())
        );
        super.enterNumLTExpression(ctx);
    }

    @Override
    public void enterNumLTEqExpression(NumLTEqExpressionContext ctx) {
        addExpression(
                ctx,
                ctx.PROPERTY().getText(),
                format("{$lte:'%s}", ctx.NUMBER().getText())
        );
        super.enterNumLTEqExpression(ctx);
    }

    @Override
    public void enterDatetimeBetweenExpression(DatetimeBetweenExpressionContext ctx) {
        addExpression(
                ctx,
                ctx.PROPERTY().getText(),
                format(
                        "{$gte: new ISODate('%s'), $lte: new ISODate('%s')}",
                        ctx.DATE_TIME(0).getText().replace("´", ""),
                        ctx.DATE_TIME(1).getText().replace("´", "")
                )
        );
        super.enterDatetimeBetweenExpression(ctx);
    }
}