package com.supplyframe.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.supplyframe.data.parser.ClickTrackingParser;
import com.supplyframe.data.vo.ClickTrackingLogVO;

public class ReadFile {

	public static void main(String[] args) throws IOException {
		
		if(args.length < 2) {
			System.out.println("Please specify output directory and host");
			return;
		}
		
		String outputDir = args[0];
		String host = args[1];
		
		processDir(outputDir, host);
	}

	private static void processDir(String dirPath, String host) throws IOException {
		File file = new File(dirPath);
		BufferedWriter writer = new BufferedWriter(new FileWriter(dirPath + "/temp.out"));
		try {
			long prevTime = Long.MAX_VALUE, currTime = 0;;
			for (File f : file.listFiles()) {
				if (!f.getName().startsWith("event")) {
					continue;
				}

				BufferedReader reader = new BufferedReader(new FileReader(f));
				
				String line;
				String prevLine = null;
				while ((line = reader.readLine()) != null) {
					try {
					ClickTrackingLogVO logVO = FrameDataFactory.parse(line, ClickTrackingParser.class);
					
					// us-east1e-ec2-tracking-06
					// us-east1b-ec2-tracking-05
					if (!logVO.getHostName().equals(host)) {
						continue;
					}
					
					writer.write(logVO.getDateTimeAsUTCString() + " \t " + logVO.getHostName());
					writer.newLine();
					currTime = logVO.getDateTime().getTime();
					
					if(currTime - prevTime > (2 * 60 * 1000)) {
						System.out.println(prevLine);
						System.out.println(line);
					}
					
					prevTime = currTime;
					prevLine = line;
					} catch (Exception e) {
						System.out.println(line);
						e.printStackTrace();
					}
				}
				System.out.println("Last line " + prevLine);
				reader.close();
				
			}
			writer.close();
		} catch (IOException e) {

		}
	}
}
