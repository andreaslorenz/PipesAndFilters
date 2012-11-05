package itb04.sa.teamb.pipesfilters.Adapter;

import java.io.StreamCorruptedException;

import itb04.sa.teamb.pipesfilters.Readable;

public class CharSourceAdapter implements Readable<Character> {

	private String m_Text = "children, children, children, children, children, children, children, children, ";  
	private int offset = 0;
	
	public Character read() throws StreamCorruptedException {
		if (offset < m_Text.length()){
			
			Character c = m_Text.charAt(offset);
			offset++;
			return c;
			
		}
		return null;
	}
	
	public String getText(){
		return m_Text;
	}
}
