package ucsd.ds.hw1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Stack;

class Bracket {
	Bracket(char type, int position) {
        this.type = type;
        this.position = position;
    }

    boolean Match(char c) {
        if (this.type == '[' && c == ']')
            return true;
        if (this.type == '{' && c == '}')
            return true;
        if (this.type == '(' && c == ')')
            return true;
        return false;
    }

    char type;
    int position;
}

class check_brackets {
    public static void main(String[] args) throws IOException {
        InputStreamReader input_stream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input_stream);
        String text = reader.readLine();

        Stack<check_brackets> opening_brackets_stack = new Stack<check_brackets>();
        
        Stack<Bracket> s = new Stack<Bracket>();
        for (int position = 0; position < text.length(); ++position) {
            char next = text.charAt(position);
            
           
            if (next == '(' || next == '[' || next == '{') {
                // Process opening bracket, write your code here
            		s.push(new Bracket(next, position));
            		continue;
            }
            
            if (next == ')' || next == ']' || next == '}') {
            	
            		if (s.empty()) { System.out.println(position+1); return;}
                // Process closing bracket, write your code here
            		if (s.peek().Match(next)) { s.pop(); continue;}
            		System.out.println(position+1); return;
            }
        }
        
        if (s.isEmpty())
    			System.out.println("Success");
        else
    			System.out.println(s.peek().position + 1);
        // Printing answer, write your code here
    }
}
