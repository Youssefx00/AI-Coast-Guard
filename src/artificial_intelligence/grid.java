package artificial_intelligence;

import java.util.Arrays;
import java.util.Vector;

public class grid {
int M;
int N;
int C;
Vector<String> thegrid;

public grid(String input) {
	String[] temp = input.split(";");
	//	Now The String is split into Three Different sectors and should look like this
	//  [m,n; C ; grid] m and n need to be split again
	//  [0  , 1 , 2]
	
	//C Extraction
	C = Integer.parseInt(temp[1]);
	
	
	
	//M and N Extraction
	String[] MandN = temp[0].split(",");
	M = Integer.parseInt(MandN[0]);
	N = Integer.parseInt(MandN[1]);
	
	
	//Grid Extraction
	String[] gridAlone = temp[3].split(",");
	
	
	Vector<String> vec = new Vector<String>();
	vec.addAll(Arrays.asList(gridAlone));
	thegrid = vec;

}
}
