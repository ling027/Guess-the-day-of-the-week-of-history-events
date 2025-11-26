package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.io.FileWriter;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JTextField;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int Player1Guess;
	private int Player2Guess;
	private int currentIndex = 0;
	public FileInputStream event = null;
	private Scanner scnr = null;
	private JTextField Turn;
	private JTextField Result;
	private JTextField Player1Choice;
	private JTextField Player2Choice;
	private FileWriter logFileWriter;
	private JButton Monday;
	private JButton Tuesday;
	private JButton Wednesday;
	private JButton Thursday;
	private JButton Friday;
	private JButton Saturday;
	private JButton Sunday;

	 private void determineWinner(int player1, int plaer2,List<HistoryEvent> Array, int curr, FileWriter log) {
		 int CurrentDay = (java.time.DayOfWeek.from(java.time.LocalDate.now()).getValue() % 7) + 1;
			int temp1  = (Player1Guess - CurrentDay + 7) % 7;
			int temp2  = (Player2Guess - CurrentDay + 7) % 7;
			Integer correctDay = Integer.valueOf(Array.get(curr).getWeekDay());
			int temp3  = (correctDay - CurrentDay + 7) % 7;
			
			int Player1Diff = Math.abs(temp3 - temp1);
			int Player2Diff = Math.abs(temp3 - temp2);
			
			if(Player1Diff < Player2Diff) {
				Result.setText("Player 1 won!");
				try {
					log.write("Event: " + Array.get(curr).getName()+"\n"
							  + "Player1 Guess: " + Player1Guess + " Player 2 Guesss: " + Player2Guess+"\n"
							  + "Result: " + Result.getText() + "\n");
					log.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if(Player1Diff > Player2Diff) {
				Result.setText("Player 2 won!");
				try {
					log.write("Event: " + Array.get(curr).getName()+"\n"
							  + "Player1 Guess: " + Player1Guess + " Player 2 Guesss: " + Player2Guess+"\n"
							  + "Result: " + Result.getText() + "\n");
					log.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				Result.setText("Tie");
				try {
					log.write("Event: " + Array.get(curr).getName()+"\n"
							  + "Player1 Guess: " + Player1Guess + " Player 2 Guesss: " + Player2Guess+"\n"
							  + "Result: " + Result.getText() + "\n");
					log.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	 }
	 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game frame = new Game();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Game() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Game.class.getResource("/resources/屏幕截图 2023-09-09 231707.png")));
		setTitle("Day_Guessing_Game");
		setBackground(new Color(255, 255, 128));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 599, 385);
		contentPane = new JPanel();
		contentPane.setBounds(new Rectangle(255, 0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea Problem = new JTextArea();
		Problem.setBounds(10, 10, 565, 182);
		contentPane.add(Problem);
		
		Turn = new JTextField();
		Turn.setBounds(238, 206, 96, 19);
		contentPane.add(Turn);
		Turn.setColumns(10);
		Turn.setEditable(false);
		
		try {
			event = new FileInputStream("Dates");
			scnr = new Scanner(event);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
            logFileWriter = new FileWriter("C:\\Users\\lingj\\eclipse-workspace\\DayOfTheWeek\\log", true);
        } catch (Exception e) {
       
            e.printStackTrace();
        }
		
		List<HistoryEvent> Events = new ArrayList<>();
		while(scnr.hasNextLine()) {
			String info = scnr.nextLine();
			String[] parts = info.split(";");
			Events.add(new HistoryEvent(parts[0],parts[1],parts[2],parts[3],parts[4]));
		}
		
		JButton NewGame = new JButton("New Game");
		NewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentIndex < Events.size()) {
				Turn.setText("Player 1");
				Problem.setText(Events.get(currentIndex).getName() + " took place on " + Events.get(currentIndex).getMonth() 
								+ "/" + Events.get(currentIndex).getDay() + "/" + Events.get(currentIndex).getYear()
								+ ". Can you guess which weekday it occured?");
				}
				else {
					NewGame.setEnabled(false);
					Monday.setEnabled(false);
					Tuesday.setEnabled(false);
					Wednesday.setEnabled(false);
					Thursday.setEnabled(false);
					Friday.setEnabled(false);
					Saturday.setEnabled(false);
					Sunday.setEnabled(false);
					Problem.setText("Game Over!");
					Turn.setText("");
				}
				if(Result.getText()!=("")) {
					Result.setText("");
					Player1Choice.setText("");
					Player2Choice.setText("");
				}
			}
		});
		
		Monday = new JButton("Mon");
		Monday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Turn.getText().equals("Player 1")) {
					Player1Guess = 1;
					Player1Choice.setText("Monday");
					Turn.setText("Player 2");
				}
				else if(Turn.getText().equals("Player 2")) {
					Player2Guess = 1;
					Player2Choice.setText("Monday");
					determineWinner(Player1Guess,Player2Guess,Events,currentIndex,logFileWriter);
					currentIndex++;
			}
			}
		});
		Monday.setBounds(60, 247, 85, 21);
		contentPane.add(Monday);
		
		Tuesday = new JButton("Tue");
		Tuesday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Turn.getText().equals("Player 1")) {
					Player1Guess = 2;
					Player1Choice.setText("Tuesday");
					Turn.setText("Player 2");
				}
				else if(Turn.getText().equals("Player 2")) {
					Player2Guess = 2;
					Player2Choice.setText("Tuesday");
					determineWinner(Player1Guess,Player2Guess,Events,currentIndex,logFileWriter);
					currentIndex++;
			}
			}
		});
		Tuesday.setBounds(177, 247, 85, 21);
		contentPane.add(Tuesday);
		
		Wednesday= new JButton("Wed");
		Wednesday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Turn.getText().equals("Player 1")) {
					Player1Guess = 3;
					Player1Choice.setText("Wednesday");
					Turn.setText("Player 2");
				}
				else if(Turn.getText().equals("Player 2")) {
					Player2Guess = 3;
					Player2Choice.setText("Wednesday");
					determineWinner(Player1Guess,Player2Guess,Events,currentIndex,logFileWriter);
					currentIndex++;
			}
			}
		});
		Wednesday.setBounds(300, 247, 85, 21);
		contentPane.add(Wednesday);
		
		Thursday = new JButton("Thur");
		Thursday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Turn.getText().equals("Player 1")) {
					Player1Guess = 4;
					Player1Choice.setText("Thursday");
					Turn.setText("Player 2");
				}
				else if(Turn.getText().equals("Player 2")) {
					Player2Guess = 4;
					Player2Choice.setText("Thursday");
					determineWinner(Player1Guess,Player2Guess,Events,currentIndex,logFileWriter);
					currentIndex++;
			}
			}
		});
		Thursday.setBounds(408, 247, 85, 21);
		contentPane.add(Thursday);
		
		Friday = new JButton("Fri");
		Friday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Turn.getText().equals("Player 1")) {
					Player1Guess = 5;
					Player1Choice.setText("Friday");
					Turn.setText("Player 2");
				}
				else if(Turn.getText().equals("Player 2")) {
					Player2Guess = 5;
					Player2Choice.setText("Friday");
					determineWinner(Player1Guess,Player2Guess,Events,currentIndex,logFileWriter);
					currentIndex++;
			}
			}
		});
		Friday.setBounds(120, 278, 85, 21);
		contentPane.add(Friday);
		
		Saturday = new JButton("Sat");
		Saturday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Turn.getText().equals("Player 1")) {
					Player1Guess = 6;
					Player1Choice.setText("Saturday");
					Turn.setText("Player 2");
				}
				else if(Turn.getText().equals("Player 2")) {
					Player2Guess = 6;
					Player2Choice.setText("Saturday");
					determineWinner(Player1Guess,Player2Guess,Events,currentIndex,logFileWriter);
					currentIndex++;
			}
			}
		});
		Saturday.setBounds(234, 278, 85, 21);
		contentPane.add(Saturday);
		
		Sunday = new JButton("Sun");
		Sunday.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Turn.getText().equals("Player 1")) {
					Player1Guess = 7;
					Player1Choice.setText("Sunday");
					Turn.setText("Player 2");
				}
				else if(Turn.getText().equals("Player 2")) {
					Player2Guess = 7;
					Player2Choice.setText("Sunday");
					determineWinner(Player1Guess,Player2Guess,Events,currentIndex,logFileWriter);
					currentIndex++;
			}
			}
		});
		Sunday.setBounds(358, 278, 85, 21);
		contentPane.add(Sunday);
		
		NewGame.setBounds(10, 206, 121, 21);
		contentPane.add(NewGame);
		
		JLabel lblNewLabel = new JLabel("Turn");
		lblNewLabel.setBounds(207, 210, 45, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Result");
		lblNewLabel_1.setBounds(398, 209, 45, 13);
		contentPane.add(lblNewLabel_1);
		
		Result = new JTextField();
		Result.setEditable(false);
		Result.setBounds(436, 206, 96, 19);
		contentPane.add(Result);
		Result.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Player 1: ");
		lblNewLabel_2.setBounds(10, 325, 65, 13);
		contentPane.add(lblNewLabel_2);
		
		Player1Choice = new JTextField();
		Player1Choice.setEditable(false);
		Player1Choice.setBounds(60, 322, 96, 19);
		contentPane.add(Player1Choice);
		Player1Choice.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Player 2:");
		lblNewLabel_3.setBounds(398, 325, 55, 13);
		contentPane.add(lblNewLabel_3);
		
		Player2Choice = new JTextField();
		Player2Choice.setEditable(false);
		Player2Choice.setBounds(453, 322, 96, 19);
		contentPane.add(Player2Choice);
		Player2Choice.setColumns(10);
		
		
	}
}
