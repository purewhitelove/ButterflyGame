// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Astrolabe.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class Astrolabe extends AbstractRelic
{

    public Astrolabe()
    {
        super("Astrolabe", "astrolabe.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.CLINK);
        cardsSelected = true;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onEquip()
    {
        cardsSelected = false;
        CardGroup tmp = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        AbstractCard card;
        for(Iterator iterator = AbstractDungeon.player.masterDeck.getPurgeableCards().group.iterator(); iterator.hasNext(); tmp.addToTop(card))
            card = (AbstractCard)iterator.next();

        if(tmp.group.isEmpty())
        {
            cardsSelected = true;
            return;
        }
        if(tmp.group.size() <= 3)
            giveCards(tmp.group);
        else
        if(!AbstractDungeon.isScreenUp)
        {
            AbstractDungeon.gridSelectScreen.open(tmp, 3, (new StringBuilder()).append(DESCRIPTIONS[1]).append(name).append(LocalizedStrings.PERIOD).toString(), false, false, false, false);
        } else
        {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
            AbstractDungeon.gridSelectScreen.open(tmp, 3, (new StringBuilder()).append(DESCRIPTIONS[1]).append(name).append(LocalizedStrings.PERIOD).toString(), false, false, false, false);
        }
    }

    public void update()
    {
        super.update();
        if(!cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == 3)
            giveCards(AbstractDungeon.gridSelectScreen.selectedCards);
    }

    public void giveCards(ArrayList group)
    {
        cardsSelected = true;
        float displayCount = 0.0F;
        Iterator i = group.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractCard card = (AbstractCard)i.next();
            card.untip();
            card.unhover();
            AbstractDungeon.player.masterDeck.removeCard(card);
            AbstractDungeon.transformCard(card, true, AbstractDungeon.miscRng);
            if(AbstractDungeon.screen != com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.TRANSFORM && AbstractDungeon.transformedCard != null)
            {
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardAndObtainEffect(AbstractDungeon.getTransformedCard(), (float)Settings.WIDTH / 3F + displayCount, (float)Settings.HEIGHT / 2.0F, false));
                displayCount += (float)Settings.WIDTH / 6F;
            }
        } while(true);
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
    }

    public AbstractRelic makeCopy()
    {
        return new Astrolabe();
    }

    public static final String ID = "Astrolabe";
    private boolean cardsSelected;
}
