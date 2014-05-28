
public class VariableInsulMat extends InsulMat{
	protected double Rratio;
	protected double costRatio;
	
	public VariableInsulMat(String nam, double ratioR, double ratioC, double thickness){
		super.name = nam; 
		super.thick = thickness;
		Rratio = ratioR;
		costRatio = ratioC;
		setRValue(Rratio);
		setCost(costRatio);
	}
	
	public void setRValue(double ratio){
		super.rValue = ratio*super.thick;
	}
	
	public void setCost(double ratio){
		super.costPerSqFt = ratio*super.thick;
	}
}
