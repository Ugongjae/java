import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.UIManager;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JProgressBar;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JSlider;

import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

import javax.swing.JToggleButton;
import javax.swing.JDesktopPane;
import javax.swing.JToolBar;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.tritonus.share.sampled.file.TAudioFileFormat;
import org.jaudiotagger.*;
import org.jaudiotagger.audio.*;

import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;

public class Music extends JFrame  {

	Volume Vol = new Volume();
	public AudioFile audioFile;
	public AudioFile audioFile2;
	private Player player;
	private boolean check = false;
	protected static int count;
	private JFrame frame;
	public static JLabel lblSong;
	public static JLabel lblSinger;
	private JPanel panel;
	private JLabel lblTime;
	private JProgressBar progressBar;
	private JPanel panel_1;
	private JButton btnPre;
	private JButton btnPlay;
	private JButton btnStop;
	private JButton btnNext;
	private JPanel panel_2;
	private JPanel panel_3;
	private JLabel lblSinger_1;
	private JLabel lblSongName;
	private JLabel lblDuration;
	private JList list;
	private JScrollPane scrollPane;
	private boolean checkplay = false;
	private ImageIcon play = new ImageIcon(ImageIO.read(new File(
			"C:\\Users\\samsung\\workspace\\music\\play.png")));
	private JPanel panel_4;
	private JButton btnOpenFile;
	private JButton btnAddSong;
	private JButton btnRemoveSong;
	private JPanel panel_5;
	private JButton btnUp;
	private JButton btnDown;
	private JSlider slider = new JSlider();
	private JSlider slider_1;
	private JLabel lblVol;
	private JButton btnOpenPlaylist;
	private JButton btnNewPlaylist;
	private JButton btnSavePlaylist;
	private JButton btnRemovePlaylist;
	private int slider_value;
	public String filepath;
	public int i = 0;
	// set ProgressBar range & Value-----------------------------------------------
	public Timer t = new Timer(1000, new ActionListener() { 		
		public void actionPerformed(ActionEvent ae) {
			progressBar.setValue(i++);
			if (i == duration) {
				i = 0;
				GoNext();
			}
			count2 = i / 60;

			if ((i - 60 * count2) < 10) {
				lblTime.setText(count2 + ":0" + i % 60 + "/" + minute + ":"
						+ second);
			} else {
				lblTime.setText(count2 + ":" + i % 60 + "/" + minute + ":"
						+ second);
			}
		}

	});

	public int duration;
	public int minute;
	public int second;
	public int count2 = 0;
	public int duration2;
	public int minute2;
	public int second2;
	public int count22 = 0;
	public static long currentLocation;
	private String dirpath;
	private int index;
	private DefaultListModel info = new DefaultListModel();		//list for play
	Vector info_data_display = new Vector();					//list for display
	Vector info_data = new Vector();							//list for save
	
