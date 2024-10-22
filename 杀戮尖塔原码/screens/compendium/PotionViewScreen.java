// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PotionViewScreen.java

package com.megacrit.cardcrawl.screens.compendium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class PotionViewScreen
    implements ScrollBarListener
{
    private static final class PotionCategory extends Enum
    {

        public static PotionCategory[] values()
        {
            return (PotionCategory[])$VALUES.clone();
        }

        public static PotionCategory valueOf(String name)
        {
            return (PotionCategory)Enum.valueOf(com/megacrit/cardcrawl/screens/compendium/PotionViewScreen$PotionCategory, name);
        }

        public static final PotionCategory NONE;
        public static final PotionCategory COMMON;
        public static final PotionCategory UNCOMMON;
        public static final PotionCategory RARE;
        private static final PotionCategory $VALUES[];

        static 
        {
            NONE = new PotionCategory("NONE", 0);
            COMMON = new PotionCategory("COMMON", 1);
            UNCOMMON = new PotionCategory("UNCOMMON", 2);
            RARE = new PotionCategory("RARE", 3);
            $VALUES = (new PotionCategory[] {
                NONE, COMMON, UNCOMMON, RARE
            });
        }

        private PotionCategory(String s, int i)
        {
            super(s, i);
        }
    }


    public PotionViewScreen()
    {
        scrollY = Settings.HEIGHT;
        targetY = scrollY;
        scrollUpperBound = (float)Settings.HEIGHT - 100F * Settings.scale;
        scrollLowerBound = (float)Settings.HEIGHT / 2.0F;
        row = 0;
        col = 0;
        button = new MenuCancelButton();
        commonPotions = null;
        uncommonPotions = null;
        rarePotions = null;
        grabbedScreen = false;
        grabStartY = 0.0F;
        controllerPotionHb = null;
        scrollBar = new ScrollBar(this);
    }

    public void open()
    {
        controllerPotionHb = null;
        sortOnOpen();
        button.show(TEXT[0]);
        targetY = scrollUpperBound - 50F * Settings.scale;
        scrollY = Settings.HEIGHT;
        CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.POTION_VIEW;
    }

    private void sortOnOpen()
    {
        commonPotions = PotionHelper.getPotionsByRarity(com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity.COMMON);
        uncommonPotions = PotionHelper.getPotionsByRarity(com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity.UNCOMMON);
        rarePotions = PotionHelper.getPotionsByRarity(com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity.RARE);
    }

    public void update()
    {
        updateControllerInput();
        if(Settings.isModded && Settings.isControllerMode && controllerPotionHb != null)
            if((float)Gdx.input.getY() > (float)Settings.HEIGHT * 0.7F)
            {
                targetY += Settings.SCROLL_SPEED;
                if(targetY > scrollUpperBound)
                    targetY = scrollUpperBound;
            } else
            if((float)Gdx.input.getY() < (float)Settings.HEIGHT * 0.3F)
            {
                targetY -= Settings.SCROLL_SPEED;
                if(targetY < scrollLowerBound)
                    targetY = scrollLowerBound;
            }
        button.update();
        if(button.hb.clicked || InputHelper.pressedEscape)
        {
            InputHelper.pressedEscape = false;
            button.hide();
            CardCrawlGame.mainMenuScreen.panelScreen.refresh();
        }
        boolean isScrollingScrollBar = scrollBar.update();
        if(!isScrollingScrollBar)
            updateScrolling();
        InputHelper.justClickedLeft = false;
        updateList(commonPotions);
        updateList(uncommonPotions);
        updateList(rarePotions);
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode)
            return;
        PotionCategory category = PotionCategory.NONE;
        int index = 0;
        boolean anyHovered = false;
        Iterator iterator = commonPotions.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractPotion r = (AbstractPotion)iterator.next();
            if(r.hb.hovered)
            {
                anyHovered = true;
                category = PotionCategory.COMMON;
                break;
            }
            index++;
        } while(true);
        if(!anyHovered)
        {
            index = 0;
            Iterator iterator1 = uncommonPotions.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractPotion r = (AbstractPotion)iterator1.next();
                if(r.hb.hovered)
                {
                    anyHovered = true;
                    category = PotionCategory.UNCOMMON;
                    break;
                }
                index++;
            } while(true);
        }
        if(!anyHovered)
        {
            index = 0;
            Iterator iterator2 = rarePotions.iterator();
            do
            {
                if(!iterator2.hasNext())
                    break;
                AbstractPotion r = (AbstractPotion)iterator2.next();
                if(r.hb.hovered)
                {
                    anyHovered = true;
                    category = PotionCategory.RARE;
                    break;
                }
                index++;
            } while(true);
        }
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$compendium$PotionViewScreen$PotionCategory[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$compendium$PotionViewScreen$PotionCategory = new int[PotionCategory.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$compendium$PotionViewScreen$PotionCategory[PotionCategory.COMMON.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$compendium$PotionViewScreen$PotionCategory[PotionCategory.UNCOMMON.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$compendium$PotionViewScreen$PotionCategory[PotionCategory.RARE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        if(!anyHovered)
        {
            System.out.println("NONE HOVERED");
            CInputHelper.setCursor(((AbstractPotion)commonPotions.get(0)).hb);
        } else
        {
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.compendium.PotionViewScreen.PotionCategory[category.ordinal()])
            {
            default:
                break;

            case 1: // '\001'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    if(index >= 10)
                        index -= 10;
                    CInputHelper.setCursor(((AbstractPotion)commonPotions.get(index)).hb);
                    controllerPotionHb = ((AbstractPotion)commonPotions.get(index)).hb;
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    if((index += 10) > commonPotions.size() - 1)
                    {
                        index %= 10;
                        if(index > uncommonPotions.size() - 1)
                            index = uncommonPotions.size() - 1;
                        CInputHelper.setCursor(((AbstractPotion)uncommonPotions.get(index)).hb);
                        controllerPotionHb = ((AbstractPotion)uncommonPotions.get(index)).hb;
                    } else
                    {
                        CInputHelper.setCursor(((AbstractPotion)commonPotions.get(index)).hb);
                        controllerPotionHb = ((AbstractPotion)commonPotions.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        index = commonPotions.size() - 1;
                    CInputHelper.setCursor(((AbstractPotion)commonPotions.get(index)).hb);
                    controllerPotionHb = ((AbstractPotion)commonPotions.get(index)).hb;
                    break;
                }
                if(!CInputActionSet.right.isJustPressed() && !CInputActionSet.altRight.isJustPressed())
                    break;
                if(++index > commonPotions.size() - 1)
                    index = 0;
                CInputHelper.setCursor(((AbstractPotion)commonPotions.get(index)).hb);
                controllerPotionHb = ((AbstractPotion)commonPotions.get(index)).hb;
                break;

            case 2: // '\002'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    if((index -= 10) < 0)
                    {
                        index = commonPotions.size() - 1;
                        CInputHelper.setCursor(((AbstractPotion)commonPotions.get(index)).hb);
                        controllerPotionHb = ((AbstractPotion)commonPotions.get(index)).hb;
                    } else
                    {
                        CInputHelper.setCursor(((AbstractPotion)uncommonPotions.get(index)).hb);
                        controllerPotionHb = ((AbstractPotion)uncommonPotions.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    if((index += 10) > uncommonPotions.size() - 1)
                    {
                        index %= 10;
                        if(index > rarePotions.size() - 1)
                            index = rarePotions.size() - 1;
                        CInputHelper.setCursor(((AbstractPotion)rarePotions.get(index)).hb);
                        controllerPotionHb = ((AbstractPotion)rarePotions.get(index)).hb;
                    } else
                    {
                        CInputHelper.setCursor(((AbstractPotion)uncommonPotions.get(index)).hb);
                        controllerPotionHb = ((AbstractPotion)uncommonPotions.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        index = uncommonPotions.size() - 1;
                    CInputHelper.setCursor(((AbstractPotion)uncommonPotions.get(index)).hb);
                    controllerPotionHb = ((AbstractPotion)uncommonPotions.get(index)).hb;
                    break;
                }
                if(!CInputActionSet.right.isJustPressed() && !CInputActionSet.altRight.isJustPressed())
                    break;
                if(++index > uncommonPotions.size() - 1)
                    index = 0;
                CInputHelper.setCursor(((AbstractPotion)uncommonPotions.get(index)).hb);
                controllerPotionHb = ((AbstractPotion)uncommonPotions.get(index)).hb;
                break;

            case 3: // '\003'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    CInputHelper.setCursor(((AbstractPotion)uncommonPotions.get(index)).hb);
                    controllerPotionHb = ((AbstractPotion)uncommonPotions.get(index)).hb;
                    break;
                }
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        index = rarePotions.size() - 1;
                    CInputHelper.setCursor(((AbstractPotion)rarePotions.get(index)).hb);
                    controllerPotionHb = ((AbstractPotion)rarePotions.get(index)).hb;
                    break;
                }
                if(!CInputActionSet.right.isJustPressed() && !CInputActionSet.altRight.isJustPressed())
                    break;
                if(++index > rarePotions.size() - 1)
                    index = 0;
                CInputHelper.setCursor(((AbstractPotion)rarePotions.get(index)).hb);
                controllerPotionHb = ((AbstractPotion)rarePotions.get(index)).hb;
                break;
            }
        }
    }

    private void updateScrolling()
    {
        int y = InputHelper.mY;
        if(!grabbedScreen && Settings.isModded)
        {
            if(InputHelper.scrolledDown)
                targetY += Settings.SCROLL_SPEED;
            else
            if(InputHelper.scrolledUp)
                targetY -= Settings.SCROLL_SPEED;
            if(InputHelper.justClickedLeft)
            {
                grabbedScreen = true;
                grabStartY = (float)y - targetY;
            }
        } else
        if(InputHelper.isMouseDown && Settings.isModded)
            targetY = (float)y - grabStartY;
        else
            grabbedScreen = false;
        scrollY = MathHelper.scrollSnapLerpSpeed(scrollY, targetY);
        resetScrolling();
        updateBarPosition();
    }

    private void resetScrolling()
    {
        if(targetY < scrollLowerBound)
            targetY = MathHelper.scrollSnapLerpSpeed(targetY, scrollLowerBound);
        else
        if(targetY > scrollUpperBound)
            targetY = MathHelper.scrollSnapLerpSpeed(targetY, scrollUpperBound);
    }

    private void updateList(ArrayList list)
    {
        AbstractPotion r;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); r.update())
        {
            r = (AbstractPotion)iterator.next();
            r.hb.update();
            r.hb.move(r.posX, r.posY);
        }

    }

    public void render(SpriteBatch sb)
    {
        row = -1;
        col = 0;
        renderList(sb, TEXT[1], TEXT[2], commonPotions);
        renderList(sb, TEXT[3], TEXT[4], uncommonPotions);
        renderList(sb, TEXT[5], TEXT[6], rarePotions);
        button.render(sb);
        if(Settings.isModded)
            scrollBar.render(sb);
    }

    private void renderList(SpriteBatch sb, String msg, String desc, ArrayList list)
    {
        row += 2;
        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, msg, START_X - 50F * Settings.scale, (scrollY + 4F * Settings.scale) - SPACE * (float)row, 99999F, 0.0F, Settings.GOLD_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, desc, (START_X - 50F * Settings.scale) + FontHelper.getSmartWidth(FontHelper.buttonLabelFont, msg, 99999F, 0.0F), scrollY - 0.0F * Settings.scale - SPACE * (float)row, 99999F, 0.0F, Settings.CREAM_COLOR);
        row++;
        col = 0;
        for(Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            AbstractPotion r = (AbstractPotion)iterator.next();
            if(col == 10)
            {
                col = 0;
                row++;
            }
            r.posX = START_X + SPACE * (float)col;
            r.posY = scrollY - SPACE * (float)row;
            r.labRender(sb);
            col++;
        }

    }

    public void scrolledUsingBar(float newPercent)
    {
        if(!Settings.isModded)
        {
            return;
        } else
        {
            float newPosition = MathHelper.valueFromPercentBetween(scrollLowerBound, scrollUpperBound, newPercent);
            scrollY = newPosition;
            targetY = newPosition;
            updateBarPosition();
            return;
        }
    }

    private void updateBarPosition()
    {
        float percent = MathHelper.percentFromValueBetween(scrollLowerBound, scrollUpperBound, scrollY);
        scrollBar.parentScrolledToPercent(percent);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final float SPACE;
    private static final float START_X;
    private float scrollY;
    private float targetY;
    private float scrollUpperBound;
    private float scrollLowerBound;
    private int row;
    private int col;
    public MenuCancelButton button;
    private ArrayList commonPotions;
    private ArrayList uncommonPotions;
    private ArrayList rarePotions;
    private boolean grabbedScreen;
    private float grabStartY;
    private ScrollBar scrollBar;
    private Hitbox controllerPotionHb;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("PotionViewScreen");
        TEXT = uiStrings.TEXT;
        SPACE = 80F * Settings.scale;
        START_X = 600F * Settings.scale;
    }
}
