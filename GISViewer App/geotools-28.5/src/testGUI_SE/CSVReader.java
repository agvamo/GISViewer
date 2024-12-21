package testGUI_SE;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CSVReader().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("CSV Reader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JButton openButton = new JButton("Open CSV File");
        openButton.addActionListener(e -> openFileAndDisplay(frame));

        frame.add(openButton, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void openFileAndDisplay(JFrame parentFrame) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() || file.getName().toLowerCase().endsWith(".csv");
            }

            @Override
            public String getDescription() {
                return "CSV Files (*.csv)";
            }
        });

        int result = fileChooser.showOpenDialog(parentFrame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            displayCsvInTable(selectedFile);
        }
    }

    private void displayCsvInTable(File csvFile) {
        List<String[]> data = readCsvFile(csvFile);
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The CSV file is empty or invalid.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Extract headers and data
        String[] headers = data.get(0);
        String[][] rows = data.subList(1, data.size()).toArray(new String[0][]);

        // Create JTable and display it in a new JFrame
        JTable table = new JTable(new DefaultTableModel(rows, headers));
        JFrame tableFrame = new JFrame("CSV Data");
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tableFrame.add(new JScrollPane(table), BorderLayout.CENTER);
        tableFrame.setSize(800, 600);
        tableFrame.setVisible(true);
    }

    private List<String[]> readCsvFile(File csvFile) {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                rows.add(line.split(","));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to read the CSV file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return rows;
    }
}


