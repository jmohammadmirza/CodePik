package com.file.process;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class FileProcess {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		String file=args[0];
		FileInputStream in=null;
		Scanner sc=null;
		String s=null;
		Set<String> sdata=new HashSet<String>();
		List<String> ldata=new ArrayList<String>();
		int count=0;
		try{
			in=new FileInputStream(file);
			sc=new Scanner(in,"UTF-8");//.useDelimiter("\\|");
			System.out.println("Started Processing : "+LocalDateTime.now());
			while(sc.hasNextLine()){
				s=sc.nextLine();
				for(String val :s.split("\\|")){
					sdata.add(val);
					ldata.add(val);
					count++;
				}
			}
			System.out.println("Data stored in collections:  "+LocalDateTime.now());
			for(String val:sdata){
				int freq=Collections.frequency(ldata, val);
				System.out.println("Frequency of "+val+ " is "+freq);
				System.out.println(count);
			}
		}
		catch(Exception e){
		throw e;	
		}
		finally{
			sc.close();
			in.close();
		}
	}

}
