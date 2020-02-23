package pl.edu.wat.wcy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Window extends JFrame {
    private JButton nextButton;
    private JLabel question;
    private String answer;
    public List<JCheckBox> checkBox;
    private JCheckBox option;
    private ButtonGroup radioGroup;
    public List<JRadioButton> radioButtonList;
    public QuestionType questionType;
    private ActionListener actionListener;

    public Window(Display choose, ActionListener actionListener) {
        super("LOL champion resolver");
        this.actionListener = actionListener;
        this.refresh(choose);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 250,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 300);
        setSize(600, 600);
    }

    public void refresh(Display choose) {
        setLayout(null);
        try {
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(ImageIO.read(
                    new File("src/main/resources/ornn.jpg")))
                    .getImage().getScaledInstance(600, 600, Image.SCALE_DEFAULT));
            setContentPane(new JLabel(imageIcon));
        } catch (IOException e) {
            e.printStackTrace();
        }

        questionType = choose.getQuestionType();
        radioButtonList = new ArrayList<>();
        checkBox = new ArrayList<>();
        question = new JLabel(choose.getQuestion());
        question.setBounds(15, 10, 500, 60);
        question.setForeground(Color.white);
        add(question);
        nextButton = new JButton("Next");
        nextButton.setBounds(400, 100, 100, 40);
        nextButton.addActionListener(actionListener);
        add(nextButton);

        if (choose.getQuestionType() == QuestionType.MULTI_CHOICE) {
            JPanel panel = new JPanel();
            int count = 0;
            for (String variant : choose.variants) {
                option = new JCheckBox(variant);
                checkBox.add(option);
                panel.add(option);
                option.setBounds(25, 70 + 25 * count, 200, 20);
                add(option);
                count++;
            }
        } else if (choose.getQuestionType() == QuestionType.SINGLE_CHOICE) {
            radioGroup = new ButtonGroup();
            int count = 0;
            for (String variant : choose.variants) {
                JRadioButton radioButton;
                radioButton = new JRadioButton(variant);
                radioButton.setBounds(25, 70 + 25 * count, 200, 20);
                radioGroup.add(radioButton);
                radioButtonList.add(radioButton);
                add(radioButton);
                count++;
            }
        } else if (choose.getQuestionType() == QuestionType.YES_NO) {
            radioGroup = new ButtonGroup();
            JRadioButton radioButtonYes = new JRadioButton("Yes");
            radioButtonYes.setBounds(25, 70, 50, 20);
            radioGroup.add(radioButtonYes);
            add(radioButtonYes);
            radioButtonList.add(radioButtonYes);

            JRadioButton radioButtonNo = new JRadioButton("No");
            radioButtonNo.setBounds(25, 95, 50, 20);
            radioGroup.add(radioButtonNo);
            add(radioButtonNo);
            radioButtonList.add(radioButtonNo);
        }

        setVisible(true);
        setSize(600, 600);
    }

    public String getAnswer() {
        for (JRadioButton button : radioButtonList) {
            if (button.isSelected()) {
                answer = button.getText();
            }
        }
        return answer;
    }

    public void finish() {
        nextButton.setVisible(false);
        nextButton = new JButton("Finish");
        nextButton.setBounds(400, 100, 100, 40);
        nextButton.addActionListener(listener -> dispose());
        add(nextButton);
    }

}
