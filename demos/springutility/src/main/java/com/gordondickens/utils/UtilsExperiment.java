package com.gordondickens.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilsExperiment {
	private static final Logger logger = LoggerFactory
			.getLogger(UtilsExperiment.class);
	private List<String> names;
	private List<String> otherNames;

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		logger.debug("Received names: {} of type {}", new Object[] { names,
				names.getClass().getName() });
		this.names = names;
	}

	public List<String> getOtherNames() {
		return otherNames;
	}

	public void setOtherNames(List<String> otherNames) {
		this.otherNames = otherNames;
	}

	@Override
	public String toString() {
		return "UtilsExperiment [names ("
				+ (names != null ? names.getClass().getName() : "NULLinator")
				+ ")="
				+ names
				+ ", otherNames("
				+ (otherNames != null ? otherNames.getClass().getName()
						: "NULLerino") + ")=" + otherNames + "]";
	}
}
