package itb04.sa.teamb.pipesfilters.data;

import java.io.Serializable;

/**
 * represents a word, a word is a collection of single chars + optional punctuations (e.g.: ".", "!", ...)
 * @author riedie
 *
 */
public class Word implements Serializable {
	
    private static final long serialVersionUID = 1L;

    public Word(){
	}
	
	public Word(String word){
		getBuffer().append(word);
	}
	
	private StringBuffer m_Buf = null;
	
	private StringBuffer getBuffer(){
		if (m_Buf == null){
			m_Buf = new StringBuffer();
		}
		return m_Buf;
	}
	
	public int getLength(){
		if (m_Buf == null){
			return 0;
		}else{
			return m_Buf.length();
		}
	}
	
	public void appendChar(char c){
		getBuffer().append(c);
	}

	@Override
	public String toString() {
		if (m_Buf != null){
			return m_Buf.toString();
		}else{
			return "";
		}
	}
	
	
}
