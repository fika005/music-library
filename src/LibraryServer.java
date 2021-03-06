package src;

import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * This class uses Jetty & servlets to implement server serving hotel and review info
 */
public class LibraryServer {

    private static final int PORT = 8081;

    public LibraryServer() {

    }

    /**
     * Function that starts the server with correct servlets
     *
     * @throws Exception throws exception if access failed
     */
    public static void main(String[] args) throws Exception {
        org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(PORT); // jetty server
        ServletHandler handler = new ServletHandler();
        IOManager db = new IOManager();
        handler.addServletWithMapping(new ServletHolder(new LoginServlet()), "/login");
        handler.addServletWithMapping(new ServletHolder(new SignUpServlet()), "/signup");
        handler.addServletWithMapping(new ServletHolder(new HomePageServlet(db)), "/homePage");
        handler.addServletWithMapping(new ServletHolder(new ShowAllSongsServlet(db)), "/showAllSongs");
        handler.addServletWithMapping(new ServletHolder(new PlayListServlet(db)), "/playlist");
        handler.addServletWithMapping(new ServletHolder(new SearchServlet()), "/search");
        handler.addServletWithMapping(new ServletHolder(new SearchResponseServlet(db)), "/searchResult");
        handler.addServletWithMapping(new ServletHolder(new DescriptionServlet(db)), "/description");
        server.setHandler(handler);
        server.start();
        server.join();
    }

}
