package com.orbitsoft.cryptocurrencyrates.ui.list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.orbitsoft.cryptocurrencyrates.R;
import com.orbitsoft.cryptocurrencyrates.databinding.CoinRowViewBinding;
import com.orbitsoft.cryptocurrencyrates.ui.model.CoinUi;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;

public class CoinRowView extends FrameLayout {
    private CoinRowViewBinding viewBinding;
    private RequestBuilder<PictureDrawable> svgRequestBuilder;
    private RequestBuilder<Drawable> imageRequestBuilder;

    public CoinRowView(@NonNull Context context) {
        super(context);
        initView(context);
    }

    private void initView(@NotNull Context context) {
        viewBinding = CoinRowViewBinding.inflate(LayoutInflater.from(context), this, true);
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        this.svgRequestBuilder =
                Glide.with(this)
                        .as(PictureDrawable.class)
                        .listener(new com.bumptech.glide.samples.svg.SvgSoftwareLayerSetter())
                        .placeholder(R.drawable.row_logo_placeholder);

        this.imageRequestBuilder = Glide.with(this).
                asDrawable()
                .placeholder(R.drawable.row_logo_placeholder);

        initChart();
    }

    /**
     * Prepare chart
     */
    private void initChart() {
        LineChart chart = viewBinding.chart;
        chart.setNoDataText("");
        chart.setDescription(null);
        chart.setPadding(0, 0, 0, 0);
        chart.setViewPortOffsets(0f, 0f, 0f, 0f);
        chart.getLegend().setEnabled(false);
        chart.getXAxis().setEnabled(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
    }

    public void bind(@NotNull CoinUi coin) {
        viewBinding.number.setText("" + coin.getPosititon());
        viewBinding.name.setText(coin.getName());
        viewBinding.symbol.setText(coin.getSymbol());
        try {
            viewBinding.price.setText(formatPrice(Double.parseDouble(coin.getPrice())));
        } catch (Exception ex) {
            viewBinding.price.setText(formatPrice(0));
        }
        try {
            if (coin.getMarketCap() == null) {
                viewBinding.marketCap.setText(formatMarketCap(0));
            } else {
                viewBinding.marketCap.setText(formatMarketCap(Double.parseDouble(coin.getMarketCap())));
            }
        } catch (Exception ex) {
            viewBinding.marketCap.setText(formatMarketCap(0));
        }
        int colorResId = R.color.row_negative;
        try {
            double change = Double.parseDouble(coin.getChange());
            String prefix = "";
            if (change > 0) {
                colorResId = R.color.row_positive;
                prefix = "+";
            }
            viewBinding.percent.setTextColor(ContextCompat.getColor(getContext(), colorResId));

            viewBinding.percent.setText(String.format(
                    getContext().getString(R.string.percent), prefix, change)
            );
        } catch (Exception ex) {
            viewBinding.percent.setText("-");
        }

        if (coin.getIconUrl().contains(".svg")) {
            svgRequestBuilder.load(coin.getIconUrl()).into(viewBinding.logo);
        } else {
            imageRequestBuilder.load(coin.getIconUrl()).into(viewBinding.logo);
        }

        drawChart(coin, colorResId);
    }

    private String formatMarketCap(double marketCap) {
        if (marketCap < 1000) return formatPrice(marketCap);
        int exp = (int) (Math.log(marketCap) / Math.log(1000));
        return String.format("%1s%c",
                formatPrice(marketCap / Math.pow(1000, exp)),
                "kMBTPE".charAt(exp - 1));
    }

    private String formatPrice(double price) {
        try {
            NumberFormat format = NumberFormat.getCurrencyInstance();
            format.setMaximumFractionDigits(2);
            format.setCurrency(Currency.getInstance("USD"));
            return format.format(price);
        } catch (Exception ex) {
            return "-";
        }
    }

    /**
     * Draw chart for this row
     *
     * @param coin
     * @param colorResId
     */
    private void drawChart(@NotNull CoinUi coin, int colorResId) {
        LineChart chart = viewBinding.chart;
        chart.clear();
        ArrayList<Entry> values = new ArrayList<>();
        int x = 0;
        for (Float value : coin.getSparkline()) {
            values.add(new Entry(x, value));
            x++;
        }
        LineDataSet set1 = new LineDataSet(values, "");
        set1.setDrawValues(false);
        set1.setDrawCircles(false);
        set1.setDrawFilled(true);
        set1.setLineWidth(1.0f);
        set1.setFillAlpha(10);
        set1.setFillColor(ContextCompat.getColor(getContext(), colorResId));
        set1.setColor(ContextCompat.getColor(getContext(), colorResId));

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets

        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // set data
        chart.setData(data);
    }

    public void recycle() {
        Glide.with(getContext()).clear(viewBinding.logo);
    }
}
