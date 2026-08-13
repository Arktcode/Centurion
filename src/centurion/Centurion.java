package centurion;

import arc.Core;
import arc.Events;
import arc.util.Log;
import arc.util.Time;
import centurion.content.CBlocks;
import centurion.content.CItems;
import centurion.input.CInputHandler;
import centurion.utils.stats.CenturyStats;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.mod.Mod;
import mindustry.ui.dialogs.BaseDialog;

import static mindustry.Vars.control;

public class Centurion extends Mod {
    /**If you want your block to inherit the 16-direction block, please uncomment it, otherwise it won't work. Mobile input replacement is not finished, you need to complete it. This block provides hooks for 16 directions, and you can use 16-direction features even if you don't inherit it.*/
    //The comment status has been removed
    @Override
    public void init() {
        // Replace the input processor (client-side only, except mobile)
        Events.on(EventType.ClientLoadEvent.class, e -> {
            if(!Vars.mobile){
                control.setInput(new CInputHandler());
            }
        });
    }

    public Centurion(){
        Log.info("Loaded Centurion constructor.");

        Events.on(ClientLoadEvent.class, e -> {
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("frog");
                dialog.cont.add("Pls Sentinel no te chinges el mod TQ ;3").row();
                dialog.cont.add("demasiado tarde, ya me chinge el mod").row();
                dialog.cont.image(Core.atlas.find("calajo")).pad(20f).row();
                dialog.cont.button("ya veo", dialog::hide).size(100f, 50f);
                dialog.show();
            });
        });
    }

    @Override
    public void loadContent(){
        Log.info("Loading Centurion content.");
        CenturyStats.load();
        CItems.load();
        CBlocks.load();
    }

}
