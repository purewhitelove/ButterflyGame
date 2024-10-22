// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DoorLock.java

package com.megacrit.cardcrawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.vfx.DoorFlashEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.screens:
//            DoorUnlockScreen

public class DoorLock
{
    public static final class LockColor extends Enum
    {

        public static LockColor[] values()
        {
            return (LockColor[])$VALUES.clone();
        }

        public static LockColor valueOf(String name)
        {
            return (LockColor)Enum.valueOf(com/megacrit/cardcrawl/screens/DoorLock$LockColor, name);
        }

        public static final LockColor RED;
        public static final LockColor GREEN;
        public static final LockColor BLUE;
        private static final LockColor $VALUES[];

        static 
        {
            RED = new LockColor("RED", 0);
            GREEN = new LockColor("GREEN", 1);
            BLUE = new LockColor("BLUE", 2);
            $VALUES = (new LockColor[] {
                RED, GREEN, BLUE
            });
        }

        private LockColor(String s, int i)
        {
            super(s, i);
        }
    }


    public DoorLock(LockColor c, boolean glowing, boolean eventVersion)
    {
        glowColor = Color.WHITE.cpy();
        lockImg = null;
        glowImg = null;
        unlockAnimation = false;
        usedFlash = false;
        x = 0.0F;
        unlockTimer = 2.0F;
        this.c = c;
        this.glowing = glowing;
        if(eventVersion)
            startY = -48F * Settings.scale;
        else
            startY = 0.0F * Settings.scale;
        y = startY;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$DoorLock$LockColor[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$DoorLock$LockColor = new int[LockColor.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$DoorLock$LockColor[LockColor.RED.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$DoorLock$LockColor[LockColor.GREEN.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$DoorLock$LockColor[LockColor.BLUE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.DoorLock.LockColor[c.ordinal()])
        {
        default:
            break;

        case 1: // '\001'
            lockImg = ImageMaster.loadImage("images/ui/door/lock_red.png");
            glowImg = ImageMaster.loadImage("images/ui/door/glow_red.png");
            glowing = CardCrawlGame.playerPref.getBoolean((new StringBuilder()).append(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD.name()).append("_WIN").toString(), false);
            if(eventVersion)
                targetY = -748F * Settings.scale;
            else
                targetY = -700F * Settings.scale;
            break;

        case 2: // '\002'
            lockImg = ImageMaster.loadImage("images/ui/door/lock_green.png");
            glowImg = ImageMaster.loadImage("images/ui/door/glow_green.png");
            glowing = CardCrawlGame.playerPref.getBoolean((new StringBuilder()).append(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT.name()).append("_WIN").toString(), false);
            if(eventVersion)
                targetY = 752F * Settings.scale;
            else
                targetY = 800F * Settings.scale;
            break;

        case 3: // '\003'
            lockImg = ImageMaster.loadImage("images/ui/door/lock_blue.png");
            glowImg = ImageMaster.loadImage("images/ui/door/glow_blue.png");
            glowing = CardCrawlGame.playerPref.getBoolean((new StringBuilder()).append(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT.name()).append("_WIN").toString(), false);
            if(eventVersion)
                targetY = -748F * Settings.scale;
            else
                targetY = -700F * Settings.scale;
            break;
        }
    }

    public void update()
    {
        updateUnlockAnimation();
    }

    private void updateUnlockAnimation()
    {
        if(unlockAnimation)
        {
            unlockTimer -= Gdx.graphics.getDeltaTime();
            if(unlockTimer < 0.0F)
            {
                unlockTimer = 0.0F;
                unlockAnimation = false;
            }
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.DoorLock.LockColor[c.ordinal()])
            {
            case 1: // '\001'
                x = Interpolation.pow5In.apply(-1000F * Settings.scale, 0.0F, unlockTimer / 2.0F);
                y = Interpolation.pow5In.apply(targetY, startY, unlockTimer / 2.0F);
                break;

            case 2: // '\002'
                y = Interpolation.pow5In.apply(800F * Settings.scale, startY, unlockTimer / 2.0F);
                break;

            case 3: // '\003'
                x = Interpolation.pow5In.apply(1000F * Settings.scale, 0.0F, unlockTimer / 2.0F);
                y = Interpolation.pow5In.apply(targetY, startY, unlockTimer / 2.0F);
                break;
            }
        }
    }

    public void unlock()
    {
        unlockAnimation = true;
        unlockTimer = 2.0F;
        x = 0.0F;
        y = startY;
    }

    public void render(SpriteBatch sb)
    {
        if(lockImg == null)
        {
            return;
        } else
        {
            renderLock(sb);
            renderGlow(sb);
            return;
        }
    }

    private void renderLock(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(lockImg, ((float)Settings.WIDTH / 2.0F - 960F) + x, ((float)Settings.HEIGHT / 2.0F - 600F) + y, 960F, 600F, 1920F, 1200F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1200, false, false);
    }

    private void renderGlow(SpriteBatch sb)
    {
        if(glowing)
        {
            glowColor.a = (MathUtils.cosDeg((System.currentTimeMillis() / 4L) % 360L) + 3F) / 4F;
            sb.setColor(glowColor);
            sb.setBlendFunction(770, 1);
            sb.draw(glowImg, ((float)Settings.WIDTH / 2.0F - 960F) + x, ((float)Settings.HEIGHT / 2.0F - 600F) + y, 960F, 600F, 1920F, 1200F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1920, 1200, false, false);
            sb.setBlendFunction(770, 771);
        }
    }

    public void reset()
    {
        usedFlash = false;
        unlockAnimation = false;
        x = 0.0F;
        y = startY;
        unlockTimer = 2.0F;
    }

    public void flash(boolean eventVersion)
    {
        if(!usedFlash)
        {
            CardCrawlGame.sound.playA("ATTACK_MAGIC_SLOW_2", 1.0F);
            usedFlash = true;
            CardCrawlGame.mainMenuScreen.doorUnlockScreen.effects.add(new DoorFlashEffect(glowImg, eventVersion));
        }
    }

    public void dispose()
    {
        lockImg.dispose();
        glowImg.dispose();
    }

    private Color glowColor;
    private Texture lockImg;
    private Texture glowImg;
    private boolean glowing;
    private boolean unlockAnimation;
    private boolean usedFlash;
    private float x;
    private float y;
    private float unlockTimer;
    private float startY;
    private float targetY;
    private LockColor c;
}
