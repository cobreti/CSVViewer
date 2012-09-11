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
			ReadFileContent();
		}
	}
	
	public File getFile() { return m_file; }
		
	public Iterator GetLinesIterator() { return new Iterator(this); }
	
	public int getLinesCount() { return m_linesCount; }
	
	public void close()
	{
		DocLine	line = m_FirstLine;
		m_FirstLine = null;
		
		while ( line != null )
		{
			line = line.Disconnect();
		}
	}
	
	protected void ReadFileContent() {
		
		try {
			InputStreamReader reader;
			
			if ( m_file.getName().endsWith(".gz") )
				reader = new InputStreamReader(new GZIPInputStream(new FileInputStream(m_file)));
			else
				reader = new FileReader(m_file);
			
			ReadContent(reader);
			
			reader.close();
		}
		catch (Exception ex) {
		}
	}
	
	protected void ReadContent( InputStreamReader in ) {
		
		BufferedReader br = new BufferedReader(in);
		String textLine;
		
		try {
			
			m_FirstLine = null;
			DocLine line = null;
			DocLine	prevLine = null;
			m_linesCount = 0;

			long startMilli = System.currentTimeMillis();
			
			while ( (textLine = br.readLine()) != null ) {
				
				line = DocLinePool.getInstance().Alloc(textLine);
				if ( prevLine != null )
					prevLine.setNext(line);
				line.setPrevious(prevLine);
				prevLine = line;
				
				++ m_linesCount;
				
				if ( m_FirstLine == null )
					m_FirstLine = line;
			}

			long endMilli = System.currentTimeMillis();

			System.out.println("lines count : " + m_linesCount );
			System.out.println("parsing time : " + (endMilli - startMilli) );
		
		}
		catch (Exception ex) {
			
		}
		
//		DocLine line = m_FirstLine;
		
//		while ( line != null ) {
//			System.out.println("line : " + line.getText() );
//			line = line.getNext();
//		}
	}
	
	private File	m_file = null;
	private DocLine	m_FirstLine;
	private int		m_linesCount = 0;
}
