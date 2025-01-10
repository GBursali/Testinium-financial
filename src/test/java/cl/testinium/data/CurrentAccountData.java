package cl.testinium.data;

import cl.testinium.utils.TextUtils;
import com.gbursali.data.BaseData;

public class CurrentAccountData extends BaseData {
    public double money;
    public String accountName;

    public CurrentAccountData() {
        super("current");
    }

    @Override
    public void save() {
        super.save();
        TextUtils.commonPlaceholders
                .add("Account Name",accountName);
    }
}
