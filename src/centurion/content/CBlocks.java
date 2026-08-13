package centurion.content;

import centurion.world.blocks.SixteenDirectionBlock;
import centurion.world.blocks.distribution.computer;
import centurion.world.blocks.distribution.SafetyArm;
import centurion.world.blocks.alchemy.energy.CovalentBeamNode;
import centurion.world.blocks.alchemy.energy.CovalentCell;
import centurion.world.blocks.alchemy.TransmutationChamber;
import centurion.world.blocks.alchemy.TransmutationForge;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;

public class CBlocks {

    public static Block runeAltar, safetyArm, computerArm, covalentNode, covalentBeamNode, covalentCell, transmutationChamber, transmutationForge,test16Dir;

    public static void load() {

        /*runeAltar = new RuneAltar("rune-altar") {{
            requirements(Category.crafting, ItemStack.with(
                    CItems.cecilion, 10
            ));
            size = 2;
            craftTime = 180f;
        }};*/

        safetyArm = new SafetyArm("safety-arm") {{
            requirements(Category.distribution, ItemStack.with(
                    CItems.cecilion, 100
            ));
            description = "brazo de seguridad para agarrar items y llevarlos de forma segura a otros lugares";
            health = 500;
            armor = 1.5f;
            size = 2;
            buildTime = 100f;

        }};

        // Agrega la Computadora de Control
        computerArm = new computer("computer") {{
            requirements(Category.distribution, ItemStack.with(
                    CItems.cecilion, 8
            ));
            description = "Permite filtrar y controlar de mejor forma al brazo de seguridad";
            size = 1;
            armor = 1f;
            health = 300;
            rangeTiles = 6;
        }};

        covalentBeamNode = new CovalentBeamNode("covalent-beam-node") {{
            requirements(Category.power, ItemStack.with(
                    CItems.cecilion, 25
            ));
            description = "Nodo direccional de energía covalente: enlaza el primer bloque covalente en la dirección de colocación (gíralo con R) y dibuja un rayo hacia él";
            size = 1;
            health = 120;
            range = 7;
        }};

        covalentCell = new CovalentCell("covalent-cell") {{
            requirements(Category.power, ItemStack.with(
                    CItems.cecilion, 30
            ));
            description = "Celda de energía covalente: almacena E.C. producida por las cámaras de transmutación";
            size = 2;
            health = 250;
            covalentCapacity = 5000f;
        }};

        transmutationChamber = new TransmutationChamber("transmutation-chamber") {{
            requirements(Category.power, ItemStack.with(
                    CItems.cecilion, 50,
                    CItems.aluminum, 30
            ));
            configurable = false;
            description = "Cámara de transmutación: convierte ítems en energía covalente (E.C.)";
            size = 2;
            health = 400;
            transmuteItem = CItems.bauxite;
            transmuteTime = 45f;
            covalentProduction = 4f;
            powerBuffer = 1500f;
        }};

        transmutationForge = new TransmutationForge("transmutation-forge") {{
            requirements(Category.power, ItemStack.with(
                    CItems.cecilion, 50,
                    CItems.aluminum, 30
            ));
            description = "Forja de transmutación: convierte energía covalente (E.C.) en ítems seleccionados";
            size = 2;
            health = 400;
            craftTime = 30f;
            powerCapacity = 1500f;
        }};
        //You can delete it, it's just a test of the block base class, just wanted to give you a heads-up.
        test16Dir = new SixteenDirectionBlock("test-16dir"){{
            requirements(Category.distribution, ItemStack.with(Items.copper, 1));
            buildVisibility = BuildVisibility.shown;
            alwaysUnlocked = true;
            size = 1;
            destructible = true;
            health = 200;
            instantBuild = true;
            quickRotate = false;
        }};
    }
}