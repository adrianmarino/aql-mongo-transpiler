package com.nonosoft.query.transpiler;

public class Main {


    private static final AQLToMongoTranspiler transpiler = new AQLToMongoTranspiler();

    public static void main(String[] args) {
        transpiler.showAST("(name = 'Adrian' or age = 30) and name = 'Maria'");
    }
}
