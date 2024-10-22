// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PandorasBox.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.Keyword;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class PandorasBox extends AbstractRelic
{

    public PandorasBox()
    {
        super("Pandora's Box", "pandoras_box.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.MAGICAL);
        count = 0;
        calledTransform = true;
        removeStrikeTip();
    }

    private void removeStrikeTip()
    {
        ArrayList strikes = new ArrayList();
        String as[] = GameDictionary.STRIKE.NAMES;
        int i = as.length;
        for(int j = 0; j < i; j++)
        {
            String s = as[j];
            strikes.add(s.toLowerCase());
        }

        Iterator t = tips.iterator();
        do
        {
            if(!t.hasNext())
                break;
            PowerTip derp = (PowerTip)t.next();
            String s = derp.header.toLowerCase();
            if(!strikes.contains(s))
                continue;
            t.remove();
            break;
        } while(true);
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onEquip()
    {
        calledTransform = false;
        Iterator i = AbstractDungeon.player.masterDeck.group.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractCard e = (AbstractCard)i.next();
            if(e.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.STARTER_DEFEND) || e.hasTag(com.megacrit.cardcrawl.cards.AbstractCard.CardTags.STARTER_STRIKE))
            {
                i.remove();
                count++;
            }
        } while(true);
        if(count > 0)
        {
            CardGroup group = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
            for(int i = 0; i < count; i++)
            {
                AbstractCard card = AbstractDungeon.returnTrulyRandomCard().makeCopy();
                UnlockTracker.markCardAsSeen(card.cardID);
                card.isSeen = true;
                AbstractRelic r;
                for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onPreviewObtainCard(card))
                    r = (AbstractRelic)iterator.next();

                group.addToBottom(card);
            }

            AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, DESCRIPTIONS[1]);
        }
    }

    public void update()
    {
        super.update();
        if(!calledTransform && AbstractDungeon.screen != com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.GRID)
        {
            calledTransform = true;
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
        }
    }

    public AbstractRelic makeCopy()
    {
        return new PandorasBox();
    }

    public static final String ID = "Pandora's Box";
    private int count;
    private boolean calledTransform;
}
