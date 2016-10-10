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
    }

    public void mousePressed(MouseEvent mouseEvent)
    {
    }

    public void mouseReleased(MouseEvent mouseEvent)
    {
    }

    public void mouseEntered(MouseEvent mouseEvent)
    {
    }

    public void mouseExited(MouseEvent mouseEvent)
    {
    }

    public enum Shape
    {
        CHARD_SHAPE_OVAL,
        CHARD_SHAPE_DIAMOND,
        CHARD_SHAPE_SQUIGGLE;
    }

    public enum Color
    {
        CARD_COLOR_RED,
        CARD_COLOR_GREEN,
        CARD_COLOR_PURPLE;
    }

    public enum Shade
    {
        CARD_SHADE_FULL,
        CARD_SHADE_HASH,
        CARD_SHADE_EMPTY;
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
        //mLabel.setIcon(new ImageIcon(getImageFileName()));

        addMouseListener(this);
    }

    public void updateIcon()
    {
        String fileName =  getImageFileName();
        //String fileName = "org/graypc/" + getImageFileName();
        java.net.URL imageURL = getClass().getResource(fileName);
        System.out.format("Path[%s]\n", imageURL.getPath());
        System.out.format("File[%s]\n", imageURL.getFile());

        //File imageFile = new File(imageURL.getFile());

        BufferedImage img = null;
        Image imgScaled = null;
        try
        {
            img = ImageIO.read(new File(imageURL.getFile()));
            imgScaled = img.getScaledInstance(xSize - 4, ySize - 4,
                    Image.SCALE_SMOOTH);
            mLabel.setIcon(new ImageIcon(imgScaled));
        }
        catch (IOException e)
        {
            System.out.format("Failed to find file [%s]\n", fileName);
        }
        /*
        if (imageURL == null)
            System.out.println("NULL");
        else
            mLabel.setIcon(new ImageIcon(imageURL));
            */
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
}
