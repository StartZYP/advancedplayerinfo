package com.ipedg.minecraft.advancedplayrinfo;

import com.ipedg.minecraft.advancedplayrinfo.tools.dao.DaoTool;
import com.ipedg.minecraft.advancedplayrinfo.tools.dao.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: Startzyp
 * @Date: 2019/10/16 22:10
 */
public class main extends JavaPlugin implements Listener {

    public static Map<String,PlayerInfo> onlineplayer = new HashMap<>();
    @Override
    public void onEnable() {
        ReloadConfig();
        new DaoTool(getDataFolder().getPath()+File.separator+"advplayer");
        StartThread();
        Bukkit.getServer().getPluginManager().registerEvents(this,this);
        super.onEnable();
    }
    @EventHandler
    public void PlayerJoinGame(PlayerJoinEvent event){
        String name = event.getPlayer().getName();
        PlayerInfo playerInfo = DaoTool.GetPlayer(name);
        playerInfo.setPlayerName(name);
        //从数据库查询玩家已经在线时长
        onlineplayer.put(event.getPlayer().getName(),playerInfo);
    }

    @EventHandler
    public void PlayerQuitGame(PlayerQuitEvent event){
        String name = event.getPlayer().getName();
        if (onlineplayer.containsKey(name)){
            PlayerInfo playerInfo = onlineplayer.get(name);
            onlineplayer.remove(name);
            DaoTool.Updata(name,playerInfo.getOnlinetime());
        }
    }

    private void ReloadConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(), "config.yml");
        if (!(file.exists())) {
            saveDefaultConfig();
        }
    }

    private void AddTime(){
        for (String key:onlineplayer.keySet()){
            PlayerInfo playerInfo = onlineplayer.get(key);
            playerInfo.setOnlinetime(playerInfo.getOnlinetime()+1);
            System.out.println(key+":"+playerInfo.toString());
            onlineplayer.put(key,playerInfo);
        }
    }

    private void StartThread(){
        new BukkitRunnable(){
            @Override
            public void run() {
                AddTime();
            }
        }.runTaskTimer(this,20L,60L*20L);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String name = sender.getName();
        if (onlineplayer.containsKey(name)&&label.equalsIgnoreCase("timeattm")){
            PlayerInfo playerInfo = onlineplayer.get(name);
            sender.sendMessage("§e§l您的名字为：§4§l"+name);
            sender.sendMessage("§5§l你的编号: §3§l["+Integer.parseInt(playerInfo.getPlayerId())+100+"]");
            sender.sendMessage("§2§l您总共游玩时间为:§d§l"+onlineplayer.get(name).getOnlinetime()+"分钟");
        }else if (onlineplayer.containsKey(name)&&label.equalsIgnoreCase("tmplayer")&&args.length==1){
            PlayerInfo playerInfo = DaoTool.GetIdPlayer(args[0]);
            if (playerInfo!=null){
                sender.sendMessage("§6§l该玩家名字为："+playerInfo.getPlayerName());
            }else {
                sender.sendMessage("§4§l没有该玩家");
            }
        }
        return super.onCommand(sender, command, label, args);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
