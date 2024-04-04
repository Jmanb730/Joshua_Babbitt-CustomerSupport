import java.util.ArrayList;
public class Ticket {

    private String name;
    private String subject;

    private String body;
    private ArrayList<Attachment> attachments;
    public Ticket(String n,String s,String b, ArrayList<Attachment> a){
        name = n;
        subject = s;
        body = b;
        attachments = a;
    }

    public String getName(){
        return name;
    }
    public void setName(String n){
        name = n;
    }

    public String getSubject(){
        return subject;
    }
    public void setSubject(String s){
        subject = s;
    }

    public String getBody(){
        return body;
    }
    public void setBody(String b){
        body = b;
    }

    public ArrayList getAttachments(){
        return attachments;
    }
    public void setAttachments(ArrayList a){
        attachments = a;
    }
    public void addAttachment(Attachment att){
        attachments.add(att);
    }
    public int getNumberOfAttachments(){
        return attachments.size();
    }
    public Attachment getAttachment(int index){

        return attachments.get(index);
    }
    public void getAllAttachments(){
        for (int i=0; i<attachments.size(); i++){
            System.out.println(attachments.get(i));
        }
    }
}
