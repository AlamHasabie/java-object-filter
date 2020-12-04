package sampleclass;

public class A
{
	public int a;
	public B b;
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