package Main;


public final class DocLine {

	public DocLine(String text) {
		
		m_Text = text;
	}
	
	public String getText()  	{ return m_Text; }
	public void setText(String text) { m_Text = text; }
	
	public DocLine getPrevious()	{ return m_Previous; }
	public void setPrevious(DocLine line) { m_Previous = line; }
	
	public DocLine getNext()		{ return m_Next; }
	public void setNext(DocLine line)	{ m_Next = line; }
	
	
	public DocLine Disconnect()
	{
		DocLine n = m_Next;
		
		m_Previous = null;
		m_Next = null;
		m_Text = null;
		
		DocLinePool.getInstance().Add(this);
		
		return n;
	}
	
	private String m_Text;
	private DocLine m_Previous = null;
	private DocLine m_Next = null;
}
