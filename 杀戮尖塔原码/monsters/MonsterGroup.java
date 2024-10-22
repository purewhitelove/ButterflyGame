// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MonsterGroup.java

package com.megacrit.cardcrawl.monsters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MonsterHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.monsters:
//            AbstractMonster, MonsterQueueItem

public class MonsterGroup
{

    public MonsterGroup(AbstractMonster input[])
    {
        monsters = new ArrayList();
        hoveredMonster = null;
        Collections.addAll(monsters, input);
    }

    public void addMonster(int newIndex, AbstractMonster m)
    {
        if(newIndex < 0)
            newIndex = 0;
        monsters.add(newIndex, m);
    }

    /**
     * @deprecated Method addMonster is deprecated
     */

    public void addMonster(AbstractMonster m)
    {
        monsters.add(m);
    }

    /**
     * @deprecated Method addSpawnedMonster is deprecated
     */

    public void addSpawnedMonster(AbstractMonster m)
    {
        monsters.add(0, m);
    }

    public MonsterGroup(AbstractMonster m)
    {
        this(new AbstractMonster[] {
            m
        });
    }

    public void showIntent()
    {
        AbstractMonster m;
        for(Iterator iterator = monsters.iterator(); iterator.hasNext(); m.createIntent())
            m = (AbstractMonster)iterator.next();

    }

    public void init()
    {
        AbstractMonster m;
        for(Iterator iterator = monsters.iterator(); iterator.hasNext(); m.init())
            m = (AbstractMonster)iterator.next();

    }

    public void add(AbstractMonster m)
    {
        monsters.add(m);
    }

    public void usePreBattleAction()
    {
        if(AbstractDungeon.loading_post_combat)
            return;
        AbstractMonster m;
        for(Iterator iterator = monsters.iterator(); iterator.hasNext(); m.useUniversalPreBattleAction())
        {
            m = (AbstractMonster)iterator.next();
            m.usePreBattleAction();
        }

    }

    public boolean areMonstersDead()
    {
        for(Iterator iterator = monsters.iterator(); iterator.hasNext();)
        {
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.isDead && !m.escaped)
                return false;
        }

