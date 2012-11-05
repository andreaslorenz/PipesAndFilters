package itb04.sa.teamb.pipesfilters.data;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * represents a line.
 * a line is a collection of words
 * 
 * @author riedie
 *
 */
public class Line implements Serializable {
    
    private static final long serialVersionUID = 1L;
	private LinkedList<Word> m_Words = new LinkedList<Word>();
	private boolean m_changed = false;
	private int m_CharCount = 0;
	static final String WORD_DELIMITER = " ";
    public static final int DEL_LENGTH = WORD_DELIMITER.length();
	
	private void changed(){
		m_changed = true;
	}
	
	public void appendWord(Word w){
		m_Words.addLast(w);
        increaseCharCountForDelimiter();
        m_CharCount += w.getLength();
	}
    
    private void increaseCharCountForDelimiter() {
        if (m_CharCount != 0) m_CharCount+=DEL_LENGTH; // add 
    }
    
    public void addFirstWord(Word w) {
        increaseCharCountForDelimiter();
        m_Words.addFirst(w);
        
    }
	
	/*public Word removeLastWord(){
		m_CharCount = Math.max(0, m_CharCount--);
        return m_Words.removeLast();
        
	}*/
	
	public int getWordCount(){
		return m_Words.size();
	}
	
	public boolean isEmpty(){
		return m_Words.isEmpty();
	}
    
    public int getCharCount() {
        return m_CharCount;
    }

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer(m_CharCount);
		
		int curWord = 1;
		for(Word w: m_Words){
			buf.append(w.toString());
			// check if it is the last word
			// if it is the last word, don't append a DELIMITER
			if (curWord != getWordCount()){
				buf.append(WORD_DELIMITER);
			}
			curWord++;
		}
		return buf.toString();
	}
	
}
