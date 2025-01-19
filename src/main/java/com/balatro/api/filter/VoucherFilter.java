package com.balatro.api.filter;

import com.balatro.api.Filter;
import com.balatro.api.Run;
import com.balatro.enums.Voucher;

public record VoucherFilter(Voucher voucher) implements Filter {

    @Override
    public boolean filter(Run run) {
        return run.hasVoucher(voucher);
    }
}
