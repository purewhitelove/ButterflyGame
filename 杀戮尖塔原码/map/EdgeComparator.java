// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MapEdge.java

package com.megacrit.cardcrawl.map;

import java.util.Comparator;

// Referenced classes of package com.megacrit.cardcrawl.map:
//            MapEdge

class EdgeComparator
    implements Comparator
{

    EdgeComparator()
    {
    }

    public int compare(MapEdge e1, MapEdge e2)
    {
        return e1.compareTo(e2);
    }

    public volatile int compare(Object obj, Object obj1)
    {
        return compare((MapEdge)obj, (MapEdge)obj1);
    }
}
