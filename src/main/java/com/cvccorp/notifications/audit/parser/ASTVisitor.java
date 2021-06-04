package com.cvccorp.notifications.audit.parser;

import antlr4.AQLBaseListener;
import antlr4.AQLParser.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ASTVisitor extends AQLBaseListener {

    @Override
    public void enterStr_eq_expression(Str_eq_expressionContext ctx) {
        var property = ctx.VARIABLE().getText();
        var value = ctx.STRING().getText();
        log.info("Expression: {} = {}", property, value);
        super.enterStr_eq_expression(ctx);
    }

    @Override
    public void enterStr_nq_expression(Str_nq_expressionContext ctx) {
        var property = ctx.VARIABLE().getText();
        var value = ctx.STRING().getText();
        log.info("Expression: {} != {}", property, value);
        super.enterStr_nq_expression(ctx);
    }

    @Override
    public void enterNum_eq_expression(Num_eq_expressionContext ctx) {
        var property = ctx.VARIABLE().getText();
        var value = ctx.NUMBER().getText();
        log.info("Expression: {} = {}", property, value);
        super.enterNum_eq_expression(ctx);
    }

    @Override
    public void enterNum_nq_expression(Num_nq_expressionContext ctx) {
        var property = ctx.VARIABLE().getText();
        var value = ctx.NUMBER().getText();
        log.info("Expression: {} != {}", property, value);
        super.enterNum_nq_expression(ctx);
    }

    @Override
    public void enterNum_gt_expression(Num_gt_expressionContext ctx) {
        var property = ctx.VARIABLE().getText();
        var value = ctx.NUMBER().getText();
        log.info("Expression: {} > {}", property, value);
        super.enterNum_gt_expression(ctx);
    }

    @Override
    public void enterNum_gte_expression(Num_gte_expressionContext ctx) {
        var property = ctx.VARIABLE().getText();
        var value = ctx.NUMBER().getText();
        log.info("Expression: {} >= {}", property, value);
        super.enterNum_gte_expression(ctx);
    }

    @Override
    public void enterNum_lt_expression(Num_lt_expressionContext ctx) {
        var property = ctx.VARIABLE().getText();
        var value = ctx.NUMBER().getText();
        log.info("Expression: {} < {}", property, value);
        super.enterNum_lt_expression(ctx);
    }

    @Override
    public void enterNum_lte_expression(Num_lte_expressionContext ctx) {
        var property = ctx.VARIABLE().getText();
        var value = ctx.NUMBER().getText();
        log.info("Expression: {} <= {}", property, value);
        super.enterNum_lte_expression(ctx);
    }

    @Override
    public void enterDatetime_between_expression(Datetime_between_expressionContext ctx) {
        var property = ctx.VARIABLE().getText();
        var from = ctx.from().getText();
        var to = ctx.to().getText();
        log.info("Expression: {} from {} to {}", property, from, to);
        super.enterDatetime_between_expression(ctx);
    }
}