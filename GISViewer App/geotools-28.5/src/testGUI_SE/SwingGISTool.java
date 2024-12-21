package testGUI_SE;

import java.io.File;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import org.geotools.data.FileDataStore;
import org.geotools.data.FileDataStoreFinder;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.action.SafeAction;
import org.geotools.swing.data.JFileDataStoreChooser;

/**
 * @author planner
 *
 */
public class SwingGISTool implements ActionListener {
	private File sourceFile;
	private SimpleFeatureSource featureSource;
	private MapContent map;
	private JTextField text;
    private JMapFrame mapFrame;
    JMenuBar menuBar;
	JMenu fileMenu, selectMenu, importMenu,toolbarMenu, helpMenu;
	JMenuItem newMenuItem, openMenuItem, saveMenuItem, exitMenuItem,
	rectangleSelectMenuItem, querySelectMenuItem, deselectMenuItem,
	importShapefileMenuItem, exportShapefileMenuItem, importCSVMenuItem, exportCSVMenuItem,
	toolbarMenuItemShow,
	aboutMenuItem, documentationMenuItem;
	JButton importshp,exportshp, importcsv, exportcsv, rectSelect, addPoint, removePoint, addLinePoint, removeLinePoint;
    JToolBar toolbar;
	
    
	public static void main(String[] args) throws Exception {
		SwingGISTool lab = new SwingGISTool();
		lab.displayShapefile();
	}
	
	
	public void displayShapefile() throws Exception {
		
		
		
		// Create a map context and add our shapefile to it
		map = new MapContent();
		map.setTitle("GISViewer");
		
		
		// Create a JMapFrame with custom toolbar buttons
		mapFrame = new JMapFrame(map);
		mapFrame.enableToolBar(true);
		mapFrame.enableStatusBar(true);
		
		
		//Menu Bar
		menuBar = new JMenuBar();
		
		//File Menu that has New File, Open, Save and Exit actions
		fileMenu = new JMenu("File");
		
		newMenuItem = new JMenuItem("New File");
		openMenuItem = new JMenuItem("Open");
		saveMenuItem = new JMenuItem("Save");
		exitMenuItem = new JMenuItem("Exit");
		
		fileMenu.add(newMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		menuBar.add(fileMenu);
		
		//Select Menu that has Rectangle Select, Query Select and Deselect actions
		selectMenu=new JMenu("Select");
		rectangleSelectMenuItem=new JMenuItem("Rectangle Tool Selection");
		querySelectMenuItem=new JMenuItem("Query Selection");
		deselectMenuItem=new JMenuItem("Deselect");
		
		selectMenu.add(rectangleSelectMenuItem);
		selectMenu.add(querySelectMenuItem);
		selectMenu.addSeparator();
		selectMenu.add(deselectMenuItem);
		menuBar.add(selectMenu);
		
		//Import Menu that has Import Shapefile, Export Shapefile, Import CSV and Export CSV actions
		importMenu=new JMenu("Import");
		importShapefileMenuItem=new JMenuItem("Import .shp",new ImageIcon(getClass().getResource("importShapefile.png")));
		importShapefileMenuItem.addActionListener(this);
		exportShapefileMenuItem= new JMenuItem("Export .shp",new ImageIcon(getClass().getResource("exportShapefile.png")));
		importCSVMenuItem=new JMenuItem("Import .csv",new ImageIcon(getClass().getResource("importCSV.png")));
		exportCSVMenuItem=new JMenuItem("Export .csv",new ImageIcon(getClass().getResource("exportCSV.png")));
		
		importMenu.add(importShapefileMenuItem);
		importMenu.add(exportShapefileMenuItem);
		importMenu.addSeparator();
		importMenu.add(importCSVMenuItem);
		importMenu.add(exportCSVMenuItem);
		menuBar.add(importMenu);
		
		
		//Toolbox Menu that has a Toolbox Show Checker
		toolbarMenu=new JMenu("Toolbar");
		toolbarMenuItemShow = new JCheckBoxMenuItem("Show Toolbar", true);
		toolbarMenuItemShow.addActionListener(this); 	
		
		toolbarMenu.add(toolbarMenuItemShow);
		menuBar.add(toolbarMenu);
		
		
		//Help Menu that has About and Documentation actions
		helpMenu=new JMenu("Help");
		aboutMenuItem=new JMenuItem("About");
		documentationMenuItem=new JMenuItem("Documentation");
		
		helpMenu.add(aboutMenuItem);
		helpMenu.addSeparator();
		helpMenu.add(documentationMenuItem);
		menuBar.add(helpMenu);
		
		
		mapFrame.setJMenuBar(menuBar);
		
		
		toolbar = mapFrame.getToolBar();
		toolbar.addSeparator();
		
		//Set buttons icons, tooltips, and action listeners
		importshp= new JButton(new ImageIcon(getClass().getResource("importShapefile.png")));
		importshp.setToolTipText("Import Shape File");
		importshp.addActionListener(this);
		exportshp=new JButton(new ImageIcon(getClass().getResource("exportShapefile.png")));
		exportshp.setToolTipText("Export Shape File");
		exportshp.addActionListener(this);
		importcsv=new JButton(new ImageIcon(getClass().getResource("importCSV.png")));
		importcsv.setToolTipText("Import CSV File");
		exportcsv=new JButton(new ImageIcon(getClass().getResource("exportCSV.png")));
		exportcsv.setToolTipText("Export CSV File");
		rectSelect=new JButton(new ImageIcon(getClass().getResource("rectangleSelection.png")));
		rectSelect.setToolTipText("Select features by drawing a rectangle on the canvas");
		addPoint=new JButton(new ImageIcon(getClass().getResource("addPoint.png")));
		addPoint.setToolTipText("Add point to canvas");
		removePoint=new JButton(new ImageIcon(getClass().getResource("removePoint.png")));
		removePoint.setToolTipText("Remove point from canvas");
		addLinePoint=new JButton(new ImageIcon(getClass().getResource("addLine.png")));
		addLinePoint.setToolTipText("Add point to a line in the canvas");
		removeLinePoint=new JButton(new ImageIcon(getClass().getResource("removeLine.png")));
		removeLinePoint.setToolTipText("Remove a point from a line in the canvas");
		
		
		//add jbuttons to toolbar
		toolbar.add(importshp);
		toolbar.add(exportshp);
		toolbar.add(importcsv);
		toolbar.add(exportcsv);
		toolbar.addSeparator();
		toolbar.add(rectSelect);
		toolbar.add(addPoint);
		toolbar.add(removePoint);
		toolbar.add(addLinePoint);
		toolbar.add(removeLinePoint);
		
		// Display the map frame. When it is closed the application will exit
		mapFrame.setBounds(450, 190, 650, 580);
		mapFrame.setDefaultCloseOperation(JMapFrame.EXIT_ON_CLOSE);
	    mapFrame.setVisible(true);
	}
	
	
	@Override
	//set actions of pressed buttons
	public void actionPerformed(ActionEvent e)  {
		System.out.println("a button has been pressed");
		
		if (e.getSource() == importshp|| e.getSource() == importShapefileMenuItem) {
			
			System.out.println("import started");
			
			sourceFile = JFileDataStoreChooser.showOpenFile("shp", null);
			if (sourceFile != null) {
				JOptionPane.showMessageDialog(text, String.format("File selected as a .shp File from %S", sourceFile));
			} else {
				JOptionPane.showMessageDialog(text, String.format("File not selected as a .shp File from %S", sourceFile));
			}
			try{
			FileDataStore store = FileDataStoreFinder.getDataStore(sourceFile);
			featureSource = store.getFeatureSource();

			// Create a map context and add our shapefile to it
			Style style = SLD.createSimpleStyle(featureSource.getSchema());
			Layer layer = new FeatureLayer(featureSource, style);
			map.layers().add(layer);
			mapFrame.setVisible(true);
			} catch (Exception ex){}
		}
		
		if (e.getSource() == exportshp) {
			System.out.println("export started");
			
			
		}
		
		if (e.getSource() == toolbarMenuItemShow){
			if (toolbarMenuItemShow.isSelected()==true){
				toolbar.setVisible(true);
				System.out.println("Toolbar enabled");
			} else {
				toolbar.setVisible(false);
				System.out.println("Toolbar disabled");
			}	
		}
		
	}
	
	
	
	
	
		

	
}
