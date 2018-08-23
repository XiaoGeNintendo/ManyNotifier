package com.hhs.notifiers.fzu;

import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

/**
 * 
 * FZU submission object.
 * 
 * @author XGN
 *
 */
public class FZUSubmission {
	public int RunID;
	public String User;
	public int PID;
	public String State;
	public String Mem;
	public String Time;
	public String Language;
	public String Len;
	public String SubmitTime;

	/*
	 * <tr align=center> <td>18827112</td> <td> <a
	 * href=userstatus?user_id=vjudge5>vjudge5</a> </td> <td> <a
	 * href=problem?id=3414>3414</a> </td> <td> <font color=blue>Accepted</font>
	 * </td> <td>696K</td> <td>0MS</td> <td>G++</td> <td>1507B</td> <td>2018-07-17
	 * 22:40:40</td> </tr>
	 */
	public FZUSubmission(Element tds) throws Exception {

		Elements son = tds.children();
		RunID = Integer.parseInt(son.get(0).text());
		SubmitTime = son.get(1).text();
		State = son.get(2).text();
		PID = Integer.parseInt(son.get(3).text());
		Language = son.get(4).text();
		Time = son.get(5).text();
		Mem = son.get(6).text();
		Len = son.get(7).text();
		User = son.get(8).text();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Language == null) ? 0 : Language.hashCode());
		result = prime * result + ((Len == null) ? 0 : Len.hashCode());
		result = prime * result + ((Mem == null) ? 0 : Mem.hashCode());
		result = prime * result + PID;
		result = prime * result + RunID;
		result = prime * result + ((State == null) ? 0 : State.hashCode());
		result = prime * result + ((SubmitTime == null) ? 0 : SubmitTime.hashCode());
		result = prime * result + ((Time == null) ? 0 : Time.hashCode());
		result = prime * result + ((User == null) ? 0 : User.hashCode());
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
		FZUSubmission other = (FZUSubmission) obj;
		if (Language == null) {
			if (other.Language != null)
				return false;
		} else if (!Language.equals(other.Language))
			return false;
		if (Len == null) {
			if (other.Len != null)
				return false;
		} else if (!Len.equals(other.Len))
			return false;
		if (Mem == null) {
			if (other.Mem != null)
				return false;
		} else if (!Mem.equals(other.Mem))
			return false;
		if (PID != other.PID)
			return false;
		if (RunID != other.RunID)
			return false;
		if (State == null) {
			if (other.State != null)
				return false;
		} else if (!State.equals(other.State))
			return false;
		if (SubmitTime == null) {
			if (other.SubmitTime != null)
				return false;
		} else if (!SubmitTime.equals(other.SubmitTime))
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
		return true;
	}

	@Override
	public String toString() {
		return "FZUSubmission [RunID=" + RunID + ", User=" + User + ", PID=" + PID + ", State=" + State + ", Mem=" + Mem
				+ ", Time=" + Time + ", Language=" + Language + ", Len=" + Len + ", SubmitTime=" + SubmitTime + "]";
	}

}
