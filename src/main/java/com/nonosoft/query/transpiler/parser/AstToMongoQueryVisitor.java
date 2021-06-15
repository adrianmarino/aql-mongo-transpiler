package com.nonosoft.query.transpiler.parser;

import antlr4.AQLBaseListener;
import antlr4.AQLParser.*;

public class AstToMongoQueryVisitor extends AQLBaseListener {

    private final MongoQueryBuilder queryBuilder;

    public AstToMongoQueryVisitor() {
        queryBuilder = new MongoQueryBuilder();
    }

    public String getQuery() {
        return queryBuilder.getQuery();
    }

    /*
     * ------------------------------------------------------------------------
     * Boolean expressions
     * ------------------------------------------------------------------------
     */

    @Override
    public void enterParExpression(ParExpressionContext ctx) {
        queryBuilder.openAnd(ctx);
        super.enterParExpression(ctx);
    }

    @Override
    public void enterOrExpression(OrExpressionContext ctx) {
        queryBuilder.openOr(ctx);
        super.enterOrExpression(ctx);
    }

    @Override
    public void enterAndExpression(AndExpressionContext ctx) {
        queryBuilder.openAnd(ctx);
        super.enterAndExpression(ctx);
    }

    @Override
    public void exitParExpression(ParExpressionContext ctx) {
        queryBuilder.close();
        super.exitParExpression(ctx);
    }

    @Override
    public void exitOrExpression(OrExpressionContext ctx) {
        queryBuilder.close();
        super.exitOrExpression(ctx);
    }

    @Override
    public void exitAndExpression(AndExpressionContext ctx) {
        queryBuilder.close();
        super.exitAndExpression(ctx);
    }


    /*
     * ------------------------------------------------------------------------
     * String expressions
     * ------------------------------------------------------------------------
     */

    @Override
    public void enterStrEqExpression(StrEqExpressionContext ctx) {
        queryBuilder.eq(ctx, ctx.PROPERTY().getText(), ctx.STRING().getText());
        super.enterStrEqExpression(ctx);
    }

    @Override
    public void enterStrNotEqExpression(StrNotEqExpressionContext ctx) {
        queryBuilder.ne(ctx, ctx.PROPERTY().getText(), ctx.STRING().getText());
        super.enterStrNotEqExpression(ctx);
    }


    /*
     * ------------------------------------------------------------------------
     * Numeric expressions
     * ------------------------------------------------------------------------
     */

    @Override
    public void enterNumEqExpression(NumEqExpressionContext ctx) {
        queryBuilder.eq(ctx, ctx.PROPERTY().getText(), ctx.NUMBER().getText());
        super.enterNumEqExpression(ctx);
    }

    @Override
    public void enterNumNotEqExpression(NumNotEqExpressionContext ctx) {
        queryBuilder.ne(ctx, ctx.PROPERTY().getText(), ctx.NUMBER().getText());
        super.enterNumNotEqExpression(ctx);
    }

    @Override
    public void enterNumGTExpression(NumGTExpressionContext ctx) {
        queryBuilder.gt(ctx, ctx.PROPERTY().getText(), ctx.NUMBER().getText());
        super.enterNumGTExpression(ctx);
    }

    @Override
    public void enterNumGTEqExpression(NumGTEqExpressionContext ctx) {
        queryBuilder.gte(ctx, ctx.PROPERTY().getText(), ctx.NUMBER().getText());
        super.enterNumGTEqExpression(ctx);
    }

    @Override
    public void enterNumLTExpression(NumLTExpressionContext ctx) {
        queryBuilder.lt(ctx, ctx.PROPERTY().getText(), ctx.NUMBER().getText());
        super.enterNumLTExpression(ctx);
    }

    @Override
    public void enterNumLTEqExpression(NumLTEqExpressionContext ctx) {
        queryBuilder.eq(ctx, ctx.PROPERTY().getText(), ctx.NUMBER().getText());
        super.enterNumLTEqExpression(ctx);
    }


    /*
     * ------------------------------------------------------------------------
     * Datetime expressions
     * ------------------------------------------------------------------------
     */

    @Override
    public void enterDateTimeEqExpression(DateTimeEqExpressionContext ctx) {
        queryBuilder.datetimeEq(ctx, ctx.PROPERTY().getText(), ctx.DATE_TIME().getText());
        super.enterDateTimeEqExpression(ctx);
    }

    @Override
    public void enterDateTimeGTEqExpression(DateTimeGTEqExpressionContext ctx) {
        queryBuilder.datetimeGte(ctx, ctx.PROPERTY().getText(), ctx.DATE_TIME().getText());
        super.enterDateTimeGTEqExpression(ctx);
    }

    @Override
    public void enterDateTimeLTEqExpression(DateTimeLTEqExpressionContext ctx) {
        queryBuilder.datetimeLte(ctx, ctx.PROPERTY().getText(), ctx.DATE_TIME().getText());
        super.enterDateTimeLTEqExpression(ctx);
    }

    @Override
    public void enterDateTimeGTExpression(DateTimeGTExpressionContext ctx) {
        queryBuilder.datetimeGt(ctx, ctx.PROPERTY().getText(), ctx.DATE_TIME().getText());
        super.enterDateTimeGTExpression(ctx);
    }

    @Override
    public void enterDateTimeLTExpression(DateTimeLTExpressionContext ctx) {
        queryBuilder.datetimeLt(ctx, ctx.PROPERTY().getText(), ctx.DATE_TIME().getText());
        super.enterDateTimeLTExpression(ctx);
    }

    @Override
    public void enterDateTimeNotEqExpression(DateTimeNotEqExpressionContext ctx) {
        queryBuilder.datetimeNe(ctx, ctx.PROPERTY().getText(), ctx.DATE_TIME().getText());
        super.enterDateTimeNotEqExpression(ctx);
    }

    @Override
    public void enterDatetimeBetweenExpression(DatetimeBetweenExpressionContext ctx) {
        queryBuilder.datetimeBetween(
                ctx,
                ctx.PROPERTY().getText(),
                ctx.DATE_TIME(0).getText(),
                ctx.DATE_TIME(1).getText()
        );
        super.enterDatetimeBetweenExpression(ctx);
    }

    /*
     * ------------------------------------------------------------------------
     * Like expression
     * ------------------------------------------------------------------------
     */

    @Override
    public void enterLikeExpression(LikeExpressionContext ctx) {
        queryBuilder.like(ctx, ctx.PROPERTY().getText(), ctx.STRING().getText());
        super.enterLikeExpression(ctx);
    }
}