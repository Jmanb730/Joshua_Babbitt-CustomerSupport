public class Attachment {

    private String name = "";
    private byte[] cotents;

    public String getName(){
        return name;
    }

    public void setName(String n){
        name = n;
    }

    public byte[] getContents(){
        return  cotents;
    }

    public void setContents(byte[] con){
        cotents = con;
    }
}
