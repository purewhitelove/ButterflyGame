// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GremlinMatchGame.java

package com.megacrit.cardcrawl.events.shrines;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GremlinMatchGame extends AbstractImageEvent
{
    private static final class CUR_SCREEN extends Enum
    {

        public static CUR_SCREEN[] values()
        {
            return (CUR_SCREEN[])$VALUES.clone();
        }

        public static CUR_SCREEN valueOf(String name)
        {
            return (CUR_SCREEN)Enum.valueOf(com/megacrit/cardcrawl/events/shrines/GremlinMatchGame$CUR_SCREEN, name);
        }

        public static final CUR_SCREEN INTRO;
        public static final CUR_SCREEN RULE_EXPLANATION;
        public static final CUR_SCREEN PLAY;
        public static final CUR_SCREEN COMPLETE;
        public static final CUR_SCREEN CLEAN_UP;
        private static final CUR_SCREEN $VALUES[];

        static 
        {
            INTRO = new CUR_SCREEN("INTRO", 0);
            RULE_EXPLANATION = new CUR_SCREEN("RULE_EXPLANATION", 1);
            PLAY = new CUR_SCREEN("PLAY", 2);
            COMPLETE = new CUR_SCREEN("COMPLETE", 3);
            CLEAN_UP = new CUR_SCREEN("CLEAN_UP", 4);
            $VALUES = (new CUR_SCREEN[] {
                INTRO, RULE_EXPLANATION, PLAY, COMPLETE, CLEAN_UP
            });
        }

        private CUR_SCREEN(String s, int i)
        {
            super(s, i);
        }
    }


    public GremlinMatchGame()
    {
        super(NAME, DESCRIPTIONS[2], "images/events/matchAndKeep.jpg");
        cardFlipped = false;
        gameDone = false;
        cleanUpCalled = false;
        attemptCount = 5;
        cards = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        waitTimer = 0.0F;
        cardsMatched = 0;
        screen = CUR_SCREEN.INTRO;
        cards.group = initializeCards();
        Collections.shuffle(cards.group, new Random(AbstractDungeon.miscRng.randomLong()));
        imageEventText.setDialogOption(OPTIONS[0]);
        matchedCards = new ArrayList();
    }

    private ArrayList initializeCards()
    {
        ArrayList retVal = new ArrayList();
        ArrayList retVal2 = new ArrayList();
        if(AbstractDungeon.ascensionLevel >= 15)
        {
            retVal.add(AbstractDungeon.getCard(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE).makeCopy());
            retVal.add(AbstractDungeon.getCard(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON).makeCopy());
            retVal.add(AbstractDungeon.getCard(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON).makeCopy());
            retVal.add(AbstractDungeon.returnRandomCurse());
            retVal.add(AbstractDungeon.returnRandomCurse());
        } else
        {
            retVal.add(AbstractDungeon.getCard(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE).makeCopy());
            retVal.add(AbstractDungeon.getCard(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON).makeCopy());
            retVal.add(AbstractDungeon.getCard(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON).makeCopy());
            retVal.add(AbstractDungeon.returnColorlessCard(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON).makeCopy());
            retVal.add(AbstractDungeon.returnRandomCurse());
        }
        retVal.add(AbstractDungeon.player.getStartCardForEvent());
        AbstractCard c;
        for(Iterator iterator = retVal.iterator(); iterator.hasNext(); retVal2.add(c.makeStatEquivalentCopy()))
        {
            c = (AbstractCard)iterator.next();
            AbstractRelic r;
            for(Iterator iterator2 = AbstractDungeon.player.relics.iterator(); iterator2.hasNext(); r.onPreviewObtainCard(c))
                r = (AbstractRelic)iterator2.next();

        }

        retVal.addAll(retVal2);
        for(Iterator iterator1 = retVal.iterator(); iterator1.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator1.next();
            c.current_x = (float)Settings.WIDTH / 2.0F;
            c.target_x = c.current_x;
            c.current_y = -300F * Settings.scale;
            c.target_y = c.current_y;
        }

        return retVal;
    }

    public void update()
    {
        super.update();
        cards.update();
        if(screen == CUR_SCREEN.PLAY)
        {
            updateControllerInput();
            updateMatchGameLogic();
        } else
        if(screen == CUR_SCREEN.CLEAN_UP)
        {
            if(!cleanUpCalled)
            {
                cleanUpCalled = true;
                cleanUpCards();
            }
            if(waitTimer > 0.0F)
            {
                waitTimer -= Gdx.graphics.getDeltaTime();
                if(waitTimer < 0.0F)
                {
                    waitTimer = 0.0F;
                    screen = CUR_SCREEN.COMPLETE;
                    GenericEventDialog.show();
                    imageEventText.updateBodyText(MSG_3);
                    imageEventText.clearRemainingOptions();
                    imageEventText.setDialogOption(OPTIONS[1]);
                }
            }
        }
        if(!GenericEventDialog.waitForInput)
            buttonEffect(GenericEventDialog.getSelectedOption());
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode)
            return;
        boolean anyHovered = false;
        int index = 0;
        Iterator iterator = cards.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.hb.hovered)
            {
                anyHovered = true;
                break;
            }
            index++;
        } while(true);
        if(!anyHovered)
        {
            Gdx.input.setCursorPosition((int)((AbstractCard)cards.group.get(0)).hb.cX, Settings.HEIGHT - (int)((AbstractCard)cards.group.get(0)).hb.cY);
        } else
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                float y = ((AbstractCard)cards.group.get(index)).hb.cY + 230F * Settings.scale;
                if(y > 865F * Settings.scale)
                    y = 290F * Settings.scale;
                Gdx.input.setCursorPosition((int)((AbstractCard)cards.group.get(index)).hb.cX, (int)((float)Settings.HEIGHT - y));
            } else
            if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            {
                float y = ((AbstractCard)cards.group.get(index)).hb.cY - 230F * Settings.scale;
                if(y < 175F * Settings.scale)
                    y = 750F * Settings.scale;
                Gdx.input.setCursorPosition((int)((AbstractCard)cards.group.get(index)).hb.cX, (int)((float)Settings.HEIGHT - y));
            } else
            if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            {
                float x = ((AbstractCard)cards.group.get(index)).hb.cX - 210F * Settings.scale;
                if(x < 530F * Settings.scale)
                    x = 1270F * Settings.scale;
                Gdx.input.setCursorPosition((int)x, Settings.HEIGHT - (int)((AbstractCard)cards.group.get(index)).hb.cY);
            } else
            if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
            {
                float x = ((AbstractCard)cards.group.get(index)).hb.cX + 210F * Settings.scale;
                if(x > 1375F * Settings.scale)
                    x = 640F * Settings.scale;
                Gdx.input.setCursorPosition((int)x, Settings.HEIGHT - (int)((AbstractCard)cards.group.get(index)).hb.cY);
            }
            if(CInputActionSet.select.isJustPressed())
            {
                CInputActionSet.select.unpress();
                InputHelper.justClickedLeft = true;
            }
        }
    }

    private void cleanUpCards()
    {
        for(Iterator iterator = cards.group.iterator(); iterator.hasNext();)
        {
            AbstractCard c = (AbstractCard)iterator.next();
            c.targetDrawScale = 0.5F;
            c.target_x = (float)Settings.WIDTH / 2.0F;
            c.target_y = -300F * Settings.scale;
        }

    }

    private void updateMatchGameLogic()
    {
        if(waitTimer == 0.0F)
        {
            hoveredCard = null;
            Iterator iterator = cards.group.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator.next();
                c.hb.update();
                if(hoveredCard == null && c.hb.hovered)
                {
                    c.drawScale = 0.7F;
                    c.targetDrawScale = 0.7F;
                    hoveredCard = c;
                    if(InputHelper.justClickedLeft && hoveredCard.isFlipped)
                    {
                        InputHelper.justClickedLeft = false;
                        hoveredCard.isFlipped = false;
                        if(!cardFlipped)
                        {
                            cardFlipped = true;
                            chosenCard = hoveredCard;
                        } else
                        {
                            cardFlipped = false;
                            if(chosenCard.cardID.equals(hoveredCard.cardID))
                            {
                                waitTimer = 1.0F;
                                chosenCard.targetDrawScale = 0.7F;
                                chosenCard.target_x = (float)Settings.WIDTH / 2.0F;
                                chosenCard.target_y = (float)Settings.HEIGHT / 2.0F;
                                hoveredCard.targetDrawScale = 0.7F;
                                hoveredCard.target_x = (float)Settings.WIDTH / 2.0F;
                                hoveredCard.target_y = (float)Settings.HEIGHT / 2.0F;
                            } else
                            {
                                waitTimer = 1.25F;
                                chosenCard.targetDrawScale = 1.0F;
                                hoveredCard.targetDrawScale = 1.0F;
                            }
                        }
                    }
                } else
                if(c != chosenCard)
                    c.targetDrawScale = 0.5F;
            } while(true);
        } else
        {
            waitTimer -= Gdx.graphics.getDeltaTime();
            if(waitTimer < 0.0F && !gameDone)
            {
                waitTimer = 0.0F;
                if(chosenCard.cardID.equals(hoveredCard.cardID))
                {
                    cardsMatched++;
                    cards.group.remove(chosenCard);
                    cards.group.remove(hoveredCard);
                    matchedCards.add(chosenCard.cardID);
                    AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(chosenCard.makeCopy(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    chosenCard = null;
                    hoveredCard = null;
                } else
                {
                    chosenCard.isFlipped = true;
                    hoveredCard.isFlipped = true;
                    chosenCard.targetDrawScale = 0.5F;
                    hoveredCard.targetDrawScale = 0.5F;
                    chosenCard = null;
                    hoveredCard = null;
                }
                attemptCount--;
                if(attemptCount == 0)
                {
                    gameDone = true;
                    waitTimer = 1.0F;
                }
            } else
            if(gameDone)
                screen = CUR_SCREEN.CLEAN_UP;
        }
    }

    protected void buttonEffect(int buttonPressed)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$events$shrines$GremlinMatchGame$CUR_SCREEN[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$events$shrines$GremlinMatchGame$CUR_SCREEN = new int[CUR_SCREEN.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$GremlinMatchGame$CUR_SCREEN[CUR_SCREEN.INTRO.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$GremlinMatchGame$CUR_SCREEN[CUR_SCREEN.RULE_EXPLANATION.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$events$shrines$GremlinMatchGame$CUR_SCREEN[CUR_SCREEN.COMPLETE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.events.shrines.GremlinMatchGame.CUR_SCREEN[screen.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.updateBodyText(MSG_2);
                imageEventText.updateDialogOption(0, OPTIONS[2]);
                screen = CUR_SCREEN.RULE_EXPLANATION;
                break;
            }
            break;

        case 2: // '\002'
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.removeDialogOption(0);
                GenericEventDialog.hide();
                screen = CUR_SCREEN.PLAY;
                placeCards();
                break;
            }
            break;

        case 3: // '\003'
            logMetricObtainCards("Match and Keep!", (new StringBuilder()).append(cardsMatched).append(" cards matched").toString(), matchedCards);
            openMap();
            break;
        }
    }

    private void placeCards()
    {
        for(int i = 0; i < cards.size(); i++)
        {
            ((AbstractCard)cards.group.get(i)).target_x = (float)(i % 4) * 210F * Settings.xScale + 640F * Settings.xScale;
            ((AbstractCard)cards.group.get(i)).target_y = (float)(i % 3) * -230F * Settings.yScale + 750F * Settings.yScale;
            ((AbstractCard)cards.group.get(i)).targetDrawScale = 0.5F;
            ((AbstractCard)cards.group.get(i)).isFlipped = true;
        }

    }

    public void render(SpriteBatch sb)
    {
        cards.render(sb);
        if(chosenCard != null)
            chosenCard.render(sb);
        if(hoveredCard != null)
            hoveredCard.render(sb);
        if(screen == CUR_SCREEN.PLAY)
            FontHelper.renderSmartText(sb, FontHelper.panelNameFont, (new StringBuilder()).append(OPTIONS[3]).append(attemptCount).toString(), 780F * Settings.scale, 80F * Settings.scale, 2000F * Settings.scale, 0.0F, Color.WHITE);
    }

    public void renderAboveTopPanel(SpriteBatch spritebatch)
    {
    }

    public static final String ID = "Match and Keep!";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private AbstractCard chosenCard;
    private AbstractCard hoveredCard;
    private boolean cardFlipped;
    private boolean gameDone;
    private boolean cleanUpCalled;
    private int attemptCount;
    private CardGroup cards;
    private float waitTimer;
    private int cardsMatched;
    private CUR_SCREEN screen;
    private static final String MSG_2;
    private static final String MSG_3;
    private List matchedCards;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Match and Keep!");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        MSG_2 = DESCRIPTIONS[0];
        MSG_3 = DESCRIPTIONS[1];
    }
}
