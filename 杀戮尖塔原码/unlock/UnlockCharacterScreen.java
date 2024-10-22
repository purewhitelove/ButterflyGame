// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnlockCharacterScreen.java

package com.megacrit.cardcrawl.unlock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.buttons.DynamicBanner;
import com.megacrit.cardcrawl.ui.buttons.UnlockConfirmButton;
import com.megacrit.cardcrawl.vfx.*;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.unlock:
//            AbstractUnlock, UnlockTracker

public class UnlockCharacterScreen
{

    public UnlockCharacterScreen()
    {
        unlockBgColor = new Color(0.1F, 0.2F, 0.25F, 1.0F);
        cones = new ArrayList();
        shinyTimer = 0.0F;
        button = new UnlockConfirmButton();
    }

    public void open(AbstractUnlock unlock)
    {
        AbstractDungeon.screen = com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.UNLOCK;
        this.unlock = unlock;
        id = CardCrawlGame.sound.play("UNLOCK_SCREEN");
        button.show();
        cones.clear();
        for(int i = 0; i < 30; i++)
            cones.add(new ConeEffect());

        unlock.onUnlockScreenOpen();
        UnlockTracker.hardUnlockOverride(unlock.key);
        UnlockTracker.lockedCharacters.remove(unlock.key);
        AbstractDungeon.dynamicBanner.appearInstantly(CardCrawlGame.languagePack.getUIString("UnlockCharacterScreen").TEXT[3]);
    }

    public void update()
    {
        if(InputHelper.justClickedRight)
            button.show();
        shinyTimer -= Gdx.graphics.getDeltaTime();
        if(shinyTimer < 0.0F)
        {
            shinyTimer = 0.2F;
            AbstractDungeon.topLevelEffects.add(new RoomShineEffect());
            AbstractDungeon.topLevelEffects.add(new RoomShineEffect());
            AbstractDungeon.topLevelEffects.add(new RoomShineEffect2());
        }
        updateConeEffect();
        unlock.player.update();
        button.update();
    }

    private void updateConeEffect()
    {
        Iterator e = cones.iterator();
        do
        {
            if(!e.hasNext())
                break;
            ConeEffect d = (ConeEffect)e.next();
            d.update();
            if(d.isDone)
                e.remove();
        } while(true);
        if(cones.size() < 30)
            cones.add(new ConeEffect());
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(unlockBgColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        sb.setBlendFunction(770, 1);
        ConeEffect e;
        for(Iterator iterator = cones.iterator(); iterator.hasNext(); e.render(sb))
            e = (ConeEffect)iterator.next();

        sb.setBlendFunction(770, 771);
        unlock.render(sb);
        unlock.player.renderPlayerImage(sb);
        button.render(sb);
    }

    private Color unlockBgColor;
    public AbstractUnlock unlock;
    private ArrayList cones;
    private float shinyTimer;
    public UnlockConfirmButton button;
    public long id;
}
