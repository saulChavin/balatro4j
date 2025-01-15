package com.balatro.api;

import com.balatro.enums.Voucher;

public record VoucherFilter(Voucher voucher) implements Filter {

    @Override
    public boolean filter(Run run) {
        return run.hasVoucher(voucher);
    }
}
