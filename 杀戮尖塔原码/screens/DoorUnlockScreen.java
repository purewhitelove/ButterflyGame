// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DoorUnlockScreen.java

package com.megacrit.cardcrawl.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.DoorShineParticleEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.screens:
//            DoorLock

public class DoorUnlockScreen
{

    public DoorUnlockScreen()
    {
        bgColor = new Color(0x141723ff);
        fadeColor = Color.BLACK.cpy();
        fadeOutColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        fadeOut = false;
        effects = new ArrayList();
        animateCircle = false;
        rotatingCircle = true;
        circleAngle = -45F;
        doorOffset = 1.0F;
        renderScale = Settings.xScale <= Settings.yScale ? Settings.yScale : Settings.xScale;
    }

    public void open(boolean eventVersion)
    {
        GameCursor.hidden = true;
        this.eventVersion = eventVersion;
        if(doorLeft == null)
        {
            doorLeft = ImageMaster.loadImage("images/ui/door/door_left.png");
            doorRight = ImageMaster.loadImage("images/ui/door/door_right.png");
            circleLeft = ImageMaster.loadImage("images/ui/door/circle_left.png");
            circleRight = ImageMaster.loadImage("images/ui/door/circle_right.png");
        } else
        if(lockRed != null)
        {
            lockRed.reset();
            lockGreen.reset();
            lockBlue.reset();
        }
        lockRed = new DoorLock(DoorLock.LockColor.RED, CardCrawlGame.playerPref.getBoolean((new StringBuilder()).append(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD.name()).append("_WIN").toString(), false), eventVersion);
        lockGreen = new DoorLock(DoorLock.LockColor.GREEN, CardCrawlGame.playerPref.getBoolean((new StringBuilder()).append(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT.name()).append("_WIN").toString(), false), eventVersion);
        lockBlue = new DoorLock(DoorLock.LockColor.BLUE, CardCrawlGame.playerPref.getBoolean((new StringBuilder()).append(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT.name()).append("_WIN").toString(), false), eventVersion);
        if(Settings.FAST_MODE)
        {
            circleTimer = 1.0F;
            circleTime = 1.0F;
            lightUpTimer = 1.0F;
            autoContinueTimer = 0.01F;
            doorTime = 2.0F;
            fadeTimer = 1.0F;
        } else
        {
            circleTimer = 3F;
            circleTime = 3F;
            lightUpTimer = 3F;
            autoContinueTimer = 0.5F;
            doorTime = 5F;
            fadeTimer = 3F;
        }
        circleAngle = -45F;
        doorOffset = 1.0F;
        rotatingCircle = true;
        fadeColor = Color.BLACK.cpy();
        CardCrawlGame.music.silenceBGM();
        fadeOut = false;
        GameCursor.hidden = true;
    }

    public void update()
    {
        updateFade();
        updateLightUp();
        updateCircle();
        lockRed.update();
        lockGreen.update();
        lockBlue.update();
        updateFadeInput();
        updateVfx();
    }

    private void updateFadeInput()
    {
        if(fadeOut)
        {
            fadeTimer -= Gdx.graphics.getDeltaTime();
            fadeOutColor.a = 1.0F - fadeTimer;
            if(fadeTimer < 0.0F)
                exit();
            return;
        }
        if(!animateCircle && fadeTimer == 0.0F)
            if(circleTimer == 0.0F)
            {
                if(autoContinueTimer > 0.0F)
                {
                    autoContinueTimer -= Gdx.graphics.getDeltaTime();
                    if(autoContinueTimer < 0.0F)
                        exit();
                } else
                if(InputHelper.justClickedLeft || CInputActionSet.proceed.isJustPressed() || CInputActionSet.select.isJustPressed())
                    exit();
            } else
            if(circleTimer == circleTime && (InputHelper.justClickedLeft || CInputActionSet.proceed.isJustPressed() || CInputActionSet.select.isJustPressed()))
            {
                fadeOut = true;
                fadeTimer = 1.0F;
            }
    }

    private void exit()
    {
        lockRed.dispose();
        lockGreen.dispose();
        lockBlue.dispose();
        if(!eventVersion)
        {
            GameCursor.hidden = false;
            CardCrawlGame.mainMenuScreen.lighten();
            CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.MAIN_MENU;
            CardCrawlGame.music.changeBGM("MENU");
        } else
        {
            CardCrawlGame.mode = com.megacrit.cardcrawl.core.CardCrawlGame.GameMode.GAMEPLAY;
            CardCrawlGame.nextDungeon = "TheEnding";
            CardCrawlGame.music.fadeOutBGM();
            CardCrawlGame.music.fadeOutTempBGM();
            AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.fadeOut();
            AbstractDungeon.isDungeonBeaten = true;
        }
    }

    private void updateLightUp()
    {
        if(animateCircle && lightUpTimer != 0.0F)
        {
            lightUpTimer -= Gdx.graphics.getDeltaTime();
            if(Settings.FAST_MODE)
            {
                if(lightUpTimer < 1.0F)
                    lockRed.flash(eventVersion);
                if(lightUpTimer < 0.75F)
                    lockGreen.flash(eventVersion);
                if(lightUpTimer < 0.5F)
                    lockBlue.flash(eventVersion);
            } else
            {
                if(lightUpTimer < 3F)
                    lockRed.flash(eventVersion);
                if(lightUpTimer < 2.5F)
                    lockGreen.flash(eventVersion);
                if(lightUpTimer < 2.0F)
                    lockBlue.flash(eventVersion);
            }
            if(lightUpTimer < 0.0F)
            {
                lightUpTimer = 0.0F;
                unlock();
            }
        }
    }

