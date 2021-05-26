package com.orbitsoft.cryptocurrencyrates.ui.list;

import com.orbitsoft.cryptocurrencyrates.ui.model.CoinUi;

import org.jetbrains.annotations.NotNull;

public class ListItem {

    public static class Row extends ListItem {
        private final CoinUi coin;

        public CoinUi getCoin() {
            return coin;
        }

        public Row(@NotNull CoinUi coin) {
            this.coin = coin;
        }

        @Override
        public String toString() {
            return "Row{" +
                    "coin=" + coin +
                    '}';
        }
    }

    public static class EmptyState extends ListItem {
    }
}
