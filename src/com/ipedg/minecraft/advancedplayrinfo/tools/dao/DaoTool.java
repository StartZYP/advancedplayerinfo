package com.ipedg.minecraft.advancedplayrinfo.tools.dao;

import java.sql.*;

/**
 * @Author: Startzyp
 * @Date: 2019/10/17 1:08
 */
public class DaoTool {
    private static Connection connection = null;
    private static Statement statement = null;

    public DaoTool(String DatabasePath){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"+DatabasePath+".db");
            statement = connection.createStatement();
            String Table = "CREATE TABLE IF NOT EXISTS 'myTable'( ID INTEGER PRIMARY KEY  AUTOINCREMENT,PlayerName VARCHAR(100), OnlineTime Long)";
            statement.executeUpdate(Table);
            statement.close();
        } catch ( Exception e ) {
        }
    }


    public static void AddDate(String PlayerName,Long OnlineTime){
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "INSERT INTO myTable (PlayerName,OnlineTime)values('"+PlayerName+"','"+OnlineTime+"')";
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void Updata(String PlayerName,Long OnlineTime){
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "update myTable set OnlineTime='"+OnlineTime+"' where PlayerName='"+PlayerName+"'";
            statement.executeUpdate(sql);
            connection.commit();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public static PlayerInfo GetPlayer(String PlayerName){
        PlayerInfo playerInfo = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            String GetSuperGroupSql = "select * from myTable where PlayerName='"+PlayerName+"'";
            ResultSet rs = statement.executeQuery(GetSuperGroupSql);
            while (rs.next()){
                playerInfo = new PlayerInfo(rs.getString("ID"),rs.getLong("OnlineTime"));
            }
            rs.close();
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        if (playerInfo==null){
            System.out.println("为空");
            AddDate(PlayerName,1L);
            playerInfo = new PlayerInfo(PlayerName,1L);
        }
        return playerInfo;
    }


}