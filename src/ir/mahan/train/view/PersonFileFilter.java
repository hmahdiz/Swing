package ir.mahan.train.view;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PersonFileFilter extends FileFilter {

	@Override
	public boolean accept(File file) {

		if (file.isDirectory()) {
			return true;
		}
		
		String name = file.getName();
		String extension = Utils.getFileExtension(name);
		
		if (extension == null)
			return false;

		if (extension.equalsIgnoreCase("per"))
			return true;

		return false;
	}

	@Override
	public String getDescription() {

		return "Person DataBase File (*.per)";
	}

}
