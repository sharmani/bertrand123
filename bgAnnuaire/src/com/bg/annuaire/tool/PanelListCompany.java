package com.bg.annuaire.tool;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.bg.annuaire.tool.company.Company;
import com.bg.annuaire.tool.company.CompanyFactory;

public class PanelListCompany extends JPanel {

	private JPanel panelGlobal = new JPanel();

	private JTable table;

	List<Company> listCompany;

	private JButton buttonDelete = new JButton("delete");

	private JButton buttonEdit = new JButton("edit");

	public PanelListCompany() {
		super();

		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteCompany();
			}

		});

		buttonEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editCompany();
			}
		});
		JPanel panelButton = new JPanel();
		panelButton.add(buttonDelete);
		panelButton.add(buttonEdit);
		this.setLayout(new BorderLayout());
		this.add(panelGlobal, BorderLayout.CENTER);
		this.add(panelButton, BorderLayout.NORTH);
		panelGlobal.setLayout(new GridLayout(0, 1));

		initPanelGlobal();
	}

	private void initPanelGlobal() {
		this.panelGlobal.removeAll();
		this.listCompany = CompanyFactory.getInstance().getList();

		Iterator<Company> ite = listCompany.iterator();
		Object[][] oo = new Object[listCompany.size()][8];
		int i = 0;
		while (ite.hasNext()) {
			Company c = ite.next();

			JPanel p = new JPanel(new GridLayout(1, 0));
			p.add(new JLabel(c.getName()));
			p.add(new JLabel(c.getSiret()));

			oo[i][0] = i;
			oo[i][1] = c.getName();
			oo[i][2] = c.getSiret();
			oo[i][3] = c.getNaf();
			oo[i][4] = c.getTelephone();
			oo[i][5] = c.getCodePostal();
			oo[i][6] = c.getVille();
			oo[i][7] = c.getEffectif();
			i++;

		}
		MyTableModel model = new MyTableModel(oo);
		this.table = new JTable(model);
		table.setAutoCreateRowSorter(true);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		this.panelGlobal.add(scrollPane);
	}

	private void editCompany(Company c) {
		System.out.println("Edit Compnay");
		ToolAnuaireGui.getInstance().log("Edit Company " + c.getName());
		ToolAnuaireGui.getInstance().editCompany(c);
	}

	private void deleteCompany() {
		System.out.println("DeleteCompany");
		int[] numeroRowSelected = this.table.getSelectedRows();
		Company[] cc = new Company[numeroRowSelected.length];
		String names="";
		for (int k = 0; k < numeroRowSelected.length; k++) {
			int ii = table.convertRowIndexToModel(numeroRowSelected[k]);
			Company c = this.listCompany.get(ii);
			cc[k] = c;
			names+=""+c.getName()+", ";
		}
		int r = JOptionPane.showConfirmDialog(this, "Confirm delete " +numeroRowSelected.length+" companies \n"+ names + " ?");
		if (r == JOptionPane.YES_OPTION) {
			CompanyFactory.getInstance().delete(cc);
			this.initPanelGlobal();
			this.updateUI();
		}
		
	}

	private void editCompany() {
		System.out.println("DeleteCompany");
		int i = table.getSelectedRow();
		int ii = table.convertRowIndexToModel(i);

		Company c = this.listCompany.get(ii);
		System.out.println("editCompany " + i + " :: " + ii + "   :: " + c.getName());
		ToolAnuaireGui.getInstance().editCompany(c);
	}

	public void updateList() {
		this.initPanelGlobal();
	}

	// xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

	private class MyTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String[] columnNames = { "id","Name", "Siret", "naf", "telephone", "codePostal", "Ville", "effectifs" };

		private Object[][] data;

		public MyTableModel(Object[][] data_) {
			this.data = data_;
		}

		public int getColumnCount() {
			return columnNames.length;
		}

		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		public Object getValueAt(int row, int col) {
			return data[row][col];
		}

		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			if (col < 2) {
				return false;
			} else {
				return true;
			}
		}

		/*
		 * Don't need to implement this method unless your table's data can
		 * change.
		 */
		public void setValueAt(Object value, int row, int col) {
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}

	}

}
