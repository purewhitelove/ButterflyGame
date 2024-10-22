// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TheEndingScene.java

package com.megacrit.cardcrawl.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.scene.ShinySparkleEffect;
import com.megacrit.cardcrawl.vfx.scene.WobblyCircleEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.scenes:
//            AbstractScene

public class TheEndingScene extends AbstractScene
{

    public TheEndingScene()
    {
        super("endingScene/scene.atlas");
        circles = new ArrayList();
        ambianceName = "AMBIANCE_BEYOND";
        fadeInAmbiance();
    }

    public void update()
    {
        super.update();
        updateParticles();
    }

    public void randomizeScene()
    {
    }

    private void updateParticles()
    {
        Iterator e = circles.iterator();
        do
        {
            if(!e.hasNext())
                break;
            AbstractGameEffect effect = (AbstractGameEffect)e.next();
            effect.update();
            if(effect.isDone)
                e.remove();
        } while(true);
        if(!(AbstractDungeon.getCurrRoom() instanceof TrueVictoryRoom) && circles.size() < 72 && !Settings.DISABLE_EFFECTS)
            if(MathUtils.randomBoolean(0.2F))
                circles.add(new ShinySparkleEffect());
            else
                circles.add(new WobblyCircleEffect());
    }

    public void nextRoom(AbstractRoom room)
    {
        super.nextRoom(room);
        randomizeScene();
        if(room instanceof MonsterRoomBoss)
            CardCrawlGame.music.silenceBGM();
        fadeInAmbiance();
    }

    public void renderCombatRoomBg(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        renderAtlasRegionIf(sb, bg, true);
    }

    public void renderCombatRoomFg(SpriteBatch sb)
    {
        if(!isCamp)
        {
            sb.setBlendFunction(770, 1);
            AbstractGameEffect e;
            for(Iterator iterator = circles.iterator(); iterator.hasNext(); e.render(sb))
                e = (AbstractGameEffect)iterator.next();

            sb.setBlendFunction(770, 771);
        }
    }

    public void renderCampfireRoom(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        renderAtlasRegionIf(sb, campfireBg, true);
    }

    private ArrayList circles;
}
