// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RelicViewScreen.java

package com.megacrit.cardcrawl.screens.compendium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import java.util.Iterator;

public class RelicViewScreen
    implements ScrollBarListener
{
    private static final class RelicCategory extends Enum
    {

        public static RelicCategory[] values()
        {
            return (RelicCategory[])$VALUES.clone();
        }

        public static RelicCategory valueOf(String name)
        {
            return (RelicCategory)Enum.valueOf(com/megacrit/cardcrawl/screens/compendium/RelicViewScreen$RelicCategory, name);
        }

        public static final RelicCategory STARTER;
        public static final RelicCategory COMMON;
        public static final RelicCategory UNCOMMON;
        public static final RelicCategory RARE;
        public static final RelicCategory BOSS;
        public static final RelicCategory EVENT;
        public static final RelicCategory SHOP;
        public static final RelicCategory NONE;
        private static final RelicCategory $VALUES[];

        static 
        {
            STARTER = new RelicCategory("STARTER", 0);
            COMMON = new RelicCategory("COMMON", 1);
            UNCOMMON = new RelicCategory("UNCOMMON", 2);
            RARE = new RelicCategory("RARE", 3);
            BOSS = new RelicCategory("BOSS", 4);
            EVENT = new RelicCategory("EVENT", 5);
            SHOP = new RelicCategory("SHOP", 6);
            NONE = new RelicCategory("NONE", 7);
            $VALUES = (new RelicCategory[] {
                STARTER, COMMON, UNCOMMON, RARE, BOSS, EVENT, SHOP, NONE
            });
        }

        private RelicCategory(String s, int i)
        {
            super(s, i);
        }
    }


    public RelicViewScreen()
    {
        scrollY = START_Y;
        targetY = scrollY;
        scrollLowerBound = (float)Settings.HEIGHT - 100F * Settings.scale;
        scrollUpperBound = 3000F * Settings.scale;
        row = 0;
        col = 0;
        button = new MenuCancelButton();
        hoveredRelic = null;
        clickStartedRelic = null;
        relicGroup = null;
        grabbedScreen = false;
        grabStartY = 0.0F;
        controllerRelicHb = null;
        scrollBar = new ScrollBar(this);
    }

    public void open()
    {
        controllerRelicHb = null;
        if(Settings.isInfo)
            RelicLibrary.unlockAndSeeAllRelics();
        sortOnOpen();
        button.show(TEXT[0]);
        targetY = scrollLowerBound;
        scrollY = (float)Settings.HEIGHT - 400F * Settings.scale;
        CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.RELIC_VIEW;
    }

    private void sortOnOpen()
    {
        RelicLibrary.starterList = RelicLibrary.sortByStatus(RelicLibrary.starterList, false);
        RelicLibrary.commonList = RelicLibrary.sortByStatus(RelicLibrary.commonList, false);
        RelicLibrary.uncommonList = RelicLibrary.sortByStatus(RelicLibrary.uncommonList, false);
        RelicLibrary.rareList = RelicLibrary.sortByStatus(RelicLibrary.rareList, false);
        RelicLibrary.bossList = RelicLibrary.sortByStatus(RelicLibrary.bossList, false);
        RelicLibrary.specialList = RelicLibrary.sortByStatus(RelicLibrary.specialList, false);
        RelicLibrary.shopList = RelicLibrary.sortByStatus(RelicLibrary.shopList, false);
    }

    public void update()
    {
        if(!CardCrawlGame.relicPopup.isOpen)
        {
            updateControllerInput();
            if(Settings.isControllerMode && controllerRelicHb != null)
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
            if(hoveredRelic != null)
            {
                CardCrawlGame.cursor.changeType(com.megacrit.cardcrawl.core.GameCursor.CursorType.INSPECT);
                if(InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed())
                    clickStartedRelic = hoveredRelic;
                if(InputHelper.justReleasedClickLeft || CInputActionSet.select.isJustPressed())
                {
                    CInputActionSet.select.unpress();
                    if(hoveredRelic == clickStartedRelic)
                    {
                        CardCrawlGame.relicPopup.open(hoveredRelic, relicGroup);
                        clickStartedRelic = null;
                    }
                }
            } else
            {
                clickStartedRelic = null;
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
            hoveredRelic = null;
            relicGroup = null;
            updateList(RelicLibrary.starterList);
            updateList(RelicLibrary.commonList);
            updateList(RelicLibrary.uncommonList);
            updateList(RelicLibrary.rareList);
            updateList(RelicLibrary.bossList);
            updateList(RelicLibrary.specialList);
            updateList(RelicLibrary.shopList);
            if(Settings.isControllerMode && controllerRelicHb != null)
                Gdx.input.setCursorPosition((int)controllerRelicHb.cX, (int)((float)Settings.HEIGHT - controllerRelicHb.cY));
        }
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode)
            return;
        RelicCategory category = RelicCategory.NONE;
        int index = 0;
        boolean anyHovered = false;
        Iterator iterator = RelicLibrary.starterList.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(r.hb.hovered)
            {
                anyHovered = true;
                category = RelicCategory.STARTER;
                break;
            }
            index++;
        } while(true);
        if(!anyHovered)
        {
            index = 0;
            Iterator iterator1 = RelicLibrary.commonList.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractRelic r = (AbstractRelic)iterator1.next();
                if(r.hb.hovered)
                {
                    anyHovered = true;
                    category = RelicCategory.COMMON;
                    break;
                }
                index++;
            } while(true);
        }
        if(!anyHovered)
        {
            index = 0;
            Iterator iterator2 = RelicLibrary.uncommonList.iterator();
            do
            {
                if(!iterator2.hasNext())
                    break;
                AbstractRelic r = (AbstractRelic)iterator2.next();
                if(r.hb.hovered)
                {
                    anyHovered = true;
                    category = RelicCategory.UNCOMMON;
                    break;
                }
                index++;
            } while(true);
        }
        if(!anyHovered)
        {
            index = 0;
            Iterator iterator3 = RelicLibrary.rareList.iterator();
            do
            {
                if(!iterator3.hasNext())
                    break;
                AbstractRelic r = (AbstractRelic)iterator3.next();
                if(r.hb.hovered)
                {
                    anyHovered = true;
                    category = RelicCategory.RARE;
                    break;
                }
                index++;
            } while(true);
        }
        if(!anyHovered)
        {
            index = 0;
            Iterator iterator4 = RelicLibrary.bossList.iterator();
            do
            {
                if(!iterator4.hasNext())
                    break;
                AbstractRelic r = (AbstractRelic)iterator4.next();
                if(r.hb.hovered)
                {
                    anyHovered = true;
                    category = RelicCategory.BOSS;
                    break;
                }
                index++;
            } while(true);
        }
        if(!anyHovered)
        {
            index = 0;
            Iterator iterator5 = RelicLibrary.specialList.iterator();
            do
            {
                if(!iterator5.hasNext())
                    break;
                AbstractRelic r = (AbstractRelic)iterator5.next();
                if(r.hb.hovered)
                {
                    anyHovered = true;
                    category = RelicCategory.EVENT;
                    break;
                }
                index++;
            } while(true);
        }
        if(!anyHovered)
        {
            index = 0;
            Iterator iterator6 = RelicLibrary.shopList.iterator();
            do
            {
                if(!iterator6.hasNext())
                    break;
                AbstractRelic r = (AbstractRelic)iterator6.next();
                if(r.hb.hovered)
                {
                    anyHovered = true;
                    category = RelicCategory.SHOP;
                    break;
                }
                index++;
            } while(true);
        }
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$compendium$RelicViewScreen$RelicCategory[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$compendium$RelicViewScreen$RelicCategory = new int[RelicCategory.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$compendium$RelicViewScreen$RelicCategory[RelicCategory.STARTER.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$compendium$RelicViewScreen$RelicCategory[RelicCategory.COMMON.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$compendium$RelicViewScreen$RelicCategory[RelicCategory.UNCOMMON.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$compendium$RelicViewScreen$RelicCategory[RelicCategory.RARE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$compendium$RelicViewScreen$RelicCategory[RelicCategory.BOSS.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$compendium$RelicViewScreen$RelicCategory[RelicCategory.EVENT.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$compendium$RelicViewScreen$RelicCategory[RelicCategory.SHOP.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
            }
        }

        if(!anyHovered)
            Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.starterList.get(0)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.starterList.get(0)).hb.cY);
        else
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.compendium.RelicViewScreen.RelicCategory[category.ordinal()])
            {
            default:
                break;

            case 1: // '\001'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                    break;
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    if((index += 10) > RelicLibrary.starterList.size() - 1)
                    {
                        index %= 10;
                        if(index > RelicLibrary.commonList.size() - 1)
                            index = RelicLibrary.commonList.size() - 1;
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.commonList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.commonList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.commonList.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        index = RelicLibrary.starterList.size() - 1;
                    Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.starterList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.starterList.get(index)).hb.cY);
                    controllerRelicHb = ((AbstractRelic)RelicLibrary.starterList.get(index)).hb;
                    break;
                }
                if(!CInputActionSet.right.isJustPressed() && !CInputActionSet.altRight.isJustPressed())
                    break;
                if(++index > RelicLibrary.starterList.size() - 1)
                    index = 0;
                Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.starterList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.starterList.get(index)).hb.cY);
                controllerRelicHb = ((AbstractRelic)RelicLibrary.starterList.get(index)).hb;
                break;

            case 2: // '\002'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    if((index -= 10) < 0)
                    {
                        index = RelicLibrary.starterList.size() - 1;
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.starterList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.starterList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.starterList.get(index)).hb;
                    } else
                    {
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.commonList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.commonList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.commonList.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    if((index += 10) > RelicLibrary.commonList.size() - 1)
                    {
                        index %= 10;
                        if(index > RelicLibrary.uncommonList.size() - 1)
                            index = RelicLibrary.uncommonList.size() - 1;
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb;
                    } else
                    {
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.commonList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.commonList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.commonList.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        index = RelicLibrary.commonList.size() - 1;
                    Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.commonList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.commonList.get(index)).hb.cY);
                    controllerRelicHb = ((AbstractRelic)RelicLibrary.commonList.get(index)).hb;
                    break;
                }
                if(!CInputActionSet.right.isJustPressed() && !CInputActionSet.altRight.isJustPressed())
                    break;
                if(++index > RelicLibrary.commonList.size() - 1)
                    index = 0;
                Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.commonList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.commonList.get(index)).hb.cY);
                controllerRelicHb = ((AbstractRelic)RelicLibrary.commonList.get(index)).hb;
                break;

            case 3: // '\003'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    if((index -= 10) < 0)
                    {
                        index = RelicLibrary.commonList.size() - 1;
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.commonList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.commonList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.commonList.get(index)).hb;
                    } else
                    {
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    if((index += 10) > RelicLibrary.uncommonList.size() - 1)
                    {
                        index %= 10;
                        if(index > RelicLibrary.rareList.size() - 1)
                            index = RelicLibrary.rareList.size() - 1;
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.rareList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.rareList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.rareList.get(index)).hb;
                    } else
                    {
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        index = RelicLibrary.uncommonList.size() - 1;
                    Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb.cY);
                    controllerRelicHb = ((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb;
                    break;
                }
                if(!CInputActionSet.right.isJustPressed() && !CInputActionSet.altRight.isJustPressed())
                    break;
                if(++index > RelicLibrary.uncommonList.size() - 1)
                    index = 0;
                Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb.cY);
                controllerRelicHb = ((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb;
                break;

            case 4: // '\004'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    if((index -= 10) < 0)
                    {
                        index = RelicLibrary.uncommonList.size() - 1;
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.uncommonList.get(index)).hb;
                    } else
                    {
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.rareList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.rareList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.rareList.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    if((index += 10) > RelicLibrary.rareList.size() - 1)
                    {
                        index %= 10;
                        if(index > RelicLibrary.bossList.size() - 1)
                            index = RelicLibrary.bossList.size() - 1;
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.bossList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.bossList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.bossList.get(index)).hb;
                    } else
                    {
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.rareList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.rareList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.rareList.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        index = RelicLibrary.rareList.size() - 1;
                    Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.rareList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.rareList.get(index)).hb.cY);
                    controllerRelicHb = ((AbstractRelic)RelicLibrary.rareList.get(index)).hb;
                    break;
                }
                if(!CInputActionSet.right.isJustPressed() && !CInputActionSet.altRight.isJustPressed())
                    break;
                if(++index > RelicLibrary.rareList.size() - 1)
                    index = 0;
                Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.rareList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.rareList.get(index)).hb.cY);
                controllerRelicHb = ((AbstractRelic)RelicLibrary.rareList.get(index)).hb;
                break;

            case 5: // '\005'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    if((index -= 10) < 0)
                    {
                        index = RelicLibrary.rareList.size() - 1;
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.rareList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.rareList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.rareList.get(index)).hb;
                    } else
                    {
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.bossList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.bossList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.bossList.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    if((index += 10) > RelicLibrary.bossList.size() - 1)
                    {
                        index %= 10;
                        if(index > RelicLibrary.specialList.size() - 1)
                            index = RelicLibrary.specialList.size() - 1;
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.specialList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.specialList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.specialList.get(index)).hb;
                    } else
                    {
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.bossList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.bossList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.bossList.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        index = RelicLibrary.bossList.size() - 1;
                    Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.bossList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.bossList.get(index)).hb.cY);
                    controllerRelicHb = ((AbstractRelic)RelicLibrary.bossList.get(index)).hb;
                    break;
                }
                if(!CInputActionSet.right.isJustPressed() && !CInputActionSet.altRight.isJustPressed())
                    break;
                if(++index > RelicLibrary.bossList.size() - 1)
                    index = 0;
                Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.bossList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.bossList.get(index)).hb.cY);
                controllerRelicHb = ((AbstractRelic)RelicLibrary.bossList.get(index)).hb;
                break;

            case 6: // '\006'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    if((index -= 10) < 0)
                    {
                        index = RelicLibrary.bossList.size() - 1;
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.bossList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.bossList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.bossList.get(index)).hb;
                    } else
                    {
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.specialList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.specialList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.specialList.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    if((index += 10) > RelicLibrary.specialList.size() - 1)
                    {
                        index %= 10;
                        if(index > RelicLibrary.shopList.size() - 1)
                            index = RelicLibrary.shopList.size() - 1;
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.shopList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.shopList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.shopList.get(index)).hb;
                    } else
                    {
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.specialList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.specialList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.specialList.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        index = RelicLibrary.specialList.size() - 1;
                    Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.specialList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.specialList.get(index)).hb.cY);
                    controllerRelicHb = ((AbstractRelic)RelicLibrary.specialList.get(index)).hb;
                    break;
                }
                if(!CInputActionSet.right.isJustPressed() && !CInputActionSet.altRight.isJustPressed())
                    break;
                if(++index > RelicLibrary.specialList.size() - 1)
                    index = 0;
                Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.specialList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.specialList.get(index)).hb.cY);
                controllerRelicHb = ((AbstractRelic)RelicLibrary.specialList.get(index)).hb;
                break;

            case 7: // '\007'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    if((index -= 10) < 0)
                    {
                        index = RelicLibrary.specialList.size() - 1;
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.specialList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.specialList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.specialList.get(index)).hb;
                        break;
                    }
                    if(index > RelicLibrary.shopList.size() - 1)
                        index = RelicLibrary.shopList.size() - 1;
                    Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.shopList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.shopList.get(index)).hb.cY);
                    controllerRelicHb = ((AbstractRelic)RelicLibrary.shopList.get(index)).hb;
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    if((index += 10) <= RelicLibrary.shopList.size() - 1)
                    {
                        Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.shopList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.shopList.get(index)).hb.cY);
                        controllerRelicHb = ((AbstractRelic)RelicLibrary.shopList.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        index = RelicLibrary.shopList.size() - 1;
                    Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.shopList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.shopList.get(index)).hb.cY);
                    controllerRelicHb = ((AbstractRelic)RelicLibrary.shopList.get(index)).hb;
                    break;
                }
                if(!CInputActionSet.right.isJustPressed() && !CInputActionSet.altRight.isJustPressed())
                    break;
                if(++index > RelicLibrary.shopList.size() - 1)
                    index = 0;
                Gdx.input.setCursorPosition((int)((AbstractRelic)RelicLibrary.shopList.get(index)).hb.cX, Settings.HEIGHT - (int)((AbstractRelic)RelicLibrary.shopList.get(index)).hb.cY);
                controllerRelicHb = ((AbstractRelic)RelicLibrary.shopList.get(index)).hb;
                break;
            }
    }

    private void updateScrolling()
    {
        if(!CardCrawlGame.relicPopup.isOpen)
        {
            int y = InputHelper.mY;
            if(!grabbedScreen)
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
            if(InputHelper.isMouseDown)
                targetY = (float)y - grabStartY;
            else
                grabbedScreen = false;
            scrollY = MathHelper.scrollSnapLerpSpeed(scrollY, targetY);
            resetScrolling();
            updateBarPosition();
        }
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
        if(!CardCrawlGame.relicPopup.isOpen)
        {
            Iterator iterator = list.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractRelic r = (AbstractRelic)iterator.next();
                r.hb.move(r.currentX, r.currentY);
                r.update();
                if(r.hb.hovered)
                {
                    hoveredRelic = r;
                    relicGroup = list;
                }
            } while(true);
        }
    }

    public void render(SpriteBatch sb)
    {
        row = -1;
        col = 0;
        renderList(sb, TEXT[1], TEXT[2], RelicLibrary.starterList);
        renderList(sb, TEXT[3], TEXT[4], RelicLibrary.commonList);
        renderList(sb, TEXT[5], TEXT[6], RelicLibrary.uncommonList);
        renderList(sb, TEXT[7], TEXT[8], RelicLibrary.rareList);
        renderList(sb, TEXT[9], TEXT[10], RelicLibrary.bossList);
        renderList(sb, TEXT[11], TEXT[12], RelicLibrary.specialList);
        renderList(sb, TEXT[13], TEXT[14], RelicLibrary.shopList);
        button.render(sb);
        scrollBar.render(sb);
    }

    private void renderList(SpriteBatch sb, String msg, String desc, ArrayList list)
    {
        row += 2;
        FontHelper.renderSmartText(sb, FontHelper.buttonLabelFont, msg, START_X - 50F * Settings.scale, (scrollY + 4F * Settings.scale) - SPACE * (float)row, 99999F, 0.0F, Settings.GOLD_COLOR);
        FontHelper.renderSmartText(sb, FontHelper.cardDescFont_N, desc, (START_X - 50F * Settings.scale) + FontHelper.getSmartWidth(FontHelper.buttonLabelFont, msg, 99999F, 0.0F), scrollY - 0.0F * Settings.scale - SPACE * (float)row, 99999F, 0.0F, Settings.CREAM_COLOR);
        row++;
        col = 0;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); col++)
        {
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(col == 10)
            {
                col = 0;
                row++;
            }
            r.currentX = START_X + SPACE * (float)col;
            r.currentY = scrollY - SPACE * (float)row;
            if(RelicLibrary.redList.contains(r))
            {
                if(UnlockTracker.isRelicLocked(r.relicId))
                    r.renderLock(sb, Settings.RED_RELIC_COLOR);
                else
                    r.render(sb, false, Settings.RED_RELIC_COLOR);
                continue;
            }
            if(RelicLibrary.greenList.contains(r))
            {
                if(UnlockTracker.isRelicLocked(r.relicId))
                    r.renderLock(sb, Settings.GREEN_RELIC_COLOR);
                else
                    r.render(sb, false, Settings.GREEN_RELIC_COLOR);
                continue;
            }
            if(RelicLibrary.blueList.contains(r))
            {
                if(UnlockTracker.isRelicLocked(r.relicId))
                    r.renderLock(sb, Settings.BLUE_RELIC_COLOR);
                else
                    r.render(sb, false, Settings.BLUE_RELIC_COLOR);
                continue;
            }
            if(RelicLibrary.whiteList.contains(r))
            {
                if(UnlockTracker.isRelicLocked(r.relicId))
                    r.renderLock(sb, Settings.PURPLE_RELIC_COLOR);
                else
                    r.render(sb, false, Settings.PURPLE_RELIC_COLOR);
                continue;
            }
            if(UnlockTracker.isRelicLocked(r.relicId))
                r.renderLock(sb, Settings.TWO_THIRDS_TRANSPARENT_BLACK_COLOR);
            else
                r.render(sb, false, Settings.TWO_THIRDS_TRANSPARENT_BLACK_COLOR);
        }

    }

    public void scrolledUsingBar(float newPercent)
    {
        float newPosition = MathHelper.valueFromPercentBetween(scrollLowerBound, scrollUpperBound, newPercent);
        scrollY = newPosition;
        targetY = newPosition;
        updateBarPosition();
    }

    private void updateBarPosition()
    {
        if(!CardCrawlGame.relicPopup.isOpen)
        {
            float percent = MathHelper.percentFromValueBetween(scrollLowerBound, scrollUpperBound, scrollY);
            scrollBar.parentScrolledToPercent(percent);
        }
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final float SPACE;
    private static final float START_X;
    private static final float START_Y;
    private float scrollY;
    private float targetY;
    private float scrollLowerBound;
    private float scrollUpperBound;
    private int row;
    private int col;
    public MenuCancelButton button;
    private AbstractRelic hoveredRelic;
    private AbstractRelic clickStartedRelic;
    private ArrayList relicGroup;
    private boolean grabbedScreen;
    private float grabStartY;
    private ScrollBar scrollBar;
    private Hitbox controllerRelicHb;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("RelicViewScreen");
        TEXT = uiStrings.TEXT;
        SPACE = 80F * Settings.scale;
        START_X = 600F * Settings.scale;
        START_Y = (float)Settings.HEIGHT - 300F * Settings.scale;
    }
}
