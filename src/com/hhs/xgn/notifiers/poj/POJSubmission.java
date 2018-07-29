package com.hhs.xgn.notifiers.poj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;


public class POJSubmission {
	public int RunID;
	public String User;
	public int Problem;
	public String Result;
	public String Memory;
	public String Time;
	public String Language;
	public String CodeLength;
	public String SubmitTime;

	/*
	 * <tr align=center> <td>18827112</td> <td> <a
	 * href=userstatus?user_id=vjudge5>vjudge5</a> </td> <td> <a
	 * href=problem?id=3414>3414</a> </td> <td> <font color=blue>Accepted</font>
	 * </td> <td>696K</td> <td>0MS</td> <td>G++</td> <td>1507B</td>
	 * <td>2018-07-17 22:40:40</td> </tr>
	 */
	public POJSubmission(String tr) throws Exception {
		tr="<table>"+tr+"</table>";
		Document d=Jsoup.parse(tr);
		Element tds=d.body().getElementsByTag("table").get(0).getElementsByTag("tbody").get(0).getElementsByTag("tr").get(0);
		Elements son=tds.children();
		RunID=Integer.parseInt(son.get(0).text());
		User=son.get(1).text();
		Problem=Integer.parseInt(son.get(2).text());
		Result=son.get(3).text();
		Memory=son.get(4).text();
		Time=son.get(5).text();
		Language=son.get(6).text();
		CodeLength=son.get(7).text();
		SubmitTime=son.get(8).text();
	}

	@Override
	public String toString() {
		return "POJSubmission [RunID=" + RunID + ", User=" + User + ", Problem=" + Problem + ", Result=" + Result
				+ ", Memory=" + Memory + ", Time=" + Time + ", Language=" + Language + ", CodeLength=" + CodeLength
				+ ", SubmitTime=" + SubmitTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CodeLength == null) ? 0 : CodeLength.hashCode());
		result = prime * result + ((Language == null) ? 0 : Language.hashCode());
		result = prime * result + ((Memory == null) ? 0 : Memory.hashCode());
		result = prime * result + Problem;
		result = prime * result + ((Result == null) ? 0 : Result.hashCode());
		result = prime * result + RunID;
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
		POJSubmission other = (POJSubmission) obj;
		if (CodeLength == null) {
			if (other.CodeLength != null)
				return false;
		} else if (!CodeLength.equals(other.CodeLength))
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
		if (Problem != other.Problem)
			return false;
		if (Result == null) {
			if (other.Result != null)
				return false;
		} else if (!Result.equals(other.Result))
			return false;
		if (RunID != other.RunID)
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
}
