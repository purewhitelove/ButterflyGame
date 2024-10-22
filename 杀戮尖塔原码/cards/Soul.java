// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Soul.java

package com.megacrit.cardcrawl.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.utils.Pool;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.daily.mods.Hoarder;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.DiscardPilePanel;
import com.megacrit.cardcrawl.ui.panels.DrawPilePanel;
import com.megacrit.cardcrawl.vfx.CardTrailEffect;
import com.megacrit.cardcrawl.vfx.combat.EmpowerEffect;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.cards:
//            CardGroup, AbstractCard

public class Soul
{

    public Soul()
    {
        crs = new CatmullRomSpline();
        controlPoints = new ArrayList();
        points = new Vector2[20];
        vfxTimer = 0.015F;
        spawnStutterTimer = 0.0F;
        isInvisible = false;
        currentSpeed = 0.0F;
        rotateClockwise = true;
        stopRotating = false;
        tmp = new Vector2();
        crs.controlPoints = new Vector2[1];
        isReadyForReuse = true;
    }

    public void discard(AbstractCard card, boolean visualOnly)
    {
        this.card = card;
        group = AbstractDungeon.player.discardPile;
        if(!visualOnly)
            group.addToTop(card);
        pos = new Vector2(card.current_x, card.current_y);
        target = new Vector2(DISCARD_X, DISCARD_Y);
        setSharedVariables();
        rotation = card.angle + 270F;
        rotateClockwise = false;
        if(Settings.FAST_MODE)
            currentSpeed = START_VELOCITY * MathUtils.random(4F, 6F);
        else
            currentSpeed = START_VELOCITY * MathUtils.random(1.0F, 4F);
    }

    public void discard(AbstractCard card)
    {
        discard(card, false);
    }

    public void shuffle(AbstractCard card, boolean isInvisible)
    {
        this.isInvisible = isInvisible;
        this.card = card;
        group = AbstractDungeon.player.drawPile;
        group.addToTop(card);
        pos = new Vector2(DISCARD_X, DISCARD_Y);
        target = new Vector2(DRAW_PILE_X, DRAW_PILE_Y);
        setSharedVariables();
        rotation = MathUtils.random(260F, 310F);
        if(Settings.FAST_MODE)
            currentSpeed = START_VELOCITY * MathUtils.random(8F, 12F);
        else
            currentSpeed = START_VELOCITY * MathUtils.random(2.0F, 5F);
        rotateClockwise = true;
        spawnStutterTimer = MathUtils.random(0.0F, 0.12F);
    }

    public void onToDeck(AbstractCard card, boolean randomSpot, boolean visualOnly)
    {
        this.card = card;
        group = AbstractDungeon.player.drawPile;
        if(!visualOnly)
            if(randomSpot)
                group.addToRandomSpot(card);
            else
                group.addToTop(card);
        pos = new Vector2(card.current_x, card.current_y);
        target = new Vector2(DRAW_PILE_X, DRAW_PILE_Y);
        setSharedVariables();
        rotation = card.angle + 270F;
        rotateClockwise = true;
    }

    public void onToDeck(AbstractCard card, boolean randomSpot)
    {
        onToDeck(card, randomSpot, false);
    }

    public void onToBottomOfDeck(AbstractCard card)
    {
        this.card = card;
        group = AbstractDungeon.player.drawPile;
        group.addToBottom(card);
        pos = new Vector2(card.current_x, card.current_y);
        target = new Vector2(DRAW_PILE_X, DRAW_PILE_Y);
        setSharedVariables();
        rotation = card.angle + 270F;
        rotateClockwise = true;
    }

    public void empower(AbstractCard card)
    {
        CardCrawlGame.sound.play("CARD_POWER_WOOSH", 0.1F);
        this.card = card;
        group = null;
        pos = new Vector2(card.current_x, card.current_y);
        target = new Vector2(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY);
        setSharedVariables();
    }

    public void obtain(AbstractCard card)
    {
        this.card = card;
        group = AbstractDungeon.player.masterDeck;
        group.addToTop(card);
        if(ModHelper.isModEnabled("Hoarder"))
        {
            group.addToTop(card.makeStatEquivalentCopy());
            group.addToTop(card.makeStatEquivalentCopy());
        }
        pos = new Vector2(card.current_x, card.current_y);
        target = new Vector2(MASTER_DECK_X, MASTER_DECK_Y);
        setSharedVariables();
    }

