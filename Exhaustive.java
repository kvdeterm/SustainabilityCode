public class Exhaustive {
	protected Home home;
	public Exhaustive(Home h) {
		
		home = h;
		// Money to spend on upgrades PLUS the initial cost they put towards their selected options
		double money = home.getMoney() + home.getCostofInputs();
		
		//cost per square foot for all material options
		double costHousewrap = home.housewrap.getCost();
		double costFiber = home.fiberboard.getCost();
		double costParticle = home.particleBoard.getCost();
		double costPly = home.ply.getCost();
		double costSheetrock = home.sheetrock.getCost();
		double costEPS = home.eps.getCost();
		double costXPS = home.xps.getCost();
		double costPoly = home.polyiso.getCost();
		double costFoam = home.sprayFoamHD.getCost();
		double costCellul = home.cellulBlown.getCost();
		double costFiberglass = home.fiberglassBatt.getCost();
		double costMineral = home.mineralWool.getCost();
		double costCarpet = home.carpet.getCost();
		double costConc = home.concrete.getCost();
		double costWoodSide = home.woodSide.getCost();
		double costVinylSide = home.vinylSide.getCost();
		double costShing = home.asphaltShing.getCost();
		
		//cost for non-insulation choices
		double homeWindowCost = home.getWindCost();
		double homeHeatSysCost = home.getHeatSysCost();
		double tripleWindowCost = home.getTriplePaneWindCost();
		double HRVCost = home.getHRVCost();
		double geoCost = home.getGeothermCost();
		
		//areas 
		int flArea = (int) (home.getFloorArea());
		int wlArea = (int) (home.getWallArea());
		int rfArea = (int) (home.getRoofArea());

		//energy savings for each of the insulation choices, Wall
		int enHousewrapW = home.housewrap.getEn(wlArea);
		int enFiberW = home.fiberboard.getEn(wlArea);
		int enParticleW = home.particleBoard.getEn(wlArea);
		int enPlyW = home.ply.getEn(wlArea);
		int enSheetrockW = home.sheetrock.getEn(wlArea);
		int enEPSW = home.eps.getEn(wlArea);
		int enXPSW = home.xps.getEn(wlArea);
		int enPolyW = home.polyiso.getEn(wlArea);
		int enFoamW = home.sprayFoamHD.getEn(wlArea);
		int enCellulW = home.cellulBlown.getEn(wlArea);
		int enFiberglassW = home.fiberglassBatt.getEn(wlArea);
		int enMineralW = home.mineralWool.getEn(wlArea);
		int enConcW = home.concrete.getEn(wlArea);
		int enWoodSideW = home.woodSide.getEn(wlArea);
		int enVinylSideW = home.vinylSide.getEn(wlArea);

		//energy savings for each of the insulation choices, Floor
		int enHousewrapF = home.housewrap.getEn(flArea);
		int enFiberF = home.fiberboard.getEn(flArea);
		int enParticleF = home.particleBoard.getEn(flArea);
		int enPlyF = home.ply.getEn(flArea);
		int enSheetrockF = home.sheetrock.getEn(flArea);
		int enEPSF = home.eps.getEn(flArea);
		int enXPSF = home.xps.getEn(flArea);
		int enPolyF = home.polyiso.getEn(flArea);
		int enFoamF = home.sprayFoamHD.getEn(flArea);
		int enCellulF = home.cellulBlown.getEn(flArea);
		int enCarpetF = home.carpet.getEn(flArea);


		//energy savings for each of the insulation choices, Roof
		int enHousewrapR = home.housewrap.getEn(rfArea);
		int enFiberR = home.fiberboard.getEn(rfArea);
		int enParticleR = home.particleBoard.getEn(rfArea);
		int enPlyR = home.ply.getEn(rfArea);
		int enSheetrockR = home.sheetrock.getEn(rfArea);
		int enEPSR = home.eps.getEn(rfArea);
		int enXPSR = home.xps.getEn(rfArea);
		int enPolyR = home.polyiso.getEn(rfArea);
		int enFoamR = home.sprayFoamHD.getEn(rfArea);
		int enCellulR = home.cellulBlown.getEn(rfArea);
		int enFiberglassR = home.fiberglassBatt.getEn(rfArea);
		int enMineralR = home.mineralWool.getEn(rfArea);
		int enConcR = home.concrete.getEn(rfArea);
		int enShingR = home.asphaltShing.getEn(rfArea);

		//energy savings for non-insulation items
		int enGeo = (int) (home.getGeoEnergySave());
		//65% efficient HRV, accounts for 30% of total heating load, *1200 to get to Btu
		int enHRV = (int) (home.getHeatLoad()*1200*.65*.3);
		int enWindUser = (int) (home.calcUserWindEnergySave(home.windowArea, home.panes));
		int enWindTriple = (int) (home.calcUserWindEnergySave(9, 3));
		
		// Number of sets of items
		int numSets = 15;

		// Initialize data sets
		Set[] data = new Set[numSets];
		
		//new Set(number of items, Name);
		data[0] = new Set(1, "Floor Layer 1");
		data[0].set(new double[] {costHousewrap*flArea},new int[] {enHousewrapF}); 
		data[0].setNames(new String[] {"Housewrap"});

		data[1] = new Set(4, "Floor Layer 2");
		data[1].set(new double[] {costFiber*flArea, costParticle*flArea, costPly*flArea, costSheetrock*flArea},new int[] {enFiberF,enParticleF,enPlyF,enSheetrockF}); 
		data[1].setNames(new String[] {"Fiberboard", "Particle Board", "Plywood", "Sheetrock"});
		
		data[2] = new Set(5, "Floor Layer 3");
		data[2].set(new double[] {costEPS*flArea, costXPS*flArea, costPoly*flArea, costFoam*flArea, costCellul*flArea},new int[] {enEPSF,enXPSF,enPolyF,enFoamF,enCellulF}); 
		data[2].setNames(new String[]{"EPS", "XPS", "Polyisocyanurate", "Spray Foam HD Polyurethane", "Cellulose Blown"});
		
		data[3] = new Set(1, "Floor Layer 4");
		data[3].set(new double[] {costCarpet*flArea},new int[] {enCarpetF}); //carpet
		data[3].setNames(new String[]{"Carpet"});

		data[4] = new Set(3, "Walls Layer 1");
		data[4].set(new double[] {costConc*wlArea, costWoodSide*wlArea, costVinylSide*wlArea},new int[] {enConcW,enWoodSideW,enVinylSideW}); 
		data[4].setNames(new String[]{"Concrete", "Wood Siding", "Vinyl Siding"});
		
		data[5] = new Set(4, "Walls Layer 2");
		data[5].set(new double[] {costFiber*wlArea, costPly*wlArea, costSheetrock*wlArea, costHousewrap*wlArea},new int[] {enFiberW,enPlyW,enSheetrockW,enHousewrapW}); 
		data[5].setNames(new String[] {"Fiberboard", "Plywood", "Sheetrock", "Housewrap"});
		
		data[6] = new Set(7, "Walls Layer 3");
		data[6].set(new double[] {costEPS*wlArea, costXPS*wlArea, costPoly*wlArea, costFoam*wlArea, costCellul*wlArea, costFiberglass*wlArea, costMineral*wlArea},
						new int[] {enEPSW,enXPSW,enPolyW,enFoamW,enCellulW,enFiberglassW,enMineralW}); 
		data[6].setNames(new String[]{"EPS", "XPS", "Polyisocyanurate", "Spray Foam HD Polyurethane", "Cellulose Blown", "Fiberglass Batts", "Mineral Wool Batts"});
		
		data[7] = new Set(4, "Walls Layer 4");
		data[7].set(new double[] {costFiber*wlArea, costParticle*wlArea, costPly*wlArea, costSheetrock*wlArea},new int[] {enFiberW,enParticleW,enPlyW,enSheetrockW}); 
		data[7].setNames(new String[] {"Sheetrock", "Fiberboard", "Particle Board", "Plywood"});
		

		data[8] = new Set(2, "Roof Layer 1");
		data[8].set(new double[] {costShing*rfArea,costConc*rfArea},new int[] {enShingR,enConcR}); 
		data[8].setNames(new String[] {"Asphalt Shingles", "Concrete"});

		data[9] = new Set(4, "Roof Layer 2");
		data[9].set(new double[] {costFiber*rfArea, costPly*rfArea, costSheetrock*rfArea, costHousewrap*rfArea},new int[] {enFiberR,enPlyR,enSheetrockR,enHousewrapR}); 
		data[9].setNames(new String[] {"Fiberboard", "Plywood", "Sheetrock", "Housewrap"});
		
		data[10] = new Set(7, "Roof Layer 3");
		data[10].set(new double[] {costEPS*rfArea, costXPS*rfArea, costPoly*rfArea, costFoam*rfArea, costCellul*rfArea, costFiberglass*rfArea, costMineral*rfArea},
						new int[] {enEPSR,enXPSR,enPolyR,enFoamR,enCellulR,enFiberglassR,enMineralR}); 
		data[10].setNames(new String[]{"EPS", "XPS", "Polyisocyanurate", "Spray Foam HD Polyurethane", "Cellulose Blown", "Fiberglass Batts", "Mineral Wool Batts"});
		
		data[11] = new Set(4, "Roof Layer 4");
		data[11].set(new double[] {costFiber*rfArea, costParticle*rfArea, costPly*rfArea, costSheetrock*rfArea},new int[] {enFiberR,enParticleR,enPlyR,enSheetrockR}); 
		data[11].setNames(new String[] {"Sheetrock", "Fiberboard", "Particle Board", "Plywood"});

		data[12] = new Set(2, "Windows");
		data[12].set(new double[] {homeWindowCost,tripleWindowCost},new int[] {enWindUser,enWindTriple}); 
		data[12].setNames(new String[] {"Double Pane Window", "Triple Pane Window"});

		data[13] = new Set(2, "HRV");
		data[13].set(new double[] {0,HRVCost},new int[] {0,enHRV}); 
		data[13].setNames(new String[] {"Standard Ventilation", "Heat Recovery Ventilation"});

		data[14] = new Set(2, "Geothermal");
		data[14].set(new double[] {homeHeatSysCost,geoCost},new int[] {0,enGeo}); 
		data[14].setNames(new String[] {"Standard Heating System", "Geothermal System"});
		
		//call exhaustive search 
		int[] solution = exhaustiveSearch(data, money);

		//print results!		
		System.out.println("Optimal solution determined to be:");
		for (int i = 0; i < numSets; ++i) {

			System.out.println(data[i].print(solution[i]));
		}
	}

	public static int[] exhaustiveSearch(Set[] data, double money) {
		//setMax simply stores the number of items in each set
		//faster cache lookups due to data organization

		int numSets = data.length;

		int[] setMax = new int[numSets];
		int[] solution = new int[numSets];
	
		int total = 1;

		for (int i = 0; i < numSets; ++i) {
			setMax[i] = data[i].n; //setMax holds the total number of combinations possible for each Set
			total *= data[i].n; //gives the total number of combinations possible
		}

		//arrays which store the cost and savings for each combination
		int[] savings = new int[total];
		int[] cost = new int[total];

		solution = calculateSavings(data, money, total);

		//find maximum savings which does not exceed our budget
		int max = -1;
		int max_ind = -1;

		for (int i = 0; i < total; ++i) {
			if (savings[i] > max && cost[i] <= money) {
				max = savings[i];
				max_ind = i;
			}
		}

		//algorithm to determine which set of indices gave us that solution
		return solution;
	}

	public static int[] calculateSavings(Set[] data, double money, long total) {

		int maxSavings = -1;
		int cost = 0;
		int savings = 0;
		int numSets = data.length;
		int index = 0;
		int[] indices = new int[numSets];
		int[] solution = new int[numSets];
		
		for (int i = 0; i < numSets; ++i) {
			indices[i] = 0;
		}

		//for all combinations
		//starting with option 0 for all sets
		//loops approximately 22M times with my data
		while (index < total) {

			cost = 0;
			savings = 0;
			//sum the cost and savings for all items in this case
			for (int i = 0; i < numSets; ++i) {

				cost += data[i].cost[indices[i]];
				savings += data[i].savings[indices[i]];

			}

			if (savings > maxSavings && cost <= money) {
				
				maxSavings = savings;
				
				for (int j = 0; j < numSets; ++j) {
					solution[j] = indices[j];
				}
			}
			
			//update the indices to the next combinations
			incrementIndices(data, indices); 
			index++;
		}
		
		return solution;
	}

	public static void incrementIndices(Set[] data, int[] indices) {

		//update the indices from last to first
		int current = indices.length - 1;

		while (current >= 0) {

			//update current index
			indices[current]++; 

			if (indices[current] == data[current].n) {

				//if we exceeded the bounds of the current set	
				indices[current] = 0;
				//reset the index of that set and move to the next one
				current--;	
			}

			else
				//successful update
				return;
		}
	}

}

class Set {

	//array of costs for the items that have been added to this Set
	double[] cost; 
	//corresponding energy savings for the items that have been added to this set
	int[] savings; 
	//number of items in the Set
	int n; 
	String name;
	String[] names;

	public Set(int n, String name) {
		this.n = n;
		this.name = name;
		this.names = new String[n];
		for (int i = 0; i < n; ++i) {
			this.names[i] = "No information given";
		}
	}

	public void set(double[] cost, int[] savings) {
		this.cost = cost;
		this.savings = savings;
	}

	public void setNames(String[] names) {
		this.names = names;
	}
	public String print(int i) {
		return name + ": " + names[i];
	}
}