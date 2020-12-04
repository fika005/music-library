package src;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * This class uses Jetty & servlets to implement server serving hotel and review info
 */
public class TestServer {

    private static final int PORT = 8081;

    public TestServer() {

    }

    /**
     * Function that starts the server with correct servlets
     *
     * @throws Exception throws exception if access failed
     */
    public static void main(String[] args) throws Exception {
        Server server = new Server(PORT); // jetty server

        ServletHandler handler = new ServletHandler();

        // FILL IN CODE:
        handler.addServletWithMapping(new ServletHolder(new Stage1()), "/stage1");
        handler.addServletWithMapping(new ServletHolder(new Stage2()), "/stage2");
        handler.addServletWithMapping(new ServletHolder(new Stage3()), "/stage3");
        handler.addServletWithMapping(new ServletHolder(new LoginServlet()), "/login");
        handler.addServletWithMapping(new ServletHolder(new HomePage()), "/homePage");
        handler.addServletWithMapping(new ServletHolder(new AlbumsServlet()), "/albums");
        handler.addServletWithMapping(new ServletHolder(new ArtistsServlet()), "/artists");
        handler.addServletWithMapping(new ServletHolder(new PlayListServlet()), "/playlist");
        handler.addServletWithMapping(new ServletHolder(new YourLibraryServlet()), "/yourLibrary");
        handler.addServletWithMapping(new ServletHolder(new SearchServlet()), "/search");
        handler.addServletWithMapping(new ServletHolder(new SearchResponse()), "/searchResult");







        server.setHandler(handler);

        server.start();
        server.join();
    }

}
