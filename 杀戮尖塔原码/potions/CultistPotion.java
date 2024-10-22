// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CultistPotion.java

package com.megacrit.cardcrawl.potions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.city.Byrd;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.TreeMap;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class CultistPotion extends AbstractPotion
{

    public CultistPotion()
    {
        super(potionStrings.NAME, "CultistPotion", AbstractPotion.PotionRarity.RARE, AbstractPotion.PotionSize.MOON, AbstractPotion.PotionEffect.NONE, new Color(0x2853bcff), new Color(0x1c2c60ff), null);
        isThrown = false;
    }

    public void initializeData()
    {
        potency = getPotency();
        description = (new StringBuilder()).append(potionStrings.DESCRIPTIONS[0]).append(potency).append(potionStrings.DESCRIPTIONS[1]).toString();
        tips.clear();
        tips.add(new PowerTip(name, description));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.RITUAL.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.RITUAL.NAMES[0])));
        tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.STRENGTH.NAMES[0]), (String)GameDictionary.keywords.get(GameDictionary.STRENGTH.NAMES[0])));
    }

    public void use(AbstractCreature target)
    {
        target = AbstractDungeon.player;
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            playSfx();
            AbstractDungeon.actionManager.addToBottom(new TalkAction(true, Byrd.DIALOG[0], 1.2F, 1.2F));
            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new RitualPower(target, potency, true), potency));
        }
    }

    private void playSfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1A"));
        else
        if(roll == 1)
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1B"));
        else
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_CULTIST_1C"));
    }

    public int getPotency(int ascensionLevel)
    {
        return 1;
    }

    public AbstractPotion makeCopy()
    {
        return new CultistPotion();
    }

    public static final String POTION_ID = "CultistPotion";
    private static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("CultistPotion");
    }
}
