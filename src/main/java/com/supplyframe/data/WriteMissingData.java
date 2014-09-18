package com.supplyframe.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import org.apache.commons.lang.StringUtils;

public class WriteMissingData {

	public static void main(String[] args) throws Exception {
		
		if(args.length < 3) {
			System.out.println("Please specify input, output and host name");
			return;
		}
		
		String input = args[0]; 
		String output = args[1]; 
		String host = args[2];
		BufferedReader reader = new BufferedReader(new FileReader(input));
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));

		String line;
		int id = 1;
		while ((line = reader.readLine()) != null) {
			
			String temp = StringUtils.join(
					new String[] { line, host, "tracking",
							"analytics_supplyframe_com.log",
							String.valueOf(id++) }, ' ');
			writer.write(temp);
			writer.newLine();
		}
		reader.close();
		writer.close();
	}

}
