package com.carta.nbaservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {

    private Integer id;
    private int ast;
    private int blk;
    private int dreb;
    @JsonProperty("fg3_pc")
    private double fg3Pc;
    private int fg3a;
    private int fg3m;
    @JsonProperty("fg_pct")
    private double fgPct;
    private int fga;
    private int fgm;
    @JsonProperty("ft_pct")
    private double ftPct;
    private int fta;
    private int ftm;
    private Game game;
    private String min;
    private int oreb;
    private int pf;
    private Player player;
    private int pts;
    private int reb;
    private int stl;
    private Team team;
    private int turnover;

    public Player(Integer id, int ast, int blk, int dreb, double fg3Pc, int fg3a, int fg3m, double fgPct, int fga, int fgm, double ftPct, int ftm, int fta,
            int ftm1, Game game, String min, int oreb, int pf, Player player, int pts, int reb, int stl, Team team, int turnover) {
        this.id = id;
        this.ast = ast;
        this.blk = blk;
        this.dreb = dreb;
        this.fg3Pc = fg3Pc;
        this.fg3a = fg3a;
        this.fg3m = fg3m;
        this.fgPct = fgPct;
        this.fga = fga;
        this.fgm = fgm;
        this.ftPct = ftPct;
        this.ftm = ftm;
        this.fta = fta;
        this.ftm = ftm1;
        this.game = game;
        this.min = min;
        this.oreb = oreb;
        this.pf = pf;
        this.player = player;
        this.pts = pts;
        this.reb = reb;
        this.stl = stl;
        this.team = team;
        this.turnover = turnover;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAst() {
        return ast;
    }

    public void setAst(int ast) {
        this.ast = ast;
    }

    public int getBlk() {
        return blk;
    }

    public void setBlk(int blk) {
        this.blk = blk;
    }

    public int getDreb() {
        return dreb;
    }

    public void setDreb(int dreb) {
        this.dreb = dreb;
    }

    public double getFg3Pc() {
        return fg3Pc;
    }

    public void setFg3Pc(double fg3Pc) {
        this.fg3Pc = fg3Pc;
    }

    public int getFg3a() {
        return fg3a;
    }

    public void setFg3a(int fg3a) {
        this.fg3a = fg3a;
    }

    public int getFg3m() {
        return fg3m;
    }

    public void setFg3m(int fg3m) {
        this.fg3m = fg3m;
    }

    public double getFgPct() {
        return fgPct;
    }

    public void setFgPct(double fgPct) {
        this.fgPct = fgPct;
    }

    public int getFga() {
        return fga;
    }

    public void setFga(int fga) {
        this.fga = fga;
    }

    public int getFgm() {
        return fgm;
    }

    public void setFgm(int fgm) {
        this.fgm = fgm;
    }

    public double getFtPct() {
        return ftPct;
    }

    public void setFtPct(double ftPct) {
        this.ftPct = ftPct;
    }

    public int getFtm() {
        return ftm;
    }

    public void setFtm(int ftm) {
        this.ftm = ftm;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public int getOreb() {
        return oreb;
    }

    public void setOreb(int oreb) {
        this.oreb = oreb;
    }

    public int getPf() {
        return pf;
    }

    public void setPf(int pf) {
        this.pf = pf;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPts() {
        return pts;
    }

    public void setPts(int pts) {
        this.pts = pts;
    }

    public int getReb() {
        return reb;
    }

    public void setReb(int reb) {
        this.reb = reb;
    }

    public int getStl() {
        return stl;
    }

    public void setStl(int stl) {
        this.stl = stl;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getTurnover() {
        return turnover;
    }

    public void setTurnover(int turnover) {
        this.turnover = turnover;
    }

    public int getFta() {
        return fta;
    }

    public void setFta(int fta) {
        this.fta = fta;
    }
}
