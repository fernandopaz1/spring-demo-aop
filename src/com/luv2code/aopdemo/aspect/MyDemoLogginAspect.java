package com.luv2code.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyDemoLogginAspect {

	// aca agregamos todos los advices relativos al loggin
	
	// agregamos un advice @Before que se aejecuta antes del metodo
	
	// este aspect se va a ajecutar para todos los metodos con la signature public void addAccount()
	// el pointcut expresion es el predicado que matchea el metodo
	// sobre el cual el advice deberia ejecutarse
	@Before("execution(* add*(com.luv2code.aopdemo.Account))")
	public void beforeAddAccountAdvice() {
		System.out.println("\n======>>> Executing @Before advice");
	}
	
	
}
