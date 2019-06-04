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

package grondag.tdnf;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.PacketByteBuf;

@Environment(EnvType.CLIENT)
public class FallingLogNetworkHandler {

    public static void accept(PacketContext context, PacketByteBuf buffer) {
        final MinecraftClient client = MinecraftClient.getInstance();
        FallingLogEntity entity = new FallingLogEntity(FallingLogEntity.FALLING_LOG, client.world);
       entity.fromBuffer(buffer);
       if(client.isOnThread()) {
           spawn(client, entity);
       } else {
           client.execute(() -> spawn(client, entity));
       }
    }
    
    private static void spawn(MinecraftClient client, FallingLogEntity entity) {
        final ClientWorld world = client.world;
        if(world == null) {
            return;
        }
        world.addEntity(entity.getEntityId(), entity);      
    }
}
