import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;


class Exam extends JFrame{
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JLabel questionLabel;
    private JButton submitAndNextButton;
    private JButton skipOrResultButton;
    private JPanel examPanel;
    private JLabel timerLabel;
    private Timer timer;
    private int timeLeft = 300; // 300 seconds or 5 minutes
    private boolean[] result = new boolean[10];
    private int question = 1;
    private ButtonGroup buttonGroup;
    private UserMenu um;
    Exam(UserMenu um){

        this.setContentPane(this.examPanel);
        this.setTitle("Online Examination");
        this.setSize(750,750);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.um = um;
        //Implementing the button group
        buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);
        buttonGroup.add(radioButton3);
        buttonGroup.add(radioButton4);

        //Setting up the timer
        timerLabel.setText(formatTime(timeLeft));
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if(timeLeft > 0){
                    timeLeft--;
                    timerLabel.setText(formatTime(timeLeft));
                } else{
                    timer.stop();
                    TimeUp();
                }
            }
        });
        timer.start();
        //Framing the first question
        setQuestion();

        submitAndNextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(question<10){
                    //Check the answer for current question
                    result[question-1] = checkAnswer();
                    //Displaying the next question
                    nextQuestion();
                } else{
                    //10th Question
                    result[question-1] = checkAnswer();
                    //Displaying the result
                    invokeResult();
                }
            }
        });

        skipOrResultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(question<10){
                    //Displaying the next question
                    nextQuestion();
                } else{
                    //10th Question
                    //Displaying the result
                    invokeResult();
                }
            }
        });
    }

    private String formatTime(int seconds) {
        long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        long secondsLeft = seconds-TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d:%02d", minutes, secondsLeft);
    }

    private void TimeUp() {
        JOptionPane.showMessageDialog(this, "Time is Up!", "Time Up", JOptionPane.WARNING_MESSAGE);
        //Displaying the results
        invokeResult();
    }

    private void setQuestion()
    {
        resetRadioButtons();
        switch (question) {
            case 1:
                questionLabel.setText("Que1: What is 5 + 3?");
                radioButton1.setText("6");
                radioButton2.setText("7");
                radioButton3.setText("8");
                radioButton4.setText("9");
                break;
            case 2:
                questionLabel.setText("Que2: What is 9 - 4?");
                radioButton1.setText("5");
                radioButton2.setText("6");
                radioButton3.setText("4");
                radioButton4.setText("7");
                break;
            case 3:
                questionLabel.setText("Que3: What is 6 * 7?");
                radioButton1.setText("42");
                radioButton2.setText("36");
                radioButton3.setText("48");
                radioButton4.setText("40");
                break;
            case 4:
                questionLabel.setText("Que4: What is 15 / 3?");
                radioButton1.setText("3");
                radioButton2.setText("4");
                radioButton3.setText("5");
                radioButton4.setText("6");
                break;
            case 5:
                questionLabel.setText("Que5: What is 12 % 5?");
                radioButton1.setText("2");
                radioButton2.setText("3");
                radioButton3.setText("4");
                radioButton4.setText("1");
                break;
            case 6:
                questionLabel.setText("Que6: What is the square root of 64?");
                radioButton1.setText("6");
                radioButton2.setText("7");
                radioButton3.setText("8");
                radioButton4.setText("9");
                break;
            case 7:
                questionLabel.setText("Que7: What is 2^3?");
                radioButton1.setText("6");
                radioButton2.setText("7");
                radioButton3.setText("8");
                radioButton4.setText("9");
                break;
            case 8:
                questionLabel.setText("Que8: What is 3 + 5 * 2?");
                radioButton1.setText("11");
                radioButton2.setText("13");
                radioButton3.setText("16");
                radioButton4.setText("10");
                break;
            case 9:
                questionLabel.setText("Que9: What is (4 + 6) / 2?");
                radioButton1.setText("4");
                radioButton2.setText("5");
                radioButton3.setText("6");
                radioButton4.setText("3");
                break;
            case 10:
                questionLabel.setText("Que10: What is 10 - 3 * 2?");
                radioButton1.setText("4");
                radioButton2.setText("5");
                radioButton3.setText("6");
                radioButton4.setText("7");
                break;
        }
    }

    private boolean checkAnswer() {
        switch (question) {
            case 1:
                return(radioButton3.isSelected());
            case 2:
                return(radioButton1.isSelected());
            case 3:
                return(radioButton1.isSelected());
            case 4:
                return(radioButton3.isSelected());
            case 5:
                return(radioButton1.isSelected());
            case 6:
                return(radioButton3.isSelected());
            case 7:
                return(radioButton3.isSelected());
            case 8:
                return(radioButton2.isSelected());
            case 9:
                return(radioButton2.isSelected());
            case 10:
                return(radioButton1.isSelected());
            default:
                return false;
        }
    }

    private void resetRadioButtons() {
        buttonGroup.clearSelection();
    }

    private void invokeResult(){
        dispose();
        setVisible(false);
        new DisplayResult(result, um);
    }

    private void nextQuestion(){
        if(question == 9){
            submitAndNextButton.setText("Submit and Result");
            skipOrResultButton.setText("Skip and Result");
        }
        question++;
        setQuestion();
    }
}
