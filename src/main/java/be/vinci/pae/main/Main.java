package be.vinci.pae.main;

import be.vinci.pae.resource.filters.CorsFilter;
import be.vinci.pae.utils.ApplicationBinder;
import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.DALServiceImpl;
import be.vinci.pae.utils.WebExceptionMapper;
import java.io.IOException;
import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;


/**
 * Main class.
 */
public class Main {

  static {
    Config.load("dev.properties");
  }
  // Be carefull, with auto formating, line :
  // "public static final String BASE_URI = Config.getProperty("BaseUri");"
  // place itself above :
  // "static {
  //    Config.load("dev.properties");
  //  }"
  // and it throws an error

  // Base URI the Grizzly HTTP server will listen on
  public static final String BASE_URI = Config.getProperty("BaseUri");

  /**
   * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
   *
   * @return Grizzly HTTP server.
   */
  public static HttpServer startServer() {

    // create a resource config that scans for JAX-RS resources and providers
    // in vinci.be package

    final ResourceConfig rc = new ResourceConfig().packages("be.vinci.pae.resource")
        .register(ApplicationBinder.class)
        .register(WebExceptionMapper.class)
        .register(CorsFilter.class);

    // create and start a new instance of grizzly http server
    // exposing the Jersey application at BASE_URI
    return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
  }

  /**
   * Main method.
   *
   * @param args The command line arguments passed to the program.
   * @throws IOException If an I/O error occurs while reading from the console.
   */
  public static void main(String[] args) throws IOException {
    final HttpServer server = startServer();
    System.out.println(String.format("Jersey app started with WADL available at "
        + BASE_URI + "\nHit enter to stop it..."));

    DALService dalService = new DALServiceImpl();

    System.in.read();
    server.stop();
  }
}
