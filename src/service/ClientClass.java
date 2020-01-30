package service;

import java.net.Socket;
import java.net.UnknownHostException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import domain.Message;

import java.io.IOException;
import java.io.PrintWriter;

public class ClientClass {
	
	public void sendBusinessRule(Message message) throws UnknownHostException, IOException {
		Socket s = new Socket("192.168.1.73", 4711);
		PrintWriter pw = new PrintWriter(s.getOutputStream());
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		
		String jsonString = gson.toJson(message);
		pw.write(jsonString);
		pw.flush();
		s.close();

	}

	
}