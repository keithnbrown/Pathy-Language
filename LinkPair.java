
public class LinkPair {

	private Link a;
	private Link b;

	public LinkPair(Link _a, Link _b)
	{
		if (_a == null && _b == null)
		{
			throw new RuntimeException("Links A and B must be non-null");
		}
		if (_a == null)
		{
			throw new RuntimeException("Link A must be non-null");
		}
		if (_b == null)
		{
			throw new RuntimeException("Link B must be non-null");
		}
		if (_a == _b || _a.equals(_b))
		{
			throw new RuntimeException("Links must not be identical");
		}
		a = _a;
		b = _b;
	}

	public Link getA()
	{
		return a;
	}

	public Link getB()
	{
		return b;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//a and b must be invertible for this to work.
		result = prime * result + ((a == null) ? 0 : a.hashCode()) + ((b == null) ? 0 : b.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		LinkPair other = (LinkPair) obj;

		//link pairs equate differently than eclipse expects so i had to alter the auto-gen code.
		//Link pairs are equal as long as they contain the same Links, no matter the A/B designation. Direction is ignored when testing equality.
		//it logically works out like this: (A=a && B=b) ^ (A=b && B=a)

		if (a == null || b == null)
		{
			//neither can be null
			return false;
		}
		else if (!((a.equals(other.a) && b.equals(other.b)) ^ (a.equals(other.b) && b.equals(other.a))))
			return false;
		return true;
	}
}
