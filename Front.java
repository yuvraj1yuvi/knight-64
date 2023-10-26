import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Front extends JFrame
{
	JFrame jf = new JFrame();
	JLabel background = new JLabel(new ImageIcon(getClass().getResource("front.png")));
	Font buttonFont = new Font("Calibri",Font.BOLD,20);
	private JButton play,howToPlay,about,exitButton;

	public Front()
	{
		jf.setTitle("KNIGHT TOUR");
		jf.setSize(700,700);
		jf.setVisible(true);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);		////centre window
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		play = new JButton("PLAY");
		howToPlay = new JButton("RULES");
		about =new JButton("ABOUT");
		exitButton = new JButton("EXIT");
		
		//set buttons position on window  
		play.setBounds(400,530,100,40);
		howToPlay.setBounds(400,590,100,40);
		about.setBounds(530,530,100,40);
		exitButton.setBounds(530,590,100,40);

		//set fonts of buttons 
		play.setForeground(Color.WHITE);
		play.setBackground(Color.BLACK);
		play.setFont(buttonFont);

		howToPlay.setFont(buttonFont);
		howToPlay.setForeground(Color.WHITE);
		howToPlay.setBackground(Color.BLACK);
		
		about.setFont(buttonFont);
		about.setForeground(Color.WHITE);
		about.setBackground(Color.BLACK);
		
		exitButton.setFont(buttonFont);
		exitButton.setForeground(Color.WHITE);
		exitButton.setBackground(Color.BLACK);

		//change cursor to hand cursor when moved on button
		play.setCursor(new Cursor(Cursor.HAND_CURSOR));
		howToPlay.setCursor(new Cursor(Cursor.HAND_CURSOR));
		about.setCursor(new Cursor(Cursor.HAND_CURSOR));
		exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		//add buttons on window
		jf.add(play);
		jf.add(howToPlay);
		jf.add(about); 
		jf.add(exitButton);

		jf.setLayout(new BorderLayout());
		background.setLayout(new FlowLayout());
		jf.add(background);

		play.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				newWindowChessBoard();
			}
		});

		howToPlay.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				newWindowHelp();
			}
		});

		about.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				newWindowAbout();
			}
		});
		exitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				jf.dispose();
			}
		});
	}

	public void newWindowChessBoard()
	{
		jf.dispose();
		new ChessBoard();
	}
	public void newWindowHelp()
	{
		jf.dispose();
		new Help();
	}
	public void newWindowAbout()
	{
		jf.dispose();
		new About();
	}

	public static void main(String[] args)
	{
		Front ft = new Front();
	}
}

class ChessBoard extends JFrame
{
	JFrame jf = new JFrame("Knight Tour Game");	
	int count=0;
	private Container contents;
	Random randomValue = new Random();		//for initialising Knight and random position
	private JButton[][] squares =new JButton[8][8];			//conponents
	private Color colorBlack = Color.BLACK;				//black square 
	private boolean[][] visitedMoves = new boolean[8][8];

	private ImageIcon Knight = new ImageIcon(getClass().getResource("Knight.png"));		//images

	int row=randomValue.nextInt(8);
	int col=randomValue.nextInt(8);

	public ChessBoard()
	{
		contents = jf.getContentPane();
		contents.setLayout(new GridLayout(8,8));

		//create event handlers :
		ButtonHandler buttonHandler = new ButtonHandler();

		//create and add board components 
		for (int i=0;i<8;i++)
		{
			for (int j=0;j<8;j++)
			{
				//initialise all squares with false value.
				visitedMoves[i][j]=false;
				
				squares[i][j]=new JButton();
				if((i+j)%2 != 0)
				{
					squares[i][j].setBackground(colorBlack);
				}
				contents.add(squares[i][j]);
				squares[i][j].addActionListener(buttonHandler);
			}
		}
		squares[row][col].setIcon(null);
		squares[row][col].setIcon(Knight);

		jf.setVisible(true);
		jf.setSize(700,700);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);		////centre window
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private boolean validMove(int i,int j)
	{
		int rowDelta = Math.abs(i-row);
		int colDelta = Math.abs(j-col);

		if((rowDelta == 1) && (colDelta==2))
		{
			return true;
		}
		if ((colDelta==1) && (rowDelta == 2))
		{
			return true;
		}

		return false;
	}

	private void processClick(int i, int j)
	{

		if(validMove(i,j)==false)
		{
			return;
		}
		else if(validMove(i,j)==true && visitedMoves[i][j]==true)
		{
			return;
		}

		else
		{
			squares[i][j].setBackground(Color.RED);
			squares[row][col].setIcon(null);
			squares[i][j].setIcon(Knight);
			row=i;
			col=j;
			visitedMoves[i][j]=true;
			count++;
			
			if(allPossibleMoves(row,col)==0)
			{
				jf.dispose(); // close window
				new Score(count);

			}
		}
	}

