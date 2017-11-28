import java.util.Stack;

@SuppressWarnings("rawtypes")
public class EvaluateExpression {
	private Stack  operands = new Stack();
	private Stack  paren = new Stack();
	private Stack  operators = new Stack();
	private String expression;
	
	public EvaluateExpression(String expression) {
		this.expression = expression.replaceAll(" *","");
	}
	@SuppressWarnings("unchecked")
	public String[][] evaluate(String [][]truthTable) {
		
		String [][]finalResult =new String[truthTable.length][truthTable[0].length+1];
		finalResult[0][truthTable[0].length] = "f";
		for(int row=0 ;row<truthTable.length ;row++) {
			for(int col=0 ;col<truthTable[row].length ;col++) {
				finalResult[row][col] = truthTable[row][col];
			}
		}
		/*
		 * 1 - loop on rows starting from second row (1) 
		 * 2 - change each character with it's corresponding value	
		 *     a b c
		 *     0 0 0
		 *     0 0 1
		 *     0 1 0
		 *     0 1 1
		 *     1 0 0
		 *     1 0 1
		 *     1 1 0
		 *     1 1 1
		 * 3 - loop on expression performed characters    
		 */
		String expressionPerformed = expression;
		int operandsNum = 0;
		for(int row = 1; row<truthTable.length;row++) {
			expressionPerformed = expression;
			for(int col=0;col<truthTable[row].length;col++) {
				expressionPerformed = expressionPerformed.replace(truthTable[0][col], truthTable[row][col]);
			}
			for(int charIndex = 0;charIndex<expressionPerformed.length();charIndex++) {
				if(expressionPerformed.charAt(charIndex)=='(' ) {
					paren.push(expressionPerformed.charAt(charIndex));
					operandsNum =0;
				}
				else if(expressionPerformed.charAt(charIndex)==')' ) {
					paren.pop();
					while(!operators.isEmpty()) {
						if(operators.peek().equals('!')) {
							int one = Integer.parseInt((String)operands.pop());
							operators.pop();
							String result = String.valueOf(one^1);//Not
							operands.push(result);
						}
						else if(operators.peek().equals('&')) {
//							if(operandsNum >=2) {
								int one = Integer.parseInt((String)operands.pop()); 
								int two = Integer.parseInt((String)operands.pop());
								operators.pop();
								String result = String.valueOf(one&two);
								operands.push(result);
//								operandsNum-= 1;
//							}	
						}
						else {
//							if(operandsNum >=2) {
								int one = Integer.parseInt((String)operands.pop()); 
								int two = Integer.parseInt((String)operands.pop());
								operators.pop();
								String result = String.valueOf(one|two);
								operands.push(result);
//								operandsNum-= 1;
//							}
						}
				}
					operandsNum = 1;
				}
				else if(expressionPerformed.charAt(charIndex)=='|' ||
						expressionPerformed.charAt(charIndex)=='&' ||
						expressionPerformed.charAt(charIndex)=='!' ) {
					operators.push(expressionPerformed.charAt(charIndex));
				}
				else {
					operands.push(Character.toString(expressionPerformed.charAt(charIndex)));
					operandsNum++;
					if(!operators.isEmpty()&&operandsNum >=2 && operators.peek().equals('&')) {
						int one = Integer.parseInt((String)operands.pop()); 
						int two = Integer.parseInt((String)operands.pop());
						operators.pop();
						String result = String.valueOf(one&two);
						operands.push(result);
						operandsNum-= 1;
					}
					else if(!operators.isEmpty()&&operandsNum >=2 && operators.peek().equals('|')) {
						int one = Integer.parseInt((String)operands.pop()); 
						int two = Integer.parseInt((String)operands.pop());
						operators.pop();
						String result = String.valueOf(one|two);
						operands.push(result);
						operandsNum-= 1;
					}
					else if(!operators.isEmpty()&&operandsNum>=1 && operators.peek().equals('!')
							&& expressionPerformed.charAt(charIndex-1)=='!') {
						int one = Integer.parseInt((String)operands.pop());
						operators.pop();
						String result = String.valueOf(one^1);//Not
						operands.push(result);
						
					}
				}
			}
			while(!operators.isEmpty()) {
					int one = Integer.parseInt((String)operands.pop()); 
					int two = Integer.parseInt((String)operands.pop());
					char op = (char) operators.pop();
					String result = "";
					if(op=='&') {
						result = String.valueOf(one&two);		
					}
					else {
						result = String.valueOf(one|two);	
					}
					operands.push(result);
				
			}
			//System.out.println(operands.pop());
			finalResult[row][truthTable[0].length] = (String) operands.pop();
			operandsNum = 0;
		}
		
		return finalResult;
	}
}