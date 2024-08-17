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

	private String input;// ���봮
	public Stack<String> stateStack = new Stack<String>();// ״̬ջ
	public Stack<String> symbolStack = new Stack<String>();// ����ջ
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
			{ "25", "/", "R11" }, { "25", ")", "R11" }, { "25", ";", "R11" }, { "25", "e", "R11" } };// ������
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
	 * ���캯��
	 */
	public SLR0(String input) {
		this.input = input;
	}

	/*
	 * �﷨����
	 */
	public void analysis() {
		Boolean flag = true, f = false;
		String s;// ״̬
		String str = input, str1 = "", str2 = "";// ʣ����Ŵ�������ջ��״̬ջ
		String a = input.substring(0, 1);// ���ַ�
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
						str = input.substring(next);// ����ʣ�ಿ��
						next++;
						num++;

					}
					if (action[i][2].charAt(0) == 'R') {
						int j;
						for (j = 0; j < production[value[i]][1].length(); j++) {
							str1 = str1.substring(0, str1.length() - 1);
							String x = stateStack.pop();
							str2 = str2.substring(0, str2.length() - x.length());// ����״̬����Ԫ�أ��ʺ϶�λ��
						}
						symbolStack.push(production[value[i]][0]);
						num++;
						str1 += production[value[i]][0];
						str2 += returnS(stateStack.peek(), production[value[i]][0]);
						stateStack.push(returnS(stateStack.peek(), production[value[i]][0]));
					}
					if (action[i][2].charAt(0) == 'a') {
						flag = false;
						System.out.println("�﷨�����ɹ���");
						return;
					}
				}
			}
			if (f == false) {
				System.out.println("����");
			}
			System.out.println(num + ": 	" + str2 + "	" + str1 + "		" + str);
		}
		
	}

	private String returnS(String s, String a) {
		String b = null;
		char c = a.charAt(0);
		if (c < 'Z' && c > 'A')// ������봮Ϊ��д�����ս�����򷵻�goto���ж�Ӧ��ֵ
		{
			for (int i = 0; i < go.length; i++) {
				if (go[i][0].equals(s) && go[i][1].equals(a)) {
					b = go[i][2];
				}
			}
		} 
		else// ������봮ΪСд���ս�����򷵻�action���ж�Ӧ��jֵ
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
