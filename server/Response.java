import java.io.OutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;

public class Response {
	private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        
        try {
        	if (request.getMethod() == "POST") {
//        		System.out.println(request.getMethod());
        		System.out.println("posting:\n" + request.toString());
//        		String[] arg = {request.parseValue()};
//        		double tax = PersonalTax.main(arg);
//        		System.out.println(tax);
//        		String xml = "<tax>" + tax + "</tax>";
//        		String header = "HTTP/1.1 200\r\n" + "Content-Type: text/xml\r\n" + "Content-Length: " + xml.getBytes().length + "\r\n" + "\r\n";
//        		output.write(header.getBytes());
//        		output.write(xml.getBytes());
        	}
        	else {
        		//将web文件写入到OutputStream字节流中
                File file = new File(HttpServer.WEB_ROOT, request.getUri());
                System.out.println(file.exists());
                if (file.exists()) {
                    fis = new FileInputStream(file);
                    String type = request.parseType();
                    String header = "HTTP/1.1 200\r\n" + "Content-Type: text/" + type + "\r\n" + "Content-Length: " + file.length() + "\r\n" + "\r\n";
                    output.write(header.getBytes());
                    int ch = fis.read(bytes, 0, BUFFER_SIZE);
                    while (ch != -1) {
                        output.write(bytes, 0, ch);
                        ch = fis.read(bytes, 0, BUFFER_SIZE);
                    }
                } else {
                    // file not found
                    String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
                            + "Content-Length: 23\r\n" + "\r\n" + "<h1>File Not Found</h1>";
                    output.write(errorMessage.getBytes());
                }
			}
        } catch (Exception e) {
            // thrown if cannot instantiate a File object
            System.out.println(e.toString());
        } finally {
            if (fis != null)
                fis.close();
        }
    }
}
