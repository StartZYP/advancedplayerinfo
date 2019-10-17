package com.ipedg.minecraft.advancedplayrinfo.tools.dao;


/**
 * @Author: Startzyp
 * @Date: 2019/10/17 0:30
 */
public class PlayerInfo {
    private String PlayerId;
    private Long onlinetime;
    private String PlayerName;


    public PlayerInfo(String playerId, Long onlinetime, String playerName) {
        PlayerId = playerId;
        this.onlinetime = onlinetime;
        PlayerName = playerName;
    }

    public PlayerInfo(String playerId, Long onlinetime) {
        PlayerId = playerId;
        this.onlinetime = onlinetime;
    }

    @Override
    public String toString() {
        return "PlayerInfo{" +
                "PlayerId='" + PlayerId + '\'' +
                ", onlinetime=" + onlinetime +
                '}';
    }

    public String getPlayerId() {
        return PlayerId;
    }

    public void setPlayerId(String playerId) {
        PlayerId = playerId;
    }

    public Long getOnlinetime() {
        return onlinetime;
    }

    public void setOnlinetime(Long onlinetime) {
        this.onlinetime = onlinetime;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }
}
