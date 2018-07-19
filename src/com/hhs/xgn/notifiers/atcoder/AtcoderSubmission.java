package com.hhs.xgn.notifiers.atcoder;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AtcoderSubmission {
	public String SubmissionTime;
	public String Task;
	public String User;
	public String Language;
	public String Score;
	public String CodeSize;
	public String Status;
	public String ExecTime;
	public String Memory;
	public String RunID;
	public AtcoderSubmission(Element tr){
		Elements son=tr.children();
		SubmissionTime=son.get(0).text();
		Task=son.get(1).text();
		User=son.get(2).text();
		Language=son.get(3).text();
		Score=son.get(4).text();
		CodeSize=son.get(5).text();
		if(son.get(6).attr("colspan").equals("3")){
			Status=son.get(6).text();
			RunID=son.get(7).getElementsByTag("a").get(0).attr("href");
		}else{
			Status=son.get(6).text();
			ExecTime=son.get(7).text();
			Memory=son.get(8).text();
			RunID=son.get(9).getElementsByTag("a").get(0).attr("href");
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CodeSize == null) ? 0 : CodeSize.hashCode());
		result = prime * result + ((ExecTime == null) ? 0 : ExecTime.hashCode());
		result = prime * result + ((Language == null) ? 0 : Language.hashCode());
		result = prime * result + ((Memory == null) ? 0 : Memory.hashCode());
		result = prime * result + ((RunID == null) ? 0 : RunID.hashCode());
		result = prime * result + ((Score == null) ? 0 : Score.hashCode());
		result = prime * result + ((Status == null) ? 0 : Status.hashCode());
		result = prime * result + ((SubmissionTime == null) ? 0 : SubmissionTime.hashCode());
		result = prime * result + ((Task == null) ? 0 : Task.hashCode());
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
		AtcoderSubmission other = (AtcoderSubmission) obj;
		if (CodeSize == null) {
			if (other.CodeSize != null)
				return false;
		} else if (!CodeSize.equals(other.CodeSize))
			return false;
		if (ExecTime == null) {
			if (other.ExecTime != null)
				return false;
		} else if (!ExecTime.equals(other.ExecTime))
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
		if (RunID == null) {
			if (other.RunID != null)
				return false;
		} else if (!RunID.equals(other.RunID))
			return false;
		if (Score == null) {
			if (other.Score != null)
				return false;
		} else if (!Score.equals(other.Score))
			return false;
		if (Status == null) {
			if (other.Status != null)
				return false;
		} else if (!Status.equals(other.Status))
			return false;
		if (SubmissionTime == null) {
			if (other.SubmissionTime != null)
				return false;
		} else if (!SubmissionTime.equals(other.SubmissionTime))
			return false;
		if (Task == null) {
			if (other.Task != null)
				return false;
		} else if (!Task.equals(other.Task))
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
		return "AtcoderSubmission [SubmissionTime=" + SubmissionTime + ", Task=" + Task + ", User=" + User
				+ ", Language=" + Language + ", Score=" + Score + ", CodeSize=" + CodeSize + ", Status=" + Status
				+ ", ExecTime=" + ExecTime + ", Memory=" + Memory + ", RunID=" + RunID + "]";
	}
	
	
}
