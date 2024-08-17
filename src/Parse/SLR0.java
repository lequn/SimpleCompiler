package Parse;

import java.io.IOException;
import java.util.Stack;

public class SLR0 {
	public static void main(String[] args) throws IOException {
		Lexer l = new Lexer("./input/input.txt");
		l.readFile();
		l.scan();
		SLR0 s = new SLR0(l.getStr() + "#");
		s.analysis();
	}

	private String input;// 输入串
	public Stack<String> stateStack = new Stack<String>();// 状态栈
	public Stack<String> symbolStack = new Stack<String>();// 符号栈
	public String[][] action = { { "0", "b", "S2" }, { "1", "#", "acc" }, { "2", "i", "S5" }, { "3", ";", "S7" },
			{ "3", "e", "S6" }, { "4", ";", "R3" }, { "4", "e", "R3" }, { "5", "=", "S8" }, { "6", "#", "R1" },
			{ "7", "i", "S5" }, { "8", "i", "S14" }, { "8", "n", "S15" }, { "8", "(", "S13" }, { "9", ";", "R2" },
			{ "9", "e", "R2" }, { "10", "+", "S16" }, { "10", "-", "S17" }, { "10", ";", "R4" }, { "10", "e", "R4" },
			{ "11", "+", "R7" }, { "11", "-", "R7" }, { "11", "*", "S18" }, { "11", "/", "S19" }, { "11", ")", "R7" },
			{ "11", ";", "R7" }, { "11", "e", "R7" }, { "12", "+", "R10" }, { "12", "-", "R10" }, { "12", "*", "R10" },
			{ "12", "/", "R10" }, { "12", ")", "R10" }, { "12", ";", "R10" }, { "12", "e", "R10" },
			{ "13", "i", "S14" }, { "13", "n", "S15" }, { "13", "(", "S13" }, { "14", "+", "R12" },
			{ "14", "-", "R12" }, { "14", "*", "R12" }, { "14", "/", "R12" }, { "14", ")", "R12" },
			{ "14", ";", "R12" }, { "14", "e", "R12" }, { "15", "+", "R13" }, { "15", "-", "R13" },
			{ "15", "*", "R13" }, { "15", "/", "R13" }, { "15", ")", "R13" }, { "15", ";", "R13" },
			{ "15", "e", "R13" }, { "16", "i", "S14" }, { "16", "n", "S15" }, { "16", "(", "S13" },
			{ "17", "i", "S14" }, { "17", "n", "S15" }, { "17", "(", "S13" }, { "18", "i", "S14" },
			{ "18", "n", "S15" }, { "18", "(", "S13" }, { "19", "i", "S14" }, { "19", "n", "S15" },
			{ "19", "(", "S13" }, { "20", "+", "S16" }, { "20", "-", "S17" }, { "20", ")", "S25" }, { "21", "+", "R5" },
			{ "21", "-", "R5" }, { "21", "*", "S18" }, { "21", "/", "S19" }, { "21", ")", "R5" }, { "21", ";", "R5" },
			{ "21", "e", "R5" }, { "22", "+", "R6" }, { "22", "-", "R6" }, { "22", "*", "S18" }, { "22", "/", "S19" },
			{ "22", ")", "R6" }, { "22", ";", "R6" }, { "22", "e", "R6" }, { "23", "+", "R8" }, { "23", "-", "R8" },
			{ "23", "*", "R8" }, { "23", "/", "R8" }, { "23", ")", "R8" }, { "23", ";", "R8" }, { "23", "e", "R8" },
			{ "24", "+", "R9" }, { "24", "-", "R9" }, { "24", "*", "R9" }, { "24", "/", "R9" }, { "24", ")", "R9" },
			{ "24", ";", "R9" }, { "24", "e", "R9" }, { "25", "+", "R11" }, { "25", "-", "R11" }, { "25", "*", "R11" },
			{ "25", "/", "R11" }, { "25", ")", "R11" }, { "25", ";", "R11" }, { "25", "e", "R11" } };// 动作表
	public int[] value = { 2, 0, 5, 7, 6, 3, 3, 8, 1, 5, 14, 15, 13, 2, 2, 16, 17, 4, 4, 7, 7, 18, 19, 7, 7, 7, 10, 10,
			10, 10, 10, 10, 10, 14, 15, 13, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 13, 14, 15, 13, 14, 15,
			13, 14, 15, 13, 14, 15, 13, 16, 17, 25, 5, 5, 18, 19, 5, 5, 5, 6, 6, 18, 19, 6, 6, 6, 8, 8, 8, 8, 8, 8, 8,
			9, 9, 9, 9, 9, 9, 9, 11, 11, 11, 11, 11, 11, 11 };
	public String[][] go = { { "0", "P", "1" }, { "2", "D", "3" }, { "2", "S", "4" }, { "7", "S", "9" },
			{ "8", "E", "10" }, { "8", "T", "11" }, { "8", "F", "12" }, { "13", "E", "20" }, { "13", "T", "11" },
			{ "13", "F", "12" }, { "16", "T", "21" }, { "16", "F", "12" }, { "17", "T", "22" }, { "17", "F", "12" },
			{ "18", "F", "23" }, { "19", "F", "24" } };
	public String[][] production = { {}, { "P", "bDe" }, { "D", "D;S" }, { "D", "S" }, { "S", "i=E" }, { "E", "E+T" },
			{ "E", "E-T" }, { "E", "T" }, { "T", "T*F" }, { "T", "T/F" }, { "T", "F" }, { "F", "(E)" }, { "F", "i" },
			{ "F", "n" } };

