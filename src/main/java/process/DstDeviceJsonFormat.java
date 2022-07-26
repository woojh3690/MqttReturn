package process;

import java.util.List;

public class DstDeviceJsonFormat {

    public List<Message> msg;

    public static class Message {
        public MessageHeader msg_header;
        public MessageData msg_data;
    }

    public static class MessageSub extends Message {
        public List<MessageData> msg_data;
    }

    public static class MessageHeader {
        public String source_key;
        public String msg_type;
        public String msg_err_code;
    }

    public static class MessageData {
        public List<ParameterFormat> parameter;
    }

    public static class ParameterFormat {
        public String parameter_name;
        public Object parameter_value;
    }

}


