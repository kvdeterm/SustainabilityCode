import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class ComboBox extends JPanel{
	
	protected final JComboBox box;
	public ComboBox(final ArrayList<InsulMat> a, final Home h, final String name){
		setPreferredSize(new Dimension(200,200));
		
		box = new JComboBox();
		for(int i = 0; i < a.size(); i++){
			box.addItem(a.get(i).getName());
		}
		
		box.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0)
			{
				for(int j = 0; j < a.size(); j++){
					if (box.getSelectedIndex() == j){
						if(name == "wall1"){
							h.setWall1(a.get(j));}
						else if (name == "wall2")
							h.setWall2(a.get(j));
						else if (name == "wall3")
							h.setWall3(a.get(j));
						else if (name == "wall4")
							h.setWall4(a.get(j));
						else if (name == "roof1")
							h.setRoof1(a.get(j));
						else if (name == "roof2")
							h.setRoof2(a.get(j));
						else if (name == "roof3")
							h.setRoof3(a.get(j));
						else if (name == "roof4")
							h.setRoof4(a.get(j));
						else if (name == "floor1")
							h.setFloor1(a.get(j));
						else if (name == "floor2")
							h.setFloor2(a.get(j));
						else if (name == "floor3")
							h.setFloor3(a.get(j));
						else if (name == "floor4")
							h.setFloor4(a.get(j));
					}
				}
			}
		});
		
		add(box);
	}
	public JComboBox getJBox(){
		return box;
	}
	public void setJBox(InsulMat d){
		box.setSelectedItem(d.getName());
	}
	
}
