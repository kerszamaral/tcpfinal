package SpendWise.Interface.PopUps;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import SpendWise.Interface.PopUp;
import SpendWise.Interface.Screen;
import SpendWise.Logic.User;
import SpendWise.Utils.Offsets;
import SpendWise.Utils.Enums.PanelOrder;
import SpendWise.Utils.Graphics.Alerts;
import SpendWise.Utils.Graphics.Components;
import SpendWise.Utils.Graphics.Panels;

public class CreateGroup extends PopUp {
    private User loggedUser;
    private JTextField groupNameField;
    private Runnable refreshGroups;

    public CreateGroup(Screen parent, String title, User loggedUser, Runnable refresheGroups) {
        super(parent, title);
        this.loggedUser = loggedUser;
        this.refreshGroups = refresheGroups;
        this.setSize((int)(POPUP_WIDTH*0.75), (int) POPUP_HEIGHT - 200);
    }
    
    @Override
    public void run() {
        Offsets offsets = new Offsets(50, 0, 50, 50);
        blankPanels = Panels.initializeOffsets((JPanel) this.getContentPane(), offsets);

        // Creating the sign up panel and it's fields
        JPanel editPanel = new JPanel(new FlowLayout());
        editPanel.setBackground(BACKGROUND_COLOR);

        groupNameField = (JTextField) Components.addTextFieldCenter(editPanel, "Group's name:", "",
                15, false, true);
                
                this.add(editPanel, BorderLayout.CENTER);
                
                // Creating the south panel
        JPanel pnlSouth = new JPanel(new BorderLayout());
        pnlSouth.setBackground(BACKGROUND_COLOR);

        Offsets southOffsets = new Offsets(0, 20, 120,120);
        Panels.initializeOffsets(pnlSouth, southOffsets);
        
        JButton btnCreate = Components.createButton("Create Group", Color.BLACK, BACKGROUND_COLOR, null);
        btnCreate.addActionListener(e -> createGroup(e));
        groupNameField.addActionListener(e -> btnCreate.doClick());

        pnlSouth.add(btnCreate, BorderLayout.CENTER);

        this.add(pnlSouth, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void createGroup(ActionEvent e) {
        String groupName = groupNameField.getText();
        if (groupName.isEmpty()) {
            Alerts.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "Group name cannot be empty");
            Alerts.setErrorBorder(groupNameField, true);
            return;
        }
        if(loggedUser.getGroupManager().findGroup(groupName) != null) {
            Alerts.showErrorMessage(getBlankPanel(PanelOrder.NORTH), "Group name already exists");
            Alerts.setErrorBorder(groupNameField, true);
            return;
        }

        loggedUser.getGroupManager().createGroup(groupName);
        this.dispose();
        refreshGroups.run();
    }
}