	/*
	 * 构造函数
	 */
	public SLR0(String input) {
		this.input = input;
	}

	/*
	 * 语法分析
	 */
	public void analysis() {
		Boolean flag = true, f = false;
		String s;// 状态
		String str = input, str1 = "", str2 = "";// 剩余符号串、符号栈、状态栈
		String a = input.substring(0, 1);// 首字符
		int next = 1;
		int num = 1;

		stateStack.push("0");
		symbolStack.push("#");

		str2 += "0";
		str1 += "#";
		System.out.println(num + ": 	" + stateStack.peek() + "	" + symbolStack.peek() + "	" + input);
		
		while (flag) {
			s = stateStack.peek();
			for (int i = 0; i < action.length; i++) {
				if ((action[i][0].equals(s)) && (action[i][1].equals(a))) {
					f = true;
					if (action[i][2].charAt(0) == 'S') {
						stateStack.push(returnS(s, a));
						str2 += returnS(s, a);
						symbolStack.push(a);
						str1 += a;

						a = input.substring(next, next + 1);
						str = input.substring(next);// 留下剩余部分
						next++;
						num++;

					}
					if (action[i][2].charAt(0) == 'R') {
						int j;
						for (j = 0; j < production[value[i]][1].length(); j++) {
							str1 = str1.substring(0, str1.length() - 1);
							String x = stateStack.pop();
							str2 = str2.substring(0, str2.length() - x.length());// 弹出状态表中元素，适合多位数
						}
						symbolStack.push(production[value[i]][0]);
						num++;
						str1 += production[value[i]][0];
						str2 += returnS(stateStack.peek(), production[value[i]][0]);
						stateStack.push(returnS(stateStack.peek(), production[value[i]][0]));
					}
					if (action[i][2].charAt(0) == 'a') {
						flag = false;
						System.out.println("语法分析成功！");
						return;
					}
				}
			}
			if (f == false) {
				System.out.println("错误");
			}
			System.out.println(num + ": 	" + str2 + "	" + str1 + "		" + str);
		}
		
	}

	private String returnS(String s, String a) {
		String b = null;
		char c = a.charAt(0);
		if (c < 'Z' && c > 'A')// 如果输入串为大写即非终结符，则返回goto表中对应的值
		{
			for (int i = 0; i < go.length; i++) {
				if (go[i][0].equals(s) && go[i][1].equals(a)) {
					b = go[i][2];
				}
			}
		} 
		else// 如果输入串为小写即终结符，则返回action表中对应的j值
		{
			if ((c >= 'a' && c <= 'z') || c == '=' || c == '/' || c == '*' || c == ';' || c == '(' || c == ')'
					|| c == '+' || c == '-') {
				for (int i = 0; i < action.length; i++) {
					if (action[i][0].equals(s) && action[i][1].equals(a)) {
						b = action[i][2].replaceAll("[a-zA-Z]", "");
					}
				}
			}
		}
		return b;
	}
}
