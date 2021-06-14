package com.nonosoft.query.transpiler.parser;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

import java.util.List;

import static java.lang.String.format;


@Slf4j
@AllArgsConstructor
public class AQLErrorListener extends BaseErrorListener {

    private final List<String> errors;

    @Override
    public void syntaxError(
            Recognizer<?, ?> recognizer,
            Object offendingSymbol,
            int line, int charPositionInLine,
            String msg,
            RecognitionException e
    ) {
        var sourceName = recognizer.getInputStream().getSourceName();
        var message = format("l:%s c:%s %s: %s", line, charPositionInLine, sourceName, msg);
        errors.add(message);
        log.error(message);
    }
}
