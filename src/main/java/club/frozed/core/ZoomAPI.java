package club.frozed.core;

import club.frozed.core.manager.player.PlayerData;
import club.frozed.core.manager.player.grants.Grant;
import club.frozed.core.manager.player.punishments.PunishmentType;
import club.frozed.core.manager.ranks.Rank;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ZoomAPI {

    public static String getLastServer(Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId());
        if (playerData != null) {
            return playerData.getLastServer();
        }

        return null;
    }

    public static boolean isStaffChat(Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId());
        if (playerData != null) {
            return playerData.isStaffChat();
        }

        return false;
    }

    public static boolean isAdminChat(Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId());
        if (playerData != null) {
            return playerData.isAdminChat();
        }

        return false;
    }

    public static ChatColor getNameColor(Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId());
        if (playerData != null) {
            return ChatColor.valueOf(playerData.getNameColor());
        }

        return null;
    }

    public static ChatColor getChatColor(Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId());
        if (playerData != null) {
            return ChatColor.valueOf(playerData.getChatColor());
        }

        return null;
    }

    public static boolean isSocialSpy(Player player) {
        PlayerData data = PlayerData.getPlayerData(player.getUniqueId());
        if (data != null) {
            return data.isSocialSpy();
        }

        return false;
    }

    public static int getPlayerCoins(Player player) {
        PlayerData data = PlayerData.getPlayerData(player.getUniqueId());
        if (data != null) {
            return data.getCoins();
        }

        return 0;
    }

    public static void setPlayerCoins(Player player, int coins) {
        PlayerData data = PlayerData.getPlayerData(player.getUniqueId());
        if (data != null) {
            data.setCoins(coins);
        }
    }

    public static void addPlayerCoins(Player player, int amount) {
        PlayerData data = PlayerData.getPlayerData(player.getUniqueId());
        if (data != null) {
            data.setCoins(data.getCoins() + amount);
        }
    }

    public static void removePlayerCoins(Player player, int amount) {
        PlayerData data = PlayerData.getPlayerData(player.getUniqueId());
        if (data != null) {
            data.setCoins(data.getCoins() - amount);
        }
    }

    public static boolean isLiked(Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId());
        if (playerData == null) {
            return false;
        }

        return playerData.isVote();
    }

    public static String getRankName(Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId());
        if (playerData == null) {
            return Zoom.getInstance().getRankManager().getDefaultRank().getName();
        }

        return playerData.getHighestRank().getName();
    }

    public static String getRankPrefix(Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId());
        if (playerData == null) {
            return Zoom.getInstance().getRankManager().getDefaultRank().getPrefix();
        }

        return playerData.getHighestRank().getPrefix() + " ";
    }

    public static ChatColor getRankColor(Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId());
        return playerData.getHighestRank().getColor();
    }

    public static String getRankSuffix(Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId());
        if (playerData == null) {
            return Zoom.getInstance().getRankManager().getDefaultRank().getSuffix();
        }

        return playerData.getHighestRank().getSuffix();
    }

    public static boolean hasPermission(Player player, String permissionToCheck) {
        if (player.isOp()) {
            return true;
        }

        PlayerData playerData = PlayerData.getPlayerData(player.getUniqueId());
        List<String> permissionsList = new ArrayList<>(playerData.getPermissions());
        for (Grant grant : playerData.getActiveGrants()) {
            Rank rank = grant.getRank();
            if (rank != null) {
                permissionsList.addAll(rank.getPermissions());
            }
        }
        return permissionsList.stream().filter(perm -> perm.equalsIgnoreCase(permissionToCheck)).findFirst().orElse(null) != null;
    }

    public static boolean isBanned(Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player.getName());
        return playerData.getActivePunishment(PunishmentType.BAN) != null;
    }

    public static boolean hasTag(Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player.getName());
        return playerData.getTag() != null;
    }

    public static String getTag(Player player) {
        PlayerData playerData = PlayerData.getPlayerData(player.getName());
        return playerData.getTag();
    }
}
