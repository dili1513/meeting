package hmh;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class SocketServer {

        public ArrayList<String> getResult(String requirement){
            ArrayList<String> result_to_return = new ArrayList<String>();
            try {
                //1.创建监听指定服务器地址以及指定服务器监听的端口号
                // Socket socket = new Socket("10.0.2.2", 12347);
                Socket socket = new Socket("10.110.212.9", 12347);
                //2.拿到客户端的socket对象的输出流发送给服务器数据
                OutputStream os = socket.getOutputStream();
                //写入要发送给服务器的数据
                os.write(requirement.getBytes());
                os.flush();
                socket.shutdownOutput();
                //拿到socket的输入流，这里存储的是服务器返回的数据
                InputStream is = socket.getInputStream();
                //解析服务器返回的数据
                InputStreamReader reader = new InputStreamReader(is);
                BufferedReader bufReader = new BufferedReader(reader);
                String s = null;
                final StringBuffer sb = new StringBuffer();
                while((s = bufReader.readLine()) != null){
                    sb.append(s);
                }
                String[] result = sb.toString().split(",");
                for(String string : result){
                    result_to_return.add(string);
                }
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    recerive_text.setText(sb.toString());
//                }
//            });
                //3、关闭IO资源（注：实际开发中需要放到finally中）
                bufReader.close();
                reader.close();
                is.close();
                os.close();
                socket.close();
                return result_to_return;
            } catch (UnknownHostException e) {
                result_to_return.clear();
                result_to_return.add("0");
                result_to_return.add(e.toString());
                return  result_to_return;
            } catch (Exception e) {
                result_to_return.clear();
                result_to_return.add("0");
                result_to_return.add(e.toString());
                return  result_to_return;
            }
        }
    }
