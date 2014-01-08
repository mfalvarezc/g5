package com.g5.businesslogic.account;

import com.g5.businesslogic.constraints.Id;
import javax.ejb.Local;

@Local
public interface ReopenAccountLocal {

    void execute(@Id long accountId);

}
