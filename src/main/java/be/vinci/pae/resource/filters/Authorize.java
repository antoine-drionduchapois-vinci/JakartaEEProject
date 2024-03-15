package be.vinci.pae.resource.filters;

import jakarta.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation used to indicate that a resource requires authorization.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {

}
