package itb04.sa.teamb.pipesfilters.Adapter;

import java.io.StreamCorruptedException;

import itb04.sa.teamb.pipesfilters.Readable;
import itb04.sa.teamb.pipesfilters.data.Line;
import itb04.sa.teamb.pipesfilters.data.Word;

public class LineSourceAdapter implements Readable<Line> {

	private int m_Counter = 1;
	private int m_MaxLines= 20;
	
	public Line read() throws StreamCorruptedException {
		if (m_Counter <= m_MaxLines){
			Line l = getLine(m_Counter);
			m_Counter++;
			return l;
		}else{
			return null;
		}
	}
	
	private Line getLine(int counter){
		Line l = new Line();
		Word curWord = new Word("This is Line#" + counter);
		l.appendWord(curWord);
		return l;
	}
}
