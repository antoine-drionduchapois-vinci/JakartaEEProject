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
   * Defines the roles that are authorized to access the resource.
   *
   * @return an array of strings representing the roles authorized to access the resource.
   */
  String[] value() default {};
}

