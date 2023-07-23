import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

class Question {
    private String question;
    private String answer;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}

public class QuizApplication extends JFrame implements ActionListener {
    private JTextArea questionTextArea;
    private JTextField answerTextField;
    private JButton nextButton;
    private ArrayList<Question> questions;
    private int currentQuestionIndex;

    public QuizApplication() {
        setTitle("Quiz Application");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        questions = new ArrayList<>();
        currentQuestionIndex = 0;

        JPanel mainPanel = new JPanel(new BorderLayout());

        questionTextArea = new JTextArea();
        questionTextArea.setEditable(false);
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(questionTextArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        answerTextField = new JTextField();
        mainPanel.add(answerTextField, BorderLayout.SOUTH);

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);

        add(mainPanel, BorderLayout.CENTER);
        add(nextButton, BorderLayout.SOUTH);
    }

    private void createQuestions() {
        questions.clear();
        String input = JOptionPane.showInputDialog("Enter the number of questions you want to create:");
        int numQuestions = Integer.parseInt(input);

        for (int i = 1; i <= numQuestions; i++) {
            String question = JOptionPane.showInputDialog("Enter question " + i + ":");
            String answer = JOptionPane.showInputDialog("Enter the answer to question " + i + ":");
            questions.add(new Question(question, answer));
        }

        currentQuestionIndex = 0;
        displayCurrentQuestion();
    }

    private void displayCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionTextArea.setText("Question " + (currentQuestionIndex + 1) + ": " + currentQuestion.getQuestion());
            answerTextField.setText("");
        } else {
            finishQuiz();
        }
    }

    private void finishQuiz() {
        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Quiz completed!\n\n");

        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String userAnswer = answerTextField.getText().trim();
            String correctAnswer = question.getAnswer();

            resultBuilder.append("Question ").append(i + 1).append(": ").append(question.getQuestion()).append("\n");
            resultBuilder.append("Your Answer: ").append(userAnswer).append("\n");
            resultBuilder.append("Correct Answer: ").append(correctAnswer).append("\n");

            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                score++;
            }

            resultBuilder.append("\n");
        }

        resultBuilder.append("Your score: ").append(score).append("/").append(questions.size());
        JOptionPane.showMessageDialog(this, resultBuilder.toString());
        System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            if (currentQuestionIndex < questions.size()) {
                currentQuestionIndex++;
                displayCurrentQuestion();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                QuizApplication quizApp = new QuizApplication();
                quizApp.createQuestions();
                quizApp.setVisible(true);
            }
        });
    }
}
//Nawal