// From: http://stackoverflow.com/questions/122407/whats-the-nearest-substitute-for-a-function-pointer-in-java
public interface Function2<S, T1, T2> {
	public S eval(T1 a, T2 b);
}
