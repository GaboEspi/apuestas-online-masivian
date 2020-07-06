package com.apuestasonline.ws.rest.controller;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Base64.Encoder;

import org.codehaus.jettison.json.JSONException;
import com.apuestasonline.ws.rest.model.Bet;
import com.apuestasonline.ws.rest.model.Roulette;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

public class Controller {
	public Roulette newRoulette() {
		Jedis jedis = new Jedis("localhost");
		Roulette roulette = new Roulette(getBase64Random("roulette_"));
		Map<String, String> map = new HashMap<String, String>();
		map.put("idRoulette", roulette.getIdRoulette());
		map.put("state", String.valueOf(roulette.isState()));
		if(jedis.hmset(roulette.getIdRoulette(), map).equals("OK")) {
			jedis.close();
			return roulette;
		}else {
			jedis.close();
			return null;
		}
	}
	public boolean openRoulette(Roulette roulette) {		
		Jedis jedis = new Jedis("localhost");
		if(jedis.hexists(roulette.getIdRoulette(), "state")) {
			jedis.hset(roulette.getIdRoulette(), "state", "true");
			jedis.close();
			return true;
		}else {
			jedis.close();
			return false;
		}
	}
	public boolean newBet(Bet bet) {
		Jedis jedis = new Jedis("localhost");
		bet.setIdBet(getBase64Random("bet_"));
		Map<String, String> map = new HashMap<String, String>();
		map.put("idRoulette", bet.getIdRoulette());
		map.put("idBet", bet.getIdBet());
		map.put("idPlayer", bet.getIdPlayer());
		map.put("betAmount", String.valueOf(bet.getBetAmount()));
		map.put("bet", bet.getBet());
		if(jedis.hmset(bet.getIdBet(), map).equals("OK")) {
			jedis.close();
			return true;
		}else {
			jedis.close();
			return false;
		}
	}
	public List<Bet> getListOfBets(Roulette roulette){
		Jedis jedis = new Jedis("localhost");
		roulette.getIdRoulette();
		Bet bet;
		List<Bet> bets = new ArrayList<Bet>();
		if(jedis.hexists(roulette.getIdRoulette(), "state")) {
			jedis.hset(roulette.getIdRoulette(), "state", "false");
			
			ScanParams scanParams = new ScanParams().count(10).match("bet_*");
			String cur = "0";
			do {
			    ScanResult<String> scanResult = jedis.scan(cur, scanParams);
			    List<String> result = scanResult.getResult();
			    for(String element : result) {
			    	if(jedis.hget(element, "idRoulette").equals(roulette.getIdRoulette())) {
			    		bet = new Bet();
				    	bet.setIdRoulette(jedis.hget(element, "idRoulette"));
				    	bet.setIdBet(jedis.hget(element, "idBet"));
				    	bet.setIdPlayer(jedis.hget(element, "idPlayer"));
				    	bet.setBetAmount(Integer.parseInt(jedis.hget(element, "betAmount")));
				    	bet.setBet(jedis.hget(element, "bet"));
				    	bets.add(bet);
			    	}			    	
			    }
			    cur = scanResult.getStringCursor();
			} while (!cur.equals("0"));
			jedis.close();
			return bets;
		}else {
			jedis.close();
			return null;
		}
	}
	public List<Roulette> getAllRoulette() throws JSONException {
		Jedis jedis = new Jedis("localhost");
		Roulette roulette;
		List<Roulette> roulettes = new ArrayList<Roulette>();
		ScanResult<String> scanResult;
		List<String> result = new ArrayList<String>();
		ScanParams scanParams = new ScanParams().count(100).match("roulette_*");
		String cur = "0";
		do {
		    scanResult = jedis.scan(cur, scanParams);
		    result = scanResult.getResult();
		    for(String element : result) {
		    	roulette = new Roulette(element);
		    	roulette.setState(Boolean.valueOf(jedis.hget(element, "state")));
		    	roulettes.add(roulette);
		    }
		    cur = scanResult.getStringCursor();
		} while (!cur.equals("0"));
		jedis.close();
		return roulettes;
	}	
	private String getBase64Random(String prefix) {
		SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[48];
        random.nextBytes(bytes);
        Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        String token = encoder.encodeToString(bytes);
        token = prefix + token;
        
        return token;
	}
}
