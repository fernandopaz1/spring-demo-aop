package com.luv2code.aopdemo;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.luv2code.aopdemo.dao.AccountDAO;
import com.luv2code.aopdemo.dao.MembershipDAO;

public class MainDemoApp {

	public static void main(String[] args) {

		// read spring config java class
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DemoConfig.class);

		// get the bean from spring container
		AccountDAO theAccountDAO = context.getBean("accountDAO", AccountDAO.class);
		
		
		// call the buisnes method
		theAccountDAO.addAccount();
		
		
		// lo llamamos otra vez
		System.out.println("Lo llamamos otra vez ");
		theAccountDAO.addAccount();
		
		System.out.println("LLamamos al addAccount de membership ");
		MembershipDAO theMembershipDAO = context.getBean("membershipDAO", MembershipDAO.class);

		theMembershipDAO.addAccount();

		// close the context
		context.close();

	}

}
