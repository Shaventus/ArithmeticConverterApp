package com.jsfcourse.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ViewScoped
@ManagedBean
public class PrefixBB implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String infixstring;
	private String postfixstring;
	private String prefixstring;
	
	private String postfixresutlstring = "";
	private String infixresutlstring = "";
	private String prefixresutlstring = "";
	
	private List<ConversionStep> prefixconversionlist = new ArrayList<ConversionStep>();
	private List<ConversionStep> postfixconversionlist = new ArrayList<ConversionStep>();
	private List<ConversionStep> infixconversionlist = new ArrayList<ConversionStep>();
	
	public void toPrefix(){
		prefixconversionlist = new ArrayList<ConversionStep>();
		String prefix = "";
		Stack<String> stack = new Stack<String>();
		StringBuilder sb = new StringBuilder();
		sb.append(infixstring);
		String a = sb.reverse().toString();
		String[] infixtable = a.split("\\s+");
		ConversionStep cs = new ConversionStep();
		for(int i = 0;i < infixtable.length;i++) {
			cs = new ConversionStep();
			cs.setSymbol(infixtable[i]);
			switch(infixtable[i]){
				case ")":
					stack.push(infixtable[i]);
					break;
				case "(":
					while(!stack.lastElement().equals(")")) {
						prefix = stack.pop() + " " + prefix;
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
								while(!stack.isEmpty() && (prio(infixtable[i]) < prio(stack.lastElement()))) {
									prefix = stack.pop() + " " + prefix;	
								}
								stack.push(infixtable[i]);
							}
						}
					break;
				default:
					prefix = infixtable[i] + " " + prefix;
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
			prefixconversionlist.add(cs);
		}
		
		while(!stack.isEmpty()) {
			prefix = stack.pop() + " " + prefix;
		}
		
		prefixresutlstring = prefix;
	}
	
	public void toInfix(){
		infixconversionlist = new ArrayList<ConversionStep>();
		String[] prefixtable = prefixstring.split("\\s+");
		Stack<String> operand= new Stack<String>();
		String infix = "";
		ConversionStep cs = new ConversionStep();
		for(int i = prefixtable.length-1;i >= 0;i--) {
			cs = new ConversionStep();
			cs.setSymbol(prefixtable[i]);
			switch(prefixtable[i]){
				case "(":
				case ")":
				case "-":
				case "+":
				case "*":
				case "/":
				case "NEG":
				case "^":
					String l;
					String f;
					l = operand.pop();
					f = operand.pop();
					operand.push("( " + l + " " + prefixtable[i] + " " + f + " ) ");
					break;
				case "sin":
				case "cos":
				case "tg":
				case "ctg":
					break;
				default:
					operand.push(prefixtable[i]);
					break;
			}
			
			String stackstring = "";
			for(int j = 0; j < operand.size();j++){
				stackstring += operand.get(j) + ", ";
			}
			if(stackstring.isEmpty()){
				cs.setStack("empty");
			} else {
				cs.setStack(stackstring);
			}
			infixconversionlist.add(cs);
		}
		
		while(!operand.isEmpty()) {
			infix += operand.pop();
		}
		
		infixresutlstring = infix;
	}
	
	public void PostfixToPrefix(){
		postfixconversionlist = new ArrayList<ConversionStep>();
		String[] postixtable = postfixstring.split("\\s+");
		Stack<String> operand= new Stack<String>();
		String prefix = "";
		ConversionStep cs = new ConversionStep();
		for(int i = 0;i < postixtable.length;i++) {
			cs = new ConversionStep();
			cs.setSymbol(postixtable[i]);
			switch(postixtable[i]){
				case "(":
				case ")":
				case "-":
				case "+":
				case "*":
				case "/":
				case "NEG":
				case "^":
					String l;
					String f;
					l = operand.pop();
					f = operand.pop();
					operand.push(postixtable[i] + " " + f + " " + l + " ");
					break;
				case "sin":
				case "cos":
				case "tg":
				case "ctg":
					break;
				default:
					operand.push(postixtable[i]);
					break;
			}
			String stackstring = "";
			for(int j = 0; j < operand.size();j++){
				stackstring += operand.get(j) + ", ";
			}
			if(stackstring.isEmpty()){
				cs.setStack("empty");
			} else {
				cs.setStack(stackstring);
			}
			postfixconversionlist.add(cs);
		}
		
		while(!operand.isEmpty()) {
			prefix += operand.pop();
		}
		
		postfixresutlstring = prefix;
	}
	
	public int prio(String s) {
		switch(s) {
			case ")":
				return 0;
			case "+":
			case "-":
			case "(":
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

	public String getPostfixresutlstring() {
		return postfixresutlstring;
	}

	public void setPostfixresutlstring(String postfixresutlstring) {
		this.postfixresutlstring = postfixresutlstring;
	}

	public String getPostfixstring() {
		return postfixstring;
	}

	public void setPostfixstring(String postfixstring) {
		this.postfixstring = postfixstring;
	}

	public String getInfixresutlstring() {
		return infixresutlstring;
	}

	public void setInfixresutlstring(String infixresutlstring) {
		this.infixresutlstring = infixresutlstring;
	}

	public List<ConversionStep> getPostfixconversionlist() {
		return postfixconversionlist;
	}

	public void setPostfixconversionlist(List<ConversionStep> postfixconversionlist) {
		this.postfixconversionlist = postfixconversionlist;
	}

	public List<ConversionStep> getInfixconversionlist() {
		return infixconversionlist;
	}

	public void setInfixconversionlist(List<ConversionStep> infixconversionlist) {
		this.infixconversionlist = infixconversionlist;
	}

	public String getPrefixstring() {
		return prefixstring;
	}

	public void setPrefixstring(String prefixstring) {
		this.prefixstring = prefixstring;
	}

	public String getPrefixresutlstring() {
		return prefixresutlstring;
	}

	public void setPrefixresutlstring(String prefixresutlstring) {
		this.prefixresutlstring = prefixresutlstring;
	}

	public List<ConversionStep> getPrefixconversionlist() {
		return prefixconversionlist;
	}

	public void setPrefixconversionlist(List<ConversionStep> prefixconversionlist) {
		this.prefixconversionlist = prefixconversionlist;
	}

}
