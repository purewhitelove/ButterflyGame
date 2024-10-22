// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TheLibrary.java

package com.megacrit.cardcrawl.events.city;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class TheLibrary extends AbstractImageEvent
{

    public TheLibrary()
    {
        super(NAME, DIALOG_1, "images/events/library.jpg");
        screenNum = 0;
        pickCard = false;
        if(AbstractDungeon.ascensionLevel >= 15)
            healAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.2F);
        else
            healAmt = MathUtils.round((float)AbstractDungeon.player.maxHealth * 0.33F);
        imageEventText.setDialogOption(OPTIONS[0]);
        imageEventText.setDialogOption((new StringBuilder()).append(OPTIONS[1]).append(healAmt).append(OPTIONS[2]).toString());
    }

    public void update()
    {
        super.update();
        if(pickCard && !AbstractDungeon.isScreenUp && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            AbstractCard c = ((AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0)).makeCopy();
            logMetricObtainCard("The Library", "Read", c);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(c, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(screenNum)
        {
        case 0: // '\0'
            switch(buttonPressed)
            {
            case 0: // '\0'
                imageEventText.updateBodyText(getBook());
                screenNum = 1;
                imageEventText.updateDialogOption(0, OPTIONS[3]);
                imageEventText.clearRemainingOptions();
                pickCard = true;
                CardGroup group = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
                for(int i = 0; i < 20; i++)
                {
                    AbstractCard card = AbstractDungeon.getCard(AbstractDungeon.rollRarity()).makeCopy();
                    boolean containsDupe = true;
label0:
                    do
                    {
                        if(!containsDupe)
                            break;
                        containsDupe = false;
                        Iterator iterator1 = group.group.iterator();
                        AbstractCard c;
                        do
                        {
                            if(!iterator1.hasNext())
                                continue label0;
                            c = (AbstractCard)iterator1.next();
                        } while(!c.cardID.equals(card.cardID));
                        containsDupe = true;
                        card = AbstractDungeon.getCard(AbstractDungeon.rollRarity()).makeCopy();
                    } while(true);
                    if(!group.contains(card))
                    {
                        AbstractRelic r;
                        for(Iterator iterator2 = AbstractDungeon.player.relics.iterator(); iterator2.hasNext(); r.onPreviewObtainCard(card))
                            r = (AbstractRelic)iterator2.next();

                        group.addToBottom(card);
                    } else
                    {
                        i--;
                    }
                }

                AbstractCard c;
                for(Iterator iterator = group.group.iterator(); iterator.hasNext(); UnlockTracker.markCardAsSeen(c.cardID))
                    c = (AbstractCard)iterator.next();

                AbstractDungeon.gridSelectScreen.open(group, 1, OPTIONS[4], false);
                break;

            default:
                imageEventText.updateBodyText(SLEEP_RESULT);
                AbstractDungeon.player.heal(healAmt, true);
                logMetricHeal("The Library", "Heal", healAmt);
                screenNum = 1;
                imageEventText.updateDialogOption(0, OPTIONS[3]);
                imageEventText.clearRemainingOptions();
                break;
            }
            break;

        default:
            openMap();
            break;
        }
    }

    private String getBook()
    {
        ArrayList list = new ArrayList();
        list.add(DESCRIPTIONS[2]);
        list.add(DESCRIPTIONS[3]);
        list.add(DESCRIPTIONS[4]);
        return (String)list.get(MathUtils.random(2));
    }

    public static final String ID = "The Library";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    private static final String DIALOG_1;
    private static final String SLEEP_RESULT;
    private int screenNum;
    private boolean pickCard;
    private static final float HP_HEAL_PERCENT = 0.33F;
    private static final float A_2_HP_HEAL_PERCENT = 0.2F;
    private int healAmt;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("The Library");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        DIALOG_1 = DESCRIPTIONS[0];
        SLEEP_RESULT = DESCRIPTIONS[1];
    }
}
