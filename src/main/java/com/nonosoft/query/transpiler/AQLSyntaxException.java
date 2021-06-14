package com.nonosoft.query.transpiler;

import java.util.List;

import static java.lang.String.format;

public class AQLSyntaxException extends RuntimeException {
    public AQLSyntaxException(List<String> errors) {
        super(format("Syntax errors: %s", errors));
    }
}
