package com.apuestasonline.ws.rest.model;

import java.util.List;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Roulette {
	private String idRoulette;
	private boolean state;
	private List<Bet> bets;
	private String result;
	public Roulette() {}
	public Roulette(String idRoulette) {
		this.idRoulette=idRoulette;
		this.state=false;
	}
	public String getIdRoulette() {
		return idRoulette;
	}
	public void setIdRuet(String idRoulette) {
		this.idRoulette = idRoulette;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public List<Bet> getBets() {
		return bets;
	}
	public void setBets(List<Bet> bets) {
		this.bets = bets;
	}
	public void addBet(Bet bet) {
		this.bets.add(bet);
	}
	public JSONObject idToJson() throws JSONException {
		JSONObject idJson = new JSONObject();
		idJson.accumulate("idRoulette", this.idRoulette);
		return idJson;
	}
	public JSONObject stateToJson() throws JSONException {
		JSONObject stateJson = new JSONObject();
		stateJson.accumulate("state", this.state);
		return stateJson;
	}
}
