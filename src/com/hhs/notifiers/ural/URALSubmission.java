package com.hhs.notifiers.ural;

import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

/**
 * URAL submission object.<br>
 * 
 * @author XGN
 *
 */
public class URALSubmission {
	public int RunID;
	public String User;
	public String PID;
	public String Result;
	public String Memory;
	public String Time;
	public String Language;
	public String Code_Length = "null";
	public String Submit_Time;
	public boolean good;
	public String TestNo;

	/*
	 * <tr align=center> <td>18827112</td> <td> <a
	 * href=userstatus?user_id=vjudge5>vjudge5</a> </td> <td> <a
	 * href=problem?id=3414>3414</a> </td> <td> <font color=blue>Accepted</font>
	 * </td> <td>696K</td> <td>0MS</td> <td>G++</td> <td>1507B</td> <td>2018-07-17
	 * 22:40:40</td> </tr>
	 */
	public URALSubmission(Element tds) throws Exception {

		Elements son = tds.children();
		if (son.size() != 9) {
			good = false;
			return;
		}
		good = true;
		RunID = Integer.parseInt(son.get(0).text());
		Submit_Time = son.get(1).text();
		User = son.get(2).text();
		PID = son.get(3).text();
		Language = son.get(4).text();
		Result = son.get(5).text();
		TestNo = son.get(6).text();
		Time = son.get(7).text();
		Memory = son.get(8).text();

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Code_Length == null) ? 0 : Code_Length.hashCode());
		result = prime * result + ((Language == null) ? 0 : Language.hashCode());
		result = prime * result + ((Memory == null) ? 0 : Memory.hashCode());
		result = prime * result + ((PID == null) ? 0 : PID.hashCode());
		result = prime * result + ((Result == null) ? 0 : Result.hashCode());
		result = prime * result + RunID;
		result = prime * result + ((Submit_Time == null) ? 0 : Submit_Time.hashCode());
		result = prime * result + ((TestNo == null) ? 0 : TestNo.hashCode());
		result = prime * result + ((Time == null) ? 0 : Time.hashCode());
		result = prime * result + ((User == null) ? 0 : User.hashCode());
		result = prime * result + (good ? 1231 : 1237);
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
		URALSubmission other = (URALSubmission) obj;
		if (Code_Length == null) {
			if (other.Code_Length != null)
				return false;
		} else if (!Code_Length.equals(other.Code_Length))
			return false;
		if (Language == null) {
			if (other.Language != null)
				return false;
		} else if (!Language.equals(other.Language))
			return false;
		if (Memory == null) {
			if (other.Memory != null)
				return false;
		} else if (!Memory.equals(other.Memory))
			return false;
		if (PID == null) {
			if (other.PID != null)
				return false;
		} else if (!PID.equals(other.PID))
			return false;
		if (Result == null) {
			if (other.Result != null)
				return false;
		} else if (!Result.equals(other.Result))
			return false;
		if (RunID != other.RunID)
			return false;
		if (Submit_Time == null) {
			if (other.Submit_Time != null)
				return false;
		} else if (!Submit_Time.equals(other.Submit_Time))
			return false;
		if (TestNo == null) {
			if (other.TestNo != null)
				return false;
		} else if (!TestNo.equals(other.TestNo))
			return false;
		if (Time == null) {
			if (other.Time != null)
				return false;
		} else if (!Time.equals(other.Time))
			return false;
		if (User == null) {
			if (other.User != null)
				return false;
		} else if (!User.equals(other.User))
			return false;
		if (good != other.good)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "URALSubmission [RunID=" + RunID + ", User=" + User + ", PID=" + PID + ", Result=" + Result + ", Memory="
				+ Memory + ", Time=" + Time + ", Language=" + Language + ", Code_Length=" + Code_Length
				+ ", Submit_Time=" + Submit_Time + ", good=" + good + ", TestNo=" + TestNo + "]";
	}

}
