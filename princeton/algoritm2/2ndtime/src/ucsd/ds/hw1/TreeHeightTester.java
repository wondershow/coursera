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
import org.junit.BeforeClass;
import org.junit.Test;

public class TreeHeightTester
{
	//dir for test cases
	private  final String
		TEST_CASE_PATH = "/Users/leizhang/coursera/princeton/algoritm2/2ndtime/src/ucsd/ds/hw1/P2Tests";
	
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
				tree_height.main(params);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.flush();
		    System.setOut(old);
		    
		    String result = baos.toString().replaceAll("[\n\r]", "");
		    if (!result.trim().equals(answer.trim())) {
		    		System.out.println("Error on input : \n" + input +  ",  \nexpected" + answer
		    				+ ", actually: \n" + result + "\n end");
		    		fail("failed on input " + input);
		    }
		    else
		    		System.out.println("input " + input + " gets correct result");
		}
		fail("Not yet implemented");
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
				caseInput[index] = content.replaceAll("(?m)^[ \t]*\r?\n", "");
				caseAnswers[index++] = ansContent.replaceAll("(?m)^[ \t]*\r?\n", "");
			}
		}
		System.out.println("number of cases " + index);
	}
}
