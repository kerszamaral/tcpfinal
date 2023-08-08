package SpendWise.Graphics.PopUps;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import SpendWise.User;
import SpendWise.Graphics.PopUp;
import SpendWise.Graphics.Screen;
import SpendWise.Managers.UserManager;
import SpendWise.Utils.PanelOrder;
import SpendWise.Utils.SignUpLabels;

public class signUp extends PopUp {
    private JTextField[] signUpFields;
    private UserManager userManager;
    private JPanel pnlTop;

    public signUp(Screen parent, String title, UserManager userManager) {
        super(parent, title);
        this.userManager = userManager;
    }

    @Override
    public void run() {
        // Creating the sign up panel and it's fields
        JPanel signUpPanel = new JPanel(new GridLayout(12, 2));
        signUpPanel.setBackground(BACKGROUND_COLOR);
        signUpFields = new JTextField[SignUpLabels.values().length];
        for (SignUpLabels label : SignUpLabels.values()) {
            JLabel txtInfo = new JLabel(label.getLabelName());
            txtInfo.setForeground(Color.WHITE);
            txtInfo.setFont(txtInfo.getFont().deriveFont(Font.BOLD, 14));
            txtInfo.setHorizontalAlignment(JLabel.RIGHT);
            signUpPanel.add(txtInfo);

            boolean isPassword = label.getLabelName().toLowerCase().contains("password");
            signUpFields[label.ordinal()] = isPassword ? new JPasswordField(15) : new JTextField(15);
            signUpPanel.add(signUpFields[label.ordinal()]);

            // Creating the spacers for the layout to look nice
            signUpPanel.add(new JLabel(""));
            signUpPanel.add(new JLabel(""));
        }

        this.setLayout(new BorderLayout());

        JPanel[] blankPanels = Screen.createOffsets((JPanel) this.getContentPane(), 50, 0, 100, 100);
        pnlTop = blankPanels[PanelOrder.NORTH.ordinal()];

        this.add(signUpPanel, BorderLayout.CENTER);

        // Creating the spacers for the layout to look nice
        JPanel pnlSouth = new JPanel(new BorderLayout());
        pnlSouth.setBackground(BACKGROUND_COLOR);

        Screen.createOffsets(pnlSouth, 5, 20, 200, 200);

        // Creating the create account button
        JButton btnCreateAccount = new JButton("Create Account");
        btnCreateAccount.setBackground(Color.BLACK);
        btnCreateAccount.setForeground(BACKGROUND_COLOR);
        btnCreateAccount.addActionListener(e -> this.createUser(e));
        pnlSouth.add(btnCreateAccount, BorderLayout.CENTER);

        this.add(pnlSouth, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void createUser(ActionEvent e) {
        Screen.showErrorMessage(pnlTop, "");

        if (this.singUpFieldsEmpty()) {
            Screen.showErrorMessage(pnlTop, "One or more fields are empty!");
            return;
        }

        if (!this.isEmailValid()) {
            Screen.showErrorMessage(pnlTop, "Invalid e-mail!");
            return;
        }

        if (!this.isPasswordTheSame()) {
            Screen.showErrorMessage(pnlTop, "Passwords do not match!");
            return;
        }

        JTextField txtUsername = signUpFields[SignUpLabels.USERNAME.ordinal()];
        String username = txtUsername.getText();
        String name = signUpFields[SignUpLabels.NAME.ordinal()].getText();
        String email = signUpFields[SignUpLabels.EMAIL.ordinal()].getText();
        String password = new String(((JPasswordField) signUpFields[SignUpLabels.PASSWORD.ordinal()]).getPassword());

        User user = new User(username, name, email, password, 0, 0);

        Screen.setErrorBorder(txtUsername, false);
        if (userManager.createUser(user)) {
            this.dispose();
            JOptionPane.showMessageDialog(this, "User created successfully!");
        } else {
            Screen.showErrorMessage(pnlTop, "Username already taken!");
            Screen.setErrorBorder(txtUsername, true);
        }
    }

    private boolean singUpFieldsEmpty() {
        boolean isAnyFieldEmpty = false;
        for (JTextField field : signUpFields) {
            Screen.setErrorBorder(field, false);
            if (field.getText().isEmpty()) {
                isAnyFieldEmpty = true;
                Screen.setErrorBorder(field, true);
            }
        }
        return isAnyFieldEmpty;
    }

    private boolean isEmailValid() {
        final String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        JTextField emailField = signUpFields[SignUpLabels.EMAIL.ordinal()];
        Screen.setErrorBorder(emailField, false);
        String email = emailField.getText();
        if (email.matches(emailRegex)) {
            return true;
        } else {
            Screen.setErrorBorder(emailField, true);
            return false;
        }
    }

    private boolean isPasswordTheSame() {
        JPasswordField passwordField = (JPasswordField) signUpFields[SignUpLabels.PASSWORD.ordinal()];
        JPasswordField repeatPasswordField = (JPasswordField) signUpFields[SignUpLabels.REPEAT_PASSWORD.ordinal()];

        Screen.setErrorBorder(passwordField, false);
        Screen.setErrorBorder(repeatPasswordField, false);

        String password = new String(passwordField.getPassword());
        String repeatPassword = new String(repeatPasswordField.getPassword());
        if (password.equals(repeatPassword)) {
            return true;
        } else {
            Screen.setErrorBorder(passwordField, true);
            Screen.setErrorBorder(repeatPasswordField, true);
            return false;
        }
    }
}
