package com.gordo.propeditor;

import org.springframework.beans.factory.annotation.Autowired;

public class Owner {
	@Autowired
	Minion minion;

	public Minion getMinion() {
		return minion;
	}

	public void setMinion(Minion minion) {
		this.minion = minion;
	}
	
	
}
