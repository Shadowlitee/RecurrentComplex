/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.reccomplex.worldgen.blockTransformers;

import com.google.gson.*;
import ivorius.ivtoolkit.tools.MCRegistry;
import ivorius.reccomplex.gui.editstructure.TableDataSourceBTNaturalAir;
import ivorius.reccomplex.gui.table.TableDataSource;
import ivorius.reccomplex.worldgen.MCRegistrySpecial;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import ivorius.reccomplex.json.JsonUtils;


import java.lang.reflect.Type;

/**
 * Created by lukas on 05.06.14.
 */
public class BTProviderNaturalAir implements BlockTransformerProvider<BlockTransformerNaturalAir>
{
    private Serializer serializer;

    public BTProviderNaturalAir()
    {
        serializer = new Serializer(MCRegistrySpecial.INSTANCE);
    }

    @Override
    public BlockTransformerNaturalAir defaultTransformer()
    {
        return new BlockTransformerNaturalAir(Blocks.grass, 0);
    }

    @Override
    public TableDataSource tableDataSource(BlockTransformerNaturalAir element)
    {
        return new TableDataSourceBTNaturalAir(element);
    }

    @Override
    public JsonSerializer<BlockTransformerNaturalAir> serializer()
    {
        return serializer;
    }

    @Override
    public JsonDeserializer<BlockTransformerNaturalAir> deserializer()
    {
        return serializer;
    }

    public static class Serializer implements JsonDeserializer<BlockTransformerNaturalAir>, JsonSerializer<BlockTransformerNaturalAir>
    {
        private MCRegistry registry;

        public Serializer(MCRegistry registry)
        {
            this.registry = registry;
        }

        @Override
        public BlockTransformerNaturalAir deserialize(JsonElement jsonElement, Type par2Type, JsonDeserializationContext context)
        {
            JsonObject jsonobject = JsonUtils.getJsonElementAsJsonObject(jsonElement, "transformerNatural");

            String sourceBlock = JsonUtils.getJsonObjectStringFieldValue(jsonobject, "source");
            Block source = registry.blockFromID(sourceBlock);
            int sourceMeta = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(jsonobject, "sourceMetadata", -1);

            return new BlockTransformerNaturalAir(source, sourceMeta);
        }

        @Override
        public JsonElement serialize(BlockTransformerNaturalAir transformerPillar, Type par2Type, JsonSerializationContext context)
        {
            JsonObject jsonobject = new JsonObject();

            jsonobject.addProperty("source", Block.blockRegistry.getNameForObject(transformerPillar.sourceBlock));
            if (transformerPillar.sourceMetadata >= 0)
            {
                jsonobject.addProperty("sourceMetadata", transformerPillar.sourceMetadata);
            }

            return jsonobject;
        }
    }
}
