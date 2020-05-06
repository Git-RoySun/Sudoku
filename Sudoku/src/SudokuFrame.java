import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

public class SudokuFrame extends JFrame implements ActionListener {

	private Sudoku game = new Sudoku();
	private JButton btnStart = new JButton("Start");
	private JButton btnClear = new JButton("Clear");
	private JButton btnUndo = new JButton("Undo");
	private JButton btnSave = new JButton("Save");
	private JTextField[][] grids= new JTextField[9][9];
	private JTextField fldFile= new JTextField(15);
	private JPanel buttonPanel = new JPanel();
	private JPanel filePanel = new JPanel();
	private JPanel gridPanel = new JPanel();
	private JPanel aboutPanel = new JPanel();
	private JPanel helpPanel = new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu sudokuMenu = new JMenu("Menu");
	private JMenuItem undoMenuItem = new JMenuItem("Undo");
	private JMenuItem saveMenuItem = new JMenuItem("Save");
	private JMenuItem helpMenuItem = new JMenuItem("Help");
	private JMenuItem aboutMenuItem = new JMenuItem("About");
	private JMenuItem exitMenuItem = new JMenuItem("Exit");


	public static void main(String[] args) {
		SudokuFrame frame = new SudokuFrame();
		frame.setTitle("Sudoku");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	public void play(){
		getContentPane().removeAll();

		updateGrid();
		getContentPane().add(gridPanel);
		gridPanel.setLayout(null);
		buttonPanel.removeAll();
		buttonPanel.add(btnUndo);
		buttonPanel.add(btnSave);
		buttonPanel.setBounds(0, 216, 216, 55);
		getContentPane().add(buttonPanel);
		setBounds(0,0,240,315);
		setLocationRelativeTo(null);
		setJMenuBar(menuBar);
		revalidate();
	}
	public SudokuFrame() {
		aboutMenuItem.addActionListener(this);
		helpMenuItem.addActionListener(this);
		saveMenuItem.addActionListener(this);
		undoMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);
		sudokuMenu.add(saveMenuItem);
		sudokuMenu.add(undoMenuItem);
		sudokuMenu.add(helpMenuItem);
		sudokuMenu.add(aboutMenuItem);
		sudokuMenu.add(exitMenuItem);
		menuBar.add(sudokuMenu);

		JLabel lblFile = new JLabel("File: ");
		btnStart.addActionListener(this);
		btnClear.addActionListener(this);
		btnSave.addActionListener(this);
		btnUndo.addActionListener(this);

		setBounds(100, 100, 350, 125);
		gridPanel.setBounds(0, 0, 220, 220);
		filePanel.setBounds(0, 0, 350, 35);
		filePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		filePanel.add(lblFile);
		filePanel.add(fldFile);
		buttonPanel.setBounds(0, 35, 350, 55);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		buttonPanel.add(btnStart);
		buttonPanel.add(btnClear);
		getContentPane().setLayout(null);
		getContentPane().add(filePanel);
		getContentPane().add(buttonPanel);

		aboutPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		aboutPanel.setLayout(gridBagLayout);

		JLabel lblTitle = new JLabel("Sudoku Game");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.insets = new Insets(0, 0, 5, 0);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		aboutPanel.add(lblTitle, gbc_lblTitle);

		JLabel lblAuthor = new JLabel("Roy Sun");
		GridBagConstraints gbc_lblAuthor = new GridBagConstraints();
		gbc_lblAuthor.insets = new Insets(0, 0, 5, 0);
		gbc_lblAuthor.gridx = 0;
		gbc_lblAuthor.gridy = 1;
		aboutPanel.add(lblAuthor, gbc_lblAuthor);

		JLabel lblCopyright = new JLabel("2020");
		GridBagConstraints gbc_lblCopyright = new GridBagConstraints();
		gbc_lblCopyright.insets = new Insets(0, 0, 5, 0);
		gbc_lblCopyright.gridx = 0;
		gbc_lblCopyright.gridy = 2;
		aboutPanel.add(lblCopyright, gbc_lblCopyright);

		JLabel lblCompany = new JLabel("Heritage College");
		GridBagConstraints gbc_lblCompany = new GridBagConstraints();
		gbc_lblCompany.gridx = 0;
		gbc_lblCompany.gridy = 3;
		aboutPanel.add(lblCompany, gbc_lblCompany);

		helpPanel.setLayout(new BorderLayout(0, 0));
		helpPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		JLabel lblRules = new JLabel("Sudoku Rules");
		lblRules.setHorizontalAlignment(SwingConstants.CENTER);
		lblRules.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblRules.setAlignmentY(Component.TOP_ALIGNMENT);
		helpPanel.add(lblRules, BorderLayout.NORTH);

		JTextPane txtpnRules = new JTextPane();
		txtpnRules.setEditable(false);
		txtpnRules.setText("Goal: To complete the 9x9 grid with numbers\r\n1. Every column, row and 3x3 grids must have all numbers from 1 to 9.\r\n2. A number can only be present once on every row, column and 3x3 grid.\r\n3. You can only undo your last move once per turn.\r\n4. You can save the game and come back later at any time.\r\n5. Have fun!");
		helpPanel.add(txtpnRules, BorderLayout.CENTER);

	}
	public void updateGrid(){
		gridPanel.removeAll();
		for(int x=0; x<grids.length;x++){
			for(int y =0;y<grids[x].length;y++){
				int _row = x;
				int _col = y;
				grids[x][y] = new JTextField(1);
				grids[x][y].setBounds(y*24+5,x*24,24,24);
				grids[x][y].setFont(new Font("Arial", Font.PLAIN, 18));
				grids[x][y].setHorizontalAlignment(SwingConstants.CENTER);
				if(game.getBoard(x,y)!=0){
					grids[x][y].setText(String.valueOf(game.getBoard(x,y)));
					grids[x][y].setEditable(false);
				}
				else{
					grids[x][y].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if (grids[_row][_col].getText().matches("^[1-9]$")){
								switch (game.makeMove(_row, _col, Integer.parseInt(grids[_row][_col].getText()))) {
									case 0:
										break;
									case 1:
										grids[_col][_row].setText("");
										JOptionPane.showMessageDialog(getContentPane(), "The number is out of range.\nPlease enter a number between 1 and 9.", "Invalid Move", JOptionPane.ERROR_MESSAGE);
										break;
									case 2:
										grids[_col][_row].setText("");
										JOptionPane.showMessageDialog(getContentPane(), "There already is a number there\nPlease try again.", "Invalid Move", JOptionPane.ERROR_MESSAGE);
										break;
									case 3:
										grids[_col][_row].setText("");
										JOptionPane.showMessageDialog(getContentPane(), grids[_row][_col].getText() + " is already present on the same column.\nPlease try again.", "Invalid Move", JOptionPane.ERROR_MESSAGE);
										break;
									case 4:
										grids[_col][_row].setText("");
										JOptionPane.showMessageDialog(getContentPane(), grids[_row][_col].getText() + " is already present on the same row.\nPlease try again.", "Invalid Move", JOptionPane.ERROR_MESSAGE);
										break;
									case 5:
										grids[_col][_row].setText("");
										JOptionPane.showMessageDialog(getContentPane(), grids[_row][_col].getText() + " is already present on the same 3x3 grid.\nPlease try again.", "Invalid Move", JOptionPane.ERROR_MESSAGE);
										break;
								}
							if (game.checkWin()) {
								game.saveGame();
								JOptionPane.showMessageDialog(getContentPane(), "Congratulation, you have completed the puzzle", "You Won", JOptionPane.INFORMATION_MESSAGE);
							} else if (game.checkLose()) {
								game.saveGame();
								JOptionPane.showMessageDialog(getContentPane(), "Sorry, you have failed the puzzle", "You Lost", JOptionPane.INFORMATION_MESSAGE);
							}
							updateGrid();
						}
							else{
								grids[_col][_row].setText("");
								JOptionPane.showMessageDialog(getContentPane(), "The input must be a number between 1 and 9", "Invalid Move", JOptionPane.ERROR_MESSAGE);
							}
						}
					} );
				}
				gridPanel.add(grids[x][y]);
			}
		}
		revalidate();
		setBounds(0,0,240,316);
		setBounds(0,0,240,315);
		setLocationRelativeTo(null);
	}

	public void save(){
		if(game.saveGame())
			JOptionPane.showMessageDialog(getContentPane(), "Your game was successfully saved.", "Save", JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(getContentPane(), "An IO exception occured, your game was not saved.", "Invalid Save", JOptionPane.ERROR_MESSAGE);
	}
	public void undo(){
		if(game.undoMove())
			JOptionPane.showMessageDialog(getContentPane(), "You last move was successfully undone", "Undo", JOptionPane.INFORMATION_MESSAGE);
		else
			JOptionPane.showMessageDialog(getContentPane(), "You cannot undo your last move", "Invalid Undo", JOptionPane.ERROR_MESSAGE);
		updateGrid();
	}
	public void clear(){
		fldFile.setText("");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnStart){
			if(fldFile.getText().length()>0) {
				switch(game.initialise(fldFile.getText())){
					case 0:
						play();
						break;
					case 1:
						JOptionPane.showMessageDialog(getContentPane(), "The file format is incorrect.\nPlease try again.", "Invalid File", JOptionPane.ERROR_MESSAGE);
						clear();
						break;
					case 2:
						JOptionPane.showMessageDialog(getContentPane(), "The file was not found.\nPlease try again.", "Invalid File", JOptionPane.ERROR_MESSAGE);
						clear();
						break;
					case 3:
						JOptionPane.showMessageDialog(getContentPane(), "The puzzle has already been solved.\nPlease try another file.", "Invalid File", JOptionPane.ERROR_MESSAGE);
						clear();
						break;
					case 4:
						JOptionPane.showMessageDialog(getContentPane(), "The puzzle cannot be solved.\nPlease try another file.", "Invalid File", JOptionPane.ERROR_MESSAGE);
						clear();
						break;
				}
			}
			else {
				switch(game.initialise("sudoku.txt")){
					case 0:
						play();
						break;
					case 1:
						JOptionPane.showMessageDialog(getContentPane(), "The default file format is incorrect.\nPlease use another file.", "Invalid Default File", JOptionPane.ERROR_MESSAGE);
						clear();
						break;
					case 2:
						JOptionPane.showMessageDialog(getContentPane(), "The default file was not found.\nPlease use another file.", "Invalid Default File", JOptionPane.ERROR_MESSAGE);
						clear();
						break;
					case 3:
						JOptionPane.showMessageDialog(getContentPane(), "The default puzzle has already been solved.\nPlease try another file.", "Invalid Default File", JOptionPane.ERROR_MESSAGE);
						clear();
						break;
					case 4:
						JOptionPane.showMessageDialog(getContentPane(), "The default puzzle cannot be solved.\nPlease try another file.", "Invalid Default File", JOptionPane.ERROR_MESSAGE);
						clear();
						break;
				}
			}
		}
		else if(e.getSource()==btnClear){
			clear();
		}
		else if(e.getSource() == btnSave){
			save();
		}
		else if(e.getSource()==btnUndo){
			undo();
		}
		else if(e.getSource()== aboutMenuItem){
			JOptionPane.showMessageDialog(this,
					aboutPanel, "About",
					JOptionPane.PLAIN_MESSAGE);
		}
		else if(e.getSource()== helpMenuItem){
			JOptionPane.showMessageDialog(this,
					helpPanel, "Help",
					JOptionPane.PLAIN_MESSAGE);
		}
		else if(e.getSource()==undoMenuItem){
			undo();
		}
		else if(e.getSource()==saveMenuItem){
			save();
		}
		else if(e.getSource()==exitMenuItem){
			System.exit(0);
		}

	}
}
