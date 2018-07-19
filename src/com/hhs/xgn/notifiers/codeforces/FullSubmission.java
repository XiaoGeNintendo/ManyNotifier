package com.hhs.xgn.notifiers.codeforces;

import com.google.gson.JsonObject;

/*
 * id	Integer.
contestId	Integer. Can be absent.
creationTimeSeconds	Integer. Time, when submission was created, in unix-format.
relativeTimeSeconds	Integer. Number of seconds, passed after the start of the contest (or a virtual start for virtual parties), before the submission.
problem	Problem object.
author	Party object.
programmingLanguage	String.
verdict	Enum: FAILED, OK, PARTIAL, COMPILATION_ERROR, RUNTIME_ERROR, WRONG_ANSWER, PRESENTATION_ERROR, TIME_LIMIT_EXCEEDED, MEMORY_LIMIT_EXCEEDED, IDLENESS_LIMIT_EXCEEDED, SECURITY_VIOLATED, CRASHED, INPUT_PREPARATION_CRASHED, CHALLENGED, SKIPPED, TESTING, REJECTED. Can be absent.
testset	Enum: SAMPLES, PRETESTS, TESTS, CHALLENGES, TESTS1, ..., TESTS10. Testset used for judging the submission.
passedTestCount	Integer. Number of passed tests.
timeConsumedMillis	Integer. Maximum time in milliseconds, consumed by solution for one test.
memoryConsumedBytes	Integer. Maximum memory in bytes, consumed by solution for one test.

 */
public class FullSubmission {
	public int id;
	public int contestId;
	public int creationTimeSeconds;
	public int relativeTimeSeconds;
	public FullProblem problem;
	public JsonObject author;
	public String language;
	/**FAILED, OK, PARTIAL, COMPILATION_ERROR, RUNTIME_ERROR, WRONG_ANSWER, PRESENTATION_ERROR, TIME_LIMIT_EXCEEDED, MEMORY_LIMIT_EXCEEDED, IDLENESS_LIMIT_EXCEEDED, SECURITY_VIOLATED, CRASHED, INPUT_PREPARATION_CRASHED, CHALLENGED, SKIPPED, TESTING, REJECTED. Can be absent.*/
	public String verdict;
	public String testset;
	public int passedTestCount;
	public int timeConsumedMillis;
	public int memoryConsumedBytes;

	public FullSubmission() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + contestId;
		result = prime * result + creationTimeSeconds;
		result = prime * result + id;
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + memoryConsumedBytes;
		result = prime * result + passedTestCount;
		result = prime * result + ((problem == null) ? 0 : problem.hashCode());
		result = prime * result + relativeTimeSeconds;
		result = prime * result + ((testset == null) ? 0 : testset.hashCode());
		result = prime * result + timeConsumedMillis;
		result = prime * result + ((verdict == null) ? 0 : verdict.hashCode());
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
		FullSubmission other = (FullSubmission) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (contestId != other.contestId)
			return false;
		if (creationTimeSeconds != other.creationTimeSeconds)
			return false;
		if (id != other.id)
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (memoryConsumedBytes != other.memoryConsumedBytes)
			return false;
		if (passedTestCount != other.passedTestCount)
			return false;
		if (problem == null) {
			if (other.problem != null)
				return false;
		} else if (!problem.equals(other.problem))
			return false;
		if (relativeTimeSeconds != other.relativeTimeSeconds)
			return false;
		if (testset == null) {
			if (other.testset != null)
				return false;
		} else if (!testset.equals(other.testset))
			return false;
		if (timeConsumedMillis != other.timeConsumedMillis)
			return false;
		if (verdict == null) {
			if (other.verdict != null)
				return false;
		} else if (!verdict.equals(other.verdict))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FullSubmission [id=" + id + ", contestId=" + contestId + ", creationTimeSeconds=" + creationTimeSeconds
				+ ", relativeTimeSeconds=" + relativeTimeSeconds + ", problem=" + problem + ", author=" + author
				+ ", language=" + language + ", verdict=" + verdict + ", testset=" + testset + ", passedTestCount="
				+ passedTestCount + ", timeConsumedMillis=" + timeConsumedMillis + ", memoryConsumedBytes="
				+ memoryConsumedBytes + "]";
	}

	public FullSubmission(JsonObject jo) {
		try {
			id = jo.get("id").getAsInt();
		} catch (Exception e) {
			id = -1;
		}
		try {
			contestId = jo.get("contestId").getAsInt();
		} catch (Exception e) {
			contestId = -1;
		}
		try {
			creationTimeSeconds = jo.get("creationTimeSeconds").getAsInt();
		} catch (Exception e) {
			creationTimeSeconds = -1;
		}
		try {
			relativeTimeSeconds = jo.get("relativeTimeSeconds").getAsInt();
		} catch (Exception e) {
			relativeTimeSeconds = -1;
		}
		try {
			problem = new FullProblem(jo.get("problem").getAsJsonObject());
		} catch (Exception e) {
			problem = new FullProblem();
		}
		try {
			author = jo.get("author").getAsJsonObject();
		} catch (Exception e) {
			author = null;
		}
		try {
			language = jo.get("programmingLanguage").getAsString();
		} catch (Exception e) {
			language = "Unknown";
		}
		try {
			verdict = jo.get("verdict").getAsString();
		} catch (Exception e) {
			verdict = "UNKNOWN";
		}
		try {
			testset = jo.get("testset").getAsString();
		} catch (Exception e) {
			testset = "";
		}
		try {
			passedTestCount = jo.get("passedTestCount").getAsInt();
		} catch (Exception e) {
			passedTestCount = 0;
		}
		try {
			timeConsumedMillis = jo.get("timeConsumedMillis").getAsInt();
		} catch (Exception e) {
			timeConsumedMillis = -1;
		}
		try {
			memoryConsumedBytes = jo.get("memoryConsumedBytes").getAsInt();
		} catch (Exception e) {
			memoryConsumedBytes = -1;
		}
	}
}
