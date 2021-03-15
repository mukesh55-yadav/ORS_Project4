package in.co.rays.proj4.util;

import java.util.Date;

/**
 * 
 * This class validates input data
 * 
 * @author MUKESH_TECHNIQUES
 *
 */

public class DataValidator {

    /**
     * 
     * Checks if value is Null
     * 
     * @param val
     * @return
     */
    public static boolean isNull(String val) {
        if (val == null || val.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * Checks if value is NOT Null
     * 
     * @param val
     * @return
     */
    public static boolean isNotNull(String val) {
        return !isNull(val);
    }
  
	/**
	 * 
	 * Checks if value is an Integer
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isInteger(String val) {
        if (isNotNull(val)) {
            try {
                int i = Integer.parseInt(val);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }

        } else {
            return false;
        }
    }
    
    /**
     * 
     * Checks if value is Long
     * 
     * @param val
     * @return
     */
	public static boolean isLong(String val) {
        if (isNotNull(val)) {
            try {
                long i = Long.parseLong(val);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }

        } else {
            return false;
        }
    }
    

    
    public static boolean isDate(String val) {

        Date d = null;
        if (isNotNull(val)) {
            d = DataUtility.getDate(val);
        }
        return d != null;
    }
    
    /**
     * 
     *  Checks if value is valid Email ID
     * 
     * @param val
     * @return
     */
    public static boolean isEmail(String val) {
        String emailreg = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (isNotNull(val)) {
            try {
                return val.matches(emailreg);
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return false;
        }
    }
    
    public static boolean isMobileNo(String val) {
    	String mobreg = "(0/91)?[6-9][0-9]{9}";
    	if (isNotNull(val))
    		try {
                return val.matches(mobreg);
            } catch (NumberFormatException e) {
                return false;
            }
    	else {
    		return false;
    	}
    }
    
    public static boolean isPhoneNo(String val) {
    	String phonereg = "(0/+91)?[0-9-][0-9-]{9,12}";
    	if (isNotNull(val))
    		try {
                return val.matches(phonereg);
            } catch (NumberFormatException e) {
                return false;
            }
    	else {
    		return false;
    	}
    }
    
    public static boolean isAddress(String val) {
    	String mobreg = "\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)";
    	if (isNotNull(val))
    		try {
                return val.matches(mobreg);
            } catch (NumberFormatException e) {
                return false;
            }
    	else {
    		return false;
    	}
    }
    
    
    /**
	 * check value is in range 
	 * @param val
	 * @return
	 */
	public static boolean isRange(String val) {

		System.out.println("isRange method run");
		
		String name ="^[a-zA-Z]{3,15}$";
		if (!val.matches(name)) {
			return true;
		} else {
			return false;
		}
	}
    
    /**
	 * Checks if value is name
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isName(String val) {

		String name = "^[A-Za-z]{2,25}$";
		if (val.matches(name)) {
			return true;
		} else {
			return false;
		}
	
	}
	
    /**
	 * Checks if value is name
	 * 
	 * @param val
	 * @return
	 */
	public static boolean isNameNumber(String val) {

		String name = "^[a-zA-Z0-9._ ]{2,50}+$";
		if (val.matches(name)) {
			return true;
		} else {
			return false;
		}
	
	}
 
	public static boolean isRollNO(String val) {
		//	String passregex = "^[0-9]{5}$";
			String passregex = "^([0-9]{2}[a-zA-Z]{2}[0-9]{2})$";
			if (!val.matches(passregex)) {
				return true;
			} else {
				return false;
			}
		}
    
    /**
	 * Checks if value is Password
	 * 
	 * @param val
	 * @return boolean
	 */
	/*This regex will enforce these rules:

		At least one upper case English letter, (?=.*?[A-Z])
		At least one lower case English letter, (?=.*?[a-z])
		At least one digit, (?=.*?[0-9])
		At least one special character, (?=.*?[#?!@$%^&*-])
		Minimum eight in length .{4,} (with the anchors)
	 */
	public static boolean isPassword(String val) {
		// String passregex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{4,}$";
		String passregex = "^[a-zA-Z0-9](?=.*?[#?!@$%^&*-]).{4,}$";
		
		if (val.matches(passregex))
		{
			return true;
		} 
		else
		{
			return false;
		}
	}
	
	public static boolean isMarks(String val) {
		
		String passregex = "(0/1)?[0-9][0-9]{9}";

		if (val.matches(passregex))
		{
			return true;
		} 
		else
		{
			return false;
		}
	}


    //  Test above methods
    public static void main(String[] args) {

  /*    System.out.println("Not Null 2 : " + isNotNull("ABC"));
        System.out.println("Not Null 3 : " + isNotNull(null));
        System.out.println("Not Null 4 : " + isNull("123"));

        System.out.println("Is Int " + isInteger(null));
        System.out.println("Is Marks " + isMarks("12"));*/
        System.out.println("Is NAME " + isName("abc#"));
        System.out.println("Is NAMENUMBER " + isNameNumber("12a3"));
        System.out.println("Is NAMENUMBER " + isPassword("ABC12@3abc"));
    }

}
