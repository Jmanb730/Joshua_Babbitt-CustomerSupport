import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;


import java.util.*;
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
        switch(action) {
            case "createForum" -> showTicketForm(request, response);
            case "viewTicket" -> viewTicket(request, response);
            case "createTicket" -> createTicket(request,response);
            case "downloadFile" -> downloadAttachment(request, response);
            default -> listTickets(request, response); // this the list and any other
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        //parameters
        /*
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String pnum = request.getParameter("pnum");
        String city = request.getParameter("city");


        //webpage
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Successfully signed up</h2>");


        out.println("<!DOCTYPE html>"+
                "<html lang=\"en\">" +
                "<link rel=\"stylesheet\" href=\"Vizsla.css\"> " +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title>Email</title>"
        );

        out.println("<title>Email</title></head><body><h1>Email</h1>");
        out.println("<nav>\n" +"    <a href=\"ticketForum.jsp\" class=\"button\">Home</a>\n" +
                "    <a href=\"history.jsp\" class=\"button\">History</a>\n" +
                "    <a href=\"care.jsp\" class=\"button\">Care</a>\n" +
                "    <a href=\"email.jsp\" class=\"button\">Email</a>\n" +
                "    <br>\n" +
                "</nav>");

        /*
        out.println("Name:" + name);
        out.println("Email:" + email);
        out.println("Phone Number:" + pnum);
        out.println("City:" + city);
        */


    }

    private void showTicketForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        Integer id;
        try{
            id = Integer.parseInt(request.getParameter("ticketId"));
            Ticket t = getTicket(id);

        }
        catch (Exception e){

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
    public void createTicket( HttpServletRequest request,HttpServletResponse response/*String n,String s,String b, ArrayList a*/)throws ServletException, IOException{
        String n = request.getParameter("name");
        String s = request.getParameter("subject");
        String b = request.getParameter("body");
        String ar = request.getParameter("attachments");
        HashMap<Integer,Object> h = new HashMap<>();
        //h = Arrays.asList(String.split(ar));
        //Ticket t = new Ticket(n,s,b,a);
        //tickets.put(currentMaxIndex,t);
        currentMaxIndex++;
    }
    public void viewTicket(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        try{
            String idString = request.getParameter("ticketId");
            Integer i = Integer.parseInt(idString);
            Ticket t = getTicket(i);

            response.sendRedirect("support?action=viewTicket");
        }
        catch (Exception e){

        }


    }
    public Ticket getTicket(Integer i){
        return tickets.get(i);
    }
    public void listTickets(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        PrintWriter out = response.getWriter();

        out.println("<html><body><h2>Tickets</h2>");
        out.println("<a href=\"blog?action=createTicket\">Create Ticket</a><br><br>");

        if(tickets.size() == 0){
            out.println("No tickets");
        }
        else{
            for(int i = 0; i < currentMaxIndex; i++){
                Ticket tick = tickets.get(i);
                out.println("Ticket number: " + i);
                /*
                out.println(": <a href=\"blog?action=view&blogId=" + id + "\">");
                out.println(blog.getTitle() + "</a><br>");
                */
            }
        }
    }

    //Attachment methods
    private void downloadAttachment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String idString = request.getParameter("ticketID");
        Integer i = Integer.parseInt(idString);
        Ticket tick = getTicket(i);


        String name = request.getParameter("attachment");
        if (name == null) {
            response.sendRedirect("support?action=download&ticketID=" + idString);
        }

        Object temp = tick.getAttachment(Integer.parseInt(idString));
        Attachment att = (Attachment)temp;

        if (att == null) {
            response.sendRedirect("support?action=view&ticketId=" + idString);
            return;
        }

        response.setHeader("Content-Disposition", "Attachment; name of attachment=" + att.getName());
        response.setContentType("application/octet-stream");

        ServletOutputStream out = response.getOutputStream();
        out.write(att.getContents());
    }

    public  void processAttachment(Attachment a){
        
    }
}

