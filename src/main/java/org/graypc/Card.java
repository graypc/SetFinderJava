package org.graypc;

import javax.swing.*;
import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.File;

/**
 * Created by petergray on 9/29/16.
 */
public class Card extends JPanel implements MouseListener
{
    public void mouseClicked(MouseEvent mouseEvent)
    {
        CardSelector cardSelector = new CardSelector();
        cardSelector.setParameters(mParameters);
        mParameters = cardSelector.showDialog(
                mouseEvent.getXOnScreen(), mouseEvent.getYOnScreen());
        updateIcon();
    }

    public void mousePressed(MouseEvent mouseEvent)
    {}

    public void mouseReleased(MouseEvent mouseEvent)
    {}

    public void mouseEntered(MouseEvent mouseEvent)
    {}

    public void mouseExited(MouseEvent mouseEvent)
    {}

    public enum Color
    {
        CARD_COLOR_RED,
        CARD_COLOR_GREEN,
        CARD_COLOR_PURPLE;
    }

    public enum Shape
    {
        CHARD_SHAPE_OVAL,
        CHARD_SHAPE_DIAMOND,
        CHARD_SHAPE_SQUIGGLE;
    }

    public enum Shade
    {
        CARD_SHADE_FULL,
        CARD_SHADE_HASH,
        CARD_SHADE_EMPTY;
    }

    public Card(String parameterCodes)
    {
        char colorCh = parameterCodes.charAt(1);
        char shapeCh = parameterCodes.charAt(2);
        char shadeCh = parameterCodes.charAt(3);

        mParameters = new CardParameters();
        mParameters.num = Integer.parseInt(parameterCodes.substring(0, 1));

        switch (colorCh)
        {
            case 'R':
                mParameters.color = Color.CARD_COLOR_RED;
                break;

            case 'G':
                mParameters.color = Color.CARD_COLOR_GREEN;
                break;

            default:
                mParameters.color = Color.CARD_COLOR_PURPLE;
                break;
        }

        switch (shapeCh)
        {
            case 'D':
                mParameters.shape = Shape.CHARD_SHAPE_DIAMOND;
                break;

            case 'O':
                mParameters.shape = Shape.CHARD_SHAPE_OVAL;
                break;

            default:
                mParameters.shape = Shape.CHARD_SHAPE_SQUIGGLE;
                break;
        }

        switch (shadeCh)
        {
            case 'F':
                mParameters.shade = Shade.CARD_SHADE_FULL;
                break;

            case 'E':
                mParameters.shade = Shade.CARD_SHADE_EMPTY;
                break;

            default:
                mParameters.shade = Shade.CARD_SHADE_HASH;
        }

        initialize();
    }

    public Card(int num, Shade shade, Color color, Shape shape)
    {
        mParameters = new CardParameters(num, color, shape, shade);
        initialize();
    }

    public boolean isEqual(CardParameters params)
    {
        return mParameters.num == params.num &&
                mParameters.color == params.color &&
                mParameters.shape == params.shape &&
                mParameters.shade == params.shade;
    }

    public String getParametersCode()
    {
        StringBuilder params = new StringBuilder();

        params.append(Integer.toString(mParameters.num));

        String color = "R";
        String shape = "D";
        String shade = "F";

        if (mParameters.color == Color.CARD_COLOR_PURPLE)
            color = "P";

        else if (mParameters.color == Color.CARD_COLOR_GREEN)
            color = "G";

        if (mParameters.shape == Shape.CHARD_SHAPE_OVAL)
            shape = "O";

        else if (mParameters.shape == Shape.CHARD_SHAPE_SQUIGGLE)
            shape = "S";

        if (mParameters.shade == Shade.CARD_SHADE_HASH)
            shade = "H";

        else if (mParameters.shade == Shade.CARD_SHADE_EMPTY)
            shade = "E";

        return params.append(color).append(shape).append(shade).toString();
    }

