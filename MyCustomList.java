Requirement 

Create a Java Class that extend the ArrayList Class.
The Custom List - MyList will allow only 5 elements to be inserted and will not allow null to be inserted.
@Author Mohammad Javed , India
*******************************************************************************************************************


import java.util.*;
public  class MyList<T>  extends ArrayList<Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return super.size();
	}

	@Override
	public boolean add(Object e) {
		// TODO Auto-generated method stub
		boolean flag=false;
		try{
		if (e!=null && size()< 5){
			flag=super.add(e);
		}
		else{
			if (!(e!=null))
			throw new RuntimeException ("MyList-Exception : Null Value not allowed  !");
			else if(size()>=5)
		    throw new RuntimeException ("MyList-Exception : List Size exceeded. Only 5 elements allowed !");
			//System.out.println("Error: List Size excceded or Trying to Insert null");
		}
		}
		catch(Exception e1){
			e1.printStackTrace();
			System.exit(0);
		}
		return flag;
	}

	@Override
	public void add(int index, Object element) {
		// TODO Auto-generated method stub
		try{
		if (element!=null && index<5)
		super.add(index, element);
	  else {
		  
		  if (!(element!=null))
				throw new RuntimeException ("MyList-Exception : Null value not allowed  !");
				else if(index>=5)
			    throw new RuntimeException ("MyList-Exception : List index exceeded. Only 5 elements allowed !");
		      
	    }
		}
		catch(Exception e3){
			e3.printStackTrace();
			System.exit(0);
		}
		
	}
	
	
	
	/**
	 * To Display the List Elements.
	 * Input MyList
	 * return no
	 * 
	 * **/
	public void displayList(MyList list){
		System.out.println("\nList Elements are \n"+Arrays.toString(list.toArray()));   
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MyList m1 =new MyList();
		MyList m2 =new MyList();
		
		m1.add(1);
		m1.add(2);
		m1.add(3);
		m1.add(4);
		//Null Test Comment below and check
		m1.add(null);
		//
		m1.add(5);
	   // Size Test Comment below and check
		m1.add(6);
		
		//add(index,element) Comment below and check
		m2.add(1,null);
		// Index Test Comment below and check
		m2.add(6,8);
				
		
		//Print the List values 
		m1.displayList(m1);
		m2.displayList(m2);
		// System.out.println("\nList Elements are \n"+Arrays.toString(m1.toArray()));     
        
   
	}

}
