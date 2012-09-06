
public class DocLine {

	public DocLine(String text, DocLine previous) {
		
		m_Text = text;
		m_Previous = previous;
		if ( m_Previous != null )
			m_Previous.m_Next = this;
	}
	
	public String getText()  	{ return m_Text; }
	
	public DocLine getPrevious()	{ return m_Previous; }
	public DocLine getNext()		{ return m_Next; }
	
	private String m_Text;
	private DocLine m_Previous = null;
	private DocLine m_Next = null;
}