    public void initialize()
    {
        mIsSolution = false;

        setPreferredSize(new Dimension(xSize, ySize));
        setMaximumSize(new Dimension(xSize, ySize));
        setMinimumSize(new Dimension(xSize, ySize));
        setBackground(java.awt.Color.GRAY);
        setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK, 2, true));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        mLabel = new JLabel();
        add(mLabel);
        updateIcon();
        addMouseListener(this);
        setBackground(java.awt.Color.GRAY);
    }

    public void updateIcon()
    {
        String fileName =  getImageFileName();
        java.net.URL imageURL = getClass().getResource(fileName);
        //System.out.format("Path[%s]", fileName);

        BufferedImage img = null;
        Image imgScaled = null;
        try
        {
            img = ImageIO.read(new File(imageURL.getFile()));
            imgScaled = img.getScaledInstance(xSize - 4, ySize - 16,
                    Image.SCALE_SMOOTH);
            mLabel.setIcon(new ImageIcon(imgScaled));
        }
        catch (IOException e)
        {
            System.out.format("Failed to find file [%s]\n", fileName);
        }
    }

    public String getImageFileName()
    {
        StringBuilder fileName = new StringBuilder();

        switch (mParameters.num)
        {
            case 1:
                fileName.append("1");
                break;

            case 2:
                fileName.append("2");
                break;

            case 3:
                fileName.append("3");
                break;
        }

        switch (mParameters.color)
        {
            case CARD_COLOR_GREEN:
                fileName.append("G");
                break;

            case CARD_COLOR_RED:
                fileName.append("R");
                break;

            case CARD_COLOR_PURPLE:
                fileName.append("P");
                break;
        }

        switch (mParameters.shape)
        {
            case CHARD_SHAPE_DIAMOND:
                fileName.append("D");
                break;

            case CHARD_SHAPE_SQUIGGLE:
                fileName.append("S");
                break;

            case CHARD_SHAPE_OVAL:
                fileName.append("O");
                break;
        }

        switch (mParameters.shade)
        {
            case CARD_SHADE_EMPTY:
                fileName.append("E");
                break;

            case CARD_SHADE_FULL:
                fileName.append("F");
                break;

            case CARD_SHADE_HASH:
                fileName.append("H");
                break;
        }

        fileName.append(".png");

        return fileName.toString();
    }

    public void updateGUI()
    {
        java.awt.Color color = java.awt.Color.GRAY;

        if (mIsSolution)
            color = java.awt.Color.YELLOW;

        setBackground(color);
    }

    public void setSolution(boolean solution)
    {
        mIsSolution = solution;
    }

    public boolean isSolution()
    {
        return mIsSolution;
    }

    class CardSelector extends JDialog
    {
        public CardSelector()
        {
            mPanel = new JPanel();
            //mParameters = new CardParameters();
            mPanel.setPreferredSize(new Dimension(xSize, ySize));
            this.getContentPane().add(mPanel);
            this.setModalityType(ModalityType.APPLICATION_MODAL);
        }

        public void setParameters(CardParameters parameters)
        {
            mParameters = parameters;
        }

        public CardParameters showDialog(int x, int y)
        {
            JComboBox numComboBox = new JComboBox();
            JComboBox colorComboBox = new JComboBox();
            JComboBox shapeComboBox = new JComboBox();
            JComboBox shadeComboBox = new JComboBox();

            mPanel.setLayout(new BoxLayout(mPanel, BoxLayout.Y_AXIS));
            mPanel.add(numComboBox);
            mPanel.add(colorComboBox);
            mPanel.add(shapeComboBox);
            mPanel.add(shadeComboBox);

            numComboBox.addItem(new Integer(1));
            numComboBox.addItem(new Integer(2));
            numComboBox.addItem(new Integer(3));

            colorComboBox.addItem("<html><font color='red'>Red</font></html>");
            colorComboBox.addItem("<html><font color='green'>Green</font></html>");
            colorComboBox.addItem("<html><font color='purple'>Purple</font></html>");

            shapeComboBox.addItem("Oval");
            shapeComboBox.addItem("Diamond");
            shapeComboBox.addItem("Squiggle");

            shadeComboBox.addItem("Full");
            shadeComboBox.addItem("Hash");
            shadeComboBox.addItem("Empty");

            adjustComboBoxSize(numComboBox);
            adjustComboBoxSize(colorComboBox);
            adjustComboBoxSize(shapeComboBox);
            adjustComboBoxSize(shadeComboBox);

            numComboBox.setSelectedIndex(mParameters.num - 1);
            colorComboBox.setSelectedIndex(mParameters.color.ordinal());
            shapeComboBox.setSelectedIndex(mParameters.shape.ordinal());
            shadeComboBox.setSelectedIndex(mParameters.shade.ordinal());

            this.pack();
            this.setLocation(x, y);

            // Block here
            this.setVisible(true);

            // Update mParameters with the combobox info
            String colorStr = (String)colorComboBox.getSelectedItem();
            String shapeStr = (String)shapeComboBox.getSelectedItem();
            String shadeStr = (String)shadeComboBox.getSelectedItem();

            mParameters.num = (Integer)numComboBox.getSelectedItem();

            // Set the color
            if (colorStr.contains("Red"))
                mParameters.color = Color.CARD_COLOR_RED;

            else if (colorStr.contains("Green"))
                mParameters.color = Color.CARD_COLOR_GREEN;

            else
                mParameters.color = Color.CARD_COLOR_PURPLE;

            // Set the shape
            if (shapeStr.contains("Oval"))
                mParameters.shape = Shape.CHARD_SHAPE_OVAL;

            else if (shapeStr.contains("Diamond"))
                mParameters.shape = Shape.CHARD_SHAPE_DIAMOND;

            else
                mParameters.shape = Shape.CHARD_SHAPE_SQUIGGLE;

            // Set the shade
            if (shadeStr.contains("Full"))
                mParameters.shade = Shade.CARD_SHADE_FULL;

            else if (shadeStr.contains("Hash"))
                mParameters.shade = Shade.CARD_SHADE_HASH;

            else
                mParameters.shade = Shade.CARD_SHADE_EMPTY;

            return mParameters;
        }

        public void adjustComboBoxSize(JComboBox comboBox)
        {
            comboBox.setMinimumSize(new Dimension(60, 30));
            comboBox.setPreferredSize(new Dimension(60, 30));
        }
        JPanel mPanel;
    }

    private boolean mIsSolution;
    private JLabel mLabel;
    public static int xSize = 100;
    public static int ySize = 150;

    CardParameters mParameters;
}

class CardParameters
{
    public CardParameters(
            int cNum, Card.Color cColor, Card.Shape cShape, Card.Shade cShade)
    {
        num = cNum;
        color = cColor;
        shape = cShape;
        shade = cShade;
    }

    public CardParameters()
    {
    }

    public int num;
    public Card.Color color;
    public Card.Shape shape;
    public Card.Shade shade;
}
