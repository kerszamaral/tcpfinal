package SpendWise.Graphics.Menus;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.*;

import SpendWise.Graphics.Screen;
import SpendWise.Utils.PanelOrder;
import SpendWise.User;

public class AccountMenu extends Screen {
    private JPanel pnlUserData;
    private User loggedUser;
    
    public AccountMenu(User loggedUser) {
        this.loggedUser = loggedUser;
        pnlUserData = new JPanel();
        this.initialize();
    }
    
    @Override
    protected void initialize() {
        super.initialize();

        pnlUserData = super.getBlankPanel(PanelOrder.CENTRAL);
        pnlUserData.setBackground(BACKGROUND_COLOR);
        pnlUserData.setLayout(new BoxLayout(pnlUserData, BoxLayout.Y_AXIS));

        addTextField("Name: ", loggedUser.getName(), 100);
        addTextField("Username: ", loggedUser.getUsername(), 100);
        addTextField("E-mail: ", loggedUser.getEmail(), 100);
        addPasswordField("Password: ", "ABACATINHO", 100);

        addEditButton();
    }

    private void addTextField(String label, String userValue, int width) {

        JLabel lbl = new JLabel(label);
        pnlUserData.add(lbl);

        JTextField textField = new JTextField(userValue, 20);
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(width, textField.getPreferredSize().height));
        pnlUserData.add(textField);

    }

    private void addPasswordField(String label, String userValue, int width) {
        JLabel lbl = new JLabel(label);
        pnlUserData.add(lbl);
        JPasswordField passwordField = new JPasswordField(userValue, 20);
        passwordField.setEditable(false);
        passwordField.setPreferredSize(new Dimension(width, passwordField.getPreferredSize().height));
        pnlUserData.add(passwordField);
    }

    private void addEditButton() {
        
        // Creates the panel that is going to the south of the screen
        JPanel pnlSouth = new JPanel();
        pnlSouth.setLayout(new BorderLayout());
        initializeBlankPanel(pnlSouth, 100, 100);
        
        // Creates the panel that is going to the east of the south panel
        JPanel pnlSouthEast = new JPanel();
        initializeBlankPanel(pnlSouthEast, 100, 100);
        
        // Creates the panels that are going to the north,south and east of the east panel
        JPanel pnlSouthEastNorth = new JPanel();
        initializeBlankPanel(pnlSouthEastNorth, 20, 20);
        JPanel pnlSouthEastSouth = new JPanel();
        initializeBlankPanel(pnlSouthEastSouth, 20, 20);
        JPanel pnlSouthEastEast = new JPanel();
        initializeBlankPanel(pnlSouthEastEast, 5, 20);

        // Adds them to the east panel
        pnlSouthEast.add(pnlSouthEastNorth, BorderLayout.NORTH);
        pnlSouthEast.add(pnlSouthEastSouth, BorderLayout.SOUTH);
        pnlSouthEast.add(pnlSouthEastEast, BorderLayout.EAST);
        
        // Creates the button itself and adds it to the east panel
        JButton btnEdit = new JButton("Edit");
        btnEdit.setBackground(Color.BLACK);
        btnEdit.setForeground(BACKGROUND_COLOR);
        btnEdit.addActionListener(e -> this.edit(e));
        pnlSouthEast.add(btnEdit, BorderLayout.CENTER);   

        // Then, add the east panel to the south panel
        pnlSouth.add(pnlSouthEast, BorderLayout.EAST);
        
        // And, finnaly, add the south panel to the screen
        this.add(pnlSouth, BorderLayout.SOUTH);
    }

    private void edit(ActionEvent e) {
        // TODO implement edit button
        // Criar um pop-up que permite editar os campos
    }

}
