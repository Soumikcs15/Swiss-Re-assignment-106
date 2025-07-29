package com.swissre;

import com.swissre.service.HCMAnalyzer;
import com.swissre.service.HCMAnalyzerImpl;

public class ApplicationStarter {

	public static void main(String[] args) {
		String filePath = "C:\\Users\\Soumik PC\\Desktop\\SwissRe Coding Round\\employees_100.csv"; //Provide File Path
		HCMAnalyzer hcmAnalyzer = new HCMAnalyzerImpl();

		hcmAnalyzer.analyzeHCMData(filePath);
	}

}
