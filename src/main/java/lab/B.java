// $Id$
// $Revision$
// $Date$

package lab;


/**
 * public class B{ }
 *
 * @version $Revision$
 * @author gautamr
 */
public class B extends A {

	int ib = 11;
	String sb = "ss";
	
	public B() {
		System.out.println("B start");
		System.out.println(ib);
		System.out.println(sb);
		System.out.println("B end");
	}
}
