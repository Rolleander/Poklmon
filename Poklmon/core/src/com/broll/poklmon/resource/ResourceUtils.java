package com.broll.poklmon.resource;

import java.io.File;

public class ResourceUtils {

	public static String DATA_PATH;

	

	public static void setDataPath(File file) {

		DATA_PATH = file.getPath() + "/";
	}


}
