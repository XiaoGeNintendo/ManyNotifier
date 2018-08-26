package com.hhs.notifiers.luogu;

import org.jsoup.nodes.*;

/**
 * Luogu submission object.<br>
 * <i>Why is luogu's format so strange??!!</i>
 * 
 * @author Zzzyt
 *
 */
public class LuoguSubmission {
	public String verdict;
	public String probId;
	public String author;
	public String score;
	public int runID;

	@Override
	public String toString() {
		return "LuoguSubmission [verdict=" + verdict + ", probId=" + probId + ", author=" + author + ", score=" + score
				+ ", runID=" + runID + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((probId == null) ? 0 : probId.hashCode());
		result = prime * result + runID;
		result = prime * result + ((score == null) ? 0 : score.hashCode());
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
		LuoguSubmission other = (LuoguSubmission) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (probId == null) {
			if (other.probId != null)
				return false;
		} else if (!probId.equals(other.probId))
			return false;
		if (runID != other.runID)
			return false;
		if (score == null) {
			if (other.score != null)
				return false;
		} else if (!score.equals(other.score))
			return false;
		if (verdict == null) {
			if (other.verdict != null)
				return false;
		} else if (!verdict.equals(other.verdict))
			return false;
		return true;
	}

	public LuoguSubmission() {

	}

	public LuoguSubmission(Element div) {
		Element div1 = div.children().get(0);

		Element lg_inline_up = div1.children().get(div1.children().size() - 1);

		Element data_pjax = lg_inline_up.children().get(0);
		for (int i = 0; i < lg_inline_up.children().size(); i++) {
			data_pjax = lg_inline_up.children().get(i);

			String tmp = data_pjax.text();

			if (!tmp.equals("")) {
				break;
			}
		}

		Element div3 = div.children().get(2);

		probId = div3.children().get(0).text();

		author = div3.children().get(div3.children().size() - 1).wholeText();
		author = author.split(" ")[1];

		verdict = data_pjax.text();

		String tmp[] = data_pjax.attr("href").split("=");
		runID = Integer.parseInt(tmp[tmp.length - 1]);

		tmp = verdict.split(" ");
		verdict = tmp[0];
		if (tmp.length == 2) {
			score = tmp[1];
		} else if (tmp.length == 3) {
			score = tmp[2];
			verdict += " ";
			verdict += tmp[1];
		} else {
			score = "";
		}
	}
}
