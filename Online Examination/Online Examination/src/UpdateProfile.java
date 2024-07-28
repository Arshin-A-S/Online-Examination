import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UpdateProfile extends JFrame{
    private JTextField usernameField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dobField;
    private JButton changePasswordButton;
    private JButton cancelButton;
    private JButton modifyProfileButton;
    private JButton confirmButton;
    private JPanel profileUpdatePanel;
    private Connection con;
    final private int userId;

    UpdateProfile(UserMenu um, int userId, String username) {
        confirmButton.setVisible(false);
        setFieldsEditable(false);
        this.userId = userId ;
        usernameField.setText(username);
        connectDatabase();
        setDetails();

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                um.setVisible(true);
            }
        });

        modifyProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modifyProfile();
            }
        });
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangePassword(UpdateProfile.this);
            }
        });

        this.setContentPane(this.profileUpdatePanel);
        this.setSize(750,750);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setFieldsEditable(boolean editable){
        usernameField.setEditable(editable);
        firstNameField.setEditable(editable);
        lastNameField.setEditable(editable);
        dobField.setEditable(editable);
    }
    private void setDetails(){
        try{
            String getQuery = "select user_name, first_name, last_name, dob from user_profile where user_id=?";
            PreparedStatement getStatement = con.prepareStatement(getQuery);
            getStatement.setInt(1, userId);
            ResultSet profileDetails = getStatement.executeQuery();
            if(profileDetails.next()){
                usernameField.setText(profileDetails.getString("user_name"));
                firstNameField.setText(profileDetails.getString("first_name"));
                lastNameField.setText(profileDetails.getString("last_name"));
                dobField.setText(String.valueOf(profileDetails.getDate("dob")));
            } else{
                throw new SQLException();
            }

        } catch(SQLException SqlQueryE){
            SqlQueryE.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Query Error!", "Database Query Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void modifyProfile(){
        setFieldsEditable(true);
        confirmButton.setVisible(true);
        changePasswordButton.setVisible(false);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean updated = updateDatabaseProfile();
                if(updated){
                    JOptionPane.showMessageDialog(null, "Profile updated successfully!", "Profile Update", JOptionPane.INFORMATION_MESSAGE);
                    setFieldsEditable(false);
                    confirmButton.setVisible(false);
                    changePasswordButton.setVisible(true);
                    setDetails();
                } else{
                    JOptionPane.showMessageDialog(null, "Failed updating the profile!", "Profile Update", JOptionPane.ERROR_MESSAGE);
                    setFieldsEditable(false);
                    confirmButton.setVisible(false);
                }
            }
        });

    }

    private boolean updateDatabaseProfile(){
        try{
            String username = usernameField.getText().strip();
            String firstName = firstNameField.getText().strip();
            String lastName = lastNameField.getText().strip();
            String dob = dobField.getText().strip();
            String updateQuery = "update user_profile set user_name=?, first_name=?, last_name=?, dob=? where user_id=?";
            PreparedStatement updateStatement = con.prepareStatement(updateQuery);
            updateStatement.setString(1, username);
            updateStatement.setString(2, firstName);
            updateStatement.setString(3, lastName);
            updateStatement.setDate(4, Date.valueOf(dob));
            updateStatement.setInt(5, userId);

            int updated = updateStatement.executeUpdate();
            if(updated > 0){
                return true;
            } else{
                return false;
            }
        } catch(SQLException SqlQueryE){
            SqlQueryE.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Query Error!", "Database Query Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
    private void connectDatabase(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_examination_system", "root", "1234");
        } catch(Exception SqlConnE){
            SqlConnE.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Connection Error: "+SqlConnE.getMessage(), "Database Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
