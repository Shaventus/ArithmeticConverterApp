package com.jsfcourse.main;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean
public class PostfixBB implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String infixstring;
	private String postfixstring = "";
	private List<ConversionStep> conversionlist = new ArrayList<ConversionStep>();
	
	public void toPostfix(){
		conversionlist = new ArrayList<ConversionStep>();
		String[] infixtable = infixstring.split("\\s+");
		String postfix = "";
		Stack<String> stack = new Stack<String>();
		ConversionStep cs = new ConversionStep();
		for(int i = 0; i < infixtable.length;i++){
			cs = new ConversionStep();
			cs.setSymbol(infixtable[i]);
			
			switch(infixtable[i]){
				case "(":
					stack.push(infixtable[i]);
					break;
				case ")":
					while(!stack.lastElement().equals("(")) {
						postfix += stack.pop() + " ";
					}
					stack.pop();
					break;
				case "-":
				case "+":
				case "*":
				case "/":
				case "NEG":
				case "^":
				case "sin":
				case "cos":
				case "tg":
				case "ctg":
						if(stack.isEmpty()) {
							stack.push(infixtable[i]);
						} else {
							if(prio(infixtable[i]) > prio(stack.lastElement())) {
								stack.push(infixtable[i]);
							} else {
								while(!stack.isEmpty() && (prio(infixtable[i]) <= prio(stack.lastElement()))) {
									postfix += stack.pop() + " ";	
								}
								stack.push(infixtable[i]);
							}
						}
						break;
				default:
					postfix += infixtable[i] + " ";
					break;
			}
			String stackstring = "";
			for(int j = 0; j < stack.size();j++){
				stackstring += stack.get(j) + ", ";
			}
			if(stackstring.isEmpty()){
				cs.setStack("empty");
			} else {
				cs.setStack(stackstring);
			}
			cs.setResult(postfix);
			conversionlist.add(cs);
		}
		
		while(!stack.isEmpty()) {
			postfix += stack.pop() + " ";
		}
		
		postfixstring = postfix;
	}
	
	public int prio(String s) {
		switch(s) {
			case "(":
				return 0;
			case "+":
			case "-":
			case ")":
				return 1;
			case "*":
			case "/":
			case "NEG":
				return 2;
			case "^":
				return 3;
			case "sin":
			case "cos":
			case "tg":
			case "ctg":
				return 4;
		}
		return 0;
	}

	public String getInfixstring() {
		return infixstring;
	}

	public void setInfixstring(String infixstring) {
		this.infixstring = infixstring;
	}

	public String getPostfixstring() {
		return postfixstring;
	}

	public void setPostfixstring(String postfixstring) {
		this.postfixstring = postfixstring;
	}

	public List<ConversionStep> getConversionlist() {
		return conversionlist;
	}

	public void setConversionlist(List<ConversionStep> conversionlist) {
		this.conversionlist = conversionlist;
	}
	
}
