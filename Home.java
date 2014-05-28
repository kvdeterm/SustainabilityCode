import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends JFrame{
	
	//insulation materials to be changed for each instance of a home
	InsulMat layer1Mat, layer2Mat, layer3Mat, layer4Mat;
	InsulMat layer1MatRoof, layer2MatRoof, layer3MatRoof, layer4MatRoof;
	InsulMat layer1MatFloor, layer2MatFloor, layer3MatFloor, layer4MatFloor;
	
	//these should all be in square feet
	double grossWallArea, netWallArea, roofArea, floorArea; //to use with the insulation values
	double doorArea = 2880/144; //average door in sq. ft.
	
	//values for calculating Manual J heating load
	protected double QWind, QDoor, QWall, QRoof, QFloor;
	protected double heatLoad;
	
	//values for exhaustive search
	int money = 0; //initial investment for exhaustive search
	double costOfInputs; //baseline cost of what user selects for comparison
	double heatingPerSqFt = 3.79; //cost of heating system per square foot of floor
	double floorCost, roofCost, wallCost, windowCost, heatSysCost;
	
	

	//thicknesses for each insulation material choice to calculate R value and cost (if they are variable materials)
	double insul1WallTh, insul2WallTh, insul3WallTh, insul4WallTh;
	double insul1RoofTh, insul2RoofTh, insul3RoofTh, insul4RoofTh;
	double insul1FloorTh, insul2FloorTh, insul3FloorTh, insul4FloorTh;
	
	//values for "typical" home for testing
	double typAvgWindSize = 3*5; //ft^2
	
	//create all Wall Insulation Materials with variable R / cost values
	//arguments: name, r per inch, cost per inch per sq. ft., typ. thickness
	InsulMat xps = new VariableInsulMat("XPS", 5, 1.15, .5);
	InsulMat eps = new VariableInsulMat("EPS", 3.9, .85, .5);
	InsulMat polyiso = new VariableInsulMat("Polyisocyanurate", 6.5, 1.2, .5);
	InsulMat sprayFoamLD = new VariableInsulMat("Spray Foam LD Polyurethane", 3.7, .35, .5);
	InsulMat sprayFoamHD = new VariableInsulMat("Spray Foam HD Polyurethane", 6.5, 1.05, .5);
	InsulMat cellulSpray = new VariableInsulMat("Cellulose Spray", 3.2, .4, .5);
	InsulMat cellulBlown = new VariableInsulMat("Cellulose Blown", 3.7, .35, 5);
	InsulMat fiberglassPack = new VariableInsulMat("Fiberglass Dense Pack", 4, .35, .5);
	InsulMat fiberglassBatt = new VariableInsulMat("Fiberglass Batts", 3.5, .15, 6);
	InsulMat mineralWool = new VariableInsulMat("Mineral Wool Batts", 3.3, .25, .5);
	InsulMat fiberboard = new VariableInsulMat("Fiberboard", 2.6, 1.4, 1);
	InsulMat particleBoard = new VariableInsulMat("Particle Board", 1.1, .7, 1);
	InsulMat brick = new VariableInsulMat("Brick", .22, 2.2, 2);
	InsulMat concrete = new VariableInsulMat("Concrete", .15, .15, 1);
	InsulMat stucco = new VariableInsulMat("Stucco", .15, .15, 1);
	InsulMat cement = new VariableInsulMat("Cement Block", .15, .15, 1);

	//create all Wall Insulation Materials with standardized thickness, R and cost
	//arguments: name, R value, cost per sq. ft., thickness
	InsulMat ply = new StandardInsulMat("Plywood", .625, .5, .5);
	InsulMat sheetrock = new StandardInsulMat("Sheetrock", .45, .82, .5);
	InsulMat woodSide = new StandardInsulMat("Wood Siding", .6, 3, .5);
	InsulMat vinylSide = new StandardInsulMat("Vinyl Siding", .15, 1, .25);
	InsulMat nothing = new StandardInsulMat("(nothing)", 0, 0, 0);
	
	//create all Roof Insulation Materials with variable R / cost values
	InsulMat cellulAttic = new VariableInsulMat("Attic Cellulose", 3.7, .15, 5);

	//create all Roof Insulation Materials with standardized thickness, R and cost
	InsulMat woodShing = new StandardInsulMat("Wooden Shingles", .6, 3, .5);
	InsulMat asphaltShing = new StandardInsulMat("Asphalt Shingles", .3, 1.25, .5);
	InsulMat carpet = new StandardInsulMat("Carpet", 2, 2, .75);
	InsulMat finishWood = new StandardInsulMat("Finish Wood", 1, 7.5, .75);
	InsulMat halfInSheath = new StandardInsulMat("Half Inch Insulated Sheathing", 3.4, 1.45, .5);
	InsulMat oneInSheath = new StandardInsulMat("One Inch Insulated Sheathing", 6.2, 2.08, 1);
	InsulMat housewrap = new StandardInsulMat("Housewrap", .1, .1, .1);


	//initialize arraylist of wall material options
	ArrayList<InsulMat> wallMats = new ArrayList<InsulMat>(); 
	//initilialize arrayList of roof material options
	ArrayList<InsulMat> roofMats = new ArrayList<InsulMat>(); 
	//initialize arrayList of floor material options
	ArrayList<InsulMat> floorMats = new ArrayList<InsulMat>();

	//add Jpanels and ContentPane
	JPanel row1a, row1b, row1c, row1d, row2a, row2b, row2c, row2d, row3a, row3b, row3c, row3d;
	JPanel row4a, row4b, row4c, row4d, row5a, row5b, row5c, row5d, row6a, row6b, row6c, row6d;
	JPanel row7a, row7b, row7c, row7d, row8a, row8b, row8c, row8d, row8e, row8DE, row9a, row9b, row9c, row9d;
	
	Container conPane;
	ComboBox layer1Wall, layer2Wall, layer3Wall, layer4Wall;
	ComboBox layer1Floor, layer2Floor, layer3Floor, layer4Floor;
	ComboBox layer1Roof, layer2Roof, layer3Roof, layer4Roof;
	
	//these are the input variables from user to calculate manual J
	int nuWindows, panes, nuDoors, nuRooms; //number of windows in the home, number of panes per window
	double windowArea, nuFloors; //average area of a window
	
	//other JComponents for text and labels and dropdown menus
	UserQuery wallQuery1, wallQuery2, wallQuery3, wallQuery4;
	UserQuery roofQuery1, roofQuery2, roofQuery3, roofQuery4;
	UserQuery floorQuery1, floorQuery2, floorQuery3, floorQuery4;
	JTextField numbWindField, panesField, numbDoorsField, windAreaField, nuFloorsField;
	JLabel nuWinLab, panesLab, nuDoorsLab, winAreaLab, nuFloorsLab;
	
	JTextField wallAreaField, areaRoofField, areaFloorField, nuRoomsField; //text fields for areas of wall, roof & floor
	JLabel wallAreaLab, areaRoofLab, areaFloorLab, nuRoomsLab; //labels for above text fields
	JButton quit, update, next, standardize;
	
	JButton begin;
	JPanel startPanel, instruct;
	

	public Home(){
		//add wall insulating materials to wall arraylist
		wallMats = createArrayList();
		//add wall insulating materials to roof arraylist
		roofMats = createArrayList();
		//add remaining roof only materials to roofMats arraylist
		roofMats = addRoofMats(roofMats);
		floorMats = roofMats;
		start();
		setSize(1000,700);
		setTitle("Unique Home Identifiers");
	}
	public void start(){
		begin = new JButton("Next");
		begin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	queryUser();
				
            }
		});
		conPane = getContentPane();
		startPanel = new JPanel();
		startPanel.add(begin);
		instruct = new JPanel();//this will include a list of instructions
		JTextArea instructions = new JTextArea();
		String in1 = "The following is a list of instructions for the following interface: ";
		String in2 = "- Please remember the golden rule of garbage in = garbage out";
		String in3 = "- All areas must be in square feet";
		String in4 = "- All insulation thicknesses must be in inches";
		String in5 = "- Please enter only positive numbers";
		String in6 = "- The number of doors refers to only external doors";
		String in7 = "- The wall area must be the total area of all external walls in the home";
		String in8 = "- The button 'Populate Typical Insulation Choices' will automatically set all 12 layers ";
		String in9 = "  of insulation to typical materials and thicknesses for Massachusetts homes ";
		String in10 = "- The button 'Update Insulation Thicknesses' will check all 12 insulation materials and ";
		String in11 = "  populate the thicknesses for those materials that come in a 'standard' thickness";
		
		instructions.setFont(new Font("new font", 5, 20));
		instructions.append(in1);
		instructions.append("\n");
		instructions.append("\n");
		instructions.append(in2);
		instructions.append("\n");
		instructions.append(in3);
		instructions.append("\n");
		instructions.append(in4);
		instructions.append("\n");
		instructions.append(in5);
		instructions.append("\n");
		instructions.append(in6);
		instructions.append("\n");
		instructions.append(in7);
		instructions.append("\n");
		instructions.append(in8);
		instructions.append("\n");
		instructions.append(in9);
		instructions.append("\n");
		instructions.append(in10);
		instructions.append("\n");
		instructions.append(in11);
		instructions.append("\n");
		
		instructions.setEditable(false);
		
		instruct.add(instructions);
		conPane.setLayout(new BorderLayout());
		conPane.add(instruct, BorderLayout.CENTER);
		conPane.add(startPanel, BorderLayout.SOUTH);
	}

	//this takes all the data from the user and inputs it into the appropriate variables
	public void queryUser(){
		conPane.remove(startPanel);
		conPane.remove(instruct);
		conPane.revalidate();
	
		//first row, with entries for #windows & #doors, avg. area of windows and #panes
		row1a = new JPanel();
		row1a.setLayout(new BorderLayout());
		row1b = new JPanel();
		row1b.setLayout(new BorderLayout());
		row1c = new JPanel();
		row1c.setLayout(new BorderLayout());
		row1d = new JPanel();
		row1d.setLayout(new BorderLayout());
		nuWinLab = new JLabel("Number of Windows: ");
		numbWindField = new JTextField(5);
		nuDoorsLab = new JLabel("Number of Doors: ");
		numbDoorsField = new JTextField(5);
		winAreaLab = new JLabel("Average Area of Windows: ");
		windAreaField = new JTextField(5);
		panesLab = new JLabel("Number of Panes per Window: ");
		panesField = new JTextField(5);
		
		row1a.add(nuWinLab, BorderLayout.CENTER);
		row1a.add(numbWindField, BorderLayout.SOUTH);
		row1b.add(nuDoorsLab, BorderLayout.CENTER);
		row1b.add(numbDoorsField, BorderLayout.SOUTH);
		row1c.add(winAreaLab, BorderLayout.CENTER);
		row1c.add(windAreaField, BorderLayout.SOUTH);
		row1d.add(panesLab, BorderLayout.CENTER);
		row1d.add(panesField, BorderLayout.SOUTH);
		
		//second row, dropdowns with insulation choices for 4 layers of wall
		row2a = new JPanel();
		row2b = new JPanel();
		row2c = new JPanel();
		row2d = new JPanel();
		wallQuery1 = new UserQuery("Wall Insulation Layer 1 (Exterior):                             ", "<html>Thickness of Wall Insulation Layer 1 <br>(Exterior): </html>", wallMats, this, "wall1");
		wallQuery2 = new UserQuery("Wall Insulation Layer 2:                                ", "<html>Thickness of Wall Insulation<br> Layer 2 :  </html>", wallMats, this, "wall2");
		wallQuery3 = new UserQuery("Wall Insulation Layer 3:                                ", "<html>Thickness of Wall Insulation<br> Layer 3:  </html>", wallMats, this, "wall3");
		wallQuery4 = new UserQuery("Wall Insulation Layer 4 (Interior):                             ", "<html>Thickness of Wall Insulation <br> Layer 4 (Interior) :  </html>", wallMats, this, "wall4");
		
				
		row2a.add(wallQuery1.getPanel1());
		row2b.add(wallQuery2.getPanel1());
		row2c.add(wallQuery3.getPanel1());
		row2d.add(wallQuery4.getPanel1());
		
		//third row, texfields for wall thickness
		row3a = new JPanel();
		row3b = new JPanel();
		row3c = new JPanel();
		row3d = new JPanel();
		
		row3a.add(wallQuery1.getPanel2());
		row3b.add(wallQuery2.getPanel2());
		row3c.add(wallQuery3.getPanel2());
		row3d.add(wallQuery4.getPanel2());
		
		//fourth row, dropdowns for floor
		row4a = new JPanel();
		row4b = new JPanel();
		row4c = new JPanel();
		row4d = new JPanel();
		
		floorQuery1 = new UserQuery("Floor Insulation Layer 1 (Exterior):                             ", "<html>Thickness of Floor Insulation Layer 1 <br>(Exterior): </html>", roofMats, this, "floor1");
		floorQuery2 = new UserQuery("Floor Insulation Layer 2:                                ", "<html>Thickness of Floor Insulation<br> Layer 2 :  </html>", roofMats, this, "floor2");
		floorQuery3 = new UserQuery("Floor Insulation Layer 2:                                ", "<html>Thickness of Floor Insulation<br> Layer 3:  </html>", roofMats, this, "floor3");
		floorQuery4 = new UserQuery("Floor Insulation Layer 4 (Interior):                             ", "<html>Thickness of Floor Insulation <br> Layer 4 (Interior) :  </html>", roofMats, this, "floor4");
		
		
		row4a.add(floorQuery1.getPanel1());
		row4b.add(floorQuery2.getPanel1());
		row4c.add(floorQuery3.getPanel1());
		row4d.add(floorQuery4.getPanel1());
		
		
		//fifth row,  text fields for floor thickness
		row5a = new JPanel();
		row5b = new JPanel();
		row5c = new JPanel();
		row5d = new JPanel();
		
		row5a.add(floorQuery1.getPanel2());
		row5b.add(floorQuery2.getPanel2());
		row5c.add(floorQuery3.getPanel2());
		row5d.add(floorQuery4.getPanel2());
		
		//row 6, dropdowns for roof
		row6a = new JPanel();
		row6b = new JPanel();
		row6c = new JPanel();
		row6d = new JPanel();
		
		roofQuery1 = new UserQuery("Roof Insulation Layer 1 (Exterior):                             ", "<html>Thickness of Roof Insulation Layer 1 <br>(Exterior): </html>", roofMats, this, "roof1" );
		roofQuery2 = new UserQuery("Roof Insulation Layer 2:                                ", "<html>Thickness of Roof Insulation<br> Layer 2 :  </html>", roofMats, this, "roof2");
		roofQuery3 = new UserQuery("Roof Insulation Layer 2:                                ", "<html>Thickness of Roof Insulation<br> Layer 3:  </html>", roofMats, this, "roof3");
		roofQuery4 = new UserQuery("Roof Insulation Layer 4 (Interior):                             ", "<html>Thickness of Roof Insulation <br> Layer 4 (Interior) :  </html>", roofMats, this, "roof4");
						
		row6a.add(roofQuery1.getPanel1());
		row6b.add(roofQuery2.getPanel1());
		row6c.add(roofQuery3.getPanel1());
		row6d.add(roofQuery4.getPanel1());
		
		//seventh row, textfields for roof thickness
		row7a = new JPanel();
		row7b = new JPanel();
		row7c = new JPanel();
		row7d = new JPanel();
		
		row7a.add(roofQuery1.getPanel2());
		row7b.add(roofQuery2.getPanel2());
		row7c.add(roofQuery3.getPanel2());
		row7d.add(roofQuery4.getPanel2());
		
		//row 8, wall area, roof area, floor area
		row8a = new JPanel();
		row8b = new JPanel();
		row8c = new JPanel();
		row8d = new JPanel();
		row8e = new JPanel();
		row8DE = new JPanel();
		
		row8a.setLayout(new BorderLayout());
		row8b.setLayout(new BorderLayout());
		row8c.setLayout(new BorderLayout());
		row8d.setLayout(new BorderLayout());
		row8e.setLayout(new BorderLayout());
		
		wallAreaLab = new JLabel("Total Wall Area: ");
		wallAreaField = new JTextField(5);
		areaRoofLab = new JLabel("Total Roof Area: ");
		areaRoofField = new JTextField(5);
		areaFloorLab = new JLabel("Total Area of a Single Floor: ");
		areaFloorField = new JTextField(5);
		nuRoomsLab = new JLabel("Number of Rooms: ");
		nuRoomsField = new JTextField(5);
		nuFloorsLab = new JLabel("Number of Floors");
		nuFloorsField = new JTextField(5);
		 
		row8a.add(wallAreaLab, BorderLayout.CENTER);
		row8a.add(wallAreaField,BorderLayout.SOUTH);
		row8b.add(areaRoofLab, BorderLayout.CENTER);
		row8b.add(areaRoofField, BorderLayout.SOUTH);
		row8c.add(areaFloorLab, BorderLayout.CENTER);
		row8c.add(areaFloorField, BorderLayout.SOUTH);	
		row8d.add(nuRoomsLab, BorderLayout.CENTER);
		row8d.add(nuRoomsField, BorderLayout.SOUTH);	
		row8e.add(nuFloorsLab, BorderLayout.CENTER);
		row8e.add(nuFloorsField, BorderLayout.SOUTH);
		row8DE.add(row8d);
		row8DE.add(row8e);
		
						
		//ninth row, buttons
		row9a = new JPanel();
		row9b = new JPanel();
		row9c = new JPanel();
		row9d = new JPanel();
		
				
		quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
		
		row9d.add(quit);

		next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//update all values, try / catch exceptions
				
				try{
					nuWindows = Integer.parseInt(numbWindField.getText());
					windowArea = Double.parseDouble(windAreaField.getText());
					nuDoors = Integer.parseInt(numbDoorsField.getText());
					panes = Integer.parseInt(panesField.getText());
					grossWallArea = Double.parseDouble(wallAreaField.getText());
					roofArea = Double.parseDouble(areaRoofField.getText());
					floorArea = Double.parseDouble(areaFloorField.getText());
					nuRooms = Integer.parseInt(nuRoomsField.getText());
					nuFloors = Double.parseDouble(nuFloorsField.getText());

					//store all thicknesses based on user inputs 
					insul1WallTh = Double.parseDouble(wallQuery1.getTextF().getText());
					insul2WallTh = Double.parseDouble(wallQuery2.getTextF().getText());
					insul3WallTh = Double.parseDouble(wallQuery3.getTextF().getText());
					insul4WallTh = Double.parseDouble(wallQuery4.getTextF().getText());
					insul1RoofTh = Double.parseDouble(roofQuery1.getTextF().getText());
					insul2RoofTh = Double.parseDouble(roofQuery2.getTextF().getText());
					insul3RoofTh = Double.parseDouble(roofQuery3.getTextF().getText());
					insul4RoofTh = Double.parseDouble(roofQuery4.getTextF().getText());
					insul1FloorTh = Double.parseDouble(floorQuery1.getTextF().getText());
					insul2FloorTh = Double.parseDouble(floorQuery2.getTextF().getText());
					insul3FloorTh = Double.parseDouble(floorQuery3.getTextF().getText());
					insul4FloorTh = Double.parseDouble(floorQuery4.getTextF().getText());

					//update materials thicknesses
					layer1Mat.setThickness(insul1WallTh);
					layer2Mat.setThickness(insul2WallTh);
					layer3Mat.setThickness(insul3WallTh);
					layer4Mat.setThickness(insul4WallTh);
					layer1MatRoof.setThickness(insul1RoofTh);
					layer2MatRoof.setThickness(insul2RoofTh);
					layer3MatRoof.setThickness(insul3RoofTh);
					layer4MatRoof.setThickness(insul4RoofTh);
					layer1MatFloor.setThickness(insul1FloorTh);
					layer2MatFloor.setThickness(insul2FloorTh);
					layer3MatFloor.setThickness(insul3FloorTh);
					layer4MatFloor.setThickness(insul4FloorTh);

					//throw exception if a negative number is input
					if(nuWindows<0 || windowArea<0 || panes<0 || nuDoors<0 || grossWallArea<0 || floorArea<0 || roofArea<0 || nuRooms<0 || nuFloors<0){
						throw new NegativeNumberException("negative");
					}
					if(insul1WallTh<0 || insul2WallTh<0 || insul3WallTh<0 ||insul4WallTh<0 || insul1RoofTh<0 || insul2RoofTh<0 || 
							insul3RoofTh<0 || insul4RoofTh<0 || insul1FloorTh<0 || insul2FloorTh<0 || insul3FloorTh<0 || insul4FloorTh<0){
						throw new NegativeNumberException("negative");
					}
					
					if(!(panes==2 || panes ==3)){
						throw new PanesException("Panes");
					}
					
					//update initial cost user has based on their selections
					floorCost = (layer1MatFloor.getCost() + layer2MatFloor.getCost() +layer3MatFloor.getCost()+ layer4MatFloor.getCost())*floorArea;
					roofCost = (layer1MatRoof.getCost() + layer2MatRoof.getCost() +layer3MatRoof.getCost()+ layer4MatRoof.getCost())*roofArea;
					wallCost = (layer1Mat.getCost() + layer2Mat.getCost() +layer3Mat.getCost()+ layer4Mat.getCost())*netWallArea;
					windowCost = getWindCost(windowArea, panes);
					heatSysCost = floorArea*nuFloors*heatingPerSqFt;
					
					costOfInputs = floorCost+roofCost+wallCost+windowCost+heatSysCost;

					//calculate heating load for the home
					manualJ();
					
					//begin next stage with optimization options, new screen
					optimizeQuery();
				}
				catch(NumberFormatException c){
					JOptionPane.showMessageDialog(new JPanel(),"You have entered an invalid number, please try again");
				}
				catch(NullPointerException a){
					JOptionPane.showMessageDialog(new JPanel(),"Please enter a number for all values");
				}
				catch(NegativeNumberException n){
					JOptionPane.showMessageDialog(new JPanel(),"Please enter only positive numbers");
				}
				catch(PanesException p){
					JOptionPane.showMessageDialog(new JPanel(),"The number of window panes can only be 2 or 3");
				}
			}
        });
		row9c.add(next);
		
		standardize = new JButton("Populate Typical Insulation Choices");
		standardize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	wallQuery1.setDropdown(woodSide);
            	wallQuery1.setText(Double.toString(woodSide.getThickness()));
            	wallQuery2.setDropdown(housewrap);
            	wallQuery2.setText(Double.toString(housewrap.getThickness()));
            	wallQuery3.setDropdown(fiberglassBatt);
            	wallQuery3.setText(Double.toString(fiberglassBatt.getThickness()));
            	wallQuery4.setDropdown(sheetrock);
            	wallQuery4.setText(Double.toString(sheetrock.getThickness()));
            	roofQuery1.setDropdown(woodShing);
            	roofQuery1.setText(Double.toString(woodShing.getThickness()));
            	roofQuery2.setDropdown(nothing);
            	roofQuery2.setText(Double.toString(nothing.getThickness()));
            	roofQuery3.setDropdown(fiberglassBatt);
            	roofQuery3.setText(Double.toString(fiberglassBatt.getThickness()));
            	roofQuery4.setDropdown(sheetrock);
            	roofQuery4.setText(Double.toString(sheetrock.getThickness()));
            	floorQuery1.setDropdown(housewrap);
            	floorQuery1.setText(Double.toString(housewrap.getThickness()));
            	floorQuery2.setDropdown(fiberglassBatt);
            	floorQuery2.setText(Double.toString(fiberglassBatt.getThickness()));
            	floorQuery3.setDropdown(ply);
            	floorQuery3.setText(Double.toString(ply.getThickness()));
            	floorQuery4.setDropdown(finishWood);
            	floorQuery4.setText(Double.toString(finishWood.getThickness()));
            	
            }
        });
		
		row9a.add(standardize);
		
		update = new JButton("Update Insulation Thicknesses");
		update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//update textboxes to reflect those materials with standardized thicknesses
            	//make those textboxes uneditable
            	//make all other textboxes editable
            	
            	//check the combo box for the name, update thickness based on the name
            	
            	if(layer1Mat instanceof StandardInsulMat){
        			wallQuery1.getTextF().setText(Double.toString(layer1Mat.getThickness()));
            		wallQuery1.getTextF().setEditable(false);
           		}
            	else wallQuery1.getTextF().setEditable(true);
        		if(layer2Mat instanceof StandardInsulMat){
        			wallQuery2.getTextF().setText(Double.toString(layer2Mat.getThickness()));
        			wallQuery2.getTextF().setEditable(false);
        		}
        		else wallQuery2.getTextF().setEditable(true);
        		if(layer3Mat instanceof StandardInsulMat){
        			wallQuery3.getTextF().setText(Double.toString(layer3Mat.getThickness()));
        			wallQuery3.getTextF().setEditable(false);
        		}
            	else wallQuery3.getTextF().setEditable(true);
        		if(layer4Mat instanceof StandardInsulMat){
        			wallQuery4.getTextF().setText(Double.toString(layer4Mat.getThickness()));
        			wallQuery4.getTextF().setEditable(false);
        		}
        		else wallQuery4.getTextF().setEditable(true);
            	
        		if(layer1MatRoof instanceof StandardInsulMat){
        			roofQuery1.getTextF().setText(Double.toString(layer1MatRoof.getThickness()));
        			roofQuery1.getTextF().setEditable(false);
        		}
        		else roofQuery1.getTextF().setEditable(true);
        		if(layer2MatRoof instanceof StandardInsulMat){
        			roofQuery2.getTextF().setText(Double.toString(layer2MatRoof.getThickness()));
        			roofQuery2.getTextF().setEditable(false);
        		}
        		else roofQuery2.getTextF().setEditable(true);
        		if(layer3MatRoof instanceof StandardInsulMat){
        			roofQuery3.getTextF().setText(Double.toString(layer3MatRoof.getThickness()));
        			roofQuery3.getTextF().setEditable(false);
        		}
        		else roofQuery3.getTextF().setEditable(true);
        		if(layer4MatRoof instanceof StandardInsulMat){
        			roofQuery4.getTextF().setText(Double.toString(layer4MatRoof.getThickness()));
        			roofQuery4.getTextF().setEditable(false);
        		}
        		else roofQuery4.getTextF().setEditable(true);
        		
        		if(layer1MatFloor instanceof StandardInsulMat){
        			floorQuery1.getTextF().setText(Double.toString(layer1MatFloor.getThickness()));
        			floorQuery1.getTextF().setEditable(false);
        		}
        		else floorQuery1.getTextF().setEditable(true);
        		if(layer2MatFloor instanceof StandardInsulMat){
        			floorQuery2.getTextF().setText(Double.toString(layer2MatFloor.getThickness()));
        			floorQuery2.getTextF().setEditable(false);
        		}
        		else floorQuery2.getTextF().setEditable(true);
        		if(layer3MatFloor instanceof StandardInsulMat){
        			floorQuery3.getTextF().setText(Double.toString(layer3MatFloor.getThickness()));
        			floorQuery3.getTextF().setEditable(false);
        		}
        		else floorQuery3.getTextF().setEditable(true);
        		if(layer4MatFloor instanceof StandardInsulMat){
        			floorQuery4.getTextF().setText(Double.toString(layer4MatFloor.getThickness()));
        			floorQuery4.getTextF().setEditable(false);
        		}
        		else floorQuery4.getTextF().setEditable(true);
        	}
            
            
        });
		
		row9b.add(update);
		
		conPane.setLayout(new GridLayout(0,4));
		conPane.add(row1a);
		conPane.add(row1b);
		conPane.add(row1c);
		conPane.add(row1d);
		
		conPane.add(row2a);
		conPane.add(row2b);
		conPane.add(row2c);
		conPane.add(row2d);
		
		conPane.add(row3a);
		conPane.add(row3b);
		conPane.add(row3c);
		conPane.add(row3d);
		
		conPane.add(row4a);
		conPane.add(row4b);
		conPane.add(row4c);
		conPane.add(row4d);
		
		conPane.add(row5a);
		conPane.add(row5b);
		conPane.add(row5c);
		conPane.add(row5d);
		
		conPane.add(row6a);
		conPane.add(row6b);
		conPane.add(row6c);
		conPane.add(row6d);
		
		conPane.add(row7a);
		conPane.add(row7b);
		conPane.add(row7c);
		conPane.add(row7d);
		
		conPane.add(row8a);
		conPane.add(row8b);
		conPane.add(row8c);
		conPane.add(row8DE);
		
		conPane.add(row9a);
		conPane.add(row9b);
		conPane.add(row9c);
		conPane.add(row9d);
				
	}
	public double getWindCost(double area, int panes){
		double cost;
		if (panes ==2){
			if(area < 6) cost = 310;
			else if (area < 9) cost = 318;
			else if (area < 12) cost = 415;
			else cost = 422;
		}
		else cost = 571;
		return cost;
	}
	public double getCostofInputs(){
		return costOfInputs;
	}
	
	public void optimizeQuery(){
		conPane.removeAll();
		conPane.revalidate();
		conPane.repaint();
		
		JPanel top = new JPanel();
		JPanel bottom = new JPanel();
		JButton calc = new JButton("Calculate Optimal Home Design");
		JButton quit2 = new JButton("Quit");
		quit2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
		
		JLabel moneyLab = new JLabel("Desired investment: ");
		final JTextField moneyField = new JTextField(5);

		top.add(moneyLab);
		top.add(moneyField);
		bottom.add(calc);
		bottom.add(quit2);
		conPane.setLayout(new BorderLayout());
		conPane.setSize(200, 300);
		conPane.add(top, BorderLayout.NORTH);
		conPane.add(bottom, BorderLayout.CENTER);
		final Home hom = this;
		
		calc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
		
		try{
			money = Integer.parseInt(moneyField.getText());
			if(money < 0 ) throw new NegativeNumberException("negative");
			Exhaustive exhaust = new Exhaustive(hom);	
		}
		catch(NumberFormatException c){
			JOptionPane.showMessageDialog(new JPanel(),"You have entered an invalid number, please try again");
		}
		catch(NullPointerException a){
			JOptionPane.showMessageDialog(new JPanel(),"Please enter a number for all values");
		}
		catch(NegativeNumberException n){
			JOptionPane.showMessageDialog(new JPanel(), "No negative numbers are allowed");
		}
		//catch negative number exceptions
		
		
            }
            });
			
            
	}
	
	public double calcHeatingCost(int sqft){
		return heatingPerSqFt *sqft;
	}
	
	public int getInitialMoney(){
		return money;
	}
	
	//generates arraylist of materials that are used in both roofs and walls
	public  ArrayList<InsulMat> createArrayList(){
		ArrayList<InsulMat> m = new ArrayList<InsulMat>();
		m.add(xps);
		m.add(eps);
		m.add(polyiso);
		m.add(sprayFoamLD);
		m.add(sprayFoamHD);
		m.add(cellulSpray);
		m.add(cellulBlown);
		m.add(fiberglassPack);
		m.add(fiberglassBatt);
		m.add(mineralWool);
		m.add(fiberboard);
		m.add(particleBoard);
		m.add(brick);
		m.add(concrete);
		m.add(stucco);
		m.add(cement);
		m.add(ply);
		m.add(sheetrock);
		m.add(woodSide);
		m.add(vinylSide);
		m.add(housewrap);
		m.add(nothing);

		return m;

	}

	//adds roof materials to argument ArrayList of InsulMat
	public  ArrayList<InsulMat> addRoofMats(ArrayList<InsulMat> m){
		m.add(cellulAttic);
		m.add(woodShing);
		m.add(asphaltShing);
		m.add(carpet);
		m.add(finishWood);
		m.add(halfInSheath);
		m.add(oneInSheath);
		

		return m;
	}
	
	public void manualJ(){
		 calcQWind();
		 calcQDoor();
		 calcQWall();
		 calcQRoof();
		 calcQFloor();
		 heatLoad = (QWind + QDoor + QWall + QRoof + QFloor)/1200; //units of tons
	}
			
	public void calcQWind(){
		double CWind = 0;
		if (panes == 2){
			CWind = .9;
		}
		else if (panes == 3){
			CWind = .6;
		}
		
		QWind = nuWindows*CWind*windowArea;
	}
	public void calcQDoor(){
		double CDoor = 2.2;
		QDoor = nuDoors*CDoor*doorArea;
	}
	public void calcQWall(){
		int tempDiff = 70; //temperature difference between winter air & home
		double uFact;
		netWallArea = grossWallArea - windowArea * nuWindows - nuDoors*2880/144.0; //average door size is 2880 in^2
		double totalR = layer1Mat.getR()+layer2Mat.getR()+layer3Mat.getR()+layer4Mat.getR();
		if (!(layer1Mat == brick || layer1Mat == stucco || layer1Mat == cement || layer1Mat == concrete)){
			uFact = -.033*Math.log(totalR)+.1522;
		}
		else uFact = -.038*Math.log(totalR)+.2202;
		
		QWall = tempDiff*uFact*netWallArea;
	}
	
	public void calcQRoof(){
		int tempDiff = 70; //temperature difference between winter air & home
		double totalR = layer1MatRoof.getR()+layer2MatRoof.getR()+layer3MatRoof.getR()+layer4MatRoof.getR();
		double uFact = -.084*Math.log(totalR)+.3078;
		QRoof = tempDiff*uFact*roofArea;
	}
	public void calcQFloor(){
		int tempDiff = 70; //temperature difference between winter air & home
		double totalR = layer1MatFloor.getR()+layer2MatFloor.getR()+layer3MatFloor.getR()+layer4MatFloor.getR();
		double uFact = -.00005*Math.pow(totalR, 3)+.0022*Math.pow(totalR, 2)-.0387*totalR+.2817;
		QFloor = tempDiff*uFact*floorArea;
	}
	
	public ArrayList<InsulMat> getWallArray(){
		return wallMats;
	}
	public ArrayList<InsulMat> getFloorArray(){
		return floorMats;
	}
	public ArrayList<InsulMat> getRoofArray(){
		return roofMats;
	}
	
	public void setInsulObject(InsulMat perm, InsulMat drop){
		perm = drop;
	}
	
	public void setWall1(InsulMat a){
		layer1Mat = a;
	}
	public void setWall2(InsulMat a){
		layer2Mat = a;
	}
	public void setWall3(InsulMat a){
		layer3Mat = a;
	}
	public void setWall4(InsulMat a){
		layer4Mat = a;
	}
	public void setRoof1(InsulMat a){
		layer1MatRoof = a;
	}
	public void setRoof2(InsulMat a){
		layer2MatRoof = a;
	}
	public void setRoof3(InsulMat a){
		layer3MatRoof = a;
	}
	public void setRoof4(InsulMat a){
		layer4MatRoof = a;
	}
	public void setFloor1(InsulMat a){
		layer1MatFloor = a;
	}
	public void setFloor2(InsulMat a){
		layer2MatFloor = a;
	}
	public void setFloor3(InsulMat a){
		layer3MatFloor = a;
	}
	public void setFloor4(InsulMat a){
		layer4MatFloor = a;
	}
	public InsulMat getWall1(){
		return layer1Mat;
	}
	public InsulMat getWall2(){
		return layer2Mat;
	}
	public InsulMat getWall3(){
		return layer3Mat;
	}
	public InsulMat getWall4(){
		return layer4Mat;
	}
	public InsulMat getRoof1(){
		return layer1MatRoof;
	}
	public InsulMat getRoof2(){
		return layer2MatRoof;
	}
	public InsulMat getRoof3(){
		return layer3MatRoof;
	}
	public InsulMat getRoof4(){
		return layer4MatRoof;
	}
	public InsulMat getFloor1(){
		return layer1MatFloor;
	}
	public InsulMat getFloor2(){
		return layer2MatFloor;
	}
	public InsulMat getFloor3(){
		return layer3MatFloor;
	}
	public InsulMat getFloor4(){
		return layer4MatFloor;
	}
	public int getMoney(){
		return money;
	}
	public double getWallArea(){
		return netWallArea;
	}
	public double getFloorArea(){
		return floorArea;
	}
	public double getRoofArea(){
		return roofArea;
	}

	public double getFlCost(){
		return floorCost;
	}
	public double getRfCost(){
		return roofCost;
	}
	public double getWlCost(){
		return wallCost;
	}
	public double getWindCost(){
		return windowCost;
	}
	public double getHeatSysCost(){
		return heatSysCost;
	}
	public int getTriplePaneWindCost(){
		return (int) (getWindCost(9,3));
	}
	
	public int checkVentNeeds(){
		//check for number of people 
		int people = 2+nuRooms;
		int floorSpace = (int) (nuFloors*floorArea);
		//the airflow required per person based on occupancy
		int airflowPeep = 5; //cfm
		//the airflow required per square foot
		double airflowFoot = .06; //cfm
		return (int) (people*airflowPeep+floorSpace*airflowFoot);
		
	}
	public int getGeothermCost(){
		int geo = 7500; //$ per ton of heating for geo system
		return (int) (geo*heatLoad);
	}
	public int getHRVCost(){
		int ventCFM = checkVentNeeds();
		int ventCost;
		if (ventCFM <= 125) ventCost = 1707;
		else if (ventCFM <= 175) ventCost = 2113;
		else ventCost = 2746;
		return ventCost;
	}
	public double getHeatLoad(){
		return heatLoad;
	}
	public double getGeoEnergySave(){
		//difference in energy efficiency between standard heating system & geothermal
		double percentDiff = 3.53; //geothermal 353% more efficient
		return heatLoad*1200/percentDiff;
	}
	//takes in the users area and gets the energy saved with their window choice
	public double calcUserWindEnergySave(double areaUser, int pane){
		double windR; //window R value
		double tempDiff = 70; //temperature difference across window in winter
		double time = 8760; //hours in a year
		if(pane!=3){
			if (areaUser <=6) windR = 1/.32;
			else if (areaUser <=9) windR = 1/.32;
			else if (areaUser <=12) windR = 1/.34;
			else windR = 1/.34;
		}
		else windR = 1/.21;
		return time*tempDiff*areaUser*nuWindows/windR;
	}
	
}
