package pl.edu.wat.wcy;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.QueryResults;
import org.drools.runtime.rule.QueryResultsRow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Controller implements ActionListener {
    private StatefulKnowledgeSession knowledgeSession;
    private Display display;
    private Window window;

    public Controller() {
        KnowledgeBase knowledgeBase = readKnowledgeBase();
        knowledgeSession = knowledgeBase.newStatefulKnowledgeSession();
        display = new Display("Choose your League of Legends champion", QuestionType.NONE);
        window = new Window(display, this);
        knowledgeSession.insert(display);
        knowledgeSession.fireAllRules();
    }

    private KnowledgeBase readKnowledgeBase() {
        KnowledgeBuilder knowledgeBuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        File file = new File("src/main/resources/rules.drl");
        knowledgeBuilder.add(ResourceFactory.newFileResource(file), ResourceType.DRL);
        KnowledgeBuilderErrors errors = knowledgeBuilder.getErrors();
        if (errors.size() > 0) {
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        KnowledgeBase knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
        knowledgeBase.addKnowledgePackages(knowledgeBuilder.getKnowledgePackages());
        return knowledgeBase;
    }

    public void actionPerformed(ActionEvent e) {
        if (window.questionType == QuestionType.SINGLE_CHOICE) {
            Attribute attribute = new Attribute(display.result + window.getAnswer());
            System.out.println(attribute.getContent());
            knowledgeSession.insert(attribute);

        } else if (window.questionType == QuestionType.YES_NO) {
            for (JRadioButton i : window.radioButtonList) {
                if ((i.isSelected()) && i.getText().equals("Tak")) {
                    Attribute attribute = new Attribute(display.result);
                    System.out.println(attribute.getContent());
                    knowledgeSession.insert(attribute);

                }
            }
        } else if (window.questionType == QuestionType.MULTI_CHOICE) {
            for (JCheckBox i : window.checkBox) {
                if (i.isSelected()) {
                    Attribute attribute = new Attribute(display.result + i.getText());
                    System.out.println(attribute.getContent());
                    knowledgeSession.insert(attribute);
                }
            }
        }
        knowledgeSession.fireAllRules();
        QueryResults results = knowledgeSession.getQueryResults("Display");
        for (QueryResultsRow row : results) {
            display = (Display) row.get("display");
        }
        knowledgeSession.insert(display);
        if (display.resultBool) {
            display = new Display("Your champion is: " + display.result, QuestionType.NONE);
            knowledgeSession.insert(display);
            window.refresh(display);
            window.finish();
        } else {
            window.refresh(display);
        }
    }
}
