package sampleclass;

public class A
{
	public int a;
	private B b;
	private int c;
	public A(){
		a = 0;
		b = new B();
		c = 1;
	}

	public B b()
	{
		return b;
	}
}