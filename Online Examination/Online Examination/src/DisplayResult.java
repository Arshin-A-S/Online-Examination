import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DisplayResult extends JFrame {

    private JPanel resultPanel;
    private JLabel scoreLabel;
    private JButton closeButton;
    private int mark;

    DisplayResult(boolean[] result, UserMenu um){
        this.setContentPane(this.resultPanel);
        this.setTitle("Online Examination");
        this.setSize(750,750);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for(boolean correct : result){
            if(correct){
                mark++;
            }
        }

        scoreLabel.setText("Mark: "+mark);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                um.setVisible(true);
            }
        });
    }
}
