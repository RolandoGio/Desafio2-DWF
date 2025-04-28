package utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * Validador personalizado para números telefónicos de El Salvador.
 * Acepta 8 dígitos, empezando con 2, 3, 6 o 7, sin guiones.
 */
@FacesValidator("svPhoneValidator")
public class SVPhoneValidator implements Validator<String> {

    @Override
    public void validate(FacesContext context, UIComponent component, String value) throws ValidatorException {
        if (value == null || value.trim().isEmpty()) {
            return; // Deja que otras validaciones manejen campos vacíos si es necesario
        }

        // Expresión regular: 8 dígitos que inicien con 2, 3, 6 o 7
        if (!value.matches("^[2367]\\d{7}$")) {
            FacesMessage msg = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Teléfono inválido",
                    "El teléfono debe tener 8 dígitos, empezar con 2, 3, 6 o 7 y no llevar guiones."
            );
            throw new ValidatorException(msg);
        }
    }
}
