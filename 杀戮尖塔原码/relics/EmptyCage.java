// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmptyCage.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class EmptyCage extends AbstractRelic
{

    public EmptyCage()
    {
        super("Empty Cage", "cage.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.SOLID);
        cardsSelected = true;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onEquip()
    {
        cardsSelected = false;
        if(AbstractDungeon.isScreenUp)
        {
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.INCOMPLETE;
        CardGroup tmp = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        AbstractCard card;
        for(Iterator iterator = AbstractDungeon.player.masterDeck.getPurgeableCards().group.iterator(); iterator.hasNext(); tmp.addToTop(card))
            card = (AbstractCard)iterator.next();

        if(tmp.group.isEmpty())
        {
            cardsSelected = true;
            return;
        }
        if(tmp.group.size() <= 2)
            deleteCards(tmp.group);
        else
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards(), 2, DESCRIPTIONS[1], false, false, false, true);
    }

    public void update()
    {
        super.update();
        if(!cardsSelected && AbstractDungeon.gridSelectScreen.selectedCards.size() == 2)
            deleteCards(AbstractDungeon.gridSelectScreen.selectedCards);
    }

    public void deleteCards(ArrayList group)
    {
        cardsSelected = true;
        float displayCount = 0.0F;
        AbstractCard card;
        for(Iterator i = group.iterator(); i.hasNext(); AbstractDungeon.player.masterDeck.removeCard(card))
        {
            card = (AbstractCard)i.next();
            card.untip();
            card.unhover();
            AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(card, (float)Settings.WIDTH / 3F + displayCount, (float)Settings.HEIGHT / 2.0F));
            displayCount += (float)Settings.WIDTH / 6F;
        }

        AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
    }

    public AbstractRelic makeCopy()
    {
        return new EmptyCage();
    }

    public static final String ID = "Empty Cage";
    private boolean cardsSelected;
}
