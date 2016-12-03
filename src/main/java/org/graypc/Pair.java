package org.graypc;

/**
 * Created by graypc on 12/2/16.
 */
public class Pair
{
    public Pair(CardParameters p1, CardParameters p2)
    {
        params1 = p1;
        params2 = p2;
        params3 = new CardParameters();

        determineNecessaryParameters();
    }

    private void determineNecessaryNum()
    {
        if (params1.num == params2.num)
            params3.num = params1.num;

        else if (params1.num != 1 && params2.num != 1)
            params3.num = 1;

        else if (params1.num != 2 && params2.num != 2)
            params3.num = 2;

        else
            params3.num = 3;
    }

    private void determineNecessaryColor()
    {
        if (params1.color == params2.color)
            params3.color = params1.color;

        else if (params1.color != Card.Color.CARD_COLOR_RED && params2.color != Card.Color.CARD_COLOR_RED)
            params3.color = Card.Color.CARD_COLOR_RED;

        else if (params1.color != Card.Color.CARD_COLOR_GREEN && params2.color != Card.Color.CARD_COLOR_GREEN)
            params3.color = Card.Color.CARD_COLOR_GREEN;

        else
            params3.color = Card.Color.CARD_COLOR_PURPLE;
    }

    private void determineNecessaryShape()
    {
        if (params1.shape == params2.shape)
            params3.shape = params1.shape;

        else if (params1.shape != Card.Shape.CHARD_SHAPE_OVAL && params2.shape != Card.Shape.CHARD_SHAPE_OVAL)
            params3.shape = Card.Shape.CHARD_SHAPE_OVAL;

        else if (params1.shape != Card.Shape.CHARD_SHAPE_DIAMOND && params2.shape != Card.Shape.CHARD_SHAPE_DIAMOND)
            params3.shape = Card.Shape.CHARD_SHAPE_DIAMOND;

        else
            params3.shape = Card.Shape.CHARD_SHAPE_SQUIGGLE;
    }

    private void determineNecessaryShade()
    {
        if (params1.shade == params2.shade)
            params3.shade = params1.shade;

        else if (params1.shade != Card.Shade.CARD_SHADE_EMPTY && params2.shade != Card.Shade.CARD_SHADE_EMPTY)
            params3.shade = Card.Shade.CARD_SHADE_EMPTY;

        else if (params1.shade != Card.Shade.CARD_SHADE_HASH && params2.shade != Card.Shade.CARD_SHADE_HASH)
            params3.shade = Card.Shade.CARD_SHADE_HASH;

        else
            params3.shade = Card.Shade.CARD_SHADE_FULL;
    }

    private void determineNecessaryParameters()
    {
        determineNecessaryNum();
        determineNecessaryColor();
        determineNecessaryShape();
        determineNecessaryShade();
    }

    CardParameters params1;
    CardParameters params2;
    CardParameters params3;
}
