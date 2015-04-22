/*
 * This forms part of PSLite, a toy language inspired by PostScript
 * designed for tinkering in ANTLR.
 *
 * David Evans, University of Derby, 2013
 *
 * This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
 * Unported License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative
 * Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041,
 * USA.
 */

public class StackElement<T> {
	public enum StackElementType {
		INTEGER,
		STRING,
		BOOL
	};

	StackElementType type;
	T value;

	public StackElement(StackElementType _t, T _v) {
		type = _t;
		value = _v;
	}

	public StackElementType getType() {
		return type;
	}

	public T getValue() {
		return value;
	}

	public String toString() {
		return value.toString();
	}
}
