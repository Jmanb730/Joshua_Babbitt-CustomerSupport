public class Attachment {

    private String name = "";
    private byte[] contents;

    public String getName(){
        return name;
    }

    public void setName(String n){
        name = n;
    }

    public byte[] getContents(){
        return  contents;
    }

    public void setContents(byte[] con){
        contents = con;
    }
}
