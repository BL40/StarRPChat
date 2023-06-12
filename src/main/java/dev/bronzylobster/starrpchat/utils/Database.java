package dev.bronzylobster.starrpchat.utils;

import org.bukkit.OfflinePlayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Database {
    private final String url;

    public Database() throws Exception {
        url = "jdbc:sqlite:plugins/StarRPChat/database.db";
        Class.forName("org.sqlite.JDBC").getConstructor().newInstance();

        Connection c = getConnection();
        Statement s = c.createStatement();

        s.executeUpdate("CREATE TABLE IF NOT EXISTS mute ('ID' INTEGER PRIMARY KEY AUTOINCREMENT, 'player' TEXT, 'time' BIGINT, 'reason' TEXT, 'updateTime' BIGINT)");

        s.close();
        c.close();
    }

    private Connection getConnection() throws Exception {
        return DriverManager.getConnection(url);
    }

    private void timeUpdate(int id) {
        try {
            Connection c = getConnection();
            Statement s = c.createStatement();

            ResultSet rs = s.executeQuery("SELECT time, updateTime FROM mute WHERE ID = " + id);
            long s_time = System.currentTimeMillis();
            long time = rs.getLong(1);
            long u_time = rs.getLong(2);
            time = time - (s_time - u_time);
            
            s.close();
            c.close();

            setMuteTime(id, time);
            setUpdateTime(id, s_time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMuted(String nick, long time, String reason, long s_time) {
        try {
            Connection c = getConnection();
            Statement s = c.createStatement();

            s.executeUpdate("INSERT INTO mute (player, time, reason, updateTime) VALUES ('" + nick + "', '" + time + "', '" + reason + "', '" + s_time + "')");

            s.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMuted(OfflinePlayer p, long time, String reason, long s_time) {
        try {
            Connection c = getConnection();
            Statement s = c.createStatement();

            String nick = p.getName();

            s.executeUpdate("INSERT INTO mute (player, time, reason, updateTime) VALUES ('" + nick + "', " + time + ", '" + reason + "', " + s_time + ")");

            s.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMuteTime(String nick, long time) {
        try {
            Connection c = getConnection();
            Statement s = c.createStatement();

            s.executeUpdate("UPDATE mute SET time = " + time + " WHERE player = '" + nick + "'");

            s.close();
            c.close();

            setUpdateTime(getID(nick), System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMuteTime(OfflinePlayer p, long time) {
        try {
            Connection c = getConnection();
            Statement s = c.createStatement();

            String nick = p.getName();

            s.executeUpdate("UPDATE mute SET time = " + time + " WHERE player = '" + nick + "'");

            s.close();
            c.close();

            setUpdateTime(getID(nick), System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMuteTime(int id, long time) {
        try {
            Connection c = getConnection();
            Statement s = c.createStatement();

            s.executeUpdate("UPDATE mute SET time = " + time + " WHERE id = " + id);

            s.close();
            c.close();

            setUpdateTime(id, System.currentTimeMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isMuted(String nick) {
        try {
            timeUpdate(getID(nick));

            Connection c = getConnection();
            Statement s = c.createStatement();

            boolean result;
            int count;

            ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM mute WHERE player = '" + nick + "'");
            count = rs.getInt(1);
            result = count > 0;

            s.close();
            c.close();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isMuted(OfflinePlayer p) {
        try {
            String nick = p.getName();

            timeUpdate(getID(nick));

            Connection c = getConnection();
            Statement s = c.createStatement();

            boolean result;
            int count;

            ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM mute WHERE player = '" + nick + "'");
            count = rs.getInt(1);
            result = count > 0;

            s.close();
            c.close();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public long getMuteTime(String nick) {
        try {
            timeUpdate(getID(nick));

            Connection c = getConnection();
            Statement s = c.createStatement();

            long count;
            ResultSet rs = s.executeQuery("SELECT time FROM mute WHERE player = '" + nick + "'");
            count = rs.getLong(1);

            s.close();
            c.close();

            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public long getMuteTime(OfflinePlayer p) {
        try {
            String nick = p.getName();

            timeUpdate(getID(nick));

            Connection c = getConnection();
            Statement s = c.createStatement();

            long count;
            ResultSet rs = s.executeQuery("SELECT time FROM mute WHERE player = '" + nick + "'");
            count = rs.getLong(1);

            s.close();
            c.close();

            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void setUpdateTime(int id, long time) {
        try {
            Connection c = getConnection();
            Statement s = c.createStatement();

            s.executeUpdate("UPDATE mute SET updateTime = " + time + " WHERE ID = " + id);

            s.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getID(String nick) {
        try {
            Connection c = getConnection();
            Statement s = c.createStatement();

            int count;

            ResultSet rs = s.executeQuery("SELECT ID FROM mute WHERE player = '" + nick + "'");
            count = rs.getInt(1);

            s.close();
            c.close();

            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getID(OfflinePlayer p) {
        try {
            Connection c = getConnection();
            Statement s = c.createStatement();

            int count;
            String nick = p.getName();

            ResultSet rs = s.executeQuery("SELECT time FROM mute WHERE player = '" + nick + "'");
            count = rs.getInt(1);

            s.close();
            c.close();

            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void removeMuted(String nick) {
        try {
            Connection c = getConnection();
            Statement s = c.createStatement();

            s.executeUpdate("DELETE FROM mute WHERE ID = " + getID(nick));

            s.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeMuted(OfflinePlayer p) {
        try {
            Connection c = getConnection();
            Statement s = c.createStatement();

            String nick = p.getName();

            s.executeUpdate("DELETE FROM mute WHERE ID = " + getID(nick));

            s.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getReason(String nick) {
        try {
            Connection c = getConnection();
            Statement s = c.createStatement();

            String reason;

            ResultSet rs = s.executeQuery("SELECT reason FROM mute WHERE player = '" + nick + "'");
            reason = rs.getString(1);

            s.close();
            c.close();

            return reason;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
