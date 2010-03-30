/**
 * @(#)FastSchedule.java
 *
 *
 * @author Daniel Ploch
 * @version 1.00 2010/3/29
 */
package edu.rpi.rocs.client.objectmodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FastSchedule
{

	ArrayList<Blocking> arr;

	public FastSchedule()
	{
		arr = new ArrayList<Blocking>();
		arr.add(new Blocking());
	}

	public void addCourse(Course C)
	{
		List<Section> sections = C.getSections();
		if (sections.size() == 0) return;

		HashSet<Blocking> setConv = new HashSet<Blocking>();
		for (Section S : sections) setConv.add(new Blocking(S));

		HashSet<Blocking> addition = new HashSet<Blocking>();
		for (Blocking A : setConv) for (Blocking B : arr)
		{
			Blocking B2 = getNew(A, B);
			if (B2 != null) addition.add(B2);
		}

		arr.clear();
		arr.addAll(addition);
	}

	public boolean test(Course C)
	{
		List<Section> sections = C.getSections();
		if (sections.size() == 0) return true;

		HashSet<Blocking> setConv = new HashSet<Blocking>();
		for (Section S : sections) setConv.add(new Blocking(S));
		for (Blocking A : setConv) for (Blocking B : arr)
			if (getNew(A, B) != null) return true;
		return false;
	}

	public static Blocking getNew(Blocking A, Blocking B)
	{
		Blocking C = new Blocking();
		for (int i = 0; i < 42; i++)
		{
			if ((A.taken[i]&B.taken[i]) != 0) return null;
			C.taken[i] = A.taken[i]|B.taken[i];
		}
		return C;
	}

	static class Blocking
	{
		public int[] taken = new int[42];

		public Blocking()
		{

		}

		public Blocking(Section S)
		{
			for (Period P : S.getPeriods())
			{
				int t1 = P.getStartInt()/10;
				int t2 = (P.getEndInt()-1)/10;
				int k = t1/24;
				for (Integer i : P.getDays())	while (k <= t2/24)
				{
					int mod = 0xffffff;
					if (k == t1/24) mod = (mod>>(24-(t1%24)));
					if (k == t2/24) mod = ((mod>>(24-(t2%24)))<<(24-(t2%24)));
					taken[i.intValue()*6+k] = mod;
				}
			}
		}

		public boolean equals(Object o)
		{
			if (o instanceof Blocking)
			{
				Blocking B = (Blocking)o;
				for (int i = 0; i < 42; i++) if (taken[i] != B.taken[i]) return false;
				return true;
			}
			return false;
		}
	}

}