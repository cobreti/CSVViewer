package Main;

public class ContentWindow {

	public ContentWindow(int index, int startLine, int endLine) {
		m_index = index;
		m_startLine = startLine;
		m_endLine = endLine;
	}
	
	public int getStartLine() 	{ return m_startLine; }
	public int getEndLine()		{ return m_endLine; }
	public int getIndex()		{ return m_index; }
	
	private	int		m_startLine;
	private int		m_endLine;
	private int		m_index;
}
