package kihonJouhouGijutusya.H21spring;

public class GapBuffer {
	private static final int INITIAL_GAP_SIZE = 128;
	private char[] buffer;
	private int gapOffset = 0;
	private int gapSize = INITIAL_GAP_SIZE;

	/**
	 * char配列の生成
	 * @param initialText
	 * gapSize + initialTextの長さ分の配列。
	 * 先頭にgapSize その後にinitialTextが入る。
	 */
	GapBuffer(String initialText) {
		buffer = new char[initialText.length() + gapSize];
		System.arraycopy(initialText.toCharArray(), 0, buffer, gapSize, initialText.length());
	}

	/**
	 * 文字挿入
	 * @param offset
	 * @param ch
	 */
	void insert(int offset, char ch) {
		confirmGap(offset);
		buffer[gapOffset++] = ch;
		gapSize--;
	}

	void delete(int offset) {
		if(length() == 0)
			return;
		confirmGap(offset + 1);
		gapOffset--;
		gapSize++;
	}

	char charAt(int offset) {
		if(offset >= gapOffset)
			offset += gapSize;
		return buffer[offset];
	}

	int length() { return buffer.length; }

	private void confirmGap(int newGapOffset) {
		if(gapSize == 0) {
			char[] temp = new char[buffer.length + INITIAL_GAP_SIZE];
			System.arraycopy(buffer, 0, temp, 0, buffer.length);
			gapOffset = buffer.length;
			gapSize = INITIAL_GAP_SIZE;
			buffer = temp;
		}
		if(newGapOffset < gapOffset) {
			System.arraycopy(buffer, newGapOffset, buffer,
					newGapOffset + gapSize, gapOffset - newGapOffset);
		} else {
			System.arraycopy(buffer, gapOffset + gapSize,
					buffer,	gapOffset, newGapOffset - gapOffset);
		}
		gapOffset = newGapOffset;
	}

	public static void main(String[] args) {
		GapBuffer gb = new GapBuffer("あいうえお");
		gb.insert(3, 'か');

		for(char c : gb.buffer) {
			System.out.println(c);
		}
	}
}
