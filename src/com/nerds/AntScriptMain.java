package com.nerds;

import java.io.BufferedReader;
import java.io.BufferedWriter; 
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AntScriptMain {
	public static void main(String[] args) {
		AntScriptCore core = new AntScriptCore();
		(new AntScriptDefaultRuntime()).setUp(core);

		if(args.length > 0) {
			for(int i = 0;i < args.length; i++){
				try {
					core.runFile(args[i]);
				} catch (AntScriptException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			System.err.println("Usage: java -jar ants.jar [script]");
		}
	}
}
