package com.utilities;
import java.io.IOException;

import javax.servlet.http.*;

import com.utilities.util.EmailSender;

@SuppressWarnings("serial")
public class CourtBookerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		 try {
			 CourtBooker booker = new CourtBooker ("http://mail.theparklangleyclub.co.uk:82/source1/index.php");
			 booker.book ();
			 } catch (Exception ex){
				 try {
				 EmailSender.send(ex);
				 } catch (Exception ex1) {
					 throw new IOException(ex1);				 }
			 }
	}
}
