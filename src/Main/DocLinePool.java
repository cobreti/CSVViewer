package Main;

public class DocLinePool {

	public static DocLinePool getInstance() { return s_instance; }
	
	protected DocLinePool() {
		
	}
	
	public DocLine Alloc(String text) {
		
		DocLine line = null;
		
		if ( m_head != null )
		{
			line = m_head;
			m_head = line.getNext();

			line.setText(text);
			line.setNext(null);
			line.setPrevious(null);
		}
		else
		{
			line = new DocLine(text);
		}
		
		return line;
	}
	
	public void Add( DocLine line )
	{
		line.setNext(m_head);
		line.setPrevious(null);
		m_head = line;
	}
		
	private DocLine		m_head = null;
	
	private static DocLinePool s_instance = new DocLinePool();
}
