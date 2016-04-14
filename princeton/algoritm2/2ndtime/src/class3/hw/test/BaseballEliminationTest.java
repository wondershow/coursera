package class3.hw.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import class3.hw.BaseballElimination;

public class BaseballEliminationTest {

	@Test
	public void isEliminatedTest() {
		BaseballElimination division;
		division = new BaseballElimination("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/src/class3/hw/files/teams4.txt");
		Assert.assertEquals(true,division.isEliminated("Philadelphia"));
		
		division = new BaseballElimination("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/src/class3/hw/files/teams4a.txt");
		Assert.assertEquals(true,division.isEliminated("Ghaddafi"));
		
		
		division = new BaseballElimination("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/src/class3/hw/files/teams5.txt");
		Assert.assertEquals(true,division.isEliminated("Detroit"));
		
		division = new BaseballElimination("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/src/class3/hw/files/teams5c.txt");
		Assert.assertEquals(true,division.isEliminated("Philadelphia"));
		
		division = new BaseballElimination("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/src/class3/hw/files/teams5.txt");
		Assert.assertEquals(false,division.isEliminated("New_York"));
		
	}
	
	@Test
	public void certificateOfEliminationTest() {
		BaseballElimination division;
		/*
		division = new BaseballElimination("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/src/class3/hw/files/teams4.txt");
		Assert.assertEquals(null,division.certificateOfElimination("Atlanta"));
		//assertArrayEquals(new String[] {"Atlanta" ,"New_York"},division.certificateOfElimination("Philadelphia"));
		Assert.assertEquals("[Atlanta, New_York]", division.certificateOfElimination("Philadelphia").toString());
		
		
		division = new BaseballElimination("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/src/class3/hw/files/teams4a.txt");
		Assert.assertEquals("[CIA, Obama]", division.certificateOfElimination("Ghaddafi").toString());
		
		division = new BaseballElimination("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/src/class3/hw/files/teams4b.txt");
		Assert.assertEquals(null, division.certificateOfElimination("Gryffindor"));

		division = new BaseballElimination("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/src/class3/hw/files/teams5.txt");
		Assert.assertEquals("[New_York, Baltimore, Boston, Toronto]", division.certificateOfElimination("Detroit").toString());

		division = new BaseballElimination("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/src/class3/hw/files/teams4.txt");
		Assert.assertEquals("[Atlanta]", division.certificateOfElimination("Montreal").toString()); */
		
		division = new BaseballElimination("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/src/class3/hw/files/teams4.txt");
		//Assert.assertEquals( null, division.certificateOfElimination("Atlanta"));
		System.out.println(division.certificateOfElimination("Atlanta"));

		division = new BaseballElimination("/Users/leizhang/coursera/princeton/algoritm2/2ndtime/src/class3/hw/files/teams5.txt");
		Assert.assertEquals(null, division.certificateOfElimination("New_York"));
		//Assert.assertEquals(["Atlanta" ,"Philadelphia"],division.certificateOfElimination("Atlanta"));
			
	}

}
