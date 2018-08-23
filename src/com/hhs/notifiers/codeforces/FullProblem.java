package com.hhs.notifiers.codeforces;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/*Problem
Represents a problem.
Field			Description
contestId		Integer. Can be absent. Id of the contest, containing the problem.
problemsetName	String. Can be absent. Short name of the problemset the problem belongs to.
index			String. Usually a letter of a letter, followed by a digit, that represent a problem index in a contest.
name			String. Localized.
type			Enum: PROGRAMMING, QUESTION.
points			Floating point number. Can be absent. Maximum ammount of points for the problem.
tags			String list. Problem tags.
*/
/**
 * Codeforces problem object.<br>
 * Fields are from Codeforces API json format.<br>
 * <b>Huge thanks to MikeMirzayanov for the fantastic API! :D</b>
 * 
 * @author XGN
 *
 */
public class FullProblem {
	public int contestId;
	public String problemsetName;
	public String index;
	@Override
	public String toString() {
		return "FullProblem [contestId=" + contestId + ", problemsetName=" + problemsetName + ", index=" + index
				+ ", name=" + name + ", type=" + type + ", points=" + points + ", tags=" + tags + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + contestId;
		result = prime * result + ((index == null) ? 0 : index.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + points;
		result = prime * result + ((problemsetName == null) ? 0 : problemsetName.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FullProblem other = (FullProblem) obj;
		if (contestId != other.contestId)
			return false;
		if (index == null) {
			if (other.index != null)
				return false;
		} else if (!index.equals(other.index))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (points != other.points)
			return false;
		if (problemsetName == null) {
			if (other.problemsetName != null)
				return false;
		} else if (!problemsetName.equals(other.problemsetName))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public String name;
	public String type;
	public int points;
	public JsonArray tags;

	public FullProblem() {

	}

	public FullProblem(JsonObject jo) {
		try {
			contestId = jo.get("contestId").getAsInt();
		} catch (Exception e) {
			contestId = -1;
		}

		try {
			problemsetName = jo.get("problemsetName").getAsString();
		} catch (Exception e) {
			problemsetName = null;
		}
		try {
			index = jo.get("index").getAsString();
		} catch (Exception e) {
			index = null;
		}
		try {
			name = jo.get("name").getAsString();
		} catch (Exception e) {
			name = null;
		}
		try {
			type = jo.get("type").getAsString();
		} catch (Exception e) {
			type = null;
		}
		try {
			points = jo.get("points").getAsInt();
		} catch (Exception e) {
			points = -1;
		}
		try {
			tags = jo.get("tags").getAsJsonArray();
		} catch (Exception e) {
			tags = new JsonArray();
		}
	}
}
