package com.led.ger.util;

import com.led.ger.entity.Account;
import com.led.ger.enumeration.AccountType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountingUtil {
    public static List<String> ACCOUNT_HEADS =
            Stream.of(
                AccountType.ASSETS,
                AccountType.LIABILITIES,
                AccountType.CAPITAL).map(Enum::name).collect(Collectors.toList());

}
