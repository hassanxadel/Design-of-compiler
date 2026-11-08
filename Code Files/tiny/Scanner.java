package tiny;
import java.util.ArrayList;


public class Scanner {
    
	private String temp;
	private boolean eoi = false;
	States currentState;
	private ArrayList<Token> tokens;
	private String code;
	private int currentChar = 0;

	/*--- Constructor ---*/
        public Scanner(String str){
            this.currentState = States.START;
            this.temp = new String();
            this.tokens = new ArrayList<>();
            this.code = str;
        }
        
        /*--- gets next character ---*/
	private char nextChar(){
		char ch = this.code.charAt( this.currentChar++ );
                this.temp = this.temp + ch;
		return ch;
	}

        /*--- gets previous character ---*/
	private void prevChar(){
		this.currentChar--;
		this.temp = this.temp.substring( 0, this.temp.length()-1 );
	}

	/*--- prevents the user from typing in a huge code that exceeds
              the maximum readable code ---*/
	private boolean hasMoreChars(){
		this.eoi = !( this.currentChar < this.code.length() );
		return !(this.eoi);
	}

	/*--- clears the temp string ---*/
	private void clearTemp(){
		this.temp = "";
	}

	/*--- returns the token that corresponds to the current state ---*/
	private TokenType stateToToken( States state ){
		TokenType token;
		switch( state ){

		case INASSIGN:
			token = TokenType.ASSIGN;
			break;

		case INID:
			token=TokenType.IDENTIFIER;
			break;

		case INNUM:
			token=TokenType.NUMBER;
			break;

		default:
			token=TokenType.NOTOKEN;
		}
		return token;
	}

	/*--- returns the token that corresponds to the symbol at hand ---*/
	private TokenType symbolToToken( char ch ){
		TokenType token;
		switch( ch ){
                    
                case '(':
			token=TokenType.LPAREN;
			break;

		case ')':
			token=TokenType.RPAREN;
			break;    
                        
		case '+':
			token=TokenType.PLUS;
			break;

		case '-':
			token=TokenType.MINUS;
			break;

		case '*':
			token=TokenType.MLT;
			break;

		case '/':
			token=TokenType.DIV;
			break;  

		case '=':
			token=TokenType.EQ;
			break;

		case '<':
			token=TokenType.ST;
			break;
                
                case '>':
			token=TokenType.GT;
			break;

		case ';':
			token=TokenType.SEMICOL;
			break;

		default:
			token=TokenType.NOTOKEN;
		}

		return token;
	}

	/*--- returns the next token code ---*/
	private Token getNextToken(){
		TokenType token = TokenType.NOTOKEN;
		while( this.currentState != States.DONE && !this.eoi ){

			if( this.hasMoreChars() ){
				char ch = this.nextChar();
				switch( this.currentState ){
                                    
				case START:
					//initial state
					if( Character.isDigit( ch ) )
						this.currentState = States.INNUM;
					else if( Character.isLetter( ch ) )
						this.currentState = States.INID;
					else if( Character.isWhitespace( ch ) ){
						this.clearTemp();
                                        }
					else if( ch == ':' )
						this.currentState = States.INASSIGN;
					else if( ch == '{' ){
						this.currentState = States.INCOMMENT;
						this.clearTemp();
					} 
                                        else {
						this.currentState = States.DONE;
						token = this.symbolToToken( ch );
					}
					break;

				case INCOMMENT:
					if( ch == '}' ){
						this.currentState = States.DONE;
						token = TokenType.COMMENT;
					} 
					break;

				case INID:
					if( !Character.isLetter( ch ) ){
						if( Character.isDigit( ch ) ){
							this.currentState = States.INID;
							break;
						}
						this.prevChar();
						this.currentState = States.DONE;
						token = TokenType.IDENTIFIER;
					}
					break;

				case INNUM:
					if( !Character.isDigit( ch ) ){
						if( Character.isLetter( ch ) ){
							this.currentState = States.DONE;
							token = TokenType.NOTOKEN;
							break;
						}
						this.prevChar();
						this.currentState = States.DONE;
						token = TokenType.NUMBER;
					}
					break;

				case INASSIGN:
					if( ch == '=' )
						token = TokenType.ASSIGN;
					else {
						this.prevChar();
						token=TokenType.NOTOKEN;
					}
					this.currentState = States.DONE;
					break;
                                        
				default:
					break;
				}	
			}
		}
		Token token2 = null;
		if( this.currentState == States.DONE )
                        token2 = new Token( token, this.temp );
		else if( !this.temp.isEmpty() )
                        token2 = new Token( this.stateToToken( this.currentState ), this.temp);
		this.currentState = States.START;
		this.clearTemp();
		return token2;
	}

	/*--- performs lexical analysis over the entered code ---*/
	public ArrayList<Token> analyze(){
		Token token = this.getNextToken();
		while( token != null ){
			if( token.getToken() != TokenType.COMMENT && 
                                token.getToken() != TokenType.NOTOKEN )
				this.tokens.add( token );
			token = this.getNextToken();
		}
		return this.tokens;
	}

	/*--- prints out all the tokens,, each with its description ---*/
        @Override
	public String toString(){
		String string = "Total number of Tokens: " + this.tokens.size() + "\n\n";
            for (Token thisToken : this.tokens) {
                string += thisToken.toString() + "\n";
            }
		return string;
	}
        
}
