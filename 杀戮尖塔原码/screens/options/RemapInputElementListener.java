// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RemapInputElementListener.java

package com.megacrit.cardcrawl.screens.options;


// Referenced classes of package com.megacrit.cardcrawl.screens.options:
//            RemapInputElement

public interface RemapInputElementListener
{

    public abstract void didStartRemapping(RemapInputElement remapinputelement);

    public abstract boolean willRemap(RemapInputElement remapinputelement, int i, int j);

    public abstract boolean willRemapController(RemapInputElement remapinputelement, int i, int j);
}
