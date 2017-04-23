package de.debitorlp.server.survivalgames.database.tables;

import java.util.UUID;

public class User {

    private String playerName;
    private UUID playerUUID;
    private int kills;
    private int deaths;
    private int rounds;
    private int wins;
    private int points;

    public User(String playerName, UUID playerUUID, int kills, int deaths, int rounds, int wins, int points) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.kills = kills;
        this.deaths = deaths;
        this.rounds = rounds;
        this.wins = wins;
        this.points = points;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}
