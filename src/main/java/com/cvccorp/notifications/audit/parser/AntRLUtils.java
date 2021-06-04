package com.cvccorp.notifications.audit.parser;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;

import javax.swing.*;
import java.util.List;

public class AntRLUtils {

    private AntRLUtils() {
    }

    public static void show(ParseTree tree, Parser parser) {
        //show AST in GUI
        var frame = new JFrame("AST");
        var panel = new JPanel();

        var viewer = new TreeViewer(List.of(parser.getRuleNames()), tree);
        viewer.setScale(3); // Scale a little

        panel.add(viewer);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
