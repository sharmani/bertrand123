package com.bg.annuaire.tool;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;
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
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.bg.annuaire.test.swing.TableCellRender_Button;
import com.bg.annuaire.tool.company.Company;
import com.bg.annuaire.tool.company.CompanyFactory;

public class PanelListCompany extends JPanel implements ActionListener {

	private JPanel panelGlobal = new JPanel();

	private Hashtable<String, Boolean> hDisplayColumn = new Hashtable<String, Boolean>();

	private Vector<String> vDisplayColumn = new Vector<String>();

	private JTable table;

	List<Company> listCompany;

	private JButton buttonDelete = new JButton("delete");

	private JButton buttonEdit = new JButton("edit");

	public PanelListCompany() {
		super();
		initDisplayColumn();
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

	private static String K_ID = "id";

	private static String K_Name = "Name";

	private static String K_SIRET = "Siret";

	private static String K_Naf = "NAF";

	private static String K_classification = "class";

	private static String K_Telephone = "tel";

	private static String K_CodePostal = "Code";

	private static String K_Ville = "Ville";

	private static String K_Effectif = "Effectif";

	private static String K_ToDo = "ToDo";

	private static String K_NbActions = "NbActions";

	/**
	 * 
	 */
	private void initDisplayColumn() {
		hDisplayColumn.put(K_ID, true);
		hDisplayColumn.put(K_Name, true);
		hDisplayColumn.put(K_SIRET, false);
		hDisplayColumn.put(K_Naf, false);
		hDisplayColumn.put(K_classification, false);
		hDisplayColumn.put(K_Telephone, true);
		hDisplayColumn.put(K_CodePostal, true);
		hDisplayColumn.put(K_Ville, true);
		hDisplayColumn.put(K_Effectif, false);
		hDisplayColumn.put(K_ToDo, true);
		hDisplayColumn.put(K_NbActions, true);
		//
		vDisplayColumn.add(K_ID);
		vDisplayColumn.add(K_Name);
		vDisplayColumn.add(K_SIRET);
		vDisplayColumn.add(K_Naf);
		vDisplayColumn.add(K_classification);
		vDisplayColumn.add(K_Telephone);
		vDisplayColumn.add(K_CodePostal);
		vDisplayColumn.add(K_Ville);
		vDisplayColumn.add(K_Effectif);
		vDisplayColumn.add(K_ToDo);
		vDisplayColumn.add(K_NbActions);
	}

	private void initPanelGlobal() {
		this.panelGlobal.removeAll();
		this.listCompany = CompanyFactory.getInstance().getList();

		Iterator<Company> ite = listCompany.iterator();
		Object[][] oo = new Object[listCompany.size()][getColumnNames().length];
		int i = 0;
		while (ite.hasNext()) {
			Company c = ite.next();
			Enumeration<String> enu = vDisplayColumn.elements();
			int j = 0;
			while (enu.hasMoreElements()) {
				String k = enu.nextElement();
				Boolean b = hDisplayColumn.get(k);
				if (b) {
					Object v = "!NoObj!";

					if (k == K_ID) {
						v = c.getId();
					} else if (k == K_Name) {
						v = c.getName();
					} else if (k == K_SIRET) {
						v = c.getSiret();
					} else if (k == K_Naf) {
						v = c.getNaf();
					} else if (k == K_classification) {
						v = c.getClassification();
					} else if (k == K_Telephone) {
						v = c.getTelephone();
					} else if (k == K_CodePostal) {
						v = c.getCodePostal();
					} else if (k == K_Ville) {
						v = c.getVille();
					} else if (k == K_Effectif) {
						v = c.getEffectif();
					} else if (k == K_ToDo) {
						v = c.getActionToDo();
					} else if (k == K_NbActions) {
						v = "" + c.getNbActions();
					}
					if (b) {
						oo[i][j] = v;
						j++;
					}
				}
			}
			/*
			 * oo[i][0] = i; oo[i][1] = c.getName(); oo[i][2] = c.getSiret();
			 * oo[i][3] = c.getNaf(); oo[i][4] = c.getClassification(); oo[i][5] =
			 * c.getTelephone(); oo[i][6] = c.getCodePostal(); oo[i][7] =
			 * c.getVille(); oo[i][8] = c.getEffectif(); oo[i][9] =
			 * c.getActionToDo().booleanValue(); oo[i][10] =
			 * ""+c.getNbActions();
			 */
			i++;
		}
		MyTableModel model = new MyTableModel(oo);
		this.table = new JTable(model);
		// //
		TableColumnModel tcm = table.getColumnModel();
		TableColumn tc = tcm.getColumn(0);
		TableCellRender_Button cell_button = new TableCellRender_Button(this);
		tc.setCellRenderer(cell_button);
		tc.setCellEditor(cell_button);
		// //
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
		String names = "";
		for (int k = 0; k < numeroRowSelected.length; k++) {
			int ii = table.convertRowIndexToModel(numeroRowSelected[k]);
			Company c = this.listCompany.get(ii);
			cc[k] = c;
			names += "" + c.getName() + ", ";
		}
		int r = JOptionPane.showConfirmDialog(this, "Confirm delete " + numeroRowSelected.length + " companies \n" + names + " ?");
		if (r == JOptionPane.YES_OPTION) {
			CompanyFactory.getInstance().delete(cc);
			this.initPanelGlobal();
			this.updateUI();
		}

	}

	private void editCompany() {
		int i = table.getSelectedRow();
		System.out.println("EditCompany  selecteRow:" + i);
		int ii = table.convertRowIndexToModel(i);

		Company c = this.listCompany.get(ii);
		System.out.println("editCompany " + i + " :: " + ii + "   :: " + c.getName());
		ToolAnuaireGui.getInstance().editCompany(c);
	}

	public void updateList() {
		this.initPanelGlobal();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("!!----------------------actionPerformed :" + e.getActionCommand());
		String command = e.getActionCommand();
		try {
			Long id = Long.parseLong(command.trim());
			ToolAnuaireGui.getInstance().displayDetail(id);
		} catch (Exception e1) {
			ToolAnuaireGui.getInstance().log("Exception " + e1.getMessage());
			e1.printStackTrace();
		}
	}

	private int getNombreDeColumns() {
		Enumeration<String> enu = this.hDisplayColumn.keys();
		int i = 0;
		while (enu.hasMoreElements()) {
			String key = enu.nextElement();
			Boolean value = this.hDisplayColumn.get(key);
			if (value) {
				i++;
			}
		}
		return i;
	}

	private String[] getColumnNames() {
		// String[] columnNames = { "id", "Name", "Siret",
		// "naf","classification", "telephone", "codePostal", "Ville",
		// "effectifs" ,"todo","nbActions"};
		String[] columnNames = new String[getNombreDeColumns()];
		Enumeration<String> enu = this.vDisplayColumn.elements();
		int i = 0;
		while (enu.hasMoreElements()) {
			String key = enu.nextElement();
			Boolean value = this.hDisplayColumn.get(key);
			if (value) {
				columnNames[i] = key;
				i++;
			}
		}
		return columnNames;
	}

	// xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

	private class MyTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String[] columnNames;

		private Object[][] data;

		public MyTableModel(Object[][] data_) {
			this.columnNames = getColumnNames();
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
			if (col == 0) {
				return true;
			} else {
				return false;
			}
		}

		/*
		 * Don't need to implement this method unless your table's data can
		 * change.
		 */
		public void setValueAt(Object value, int row, int col) {
			if (col == 0) {
				return;
			}
			data[row][col] = value;
			fireTableCellUpdated(row, col);
		}

	}

}
