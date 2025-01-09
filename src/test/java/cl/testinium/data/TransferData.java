package cl.testinium.data;

import com.gbursali.data.BaseData;

import java.time.LocalDateTime;
import java.util.Map;

public class TransferData extends BaseData {
    public String senderAccount;
    public String receiverAccount;
    public double amount;

    public LocalDateTime sendingDate;

    public TransferData(String identifier) {
        super(identifier);
    }

    public static TransferData fromMap(Map<String, String> data) {
        return new TransferData("Step populated data") {{
            senderAccount = data.get("senderAccount");
            receiverAccount = data.get("receiverAccount");
            amount = Double.parseDouble(data.get("amount"));
        }};
    }

}
