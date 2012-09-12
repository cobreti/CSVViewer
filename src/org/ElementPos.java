package org;

public class ElementPos {

	public ElementPos(Document.Iterator pos, int start, int length) {
		m_linePos = pos;
		m_start = start;
		m_length = length;
	}
	
	public Document.Iterator getLinePos()		{ return m_linePos; }
	public int getStart()						{ return m_start; }
	public int getLength()						{ return m_length; }
	
	private Document.Iterator		m_linePos;
	private int						m_start;
	private int						m_length;
}
