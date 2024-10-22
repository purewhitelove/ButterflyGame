// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BattleStartEffect.java

package com.megacrit.cardcrawl.vfx.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.UpgradeShineParticleEffect;
import java.util.ArrayList;
import java.util.Iterator;

public class BattleStartEffect extends AbstractGameEffect
{

    public BattleStartEffect(boolean surpriseAttack)
    {
        soundPlayed = false;
        bossFight = false;
        currentHeight = 0.0F;
        turnMessageColor = new Color(0.7F, 0.7F, 0.7F, 0.0F);
        timer1 = 1.0F;
        timer2 = 1.0F;
        firstMessageX = (float)Settings.WIDTH / 2.0F;
        secondMessageX = (float)Settings.WIDTH * 1.5F;
        showHb = false;
        swordTimer = 0.5F;
        swordSound1 = false;
        swordColor = new Color(0.9F, 0.9F, 0.85F, 0.0F);
        duration = 4F;
        startingDuration = 4F;
        this.surpriseAttack = surpriseAttack;
        bgColor = new Color(AbstractDungeon.fadeColor.r / 2.0F, AbstractDungeon.fadeColor.g / 2.0F, AbstractDungeon.fadeColor.b / 2.0F, 0.0F);
        if(img == null)
            img = ImageMaster.vfxAtlas.findRegion("combat/battleStartSword");
        scale = Settings.scale;
        swordY = ((float)Settings.HEIGHT / 2.0F - (float)img.packedHeight / 2.0F) + 20F * Settings.scale;
        if(surpriseAttack)
            turnMsg = ENEMY_TURN_MSG;
        else
            turnMsg = PLAYER_TURN_MSG;
        color = Settings.GOLD_COLOR.cpy();
        color.a = 0.0F;
        if(Settings.usesOrdinal)
            battleStartMessage = (new StringBuilder()).append(Integer.toString(GameActionManager.turn)).append(getOrdinalNaming(GameActionManager.turn)).append(TURN_TXT).toString();
        else
        if(Settings.language == com.megacrit.cardcrawl.core.Settings.GameLanguage.VIE)
            battleStartMessage = (new StringBuilder()).append(TURN_TXT).append(" ").append(Integer.toString(GameActionManager.turn)).toString();
        else
            battleStartMessage = (new StringBuilder()).append(Integer.toString(GameActionManager.turn)).append(TURN_TXT).toString();
        if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)
        {
            bossFight = true;
            CardCrawlGame.sound.play("BATTLE_START_BOSS");
        } else
        if(MathUtils.randomBoolean())
            CardCrawlGame.sound.play("BATTLE_START_1");
        else
            CardCrawlGame.sound.play("BATTLE_START_2");
    }

    public static String getOrdinalNaming(int i)
    {
        return i % 100 != 11 && i % 100 != 12 && i % 100 != 13 ? (new String[] {
            "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"
        })[i % 10] : "th";
    }

    public void update()
    {
        if(!showHb)
        {
            AbstractDungeon.player.showHealthBar();
            AbstractMonster m;
            for(Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator(); iterator.hasNext(); m.showHealthBar())
                m = (AbstractMonster)iterator.next();

            showHb = true;
        }
        if(duration > 3F)
            currentHeight = MathUtils.lerp(currentHeight, TARGET_HEIGHT, Gdx.graphics.getDeltaTime() * 3F);
        else
        if(duration < 0.5F)
            currentHeight = MathUtils.lerp(currentHeight, 0.0F, Gdx.graphics.getDeltaTime() * 3F);
        if(duration < 3F && timer1 != 0.0F)
        {
            timer1 -= Gdx.graphics.getDeltaTime();
            if(timer1 < 0.0F)
                timer1 = 0.0F;
            firstMessageX = Interpolation.pow2In.apply(firstMessageX, MSG_VANISH_X, 1.0F - timer1);
        } else
        if(duration < 3F && timer2 != 0.0F)
        {
            if(!soundPlayed)
            {
                CardCrawlGame.sound.play("TURN_EFFECT");
                AbstractDungeon.getMonsters().showIntent();
                soundPlayed = true;
            }
            timer2 -= Gdx.graphics.getDeltaTime();
            if(timer2 < 0.0F)
                timer2 = 0.0F;
            secondMessageX = Interpolation.pow2In.apply(secondMessageX, WIDTH_DIV_2, 1.0F - timer2);
        }
        if(duration > 1.0F)
            color.a = MathUtils.lerp(color.a, 1.0F, Gdx.graphics.getDeltaTime() * 5F);
        else
            color.a = MathUtils.lerp(color.a, 0.0F, Gdx.graphics.getDeltaTime() * 5F);
        bgColor.a = color.a * 0.75F;
        turnMessageColor.a = color.a;
        if(Settings.FAST_MODE)
            duration -= Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F)
            isDone = true;
        updateSwords();
    }

    private void updateSwords()
    {
        swordTimer -= Gdx.graphics.getDeltaTime();
        if(swordTimer < 0.0F)
            swordTimer = 0.0F;
        swordColor.a = Interpolation.fade.apply(1.0F, 0.01F, swordTimer / 0.5F);
        if(bossFight)
        {
            if(swordTimer < 0.1F && !swordSound1)
            {
                swordSound1 = true;
                CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
                for(int i = 0; i < 30; i++)
                    if(MathUtils.randomBoolean())
                        AbstractDungeon.effectsQueue.add(new UpgradeShineParticleEffect((float)Settings.WIDTH / 2.0F + MathUtils.random(-150F, 150F) * Settings.scale, (float)Settings.HEIGHT / 2.0F + MathUtils.random(-10F, 50F) * Settings.scale));
                    else
                        AbstractDungeon.topLevelEffectsQueue.add(new UpgradeShineParticleEffect((float)Settings.WIDTH / 2.0F + MathUtils.random(-150F, 150F) * Settings.scale, (float)Settings.HEIGHT / 2.0F + MathUtils.random(-10F, 50F) * Settings.scale));

            }
            swordX = Interpolation.pow3Out.apply(SWORD_DEST_X, SWORD_START_X, swordTimer / 0.5F);
            swordAngle = Interpolation.pow3Out.apply(-50F, 500F, swordTimer / 0.5F);
        } else
        {
            swordX = SWORD_DEST_X;
            swordAngle = -50F;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(bgColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, HEIGHT_DIV_2 - currentHeight / 2.0F, Settings.WIDTH, currentHeight);
        renderSwords(sb);
        FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, BATTLE_START_MSG, firstMessageX, HEIGHT_DIV_2 + MAIN_MSG_OFFSET_Y, color, 1.0F);
        FontHelper.renderFontCentered(sb, FontHelper.bannerNameFont, turnMsg, secondMessageX, HEIGHT_DIV_2 + MAIN_MSG_OFFSET_Y, color, 1.0F);
        if(!surpriseAttack)
            FontHelper.renderFontCentered(sb, FontHelper.turnNumFont, battleStartMessage, secondMessageX, HEIGHT_DIV_2 + TURN_MSG_OFFSET_Y, turnMessageColor);
    }

    public void dispose()
    {
    }

    private void renderSwords(SpriteBatch sb)
    {
        sb.setColor(swordColor);
        sb.draw(img, (((float)Settings.WIDTH - swordX - (float)img.packedWidth / 2.0F) + firstMessageX) - (float)Settings.WIDTH / 2.0F, swordY, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, -scale, -scale, -swordAngle + 180F);
        sb.draw(img, ((swordX - (float)img.packedWidth / 2.0F) + firstMessageX) - (float)Settings.WIDTH / 2.0F, swordY, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, img.packedWidth, img.packedHeight, scale, scale, swordAngle);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final float EFFECT_DUR = 4F;
    private static final float HEIGHT_DIV_2;
    private static final float WIDTH_DIV_2;
    private boolean surpriseAttack;
    private boolean soundPlayed;
    private boolean bossFight;
    private Color bgColor;
    private static final float TARGET_HEIGHT;
    private static final float BG_RECT_EXPAND_SPEED = 3F;
    private float currentHeight;
    private String battleStartMessage;
    private static final String BATTLE_START_MSG;
    public static final String PLAYER_TURN_MSG;
    public static final String ENEMY_TURN_MSG;
    public static final String TURN_TXT;
    private String turnMsg;
    private static final float TEXT_FADE_SPEED = 5F;
    private static final float MAIN_MSG_OFFSET_Y;
    private static final float TURN_MSG_OFFSET_Y;
    private Color turnMessageColor;
    private float timer1;
    private float timer2;
    private static final float MSG_VANISH_X;
    private float firstMessageX;
    private float secondMessageX;
    private boolean showHb;
    private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion img = null;
    private static final float SWORD_ANIM_TIME = 0.5F;
    private float swordTimer;
    private static final float SWORD_START_X;
    private static final float SWORD_DEST_X;
    private float swordX;
    private float swordY;
    private float swordAngle;
    private boolean swordSound1;
    private Color swordColor;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("BattleStartEffect");
        TEXT = uiStrings.TEXT;
        HEIGHT_DIV_2 = (float)Settings.HEIGHT / 2.0F;
        WIDTH_DIV_2 = (float)Settings.WIDTH / 2.0F;
        TARGET_HEIGHT = 150F * Settings.scale;
        BATTLE_START_MSG = TEXT[0];
        PLAYER_TURN_MSG = TEXT[1];
        ENEMY_TURN_MSG = TEXT[2];
        TURN_TXT = TEXT[3];
        MAIN_MSG_OFFSET_Y = 20F * Settings.scale;
        TURN_MSG_OFFSET_Y = -30F * Settings.scale;
        MSG_VANISH_X = (float)(-Settings.WIDTH) * 0.25F;
        SWORD_START_X = -50F * Settings.scale;
        SWORD_DEST_X = (float)Settings.WIDTH / 2.0F + 0.0F * Settings.scale;
    }
}
