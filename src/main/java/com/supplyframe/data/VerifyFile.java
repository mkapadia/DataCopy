package com.supplyframe.data;

import java.io.BufferedReader;
import java.io.FileReader;

import com.supplyframe.data.FrameDataFactory;
import com.supplyframe.data.parser.ClickTrackingParser;
import com.supplyframe.data.vo.ClickTrackingLogVO;

public class VerifyFile {

	// sudo -u nobody hdfs dfs -copyFromLocal event.east06.log /flumed/CT/2014/05/28/05/
	public static void main(String[] args) throws Exception {
		
		if(args.length < 1) {
			System.out.println("Please sepcify file you want to verify");
			return;
		}
		
		BufferedReader reader = new BufferedReader(new FileReader(args[0]));
		String line;
		int lineNumber = 0;
		while((line = reader.readLine()) != null) {
			ClickTrackingLogVO logVO = FrameDataFactory.parse(line, ClickTrackingParser.class);
			System.out.println(lineNumber + " " + logVO.getDateTimeAsUTCString() + " " + logVO.getHostName());
			lineNumber++;
			
		}
		reader.close();
	}
}
