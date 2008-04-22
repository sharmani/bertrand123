package com.bg.annuaire.test.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

//public class TableCellRender_Button extends JButton implements TableCellRenderer, TableCellEditor {
/* (swing1.1beta3) */

/**
 * @version 1.0 11/09/98
 */
public class TableCellRender_Button extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
	protected JButton button;
	//
	// Instance Variables
	//

	/** The Swing component being edited. */
	protected JButton button__;


	private String label_;

	private boolean isEditable = false;
	
	/**
	 * An integer specifying the number of clicks needed to start editing. Even
	 * if <code>clickCountToStart</code> is defined as zero, it will not
	 * initiate until a click occurs.
	 */
	protected int clickCountToStart = 1;


	public TableCellRender_Button(ActionListener l) {
		// super(new JButton());
		button = new JButton();
		//button = button_;
		button.setOpaque(true);
		button.setRequestFocusEnabled(false);
		button.addActionListener(l);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}

	public Component getComponent() {
		return button;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		if (isSelected) {
			button.setEnabled(true);
			isEditable = true;
			// button.setForeground(table.getSelectionForeground());
			// button.setBackground(table.getSelectionBackground());
		} else {
			button.setEnabled(false);
			isEditable = false;
			// button.setForeground(table.getForeground());
			// button.setBackground(table.getBackground());
		}
		setText("" + value);
		return button;
	}

	/*
	 * protected void fireEditingStopped() { super.fireEditingStopped(); }
	 */

	private void setText(Object value) {
		button.setText("Détail: " + value);
		// button.setActionCommand(""+value);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		// super.getTableCellEditorComponent(table, value, isSelected, row,
		// column);
		this.setText(value);
		return this.button;
	}


	/**
	 * The delegate class which handles all methods sent from the
	 * <code>CellEditor</code>.
	 */
	// protected EditorDelegateBg delegate____________________;

	//
	// Constructors
	//

	//
	// Modifying
	//

	/**
	 * Specifies the number of clicks needed to start editing.
	 * 
	 * @param count
	 *            an int specifying the number of clicks needed to start editing
	 * @see #getClickCountToStart
	 */
	public void setClickCountToStart(int count) {
		clickCountToStart = count;
	}

	/**
	 * Returns the number of clicks needed to start editing.
	 * 
	 * @return the number of clicks needed to start editing
	 */
	public int getClickCountToStart() {
		return clickCountToStart;
	}

	//
	// Override the implementations of the superclass, forwarding all methods
	// from the CellEditor interface to our delegate.
	//

	//
	// Implementing the TreeCellEditor Interface
	//

	/** Implements the <code>TreeCellEditor</code> interface. */
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
		// String stringValue = tree.convertValueToText(value, isSelected,
		// expanded, leaf, row, false);

		// delegate.setValue(stringValue);
		return button;
	}

	/** The value of this cell. */
	protected Object value;

	/**
	 * Returns the value of this cell.
	 * 
	 * @return the value of this cell
	 */
	public Object getCellEditorValue() {
		return value;
	}

	/**
	 * Sets the value of this cell.
	 * 
	 * @param value
	 *            the new value of this cell
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Returns true if <code>anEvent</code> is <b>not</b> a
	 * <code>MouseEvent</code>. Otherwise, it returns true if the necessary
	 * number of clicks have occurred, and returns false otherwise.
	 * 
	 * @param anEvent
	 *            the event
	 * @return true if cell is ready for editing, false otherwise
	 * @see #setClickCountToStart
	 * @see #shouldSelectCell
	 */

	public boolean isCellEditable(EventObject anEvent) {
		if (anEvent instanceof MouseEvent) {
			return ((MouseEvent) anEvent).getClickCount() >= clickCountToStart;
		}
		return true;
	}

	/**
	 * Returns true to indicate that the editing cell may be selected.
	 * 
	 * @param anEvent
	 *            the event
	 * @return true
	 * @see #isCellEditable
	 */
	public boolean shouldSelectCell(EventObject anEvent) {
		return true;
	}

	/**
	 * Returns true to indicate that editing has begun.
	 * 
	 * @param anEvent
	 *            the event
	 */
	public boolean startCellEditing(EventObject anEvent) {
		return true;
	}

	/**
	 * Stops editing and returns true to indicate that editing has stopped. This
	 * method calls <code>fireEditingStopped</code>.
	 * 
	 * @return true
	 */
	public boolean stopCellEditing() {
		fireEditingStopped();
		return true;
	}

	/**
	 * Cancels editing. This method calls <code>fireEditingCanceled</code>.
	 */
	public void cancelCellEditing() {
		fireEditingCanceled();
	}

	/**
	 * When an action is performed, editing is ended.
	 * 
	 * @param e
	 *            the action event
	 * @see #stopCellEditing
	 */
	public void actionPerformed(ActionEvent e) {
		this.stopCellEditing();
	}

	/**
	 * When an item's state changes, editing is ended.
	 * 
	 * @param e
	 *            the action event
	 * @see #stopCellEditing
	 */
	public void itemStateChanged(ItemEvent e) {
		this.stopCellEditing();
	}

}
