package com.utilities;

import java.util.List;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlLink;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLLinkElement;
import com.utilities.util.EmailSender;

public class CourtBooker {
	private String bUrl;
	 private String[] courtTimes = {
			 "bcsynp.php?a=27|510|80|9|",
			 "bcsynp.php?a=27|540|80|9|",
			 "bcsynp.php?a=27|570|80|9|",
			 "bcsynp.php?a=27|600|80|9|"
	 };
	 public CourtBooker (String url) throws Exception {
	  bUrl = url; 
	 }
	 
	 public void book () throws Exception {
	  WebClient wb = new WebClient ();
	  HtmlPage p = (HtmlPage) wb.getPage(bUrl);
	 
	  HtmlForm f = p.getFormByName("Form");
	  HtmlTextInput logonU = (HtmlTextInput) f.getInputByName("LogonU");
	  HtmlTextInput logonPW = (HtmlTextInput) f.getInputByName("LogonPW");
	  HtmlSubmitInput submit = (HtmlSubmitInput) f.getInputByName("logonSubmit");
	  
	  
	  logonU.setValueAttribute("singh");
	  logonPW.setValueAttribute("7164");
	  
	  HtmlPage menuPage = (HtmlPage) submit.click();
	  HtmlForm menuForm= menuPage.getFormByName("Form");
	  
	  HtmlSubmitInput courtBkngBtn = (HtmlSubmitInput) menuForm.getInputByName("BtnCourtBookings");
	  
	  // Select Court Page
	  HtmlPage courtPage = (HtmlPage) courtBkngBtn.click();
	  HtmlForm courtForm= courtPage.getFormByName("Form");
	  
	  HtmlSubmitInput croydonCourtBtn = (HtmlSubmitInput) courtForm.getInputByName("BtnCourtGroupIDArray[7]");
	  
	  //Calendar Page
	  HtmlPage calendarPage = (HtmlPage) croydonCourtBtn.click();
	  HtmlPage calendarPage2Weeks = this.nextWeek(this.nextWeek(calendarPage));
	  for (String courtTime:courtTimes) {
		  calendarPage2Weeks = bookCourt(calendarPage2Weeks, courtTime);
	  }
	 
	 }
	 
	 private HtmlPage nextWeek(HtmlPage calendarPage) throws Exception {
		  HtmlForm calendarForm = (HtmlForm) calendarPage.getFormByName("Form");
		  HtmlSubmitInput nextWeekBtn = (HtmlSubmitInput) calendarForm.getInputByName("BtnNextWeek");
		  HtmlPage nextWeekCalendar = (HtmlPage) nextWeekBtn.click();
		  return nextWeekCalendar;
	 }
	 
	 private HtmlPage bookCourt(HtmlPage calendarPage, String courtTime) throws Exception {
		 HtmlForm calendarForm = (HtmlForm) calendarPage.getFormByName("Form");
		  List<HtmlAnchor> links = calendarForm.getElementsByAttribute("a", "HREF", courtTime); 
		  System.out.println("Number of links for "+ courtTime +" are " + links.size());
		  
		  if(links.size() ==0){
			  throw new Exception("- Court is already booked");
		  }
		  
		  // Confirmation Page
		  HtmlPage confirmPage = (HtmlPage) links.get(0).click();
		  HtmlForm confirmForm = (HtmlForm) confirmPage.getFormByName("Form");
		  HtmlSubmitInput acceptBtn = (HtmlSubmitInput) confirmForm.getInputByName("accept");
		  
		  // Go back screen
		  
		  HtmlPage goBackPage = (HtmlPage) acceptBtn.click();
		  HtmlForm goBackForm = (HtmlForm) goBackPage.getFormByName("Form");
		  HtmlSubmitInput okBtn = (HtmlSubmitInput) goBackForm.getInputByName("BtnOK");
		  
		  HtmlPage resultPage = (HtmlPage) okBtn.click();
		  return resultPage;
	 }
			 
			
	 
	 public static void main (String args[]) throws Exception {
	  
		 try {
		 CourtBooker booker = new CourtBooker ("http://mail.theparklangleyclub.co.uk:82/source1/index.php");
		 booker.book ();
		 } catch (Exception ex){
			 EmailSender.send(ex);
		 }
	  
	 
	 }
	 
}