    private void updateVfx()
    {
        Iterator i = effects.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractGameEffect e = (AbstractGameEffect)i.next();
            e.update();
            if(e.isDone)
                i.remove();
        } while(true);
    }

    private void updateFade()
    {
        if(fadeTimer != 0.0F)
        {
            fadeTimer -= Gdx.graphics.getDeltaTime();
            if(fadeTimer < 0.0F)
            {
                fadeTimer = 0.0F;
                animateCircle = eventVersion;
            }
            fadeColor.a = Interpolation.fade.apply(0.0F, 1.0F, fadeTimer / 3F);
        }
    }

    private void unlock()
    {
        if(animateCircle)
        {
            CardCrawlGame.sound.playA("ATTACK_HEAVY", 0.4F);
            CardCrawlGame.sound.playA("POWER_SHACKLE", 0.1F);
            CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, true);
            for(int i = 0; i < 50; i++)
                effects.add(new DoorShineParticleEffect(MathUtils.random((float)Settings.WIDTH * 0.45F, (float)Settings.WIDTH * 0.55F), MathUtils.random((float)Settings.HEIGHT * 0.45F, (float)Settings.HEIGHT * 0.55F)));

            lockRed.unlock();
            lockGreen.unlock();
            lockBlue.unlock();
        }
    }

    private void updateCircle()
    {
        if(animateCircle && fadeTimer == 0.0F && lightUpTimer == 0.0F)
            if(rotatingCircle)
            {
                circleTimer -= Gdx.graphics.getDeltaTime();
                circleAngle = Interpolation.fade.apply(0.0F, -45F, circleTimer / circleTime);
                if(circleTimer < 0.0F)
                {
                    rotatingCircle = false;
                    circleTimer = doorTime;
                    circleAngle = 0.0F;
                    CardCrawlGame.screenShake.mildRumble(doorTime - 0.25F);
                    CardCrawlGame.sound.playA("RELIC_DROP_ROCKY", 0.3F);
                    CardCrawlGame.sound.playA("RELIC_DROP_ROCKY", -0.6F);
                    CardCrawlGame.sound.playA("EVENT_GOLDEN", -0.3F);
                    CardCrawlGame.sound.playA("EVENT_WINDING", 0.5F);
                }
            } else
            {
                circleTimer -= Gdx.graphics.getDeltaTime();
                if(circleTimer < 0.0F)
                {
                    circleTimer = 0.0F;
                    animateCircle = false;
                }
                bgColor.r = MathHelper.slowColorLerpSnap(bgColor.r, 0.0F);
                bgColor.g = MathHelper.slowColorLerpSnap(bgColor.g, 0.0F);
                bgColor.b = MathHelper.slowColorLerpSnap(bgColor.b, 0.0F);
                doorOffset = 1200F * Settings.scale * Interpolation.pow3.apply(1.0F, 0.0F, circleTimer / doorTime);
            }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(bgColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        renderMainDoor(sb);
        renderCircleMechanism(sb);
        lockRed.render(sb);
        lockGreen.render(sb);
        lockBlue.render(sb);
        renderFade(sb);
        if(fadeOut)
        {
            sb.setColor(fadeOutColor);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        }
        AbstractGameEffect e;
        for(Iterator iterator = effects.iterator(); iterator.hasNext(); e.render(sb))
            e = (AbstractGameEffect)iterator.next();

    }

    private void renderFade(SpriteBatch sb)
    {
        sb.setColor(fadeColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    private void renderMainDoor(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        float yOffset = 0.0F;
        if(eventVersion)
            yOffset = -48F * Settings.scale;
        sb.draw(doorLeft, (float)Settings.WIDTH / 2.0F - 960F - doorOffset, ((float)Settings.HEIGHT / 2.0F - 600F) + yOffset, 960F, 600F, 1920F, 1200F, renderScale, renderScale, 0.0F, 0, 0, 1920, 1200, false, false);
        sb.draw(doorRight, ((float)Settings.WIDTH / 2.0F - 960F) + doorOffset, ((float)Settings.HEIGHT / 2.0F - 600F) + yOffset, 960F, 600F, 1920F, 1200F, renderScale, renderScale, 0.0F, 0, 0, 1920, 1200, false, false);
    }

    private void renderCircleMechanism(SpriteBatch sb)
    {
        float yOffset = 0.0F;
        if(eventVersion)
            yOffset = -48F * Settings.scale;
        sb.draw(circleRight, ((float)Settings.WIDTH / 2.0F - 960F) + doorOffset, ((float)Settings.HEIGHT / 2.0F - 600F) + yOffset, 960F, 600F, 1920F, 1200F, renderScale, renderScale, circleAngle, 2, 2, 1920, 1200, false, false);
        sb.draw(circleLeft, (float)Settings.WIDTH / 2.0F - 960F - doorOffset, ((float)Settings.HEIGHT / 2.0F - 600F) + yOffset, 960F, 600F, 1920F, 1200F, renderScale, renderScale, circleAngle, 2, 2, 1920, 1200, false, false);
    }

    public static boolean show = false;
    private static Texture doorLeft;
    private static Texture doorRight;
    private static Texture circleLeft;
    private static Texture circleRight;
    private Color bgColor;
    private Color fadeColor;
    private Color fadeOutColor;
    private float fadeTimer;
    private float lightUpTimer;
    private boolean fadeOut;
    private DoorLock lockGreen;
    private DoorLock lockBlue;
    private DoorLock lockRed;
    public ArrayList effects;
    private boolean animateCircle;
    private boolean rotatingCircle;
    private boolean eventVersion;
    private float circleAngle;
    private float doorOffset;
    private float circleTimer;
    private float circleTime;
    private float autoContinueTimer;
    private float doorTime;
    private float renderScale;

}
