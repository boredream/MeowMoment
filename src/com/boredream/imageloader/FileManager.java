package com.boredream.imageloader;

public class FileManager {

	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "com.xlent/images/";
		} else {
			return CommonUtil.getRootFilePath() + "com.xlent/images";
		}
	}
}