        return true;
    }

    public boolean areMonstersBasicallyDead()
    {
        for(Iterator iterator = monsters.iterator(); iterator.hasNext();)
        {
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.isDying && !m.isEscaping)
                return false;
        }

        return true;
    }

    public void applyPreTurnLogic()
    {
        Iterator iterator = monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.isDying && !m.isEscaping)
            {
                if(!m.hasPower("Barricade"))
                    m.loseBlock();
                m.applyStartOfTurnPowers();
            }
        } while(true);
    }

    public AbstractMonster getMonster(String id)
    {
        for(Iterator iterator = monsters.iterator(); iterator.hasNext();)
        {
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(m.id.equals(id))
                return m;
        }

        logger.info((new StringBuilder()).append("MONSTER GROUP getMonster(): Could not find monster: ").append(id).toString());
        return null;
    }

    public void queueMonsters()
    {
        Iterator iterator = monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.isDeadOrEscaped() || m.halfDead)
                AbstractDungeon.actionManager.monsterQueue.add(new MonsterQueueItem(m));
        } while(true);
    }

    public boolean haveMonstersEscaped()
    {
        for(Iterator iterator = monsters.iterator(); iterator.hasNext();)
        {
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.escaped)
                return false;
        }

        return true;
    }

    public boolean isMonsterEscaping()
    {
        for(Iterator iterator = monsters.iterator(); iterator.hasNext();)
        {
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(m.nextMove == 99)
                return true;
        }

        return false;
    }

    public boolean hasMonsterEscaped()
    {
        for(Iterator iterator = monsters.iterator(); iterator.hasNext();)
        {
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(m.isEscaping)
                return true;
        }

        return CardCrawlGame.dungeon instanceof TheCity;
    }

    public AbstractMonster getRandomMonster()
    {
        return getRandomMonster(null, false);
    }

    public AbstractMonster getRandomMonster(boolean aliveOnly)
    {
        return getRandomMonster(null, aliveOnly);
    }

    public AbstractMonster getRandomMonster(AbstractMonster exception, boolean aliveOnly, Random rng)
    {
        if(areMonstersBasicallyDead())
            return null;
        ArrayList tmp;
        if(exception == null)
            if(aliveOnly)
            {
                tmp = new ArrayList();
                Iterator iterator = monsters.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    AbstractMonster m = (AbstractMonster)iterator.next();
                    if(!m.halfDead && !m.isDying && !m.isEscaping)
                        tmp.add(m);
                } while(true);
                if(tmp.size() <= 0)
                    return null;
                else
                    return (AbstractMonster)tmp.get(rng.random(0, tmp.size() - 1));
            } else
            {
                return (AbstractMonster)monsters.get(rng.random(0, monsters.size() - 1));
            }
        if(monsters.size() == 1)
            return (AbstractMonster)monsters.get(0);
        if(aliveOnly)
        {
            tmp = new ArrayList();
            Iterator iterator1 = monsters.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator1.next();
                if(!m.halfDead && !m.isDying && !m.isEscaping && !exception.equals(m))
                    tmp.add(m);
            } while(true);
            if(tmp.size() == 0)
                return null;
            else
                return (AbstractMonster)tmp.get(rng.random(0, tmp.size() - 1));
        }
        tmp = new ArrayList();
        Iterator iterator2 = monsters.iterator();
        do
        {
            if(!iterator2.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator2.next();
            if(!exception.equals(m))
                tmp.add(m);
        } while(true);
        return (AbstractMonster)tmp.get(rng.random(0, tmp.size() - 1));
    }

    public AbstractMonster getRandomMonster(AbstractMonster exception, boolean aliveOnly)
    {
        if(areMonstersBasicallyDead())
            return null;
        ArrayList tmp;
        if(exception == null)
            if(aliveOnly)
            {
                tmp = new ArrayList();
                Iterator iterator = monsters.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    AbstractMonster m = (AbstractMonster)iterator.next();
                    if(!m.halfDead && !m.isDying && !m.isEscaping)
                        tmp.add(m);
                } while(true);
                if(tmp.size() <= 0)
                    return null;
                else
                    return (AbstractMonster)tmp.get(MathUtils.random(0, tmp.size() - 1));
            } else
            {
                return (AbstractMonster)monsters.get(MathUtils.random(0, monsters.size() - 1));
            }
        if(monsters.size() == 1)
            return (AbstractMonster)monsters.get(0);
        if(aliveOnly)
        {
            tmp = new ArrayList();
            Iterator iterator1 = monsters.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator1.next();
                if(!m.halfDead && !m.isDying && !m.isEscaping && !exception.equals(m))
                    tmp.add(m);
            } while(true);
            if(tmp.size() == 0)
                return null;
            else
                return (AbstractMonster)tmp.get(MathUtils.random(0, tmp.size() - 1));
        }
        tmp = new ArrayList();
        Iterator iterator2 = monsters.iterator();
        do
        {
            if(!iterator2.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator2.next();
            if(!exception.equals(m))
                tmp.add(m);
        } while(true);
        return (AbstractMonster)tmp.get(MathUtils.random(0, tmp.size() - 1));
    }

    public void update()
    {
        AbstractMonster m;
        for(Iterator iterator = monsters.iterator(); iterator.hasNext(); m.update())
            m = (AbstractMonster)iterator.next();

        if(AbstractDungeon.screen != com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.DEATH)
        {
            hoveredMonster = null;
            Iterator iterator1 = monsters.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator1.next();
                if(m.isDying || m.isEscaping)
                    continue;
                m.hb.update();
                m.intentHb.update();
                m.healthHb.update();
                if(!m.hb.hovered && !m.intentHb.hovered && !m.healthHb.hovered || AbstractDungeon.player.isDraggingCard)
                    continue;
                hoveredMonster = m;
                break;
            } while(true);
            if(hoveredMonster == null)
                AbstractDungeon.player.hoverEnemyWaitTimer = -1F;
        } else
        {
            hoveredMonster = null;
        }
    }

    public void updateAnimations()
    {
        AbstractMonster m;
        for(Iterator iterator = monsters.iterator(); iterator.hasNext(); m.updatePowers())
            m = (AbstractMonster)iterator.next();

    }

    public boolean shouldFlipVfx()
    {
        return AbstractDungeon.lastCombatMetricKey.equals("Shield and Spear") && ((AbstractMonster)monsters.get(1)).isDying;
    }

    public void escape()
    {
        AbstractMonster m;
        for(Iterator iterator = monsters.iterator(); iterator.hasNext(); m.escape())
            m = (AbstractMonster)iterator.next();

    }

    public void unhover()
    {
        AbstractMonster m;
        for(Iterator iterator = monsters.iterator(); iterator.hasNext(); m.unhover())
            m = (AbstractMonster)iterator.next();

    }

    public void render(SpriteBatch sb)
    {
        if(hoveredMonster != null && !hoveredMonster.isDead && !hoveredMonster.escaped && AbstractDungeon.player.hoverEnemyWaitTimer < 0.0F && (!AbstractDungeon.isScreenUp || PeekButton.isPeeking))
            hoveredMonster.renderTip(sb);
        AbstractMonster m;
        for(Iterator iterator = monsters.iterator(); iterator.hasNext(); m.render(sb))
            m = (AbstractMonster)iterator.next();

    }

    public void applyEndOfTurnPowers()
    {
        Iterator iterator = monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.isDying && !m.isEscaping)
                m.applyEndOfTurnTriggers();
        } while(true);
        AbstractPower p;
        for(iterator = AbstractDungeon.player.powers.iterator(); iterator.hasNext(); p.atEndOfRound())
            p = (AbstractPower)iterator.next();

        iterator = monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.isDying && !m.isEscaping)
            {
                Iterator iterator1 = m.powers.iterator();
                while(iterator1.hasNext()) 
                {
                    AbstractPower p = (AbstractPower)iterator1.next();
                    p.atEndOfRound();
                }
            }
        } while(true);
    }

    public void renderReticle(SpriteBatch sb)
    {
        Iterator iterator = monsters.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractMonster m = (AbstractMonster)iterator.next();
            if(!m.isDying && !m.isEscaping)
                m.renderReticle(sb);
        } while(true);
    }

    public ArrayList getMonsterNames()
    {
        ArrayList arr = new ArrayList();
        AbstractMonster m;
        for(Iterator iterator = monsters.iterator(); iterator.hasNext(); arr.add(m.id))
            m = (AbstractMonster)iterator.next();

        return arr;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/monsters/MonsterGroup.getName());
    public ArrayList monsters;
    public AbstractMonster hoveredMonster;

}
