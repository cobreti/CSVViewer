import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.font.*;
import java.awt.geom.*;


public class DocViewPanel 	extends JPanel
							implements AdjustmentListener {

	public DocViewPanel( DocumentController controller ) {
		super();
		m_Controller = controller;	
	}
	
	public void OnDocumentContentChanged() {
		
		Document 		doc = m_Controller.getDocument();
		
		m_topLinePos = doc.GetLinesIterator();
		
		updateUI();
	}
	
	public void paintComponent(Graphics g) {

		if ( m_topLinePos == null )
			return;
		
		super.paintComponent(g);
		
		Dimension 					size = getSize();
		Graphics2D 					g2d = (Graphics2D)g;
		FontMetrics 				fm = g.getFontMetrics();
		Document 					doc = m_Controller.getDocument();
		Font 						font = m_linesFont;
		FontRenderContext 			frc = g2d.getFontRenderContext();
		float						y = 5;
		float						x = 0;
		float						emptyLineHeight = 0;
		int							visibleLinesCount = 0;
		Document.Iterator			linePos = doc.new Iterator(m_topLinePos);
		Rectangle2D					bounds;
		
		g.setFont(m_linesFont);
		
		bounds = font.getStringBounds("000000", frc);
		x = (float)bounds.getWidth() + 10.0f;
		emptyLineHeight = (float)bounds.getHeight();
		
		while ( linePos.isValid() && y < size.height-5 )
		{
			bounds = font.getStringBounds(linePos.getLineText(), frc);
			y += bounds.getHeight();
			g2d.drawString(linePos.getLineText(), x, y);
			
			g2d.drawString(Integer.toString(linePos.getLineNo()), 5.0f, y);
			
			y += 5;
			linePos.gotoNext();
			++ visibleLinesCount;
		}
		
		if ( visibleLinesCount == 0 )
			visibleLinesCount = 1;
		
		if ( visibleLinesCount != m_VisibleLinesCount )
		{
			m_VisibleLinesCount = visibleLinesCount;
			m_Controller.OnVisibleLinesCountChanged(m_VisibleLinesCount);
		}
		
	}

	public void adjustmentValueChanged(AdjustmentEvent e) {
		
		if ( m_topLinePos != null )
		{
			m_topLinePos.gotoLineNo(e.getValue());
			updateUI();
		}
	}

	protected DocumentController 	m_Controller;
	protected int 					m_VisibleLinesCount = 1;
	protected Document.Iterator		m_topLinePos;
	protected Font					m_linesFont = new Font("Courier", Font.PLAIN, 10);
	protected Font					m_lineNoFont = new Font("Courier", Font.ITALIC, 10);
}
