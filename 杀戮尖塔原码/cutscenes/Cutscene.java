// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Cutscene.java

package com.megacrit.cardcrawl.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.VictoryScreen;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.cutscenes:
//            CutscenePanel

public class Cutscene
    implements Disposable
{

    public Cutscene(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass chosenClass)
    {
        currentScene = 0;
        darkenTimer = 1.0F;
        fadeTimer = 1.0F;
        switchTimer = 1.0F;
        panels = new ArrayList();
        isDone = false;
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass = new int[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$characters$AbstractPlayer$PlayerClass[com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass[chosenClass.ordinal()])
        {
        case 1: // '\001'
            bgImg = ImageMaster.loadImage("images/scenes/redBg.jpg");
            panels.add(new CutscenePanel("images/scenes/ironclad1.png", "ATTACK_HEAVY"));
            panels.add(new CutscenePanel("images/scenes/ironclad2.png"));
            panels.add(new CutscenePanel("images/scenes/ironclad3.png"));
            break;

        case 2: // '\002'
            bgImg = ImageMaster.loadImage("images/scenes/greenBg.jpg");
            panels.add(new CutscenePanel("images/scenes/silent1.png", "ATTACK_POISON2"));
            panels.add(new CutscenePanel("images/scenes/silent2.png"));
            panels.add(new CutscenePanel("images/scenes/silent3.png"));
            break;

        case 3: // '\003'
            bgImg = ImageMaster.loadImage("images/scenes/blueBg.jpg");
            panels.add(new CutscenePanel("images/scenes/defect1.png", "ATTACK_MAGIC_BEAM_SHORT"));
            panels.add(new CutscenePanel("images/scenes/defect2.png"));
            panels.add(new CutscenePanel("images/scenes/defect3.png"));
            break;

        case 4: // '\004'
            bgImg = ImageMaster.loadImage("images/scenes/purpleBg.jpg");
            panels.add(new CutscenePanel("images/scenes/watcher1.png", "WATCHER_HEART_PUNCH"));
            panels.add(new CutscenePanel("images/scenes/watcher2.png"));
            panels.add(new CutscenePanel("images/scenes/watcher3.png"));
            break;

        default:
            bgImg = ImageMaster.loadImage("images/scenes/redBg.jpg");
            panels.add(new CutscenePanel("images/scenes/ironclad1.png", "ATTACK_HEAVY"));
            panels.add(new CutscenePanel("images/scenes/ironclad2.png"));
            panels.add(new CutscenePanel("images/scenes/ironclad3.png"));
            break;
        }
        bgColor = Color.WHITE.cpy();
        screenColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
    }

    public void update()
    {
        updateFadeOut();
        updateFadeIn();
        CutscenePanel p;
        for(Iterator iterator = panels.iterator(); iterator.hasNext(); p.update())
            p = (CutscenePanel)iterator.next();

        updateIfDone();
        updateSceneChange();
    }

    private void updateIfDone()
    {
        if(isDone)
        {
            bgColor.a -= Gdx.graphics.getDeltaTime();
            for(Iterator iterator = panels.iterator(); iterator.hasNext();)
            {
                CutscenePanel p = (CutscenePanel)iterator.next();
                if(!p.finished)
                    return;
            }

            dispose();
            bgColor.a = 0.0F;
            openVictoryScreen();
        }
    }

    private void updateSceneChange()
    {
        switchTimer -= Gdx.graphics.getDeltaTime();
        if((InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()) && switchTimer > 1.0F)
            switchTimer = 1.0F;
        if(switchTimer < 0.0F)
        {
            for(Iterator iterator = panels.iterator(); iterator.hasNext();)
            {
                CutscenePanel p = (CutscenePanel)iterator.next();
                if(!p.activated)
                {
                    p.activate();
                    switchTimer = 5F;
                    return;
                }
            }

            CutscenePanel p;
            for(Iterator iterator1 = panels.iterator(); iterator1.hasNext(); p.fadeOut())
                p = (CutscenePanel)iterator1.next();

            isDone = true;
        }
    }

    private void openVictoryScreen()
    {
        GameCursor.hidden = false;
        AbstractDungeon.victoryScreen = new VictoryScreen(null);
    }

    private void updateFadeIn()
    {
        if(darkenTimer == 0.0F)
        {
            fadeTimer -= Gdx.graphics.getDeltaTime();
            if(fadeTimer < 0.0F)
                fadeTimer = 0.0F;
            screenColor.a = fadeTimer;
        }
    }

    private void updateFadeOut()
    {
        if(darkenTimer != 0.0F)
        {
            darkenTimer -= Gdx.graphics.getDeltaTime();
            if(darkenTimer < 0.0F)
            {
                darkenTimer = 0.0F;
                fadeTimer = 1.0F;
                switchTimer = 1.0F;
            }
            screenColor.a = 1.0F - darkenTimer;
        }
    }

    public void render(SpriteBatch sb)
    {
        if(currentScene <= 1)
        {
            sb.setColor(Color.BLACK);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        }
    }

    public void renderAbove(SpriteBatch sb)
    {
        if(bgImg != null)
        {
            sb.setColor(bgColor);
            renderImg(sb, bgImg);
        }
        renderPanels(sb);
        sb.setColor(screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    private void renderPanels(SpriteBatch sb)
    {
        CutscenePanel p;
        for(Iterator iterator = panels.iterator(); iterator.hasNext(); p.render(sb))
            p = (CutscenePanel)iterator.next();

    }

    private void renderImg(SpriteBatch sb, Texture img)
    {
        if(Settings.isSixteenByTen)
            sb.draw(img, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        else
            sb.draw(img, 0.0F, -50F * Settings.scale, Settings.WIDTH, (float)Settings.HEIGHT + 110F * Settings.scale);
    }

    public void dispose()
    {
        if(bgImg != null)
        {
            bgImg.dispose();
            bgImg = null;
        }
        CutscenePanel p;
        for(Iterator iterator = panels.iterator(); iterator.hasNext(); p.dispose())
            p = (CutscenePanel)iterator.next();

    }

    private int currentScene;
    private float darkenTimer;
    private float fadeTimer;
    private float switchTimer;
    private Color screenColor;
    private Color bgColor;
    private ArrayList panels;
    private Texture bgImg;
    private boolean isDone;
}
