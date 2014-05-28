
public abstract class InsulMat {
	//thickness of insulation material
	protected double thick; 
	//R value of insulation material at a given thickness
	protected double rValue; 
	// cost per square foot of insulation surface at a given thickness
	protected double costPerSqFt; 
	protected String name; 
	
	final int insideTemp = 75; //desired indoor temp
	final int outsideTemp = 5; //design winter temp in MA
	
	public void setThickness(double t) { 
		thick = t;
	}
	public void setRValue(double r) { 
		rValue = r;
	}
	public void setCost(double c) { 
		costPerSqFt = c;
	}
	public void setName(String n) { 
		name = n;
	}
	public int getEn(int area){
		int time = 8760; //hours
		//temp differential across barriers
		int tempDiff = insideTemp-outsideTemp;
		return (int) (time*tempDiff*area/this.rValue);
	}
	public String getName(){
		return name;
	}
	public double getThickness(){
		return thick;
	}
	public double getR(){
		return rValue;
	}
	public double getCost(){
		return costPerSqFt;
	}
}
