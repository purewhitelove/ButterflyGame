// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractImageEvent.java

package com.megacrit.cardcrawl.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import com.megacrit.cardcrawl.vfx.scene.EventBgParticle;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.events:
//            AbstractEvent, GenericEventDialog, RoomEventDialog

public abstract class AbstractImageEvent extends AbstractEvent
{

    public AbstractImageEvent(String title, String body, String imgUrl)
    {
        imageEventText.clear();
        roomEventText.clear();
        this.title = title;
        this.body = body;
        imageEventText.loadImage(imgUrl);
        type = AbstractEvent.EventType.IMAGE;
        noCardsInRewards = false;
    }

    public void update()
    {
        if(!combatTime)
        {
            hasFocus = true;
            if(MathUtils.randomBoolean(0.1F))
                AbstractDungeon.effectList.add(new EventBgParticle());
            if(waitTimer > 0.0F)
            {
                waitTimer -= Gdx.graphics.getDeltaTime();
                if(waitTimer < 0.0F)
                {
                    imageEventText.show(title, body);
                    waitTimer = 0.0F;
                }
            }
            if(!GenericEventDialog.waitForInput)
                buttonEffect(GenericEventDialog.getSelectedOption());
        }
    }

    public void showProceedScreen(String bodyText)
    {
        imageEventText.updateBodyText(bodyText);
        imageEventText.updateDialogOption(0, DESCRIPTIONS[0]);
        imageEventText.clearRemainingOptions();
        screenNum = 99;
    }

    public void render(SpriteBatch spritebatch)
    {
    }

    protected void openMap()
    {
        AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.dungeonMapScreen.open(false);
    }

    public void enterCombatFromImage()
    {
        AbstractDungeon.getCurrRoom().smoked = false;
        AbstractDungeon.player.isEscaping = false;
        AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT;
        AbstractDungeon.getCurrRoom().monsters.init();
        AbstractRoom.waitTimer = 0.1F;
        AbstractDungeon.player.preBattlePrep();
        hasFocus = false;
        GenericEventDialog.hide();
        CardCrawlGame.fadeIn(1.5F);
        AbstractDungeon.rs = com.megacrit.cardcrawl.dungeons.AbstractDungeon.RenderScene.NORMAL;
        combatTime = true;
    }

    public void enterImageFromCombat()
    {
        AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.EVENT;
        AbstractDungeon.getCurrRoom().isBattleOver = false;
        AbstractDungeon.getCurrRoom().monsters.monsters.clear();
        AbstractDungeon.getCurrRoom().rewards.clear();
        hasDialog = true;
        hasFocus = true;
        combatTime = false;
        GenericEventDialog.show();
        CardCrawlGame.fadeIn(1.5F);
        AbstractDungeon.rs = com.megacrit.cardcrawl.dungeons.AbstractDungeon.RenderScene.EVENT;
    }

    protected String title;
    private static final EventStrings eventStrings;
    private static final String DESCRIPTIONS[];

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Proceed Screen");
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    }
}
