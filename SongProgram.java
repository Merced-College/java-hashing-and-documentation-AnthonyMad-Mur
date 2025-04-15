/**
* SongProgram.java
* @author Anthony Madrigal-Murillo
* @since 4/14/25
* This class is responsible for the GUI and basic version of the program. This class contains 
* the instructions needed to load the song data from the data.csv file and is responsible 
* for finding and outputing the correct song given a recognized song ID.
*/

//package hashingAndDocumentation;//comment this out if you do not need a package
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class SongProgram {

    // HashMap to store SongRecords with the song's ID as the key
    private HashMap<String, SongRecord> songMap;

    // Constructor
    public SongProgram() {
        songMap = new HashMap<>();
    }

    // Method to load songs from a CSV file
    // Preconditions: the parameter filePath should be a CSV file that the program has
    // access to.
    // Postconditions: the program correctly reads all lines of the CSV and stores the songs
    // as song objects.
    // @param filePath, filePath is used to direct the program to the CSV file that contains
    // data on the songs being used.
    // Returns: The program does not return anything.
    // Exceptions: The program will throw an error if the CSV file could not be read.
    public void loadSongsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            //read in first line and do nothing with it
            br.readLine();
            
            while ((line = br.readLine()) != null) {
            	
            	//System.out.println(line);//for testing
                // Create a SongRecord from the line and add it to the map
                SongRecord song = new SongRecord(line);
                songMap.put(song.getId(), song);
            }
            System.out.println("Songs successfully loaded from CSV.");
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    // Method to retrieve a SongRecord by ID
    // Preconditions: the parameter id should be a recognizable id that the program has
    // is familiar with.
    // Postconditions: The program returns the ID of a song.
    // @param id, id is used to direct the program to the record of a song
    // Returns: The program returns a SongRecord of a song.
    // Exceptions: The program throws no exceptions.
    public SongRecord getSongById(String id) {
        return songMap.get(id);
    }

    // Method to print all songs (for debugging or display purposes)
    // Preconditions: SongRecord should be full of songs.
    // Postconditions: The program prints all the values of songs in SongRecord.
    // Method has no parameters.
    // Returns: The program does not return anything.
    // Exceptions: The program throws no exceptions.
    public void printAllSongs() {
        for (SongRecord song : songMap.values()) {
            System.out.println(song);
        }
    }
    
    // GUI method to search for a song by ID
    // Preconditions: Song IDs should be full and recognizable by the program.
    // Postconditions: The program produces a usable GUI for searching for songs by id.
    // Method has no parameters.
    // Returns: The program does not return anything.
    // Exceptions: The program throws no exceptions.
    public void openSearchGui() {
        // Create the main frame
        JFrame frame = new JFrame("Song Lookup");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold input and button
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Label, Text Field, and Button
        JLabel label = new JLabel("Enter Song ID:");
        JTextField idField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        // Add label, text field, and button to panel
        panel.add(label);
        panel.add(idField);
        panel.add(searchButton);

        // Result area to display song details
        JTextArea resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane);

        // Add action listener for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                SongRecord song = getSongById(id);
                if (song != null) {
                    resultArea.setText("Song Found:\n" + song.toString());
                } else {
                    resultArea.setText("Song with ID " + id + " not found.");
                }
            }
        });

        // Add panel to frame
        frame.add(panel);
        frame.setVisible(true);
    }

    // Main method to demonstrate functionality and open GUI
    // Preconditions: Song IDs should be full and recognizable by the program.
    // Postconditions: The program opens its usable GUI.
    // Method has no parameters.
    // Returns: The program does not return anything.
    // Exceptions: The program throws no exceptions.
    public static void main2(String[] args) {
        SongProgram program = new SongProgram();

        // Load songs from a CSV file
        String filePath = "data.csv";  // replace with actual file path
        program.loadSongsFromCSV(filePath);

        // Open GUI for searching songs by ID
        program.openSearchGui();
    }

    // Main method to demonstrate functionality not using a GUI
    // Preconditions: Song IDs should be full and recognizable by the program.
    // Postconditions: The program functions in basic console text using song ids to display
    // complete data about songs.
    // Method has no parameters.
    // Returns: The program does not return anything.
    // Exceptions: The program throws no exceptions.
    public static void main(String[] args) {
        SongProgram program = new SongProgram();

        // Load songs from a CSV file
        String filePath = "data.csv";  // replace with actual file path
        program.loadSongsFromCSV(filePath);

        // Demonstrate retrieving a song by ID
        String testId = "4BJqT0PrAfrxzMOxytFOIz";  // replace with an actual ID from your file
        SongRecord song = program.getSongById(testId);
        if (song != null) {
            System.out.println("Retrieved song: " + song);
        } else {
            System.out.println("Song with ID " + testId + " not found.");
        }

        // Print all songs
        //program.printAllSongs();
    }
}

