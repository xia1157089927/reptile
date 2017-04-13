package exception;

public class RuleException extends RuntimeException {

	private static final long serialVersionUID = 5962084033311611416L;
	
	public RuleException() {  
        super();  
    }  
  
    public RuleException(String message, Throwable cause) {  
        super(message, cause);  
    }  
  
    public RuleException(String message) {  
        super(message);  
    }  
  
    public RuleException(Throwable cause) {  
        super(cause);  
    }  
	

}
