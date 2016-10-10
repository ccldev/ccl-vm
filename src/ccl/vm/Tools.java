package ccl.vm;

import java.util.ArrayList;

public class Tools {

	@SafeVarargs
	public static <T> T[] link(T[]... arrays){
		ArrayList<T> list = new ArrayList<T>();
		for(int i = 0; i < arrays.length; i++){
			T[] arr = arrays[i];
			for(int k = 0; k < arr.length; k++){
				list.add(arr[k]);
			}
		}
		return list.toArray(arrays[0]);
	}
	
}
