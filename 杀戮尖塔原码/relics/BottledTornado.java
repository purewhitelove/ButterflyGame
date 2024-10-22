// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BottledTornado.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class BottledTornado extends AbstractRelic
{

    public BottledTornado()
    {
        super("Bottled Tornado", "bottledTornado.png", AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.CLINK);
        cardSelected = true;
        card = null;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public AbstractCard getCard()
    {
        return card.makeCopy();
    }

    public void onEquip()
    {
        if(AbstractDungeon.player.masterDeck.getPurgeableCards().getPowers().size() > 0)
        {
            cardSelected = false;
            if(AbstractDungeon.isScreenUp)
            {
                AbstractDungeon.dynamicBanner.hide();
                AbstractDungeon.overlayMenu.cancelButton.hide();
                AbstractDungeon.previousScreen = AbstractDungeon.screen;
            }
            AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.INCOMPLETE;
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck.getPurgeableCards().getPowers(), 1, (new StringBuilder()).append(DESCRIPTIONS[1]).append(name).append(LocalizedStrings.PERIOD).toString(), false, false, false, false);
        }
    }

    public void onUnequip()
    {
        if(card != null)
        {
            AbstractCard cardInDeck = AbstractDungeon.player.masterDeck.getSpecificCard(card);
            if(cardInDeck != null)
                cardInDeck.inBottleTornado = false;
        }
    }

    public void update()
    {
        super.update();
        if(!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())
        {
            cardSelected = true;
            card = (AbstractCard)AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            card.inBottleTornado = true;
            AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            description = (new StringBuilder()).append(DESCRIPTIONS[2]).append(FontHelper.colorString(card.name, "y")).append(DESCRIPTIONS[3]).toString();
            tips.clear();
            tips.add(new PowerTip(name, description));
            initializeTips();
        }
    }

    public void setDescriptionAfterLoading()
    {
        description = (new StringBuilder()).append(DESCRIPTIONS[2]).append(FontHelper.colorString(card.name, "y")).append(DESCRIPTIONS[3]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void atBattleStart()
    {
        flash();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }

    public boolean canSpawn()
    {
        return CardHelper.hasCardType(com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER);
    }

    public AbstractRelic makeCopy()
    {
        return new BottledTornado();
    }

    public static final String ID = "Bottled Tornado";
    private boolean cardSelected;
    public AbstractCard card;
}
