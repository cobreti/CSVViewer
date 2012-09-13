package org;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ContentWindowSelectionPanel extends JPanel {

	protected class BtnActionListener implements ActionListener {
		public BtnActionListener(DocumentController controller, int index) {
			m_index = index;
			m_controller = controller;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			System.out.println(e.toString());
			
			System.out.println("windowIndex = " + Integer.toString(m_index) );
			
			m_controller.OnContentWindowIndexChanged(m_index);
		}

		private int 				m_index;
		private DocumentController	m_controller;
	}
	
	public ContentWindowSelectionPanel(DocumentController controller) {
		
		m_controller = controller;
		
		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void OnNewDocument( Document doc )
	{		
		removeAll();
		
		if ( doc.getWindowsCount() > 1 )
		{
			for (int index = 0; index < doc.getWindowsCount(); ++ index)
			{
				AddButton(index);
			}
		}
		
		updateUI();
	}
	
	protected void AddButton(int index) {
		JToggleButton btn = new JToggleButton( Integer.toString(index) );
		btn.addActionListener(new BtnActionListener(m_controller, index));
		btn.setFont(m_btnFont);
		m_btnGroup.add(btn);
		add(btn);
		
		if ( index == 0 )
			btn.setSelected(true);
	}
	
	private ButtonGroup			m_btnGroup = new ButtonGroup();
	private DocumentController	m_controller;
	private Font				m_btnFont = new Font("arial", Font.PLAIN, 10);
}
