package tiny;

public class Token {

	//variables
	private TokenType token;
	private String string;
	
	//constructor
	public Token( TokenType token, String string ){

		this.token = token;
		this.string = string;

		// get token
		if( token == TokenType.IDENTIFIER )
			this.getType();
	}

	//function to return tokens
	private void getType(){

                if(this.string.compareToIgnoreCase("int") == 0)
                        this.token = TokenType.INTEGER;
                else if( this.string.compareToIgnoreCase("if" ) == 0 )
			this.token = TokenType.IF;

		else if( this.string.compareToIgnoreCase("then" ) == 0 )
			this.token= TokenType.THEN;

		else if( this.string.compareToIgnoreCase("else" ) == 0)
			this.token= TokenType.ELSE;

		else if( this.string.compareToIgnoreCase("end" ) == 0 )
			this.token= TokenType.END;

		else if( this.string.compareToIgnoreCase("read" ) == 0)
			this.token = TokenType.READ;

		else if( this.string.compareToIgnoreCase("write" ) == 0 )
			this.token = TokenType.WRITE;

		else if( this.string.compareToIgnoreCase("repeat" ) == 0 )
			this.token = TokenType.REPEAT;

		else if( this.string.compareToIgnoreCase("until" ) == 0 )
			this.token = TokenType.UNTIL;
	}

        @Override
	public String toString(){
		return "-> " + this.string + spacing(this.string) + " | " + this.token  ;
	}

	public TokenType getToken(){		
		return this.token;
	}
        
        public String spacing(String s){
            String spaces = "";
            for(int i = s.length(); i <= 6; i++ ){
                spaces += " ";
            }
            return spaces;
        }
}
