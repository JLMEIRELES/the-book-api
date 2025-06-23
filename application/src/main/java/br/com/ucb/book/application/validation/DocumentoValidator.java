package br.com.ucb.book.application.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;

@NoArgsConstructor
public class DocumentoValidator implements ConstraintValidator<Documento, String> {

    private final CPFValidator cpfValidator = new CPFValidator();

    private final CNPJValidator cnpjValidator = new CNPJValidator();

    @Override
    public void initialize(Documento constraintAnnotation) {
        cpfValidator.initialize(null);
        cnpjValidator.initialize(null);
    }

    @Override
    public boolean isValid(String document, ConstraintValidatorContext context) {
        return cpfValidator.isValid(document, context) || cnpjValidator.isValid(document, context);
    }
}
