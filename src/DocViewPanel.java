import javax.swing.*;
import java.awt.*;
import java.awt.font.*;


public class DocViewPanel extends JPanel {

	public DocViewPanel( DocumentController controller ) {
		super();
		m_Controller = controller;
	}
	
	public void paintComponent(Graphics g) {
		
		Dimension size = getSize();
		Graphics2D g2d = (Graphics2D)g;
		Rectangle r = new Rectangle(0, 0, size.width-1, size.height-1);
		FontMetrics fm = g.getFontMetrics();
		Document doc = m_Controller.getDocument();
		Font font = g.getFont();
		FontRenderContext frc = g2d.getFontRenderContext();
		float		y = 5;
		float		x = 0;
		float		emptyLineHeight = 0;
		DocLine line = doc.getFirstLine();
		
		TextLayout layout = new TextLayout("000000", font, frc);
		x = (float) layout.getBounds().getWidth() + 5;
		emptyLineHeight = (float)layout.getBounds().getHeight();
		
		g2d.draw(r);
		
		while ( line != null && y < size.height-5 )
		{
			if ( line.getText().isEmpty() )
				y += emptyLineHeight;
			else
			{
				layout = new TextLayout(line.getText(), font, frc);
				y += layout.getBounds().getHeight();
				layout.draw(g2d, x, y);
			}
			
			y += 5;			
			line = line.getNext();
		}
	}
	
	protected DocumentController m_Controller;
}
