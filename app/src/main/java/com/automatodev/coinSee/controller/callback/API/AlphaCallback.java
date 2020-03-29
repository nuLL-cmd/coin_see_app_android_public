package com.automatodev.coinSee.controller.callback.API;

import com.automatodev.coinSee.controller.entity.CoinEntityAlpha;
import com.automatodev.coinSee.controller.entity.CoinRangeEntityAlpha;

import java.util.List;

public interface AlphaCallback {
    void onSuccessSingle(CoinEntityAlpha coinEntityAlpha);
    void onSuccessRange(List<CoinRangeEntityAlpha> rangeList);
}
