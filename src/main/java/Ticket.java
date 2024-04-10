import java.util.HashMap;

class tClass<A>{
    A obj;
    tClass(A obj){this.obj = obj; }
    public A getObj(){return  this.obj;}
}
public class Ticket {

    private String name;
    private String subject;

    private String body;
    private HashMap<Integer,Object> attachments;

    public Ticket(){}
    public Ticket(String n,String s,String b, HashMap<Integer, Object> a){
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

    public HashMap<Integer, Object> getAttachments(){
        return attachments;
    }
    public void setAttachments(HashMap h){
        attachments = h;
    }
    public void addAttachment(Integer i,Attachment att){
        attachments.put(i,att);
    }
    public int getNumberOfAttachments(){
        return attachments.size();
    }
    public Object getAttachment(Integer index){

        return attachments.get(index);
    }
    public void getAllAttachments(){
        for (int i=0; i<attachments.size(); i++){
            System.out.println(attachments.get(i));
        }
    }
}
