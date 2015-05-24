package com.pilotcraftmc.stockmarket;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileManager {
	
	public static void write(String textToWrite, String fileName){
		try{
			PrintWriter writer = new PrintWriter(fileName, "UTF-8");
			writer.print(textToWrite);
			writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
    public static String[] readFile(String fileName) throws IOException {
	@SuppressWarnings("resource")
	Scanner inFile1 = new Scanner(new File(fileName)).useDelimiter(",\\s*");
	int count = -1;
	String[] read = new String[100];
	while (inFile1.hasNext()) {
	    read[++count] = inFile1.nextLine();
	}
	inFile1.close();

	return read;
    }

}
