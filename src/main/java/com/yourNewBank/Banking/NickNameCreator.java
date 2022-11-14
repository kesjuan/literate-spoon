//package com.yourNewBank.Banking;
//
//import com.fasterxml.jackson.core.JacksonException;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
//import com.fasterxml.jackson.databind.node.IntNode;
//import com.yourNewBank.Banking.model.Account;
//import com.yourNewBank.Banking.model.Customer;
//import org.springframework.boot.jackson.JsonComponent;
//
//import java.io.IOException;
//
//@JsonComponent
//public class NickNameCreator extends StdDeserializer<Account> {
//
//    public NickNameCreator(Class<?> vc) {
//        super(vc);
//    }
//    public NickNameCreator(){
//        this(null);
//    }
//    @Override
//    public Account deserialize(JsonParser parser, DeserializationContext ctx)
//            throws IOException, JacksonException {
//    Customer customer = new Customer();
//        JsonNode node = parser.getCodec().readTree(parser);
//        Integer id = (Integer) ((IntNode) node.get("id")).numberValue();
//        String type = node.get("type").asText();
//        Integer rewards = (Integer) node.get("rewards").numberValue();
//        Double balance = (Double) node.get("balance").asDouble();
//
//        String custom
//        String nickname =
//        ArchiveStatus status = new ArchiveStatus(false);
//
//        if(node.get("status") != null) {
//            String active = node.get("status").asText();
//            if("active".equalsIgnoreCase(active)) {
//                status.setActive(true);
//            }
//        }
//
//        return new Record(id.longValue(), message, ZonedDateTime.parse(timestamp, dtf), status);
//    }
//}







//    @Override
//    public String deserialize(JsonParser jsonParser, DeserializationContext dc) throws IOException, JacksonException {
//        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
//        return   customer.getFirstName() +" 's" +account.getType().name().toLowerCase() + " account";
//    }


//}
