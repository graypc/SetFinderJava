package org.graypc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

/**
 * Created by graypc on 12/2/16.
 */
public class SetFinder extends JFrame implements WindowListener {
    private List<List<Card>> mCards;

    public SetFinder()
    {
        super("Set Finder");

        XML xmlReader = new XML();
        mCards = xmlReader.readXML();
        createAndShowGUI();
    }

    private void createAndShowGUI()
    {
        //Create and set up the window.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        getContentPane().add(mainPanel);

        // For each row
        for (int rowIndex = 0; rowIndex < mCards.size(); ++rowIndex)
        {
            List<Card> row = mCards.get(rowIndex);

            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
            rowPanel.add(Box.createRigidArea(new Dimension(5, 0)));

            for (int cardIndex = 0; cardIndex < row.size(); ++cardIndex)
            {
                Card card = row.get(cardIndex);

                // Add this card to the row
                rowPanel.add(card);
                rowPanel.add(Box.createRigidArea(new Dimension(5, 0)));
            }

            mainPanel.add(rowPanel);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.X_AXIS));

        JButton solveButton = new JButton("Solve");
        JButton addRowButton = new JButton("Add Row");
        controlsPanel.add(solveButton);
        controlsPanel.add(addRowButton);

        solveButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                findSolution();
            }
        });

        addRowButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                addRow();
            }
        });

        mainPanel.add(controlsPanel);

        //Display the window.
        pack();
        addWindowListener(this);
    }

    private static void addRow()
    {
    }

    private static void findSolution()
    {
    }

    public void windowOpened(WindowEvent windowEvent)
    {}

    public void windowClosing(WindowEvent windowEvent)
    {
        new XML().writeXML(mCards);
    }

    public void windowClosed(WindowEvent windowEvent)
    {}

    public void windowIconified(WindowEvent windowEvent)
    {}

    public void windowDeiconified(WindowEvent windowEvent)
    {}

    public void windowActivated(WindowEvent windowEvent)
    {}

    public void windowDeactivated(WindowEvent windowEvent)
    {}
}
