package ucsd.ds.hw1;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;
/**
 * A test frame work for many cases.
 * Input of the tested class are stored in files 1.txt 2.txt 3.txt
 * asnwers are stored in files 1.a 2.a 3.a
 * 
 * this unit test gets all inputs and input them into the 
 * class being test and compare the output with the corresponding answer.
 * **/
public class CheckBracketsTester
{
	//dir for test cases
	private  final String
		TEST_CASE_PATH = "/Users/leizhang/coursera/princeton/algoritm2/2ndtime/src/ucsd/ds/hw1/P1Tests";
	
	//inputs and answers
	private String[] caseInput, caseAnswers;
	
	@Before
	public void setUp() throws Exception
	{
		loadTestCases();
	}

	@Test
	public void test()
	{
		for (int i = 0; i < caseInput.length; i++)
		{
			String input = caseInput[i];
			String answer = caseAnswers[i];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
		    PrintStream ps = new PrintStream(baos);
		    PrintStream old = System.out;
		    System.setOut(ps);
		    
		    
		    
		    String[] params = new String[1];
		    params[0] = caseInput[i];
		    try
			{
				System.setIn(new ByteArrayInputStream(input.getBytes())); 
				check_brackets.main(params);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.flush();
		    System.setOut(old);
		    
		    
		    String result = baos.toString();
		    if (!result.trim().equals(answer)) {
		    		System.out.println("Error on input " + input +  ", expected" + answer
		    				+ ", actually " + result);
		    		fail("failed on input " + input);
		    }
		    else
		    		System.out.println("input " + input + " gets correct result");
		}
	}
	
	private void loadTestCases()
	{
		File folder = new File(TEST_CASE_PATH);
		File[] listOfFiles = folder.listFiles();
		int N = listOfFiles.length/2;
		caseInput = new String[N];
		caseAnswers = new String[N];
		
		int index = 0;
		for (File file : listOfFiles)
		{
			String fName = file.getAbsolutePath();
			String ansFName, content = "", ansContent = ""; 
			if (!fName.endsWith(".a"))
			{
				ansFName = fName + ".a";
				try
				{
					content = new Scanner(new File(fName)).useDelimiter("\\Z").next();
					ansContent = new Scanner(new File(ansFName)).useDelimiter("\\Z").next();
				} 
				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
				caseInput[index] = content;
				caseAnswers[index++] = ansContent;
			}
		}
		System.out.println("number of cases " + index);
		
	}
}
