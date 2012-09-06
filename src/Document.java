
import java.awt.*;
import java.io.*;

public class Document {

	public Document(File file) {
		m_file = file;
		
		ReadFileContent();
	}
	
	public File getFile() { return m_file; }
	
	protected void ReadFileContent() {
		
		try {
			FileReader reader = new FileReader(m_file);
			
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
			m_linesCount = 0;

			while ( (textLine = br.readLine()) != null ) {
				
				line = new DocLine(textLine, line);
				++ m_linesCount;
				
				if ( m_FirstLine == null )
					m_FirstLine = line;
			}
		}
		catch (Exception ex) {
			
		}
		
		System.out.println("lines count : " + m_linesCount );
		DocLine line = m_FirstLine;
		
		while ( line != null ) {
			System.out.println("line : " + line.getText() );
			line = line.getNext();
		}
	}
	
	private File	m_file;
	private DocLine	m_FirstLine;
	private int		m_linesCount = 0;
}
