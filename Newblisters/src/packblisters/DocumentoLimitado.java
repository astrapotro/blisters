package packblisters;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class DocumentoLimitado extends PlainDocument 
{

	private static final long serialVersionUID = 1L;
	private int limite;
    
    public DocumentoLimitado(int limite) 
    {
        super();
        setLimit(limite);  
    }
    
    public final int getLimite() 
    {
    	return limite;
    }
    
    public void insertString(int offset, String s, AttributeSet attributeSet) 
        throws BadLocationException 
    {
        if(offset < limite)
	    {
	        super.insertString(offset,s,attributeSet);
	    } // en otro caso perder la cadena
    }
    
    public final void setLimit(int newValue) 
    {
        this.limite = newValue;
    }
}