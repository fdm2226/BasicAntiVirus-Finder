import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class AntiVirus extends JFrame implements Runnable{

	
	File[] myFile;
	int directories_count = 0;
	JTextField t_field = new JTextField("", 50); // for file name
	JTextArea t_area = new JTextArea(); // for displaying directories
	JLabel file_label = new JLabel("File Name :"); // for file name label
	JLabel dir_label = new JLabel("Directory :"); // for directory label
	JLabel status = new JLabel("Status: Ready"); // current status label
	JScrollPane sp = new JScrollPane(t_area); // scroll bars in text area
	JComboBox jcb = new JComboBox(); // for combo box
	JButton cleanVirus = new JButton("Clean Virus"); //for search button
	
	String file_name; //for storing file name
	String dir; //for storing directory
	boolean deleted = false; //for changing the status
	
	AntiVirus() {
		
		directories_count = File.listRoots().length; //counting total disk drives 
		myFile = File.listRoots(); // a file object for storing root directories
		
		for (int i=0; i<directories_count; i++) 
			jcb.addItem(myFile[i]); // adding directories in comboBox
			
			JPanel up = new JPanel(); //making a new panel to add north
			add(up, "North"); //adding panel in north position
			up.add(file_label); //first adding File Name label
			up.add(t_field); // then text field to enter file name
			up.add(dir_label); //directory label
			up.add(jcb); // then comboBox
			up.add(status); // status label
			up.add(cleanVirus); // search button
			
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setSize(1024, 700);  //frame size
				setVisible(true); //frame visibility
				
				add(sp); // adding scroll pane	
				


			    cleanVirus.addMouseListener(new MouseAdapter(){
			     
			       public void mouseClicked(MouseEvent me) { //overriding				
			     
			          file_name = t_field.getText();// getting the text from text field
			          dir = jcb.getSelectedItem().toString(); // directory selected from combo box
			          dir +="\\";    //appending double slashes to dir e.g C:\\		
			          status.setText("Finding Virus"); //setting the status
			          startThread(); //creating a new thread using this function
			    			} 
			       }); // end anonymous class and mouselistener
			    }      // end constructor	
	

    public void startThread(){
         Thread t = new Thread(this); //making a thread object
         t.start(); //starting thread
    }
		
		
	
	@Override
	public void run() { // overriding of runnable interface
		try {
			DeleteVirus(file_name, new File(dir)); //calling delete virus
			if (deleted == true)
				status.setText("Virus Deleted"); // if file is deleted then set label
			else 
				status.setText("No Virus found"); // otherwise no file is found	
		}catch(Exception e){} //handling the exception
		
	}


	private void DeleteVirus(String v_dir, File v) {
		// TODO Auto-generated method stub
		File[] list = v.listFiles(); //listing files
		
		if (list != null) { //checking so that nullpointerException cannot occur
			
			for (File subfile : list) { //listing every subdirectory
				
				t_area.append(""+v.getAbsolutePath()); //showing in text area  
				t_area.append("\n");
				
				File v_file = new File(v.getAbsolutePath(), file_name); // getting the path of file
				
				if(v_file.isFile()) //v_file points to a file
				{
					v_file.delete();  // delete that file which has specified name
					deleted = true; // turn boolean to true to set the label later
				}
				if (subfile.isDirectory()) {
					DeleteVirus(v_dir, subfile); //if more directories then search in them
			      }	 
				} //end for
			} //end if
		} //end DeleteVirus
	
	
	public static void main(String args[]) {
		new AntiVirus();
	}//end main
		
	}//end fileFinder class
	
	
	


