package com.arithmetic.multiplication;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MultiplicationBLogic {
	public int execute(int a, int b) {
		System.out.println(" MultiplicationBLogic.execute(" + a + "," + b + ")");
		return a * b;
	}


}
