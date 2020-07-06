package com.apuestasonline.ws.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.apuestasonline.ws.rest.controller.Controller;
import com.apuestasonline.ws.rest.model.Bet;
import com.apuestasonline.ws.rest.model.Roulette;

@Path("/bets")
public class ServiceBets {
	private Controller controller;
	public ServiceBets() {
		 controller = new Controller();
	}
//	@POST
//	@Path("/user")
//	@Consumes({MediaType.APPLICATION_JSON})
//	@Produces({MediaType.APPLICATION_JSON})
//	public User user(User us) {
//		us.setName("gabriel");
//		us.setAge(27);
//		return us;
//	}
//	@POST
//	@Path("/username")
//	@Consumes({MediaType.APPLICATION_JSON})
//	@Produces({MediaType.APPLICATION_JSON})
//	public JSONObject getNewRoulette() throws JSONException {
//		User us = new User();
//		us.setName("gabriel");
//		us.setAge(27);
//		
//		//return us.getName();
//		return us.nameToJson();
//	}
	
	
	
	
	
	@POST
	@Path("/newroulette")
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject newRoulette() throws JSONException {			
		return messageJson("idRoulette", controller.newRoulette().getIdRoulette());
	}
	@POST
	@Path("/openroulette")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject openRouletteById(Roulette roulette) throws JSONException {
		if(controller.openRoulette(roulette)) {
			return messageJson("state", "true"); 
		}else {
			return messageJson("state", "false");			
		}
	}
	@POST
	@Path("/newbet")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject newBet(Bet bet) throws JSONException {
		if(bet.getBetAmount() >= 10000) {
			return messageJson("error", "bet acount exceeds");
		}
		if(controller.newBet(bet)) {
			return messageJson("state", "true");
		}else {
			return messageJson("state", "false");
		}		
	}
	@POST
	@Path("/closeroulette")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject closeRouletteById(Roulette roulette) throws JSONException {
		JSONArray jsonArrayBetsObject = new JSONArray();
		JSONObject jsonObjectBet = new JSONObject();
		List<Bet> bets = new ArrayList<Bet>();
		bets = controller.getListOfBets(roulette);
		if(bets != null) {
			for(Bet bet : bets) {
				jsonObjectBet = new JSONObject();
				jsonObjectBet.accumulate("idRoulette", bet.getIdRoulette());
				jsonObjectBet.accumulate("idBet", bet.getIdBet());
				jsonObjectBet.accumulate("idPlayer", bet.getIdPlayer());
				jsonObjectBet.accumulate("idBetAmount", bet.getBetAmount());
				jsonObjectBet.accumulate("bet", bet.getBet());
				jsonObjectBet.accumulate("win", bet.isWin());
				jsonArrayBetsObject.put(jsonObjectBet);
			}
			jsonObjectBet = new JSONObject();
			jsonObjectBet.accumulate("bets", jsonArrayBetsObject);
			return jsonObjectBet;
		}else {
			return messageJson("error","no valid id roulette");
		}
	}
	@POST
	@Path("/allroulette")
	@Produces({MediaType.APPLICATION_JSON})
	public JSONObject getAllRoulette() throws JSONException {
		JSONArray jsonArrayRouletteObject = new JSONArray();
		JSONObject jsonObjectRoulette;
		List<Roulette> roulettes = controller.getAllRoulette();
		for(Roulette roulette : roulettes) {
			jsonObjectRoulette = new JSONObject();
			jsonObjectRoulette.accumulate("idRoulette", roulette.getIdRoulette());
			jsonObjectRoulette.accumulate("state", String.valueOf(roulette.isState()));
			jsonArrayRouletteObject.put(jsonObjectRoulette);
		}
		jsonObjectRoulette = new JSONObject();
		jsonObjectRoulette.accumulate("roulettes", jsonArrayRouletteObject);
		return jsonObjectRoulette;
	}
	private JSONObject messageJson(String key, String message) throws JSONException {
		JSONObject json = new JSONObject();
		json.accumulate(key, message);
		return json;
	}
	
}
