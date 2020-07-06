package com.apuestasonline.ws.rest.model;

public class Bet {
	private String idRoulette;
	private String idBet;
	private String idPlayer;
	private int betAmount;
	private String bet;
	private boolean win;
	public String getIdRoulette() {
		return idRoulette;
	}
	public void setIdRoulette(String idRoulette) {
		this.idRoulette = idRoulette;
	}	
	public String getIdBet() {
		return idBet;
	}
	public void setIdBet(String idBet) {
		this.idBet = idBet;
	}
	public String getIdPlayer() {
		return idPlayer;
	}
	public void setIdPlayer(String idPlayer) {
		this.idPlayer = idPlayer;
	}
	public int getBetAmount() {
		return betAmount;
	}
	public void setBetAmount(int betAmount) {
		this.betAmount = betAmount;
	}
	public String getBet() {
		return bet;
	}
	public void setBet(String bet) {
		this.bet = bet;
	}
	public boolean isWin() {
		return win;
	}
	public void setWin(boolean win) {
		this.win = win;
	}
}
