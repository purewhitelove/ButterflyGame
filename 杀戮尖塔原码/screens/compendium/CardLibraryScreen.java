// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CardLibraryScreen.java

package com.megacrit.cardcrawl.screens.compendium;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.screens.compendium:
//            CardLibSortHeader

public class CardLibraryScreen
    implements TabBarListener, ScrollBarListener
{
    private static final class CardLibSelectionType extends Enum
    {

        public static CardLibSelectionType[] values()
        {
            return (CardLibSelectionType[])$VALUES.clone();
        }

        public static CardLibSelectionType valueOf(String name)
        {
            return (CardLibSelectionType)Enum.valueOf(com/megacrit/cardcrawl/screens/compendium/CardLibraryScreen$CardLibSelectionType, name);
        }

        public static final CardLibSelectionType NONE;
        public static final CardLibSelectionType FILTERS;
        public static final CardLibSelectionType CARDS;
        private static final CardLibSelectionType $VALUES[];

        static 
        {
            NONE = new CardLibSelectionType("NONE", 0);
            FILTERS = new CardLibSelectionType("FILTERS", 1);
            CARDS = new CardLibSelectionType("CARDS", 2);
            $VALUES = (new CardLibSelectionType[] {
                NONE, FILTERS, CARDS
            });
        }

        private CardLibSelectionType(String s, int i)
        {
            super(s, i);
        }
    }


    public CardLibraryScreen()
    {
        grabbedScreen = false;
        grabStartY = 0.0F;
        currentDiffY = 0.0F;
        scrollLowerBound = -Settings.DEFAULT_SCROLL_LIMIT;
        scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        hoveredCard = null;
        clickStartedCard = null;
        button = new MenuCancelButton();
        redCards = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        greenCards = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        blueCards = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        purpleCards = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        colorlessCards = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        curseCards = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        type = CardLibSelectionType.NONE;
        filterSelectionImg = null;
        selectionIndex = 0;
        controllerCard = null;
        highlightBoxColor = new Color(1.0F, 0.95F, 0.5F, 0.0F);
        drawStartX = Settings.WIDTH;
        drawStartX -= (float)CARDS_PER_LINE * AbstractCard.IMG_WIDTH * 0.75F;
        drawStartX -= (float)(CARDS_PER_LINE - 1) * Settings.CARD_VIEW_PAD_X;
        drawStartX /= 2.0F;
        drawStartX += (AbstractCard.IMG_WIDTH * 0.75F) / 2.0F;
        padX = AbstractCard.IMG_WIDTH * 0.75F + Settings.CARD_VIEW_PAD_X;
        padY = AbstractCard.IMG_HEIGHT * 0.75F + Settings.CARD_VIEW_PAD_Y;
        colorBar = new ColorTabBar(this);
        sortHeader = new CardLibSortHeader(null);
        scrollBar = new ScrollBar(this);
    }

    public void initialize()
    {
        logger.info("Initializing card library screen.");
        redCards.group = CardLibrary.getCardList(com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType.RED);
        greenCards.group = CardLibrary.getCardList(com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType.GREEN);
        blueCards.group = CardLibrary.getCardList(com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType.BLUE);
        purpleCards.group = CardLibrary.getCardList(com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType.PURPLE);
        colorlessCards.group = CardLibrary.getCardList(com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType.COLORLESS);
        curseCards.group = CardLibrary.getCardList(com.megacrit.cardcrawl.helpers.CardLibrary.LibraryType.CURSE);
        visibleCards = redCards;
        sortHeader.setGroup(visibleCards);
        calculateScrollBounds();
    }

    private void setLockStatus()
    {
        lockStatusHelper(redCards);
        lockStatusHelper(greenCards);
        lockStatusHelper(blueCards);
        lockStatusHelper(purpleCards);
        lockStatusHelper(colorlessCards);
        lockStatusHelper(curseCards);
    }

    private void lockStatusHelper(CardGroup group)
    {
        ArrayList toAdd = new ArrayList();
        Iterator i = group.group.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractCard c = (AbstractCard)i.next();
            if(UnlockTracker.isCardLocked(c.cardID))
            {
                AbstractCard tmp = CardLibrary.getCopy(c.cardID);
                tmp.setLocked();
                toAdd.add(tmp);
                i.remove();
            }
        } while(true);
        group.group.addAll(toAdd);
    }

    public void open()
    {
        controllerCard = null;
        if(Settings.isInfo)
            CardLibrary.unlockAndSeeAllCards();
        if(filterSelectionImg == null)
            filterSelectionImg = ImageMaster.loadImage("images/ui/cardlibrary/selectBox.png");
        setLockStatus();
        sortOnOpen();
        button.show(TEXT[0]);
        currentDiffY = scrollLowerBound;
        SingleCardViewPopup.isViewingUpgrade = false;
        CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.CARD_LIBRARY;
    }

    private void sortOnOpen()
    {
        sortHeader.justSorted = true;
        visibleCards.sortAlphabetically(true);
        visibleCards.sortByRarity(true);
        visibleCards.sortByStatus(true);
        for(Iterator iterator = visibleCards.group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            c.drawScale = MathUtils.random(0.6F, 0.65F);
            c.targetDrawScale = 0.75F;
        }

    }

    public void update()
    {
        updateControllerInput();
        if(Settings.isControllerMode && controllerCard != null && !CardCrawlGame.isPopupOpen)
            if((float)Gdx.input.getY() > (float)Settings.HEIGHT * 0.75F)
                currentDiffY += Settings.SCROLL_SPEED;
            else
            if((float)Gdx.input.getY() < (float)Settings.HEIGHT * 0.25F)
                currentDiffY -= Settings.SCROLL_SPEED;
        colorBar.update(visibleCards.getBottomCard().current_y + 230F * Settings.yScale);
        sortHeader.update();
        if(hoveredCard != null)
        {
            CardCrawlGame.cursor.changeType(com.megacrit.cardcrawl.core.GameCursor.CursorType.INSPECT);
            if(InputHelper.justClickedLeft)
                clickStartedCard = hoveredCard;
            if(InputHelper.justReleasedClickLeft && clickStartedCard != null && hoveredCard != null || hoveredCard != null && CInputActionSet.select.isJustPressed())
            {
                if(Settings.isControllerMode)
                    clickStartedCard = hoveredCard;
                InputHelper.justReleasedClickLeft = false;
                CardCrawlGame.cardPopup.open(clickStartedCard, visibleCards);
                clickStartedCard = null;
            }
        } else
        {
            clickStartedCard = null;
        }
        boolean isScrollBarScrolling = scrollBar.update();
        if(!CardCrawlGame.cardPopup.isOpen && !isScrollBarScrolling)
            updateScrolling();
        updateCards();
        button.update();
        if(button.hb.clicked || InputHelper.pressedEscape)
        {
            InputHelper.pressedEscape = false;
            button.hb.clicked = false;
            button.hide();
            CardCrawlGame.mainMenuScreen.panelScreen.refresh();
        }
        if(Settings.isControllerMode && controllerCard != null)
            CInputHelper.setCursor(controllerCard.hb);
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode)
            return;
        selectionIndex = 0;
        boolean anyHovered = false;
        type = CardLibSelectionType.NONE;
        if(colorBar.viewUpgradeHb.hovered)
        {
            anyHovered = true;
            type = CardLibSelectionType.FILTERS;
            selectionIndex = 4;
            controllerCard = null;
        } else
        if(sortHeader.updateControllerInput() != null)
        {
            anyHovered = true;
            controllerCard = null;
            type = CardLibSelectionType.FILTERS;
            selectionIndex = sortHeader.getHoveredIndex();
        } else
        {
            Iterator iterator = visibleCards.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                if(c.hb.hovered)
                {
                    anyHovered = true;
                    type = CardLibSelectionType.CARDS;
                    break;
                }
                selectionIndex++;
            } while(true);
        }
        if(!anyHovered)
        {
            CInputHelper.setCursor(((AbstractCard)visibleCards.group.get(0)).hb);
            return;
        }
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$compendium$CardLibraryScreen$CardLibSelectionType[];
            static final int $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab = new int[com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.CurrentTab.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab[com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.CurrentTab.RED.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab[com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.CurrentTab.GREEN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab[com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.CurrentTab.BLUE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab[com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.CurrentTab.PURPLE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab[com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.CurrentTab.COLORLESS.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$ColorTabBar$CurrentTab[com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.CurrentTab.CURSE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                $SwitchMap$com$megacrit$cardcrawl$screens$compendium$CardLibraryScreen$CardLibSelectionType = new int[CardLibSelectionType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$compendium$CardLibraryScreen$CardLibSelectionType[CardLibSelectionType.CARDS.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$compendium$CardLibraryScreen$CardLibSelectionType[CardLibSelectionType.FILTERS.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$compendium$CardLibraryScreen$CardLibSelectionType[CardLibSelectionType.NONE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.CardLibSelectionType[type.ordinal()])
        {
        case 1: // '\001'
            if((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && visibleCards.size() > CARDS_PER_LINE)
            {
                if(selectionIndex < CARDS_PER_LINE)
                {
                    CInputHelper.setCursor(sortHeader.buttons[0].hb);
                    controllerCard = null;
                    return;
                }
                selectionIndex -= 5;
                CInputHelper.setCursor(((AbstractCard)visibleCards.group.get(selectionIndex)).hb);
                controllerCard = (AbstractCard)visibleCards.group.get(selectionIndex);
            } else
            if((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && visibleCards.size() > CARDS_PER_LINE)
            {
                if(selectionIndex < visibleCards.size() - CARDS_PER_LINE)
                    selectionIndex += CARDS_PER_LINE;
                else
                    selectionIndex = selectionIndex % CARDS_PER_LINE;
                CInputHelper.setCursor(((AbstractCard)visibleCards.group.get(selectionIndex)).hb);
                controllerCard = (AbstractCard)visibleCards.group.get(selectionIndex);
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                if(selectionIndex % CARDS_PER_LINE > 0)
                {
                    selectionIndex--;
                } else
                {
                    selectionIndex += CARDS_PER_LINE - 1;
                    if(selectionIndex > visibleCards.size() - 1)
                        selectionIndex = visibleCards.size() - 1;
                }
                CInputHelper.setCursor(((AbstractCard)visibleCards.group.get(selectionIndex)).hb);
                controllerCard = (AbstractCard)visibleCards.group.get(selectionIndex);
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
            {
                if(selectionIndex % CARDS_PER_LINE < CARDS_PER_LINE - 1)
                {
                    selectionIndex++;
                    if(selectionIndex > visibleCards.size() - 1)
                        selectionIndex -= visibleCards.size() % CARDS_PER_LINE;
                } else
                {
                    selectionIndex -= CARDS_PER_LINE - 1;
                    if(selectionIndex < 0)
                        selectionIndex = 0;
                }
                CInputHelper.setCursor(((AbstractCard)visibleCards.group.get(selectionIndex)).hb);
                controllerCard = (AbstractCard)visibleCards.group.get(selectionIndex);
            }
            break;

        case 2: // '\002'
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                CInputHelper.setCursor(((AbstractCard)visibleCards.group.get(0)).hb);
            else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
            {
                selectionIndex++;
                if(selectionIndex == sortHeader.buttons.length)
                {
                    CInputHelper.setCursor(colorBar.viewUpgradeHb);
                } else
                {
                    if(selectionIndex > sortHeader.buttons.length)
                        selectionIndex = 0;
                    CInputHelper.setCursor(sortHeader.buttons[selectionIndex].hb);
                }
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                selectionIndex--;
                if(selectionIndex == -1)
                {
                    CInputHelper.setCursor(colorBar.viewUpgradeHb);
                } else
                {
                    if(selectionIndex > sortHeader.buttons.length - 1)
                        selectionIndex = sortHeader.buttons.length - 1;
                    CInputHelper.setCursor(sortHeader.buttons[selectionIndex].hb);
                }
            }
            break;
        }
        if(type == CardLibSelectionType.FILTERS)
            sortHeader.selectionIndex = selectionIndex;
        else
            sortHeader.selectionIndex = -1;
    }

    private void updateCards()
    {
        hoveredCard = null;
        int lineNum = 0;
        ArrayList cards = visibleCards.group;
        for(int i = 0; i < cards.size(); i++)
        {
            int mod = i % CARDS_PER_LINE;
            if(mod == 0 && i != 0)
                lineNum++;
            ((AbstractCard)cards.get(i)).target_x = drawStartX + (float)mod * padX;
            ((AbstractCard)cards.get(i)).target_y = (drawStartY + currentDiffY) - (float)lineNum * padY;
            ((AbstractCard)cards.get(i)).update();
            ((AbstractCard)cards.get(i)).updateHoverLogic();
            if(((AbstractCard)cards.get(i)).hb.hovered)
                hoveredCard = (AbstractCard)cards.get(i);
        }

        if(sortHeader.justSorted)
        {
            for(Iterator iterator = cards.iterator(); iterator.hasNext();)
            {
                AbstractCard c = (AbstractCard)iterator.next();
                c.current_x = c.target_x;
                c.current_y = c.target_y;
            }

            sortHeader.justSorted = false;
        }
    }

    private void updateScrolling()
    {
        int y = InputHelper.mY;
        if(!grabbedScreen)
        {
            if(InputHelper.scrolledDown)
                currentDiffY += Settings.SCROLL_SPEED;
            else
            if(InputHelper.scrolledUp)
                currentDiffY -= Settings.SCROLL_SPEED;
            if(InputHelper.justClickedLeft)
            {
                grabbedScreen = true;
                grabStartY = (float)y - currentDiffY;
            }
        } else
        if(InputHelper.isMouseDown)
            currentDiffY = (float)y - grabStartY;
        else
            grabbedScreen = false;
        resetScrolling();
        updateBarPosition();
    }

    private void calculateScrollBounds()
    {
        int size = visibleCards.size();
        int scrollTmp = 0;
        if(size > CARDS_PER_LINE * 2)
        {
            scrollTmp = size / CARDS_PER_LINE - 2;
            if(size % CARDS_PER_LINE != 0)
                scrollTmp++;
            scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT + (float)scrollTmp * padY;
        } else
        {
            scrollUpperBound = Settings.DEFAULT_SCROLL_LIMIT;
        }
    }

    private void resetScrolling()
    {
        if(currentDiffY < scrollLowerBound)
            currentDiffY = MathHelper.scrollSnapLerpSpeed(currentDiffY, scrollLowerBound);
        else
        if(currentDiffY > scrollUpperBound)
            currentDiffY = MathHelper.scrollSnapLerpSpeed(currentDiffY, scrollUpperBound);
    }

    public void render(SpriteBatch sb)
    {
        scrollBar.render(sb);
        colorBar.render(sb, visibleCards.getBottomCard().current_y + 230F * Settings.yScale);
        sortHeader.render(sb);
        renderGroup(sb, visibleCards);
        if(hoveredCard != null)
        {
            hoveredCard.renderHoverShadow(sb);
            hoveredCard.renderInLibrary(sb);
        }
        button.render(sb);
        if(Settings.isControllerMode)
            renderControllerUi(sb);
    }

    private void renderControllerUi(SpriteBatch sb)
    {
        sb.draw(CInputActionSet.pageLeftViewDeck.getKeyImg(), 280F * Settings.xScale - 32F, (sortHeader.group.getBottomCard().current_y + 280F * Settings.yScale) - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        sb.draw(CInputActionSet.pageRightViewExhaust.getKeyImg(), 1640F * Settings.xScale - 32F, (sortHeader.group.getBottomCard().current_y + 280F * Settings.yScale) - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        if(type == CardLibSelectionType.FILTERS && (selectionIndex == 4 || selectionIndex == 3 && Settings.removeAtoZSort))
        {
            highlightBoxColor.a = 0.7F + MathUtils.cosDeg((System.currentTimeMillis() / 2L) % 360L) / 5F;
            sb.setColor(highlightBoxColor);
            float doop = 1.0F + (1.0F + MathUtils.cosDeg((System.currentTimeMillis() / 2L) % 360L)) / 50F;
            sb.draw(filterSelectionImg, colorBar.viewUpgradeHb.cX - 100F, colorBar.viewUpgradeHb.cY - 43F, 100F, 43F, 200F, 86F, Settings.scale * doop * (colorBar.viewUpgradeHb.width / 150F / Settings.scale), Settings.scale * doop, 0.0F, 0, 0, 200, 86, false, false);
        }
    }

    private void renderGroup(SpriteBatch sb, CardGroup group)
    {
        group.renderInLibrary(sb);
        group.renderTip(sb);
    }

    public void didChangeTab(ColorTabBar tabBar, com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.CurrentTab newSelection)
    {
        CardGroup oldSelection = visibleCards;
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.CurrentTab[newSelection.ordinal()])
        {
        case 1: // '\001'
            visibleCards = redCards;
            break;

        case 2: // '\002'
            visibleCards = greenCards;
            break;

        case 3: // '\003'
            visibleCards = blueCards;
            break;

        case 4: // '\004'
            visibleCards = purpleCards;
            break;

        case 5: // '\005'
            visibleCards = colorlessCards;
            break;

        case 6: // '\006'
            visibleCards = curseCards;
            break;
        }
        if(oldSelection != visibleCards)
        {
            sortHeader.setGroup(visibleCards);
            calculateScrollBounds();
        }
        sortHeader.justSorted = true;
        for(Iterator iterator = visibleCards.group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            c.drawScale = MathUtils.random(0.6F, 0.65F);
            c.targetDrawScale = 0.75F;
        }

    }

    public void scrolledUsingBar(float newPercent)
    {
        currentDiffY = MathHelper.valueFromPercentBetween(scrollLowerBound, scrollUpperBound, newPercent);
        updateBarPosition();
    }

    private void updateBarPosition()
    {
        float percent = MathHelper.percentFromValueBetween(scrollLowerBound, scrollUpperBound, currentDiffY);
        scrollBar.parentScrolledToPercent(percent);
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/compendium/CardLibraryScreen.getName());
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static float drawStartX;
    private static float drawStartY;
    private static float padX;
    private static float padY;
    private static final int CARDS_PER_LINE;
    private boolean grabbedScreen;
    private float grabStartY;
    private float currentDiffY;
    private float scrollLowerBound;
    private float scrollUpperBound;
    private AbstractCard hoveredCard;
    private AbstractCard clickStartedCard;
    private ColorTabBar colorBar;
    public MenuCancelButton button;
    private CardGroup redCards;
    private CardGroup greenCards;
    private CardGroup blueCards;
    private CardGroup purpleCards;
    private CardGroup colorlessCards;
    private CardGroup curseCards;
    private CardLibSortHeader sortHeader;
    private CardGroup visibleCards;
    private ScrollBar scrollBar;
    private CardLibSelectionType type;
    private Texture filterSelectionImg;
    private int selectionIndex;
    private AbstractCard controllerCard;
    private Color highlightBoxColor;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("CardLibraryScreen");
        TEXT = uiStrings.TEXT;
        drawStartY = (float)Settings.HEIGHT * 0.66F;
        CARDS_PER_LINE = (int)((float)Settings.WIDTH / (AbstractCard.IMG_WIDTH * 0.75F + Settings.CARD_VIEW_PAD_X * 3F));
    }
}
