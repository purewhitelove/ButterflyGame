// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CallingBell.java

package com.megacrit.cardcrawl.relics;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            AbstractRelic

public class CallingBell extends AbstractRelic
{

    public CallingBell()
    {
        super("Calling Bell", "bell.png", AbstractRelic.RelicTier.BOSS, AbstractRelic.LandingSound.SOLID);
        cardsReceived = true;
    }

    public String getUpdatedDescription()
    {
        return DESCRIPTIONS[0];
    }

    public void onEquip()
    {
        cardsReceived = false;
        CardGroup group = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        AbstractCard bellCurse = new CurseOfTheBell();
        UnlockTracker.markCardAsSeen(bellCurse.cardID);
        group.addToBottom(bellCurse.makeCopy());
        AbstractDungeon.gridSelectScreen.openConfirmationGrid(group, DESCRIPTIONS[1]);
        CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
    }

    public void update()
    {
        super.update();
        if(!cardsReceived && !AbstractDungeon.isScreenUp)
        {
            AbstractDungeon.combatRewardScreen.open();
            AbstractDungeon.combatRewardScreen.rewards.clear();
            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.COMMON)));
            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.UNCOMMON)));
            AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(AbstractRelic.RelicTier.RARE)));
            AbstractDungeon.combatRewardScreen.positionRewards();
            AbstractDungeon.overlayMenu.proceedButton.setLabel(DESCRIPTIONS[2]);
            cardsReceived = true;
            AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
        }
        if(hb.hovered && InputHelper.justClickedLeft)
        {
            CardCrawlGame.sound.playA("BELL", MathUtils.random(-0.2F, -0.3F));
            flash();
        }
    }

    public AbstractRelic makeCopy()
    {
        return new CallingBell();
    }

    public static final String ID = "Calling Bell";
    private boolean cardsReceived;
}
