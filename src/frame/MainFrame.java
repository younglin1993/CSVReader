package frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import model.Column;
import model.HeaderRow;
import model.Row;
import model.Table;
import project02.IOUtil;

public class MainFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Container cp;
	private DefaultTableModel model = new DefaultTableModel();
	private JTable jTable = new JTable(model);
	private TableColumnModel columnModel = jTable.getColumnModel();
	private JScrollPane jsp = new JScrollPane(jTable);
	private JPanel jpnN = new JPanel();
	private JPanel jpnW = new JPanel();
	private JTextField jtf = new JTextField();
	private JComboBox<String> comboBox = new JComboBox<>();
	private JButton searchBtn = new JButton("Search");
	private List<JCheckBox> checkBoxList = new ArrayList<>();
	private List<Column> colList = new ArrayList<>();
	
	
	
	public void init() {
		cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		this.setBounds(350, 150, 1000, 800);
		this.setTitle("CSVReader");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		cp.add(jsp, BorderLayout.CENTER);
		cp.add(jpnN, BorderLayout.NORTH);
		cp.add(jpnW, BorderLayout.WEST);
		jpnN.setLayout(new BorderLayout());
		jpnN.add(jtf, BorderLayout.CENTER);
		jpnN.add(comboBox, BorderLayout.WEST);
		jpnN.add(searchBtn, BorderLayout.EAST);
		jpnW.setLayout(new GridLayout(30, 0, 3, 3));
		
		comboBox.addItem("UTF-8");
		comboBox.addItem("MS950");
		comboBox.addItem("BIG5");
		
		searchBtn.addActionListener(e -> {
			try {
				model.setColumnCount(0);
				jTable.setModel(model);
				jTable = new JTable(model);
				checkBoxList = new ArrayList<>();
				colList = new ArrayList<>();
				BufferedReader br = IOUtil.getBufferedReader(getURL(), getCharsetName());
				String line = br.readLine();
				Table table = new Table();
				HeaderRow headerRow = new HeaderRow();
				for(String value : line.split(",")) {
					headerRow.add(value);
				}
				table.setHeaderRow(headerRow);
				while((line = br.readLine()) != null) {
					Row row = new Row();
					for(String str : line.split(",")) {
						row.add(str);
					}
					table.add(row);
				}
				jpnW.invalidate();
				jpnN.invalidate();
				jpnW.removeAll();
				for(int i = 0; i < headerRow.size(); i++) {
					Column col = table.getColumn(i);
					colList.add(col);
					JCheckBox checkBox = new JCheckBox(col.get(0));
					checkBox.setSelected(true);
					Object[] data = new Object[col.size()-1];
					for(int j = 0; j < col.size()-1; j++) {
						data[j] = col.get(j+1);
					}
					model.addColumn(col.get(0), data);
					jTable.setModel(model);
					
					checkBox.addActionListener(ae -> {
						if(checkBox.isSelected()) showColumn(col);
						else hideColumn(col);
					});
					checkBoxList.add(checkBox);
					jpnW.add(checkBox);
				}
				jpnN.validate();
				jpnW.validate();
				jpnW.revalidate();
			} catch (IOException e1) {
				
			} catch (Exception e2) {
				
			}
		});
	}
	
	public MainFrame() {
		init();
		setVisible(true);
		System.out.println(getURL());
	}
	
	public String getCharsetName() {
		return (String) comboBox.getSelectedItem();
	}
	
	public String getURL() {
		return jtf.getText();
	}
	
	
	public void	showColumn(Column col) {
		columnModel.getColumn(colList.indexOf(col)).setWidth(100);
        columnModel.getColumn(colList.indexOf(col)).setPreferredWidth(100);
        columnModel.getColumn(colList.indexOf(col)).setMinWidth(50);
        columnModel.getColumn(colList.indexOf(col)).setMaxWidth(200);
        columnModel.getColumn(colList.indexOf(col)).setResizable(true);
        columnModel.getColumn(colList.indexOf(col)).setPreferredWidth(100);
	}
	
	public void hideColumn(Column col) {
		 columnModel.getColumn(colList.indexOf(col)).setWidth(0);
         columnModel.getColumn(colList.indexOf(col)).setMinWidth(0);
         columnModel.getColumn(colList.indexOf(col)).setMaxWidth(0);
         columnModel.getColumn(colList.indexOf(col)).setResizable(false);
         columnModel.getColumn(colList.indexOf(col)).setPreferredWidth(0);
	}
}
