/*******************************************************************************
 * Copyright 2019 grondag
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package grondag.tdnf.mixin;

import org.spongepowered.asm.mixin.Mixin;

import grondag.tdnf.Dispatcher;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.PillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//TODO:  add neighbor check and also drop if supporting block is removed - make configurable
@Mixin(LogBlock.class)
public abstract class MixinLogBlock extends PillarBlock {
    
    public MixinLogBlock(MaterialColor materialColor_1, Block.Settings block$Settings_1) {
        super(block$Settings_1);
    }

    @Override
    public void onBlockRemoved(BlockState oldState, World world, BlockPos blockPos, BlockState newState, boolean notify) {
       if (oldState.getBlock() != newState.getBlock()) {
           Dispatcher.enqueCheck(world, blockPos);
       }
       super.onBlockRemoved(oldState, world, blockPos, newState, notify);
    }
}
