package itb04.sa.teamb.pipesfilters.data;

import java.io.Serializable;
import java.util.LinkedList;

public class Page implements Serializable {

    private static final long serialVersionUID = 1L;
	public LinkedList<Line> m_Lines = new LinkedList<Line>();
	
	private final static String LINE_DELIMITER = "\n";
	
	public void addLine(Line l){
		m_Lines.addLast(l);
	}
	
	public int getLineCount(){
		return m_Lines.size();
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		int curLine = 1;
		for(Line l: m_Lines){
			buf.append(l.toString());
			// check if it is the last line
			// if it's not the last line, append no DELIMITER
			if (curLine != m_Lines.size()){
				buf.append(LINE_DELIMITER);
			}
			curLine++;
		}
		return buf.toString();
	}
	
	
}
