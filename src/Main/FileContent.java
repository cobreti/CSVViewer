package Main;

import java.io.*;
import java.util.zip.GZIPInputStream;


public class FileContent {

	public FileContent(File file) {
		m_file = file;
		
		Analyse();
		CreatedNeededWindows();
	}
	
	public int getLinesCount()						{ return m_linesCount; }
	public int getWindowsCount()					{ return m_windows.length; }
	public ContentWindow getWindow(int index)		{ return m_windows[index]; }
	
	public DocLine getContent( int windowIndex )
	{
		if ( windowIndex >= m_windows.length )
			return null;
		
		DocLine			firstLine 	= null;
		DocLine 		line 		= null;
		DocLine			prevLine 	= null;
		int				lineIndex	= 0;
		String 			textLine;
		ContentWindow	window = m_windows[windowIndex];

		try {
			
			InputStreamReader reader = CreateStreamReader();
			BufferedReader br = new BufferedReader(reader);
			
			m_linesCount = 0;
			
			while ( lineIndex < window.getEndLine() && (textLine = br.readLine()) != null ) {
				
				if ( lineIndex >= window.getStartLine() )
				{
					line = DocLinePool.getInstance().Alloc(textLine);
					if ( prevLine != null )
						prevLine.setNext(line);
					line.setPrevious(prevLine);
					prevLine = line;
					
					if ( firstLine == null )
						firstLine = line;
				}
				
				++ lineIndex;
			}
			
			reader.close();
		}
		catch ( Exception ex) {
			
		}
		
		return firstLine;
	}

	protected InputStreamReader CreateStreamReader()
	{
		InputStreamReader reader = null;

		try {
			
			if ( m_file.getName().endsWith(".gz") )
				reader = new InputStreamReader(new GZIPInputStream(new FileInputStream(m_file)));
			else
				reader = new FileReader(m_file);
		}
		catch (Exception ex) {
		}		
		
		return reader;
	}
	
	protected void Analyse() {
		
		try {
		
			InputStreamReader reader = CreateStreamReader();
			BufferedReader br = new BufferedReader(reader);
			
			m_linesCount = 0;
			
			while ( br.readLine() != null )
				++ m_linesCount;
			
			reader.close();
		}
		catch ( Exception ex) {
			
		}
	}
	
	protected void CreatedNeededWindows()
	{
		int		kWindowSize 	= 100000;
		int		windowsCount 	= m_linesCount / kWindowSize;
		int		lineStart		= 0;
		int		lineEnd			= 0;
		int		windowIndex		= 0;
		
		if ( m_linesCount % kWindowSize > 0 )
			++ windowsCount;
		
		System.out.println();
		System.out.println("number of lines : " + m_linesCount );
		System.out.println("number of windows required : " + windowsCount );
		
		m_windows = new ContentWindow[windowsCount];
		
		while ( lineStart < m_linesCount )
		{
			lineEnd = lineStart + kWindowSize;
			if ( lineEnd > m_linesCount )
				lineEnd = m_linesCount;
			
			m_windows[windowIndex] = new ContentWindow(windowIndex, lineStart, lineEnd);
			
			System.out.println("Window : [ " + lineStart + ", " + lineEnd + " ] " );
			
			lineStart = lineEnd;
			++ windowIndex;
		}
	}
	
	private File				m_file;
	private int					m_linesCount = 0;
	private ContentWindow[]		m_windows = null;
}
