package org.graypc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Iterator;
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

    private void addRow()
    {
    }

    private void findSolution()
    {
        // Convert the List<List<Card>> to a 1D List
        List<Card> cards = new ArrayList<Card>();

        for (int i = 0; i < mCards.size(); ++i)
            cards.addAll(mCards.get(i));

        // Assume no solutions
        for (int i = 0; i < cards.size(); ++i)
        {
            cards.get(i).setSolution(false);
            cards.get(i).updateGUI();
        }

        Card card1;
        Card card2;

        for (int i = 0; i < cards.size(); ++i)
        {
            card1 = cards.get(i);

            for (int j = i + 1; j < cards.size(); ++j)
            {
                card2 = cards.get(j);
                Pair pair = new Pair(card1.mParameters, card2.mParameters);

                for (int k = 0; k < cards.size(); ++k)
                {
                    if (cards.get(k).isEqual(pair.params3))
                    {
                        card1.setSolution(true);
                        card2.setSolution(true);
                        cards.get(k).setSolution(true);

                        card1.updateGUI();
                        card2.updateGUI();
                        cards.get(k).updateGUI();

                        //System.out.format("MATCH\n");
                    }
                }

                //System.out.format("Card1[%s] Card2[%s]\n",
                 //       card1.getParametersCode(), card2.getParametersCode());

            }
        }
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
