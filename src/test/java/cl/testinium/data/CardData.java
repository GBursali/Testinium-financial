package cl.testinium.data;

import cl.testinium.utils.JsonReader;
import com.gbursali.data.BaseData;
import com.gbursali.data.DataManager;

import java.time.LocalDateTime;
import java.util.Map;

public class CardData extends BaseData {

    public String cardNumber;
    public String cardHolder;
    public String cardExpiry;
    public String cvv;
    public int amount;
    public LocalDateTime date;

    public CardData(String identifier) {
        super(identifier);
    }

    public static CardData fromMap(Map<String, String> data) {
        return new CardData("Data from table") {{
            cardNumber = data.get("Card Number").replace("-","");
            cardHolder = data.get("Card Holder").replace("-","");
            cardExpiry = data.get("Card Expiry Date").replace("-","");
            cvv = data.get("Card CVV").replace("-","");
            amount = Integer.parseInt(data.get("Amount"));
        }};
    }

    public TransferData toTransferData() {
        var data = new TransferData(identifier);
        data.senderAccount = cardHolder;
        data.receiverAccount = DataManager.getFirst(CurrentAccountData.class)
                .orElseThrow(() -> new IllegalStateException(JsonReader.getExceptionMessage("Account information not saved")))
                .accountName;
        data.amount = amount;
        data.sendingDate = date;
        return data;
    }

    public boolean checkCardNumberRule() {
        return cardNumber.length() == 16 &&
                cardNumber.matches("[0-9]+");
    }
}
