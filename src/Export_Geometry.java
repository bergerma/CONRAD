import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import edu.stanford.rsl.conrad.geometry.trajectories.Trajectory;
import edu.stanford.rsl.conrad.numerics.SimpleMatrix;
import edu.stanford.rsl.conrad.utils.Configuration;
import edu.stanford.rsl.conrad.utils.FileUtil;
import ij.IJ;
import ij.plugin.PlugIn;

/**
 * Exports primary and secondary angles as well as projection matrices into a txt file.
 * 
 * @author Jang-Hwan Choi, Martin Berger
 *
 */
public class Export_Geometry implements PlugIn {

	public Export_Geometry(){
		
	}
	
	@Override
	public void run(String arg) {
		Configuration.loadConfiguration();
		try {
			Trajectory geometry = Configuration.getGlobalConfiguration().getGeometry();
			String filename = FileUtil.myFileChoose(".txt", true);
			if (!filename.endsWith(".txt")) filename += ".txt";
			exportGeometryToTextFile(geometry, filename);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			IJ.log(e.getLocalizedMessage());
		}
	
	}
	
	public void exportGeometryToTextFile(Trajectory geometry, String filename) throws IOException{
		FileWriter fos = new FileWriter(filename);
		BufferedWriter buf = new BufferedWriter(fos);
		buf.write("projtable version 3");
		buf.newLine();
		buf.write(new Date(System.currentTimeMillis()).toString());
		buf.newLine();
		buf.newLine();
		buf.write("# format: angle / entries of projection matrices");
		buf.newLine();
		// write geometry.			
		buf.write(geometry.getProjectionMatrices().length+"");
		buf.newLine();
		buf.newLine();
		
		for (int i=0; i<geometry.getProjectionMatrices().length; i++){
			buf.write("@ "+(i+1));
			buf.newLine();
			buf.write(geometry.getPrimaryAngles()[i]+" "+geometry.getSecondaryAngles()[i]);
			buf.newLine();
			SimpleMatrix proj = geometry.getProjectionMatrices()[i].computeP();
			for (int j=0; j < proj.getRows(); j++) {
				for (int k=0; k < proj.getCols(); k++){
					buf.write(proj.getElement(j,k)+" ");
				}
				buf.newLine();
			}
			
			buf.newLine();				
		}
		
		
		buf.close();
		fos.close();
	}

}

/*
 * Copyright (C) 2010-2016 - Martin Berger
 * CONRAD is developed as an Open Source project under the GNU General Public License (GPL).
*/
