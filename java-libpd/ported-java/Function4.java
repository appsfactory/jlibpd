// From: http://stackoverflow.com/questions/122407/whats-the-nearest-substitute-for-a-function-pointer-in-java
public interface Function4<S, T1, T2, T3, T4> {
	public S eval(T1 a, T2 b, T3 c, T4 d);
}
