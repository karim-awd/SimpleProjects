import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter the variables used in the expression as :x y z \nvariables are letters(i.e x ,Y ..)\n");
		String variablesString = scan.nextLine();
		variablesString = variablesString.replaceAll(" +", " ").trim();
		String []variables = variablesString.split("\\s");
		int rowsNum = (int) Math.pow(2,variables.length);
		String truthTable[][] = new String[rowsNum+1][variables.length];
		for(int i=0;i<variables.length;i++) {
			truthTable[0][i]=variables[i];
			System.out.print(truthTable[0][i]+" ");
		}
		
		System.out.println();
        for (int i=0; i<rowsNum; i++) {
        	String binary = Integer.toBinaryString(i);
        	int x = 0;
        	while(x<variables.length-binary.length()) {
        		System.out.print("0 ");
        		truthTable[i+1][x] = "0";
        		x++;
        	}
        	for(int ind =0;ind<binary.length();ind++) {
        		truthTable[i+1][x] = binary.substring(ind, ind+1);
        		x++;
        	}
        	binary = binary.replaceAll(""," ").trim();
        	System.out.println(binary);
        	
        }
		System.out.println("Please Enter the Boolean Expression \n"
				+ "Note : (and:&) , (or:|) ,(not:!) \nExamble Format: (x&y)|(!z) :");
		int x = 0;
		int check = rowsNum;
		while(x<10) {
			String expression = scan.nextLine();
			EvaluateExpression evaluate = new EvaluateExpression(expression);
			String[][] finalTruthTable  = evaluate.evaluate(truthTable);
			for(int indRow=0;indRow<finalTruthTable.length;indRow++) {
				for(int indCol = 0;indCol<finalTruthTable[indRow].length;indCol++) {
					System.out.print(finalTruthTable[indRow][indCol]+ " ");
					if(indRow>0&& indCol==finalTruthTable[indRow].length-1) {
						check = check-Integer.parseInt(finalTruthTable[indRow][indCol]);
					}
				}
				System.out.println();
			}
			if(check==rowsNum) {
				System.out.println("Contradiction");
			}
			else if(check==0) {
				System.out.println("Tautology");
			}
			check = rowsNum;
			x++;
		}
		
		scan.close();
	}

}