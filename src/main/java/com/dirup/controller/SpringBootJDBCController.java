package com.dirup.controller;

import java.io.InterruptedIOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dirup.service.EmailService;
import java.io.IOException;

@RestController
public class SpringBootJDBCController {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Value("${cloud.aws.credentials.access-key}")
	private String awsKeyId;

	@Value("${cloud.aws.credentials.secret-key}")
	private String awsKeySecret;

	@Value("${cloud.aws.region.static}")
	private String awsRegion;

	@Value("${app.awsServices.bucketName}")
	private String bucketName;

	@Value("${app.endpointUrl}")
	private String endpointUrl;
	@Autowired
	EmailService emailService;

	@RequestMapping(value="/saveData", method=RequestMethod.GET)	
	public void saveData() throws IOException 
	{

		Connection conn = null;
		Statement statement = null;

		try {
			//Make sure to choose appropriate Redshift Jdbc driver and its jar in classpath
			Class.forName("com.amazon.redshift.jdbc42.Driver");
			Properties props = new Properties();
			props.setProperty("user", "username***");
			props.setProperty("password", "password****");

			System.out.println("\n\nconnecting to database...\n\n");

			conn = DriverManager.getConnection("jdbc:redshift://********url-to-redshift.redshift.amazonaws.com:5439/example-database", props);

			System.out.println("\n\nConnection made!\n\n");

			statement = conn.createStatement();

			String command = "COPY my_table from 's3://path/to/csv/example.csv' CREDENTIALS 'aws_access_key_id=awsKeyId;aws_secret_access_key=awsKeySecret' CSV DELIMITER ',' ignoreheader 1";

			System.out.println("\n\nExecuting...\n\n");

			statement.executeUpdate(command);
			//you must need to commit, if you realy want to have data saved, otherwise it will not appear if you query from other session.
			conn.commit();
			System.out.println("\n\n Data copied.\n\n");
			statement.close();
			conn.close();
		} catch (Exception ex) {
			EmailService.sendEmail();
			ex.printStackTrace();
		}
	}

}
