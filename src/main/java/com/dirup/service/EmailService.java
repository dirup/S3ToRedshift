package com.dirup.service;


import java.io.IOException;

import javax.print.attribute.standard.Destination;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ResourceProperties.Content;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.internal.eventstreaming.Message;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.SendEmailRequest; 

	public class EmailService {

	  // Replace xxxx@example.com with your "From" address.
	  @Value("${app.toEmail}")
		private String TO;

	  // Replace xxxx@example.com with a "To" address. If your account
	  // is still in the sandbox, this address must be verified.
	  @Value("${app.fromEmail}")
		private String FROM;

	  // The configuration set to use for this email. If you do not want to use a
	  // configuration set, comment the following variable and the 
	  // .withConfigurationSetName(CONFIGSET); argument below.
	  static final String CONFIGSET = "ConfigSet";

	  // The subject line for the email.
	  static final String SUBJECT = "Amazon test email (AWS SDK for Java)";
	  
	  // The HTML body for the email.
	  static final String HTMLBODY = "<h1>Amazon test (AWS SDK for Java)</h1>"
	      + "<p>This email was sent to notify the error";

	  // The email body for recipients with non-HTML email clients.
	  static final String TEXTBODY = "This email was sent through Amazon for the error ";

	  public static void sendEmail() throws IOException {

	    try {
	      AmazonSimpleEmailService client = 
	          AmazonSimpleEmailServiceClientBuilder.standard()
	          // Replace US_WEST_2 with the AWS Region you're using for
	          // Amazon SES.
	            .withRegion(Regions.US_WEST_2).build();
	      SendEmailRequest request = new SendEmailRequest()
	          .withDestination(
	              new Destination().withToAddresses(TO))
	          .withMessage(new Message()
	              .withBody(new Body()
	                  .withHtml(new Content()
	                      .withCharset("UTF-8").withData(HTMLBODY))
	                  .withText(new Content()
	                      .withCharset("UTF-8").withData(TEXTBODY)))
	              .withSubject(new Content()
	                  .withCharset("UTF-8").withData(SUBJECT)))
	          .withSource(FROM)
	          // Comment or remove the next line if you are not using a
	          // configuration set
	          .withConfigurationSetName(CONFIGSET);
	      client.sendEmail(request);
	      System.out.println("Email sent!");
	    } catch (Exception ex) {
	      System.out.println("The email was not sent. Error message: " 
	          + ex.getMessage());
	    }
	  }

	
}
