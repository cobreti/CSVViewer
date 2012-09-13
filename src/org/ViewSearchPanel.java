package org;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

public class ViewSearchPanel extends JPanel {

	/**
	 * 
	 * @author scripter
	 *
	 */
	private class NextBtnListener implements ActionListener {

		public NextBtnListener( ViewSearchPanel owner ) {
			m_owner = owner;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			m_owner.m_controller.OnFindNext(m_owner.m_textField.getText());
		}
		
		private ViewSearchPanel m_owner;
	}
	
	/**
	 * 
	 * @author scripter
	 *
	 */
	private class PreviousBtnListener implements ActionListener {

		public PreviousBtnListener( ViewSearchPanel owner ) {
			m_owner = owner;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			m_owner.m_controller.OnFindPrevious(m_owner.m_textField.getText());
		}
		
		private ViewSearchPanel m_owner;
	}
	
	
	private class TextFieldListener implements DocumentListener {

		public TextFieldListener( ViewSearchPanel owner ) {
			m_owner = owner;
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			String text = m_owner.m_textField.getText();

			m_owner.m_btnNext.setEnabled( !text.isEmpty() );
			m_owner.m_btnPrevious.setEnabled( !text.isEmpty() );						
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			String text = m_owner.m_textField.getText();

			m_owner.m_btnNext.setEnabled( !text.isEmpty() );
			m_owner.m_btnPrevious.setEnabled( !text.isEmpty() );						
			
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			String text = m_owner.m_textField.getText();

			m_owner.m_btnNext.setEnabled( !text.isEmpty() );
			m_owner.m_btnPrevious.setEnabled( !text.isEmpty() );						
			
		}

		private ViewSearchPanel m_owner;
	}

	
	public ViewSearchPanel( DocumentController controller ) {
		m_controller = controller;
		
		setLayout( new BoxLayout(this, BoxLayout.X_AXIS));
		
		m_textField = new JTextField();
		m_textField.setFont(m_font);
		Dimension maxSize = m_textField.getMaximumSize();
		maxSize.setSize( 200, maxSize.width);
		m_textField.setMaximumSize(maxSize);
		m_textField.getDocument().addDocumentListener( new TextFieldListener(this) );
		
		m_btnNext = new JButton("Next");
		m_btnNext.setFont(m_font);
		m_btnNext.setEnabled(false);
		m_btnNext.addActionListener( new NextBtnListener(this) );
		
		m_btnPrevious = new JButton("Prec");
		m_btnPrevious.setFont(m_font);
		m_btnPrevious.setEnabled(false);
		m_btnPrevious.addActionListener( new PreviousBtnListener(this) );
		
		add(m_textField);
		add(m_btnPrevious);
		add(m_btnNext);
	}
	
	private DocumentController 	m_controller;
	private JTextField			m_textField;
	private JButton				m_btnNext;
	private JButton				m_btnPrevious;
	private Font				m_font = new Font("arial", Font.PLAIN, 10);
}
