import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserQuery extends JFrame{

	protected JLabel dropdownLab, thLab;
	protected ComboBox dropdown;
	protected JTextField thField;
	protected JPanel panel1 = new JPanel();
	protected JPanel panel2 = new JPanel();
	protected Container conPane;
	
	public UserQuery(String ddlab, String thick, ArrayList<InsulMat> insul, Home h, String name){
		dropdownLab = new JLabel(ddlab);
		thLab = new JLabel(thick);
		dropdown = new ComboBox(insul, h, name);
		thField = new JTextField(5);
		
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		panel1.add(dropdownLab);
		panel1.add(dropdown);
		panel2.add(thLab);
		panel2.add(thField);
	}
	
	public JPanel getPanel1(){
		return panel1;
	}
	public JPanel getPanel2(){
		return panel2;
	}
	public JTextField getTextF(){
		return thField;
	}
	public JComboBox getDropdown(){
		return dropdown.getJBox();
	}
	public void setDropdown(InsulMat m){
		dropdown.setJBox(m);
	}
	public void setText(String s){
		thField.setText(s);
	}
}
