import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegisterForm extends JFrame{
    private JPanel registerPanel;
    private JTextField usernameField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton registerButton;
    private JButton cancelButton;

    public RegisterForm(WelcomeMenu wm) {

        //Database Connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_examination_system", "root", "1234");

            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = usernameField.getText().strip();
                    String pass = new String(passwordField1.getPassword()).strip();
                    String confirmPass = new String(passwordField2.getPassword()).strip();

                    if (pass.equals(confirmPass)) {
                        try {
                            // Check if username exists
                            String getIdQuery = "select user_id from users where username=?";
                            PreparedStatement checkUserStmt = con.prepareStatement(getIdQuery);
                            checkUserStmt.setString(1, username);
                            ResultSet rsId = checkUserStmt.executeQuery();

                            if (!rsId.next()) {
                                // Insert record query
                                String insertUser = "insert into users (username, passwd) values(?, ?)";
                                String insertUserProfile = "insert into user_profile (user_id, user_name) values(?, ?)";
                                PreparedStatement insProfile = con.prepareStatement(insertUserProfile);
                                PreparedStatement ins = con.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
                                ins.setString(1, username);
                                ins.setString(2, pass);
                                int recordInserted = ins.executeUpdate();
                                if (recordInserted > 0) {
                                    ResultSet generatedKeys = ins.getGeneratedKeys();
                                    if (generatedKeys.next()) {
                                        int newUserId = generatedKeys.getInt(1);
                                        //Inserting records into user profile table
                                        insProfile.setInt(1, newUserId);
                                        insProfile.setString(2, username);
                                        insProfile.executeUpdate();
                                        JOptionPane.showMessageDialog(RegisterForm.this, "User registered successfully with ID: " + newUserId, "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }
                                dispose();
                                wm.setVisible(true);
                            } else {
                                int userNum = rsId.getInt("user_id");
                                JOptionPane.showMessageDialog(RegisterForm.this, "Username already exists with the user ID: " + userNum, "User Already Exists", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(RegisterForm.this, "Error: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(RegisterForm.this, "Password and Confirm Password are not matching!", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    wm.setVisible(true);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Database Connection Error", JOptionPane.ERROR_MESSAGE);
        }

        this.setContentPane(this.registerPanel);
        this.setSize(750, 750);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
