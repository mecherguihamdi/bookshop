import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BookShop {

	private JFrame frame;
	private JTextField txtbname;
	private JTextField txtbedition;
	private JTextField txtbprice;
	private JTable table;
	private JTextField txtbid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookShop window = new BookShop();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BookShop() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pat;
	ResultSet rs;
	
	
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/bookshop", "root","11405630");
		}
		catch (ClassNotFoundException ex){
			
		}
		catch (SQLException ex) {
			
		}
	}
	
	public void table_load() {
		try {
			pat = con.prepareStatement("select * from book");
			rs = pat.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		
		catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.control);
		frame.setBounds(100, 100, 587, 429);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblBookShop = new JLabel("Book Shop");
		lblBookShop.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblBookShop.setBounds(230, 11, 148, 50);
		frame.getContentPane().add(lblBookShop);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("");
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 72, 273, 168);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book name");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 46, 78, 14);
		panel.add(lblNewLabel);
		
		JLabel lblEdition = new JLabel("Edition");
		lblEdition.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEdition.setBounds(10, 81, 78, 14);
		panel.add(lblEdition);
		
		JLabel lblPrice = new JLabel("Price");
		lblPrice.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPrice.setBounds(10, 117, 78, 14);
		panel.add(lblPrice);
		
		txtbname = new JTextField();
		txtbname.setBounds(109, 44, 130, 20);
		panel.add(txtbname);
		txtbname.setColumns(10);
		
		txtbedition = new JTextField();
		txtbedition.setColumns(10);
		txtbedition.setBounds(109, 79, 130, 20);
		panel.add(txtbedition);
		
		txtbprice = new JTextField();
		txtbprice.setColumns(10);
		txtbprice.setBounds(109, 115, 130, 20);
		panel.add(txtbprice);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String bname,bedition,bprice;
				
				bname = txtbname.getText();
				bedition = txtbedition.getText();
				bprice = txtbprice.getText();
				
				try {
					pat = con.prepareStatement("insert into book(name,edition,price)Values(?,?,?)");
					pat.setString(1, bname);
					pat.setString(2, bedition);
					pat.setString(3, bprice);
					pat.executeUpdate();
					JOptionPane.showMessageDialog(null, "record added !!!");
					table_load();
					txtbname.setText("");
					txtbedition.setText("");
					txtbprice.setText("");
					txtbname.requestFocus();
				}
				
				catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton.setBounds(32, 251, 71, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Clear");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtbname.setText("");
				txtbedition.setText("");
				txtbprice.setText("");
				txtbname.requestFocus();
			}
		});
		btnNewButton_1.setBounds(113, 251, 71, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Exit");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton_1_1.setBounds(194, 251, 71, 23);
		frame.getContentPane().add(btnNewButton_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(305, 80, 256, 265);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 290, 282, 55);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblBookId = new JLabel("Book ID");
		lblBookId.setBounds(10, 23, 57, 15);
		lblBookId.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel_1.add(lblBookId);
		
		txtbid = new JTextField();
		txtbid.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					String bid = txtbid.getText();
					
					pat = con.prepareStatement("select name,edition,price from book where id = ?");
					pat.setString(1, bid);
					ResultSet rs = pat.executeQuery();
					
					if (rs.next() == true) {
						String bname = rs.getString(1);
						String bedition = rs.getString(2);
						String bprice = rs.getString(3);
						
						txtbname.setText(bname);
						txtbedition.setText(bedition);
						txtbprice.setText(bprice);
					}
					
					else {
						txtbname.setText("");
						txtbedition.setText("");
						txtbprice.setText("");
					}
					
				}
				
				catch (SQLException ex) {
					
				}
			}
		});
		txtbid.setBounds(108, 21, 130, 20);
		panel_1.add(txtbid);
		txtbid.setColumns(10);
		
		JButton btnNewButton_1_2 = new JButton("Update");
		btnNewButton_1_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bname,bedition,bprice,bid;
				
				bname = txtbname.getText();
				bedition = txtbedition.getText();
				bprice = txtbprice.getText();
				bid = txtbid.getText();
				
				try {
					pat = con.prepareStatement("update book set name=?, edition=?, price=? where id=?");
					pat.setString(1, bname);
					pat.setString(2, bedition);
					pat.setString(3, bprice);
					pat.setString(4, bid);
					pat.executeUpdate();
					JOptionPane.showMessageDialog(null, "record update !!!");
					table_load();
					txtbname.setText("");
					txtbedition.setText("");
					txtbprice.setText("");
					txtbname.requestFocus();
				}
				
				catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton_1_2.setBounds(364, 356, 77, 23);
		frame.getContentPane().add(btnNewButton_1_2);
		
		JButton btnNewButton_1_1_1 = new JButton("Delete");
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bid;
			
				bid = txtbid.getText();
				
				try {
					pat = con.prepareStatement("delete from book where id=?");
					pat.setString(1, bid);
					pat.executeUpdate();
					JOptionPane.showMessageDialog(null, "record delete !!!");
					table_load();
					txtbname.setText("");
					txtbedition.setText("");
					txtbprice.setText("");
					txtbname.requestFocus();
				}
				
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1_1_1.setBounds(445, 356, 77, 23);
		frame.getContentPane().add(btnNewButton_1_1_1);
	}
}