    private void setSharedVariables()
    {
        controlPoints.clear();
        if(Settings.FAST_MODE)
        {
            rotationRate = ROTATION_RATE * MathUtils.random(4F, 6F);
            currentSpeed = START_VELOCITY * MathUtils.random(1.0F, 1.5F);
            backUpTimer = 0.5F;
        } else
        {
            rotationRate = ROTATION_RATE * MathUtils.random(1.0F, 2.0F);
            currentSpeed = START_VELOCITY * MathUtils.random(0.2F, 1.0F);
            backUpTimer = 1.5F;
        }
        stopRotating = false;
        rotateClockwise = MathUtils.randomBoolean();
        rotation = MathUtils.random(0, 359);
        isReadyForReuse = false;
        isDone = false;
    }

    public void update()
    {
        if(isCarryingCard())
        {
            card.update();
            card.targetAngle = rotation + 90F;
            card.current_x = pos.x;
            card.current_y = pos.y;
            card.target_x = card.current_x;
            card.target_y = card.current_y;
            if(spawnStutterTimer > 0.0F)
            {
                spawnStutterTimer -= Gdx.graphics.getDeltaTime();
                return;
            }
            updateMovement();
            updateBackUpTimer();
        } else
        {
            isDone = true;
        }
        if(isDone)
        {
            if(group == null)
            {
                AbstractDungeon.effectList.add(new EmpowerEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY));
                isReadyForReuse = true;
                return;
            }
            static class _cls2
            {

                static final int $SwitchMap$com$megacrit$cardcrawl$cards$CardGroup$CardGroupType[];

                static 
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$CardGroup$CardGroupType = new int[CardGroup.CardGroupType.values().length];
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$cards$CardGroup$CardGroupType[CardGroup.CardGroupType.MASTER_DECK.ordinal()] = 1;
                    }
                    catch(NoSuchFieldError nosuchfielderror) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$cards$CardGroup$CardGroupType[CardGroup.CardGroupType.DRAW_PILE.ordinal()] = 2;
                    }
                    catch(NoSuchFieldError nosuchfielderror1) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$cards$CardGroup$CardGroupType[CardGroup.CardGroupType.DISCARD_PILE.ordinal()] = 3;
                    }
                    catch(NoSuchFieldError nosuchfielderror2) { }
                    try
                    {
                        $SwitchMap$com$megacrit$cardcrawl$cards$CardGroup$CardGroupType[CardGroup.CardGroupType.EXHAUST_PILE.ordinal()] = 4;
                    }
                    catch(NoSuchFieldError nosuchfielderror3) { }
                }
            }

            switch(_cls2..SwitchMap.com.megacrit.cardcrawl.cards.CardGroup.CardGroupType[group.type.ordinal()])
            {
            case 1: // '\001'
                card.setAngle(0.0F);
                card.targetDrawScale = 0.75F;
                break;

            case 2: // '\002'
                card.targetDrawScale = 0.75F;
                card.setAngle(0.0F);
                card.lighten(false);
                card.clearPowers();
                AbstractDungeon.overlayMenu.combatDeckPanel.pop();
                break;

            case 3: // '\003'
                card.targetDrawScale = 0.75F;
                card.setAngle(0.0F);
                card.lighten(false);
                card.clearPowers();
                card.teleportToDiscardPile();
                AbstractDungeon.overlayMenu.discardPilePanel.pop();
                break;
            }
            if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
                AbstractDungeon.player.hand.applyPowers();
            isReadyForReuse = true;
        }
    }

    private boolean isCarryingCard()
    {
        if(group == null)
            return true;
        switch(_cls2..SwitchMap.com.megacrit.cardcrawl.cards.CardGroup.CardGroupType[group.type.ordinal()])
        {
        case 2: // '\002'
            return AbstractDungeon.player.drawPile.contains(card);

        case 3: // '\003'
            return AbstractDungeon.player.discardPile.contains(card);
        }
        return true;
    }

    private void updateMovement()
    {
        tmp.x = pos.x - target.x;
        tmp.y = pos.y - target.y;
        tmp.nor();
        float targetAngle = tmp.angle();
        rotationRate += Gdx.graphics.getDeltaTime() * 800F;
        if(!stopRotating)
        {
            if(rotateClockwise)
            {
                rotation += Gdx.graphics.getDeltaTime() * rotationRate;
            } else
            {
                rotation -= Gdx.graphics.getDeltaTime() * rotationRate;
                if(rotation < 0.0F)
                    rotation += 360F;
            }
            rotation = rotation % 360F;
            if(!stopRotating)
                if(target.dst(pos) < HOME_IN_THRESHOLD)
                {
                    rotation = targetAngle;
                    stopRotating = true;
                } else
                if(Math.abs(rotation - targetAngle) < Gdx.graphics.getDeltaTime() * rotationRate)
                {
                    rotation = targetAngle;
                    stopRotating = true;
                }
        }
        tmp.setAngle(rotation);
        tmp.x *= Gdx.graphics.getDeltaTime() * currentSpeed;
        tmp.y *= Gdx.graphics.getDeltaTime() * currentSpeed;
        pos.sub(tmp);
        if(stopRotating && backUpTimer < 1.35F)
            currentSpeed += Gdx.graphics.getDeltaTime() * VELOCITY_RAMP_RATE * 3F;
        else
            currentSpeed += Gdx.graphics.getDeltaTime() * VELOCITY_RAMP_RATE * 1.5F;
        if(currentSpeed > MAX_VELOCITY)
            currentSpeed = MAX_VELOCITY;
        if(target.x < (float)Settings.WIDTH / 2.0F && pos.x < 0.0F)
            isDone = true;
        else
        if(target.x > (float)Settings.WIDTH / 2.0F && pos.x > (float)Settings.WIDTH)
            isDone = true;
        if(target.dst(pos) < DST_THRESHOLD)
            isDone = true;
        vfxTimer -= Gdx.graphics.getDeltaTime();
        if(!isDone && vfxTimer < 0.0F)
        {
            vfxTimer = 0.015F;
            if(!controlPoints.isEmpty())
            {
                if(!((Vector2)controlPoints.get(0)).equals(pos))
                    controlPoints.add(pos.cpy());
            } else
            {
                controlPoints.add(pos.cpy());
            }
            if(controlPoints.size() > 10)
                controlPoints.remove(0);
            if(controlPoints.size() > 3)
            {
                Vector2 vec2Array[] = new Vector2[0];
                crs.set((com.badlogic.gdx.math.Vector[])controlPoints.toArray(vec2Array), false);
                for(int i = 0; i < 20; i++)
                {
                    if(points[i] == null)
                        points[i] = new Vector2();
                    Vector2 derp = (Vector2)crs.valueAt(points[i], (float)i / 19F);
                    CardTrailEffect effect = (CardTrailEffect)trailEffectPool.obtain();
                    effect.init(derp.x, derp.y);
                    AbstractDungeon.topLevelEffects.add(effect);
                }

            }
        }
    }

    private void updateBackUpTimer()
    {
        backUpTimer -= Gdx.graphics.getDeltaTime();
        if(backUpTimer < 0.0F)
            isDone = true;
    }

    public void render(SpriteBatch sb)
    {
        if(!isInvisible)
        {
            card.renderOuterGlow(sb);
            card.render(sb);
        }
    }

    public AbstractCard card;
    public CardGroup group;
    private CatmullRomSpline crs;
    private ArrayList controlPoints;
    private static final int NUM_POINTS = 20;
    private Vector2 points[];
    private Vector2 pos;
    private Vector2 target;
    private static final float VFX_INTERVAL = 0.015F;
    private float backUpTimer;
    private float vfxTimer;
    private static final float BACK_UP_TIME = 1.5F;
    private float spawnStutterTimer;
    private static final float STUTTER_TIME_MAX = 0.12F;
    private boolean isInvisible;
    public static final Pool trailEffectPool = new Pool() {

        protected CardTrailEffect newObject()
        {
            return new CardTrailEffect();
        }

        protected volatile Object newObject()
        {
            return newObject();
        }

    }
