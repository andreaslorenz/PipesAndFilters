package itb04.sa.teamb.pipesfilters.Adapter;

import java.io.StreamCorruptedException;

import itb04.sa.teamb.pipesfilters.Readable;
import itb04.sa.teamb.pipesfilters.data.Word;

public class WordSourceAdapter implements Readable<Word> {

	private String[] m_Words = {"This", "is", "a", "sample", "Text", "with", "several", "words."};
	private int offset = 0;
	
	public WordSourceAdapter() {
		super();
	}

	public Word read() throws StreamCorruptedException {
		if (offset < m_Words.length){
			Word curWord = new Word(m_Words[offset]);
			offset++;
			return curWord;
		}else{
			return null;
		}
	}
	
	public String getText(){
		return "This is a sample Text with several words.";
	}

}
