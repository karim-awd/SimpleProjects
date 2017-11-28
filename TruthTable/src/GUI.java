import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class GUI {

	private JFrame frmTabularSolver;
	private JTextField textField_1;
	private JTextField textField_2;
	private final Action action = new SwingAction();
	private JTextArea textArea;

	private int rowsNum;
	private String truthTable[][];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmTabularSolver.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTabularSolver = new JFrame();
		frmTabularSolver.setTitle("Tabular Solver");
		frmTabularSolver.setBounds(100, 100, 1181, 620);
		frmTabularSolver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblTytle = new JLabel("Welcome To Truth Table Constractor");
		lblTytle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTytle.setFont(new Font("MS Reference Sans Serif", Font.BOLD, 14));

		JLabel lblNoOfVariables = new JLabel("Enter the variables used in the expression separated by spaces:");
		lblNoOfVariables.setFont(new Font("Tahoma", Font.BOLD, 14));

		JLabel lblNoOfMinterms = new JLabel(
				"Please Enter the Boolean Expression \n " + "Using : (and:&) , (or:|) ,(not:!) \n");
		lblNoOfMinterms.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNoOfMinterms.setVisible(false);

		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.LEFT);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setHorizontalAlignment(SwingConstants.LEFT);
		textField_2.setColumns(10);
		textField_2.setVisible(false);
		JLabel lblEx = new JLabel("Examble Format: (x&y)|(!z) :");
		lblEx.setFont(new Font("SansSerif", Font.BOLD, 12));
		lblEx.setVisible(false);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);

		JLabel label = new JLabel("(Ex: x y z)");

		JButton btnEnter = new JButton("Enter");
		btnEnter.setVisible(false);

		JButton btnNewButton = new JButton("Enter");

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				String variablesString = textField_1.getText();
				variablesString = variablesString.replaceAll(" +", " ").trim();
				String[] variables = variablesString.split("\\s");
				rowsNum = (int) Math.pow(2, variables.length);
				truthTable = new String[rowsNum + 1][variables.length];
				for (int i = 0; i < variables.length; i++) {
					truthTable[0][i] = variables[i];
					textArea.setText(textArea.getText() + (truthTable[0][i] + " "));
				}
				textArea.setText(textArea.getText() + "\n");

				for (int i = 0; i < rowsNum; i++) {
					String binary = Integer.toBinaryString(i);
					int x = 0;
					while (x < variables.length - binary.length()) {
						textArea.setText(textArea.getText() + "0 ");
						truthTable[i + 1][x] = "0";
						x++;
					}
					for (int ind = 0; ind < binary.length(); ind++) {
						truthTable[i + 1][x] = binary.substring(ind, ind + 1);
						x++;
					}
					binary = binary.replaceAll("", " ").trim();
					textArea.setText(textArea.getText() + binary + "\n");

				}

				lblNoOfMinterms.setVisible(true);
				lblEx.setVisible(true);
				textField_2.setVisible(true);
				btnEnter.setVisible(true);
			}
		});

		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				int check = rowsNum;

				String expression = textField_2.getText();
				EvaluateExpression evaluate = new EvaluateExpression(expression);
				String[][] finalTruthTable = evaluate.evaluate(truthTable);
				for (int indRow = 0; indRow < finalTruthTable.length; indRow++) {
					for (int indCol = 0; indCol < finalTruthTable[indRow].length; indCol++) {
						textArea.setText(textArea.getText() + finalTruthTable[indRow][indCol] );
						if (indRow > 0 && indCol == finalTruthTable[indRow].length - 1) {
							check = check - Integer.parseInt(finalTruthTable[indRow][indCol]);
						}
						if(indCol == finalTruthTable[indRow].length-2)
							textArea.setText(textArea.getText() + " |  " );
					}
					textArea.setText(textArea.getText() + "\n");
				}
				if (check == rowsNum) {
					textArea.setText(textArea.getText() + "Contradiction" + "\n");
				} else if (check == 0) {
					textArea.setText(textArea.getText() + "Tautology" + "\n");
				}
				check = rowsNum;

			}
		});
		
		JButton btnSave = new JButton("Save ");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrintWriter writer;
				try {
					writer = new PrintWriter("save.txt", "UTF-8");
					writer.println(textArea.getText());
					writer.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

		GroupLayout groupLayout = new GroupLayout(frmTabularSolver.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(49)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNoOfVariables)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(63)
											.addComponent(label, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
											.addGap(43)
											.addComponent(btnNewButton))
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(39)
											.addComponent(lblEx, GroupLayout.PREFERRED_SIZE, 378, GroupLayout.PREFERRED_SIZE))
										.addComponent(lblNoOfMinterms, GroupLayout.PREFERRED_SIZE, 484, GroupLayout.PREFERRED_SIZE)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 406, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(btnEnter)))
									.addPreferredGap(ComponentPlacement.RELATED, 106, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnSave)
									.addGap(18)))
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 459, GroupLayout.PREFERRED_SIZE)
							.addGap(63))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblTytle, GroupLayout.DEFAULT_SIZE, 1077, Short.MAX_VALUE)
							.addGap(35)))
					.addGap(2))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblTytle, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 448, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(78)
							.addComponent(lblNoOfVariables)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label)
								.addComponent(btnNewButton))
							.addPreferredGap(ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
							.addComponent(lblNoOfMinterms, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblEx)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnEnter))
							.addGap(82)
							.addComponent(btnSave)))
					.addContainerGap(32, Short.MAX_VALUE))
		);

		textArea = new JTextArea();
		textArea.setEnabled(false);
		textArea.setEditable(false);
		textArea.setFont(new Font("SansSerif", Font.BOLD, 27));
		scrollPane.setViewportView(textArea);
		frmTabularSolver.getContentPane().setLayout(groupLayout);

		JMenuBar menuBar = new JMenuBar();
		frmTabularSolver.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Help");
		menuBar.add(mnNewMenu);

		JMenuItem mntmAboutUs = new JMenuItem("About Us!");
		mntmAboutUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				About about = new About();
				about.setModal(true);
				about.setVisible(true);
			}
		});
		mnNewMenu.add(mntmAboutUs);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		public void actionPerformed(ActionEvent e) {
		}
	}
}
