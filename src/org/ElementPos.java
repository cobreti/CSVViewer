package org;

public class ElementPos {

	public ElementPos(int lineNo, int start, int length) {
		m_lineNo = lineNo;
		m_start = start;
		m_length = length;
	}
	
	public int getStart()						{ return m_start; }
	public void setStart(int pos)				{ m_start = pos; }
	
	public int getLength()						{ return m_length; }
	public void setLength(int len)				{ m_length = len; }
	
	public int getLineNo()						{ return m_lineNo; }
	public void setLineNo(int no)				{ m_lineNo = no; }
	
	private int						m_lineNo;
	private int						m_start;
	private int						m_length;
}
