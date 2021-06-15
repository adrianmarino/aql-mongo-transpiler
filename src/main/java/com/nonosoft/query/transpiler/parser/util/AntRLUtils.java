package com.nonosoft.query.transpiler.parser.util;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.swing.*;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class AntRLUtils {

    private AntRLUtils() {
    }

    public static void showTree(ParseTree tree, Parser parser) {
        var frame = new JFrame("AST");
        var panel = new JPanel();

        var viewer = new TreeViewer(List.of(parser.getRuleNames()), tree);
        viewer.setScale(3); // Plot scale

        panel.add(viewer);

        frame.add(panel);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
