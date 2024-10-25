package control.servicios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ServicioValidacionesForm {

	
	public static boolean validarFormTexto(ArrayList <String>campos,int longitud) {
		
		boolean validado=true;
		
		for (String campo : campos) {
			if(campo.length()< longitud) {
				validado=false;
			}
		}
		return validado;
	}
	
	public static boolean validarCheckBox(String[] checkBoxes) {
		ArrayList<String> listaChecks = new ArrayList<>(Arrays.asList(checkBoxes));
	    if (listaChecks == null || listaChecks.size() == 0) {
	        return false; 
	    }
	    
	    for (String checkBox : listaChecks) {
	        if (checkBox == null || checkBox.trim().isEmpty()) {
	            return false; // Si uno está vacío o nulo, no es válido.
	        }
	    }
	    return true; // Todos los checkboxes son válidos.
	}

	
}
