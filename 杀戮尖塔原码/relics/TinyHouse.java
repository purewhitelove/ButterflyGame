// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TinyHouse.java

package com.megacrit.cardcrawl.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class TinyHouse extends AbstractRelic
{

    public TinyHouse()
    {
        super("Tiny House", "tinyHouse.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.FLAT);
    }

    public String getUpdatedDescription()
    {
        return (new StringBuilder()).append(DESCRIPTIONS[0]).append(50).append(DESCRIPTIONS[1]).append(5).append(DESCRIPTIONS[2]).toString();
    }

    public void onEquip()
    {
        ArrayList upgradableCards = new ArrayList();
        Iterator iterator = AbstractDungeon.player.masterDeck.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.canUpgrade())
                upgradableCards.add(c);
        } while(true);
        Collections.shuffle(upgradableCards, new Random(AbstractDungeon.miscRng.randomLong()));
        if(!upgradableCards.isEmpty())
            if(upgradableCards.size() == 1)
            {
                ((AbstractCard)upgradableCards.get(0)).upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy()));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            } else
            {
                ((AbstractCard)upgradableCards.get(0)).upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(0));
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)upgradableCards.get(1));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)upgradableCards.get(0)).makeStatEquivalentCopy(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            }
        AbstractDungeon.player.increaseMaxHp(5, true);
        AbstractDungeon.getCurrRoom().addGoldToRewards(50);
        AbstractDungeon.getCurrRoom().addPotionToRewards(PotionHelper.getRandomPotion(AbstractDungeon.miscRng));
        AbstractDungeon.combatRewardScreen.open(DESCRIPTIONS[3]);
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.0F;
    }

    public AbstractRelic makeCopy()
    {
        return new TinyHouse();
    }

    public static final String ID = "Tiny House";
    private static final int GOLD_AMT = 50;
    private static final int HP_AMT = 5;
}
