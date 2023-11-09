package com.wintermute.mobile.cashbutler.domain.finance

enum class ProposedCashFlowSources(val displayName: String) {
    CASH("Cash"),
    BANK_ACCOUNTS("Bank accounts"),
    DIGITAL_WALLETS("Digital Wallets"),
    CRYPTOCURRENCY("Cryptocurrency"),
    CREDIT_CARDS("Credit cards")
}