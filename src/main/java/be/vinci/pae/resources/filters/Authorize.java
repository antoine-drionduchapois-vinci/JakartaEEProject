package be.vinci.pae.resources.filters;

import jakarta.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation used to indicate that a resource requires authorization.
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorize {

  /**
   * The value attribute allows specifying additional information related to authorization. This
   * information could include roles, permissions, or other criteria required for authorization.
   *
   * @return An array of strings representing additional information related to authorization.
   */
  String[] value() default {};
}