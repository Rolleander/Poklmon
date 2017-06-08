package com.broll.pokllib.script;

import java.util.List;

import com.broll.pokllib.script.syntax.Value;
import com.broll.pokllib.script.syntax.VariableException;

public class ScriptTest {

	public static void main(String[] args) {
		new ScriptTest();
	}

	public ScriptTest() {
		ScriptModul modul = new ScriptModul();
		modul.setScriptInterface(new Scope());

		String script = "var test init 15 \n label t \n do hallo @ test, #hallo \n set test mul 0.99 \n is test > 0.5 \n goto t \n end \n do ende "
				+ "\n var check init true \n do info @ check \n set check to false \n do info @ check \n var rand init %random% \n do disp @ rand \n is rand = rand \n do disp @ true \n else \n do disp @ false \n end ";
		script="is $random() > 0";
		script="var test init 10 \n label loop \n 	do text @ #Countdown: ,test,#! \n set test sub 1 \n is test >=0 \n goto loop \n else \n do test @ 0 \n is $test() = 0 ";
	

		try {
			modul.runFullScript(script);

		} catch (ScriptException e) {
			e.printStackTrace();
		}

		
		CodeSyntaxChecker codeSyntaxChecker = new CodeSyntaxChecker();
		try {
			codeSyntaxChecker.checkCode(script);
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	private class Scope extends ScriptInterface {

		@Override
		public void noCommand() {
		}

		@Override
		public void command(String cmd, List<Value> parameter) {
			String info = "call " + cmd + " @ ";
			for (Value value : parameter) {
				info += value.getValue() + " ; ";
			}
			System.out.println(info);
		}

		@Override
		public Object function(String fct, List<Value> parameter) {
			String info = "function " + fct + " (";
			for (Value value : parameter) {
				info += value.getValue() + " ; ";
			}
			info += ")";
			System.out.println(info);
			return 0;
		}

	}
}
