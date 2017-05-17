package otuman.godfrey.biometricattendence.system.gui.app;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import otuman.godfrey.biometricattendence.system.model.AttendanceRecords;
import otuman.godfrey.biometricattendence.system.model.Person;

public class TablePanelModel extends AbstractTableModel  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ArrayList columnNames; 
        private List<Person> list;
        private List<AttendanceRecords> attendRecord;

    
        TablePanelModel(String no,String id,String fullName,String gender,String fingerId){
          columnNames = new ArrayList<>();
          columnNames.add(no);
          columnNames.add(id);
          columnNames.add(fullName);
          columnNames.add(gender);
          columnNames.add(fingerId);
          
        }

        TablePanelModel(String no,String id,String fullName,String attStatus) {
          columnNames = new ArrayList<>();
          columnNames.add(no);
          columnNames.add(id);
          columnNames.add(fullName);
          columnNames.add(attStatus);
                     
        }

        @Override
	public String getColumnName(int columnIndex) {
    	
    	return columnNames.get(columnIndex).toString();
    	
	}
	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
             Person person   = list.get(rowIndex);
             switch(columnIndex){
                 case 0:
                    return rowIndex +1;
                 case 1:
                     return (person.getFirstName() + " "+person.getMiddleName()+" "+person.getLastName());
                 case 2:
                     return person.getGender(); 
                 case 3:
                     return person.getID();
                     //break;
                 case 4:
                   return person.getFinger_Id();
                    //break;
                 
             }
             return null;
	}
        
       public void setListOfPeople(List<Person> list) {
        this.list = list;
       }
       public void setAttendRecord(List<AttendanceRecords> attendRecord) {
        this.attendRecord = attendRecord;
       }
        
}
