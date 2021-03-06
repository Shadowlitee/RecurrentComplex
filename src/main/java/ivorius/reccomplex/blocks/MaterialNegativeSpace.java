/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.reccomplex.blocks;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

/**
 * Created by lukas on 25.05.14.
 */
public class MaterialNegativeSpace extends Material
{
    public MaterialNegativeSpace()
    {
        super(MapColor.airColor);

        setReplaceable();
    }

    @Override
    public boolean isSolid()
    {
        return false;
    }

    @Override
    public boolean getCanBlockGrass()
    {
        return false;
    }

    @Override
    public boolean blocksMovement()
    {
        return false;
    }
}
