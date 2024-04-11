import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


import java.awt.*;
import java.awt.Image;
import java.util.*;
import java.util.LinkedHashMap;
import java.io.*;
import java.util.Map;
import java.lang.*;
import static java.lang.System.out;

@WebServlet(name = "customerSupport" , value="/customerSupport")
//@MultipartConfig(fileSizeThreshold = 5_242_880, maxFileSize = 20_971_520L, maxRequestSize = 41_943_040L);
public class TicketServlet extends HttpServlet {
    Integer currentMaxIndex = 0;
    private Map<Integer, Ticket> tickets = new LinkedHashMap<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        String action = request.getParameter("action");

        if (action == null) {
            action = "createForum";
        }
        switch (action) {
            case "createForum" -> showTicketForm(request, response);
            case "viewTicket" -> viewTicket(request, response);
            case "createTicket" -> createTicket(request, response);
            case "downloadFile" -> downloadAttachment(request, response);
            default -> listTickets(request, response); // this the list and any other
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

    }

    private void showTicketForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer id;
        try {
            id = Integer.parseInt(request.getParameter("ticketId"));
            Ticket t = getTicket(id);

        } catch (Exception e) {

        }

        PrintWriter out = response.getWriter();

        out.println("<html><body><h2>Create a Ticket</h2>");
        out.println("<form method=\"POST\" action=\"createForum\" enctype=\"multipart/form-data\">");
        out.println("<input type=\"hidden\" name=\"action\" value=\"create\">");
        out.println("Name:<br>");
        out.println("<input type=\"text\" name=\"Name\"><br><br>");
        out.println("Subject:<br>");
        out.println("<input type=\"text\" name=\"subject\"><br><br>");
        out.println("Body:<br>");
        out.println("<textarea name=\"body\" rows=\"25\" cols=\"100\"></textarea><br><br>");
        out.println("<b>Ticket</b><br>");
        out.println("<input type=\"file\" name=\"file1\"><br><br>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form></body></html>");

    }

    //ticket methods
    public void createTicket(HttpServletRequest request, HttpServletResponse response/*String n,String s,String b, ArrayList a*/) throws ServletException, IOException {

        Ticket t = new Ticket();
        t.setName(request.getParameter("Name"));
        t.setSubject(request.getParameter("subject"));
        t.setBody(request.getParameter("body"));

        /*
        Part file = request.getPart("file1");
        if (file != null) {
            Image image = this.processAttachment(file);
            if(image != null){
                t.setImage(image);
            }
        }

        t.setImage(((request.getParameter("file1")));
        */

        HashMap<Integer, Object> h = new HashMap<>();
        //h = Arrays.asList(String.split(ar));
        //Ticket t = new Ticket(n,s,b,a);
        tickets.put(currentMaxIndex, t);
        currentMaxIndex++;
    }

    public void viewTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idString = request.getParameter("ticketId");
            Integer i = Integer.parseInt(idString);
            Ticket t = getTicket(i);

            out.println("<html><body><h2>Ticket</h2>");
            out.println("<h3>" + t.getName() + "</h3>");
            out.println("<p>Date: " + t.getSubject() + "</p>");
            out.println("<p>" + t.getBody() + "</p>");
            out.println("<a href=\"showTicketForum\">Return to support request page</a>");
            out.println("</body></html>");
        } catch (Exception e) {
            out.println("<html><body>");
            out.println("<h1>Something is wrong with your request <h2>");
            out.println("<a href=\"showTicketForum\">Return to support request page</a>");
            out.println("</body></html>");
        }


    }

    public Ticket getTicket(Integer i) {
        return tickets.get(i);
    }

    public void listTickets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        out.println("<html><body><h2>Tickets</h2>");
        out.println("<a href=\"support?action=createTicket\">Create Ticket</a><br><br>");


        if (tickets.size() == 0) {
            out.println("There are no tickets yet");
        } else {
            for (int id : tickets.keySet()) {
                Ticket t = tickets.get(id);
                out.println("Ticket #" + id);
                out.println(": <a href=\"blog?action=view&blogId=" + id + "\">");
                out.println(t.getName() + "</a><br>");
            }
        }
        out.println("</body></html>");
    }


    //Attachment methods
    private void downloadAttachment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String idString = request.getParameter("ticketID");
        Integer i = Integer.parseInt(idString);
        Ticket t = getTicket(i);


        String name = request.getParameter("attachment");
        if (name == null) {
            response.sendRedirect("support?action=download&ticketID=" + idString);
        }

        Object temp = t.getAttachment(Integer.parseInt(idString));
        Attachment att = (Attachment) temp;

        if (att == null) {
            response.sendRedirect("support?action=view&ticketId=" + idString);
            return;
        }

        response.setHeader("Content-Disposition", "Attachment; name of attachment=" + att.getName());
        response.setContentType("application/octet-stream");

        ServletOutputStream out = response.getOutputStream();
        out.write(att.getContents());
    }


//public Attachment processAttachment(Attachment a){ return  a;}

    /*
    public Image processAttachment(Part file) throws IOException {
        InputStream in = file.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int read;
        final byte[] bytes = new byte[1024];
        while ((read = in.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }

        //Image image = new Image();
        Image image;
        image.setName(file.getSubmittedFileName());
        image.setContents(out.toByteArray());

        return image;
    }
    */
}