	public int allPossibleMoves(int row,int col)
	{
		int totalMoves=0,vm=0;

		int x[]={2,1,-1,-2,-2,-1,1,2};
		int y[]={1,2,2,1,-1,-2,-2,-1};

		for(int a=0;a<8;a++)
		{
			//position of knight after new move
			int c =	row + x[a];
			int d = col + y[a];

			//check for new position if it exist inside board or not
			if(c>=0 && c<8 && d>=0 && d<8)
			{
				totalMoves++;
				if(visitedMoves[c][d]==true)
				{
					vm++;
				}
			}
		}
		return totalMoves-vm; 
	}

	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Object source =e.getSource();
			for(int i=0;i<8;i++)
			{
				for(int j=0;j<8;j++)
				{
					if(source ==squares[i][j])
					{
						processClick(i,j);
						return;
					}
				}
			}
		}
	}
}


class Score extends JFrame
{
	JFrame jf = new JFrame("Score");
	JLabel background = new JLabel(new ImageIcon(getClass().getResource("score.png")));
	JLabel fullScore = new JLabel(new ImageIcon(getClass().getResource("fullScore.png")));
	Font buttonFont = new Font("Calibri",Font.BOLD,20);
	Font scoreFont = new Font("Calibri",Font.BOLD,50);
	private JButton playAgain,mainMenu;
	private JLabel scoreValue;

	public Score(int count)
	{
		jf.setSize(700,700);
		jf.setVisible(true);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);		////centre window
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		scoreValue = new JLabel();
		scoreValue.setText(String.valueOf(count));
		scoreValue.setBounds(215,173,100,50);
		
		scoreValue.setFont(scoreFont);

		scoreValue.setForeground(Color.WHITE);

		playAgain = new JButton("PLAY AGAIN");
		mainMenu = new JButton("MAIN MENU");
				
		playAgain.setBounds(460,520,150,40);
		mainMenu.setBounds(460,570,150,40);
		
		playAgain.setFont(buttonFont);
		playAgain.setForeground(Color.WHITE);
		playAgain.setBackground(Color.BLACK);
		mainMenu.setFont(buttonFont);
		mainMenu.setForeground(Color.WHITE);
		mainMenu.setBackground(Color.BLACK);

		jf.add(scoreValue);
		jf.add(playAgain);
		jf.add(mainMenu);

		//change cursor to hand cursor when moved on button
		playAgain.setCursor(new Cursor(Cursor.HAND_CURSOR));
		mainMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));

		jf.setLayout(new BorderLayout());
		background.setLayout(new FlowLayout());
		if(count<64)
		{
			jf.add(background);	
		}
		else
		{
			jf.add(fullScore);
		}


		playAgain.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				newWindowChessBoard();
			}
		});
		mainMenu.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				newWindowFront();
			}
		});
	}
	public void newWindowChessBoard()
	{
		jf.dispose();
		new ChessBoard();
	}
	public void newWindowFront()
	{
		jf.dispose();
		new Front();
	}
}

class Help extends JFrame
{
	JFrame jf = new JFrame("RULES");
	JLabel background = new JLabel(new ImageIcon(getClass().getResource("rules.png")));
	Font buttonFont = new Font("Calibri",Font.BOLD,20);
	JButton back;

	public Help()
	{
		jf.setSize(700,700);
		jf.setVisible(true);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);		////centre window
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		back = new JButton("BACK");
		back.setBounds(500,570,100,40);
		
		back.setFont(buttonFont);
		back.setForeground(Color.WHITE);
		back.setBackground(Color.BLACK);

		jf.add(back);

		jf.setLayout(new BorderLayout());
		background.setLayout(new FlowLayout());
		jf.add(background);

		//change cursor to hand cursor when moved on button
		back.setCursor(new Cursor(Cursor.HAND_CURSOR));

		back.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				newWindowFront();
			}
		});

	}
	public void newWindowFront()
	{
		jf.dispose();
		new Front();
	}
}

//About window which provide info about the game and it's creator  
class About extends JFrame
{
	JFrame jf = new JFrame("ABOUT");
	JLabel background = new JLabel(new ImageIcon(getClass().getResource("about.png")));
	Font buttonFont = new Font("Calibri",Font.BOLD,20);
	JButton back;

	public About()
	{
		jf.setSize(700,700);
		jf.setVisible(true);
		jf.setResizable(false);
		jf.setLocationRelativeTo(null);		////centre window
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		back = new JButton("BACK");     //Initialising back button 
		back.setBounds(500,570,100,40);

		back.setFont(buttonFont);
		back.setForeground(Color.WHITE);
		back.setBackground(Color.BLACK);

		jf.add(back);     // adding button to frame 

		jf.setLayout(new BorderLayout());
		background.setLayout(new FlowLayout());
		jf.add(background);

		//change cursor to hand cursor when moved on button
		back.setCursor(new Cursor(Cursor.HAND_CURSOR));

		back.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				newWindowFront();
			}
		});
	}

	public void newWindowFront()
	{
		jf.dispose();
		new Front();
	}
}
