package ucsd.ds.hw1;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

class Request {
    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
        this.finish_time = -3;
    }

    public int arrival_time;
    public int process_time;
    public int finish_time;
}

//class QueueBuffer {
//	int capacity, size;
//	//private ArrayList<>
//	
//	public QueueBuffer() {
//		
//		
//	}
//}


class Response {
    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }
    
    public boolean dropped;
    public int start_time;
}




class Buffer {
	private ArrayDeque<Request> queue;
    public Buffer(int size) {
        this.size_ = size;
        this.finish_time_ = new ArrayList<Integer>();
        queue = new ArrayDeque<Request>();
    }

    public Response Process(Request request) {
        // write your code here
    		//for (int i = 0; )
    		Response res;
    		
    		
    		
    		while (!queue.isEmpty() && queue.peekFirst().finish_time <= request.arrival_time)
    			queue.pollFirst();
    		
    		//System.out.println();
    		if (queue.isEmpty()) {
    			queue.add(request);
    			request.finish_time = request.arrival_time + request.process_time;
    			return new Response(false, request.arrival_time);
    		}
    		
    		if (queue.size() < this.size_)
    		{
    			//res = new Response(true, lastItemFinishTime);
    			//Request oldestInQ = queue.peekFirst();
    			Request recentInQ = queue.peekLast();
    			res = new Response(false, recentInQ.finish_time);
    			if (recentInQ.finish_time > request.arrival_time) 
    				request.finish_time = recentInQ.finish_time + request.process_time;
    			else
    				request.finish_time = recentInQ.arrival_time + request.process_time;
    			queue.add(request);
    			
    			return res;
    		}
    		
    		//the rest of this function handles when the queue is full
    		Request oldestInQ = queue.peekFirst();
		Request recentInQ = queue.peekLast();
    		if (oldestInQ.finish_time > request.arrival_time)
    		// when a request arrives, the cpu has not finishing the 1st item in the queue yet
    			return new Response(true, -1);
    		if (request.arrival_time <= recentInQ.finish_time) {
    			request.finish_time = recentInQ.finish_time + request.process_time;
    			res = new Response(false, recentInQ.finish_time);
    		} else {
    			request.finish_time = request.arrival_time + request.process_time;
    			res = new Response(false, request.arrival_time);
    		}
    		queue.pollFirst();
    		return res;
    }

    private int size_;
    private ArrayList<Integer> finish_time_;
}

class process_packages {
    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<Request>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }
        return requests;
    }

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<Response>();
        for (int i = 0; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int buffer_max_size = scanner.nextInt();
        Buffer buffer = new Buffer(buffer_max_size);

        ArrayList<Request> requests = ReadQueries(scanner);
        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        PrintResponses(responses);
    }
}
