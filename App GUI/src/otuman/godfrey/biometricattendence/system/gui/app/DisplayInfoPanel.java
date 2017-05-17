package otuman.godfrey.biometricattendence.system.gui.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import otuman.godfrey.biometricattendence.system.model.Person;
import otuman.godfrey.biometricattendence.system.model.controller.Controller;

public class DisplayInfoPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int height;
	private final int width;
	private final Dimension dim;
	private final TablePanelModel tablePanelModel;
	private final JTable jTable;
	/**
	 * 
	 */

	public DisplayInfoPanel() {
		height              = 600;
		width               = 500;
		dim                 = new Dimension(width, height);
		
		tablePanelModel = new TablePanelModel("No","Full Name","Gender","Student ID", "Finger ID");
		jTable          = new JTable(tablePanelModel);
                jTable.addMouseListener(new MouseAdapter(){
                   @Override
                   public void mouseClicked(MouseEvent e) {
                     if (e.getClickCount() == 2) {
                     JTable target = (JTable)e.getSource();
                     int row       = target.getSelectedRow();
                     TableModel model = target.getModel();
                     String id = model.getValueAt(row, 3).toString();
                     AddNewPersonPannel.DataFromTable(Integer.parseInt(id));
                                                         
                   }
                   }
                 });
                
		setLayout(new BorderLayout());
                add(new JScrollPane(jTable), BorderLayout.CENTER);
		setPreferredSize(dim);
		setBackground(new Color(248,248,255));
        //setBackground(new Color(0, 204, 204));
		setVisible(true);
	}
        
        public void setData(List<Person> listOfPeople){
        tablePanelModel.setListOfPeople(listOfPeople);
        }
        public void refresh(){
           tablePanelModel.fireTableDataChanged();
        }

}
