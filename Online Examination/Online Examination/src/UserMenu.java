import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserMenu extends JFrame{
    private JLabel welcomeLabel;
    private JButton exitButton;
    private JButton beginExamButton;
    private JButton logOutButton;
    private JButton updateProfileButton;
    private JPanel userMenuPanel;

    UserMenu(final WelcomeMenu wm, final int userId, final String username){
        this.welcomeLabel.setText("Welcome "+username);

        updateProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new UpdateProfile(UserMenu.this, userId, username);
            }
        });

        beginExamButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Exam(UserMenu.this);
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                wm.setVisible(true);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.setContentPane(this.userMenuPanel);
        this.setSize(750,750);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
