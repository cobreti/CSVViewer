package org;

import java.awt.*;
import java.io.*;
import java.util.zip.*;

public class Document {

	public class Iterator {

		public Iterator( Iterator src ) {
			m_owner = src.m_owner;
			m_line = src.m_line;
			m_lineNo = src.m_lineNo;
		}
		
		public boolean isValid() { return m_line != null; }
				
		public String getLineText() {
			
			if ( m_line != null )
				return m_line.getText();
			
			return null;
		}
		
		public int getLineNo() { return m_lineNo; }
		
		public boolean gotoLineNo(int lineNo)
		{
			if ( m_line == null )
				return false;
			
			while ( m_lineNo > lineNo )
				gotoPrevious();
			
			while ( m_lineNo < lineNo )
				gotoNext();
			
			return ( m_line != null );
		}
		
		public boolean gotoNext() {

			if ( m_line == null )
				return false;
			
			m_line = m_line.getNext();
			m_lineNo ++;
			
			return (m_line != null);
		}
		
		public boolean gotoPrevious() {
			if ( m_line == null )
				return false;
			
			m_line = m_line.getPrevious();
			m_lineNo --;
			
			return (m_line != null);
		}
				
		protected Iterator(Document owner) {
			m_owner = owner;
			m_line = m_owner.m_FirstLine;
			m_lineNo = 0;
		}

		private Document 		m_owner;
		private DocLine 		m_line;
		private int				m_lineNo;
	}
	
	public Document(File file) {
		m_file = file;
		
		if ( m_file != null )
		{
			m_fileContent = new FileContent(file);
//			ReadFileContent();
		}
	}
	
	public File getFile() { return m_file; }
		
	public Iterator GetLinesIterator() { return new Iterator(this); }
	
	public int getLinesCount() {
		
		if ( m_currentWindow == null )
			return 0;
		
		return m_currentWindow.getEndLine() - m_currentWindow.getStartLine(); 
	}
	public int getWindowsCount()		{ return m_fileContent.getWindowsCount(); }
	public ContentWindow getWindow()	{ return m_currentWindow; }
	
	public ElementPos getFoundElementPos()	{ return m_foundElement; }
	
	public void close()
	{
		ReleaseLines();
	}
	
	public void ReadContent(int windowIndex)
	{
		ReleaseLines();
		
		m_currentWindow = m_fileContent.getWindow(windowIndex);
		m_FirstLine = m_fileContent.getContent(windowIndex);
	}
	
	public boolean SearchNext(String text) {
		m_foundElement = FindNext(m_searchStartPos, text);
		
		if ( m_foundElement != null )
		{
			m_searchStartPos.setLineNo(m_foundElement.getLineNo());
			m_searchStartPos.setStart(m_foundElement.getStart()+1);
		}
		
		return m_foundElement != null;
	}
	
	public boolean SearchPrevious(String text) {
		m_foundElement = FindPrevious(m_searchStartPos, text);
		
		if ( m_foundElement != null )
		{
			m_searchStartPos.setLineNo(m_foundElement.getLineNo());
			m_searchStartPos.setStart(m_foundElement.getStart()+1);
		}
		
		return m_foundElement != null;
	}

	public void OnLineSelected(int lineNo, String text) {
		m_foundElement = null;
		m_searchStartPos.setLineNo(lineNo);
		m_searchStartPos.setStart(0);
	}
	
	protected ElementPos FindNext( ElementPos startPos, String text ) {
		
		Iterator	pos = new Iterator(this);
		ElementPos	foundPos = null;
		int			startIndex = startPos.getStart();
		
		pos.gotoLineNo(startPos.getLineNo());
		
		while ( foundPos == null && pos.isValid() )
		{
			String line_text = pos.getLineText().substring(startIndex);
			int index = line_text.indexOf(text);
			if ( index >= 0 )
				foundPos = new ElementPos(pos.getLineNo(), index + startIndex, text.length());
			else
			{
				pos.gotoNext();
				startIndex = 0;
			}
		}
		
		return foundPos;
	}
	
	protected ElementPos FindPrevious( ElementPos startPos, String text ) {
		
		Iterator	pos = new Iterator(this);
		ElementPos	foundPos = null;
		int			startIndex = startPos.getStart();
		
		pos.gotoLineNo(startPos.getLineNo());
		
		while ( foundPos == null && pos.isValid() )
		{
			String line_text = pos.getLineText().substring(0, startIndex);
			int index = line_text.indexOf(text);
			if ( index >= 0 )
				foundPos = new ElementPos(pos.getLineNo(), index, text.length());
			else
			{
				pos.gotoPrevious();
				
				if ( pos.isValid() )
					startIndex = pos.getLineText().length();
			}
		}
		
		return foundPos;
	}

	protected void ReleaseLines()
	{
		DocLine	line = m_FirstLine;
		m_FirstLine = null;
		
		while ( line != null )
		{
			line = line.Disconnect();
		}
	}
	
	
	private File			m_file = null;
	private DocLine			m_FirstLine;
	private FileContent		m_fileContent;
	private ContentWindow	m_currentWindow;
	private ElementPos		m_foundElement = null;
	private ElementPos		m_searchStartPos = new ElementPos(0, 0, 0);
}
