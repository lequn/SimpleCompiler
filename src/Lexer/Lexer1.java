package Lexer;

import java.io.*;

public class Lexer1 {
	private char peek = ' ';
	private String[] keyword = { "begin", "end" };
	private char[] operators = { '+', '-', '*', '/', '=', '(', ')' };// ������
	private char[] separators = { ',', ';', '{', '}' };// �ָ���
	private StringBuffer buffer = new StringBuffer();
	private String filename;
	private String strToken = "";

	public static void main(String[] args) throws IOException {
		Lexer1 l = new Lexer1("./input/input.txt");
		l.readFile();
		l.scan();
	}

	/**
	 * ���캯��
	 * @param filename
	 */
	public Lexer1(String filename) {
		this.filename = filename;
	}

	/**
	 * ��Դ������뵽������
	 */
	private void readFile() {
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
			System.out.println("Դ�ļ�δ�ҵ���");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("��д�ļ��쳣!");
			e.printStackTrace();
		}
	}

	/**
	 * д���ļ�
	 * @param str	д����ַ���
	 */
	private void writeFile(String str) {
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

	private void scan() throws IOException {
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
			if (Character.isLetter(peek))// ���peek����ĸ
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
					writeFile("b" + "(��" + line + "��" + "��" + row + "��)\n");
				} else if (k == 2) {
					writeFile("e" + "(��" + line + "��" + "��" + row + "��)\n");
				} else if (peek != '\n' || peek != '\r') {
					writeFile("i" + "(��" + line + "��" + "��" + row + "��)\n");
				}
				strToken = "";
				row += r;
			}
			if (Character.isDigit(peek))// ���������
			{
				int s = 0;
				for (; i < buffer.length() && (Character.isDigit(peek) || peek == '.'); i++) {
					s++;
					peek = buffer.charAt(i);
					if (Character.isLetter(peek))
						break;
				}
				writeFile("n" + "(��" + line + "��" + "��" + row + "��)\n");
				row += s;
			}
			for (int j = 0; j < operators.length; j++)// ����ǲ�����
			{
				if (peek == operators[j]) {
					strToken += peek;
					writeFile(strToken + "(��" + line + "��" + "��" + row + "��)\n");
				}
				strToken = "";
			}
			for (int j = 0; j < separators.length; j++)// ����Ƿָ���
			{
				if (peek == separators[j]) {
					strToken += peek;
					writeFile(strToken + "(��" + line + "��" + "��" + row + "��)\n");
				}
				strToken = "";
			}
		}
	}
}
