package Parse;
import java.io.*;
import java.util.Stack;

public class Parse {
	public static void main(String[] args) throws IOException {
		Lexer l = new Lexer("./input/input.txt");
		l.readFile();
		l.scan();
		Parse p = new Parse(l.getStr() + "#");
		p.analysis();
	}

	public char[][] f = { { 'P', 'b', }, { 'D', 'i' }, { 'C', 'e' },
			{ 'C', ';' }, { 'S', 'i' }, { 'E', 'i' }, { 'E', '(' },
			{ 'E', 'n' }, { 'G', 'e' }, { 'G', ')' }, { 'G', ';' },
			{ 'G', '+' }, { 'G', '-' }, { 'T', 'i' }, { 'T', '(' },
			{ 'T', 'n' }, { 'U', 'e' }, { 'U', ';' }, { 'U', ')' },
			{ 'U', '*' }, { 'U', '/' }, { 'U', '+' }, { 'U', '-' },
			{ 'F', 'i' }, { 'F', '(' }, { 'F', 'n' } };
	public String[] g = { "bDe", "SC", "k", ";SC", "i=E", "TG", "TG", "TG",
			"k", "k", "k", "+TG", "-TG", "FU", "FU", "FU", "k", "k", "k",
			"*FU", "/FU", "k", "k", "i", "(E)", "n" };
	public String str;
	public Stack<Character> stack = new Stack<Character>();
	public Character x;
	public Character a;

	public Parse(String str) {
		this.str = str;
	}

	private boolean isMarch(Character x2, Character a2) {
		boolean b = false;
		for (int i = 0; i < f.length; i++) {
			if ((f[i][0] == x2) && (f[i][1] == a2)) {
				b = true;
				break;
			}
		}
		return b;
	}

	public void analysis() {
		Boolean flag = true;
		int next = 1;
		int num = 1;
		String str1 = str, str2 = "",s="";//剩余输入串、产生式、栈
		stack.push('#');
		stack.push('P');
		s += "#";
		s += "P";
		a = str.charAt(0);
		System.out.println(num+" "+ s + "		" + str1);
		num++;
		while (flag) {
			x = stack.pop();
			s = s.substring(0, s.length() - 1);
			if ((x >= 'a' && x <= 'z') || x == '=' || x == '/' || x == '*'
					|| x == ';' || x == '(' || x == ')' || x == '+' || x == '-') {
				if (x == a) {
					a = str.charAt(next);
					str1 = str.substring(next);
					next++;
					System.out.println(num + " " + s + "		" + str1 + "		" + str2);
					num++;
				} else {
					System.out.println("错误！");
					return;
				}
			} else if (x == '#') {
				if (x == a) {
					flag = false;
				} else {
					System.out.println("错误！");
					return;
				}
			} else if (isMarch(x, a)) 
			{
				int i;
				for (i = 0; i < f.length; i++) 
				{
					if ((f[i][0] == x) && (f[i][1] == a)) 
					{
						break;
					}
				}
				str2 = f[i][0] + "->" + g[i];
				for (int l = g[i].length() - 1; l >= 0; l--) 
				{
					if (g[i].charAt(l) != 'k') 
					{
						stack.push(g[i].charAt(l));
						s += g[i].charAt(l);
					}
				}
				System.out.println(num + " " + s + "		" + str1 + "		" + str2);
				num++;
			}
		}
		System.out.println("语法分析成功！");
	}

}
