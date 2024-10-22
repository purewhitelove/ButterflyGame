// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SmokeBomb.java

package com.megacrit.cardcrawl.potions;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.BackAttackPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.buttons.EndTurnButton;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.potions:
//            AbstractPotion

public class SmokeBomb extends AbstractPotion
{

    public SmokeBomb()
    {
        super(potionStrings.NAME, "SmokeBomb", AbstractPotion.PotionRarity.RARE, AbstractPotion.PotionSize.SPHERE, AbstractPotion.PotionColor.SMOKE);
        description = potionStrings.DESCRIPTIONS[0];
        isThrown = true;
        tips.add(new PowerTip(name, description));
    }

    public void use(AbstractCreature target)
    {
        target = AbstractDungeon.player;
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            AbstractDungeon.getCurrRoom().smoked = true;
            addToBot(new VFXAction(new SmokeBombEffect(target.hb.cX, target.hb.cY)));
            AbstractDungeon.player.hideHealthBar();
            AbstractDungeon.player.isEscaping = true;
            AbstractDungeon.player.flipHorizontal = !AbstractDungeon.player.flipHorizontal;
            AbstractDungeon.overlayMenu.endTurnButton.disable();
            AbstractDungeon.player.escapeTimer = 2.5F;
        }
    }

    public boolean canUse()
    {
        if(super.canUse())
        {
            for(Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator(); iterator.hasNext();)
            {
                AbstractMonster m = (AbstractMonster)iterator.next();
                if(m.hasPower("BackAttack"))
                    return false;
                if(m.type == com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS)
                    return false;
            }

            return true;
        } else
        {
            return false;
        }
    }

    public int getPotency(int ascensionLevel)
    {
        return 0;
    }

    public AbstractPotion makeCopy()
    {
        return new SmokeBomb();
    }

    public static final String POTION_ID = "SmokeBomb";
    public static final PotionStrings potionStrings;

    static 
    {
        potionStrings = CardCrawlGame.languagePack.getPotionString("SmokeBomb");
    }
}
