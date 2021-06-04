package com.cvccorp.notifications.audit.parser;

import antlr4.Java8BaseListener;
import antlr4.Java8Parser;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.isUpperCase;
import static java.lang.String.format;

@Getter
public class UppercaseMethodListener extends Java8BaseListener {

    private final List<String> errors = new ArrayList<>();

    public void enterMethodDeclarator(Java8Parser.MethodDeclaratorContext ctx) {
        var node = ctx.Identifier();
        var methodName = node.getText();

        if (isUpperCase(methodName.charAt(0)))
            errors.add(format("Method %s is uppercased!", methodName));
    }
}