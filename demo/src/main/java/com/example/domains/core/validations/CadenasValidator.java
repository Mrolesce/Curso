package com.example.domains.core.validations;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CadenasValidator {

	
	//metodo para validarNif -> https://www.lawebdelprogramador.com/foros/Java/1582093-validar-nif.html
	public static boolean isNIF(String nif){
		if(nif == null) return true;
        boolean correcto=false;
        Pattern pattern=Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
        Matcher matcher=pattern.matcher(nif);
        if(matcher.matches()){
            String letra=matcher.group(2);
            String letras="TRWAGMYFPDXBNJZSQVHLCKE";
            int index=Integer.parseInt(matcher.group(1));
            index=index%23;
            String reference=letras.substring(index,index+1);
            if(reference.equalsIgnoreCase(letra)){
                correcto=true;
            }else{
                correcto=false;
            }
        }else{
            correcto=false;
        }
        return correcto;
    }
	
	public boolean noEsNif(String nif) {
		return !isNIF(nif);
	}

}
