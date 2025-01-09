package cl.testinium.data;

import com.gbursali.data.BaseData;

public class CurrentAccountData extends BaseData {
    public double money;
    public String accountName;

    public CurrentAccountData() {
        super("current");
    }
}
