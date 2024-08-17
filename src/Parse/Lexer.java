package Parse;

import java.io.*;

public class Lexer {
	private char peek = ' ';
	private String[] keyword = { "begin", "end" };
	private char[] operators = { '+', '-', '*', '/', '=', '(', ')' };// 操作符
	private char[] separators = { ',', ';', '{', '}' };// 分隔符
	private StringBuffer buffer = new StringBuffer();
	private String filename;
	private String strToken = "";
	public  String str="";
	public static void main(String[] args) throws IOException {
		Lexer l = new Lexer("./input/input.txt");
		l.readFile();
		l.scan();
		l.sysout();
	}
	public void sysout()
	{
		System.out.println(str);
	}
	
	public String  getStr()
	{
		return str;
	}
	
	/**
	 * 构造函数
	 * @param filename
	 */
	public Lexer(String filename) {
		this.filename = filename;
	}

	/**
	 * 将源程序读入到缓冲区
	 */
	public void readFile() {
		BufferedReader br;
		try {
			FileReader file = new FileReader(this.filename);
			br = new BufferedReader(file);
			int temp = 0;
			while ((temp = br.read()) != -1) {
				buffer.append((char) temp);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("源文件未找到！");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("读写文件异常!");
			e.printStackTrace();
		}
	}

	/**
	 * 写入文件
	 * @param str	写入的字符串
	 */
	public void writeFile(String str) {
		try {
			File file = new File("./input/output.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsolutePath(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(str);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public  void scan() throws IOException {
		int i = 0;
		int line = 1;
		int row = 0;
		while (i < buffer.length()) {
			peek = buffer.charAt(i);
			i++;
			for (;; peek = buffer.charAt(i), i++) {
				if (peek == '	' || peek == '\t') {
					row++;
					continue;
				} else if (peek == '\n') {
					row = 0;
					line = line + 1;
				} else
					break;
			}
			row++;
			if (Character.isLetter(peek))// 如果peek是字母
			{
				int k = 0, r = 0;
				strToken += peek;
				for (; i < buffer.length() && (Character.isLetter(peek) || Character.isDigit(peek)); i++) {
					r++;
					peek = buffer.charAt(i);
					strToken += peek;
					if (keyword[0].equals(strToken))
						k = 1;
					if (keyword[1].equals(strToken))
						k = 2;
				}
				if (k == 1) {
					str+="b";
				} else if (k == 2) {
					str+="e";
				} else if (peek != '\n' || peek != '\r') {
					str+="i";
				}
				strToken = "";
				row += r;
			}
			if (Character.isDigit(peek))// 如果是数字
			{
				int s = 0;
				for (; i < buffer.length() && (Character.isDigit(peek) || peek == '.'); i++) {
					s++;
					peek = buffer.charAt(i);
					if (Character.isLetter(peek))
						break;
				}
				str+="n";
				row += s;
			}
			for (int j = 0; j < operators.length; j++)// 如果是操作符
			{
				if (peek == operators[j]) {
					strToken += peek;
					str+=strToken;
				}
				strToken = "";
			}
			for (int j = 0; j < separators.length; j++)// 如果是分隔符
			{
				if (peek == separators[j]) {
					strToken += peek;
					str+=strToken;
				}
				strToken = "";
			}
		}
	}
}
