// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PlayerTurnEffect.java

package com.megacrit.cardcrawl.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.vfx:
//            AbstractGameEffect

public class PlayerTurnEffect extends AbstractGameEffect
{

    public PlayerTurnEffect()
    {
        currentHeight = 0.0F;
        turnMessageColor = new Color(0.7F, 0.7F, 0.7F, 0.0F);
        duration = 2.0F;
        startingDuration = 2.0F;
        bgColor = new Color(AbstractDungeon.fadeColor.r / 2.0F, AbstractDungeon.fadeColor.g / 2.0F, AbstractDungeon.fadeColor.b / 2.0F, 0.0F);
        color = Settings.GOLD_COLOR.cpy();
        color.a = 0.0F;
        if(Settings.usesOrdinal)
            turnMessage = (new StringBuilder()).append(Integer.toString(GameActionManager.turn)).append(getOrdinalNaming(GameActionManager.turn)).append(BattleStartEffect.TURN_TXT).toString();
        else
        if(Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.VIE)
            turnMessage = (new StringBuilder()).append(BattleStartEffect.TURN_TXT).append(" ").append(Integer.toString(GameActionManager.turn)).toString();
        else
            turnMessage = (new StringBuilder()).append(Integer.toString(GameActionManager.turn)).append(BattleStartEffect.TURN_TXT).toString();
        AbstractDungeon.player.energy.recharge();
        AbstractRelic r;
        for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onEnergyRecharge())
            r = (AbstractRelic)iterator.next();

        AbstractPower p;
        for(Iterator iterator1 = AbstractDungeon.player.powers.iterator(); iterator1.hasNext(); p.onEnergyRecharge())
            p = (AbstractPower)iterator1.next();

        CardCrawlGame.sound.play("TURN_EFFECT");
        AbstractDungeon.getMonsters().showIntent();
        scale = 1.0F;
    }

    public static String getOrdinalNaming(int i)
    {
        return i % 100 != 11 && i % 100 != 12 && i % 100 != 13 ? (new String[] {
            "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"
        })[i % 10] : "th";
    }

    public void update()
    {
        if(duration > 1.5F)
            currentHeight = MathUtils.lerp(currentHeight, TARGET_HEIGHT, Gdx.graphics.getDeltaTime() * 3F);
        else
        if(duration < 0.5F)
            currentHeight = MathUtils.lerp(currentHeight, 0.0F, Gdx.graphics.getDeltaTime() * 3F);
        if(duration > 1.5F)
        {
            scale = Interpolation.exp10In.apply(1.0F, 3F, (duration - 1.5F) * 2.0F);
            color.a = Interpolation.exp10In.apply(1.0F, 0.0F, (duration - 1.5F) * 2.0F);
        } else
        if(duration < 0.5F)
        {
            scale = Interpolation.pow3In.apply(0.9F, 1.0F, duration * 2.0F);
            color.a = Interpolation.pow3In.apply(0.0F, 1.0F, duration * 2.0F);
        }
        bgColor.a = color.a * 0.75F;
        turnMessageColor.a = color.a;
        if(Settings.FAST_MODE)
            duration -= Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
        {
            isDone = true;
            AbstractPower p;
            for(Iterator iterator = AbstractDungeon.player.powers.iterator(); iterator.hasNext(); p.atEnergyGain())
                p = (AbstractPower)iterator.next();

        }
    }

    public void render(SpriteBatch sb)
    {
        if(!isDone)
        {
            sb.setColor(bgColor);
            sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, HEIGHT_DIV_2 - currentHeight / 2.0F, Settings.WIDTH, currentHeight);
            sb.setBlendFunction(770, 1);
            FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, BattleStartEffect.PLAYER_TURN_MSG, WIDTH_DIV_2, HEIGHT_DIV_2 + MAIN_MSG_OFFSET_Y, color, scale);
            FontHelper.renderFontCentered(sb, FontHelper.turnNumFont, turnMessage, WIDTH_DIV_2, HEIGHT_DIV_2 + TURN_MSG_OFFSET_Y, turnMessageColor, scale);
            sb.setBlendFunction(770, 771);
        }
    }

    public void dispose()
    {
    }

    private static final float DUR = 2F;
    private static final float HEIGHT_DIV_2;
    private static final float WIDTH_DIV_2;
    private Color bgColor;
    private static final float TARGET_HEIGHT;
    private static final float BG_RECT_EXPAND_SPEED = 3F;
    private float currentHeight;
    private String turnMessage;
    private static final float MAIN_MSG_OFFSET_Y;
    private static final float TURN_MSG_OFFSET_Y;
    private Color turnMessageColor;

    static 
    {
        HEIGHT_DIV_2 = (float)Settings.HEIGHT / 2.0F;
        WIDTH_DIV_2 = (float)Settings.WIDTH / 2.0F;
        TARGET_HEIGHT = 150F * Settings.scale;
        MAIN_MSG_OFFSET_Y = 20F * Settings.scale;
        TURN_MSG_OFFSET_Y = -30F * Settings.scale;
    }
}