;
    private static final float DISCARD_X;
    private static final float DISCARD_Y;
    private static final float DRAW_PILE_X;
    private static final float DRAW_PILE_Y;
    private static final float MASTER_DECK_X;
    private static final float MASTER_DECK_Y;
    private float currentSpeed;
    private static final float START_VELOCITY;
    private static final float MAX_VELOCITY;
    private static final float VELOCITY_RAMP_RATE;
    public boolean isReadyForReuse;
    public boolean isDone;
    private static final float DST_THRESHOLD;
    private static final float HOME_IN_THRESHOLD;
    private float rotation;
    private boolean rotateClockwise;
    private boolean stopRotating;
    private float rotationRate;
    private static final float ROTATION_RATE;
    private static final float ROTATION_RAMP_RATE = 800F;
    private Vector2 tmp;

    static 
    {
        DISCARD_X = (float)Settings.WIDTH * 0.96F;
        DISCARD_Y = (float)Settings.HEIGHT * 0.06F;
        DRAW_PILE_X = (float)Settings.WIDTH * 0.04F;
        DRAW_PILE_Y = (float)Settings.HEIGHT * 0.06F;
        MASTER_DECK_X = (float)Settings.WIDTH - 96F * Settings.scale;
        MASTER_DECK_Y = (float)Settings.HEIGHT - 32F * Settings.scale;
        START_VELOCITY = 200F * Settings.scale;
        MAX_VELOCITY = 6000F * Settings.scale;
        VELOCITY_RAMP_RATE = 3000F * Settings.scale;
        DST_THRESHOLD = 36F * Settings.scale;
        HOME_IN_THRESHOLD = 72F * Settings.scale;
        ROTATION_RATE = 150F * Settings.scale;
    }
}
