// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardLibSortHeader.java

package com.megacrit.cardcrawl.screens.compendium;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.mainMenu.SortHeaderButton;
import com.megacrit.cardcrawl.screens.mainMenu.SortHeaderButtonListener;

public class CardLibSortHeader
    implements SortHeaderButtonListener
{

    public CardLibSortHeader(CardGroup group)
    {
        justSorted = false;
        selectionIndex = -1;
        selectionColor = new Color(1.0F, 0.95F, 0.5F, 0.0F);
        if(img == null)
            img = ImageMaster.loadImage("images/ui/cardlibrary/selectBox.png");
        this.group = group;
        float xPosition = START_X;
        rarityButton = new SortHeaderButton(TEXT[0], xPosition, 0.0F, this);
        xPosition += SPACE_X;
        typeButton = new SortHeaderButton(TEXT[1], xPosition, 0.0F, this);
        xPosition += SPACE_X;
        costButton = new SortHeaderButton(TEXT[3], xPosition, 0.0F, this);
        if(!Settings.removeAtoZSort)
        {
            xPosition += SPACE_X;
            nameButton = new SortHeaderButton(TEXT[2], xPosition, 0.0F, this);
            buttons = (new SortHeaderButton[] {
                rarityButton, typeButton, costButton, nameButton
            });
        } else
        {
            buttons = (new SortHeaderButton[] {
                rarityButton, typeButton, costButton
            });
        }
    }

    public void setGroup(CardGroup group)
    {
        this.group = group;
        group.sortAlphabetically(true);
        group.sortByRarity(true);
        group.sortByStatus(true);
        SortHeaderButton asortheaderbutton[] = buttons;
        int i = asortheaderbutton.length;
        for(int j = 0; j < i; j++)
        {
            SortHeaderButton button = asortheaderbutton[j];
            button.reset();
        }

    }

    public void update()
    {
        SortHeaderButton asortheaderbutton[] = buttons;
        int i = asortheaderbutton.length;
        for(int j = 0; j < i; j++)
        {
            SortHeaderButton button = asortheaderbutton[j];
            button.update();
        }

    }

    public Hitbox updateControllerInput()
    {
        SortHeaderButton asortheaderbutton[] = buttons;
        int i = asortheaderbutton.length;
        for(int j = 0; j < i; j++)
        {
            SortHeaderButton button = asortheaderbutton[j];
            if(button.hb.hovered)
                return button.hb;
        }

        return null;
    }

    public int getHoveredIndex()
    {
        int retVal = 0;
        SortHeaderButton asortheaderbutton[] = buttons;
        int i = asortheaderbutton.length;
        for(int j = 0; j < i; j++)
        {
            SortHeaderButton button = asortheaderbutton[j];
            if(button.hb.hovered)
                return retVal;
            retVal++;
        }

        return 0;
    }

    public void clearActiveButtons()
    {
        SortHeaderButton asortheaderbutton[] = buttons;
        int i = asortheaderbutton.length;
        for(int j = 0; j < i; j++)
        {
            SortHeaderButton button = asortheaderbutton[j];
            button.setActive(false);
        }

    }

    public void didChangeOrder(SortHeaderButton button, boolean isAscending)
    {
        if(button == rarityButton)
            group.sortByRarity(isAscending);
        else
        if(button == typeButton)
            group.sortByType(isAscending);
        else
        if(button == nameButton)
            group.sortAlphabetically(isAscending);
        else
        if(button == costButton)
            group.sortByCost(isAscending);
        else
            return;
        group.sortByStatus(false);
        justSorted = true;
        button.setActive(true);
    }

    public void render(SpriteBatch sb)
    {
        updateScrollPositions();
        renderButtons(sb);
        renderSelection(sb);
    }

    protected void updateScrollPositions()
    {
        float scrolledY = group.getBottomCard().current_y + 230F * Settings.yScale;
        SortHeaderButton asortheaderbutton[] = buttons;
        int i = asortheaderbutton.length;
        for(int j = 0; j < i; j++)
        {
            SortHeaderButton button = asortheaderbutton[j];
            button.updateScrollPosition(scrolledY);
        }

    }

    protected void renderButtons(SpriteBatch sb)
    {
        SortHeaderButton asortheaderbutton[] = buttons;
        int i = asortheaderbutton.length;
        for(int j = 0; j < i; j++)
        {
            SortHeaderButton b = asortheaderbutton[j];
            b.render(sb);
        }

    }

    protected void renderSelection(SpriteBatch sb)
    {
        for(int i = 0; i < buttons.length; i++)
            if(i == selectionIndex)
            {
                selectionColor.a = 0.7F + MathUtils.cosDeg((System.currentTimeMillis() / 2L) % 360L) / 5F;
                sb.setColor(selectionColor);
                float doop = 1.0F + (1.0F + MathUtils.cosDeg((System.currentTimeMillis() / 2L) % 360L)) / 50F;
                sb.draw(img, buttons[selectionIndex].hb.cX - 80F - (buttons[selectionIndex].textWidth / 2.0F) * Settings.scale, buttons[selectionIndex].hb.cY - 43F, 100F, 43F, 160F + buttons[selectionIndex].textWidth, 86F, Settings.scale * doop, Settings.scale * doop, 0.0F, 0, 0, 200, 86, false, false);
            }

    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public CardGroup group;
    public boolean justSorted;
    public static final float START_X;
    public static final float SPACE_X;
    private SortHeaderButton rarityButton;
    private SortHeaderButton typeButton;
    private SortHeaderButton costButton;
    private SortHeaderButton nameButton;
    public SortHeaderButton buttons[];
    public int selectionIndex;
    private static Texture img;
    private Color selectionColor;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("CardLibSortHeader");
        TEXT = uiStrings.TEXT;
        START_X = 430F * Settings.xScale;
        SPACE_X = 226F * Settings.xScale;
    }
}
