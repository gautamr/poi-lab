// $Id$
// $Revision$
// $Date$

package lab.hash;


/**
 * public class HashFunction{ }
 *
 * @version $Revision$
 * @author gautamr
 */
public class HashFunction {

	public int hash(Object o) {
		return (o.hashCode() << 31) % 10;
	}
}
