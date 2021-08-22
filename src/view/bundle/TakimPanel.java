package view.bundle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.bundle.AMilliTakim;
import model.bundle.Attributes;
import model.bundle.Found;
import model.bundle.Futbolcu;
import model.bundle.SortKey;

public class TakimPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static final int MAX_SIZE = 26;
	private AMilliTakim milliTakim = new AMilliTakim(MAX_SIZE);
	private JPanel upperPanel;
	private JPanel lowerPanel;
	private JButton newButton;
	private JButton fillButton;
	private JButton findButton;
	private JButton insButton;
	private JButton delButton;
	private JButton removeMax;
	private JButton sortButton;
	private JButton changeDups;
	private JLabel firstName;
	private JLabel lastName;
	private JLabel formaNo;
	private JLabel showDups;
	private JTextField fname;
	private JTextField last;
	private JTextField formNo;
	private JTable takimTable;
	private TakimTableModel takimModel;
	private JPanel textPanel;
	private JPanel buttonPanel;
	private JLabel dupPrompt;
	private JLabel sortAttributes;
	private JLabel sortAscDesc;
	private JPanel radioPanel;
	private JRadioButton formaNoSort;
	private JRadioButton firstNameSort;
	private JRadioButton lastNameSort;
	private JRadioButton ratingSort;
	private JRadioButton ascending;
	private JRadioButton descending;
	private JPanel radioAscPanel;
	private JPanel rightPanel;
	private ButtonGroup attributes;
	private ButtonGroup sortType;
	private static final Color BG_COLOR = new Color(184, 210, 224);
	
	public TakimPanel() {
		takimModel = new TakimTableModel(milliTakim);
		takimTable = new JTable(takimModel);
		takimTable.setDefaultRenderer(Object.class, new FindColumnCellRenderer());
		newButton = new JButton("New");
		fillButton = new JButton("Fill");
		insButton = new JButton("Insert");
		findButton = new JButton("Find");
		delButton = new JButton("Delete");
		sortButton = new JButton("Sort");
		removeMax = new JButton("Remove Max");
		changeDups = new JButton("Duplicate On/Off");
		firstName = new JLabel("First Name: ");
		fname = new JTextField("", 10);
		lastName = new JLabel("Last Name: ");
		last = new JTextField("", 10);
		formaNo = new JLabel("Forma No: ");
		formNo = new JTextField("", 4);
		dupPrompt = new JLabel("Duplicate Enabled: ");
		showDups = new JLabel(String.valueOf(milliTakim.getDuplicateAllowed()));
		sortAttributes = new JLabel("Sort Attributes: ");
		sortAscDesc = new JLabel("Ascending / Descending: ");
		formaNoSort = new JRadioButton("Forma No", true);
		formaNoSort.setActionCommand(Attributes.FORMA_NO.getAttribute());
		firstNameSort = new JRadioButton("First Name");
		firstNameSort.setActionCommand(Attributes.FIRST_NAME.getAttribute());
		lastNameSort = new JRadioButton("Last Name");
		lastNameSort.setActionCommand(Attributes.LAST_NAME.getAttribute());
		ratingSort = new JRadioButton("Rating");
		ratingSort.setActionCommand(Attributes.RATING.getAttribute());
		ascending = new JRadioButton("Ascending", true);
		ascending.setActionCommand(SortKey.ASCENDING.getSortType());
		descending = new JRadioButton("Descending");
		descending.setActionCommand(SortKey.DESCENDING.getSortType());
		attributes = new ButtonGroup();
		sortType = new ButtonGroup();
		
		newButton.addActionListener(e -> {
			milliTakim = new AMilliTakim(MAX_SIZE);
			takimModel.setTakim(milliTakim);
			revalidate();
			repaint();
		});
		
		changeDups.addActionListener(e -> {
			milliTakim.setDuplicateAllowed(!milliTakim.getDuplicateAllowed());
			takimModel.setTakim(milliTakim);
			showDups.setText(String.valueOf(milliTakim.getDuplicateAllowed()));
		});
		
		fillButton.addActionListener(e -> {
			if(milliTakim != null) {
				milliTakim.fillTakim();
				takimModel.setTakim(milliTakim);
				revalidate();
				repaint();
			}
		});
		
		sortButton.addActionListener(e -> {
			if(milliTakim != null) {
				milliTakim.sort(0, milliTakim.getCurrentSize() - 1, getAttribute(), getSortType());
				takimModel.setTakim(milliTakim);
				revalidate();
				repaint();
			}
		});
		
		removeMax.addActionListener(e -> {
			if(milliTakim != null && milliTakim.getCurrentSize() != 0) {
				milliTakim.removeMaxRating();
				takimModel.setTakim(milliTakim);
				revalidate();
				repaint();
			}
		});
		
		findButton.addActionListener(f -> {
			
			try {
				if(!fname.getText().equals("") && !last.getText().equals("") && !formNo.getText().equals("")) {
					Futbolcu futbolcu = new Futbolcu(Integer.parseInt(formNo.getText()), fname.getText(), last.getText());
					if(milliTakim != null && milliTakim.getCurrentSize() != 0 && milliTakim.find(futbolcu) != null) {
						revalidate();
						repaint();
					}
					ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
					Runnable task = new Runnable() {
						
						@Override
						public void run() {
							milliTakim.find(futbolcu).changeFoundStatus(Found.NOT_FOUND);
							revalidate();
							repaint();
						}
					};    
					executor.schedule(task, 1, TimeUnit.SECONDS);
					executor.shutdown();
				}
				else {
					JOptionPane.showMessageDialog(this.getParent(), "Bulma iþlemi gerçekleþtirilemedi, tüm alanlar doldurulmadý!");
				}
			}
			catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(this.getParent(), "Bulma iþlemi gerçekleþtirilemedi, forma no kutucuðuna geçerli bir sayý girmediniz !");
			}
		});
		
		insButton.addActionListener(l -> {
			try {
				if(!fname.getText().equals("") && !last.getText().equals("") && !formNo.getText().equals("") && milliTakim != null && milliTakim.getCurrentSize() < MAX_SIZE) {
					milliTakim.insert(Integer.parseInt(formNo.getText()), fname.getText(), last.getText());
					takimModel.setTakim(milliTakim);
					revalidate();
					repaint();
				}
				else if(fname.getText().equals("") || last.getText().equals("") || formNo.getText().equals("")){
					JOptionPane.showMessageDialog(this.getParent(), "Ekleme iþlemi gerçekleþtirilemedi, tüm alanlar doldurulmadý!");
				}
				else {
					JOptionPane.showMessageDialog(this.getParent(), "Ekleme iþlemi gerçekleþtirilemedi, takým " + MAX_SIZE + " kiþiden fazla olamaz.");
				}
			}
			catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(this.getParent(), "Ekleme iþlemi gerçekleþtirilemedi, forma no kutucuðuna geçerli bir sayý girmediniz !");
			}
		});
		
		delButton.addActionListener(l -> {
			try {
				if(!fname.getText().equals("") && !last.getText().equals("") && !formNo.getText().equals("")) {
					Futbolcu futbolcu = new Futbolcu(Integer.parseInt(formNo.getText()), fname.getText(), last.getText());
					if(milliTakim != null && milliTakim.getCurrentSize() != 0 && milliTakim.find(futbolcu) != null) {
						milliTakim.delete(futbolcu);
						takimModel.setTakim(milliTakim);
						revalidate();
						repaint();
					}
					else {
						JOptionPane.showMessageDialog(this.getParent(), "Silme iþlemi gerçekleþtirilemedi, böyle bir oyuncu yok !");
					}
				}
				else {
					JOptionPane.showMessageDialog(this.getParent(), "Silme iþlemi gerçekleþtirilemedi, tüm alanlar doldurulmadý!");
				}
			}
			catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(this.getParent(), "Silme iþlemi gerçekleþtirilemedi, forma no kutucuðuna geçerli bir sayý girmediniz !");
			}
		});
		
		upperPanel = new JPanel();
		buttonPanel = new JPanel();
		textPanel = new JPanel();
		radioPanel = new JPanel();
		rightPanel = new JPanel();
		radioAscPanel = new JPanel();
		textPanel.setLayout(new GridLayout(5, 2));
		buttonPanel.setLayout(new GridLayout(2, 4));
		radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));
		radioAscPanel.setLayout(new BoxLayout(radioAscPanel, BoxLayout.X_AXIS));
	
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(newButton);
		buttonPanel.add(fillButton);
		buttonPanel.add(findButton);
		buttonPanel.add(insButton);
		buttonPanel.add(delButton);
		buttonPanel.add(sortButton);
		buttonPanel.add(removeMax);
		buttonPanel.add(changeDups);
		textPanel.add(firstName);
		textPanel.add(fname);
		textPanel.add(lastName);
		textPanel.add(last);
		textPanel.add(formaNo);
		textPanel.add(formNo);
		textPanel.add(dupPrompt);
		textPanel.add(showDups);
		radioPanel.add(sortAttributes);
		radioPanel.add(formaNoSort);
		radioPanel.add(firstNameSort);
		radioPanel.add(lastNameSort);
		radioPanel.add(ratingSort);
		attributes.add(formaNoSort);
		attributes.add(firstNameSort);
		attributes.add(lastNameSort);
		attributes.add(ratingSort);
		radioAscPanel.add(sortAscDesc);
		radioAscPanel.add(ascending);
		radioAscPanel.add(descending);
		radioAscPanel.add(Box.createRigidArea(new Dimension(75, radioAscPanel.getHeight())));
		sortType.add(ascending);
		sortType.add(descending);

		rightPanel.add(textPanel);
		rightPanel.add(radioPanel);
		rightPanel.add(radioAscPanel);
		buttonPanel.setBorder(new EmptyBorder(0, 5, 0, 50));
		rightPanel.setBorder(new EmptyBorder(0, 0, 0, 5));

		upperPanel.setLayout(new BorderLayout(10, 0));
		upperPanel.add(buttonPanel, BorderLayout.WEST);
		upperPanel.add(rightPanel, BorderLayout.EAST);
		upperPanel.setBorder(new EmptyBorder(0, 0, 30, 0));
		
		lowerPanel = new JPanel();
		takimTable.setPreferredScrollableViewportSize(new Dimension(upperPanel.getPreferredSize().width, (int) takimTable.getPreferredScrollableViewportSize().getHeight() + 15));
		JScrollPane takimPane = new JScrollPane(takimTable);
		takimTable.setFillsViewportHeight(true);
		lowerPanel.add(takimPane);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;
		add(upperPanel, gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 500;
		add(lowerPanel, gc);
		
		upperPanel.setBackground(BG_COLOR);
		buttonPanel.setBackground(BG_COLOR);
		rightPanel.setBackground(BG_COLOR);
		textPanel.setBackground(BG_COLOR);
		radioPanel.setBackground(BG_COLOR);
		formaNoSort.setBackground(BG_COLOR);
		formaNoSort.setBackground(BG_COLOR);
		firstNameSort.setBackground(BG_COLOR);
		lastNameSort.setBackground(BG_COLOR);
		ratingSort.setBackground(BG_COLOR);
		ascending.setBackground(BG_COLOR);
		descending.setBackground(BG_COLOR);
		radioAscPanel.setBackground(BG_COLOR);
		lowerPanel.setBackground(BG_COLOR);
		setBackground(BG_COLOR);
		
		setPreferredSize(new Dimension((int) getPreferredSize().getWidth() + 100, (int) getPreferredSize().getHeight() + 45));
		setVisible(true);
	}
	
	private Attributes getAttribute() {
		String attriString = attributes.getSelection().getActionCommand();
		switch(attriString) {
		case "Forma No":
			return Attributes.FORMA_NO;
		case "First Name":
			return Attributes.FIRST_NAME;
		case "Last Name":
			return Attributes.LAST_NAME;
		}
		return Attributes.RATING;
	}
	
	private SortKey getSortType() {
		String attriString = sortType.getSelection().getActionCommand();
		if(attriString.equals("Ascending"))
			return SortKey.ASCENDING;
		return SortKey.DESCENDING;
	}
}
