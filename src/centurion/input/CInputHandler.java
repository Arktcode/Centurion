package centurion.input;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import mindustry.entities.units.*;
import mindustry.graphics.Pal;
import mindustry.input.*;
import mindustry.world.*;
import centurion.world.blocks.*;

import static mindustry.Vars.*;

/**
 * Expanded input handling, supporting 16-direction blocks with 22.5° rotation steps。
 * Interaction design：
 *   R key/scroll → currentFullDir ± 1（Step 22.5°）
 *   Shift+mouse → Calculate based on the mouse angle（Processing in drawPlace）
 *   Sync rotation every frame = currentFullDir / 4 to ensure original version compatibility
 */
public class CInputHandler extends DesktopInput {

    @Override
    public void update() {
        if (block instanceof SixteenDirectionBlock && selectPlans.isEmpty()) {
            // Single block mode: use R/scroll wheel to step in 16 directions
            int axis = (int) Core.input.axisTap(Binding.rotate);
            if (axis != 0) {
                SixteenDirectionBlock.addCurrentFullDir(Mathf.sign(axis));
            }
            rotation = SixteenDirectionBlock.getCurrentFullDir() / 4;
            super.update();
        } else {
            // Blueprint mode: don't consume, let the original rotatePlans handle it
            super.update();
        }
    }

    @Override
    public void rotatePlans(Seq<BuildPlan> plans, int direction) {
        // Sync rotate 16-way config (the original only changed plan.rotation to 4-way)
        for (var plan : plans) {
            if (!plan.breaking && plan.block instanceof SixteenDirectionBlock && plan.config instanceof Integer cfg) {
                plan.config = Mathf.mod(cfg + direction * 4, 16);
            }
        }
        super.rotatePlans(plans, direction);
    }

    @Override
    public void drawArrow(Block block, int x, int y, int rotation, boolean valid) {
        if (block instanceof SixteenDirectionBlock) {
            int fullRot = SixteenDirectionBlock.fullRotation(rotation);
            float angle = SixteenDirectionBlock.angleFrom(fullRot);

            float trns = (block.size / 2) * tilesize;
            float dx = Angles.trnsx(angle, trns);
            float dy = Angles.trnsy(angle, trns);
            float ox = x * tilesize + block.offset + dx;
            float oy = y * tilesize + block.offset + dy;

            Draw.color(!valid ? Pal.removeBack : Pal.accentBack);
            TextureRegion arrow = Core.atlas.find("place-arrow");
            Draw.rect(arrow, ox, oy - 1,
                arrow.width * arrow.scl(),
                arrow.height * arrow.scl(),
                angle - 90);

            Draw.color(!valid ? Pal.remove : Pal.accent);
            Draw.rect(arrow, ox, oy,
                arrow.width * arrow.scl(),
                arrow.height * arrow.scl(),
                angle - 90);
            Draw.reset();
            return;
        }
        super.drawArrow(block, x, y, rotation, valid);
    }

    @Override
    protected void flushPlans(Seq<BuildPlan> plans) {
        if (block instanceof SixteenDirectionBlock) {
            // Bypass the rotation check of Build.validPlaceIgnoreUnits (Build.java:252)
            // The same type with the same rotation will be banned in the original version; temporarily changing the rotation to pass the check,
            // Then fix it back in SixteenDirectionBuild.configured()
            for (var plan : plans) {
                Tile tile = world.tile(plan.x, plan.y);
                if (tile != null && tile.build != null && tile.block() == plan.block && tile.team() == player.team()
                    && plan.rotation == tile.build.rotation) {
                    // 16-direction exclusive check: only block building with the same config, bypass with a different config
                    if (tile.build instanceof SixteenDirectionBlock.SixteenDirectionBuild sdBuild
                        && plan.config instanceof Integer cfg) {
                        if (sdBuild.getFullRotation() == Mathf.mod(cfg, 16)) {
                            continue; // Same config → let the original rotation check block it
                        }
                    }
                    plan.rotation = (plan.rotation + 1) % 4;
                }
            }
        }
        super.flushPlans(plans);
    }
}
