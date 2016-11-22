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
        JFrame frame = new JFrame();
        CardSelector cardSelector = new CardSelector(frame);
        CardParameters parameters = new CardParameters(mNum, mColor, mShape, mShade);
        cardSelector.setParameters(parameters);
        parameters = cardSelector.showDialog();

        // Start here.  Add gui controls to CardSelector.
        if (parameters != null)
        {
            System.out.format("New parameters.  Num[%d] Color[%s] Shape[%s] Shade[%s]\n",
                    parameters.num, parameters.color, parameters.shape, parameters.shade);
        }

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

    class CardParameters
    {
        public CardParameters(int cNum, Color cColor, Shape cShape, Shade cShade)
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
        public Color color;
        public Shape shape;
        public Shade shade;
    }

    private Shade mShade;
    private Color mColor;
    private Shape mShape;
    private int mNum;
    private boolean mIsSolution;
    private JLabel mLabel;

    public static int xSize = 100;
    public static int ySize = 150;

    public Card(int num, Shade shade, Color color, Shape shape)
    {
        mNum = num;
        mShade = shade;
        mColor = color;
        mShape = shape;
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

        switch (mNum)
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

        switch (mColor)
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

        switch (mShape)
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

        switch (mShade)
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

    class CardSelector extends JDialog
    {
        public CardSelector(Frame frame)
        {
            mPanel = new JPanel();
            mParameters = new CardParameters();
            this.getContentPane().add(mPanel);
        }

        public void setParameters(CardParameters parameters)
        {
            mParameters = parameters;
        }

        public CardParameters showDialog()
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

            colorComboBox.addItem(new JLabel("<html>Text color: <font color='red'>red</font></html>"));
            colorComboBox.addItem(new JLabel("<html>Text color: <font color='green'>green</font></html>"));
            colorComboBox.addItem(new JLabel("<html>Text color: <font color='purple'>purple</font></html>"));

            shapeComboBox.addItem(new JLabel("Oval"));
            shapeComboBox.addItem(new JLabel("Diamond"));
            shapeComboBox.addItem(new JLabel("Squiggle"));

            shadeComboBox.addItem(new JLabel("Full"));
            shadeComboBox.addItem(new JLabel("Hash"));
            shadeComboBox.addItem(new JLabel("Empty"));

            adjustComboBoxSize(numComboBox);
            adjustComboBoxSize(colorComboBox);
            adjustComboBoxSize(shapeComboBox);
            adjustComboBoxSize(shadeComboBox);

            this.pack();

            // Block here
            this.setVisible(true);
            return mParameters;
        }

        public void adjustComboBoxSize(JComboBox comboBox)
        {
            comboBox.setMinimumSize(new Dimension(60, 30));
            comboBox.setPreferredSize(new Dimension(60, 30));
        }
    }

    JPanel mPanel;
    CardParameters mParameters;
}
