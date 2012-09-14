package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;


public class DocViewPanel 	extends JPanel
							implements AdjustmentListener, MouseListener, ComponentListener, KeyListener {
	
	public class VertScrollbarListener implements AdjustmentListener {
		
		public VertScrollbarListener(DocViewPanel view) {
			m_view = view;
		}
		
		public void adjustmentValueChanged(AdjustmentEvent e) {
			
			if ( m_view.m_topLinePos != null )
			{
				m_view.m_topLinePos.gotoLineNo(e.getValue());
				m_view.updateUI();
			}
		}

		private DocViewPanel m_view;
	}
	
	public class HorzScrollbarListener implements AdjustmentListener {
		
		public HorzScrollbarListener(DocViewPanel view) {
			m_view = view;
		}
		
		public void adjustmentValueChanged(AdjustmentEvent e) {

			m_view.m_horzOffset = -e.getValue();
			m_view.updateUI();
		}

		private DocViewPanel m_view;
	}

	protected class LineDisplayIterator extends Document.Iterator
	{
		public LineDisplayIterator(	Graphics g, 
									Document doc, 
									Document.Iterator pos,
									Rectangle viewRect,
									Point startPos,
									int horzOffset ) {
			doc.super(pos);
			
			m_graphics = g;
			m_graphics2D = (Graphics2D)g;
			m_frc = m_graphics2D.getFontRenderContext();
			m_viewRect = viewRect;
			m_pos = startPos;
			m_fontMetrics = m_graphics2D.getFontMetrics();
			m_horzOffset = horzOffset;
			
			Rectangle2D bounds = m_graphics2D.getFont().getStringBounds("00000000", m_frc);
			m_lineNumberMarginSize = bounds.getWidth() + 10;
		}
		
		public boolean isValid() {
			if ( !super.isValid() )
				return false;
			
			return (m_pos.y < m_viewRect.getMaxY());
		}
		
		public boolean gotoNext() {
			
			int				maxAscent = m_fontMetrics.getMaxAscent();
			int				maxDescent = m_fontMetrics.getMaxDescent();
			m_pos.y += maxAscent + maxDescent + 5;
			
			return super.gotoNext();
		}
		
		public boolean isPointInLine( Point pt ) {
			
			if ( !isValid() )
				return false;
			
			int				maxAscent = m_fontMetrics.getMaxAscent();
			int				maxDescent = m_fontMetrics.getMaxDescent();
			
			

			Rectangle2D		bounds = m_graphics.getFont().getStringBounds(getLineText(), m_frc);
			Rectangle		shape = new Rectangle(m_pos.x + (int)m_lineNumberMarginSize, (int)m_pos.y, 
													(int)bounds.getWidth(), maxAscent + maxDescent );
			
			return shape.contains(pt);
		}
		
		public double getMaxWidth() { return m_MaxLineWidth; }
		
		public void DisplayLine()
		{
			int				maxAscent = m_fontMetrics.getMaxAscent();
			int				maxDescent = m_fontMetrics.getMaxDescent();
			
			if ( getLineText() == null )
			{
				return;
			}
			
			Rectangle2D		bounds = m_graphics.getFont().getStringBounds(getLineText(), m_frc);
			
			if ( bounds.getWidth() > m_MaxLineWidth )
				m_MaxLineWidth = bounds.getWidth();

			Rectangle clipRect = m_graphics2D.getClipBounds();
			m_graphics2D.clipRect(	(int)(m_pos.x + m_lineNumberMarginSize), m_pos.y, 
									(int)bounds.getWidth(), maxAscent + maxDescent);
			m_graphics2D.drawString(	getLineText(), 
										m_pos.x + (int)m_lineNumberMarginSize + m_horzOffset, 
										m_pos.y + maxAscent );
			m_graphics2D.setClip(clipRect);
		}
		
		public void Highlight( ElementPos pos ) {
			
			int				maxAscent = m_fontMetrics.getMaxAscent();
			int				maxDescent = m_fontMetrics.getMaxDescent();
			String			text = getLineText();
			String			prefix = text.substring(0, pos.getStart());
			String			element = text.substring(pos.getStart(), pos.getStart() + pos.getLength());
			int				left = 0;
			
			if ( prefix != null )
			{
				Rectangle2D		bounds = m_graphics.getFont().getStringBounds(prefix, m_frc);
				left = (int)bounds.getWidth();
			}
			
			Rectangle2D bounds = m_graphics.getFont().getStringBounds(element, m_frc);

			Rectangle clipRect = m_graphics2D.getClipBounds();
			m_graphics2D.clipRect(	(int)(m_pos.x + m_lineNumberMarginSize), m_pos.y, 
									clipRect.width, maxAscent + maxDescent);

			Rectangle	rcElement = new Rectangle();
			rcElement.setBounds(	m_pos.x + (int)m_lineNumberMarginSize + m_horzOffset + left,
									m_pos.y,
									(int)bounds.getWidth(),
									maxAscent + maxDescent );
			Color backupColor = m_graphics2D.getColor();
			m_graphics2D.setColor(Color.YELLOW);
			m_graphics2D.fill(rcElement);
			m_graphics2D.setColor(backupColor);
			
			m_graphics2D.setClip(clipRect);
		}
		
		public void DisplayLineNo(int offset)
		{
			int				maxAscent = m_fontMetrics.getMaxAscent();

			m_graphics2D.drawString(Integer.toString(getLineNo()+offset+1), m_pos.x + 5, m_pos.y + maxAscent );			
		}
		
		private Graphics					m_graphics;
		private Graphics2D					m_graphics2D;
		private FontRenderContext			m_frc;
		private Rectangle					m_viewRect;
		private Point						m_pos;
		private FontMetrics					m_fontMetrics;
		private double						m_lineNumberMarginSize;
		private double						m_MaxLineWidth = 0;
		private int							m_horzOffset;
	}
	
	
	public DocViewPanel( DocumentController controller ) {
		super();
		m_Controller = controller;
		
		addMouseListener(this);
		addComponentListener(this);
		addKeyListener(this);
	}
	
	public void OnDocumentContentChanged() {
		
		Document 		doc = m_Controller.getDocument();
		
		m_topLinePos = doc.GetLinesIterator();
		
		UpdateVisibleLinesCount();
		
//		doc.Search("GiveUp");
		
		updateUI();
	}
	
	public void OnDocumentClosing() {
		
		m_topLinePos = null;
		m_selectedLine = -1;
//		updateUI();
	}
	
	public void paintComponent(Graphics g) {

		if ( m_topLinePos == null )
			return;
		
		super.paintComponent(g);
		
		
		Dimension 					size = getSize();
		Document 					doc = m_Controller.getDocument();
		LineDisplayIterator			displayIterator = new LineDisplayIterator(	g,
																				doc, 
																				m_topLinePos,
																				new Rectangle(0, 0, size.width, size.height),
																				new Point(0,0),
																				m_horzOffset );
		ContentWindow				window = doc.getWindow();
		ElementPos					foundElement = doc.getFoundElementPos();
		
		while ( displayIterator.isValid() ) {
			
			if ( m_selectedLine == displayIterator.getLineNo() )
				g.setFont(m_SelectedLineNoFont);
			else
				g.setFont(m_lineNoFont);
			displayIterator.DisplayLineNo(window.getStartLine());
			
			if ( 	foundElement != null && 
					displayIterator.getLineNo() == foundElement.getLineNo() )
			{
				displayIterator.Highlight(foundElement);
			}
			
			if ( m_selectedLine == displayIterator.getLineNo() )
				g.setFont(m_selectedLineFont);
			else
				g.setFont(m_lineFont);
			displayIterator.DisplayLine();

			displayIterator.gotoNext();
		}
			
		m_Controller.OnMaxLineWidthUpdate((int)displayIterator.getMaxWidth());
	}

	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {

		if ( m_topLinePos == null )
			return;
		
		LineDisplayIterator			displayIterator = new LineDisplayIterator(	getGraphics(),
																				m_Controller.getDocument(), 
																				m_topLinePos,
																				new Rectangle(0, 0, getSize().width, getSize().height),
																				new Point(5,0),
																				m_horzOffset );
		int							selectedLine = -1;
		String						selectedLineText = null;
		
		m_selectedLine = -1;
				
		while ( displayIterator.isValid() && selectedLine == -1 )
		{
			if ( displayIterator.isPointInLine(e.getPoint()) )
			{
				selectedLine = displayIterator.getLineNo();
				selectedLineText = displayIterator.getLineText();
			}
			
			displayIterator.gotoNext();
		}
		
		if ( m_selectedLine != selectedLine )
		{
			m_selectedLine = selectedLine;
			m_Controller.OnLineSelected(m_selectedLine, selectedLineText);
			updateUI();
		}
		
		grabFocus();
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}

	public void componentHidden(ComponentEvent e) {
		
	}
	
	public void componentMoved(ComponentEvent e) {
		
	}
	
	public void componentResized(ComponentEvent e) {
		UpdateVisibleLinesCount();
	}
	
	public void componentShown(ComponentEvent e) {
		
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		Document doc = m_Controller.getDocument();
		
		switch (e.getKeyCode()){

			case KeyEvent.VK_DOWN:{
				
				if ( m_selectedLine < doc.getLinesCount() )
				{
					++ m_selectedLine;
					Document.Iterator pos = doc.new Iterator(m_topLinePos);
					pos.gotoLineNo(m_selectedLine);
					m_Controller.OnLineSelected(m_selectedLine, pos.getLineText());
				}
								
				updateUI();
			}
			break;
			
			case KeyEvent.VK_UP:{
				
				if ( m_selectedLine > 0 )
				{
					-- m_selectedLine;
					Document.Iterator pos = doc.new Iterator(m_topLinePos);
					pos.gotoLineNo(m_selectedLine);
					m_Controller.OnLineSelected(m_selectedLine, pos.getLineText());
				}
				
				updateUI();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public Rectangle2D getTextBounds(String text, int startIndex) {
		
		Graphics			g = getGraphics();
		FontMetrics			fm = g.getFontMetrics(m_lineFont);
	
		Rectangle2D			bounds = m_selectedLineFont.getStringBounds(text.substring(0, startIndex), fm.getFontRenderContext());
		
		return bounds;
	}
	
	public void SetSelectedLine(int lineNo) {
		m_selectedLine = lineNo;
	}

	protected void UpdateVisibleLinesCount() {

		Graphics		g = getGraphics();
		g.setFont(m_lineFont);
		FontMetrics		fontMetrics = g.getFontMetrics();
		
		int		numberOfVisibleLines = getSize().height / (fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent() + 5);
		
		m_Controller.OnVisibleLinesCountChanged(numberOfVisibleLines-1);
	}
	
	protected DocumentController 	m_Controller;
	protected int 					m_VisibleLinesCount = 1;
	protected Document.Iterator		m_topLinePos;
	protected Font					m_lineFont = new Font("Courier", Font.PLAIN, 12);
	protected Font					m_selectedLineFont = new Font("Courier", Font.BOLD, 12);
	protected Font					m_lineNoFont = new Font("Courier", Font.ITALIC, 12);
	protected Font					m_SelectedLineNoFont = new Font("Courier", Font.BOLD | Font.ITALIC, 12);
	protected int					m_selectedLine = -1;
	protected int					m_horzOffset = 0;
}