	public static void main(String[] args) throws Exception {
		
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Music window = new Music();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Making ShortCut Ctrl + Keyboard key--------------------------------------------------------
	public Music() throws IOException {
		initialize();
		 KeyboardFocusManager manager=KeyboardFocusManager.getCurrentKeyboardFocusManager();
		  manager.addKeyEventDispatcher(new KeyEventDispatcher(){
		    @Override public boolean dispatchKeyEvent(KeyEvent e) {
	            int code = e.getKeyCode();
	            if (code == KeyEvent.VK_SPACE && e.getID() == KeyEvent.KEY_RELEASED) {
	               if (checkplay == true) {
	                  try {
	                     btnPlay.setIcon(new ImageIcon(
	                           ImageIO.read(new File(
	                                 "C:\\Users\\samsung\\workspace\\music\\play.png"))));
	                     t.stop();
	                     Vol.Pause();
	                  } catch (IOException e1) {
	                     // TODO Auto-generated catch block
	                     e1.printStackTrace();
	                  }
	                  checkplay = false;
	               } else {
	                  try {
	                     btnPlay.setIcon(new ImageIcon(
	                           ImageIO.read(new File(
	                                 "C:\\Users\\samsung\\workspace\\music\\pause.png"))));
	                     t.start();
	                     Vol.Resume();
	                  } catch (IOException e1) {
	                     // TODO Auto-generated catch block
	                     e1.printStackTrace();
	                  }
	                  checkplay = true;
	               }
	            }  else if (code == KeyEvent.VK_S
	                  && e.getID() == KeyEvent.KEY_RELEASED) {
	               try {
	                  btnStop.setIcon(new ImageIcon(ImageIO.read(new File(
	                        "C:\\Users\\samsung\\workspace\\music\\stop.png"))));
	                  Vol.Stop();
	                  i = 0;
	                  lblTime.setText(count + ":0" + i % 60);
	                  progressBar.setValue(i);
	                  t.stop();
	                  check = false;
	                  btnPlay.setIcon(new ImageIcon(ImageIO.read(new File(
	                        "C:\\Users\\samsung\\workspace\\music\\play.png"))));
	               } catch (IOException e1) {
	                  // TODO Auto-generated catch block
	                  e1.printStackTrace();
	               }

	            } else if (code == KeyEvent.VK_LEFT
	                  && e.getID() == KeyEvent.KEY_RELEASED) {
	               try {
	                  btnPre.setIcon(new ImageIcon(
	                        ImageIO.read(new File(
	                              "C:\\Users\\samsung\\workspace\\music\\blackback.png"))));
	                  Vol.player.close();
	                  i = 0;
	                  t.stop();
	                  Songinfo next = new Songinfo("", "", "");
	                  try {
	                     next = (Songinfo) info.getElementAt(Vol.getIndex() - 1);
	                     CalDurationPlay(next.path);
	                     // -----------------------------------------------------------------------

	                     t.start();
	                     Vol.Play(next.path, Vol.getIndex() - 1);
	                  } catch (ArrayIndexOutOfBoundsException ae) {
	                     next = (Songinfo) info.getElementAt(0);
	                     CalDurationPlay(next.path);

	                     t.start();
	                     Vol.Play(next.path, 0);
	                  }
	               } catch (IOException e1) {
	                  // TODO Auto-generated catch block
	                  e1.printStackTrace();
	               }

	            }else if (code == KeyEvent.VK_RIGHT&& e.getID() == KeyEvent.KEY_RELEASED){
	               try {
	                  btnNext.setIcon(new ImageIcon(
	                        ImageIO.read(new File(
	                              "C:\\Users\\samsung\\workspace\\music\\blacknext.png"))));
	                  GoNext();

	               } catch (IOException e1) {
	                  // TODO Auto-generated catch block
	                  e1.printStackTrace();
	               }
	            }

	               return false;
	         }
		    });
		
	}

	private void initialize() throws IOException {

		frame = new JFrame("Music Player");
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(
				"C:\\Users\\samsung\\workspace\\music\\Music.png"));
		frame.setBounds(100, 100, 450, 427);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("65dlu:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"), }));
		
		lblSong = new JLabel("Song");
		lblSong.setFont(new Font("Verdana", Font.PLAIN, 17));
		frame.getContentPane().add(lblSong, "4, 2, 7, 1");

		lblSinger = new JLabel("Singer");
		lblSinger.setFont(new Font("Verdana", Font.PLAIN, 11));
		frame.getContentPane().add(lblSinger, "4, 4, 7, 1");

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frame.getContentPane().add(panel, "4, 6, 9, 1, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("70px"), ColumnSpec.decode("300px"), },
				new RowSpec[] { RowSpec.decode("15px"), }));

		lblTime = new JLabel("Time");
		panel.add(lblTime, "1, 1, fill, fill");

		progressBar = new JProgressBar();
		progressBar.setForeground(Color.BLACK);
		// when Click the Progressbar User can choose the starting time to play (jump in the song and play)-----------------------------------
		progressBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// int v = progressBar.getValue();

				currentLocation = 0;
				int mouseX = e.getX();

				int progressBarVal = (int) Math
						.round(((double) mouseX / (double) progressBar
								.getWidth()) * progressBar.getMaximum());

				progressBar.setValue(progressBarVal);

				currentLocation = Vol.songTotalLength / duration
						* progressBarVal;
				Vol.Resume2();
				i = progressBarVal;
				count2 = i / 60;
				if ((i - 60 * count2) < 10) {
					lblTime.setText(count2 + ":0" + i % 60 + "/" + minute + ":"
							+ second);
				} else {
					lblTime.setText(count2 + ":" + i % 60 + "/" + minute + ":"
							+ second);
				}

			}
		});
		
		panel.add(progressBar, "2, 1, fill, fill");

		lblVol = new JLabel("");
		lblVol.setIcon(new ImageIcon(
				"C:\\Users\\samsung\\workspace\\music\\volume.png"));
		frame.getContentPane().add(lblVol, "4, 8, center, default");

		slider_1 = new JSlider();
		//User can control the Volume with slider --------------------------------------------------------------
		slider_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				
				Mixer.Info [] mixers = AudioSystem.getMixerInfo();
				for (Mixer.Info mixerInfo : mixers)
				{
				    Mixer mixer = AudioSystem.getMixer(mixerInfo);
				    Line.Info [] lineInfos = mixer.getTargetLineInfo(); // target, not source
				    for (Line.Info lineInfo : lineInfos)
				    {
				        Line line = null;
				        boolean opened = true;
				        try
				        {
				            line = mixer.getLine(lineInfo);
				            opened = line.isOpen() || line instanceof Clip;
				            if (!opened)
				            {
				                line.open();
				            }
				            FloatControl volCtrl = (FloatControl)line.getControl(FloatControl.Type.VOLUME);
				            volCtrl.setValue((float)slider_1.getValue()/100);
				          
				        }
				        catch (LineUnavailableException e)
				        {
				            e.printStackTrace();
				        }
				        catch (IllegalArgumentException iaEx)
				        {
				        }
				        finally
				        {
				            if (line != null && !opened)
				            {
				                line.close();
				            }
				        }
				    }
				}
				
			}
		});
		
	

		frame.getContentPane().add(slider_1, "6, 8");

		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		//panel_1.addKeyListener(new KeyAdapter() {});
		frame.getContentPane().add(panel_1, "10, 8, fill, fill");

		btnPre = new JButton(new ImageIcon(ImageIO.read(new File(
				"C:\\Users\\samsung\\workspace\\music\\blackback.png"))));
		btnPre.addMouseListener(new MouseAdapter() {
			@Override // When MousePressed btnPre change the Icon-----------------------------------------
			public void mousePressed(MouseEvent e) {
				try {
					btnPre.setIcon(new ImageIcon(
							ImageIO.read(new File(
									"C:\\Users\\samsung\\workspace\\music\\whiteback.png"))));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override// When MouseRealease btnPre change the Icon-----------------------------------------
			public void mouseReleased(MouseEvent e) {
				try {
					btnPre.setIcon(new ImageIcon(
							ImageIO.read(new File(
									"C:\\Users\\samsung\\workspace\\music\\blackback.png"))));
					Vol.player.close();
					i = 0;
					t.stop();
					Songinfo next = new Songinfo("", "", "");
					try {
						next = (Songinfo) info.getElementAt(Vol.getIndex() - 1);
						CalDurationPlay(next.path);
						// Play the previous Song -----------------------------------------------------------------------

						t.start();
						Vol.Play(next.path, Vol.getIndex() - 1);
					} catch (ArrayIndexOutOfBoundsException ae) {
						next = (Songinfo) info.getElementAt(0);
						CalDurationPlay(next.path);

						t.start();
						Vol.Play(next.path, 0);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_1.add(btnPre);
		btnPre.setContentAreaFilled(false);
		btnPre.setFocusPainted(false);

		btnPlay = new JButton(play);
		btnPlay.setContentAreaFilled(false);
		btnPlay.setFocusPainted(false);
		// --------------------------------------------------------------------------
		// change image play to pause , pause to play
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override // btnPlay button can Play & Pause Button
			public void mousePressed(MouseEvent e) {

				if (!checkplay) {
					try {
						btnPlay.setIcon(new ImageIcon(
								ImageIO.read(new File(
										"C:\\Users\\samsung\\workspace\\music\\whiteplay.png"))));

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						btnPlay.setIcon(new ImageIcon(
								ImageIO.read(new File(
										"C:\\Users\\samsung\\workspace\\music\\whitepause.png"))));

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// Song resume 
				if (check) {
					if (!checkplay) {
						try {
							btnPlay.setIcon(new ImageIcon(
									ImageIO.read(new File(
											"C:\\Users\\samsung\\workspace\\music\\pause.png"))));
							t.start();
							Vol.Resume();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						checkplay = true;
					}
					//Song Pause
					else {
						try {
							btnPlay.setIcon(new ImageIcon(
									ImageIO.read(new File(
											"C:\\Users\\samsung\\workspace\\music\\play.png"))));
							t.stop();
							Vol.Pause();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						checkplay = false;
					}
				} else if (!check) {
					// -----------------------------------------------------------------------------------------------------------------
					// ----------------------------------------------------------------------------------Analyze
					// duration of song
					Songinfo songname = (Songinfo) info.getElementAt(index);

					CalDurationPlay(songname.path);
					count2 = i / 60;
					if ((i - 60 * count2) < 10) {
						lblTime.setText(count2 + ":0" + i % 60 + "/" + minute
								+ ":" + second);
					} else {
						lblTime.setText(count2 + ":" + i % 60 + "/" + minute
								+ ":" + second);
					}

					// -----------------------------------------------------------------------

					Vol.Play(songname.path + "", index);
					
					
					System.out.println("duration " + minute + " : " + second);
					t.start();
					// add(progressBar);
					try {
						btnPlay.setIcon(new ImageIcon(
								ImageIO.read(new File(
										"C:\\Users\\samsung\\workspace\\music\\pause.png"))));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					check = true;
					checkplay = true;
				}
			}
		});
		panel_1.add(btnPlay);

		btnStop = new JButton(new ImageIcon(ImageIO.read(new File(
				"C:\\Users\\samsung\\workspace\\music\\stop.png"))));
		btnStop.setContentAreaFilled(false);
		btnStop.setFocusPainted(false);
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					btnStop.setIcon(new ImageIcon(
							ImageIO.read(new File(
									"C:\\Users\\samsung\\workspace\\music\\whitestop.png"))));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			// When Released progressbar and song are reset
			public void mouseReleased(MouseEvent e) {
				try {
					btnStop.setIcon(new ImageIcon(ImageIO.read(new File(
							"C:\\Users\\samsung\\workspace\\music\\stop.png"))));
					Vol.Stop();
					i = 0;
					lblTime.setText(count + ":0" + i % 60);
					progressBar.setValue(i);
					t.stop();
					check = false;
					btnPlay.setIcon(new ImageIcon(ImageIO.read(new File(
							"C:\\Users\\samsung\\workspace\\music\\play.png"))));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_1.add(btnStop);

		btnNext = new JButton(new ImageIcon(ImageIO.read(new File(
				"C:\\Users\\samsung\\workspace\\music\\blacknext.png"))));

		btnNext.setContentAreaFilled(false);
		btnNext.setFocusPainted(false);
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					btnNext.setIcon(new ImageIcon(
							ImageIO.read(new File(
									"C:\\Users\\samsung\\workspace\\music\\whitenext.png"))));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					btnNext.setIcon(new ImageIcon(
							ImageIO.read(new File(
									"C:\\Users\\samsung\\workspace\\music\\blacknext.png"))));
					GoNext();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel_1.add(btnNext);

		panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, "4, 10, 9, 1, fill, fill");
		panel_2.setLayout(new BorderLayout(0, 0));

		panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new GridLayout(0, 3, 0, 0));

		lblSinger_1 = new JLabel("Singer");
		lblSinger_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		panel_3.add(lblSinger_1);

		lblSongName = new JLabel("Songname");
		lblSongName.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		panel_3.add(lblSongName);

		lblDuration = new JLabel("Duration");
		lblDuration.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		panel_3.add(lblDuration);
		// --------------------------------------------------------------------------------------------select
		// list
		class Select implements MouseListener, ListSelectionListener {
			JList lst;

			public void valueChanged(ListSelectionEvent lse) {
				lst = (JList) lse.getSource();
				try {
					index = lst.getSelectedIndex();
				} catch (NumberFormatException e) {

				}
			}

			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {

					if (check) {
						try {

							Songinfo songname = (Songinfo) info
									.getElementAt(index);
							Vol.player.close();
							Vol.Play(songname.path, index);
							CalDurationPlay(songname.path);
							i = 0;
							t.start();

						} catch (NumberFormatException ea) {

						}
					} else {
						try {
							Songinfo songname = (Songinfo) info
									.getElementAt(index);
							i = 0;
							Vol.Play(songname.path, index);
							check = true;
							checkplay = true;
							CalDurationPlay(songname.path);

							t.start();
							try {
								btnPlay.setIcon(new ImageIcon(
										ImageIO.read(new File(
												"C:\\Users\\samsung\\workspace\\music\\pause.png"))));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
							}
						} catch (NumberFormatException ea) {
						}

					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		}
		// -------------------------------------------------------------------------------------------------------------------------

		list = new JList();
		TabListCellRenderer renderer = new TabListCellRenderer();
		list.setCellRenderer(renderer);
		list.addListSelectionListener(new Select());
		list.addMouseListener(new Select());

		panel_2.add(list, BorderLayout.CENTER);

		scrollPane = new JScrollPane(list);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel_2.add(scrollPane, BorderLayout.CENTER);

		panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		frame.getContentPane().add(panel_5, "14, 10, fill, fill");
		panel_5.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("20px"), },
				new RowSpec[] { FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("30px"),
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("30px"), }));

		btnUp = new JButton(new ImageIcon(ImageIO.read(new File(
				"C:\\Users\\samsung\\workspace\\music\\up.png"))));
		btnUp.setFocusable(false);
		btnUp.addActionListener(new ActionListener() {//-----------------------------------------up the song in list
	         public void actionPerformed(ActionEvent e) {
	            if(index==0){                     //do nothing when you selected first song
	               
	            }
	            else if(index==Vol.getIndex()){      
	               Swap(-1,-1,-1,0,-1,0,-1,0);         //when song played now == selected song
	               
	            }
	            else if(index==Vol.getIndex()+1){      //when song played now+1==selected song
	               Swap(0,1,0,-1,0,-1,0,-1);
	               
	            }
	            else{
	               Swap(0);
	            }
	         }
	      });
		panel_5.add(btnUp, "2, 6");

		btnDown = new JButton(new ImageIcon(ImageIO.read(new File(
				"C:\\Users\\samsung\\workspace\\music\\down.png"))));
		btnDown.setFocusable(false);
		btnDown.addActionListener(new ActionListener() {//------------------------------------------down the song in list
	         public void actionPerformed(ActionEvent e) {
	            try{
	               if(index==Vol.getIndex()){         //when song played now == selected song
	                  Swap(1,1,1,0,1,0,1,0);
	                  
	               }
	               else if(index==Vol.getIndex()-1){   //when song played now-1 == selected song
	                  Swap(0,-1,0,1,0,1,0,1);
	                  
	               }
	               else{
	                  Swap(1);
	               }
	            }catch(ArrayIndexOutOfBoundsException ae){//do nothing when you select last song
	               
	            }
	         }
	      });
		panel_5.add(btnDown, "2, 8");

		panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		frame.getContentPane().add(panel_4, "4, 14, 9, 1, fill, fill");
		panel_4.setLayout(new FormLayout(
				new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("50dlu"),
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));
		
		class OpenActionListener implements ActionListener {

			public void actionPerformed(ActionEvent e) {
				
				// remove current playlist and make new play list
				if (e.getSource() == btnNewPlaylist) {
					dirpath = "";
					info.removeAllElements();
					info_data_display.removeAllElements();
					info_data.removeAllElements();
					list.setListData(info_data_display);
					list.updateUI();
				} 
				
				// Add song to current playlist
				else if (e.getSource() == btnAddSong) {
					try {
						JFileChooser chooser = new JFileChooser();
						// User can only choose .mp3 file
						FileNameExtensionFilter filter = new FileNameExtensionFilter(
								"MP3 Files", 
								"mp3"); 
						chooser.setFileFilter(filter); 

					
						int ret = chooser.showOpenDialog(null);
						if (ret == JFileChooser.APPROVE_OPTION) { 
							
							filepath = chooser.getSelectedFile().getPath(); 
						

							Volume addVol = new Volume();
							addVol.setVolume(filepath);
							CalDuration(filepath);
							info.addElement(new Songinfo(addVol.getSong(),
									addVol.getSinger(), addVol.fileLocation));
							info_data_display.addElement(addVol.getSinger()
		                              + "\t" + addVol.getSong() + "\t"
		                              + minute2 + ":" + second2);
							info_data.addElement(addVol.getSinger() + ";"
									+ addVol.getSong() + ";"
									+ addVol.fileLocation + "\n");
							list.updateUI();
						}

					} catch (ArrayIndexOutOfBoundsException u) {
						e.setSource(btnOpenFile);

					}
					
				} 
				// remove the song in current playlist
				else if (e.getSource() == btnRemoveSong) {
					info.remove(index);
					info_data_display.remove(index);
					info_data.remove(index);
					list.updateUI();
					if (Vol.getIndex() == index) {
						//when remove now playing song , play the next song
						try {
							Songinfo songname = (Songinfo) info
									.getElementAt(list.getSelectedIndex());
							Vol.player.close();
							Vol.Play(songname.path, index);
							i = 0;

							CalDurationPlay(songname.path);
							t.start();
							
						}
						// when remove not playing song, song will be still playing
						catch (ArrayIndexOutOfBoundsException ae) {

							try {
								Songinfo songname = (Songinfo) info
										.getElementAt(0);
								Vol.player.close();
								Vol.Play(songname.path, index);
								i = 0;

								CalDurationPlay(songname.path);
								t.start();
							} 
							// when remove all the song in playlist the mp3 player has defaulted
							catch (ArrayIndexOutOfBoundsException a) {
								Vol.Stop();
								t.stop();
								i = 0;
								lblTime.setText("Time");
								lblSong.setText("Song");
								lblSinger.setText("Singer");
								progressBar.setValue(0);
								check = false;
								checkplay = false;
								try {
									btnPlay.setIcon(new ImageIcon(
											ImageIO.read(new File(
													"C:\\Users\\samsung\\workspace\\music\\play.png"))));
								} catch (IOException e1) {
									// TODO Auto-generated catch block
								}
								Vol.fileLocation = "";
								filepath = "";
							}
						}

					} 
					// analyze User remove now playing song or other
					else if (index < Vol.getIndex()) {
						Vol.setIndex(Vol.getIndex() - 1);
					}
				} 
				
				// Open mp3 files and auto play
				else if (e.getSource() == btnOpenFile) {
					JFileChooser chooser = new JFileChooser();
					
					// User can only choose mp3 files
					FileNameExtensionFilter filter = new FileNameExtensionFilter(
							"MP3 Files",
							"mp3"); 
					chooser.setFileFilter(filter); 

					
					int ret = chooser.showOpenDialog(null);
					if (ret == JFileChooser.APPROVE_OPTION) { 
						
						dirpath = "";
						filepath = chooser.getSelectedFile().getPath(); 
						
						// if song is playing stop that song and play the selected song
						if (check) {

							CalDurationPlay(filepath);
							Vol.player.close();
							Vol.Play(filepath + "", 0);
							i = 0;
							t.start();
							check = false;
							try {
								btnPlay.setIcon(new ImageIcon(
										ImageIO.read(new File(
												"C:\\Users\\samsung\\workspace\\music\\pause.png"))));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} 
						
						// if song is not playing play the selected song
						else {
							CalDurationPlay(filepath);
							Vol.Play(filepath + "", 0);
							i = 0;
							t.start();
							try {
								btnPlay.setIcon(new ImageIcon(
										ImageIO.read(new File(
												"C:\\Users\\samsung\\workspace\\music\\pause.png"))));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						check = true;
						checkplay = true;
						Volume addVol = new Volume();
						addVol.setVolume(filepath);
						CalDuration(filepath);
						info.removeAllElements();
						info.addElement(new Songinfo(addVol.getSong(), addVol
								.getSinger(), addVol.fileLocation));
						info_data_display.removeAllElements();
						info_data_display.addElement(addVol.getSinger()
	                              + "\t" + addVol.getSong() + "\t"
	                              + minute2 + ":" + second2);
						info_data.removeAllElements();
						info_data.addElement(addVol.getSinger() + ";"
								+ addVol.getSong() + ";" + addVol.fileLocation
								+ "\n");
						list.setListData(info_data_display);
						list.updateUI();
					}

				}
				// Open the playlist
				else if (e.getSource() == btnOpenPlaylist) {
					JFileChooser fc = new JFileChooser();
					
					// User can Open txt files
					FileNameExtensionFilter filter1 = new FileNameExtensionFilter(
							"TXT Files", 
							"txt"); 
					fc.setFileFilter(filter1);
					int returnVal = fc.showOpenDialog(null);

					// check if user action
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						info.removeAllElements();
						info_data_display.removeAllElements();
						info_data.removeAllElements();
						list.setListData(info_data_display);
						File file = fc.getSelectedFile();
						dirpath = file.getAbsolutePath();
						FileReader fr;
						try {
							fr = new FileReader(file.getAbsolutePath());
							BufferedReader br = new BufferedReader(fr);
							String sCurrentLine;
							while ((sCurrentLine = br.readLine()) != null) {
								int findsemi = sCurrentLine.lastIndexOf(";");
								String subpath = sCurrentLine
										.substring(findsemi + 1);
								Volume addVol = new Volume();
								addVol.setVolume(subpath);
								CalDuration(subpath);
								info.addElement(new Songinfo(addVol.getSong(),
										addVol.getSinger(), addVol.fileLocation));
								info_data_display.addElement(addVol.getSinger()
			                              + "\t" + addVol.getSong() + "\t"
			                              + minute2 + ":" + second2);
								info_data.addElement(addVol.getSinger() + ";"
										+ addVol.getSong() + ";"
										+ addVol.fileLocation + "\n");

							}
							list.setListData(info_data_display);
							list.updateUI();
							Songinfo songname = (Songinfo) info.getElementAt(0);

							Vol.Play(songname.path, 0);
							check = true;
							checkplay = true;
							br.close();
						} catch (StringIndexOutOfBoundsException be) {
							// Ignore last ']'
						} catch (Exception ex) {
							ex.printStackTrace();
						}

					}
				} 
				
				// remove playlist and stop the song
				else if (e.getSource() == btnRemovePlaylist) {
					File file = new File(dirpath);
					file.delete();
					Vol.Stop();
					t.stop();
					i = 0;
					lblTime.setText("Time");
					lblSong.setText("Song");
					lblSinger.setText("Singer");
					progressBar.setValue(0);
					check = false;
					checkplay = false;
					try {
						btnPlay.setIcon(new ImageIcon(
								ImageIO.read(new File(
										"C:\\Users\\samsung\\workspace\\music\\play.png"))));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
					}
					Vol.fileLocation = "";
					dirpath = "";
					info.removeAllElements();
					info_data_display.removeAllElements();
					info_data.removeAllElements();
					list.setListData(info_data_display);
					list.updateUI();
				}

				// save playlist 
				else if (e.getSource() == btnSavePlaylist) {
					JFileChooser fc = new JFileChooser();
					
					// User can save file with Txt.
					FileNameExtensionFilter filter1 = new FileNameExtensionFilter(
							"TXT Files", 
							"txt"); 
					fc.setFileFilter(filter1);
					int returnVal = fc.showSaveDialog(null);

					// check if user action
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						dirpath = file.getAbsolutePath();

						FileWriter fw;
						try {
							fw = new FileWriter(file.getAbsolutePath()+".txt");
							BufferedWriter bw = new BufferedWriter(fw);
							bw.write(info_data + "");
							bw.flush();
							bw.close();
							fw.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}

					}
				}
			}
		}
		// -------------------------------------------------------------------------------------------------

		btnOpenFile = new JButton("Open File");
		btnOpenFile.setBackground(Color.BLACK);
		btnOpenFile.setForeground(Color.GREEN);
		btnOpenFile.addActionListener(new OpenActionListener());
		btnOpenFile.setFocusable(false);
		panel_4.add(btnOpenFile, "2, 2");

		btnOpenPlaylist = new JButton("Open Playlist");
		btnOpenPlaylist.setBackground(Color.BLACK);
		btnOpenPlaylist.setForeground(Color.GREEN);
		btnOpenPlaylist.addActionListener(new OpenActionListener());
		btnOpenPlaylist.setFocusable(false);
		panel_4.add(btnOpenPlaylist, "12, 2");

		btnAddSong = new JButton("Add Song");
		btnAddSong.setBackground(Color.BLACK);
		btnAddSong.setForeground(Color.GREEN);
		btnAddSong.setFocusable(false);
		btnAddSong.addActionListener(new OpenActionListener());

		panel_4.add(btnAddSong, "2, 4");

		btnNewPlaylist = new JButton("New Playlist");
		btnNewPlaylist.setBackground(Color.BLACK);
		btnNewPlaylist.setForeground(Color.GREEN);
		btnNewPlaylist.setFocusable(false);
		btnNewPlaylist.addActionListener(new OpenActionListener());
		panel_4.add(btnNewPlaylist, "12, 4");

		btnRemoveSong = new JButton("Remove Song");
		btnRemoveSong.setBackground(Color.BLACK);
		btnRemoveSong.setForeground(Color.GREEN);
		btnRemoveSong.setFocusable(false);
		btnRemoveSong.addActionListener(new OpenActionListener());
		panel_4.add(btnRemoveSong, "2, 6");

		btnSavePlaylist = new JButton("Save Playlist");
		btnSavePlaylist.setBackground(Color.BLACK);
		btnSavePlaylist.setForeground(Color.GREEN);
		btnSavePlaylist.setFocusable(false);
		btnSavePlaylist.addActionListener(new OpenActionListener());
		panel_4.add(btnSavePlaylist, "12, 6");

		btnRemovePlaylist = new JButton("Remove Playlist");
		btnRemovePlaylist.setForeground(Color.GREEN);
		btnRemovePlaylist.setBackground(Color.BLACK);
		btnRemovePlaylist.setFocusable(false);
		btnRemovePlaylist.addActionListener(new OpenActionListener());
		panel_4.add(btnRemovePlaylist, "12, 8");
	}

	//-------------------------------------------------------------------------------------------go to next song------------------------
   public void GoNext() {
      Vol.player.close();
      i = 0;
      t.stop();
      Songinfo next = new Songinfo("", "", "");
      try {
         next = (Songinfo) info.getElementAt(Vol.getIndex() + 1);
         CalDurationPlay(next.path);

         t.start();
         Vol.Play(next.path, Vol.getIndex() + 1);
      } catch (ArrayIndexOutOfBoundsException aec) {
         next = (Songinfo) info.getElementAt(0);                           //when i go to next song during playing last song,
         CalDurationPlay(next.path);                                    //go to first song again

         t.start();
         Vol.Play(next.path, 0);
      }
   }
   //-------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------calculation the duration-------------------------------
   public void CalDuration(String path) {
      try {
         audioFile2 = AudioFileIO.read(new File(path));                           //duration for output of list

         duration2 = audioFile2.getAudioHeader().getTrackLength();
         second2 = duration2 % 60;
         minute2 = (duration2 - second2) / 60;
         //progressBar.setMinimum(0);
         //progressBar.setMaximum(duration);

      } catch (Exception e1) {
         e1.printStackTrace();

      }
   }
   public void CalDurationPlay(String path) {                                    //duration for music played
      try {
         audioFile = AudioFileIO.read(new File(path));

         duration = audioFile.getAudioHeader().getTrackLength();
         second = duration % 60;
         minute = (duration - second) / 60;
         progressBar.setMinimum(0);
         progressBar.setMaximum(duration);

      } catch (Exception e1) {
         e1.printStackTrace();

      }
   }
   //----------------------------------------------------------------------------------------------------------------------------------

//--------------------------------------------------------------------------------------------------------------------------Tab control in List-------------------
   static class TabListCellRenderer extends JLabel implements ListCellRenderer {
      protected static final Border m_noFocusBorder = new EmptyBorder(1, 1,
            1, 1);

      protected FontMetrics m_fm = null;

      public TabListCellRenderer() {
         super();
         setOpaque(true);
         setBorder(m_noFocusBorder);
      }

      public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
         setText(value.toString());

         setBackground(isSelected ? list.getSelectionBackground() : list
               .getBackground());
         setForeground(isSelected ? list.getSelectionForeground() : list
               .getForeground());

         setFont(list.getFont());
         setBorder((cellHasFocus) ? UIManager
               .getBorder("List.focusCellHighlightBorder")
               : m_noFocusBorder);

         return this;
      }

      public void paint(Graphics g) {
         m_fm = g.getFontMetrics();

         g.setColor(getBackground());                           //not change other option
         g.fillRect(0, 0, getWidth(), getHeight());
         getBorder().paintBorder(this, g, 0, 0, getWidth(), getHeight());

         g.setColor(getForeground());
         g.setFont(getFont());
         Insets insets = getInsets();
         int x = insets.left;
         int y = insets.top + m_fm.getAscent();

         StringTokenizer st = new StringTokenizer(getText(), "\t");      //when tab is inputed
         while (st.hasMoreTokens()) {                           //create the blank
            String str = st.nextToken();
            g.drawString(str, x, y);
            // insert distance for each tab
            x += 130;                                       //Control the blank size

            if (!st.hasMoreTokens())
               break;
         }
      }
   }
   //-----------------------------------------------------------------------------------------------------------------------------------------
   //----------------------------------------change the order of list--------------------------------
   public void Swap(int a1,int a2,int a3,int a4,int a5, int a6, int a7, int a8){
      Songinfo swapVol = new Songinfo("","","");
      swapVol=(Songinfo) info.getElementAt(index+a1);
      Vol.setIndex(Vol.getIndex()+a2);
      info.setElementAt(new Songinfo(Vol.getSong(),                  //swap list for play
            Vol.getSinger(), Vol.fileLocation), index+a3);
      info.setElementAt(swapVol,index+a4);
      info_data.setElementAt(Vol.getSinger() + ";"                  //swap list for save
                  +Vol.getSong() + ";"
                  + Vol.fileLocation + "\n", index+a5);
      info_data.setElementAt(swapVol.singer + ";"
            +swapVol.name + ";"
            +swapVol.path + "\n",index+a6);
      CalDuration(Vol.fileLocation);
      info_data_display.setElementAt(Vol.getSong()                  //swap list for display
            + "\t" + Vol.getSinger() + "\t" + minute2
            + ":" + second2, index+a7);
      CalDuration(swapVol.path);
      info_data_display.setElementAt(swapVol.name+"\t"+swapVol.singer+"\t"+minute2+":"+second2,index+a8);
      list.setListData(info_data_display);
      list.updateUI();
   }
   public void Swap(int a){
      Songinfo swapVol1 = new Songinfo("","","");
      Songinfo swapVol2 = new Songinfo("","","");
      swapVol1=(Songinfo) info.getElementAt(index-1+a);
      swapVol2=(Songinfo) info.getElementAt(index+a);
      info.setElementAt(swapVol1, index+a);                        //swap list for play
      info.setElementAt(swapVol2,index-1+a);
      info_data.setElementAt(swapVol1.singer + ";"                  //swap list for save
            +swapVol1.name + ";"
            +swapVol1.path + "\n", index+a);
      info_data.setElementAt(swapVol2.singer + ";"
            +swapVol2.name + ";"
            +swapVol2.path + "\n",index-1+a);
      CalDuration(swapVol1.path);
      info_data_display.setElementAt(swapVol1.name+"\t"+swapVol1.singer+"\t"+minute2+":"+second2, index+a);   //swap list for display
      CalDuration(swapVol2.path);
      info_data_display.setElementAt(swapVol2.name+"\t"+swapVol2.singer+"\t"+minute2+":"+second2,index-1+a);
      list.setListData(info_data_display);
      list.updateUI();
   }
	
	
}