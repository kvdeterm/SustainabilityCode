
public class StandardInsulMat extends InsulMat{

	public StandardInsulMat(String nam, double valueR, double valueC, double thickness){
		super.name = nam; 
		super.rValue = valueR;
		super.costPerSqFt = valueC;
		super.thick = thickness;
		
	}
}
