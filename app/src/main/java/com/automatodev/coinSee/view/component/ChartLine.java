package com.automatodev.coinSee.view.component;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.automatodev.coinSee.R;
import com.automatodev.coinSee.controller.entity.CoinChildr;
import com.automatodev.coinSee.controller.service.ConvertDataService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChartLine {
    private Activity context;
    private List<CoinChildr> valueList;
    private LineChart chartGlobal;
    private ConvertDataService convertDataService;
    private int var = 0;

    public ChartLine(Activity context, LineChart chartGlobal, List<CoinChildr> valueList) {
        this.context = context;
        this.valueList = valueList;
        this.chartGlobal = chartGlobal;
        convertDataService = new ConvertDataService();
    }

    public ChartLine(Activity context,LineChart chartGlobal, List<CoinChildr> valueList, int var) {
        this.context = context;
        this.valueList = valueList;
        this.chartGlobal = chartGlobal;
        this.var = var;
        convertDataService = new ConvertDataService();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void makeGraph() {
        //Lista do tipo Entry que recebe um entry passando como pramatro os valores do eixo X e eixo y
        List<Entry> entradaTestes = new ArrayList<>();
        entradaTestes.add(new Entry(0, Float.parseFloat(valueList.get(13).getBid())));
        entradaTestes.add(new Entry(1, Float.parseFloat(valueList.get(12).getBid())));
        entradaTestes.add(new Entry(2, Float.parseFloat(valueList.get(11).getBid())));
        entradaTestes.add(new Entry(3, Float.parseFloat(valueList.get(10).getBid())));
        entradaTestes.add(new Entry(4, Float.parseFloat(valueList.get(9).getBid())));
        entradaTestes.add(new Entry(5, Float.parseFloat(valueList.get(8).getBid())));
        entradaTestes.add(new Entry(6, Float.parseFloat(valueList.get(7).getBid())));
        entradaTestes.add(new Entry(7, Float.parseFloat(valueList.get(6).getBid())));
        entradaTestes.add(new Entry(8, Float.parseFloat(valueList.get(5).getBid())));
        entradaTestes.add(new Entry(9, Float.parseFloat(valueList.get(4).getBid())));
        entradaTestes.add(new Entry(10, Float.parseFloat(valueList.get(3).getBid())));
        entradaTestes.add(new Entry(11, Float.parseFloat(valueList.get(2).getBid())));
        entradaTestes.add(new Entry(12, Float.parseFloat(valueList.get(1).getBid())));
        entradaTestes.add(new Entry(13, Float.parseFloat(valueList.get(0).getBid())));
        //Lista contendo as datas que substituira os valores de X no objeto Entry dentro do grafico
        final List<String> mxData = new ArrayList<>();
        mxData.add(convertDataService.convertDayMonth(valueList.get(13).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(12).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(11).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(10).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(9).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(8).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(7).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(6).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(5).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(4).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(3).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(2).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(1).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(0).getTimestamp().substring(0, 10)));
        //Configuração de dados do eixo X
        XAxis xAxis = chartGlobal.getXAxis(); //Inicia o eixo X
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //Dados(labels) do eixo X no Bottom do grafico
        xAxis.setTextColor(Color.parseColor("#3949AB")); //Seta a cor dos dados(labels) do eixo X
        xAxis.setTextSize(10); //Tanho dos dados(labels) do eixo X
        xAxis.setYOffset(10f);
        xAxis.setXOffset(-10f);
        xAxis.setGranularity(1f);
        xAxis.setAxisLineWidth(1);
        //xAxis.setDrawGridLines(false); // desabilita as linhas na vertical do grafico
        xAxis.setValueFormatter(formatData(mxData));
        //Configuração de dados do eixo Y
        YAxis yr = chartGlobal.getAxisRight();//Inicia o eixo Y do lado direito do grafico
        yr.setEnabled(false); //Desabilita a visualização dos dados no eixo Y do lado direito do grafico
        yr.setGranularity(1f);
        YAxis yl = chartGlobal.getAxisLeft(); //Inicia o eixo Y do lado esquerdo do grafico
            yl.setEnabled(false); //Desabilita a visualização dos dados no eixo Y do lado direito do grafico
        //Incicia um novo LineDataSet passando a lista de Entrys e uma label para o grafico
        LineDataSet lineDataSet = new LineDataSet(entradaTestes, "últimos fechamentos");
        lineDataSet.setDrawFilled(true); //Preenchimento de cor a baixo da linha tenua do grafico
        lineDataSet.enableDashedLine(5,18,0); //linha tenua serrilhada
        lineDataSet.setDrawValues(true); //Valores do eixo Y em cada nó da linha tenua do grafico
        lineDataSet.setValueTextColor(Color.BLACK); //Cor do texto do valor em cada nó da linha tenua do grafico
        lineDataSet.setValueTextSize(8); //Tamanho do texto do valor em cada nó da linha tenua do grafico
        lineDataSet.setValueFormatter(formatValue()); //Formata o valor em cada nó da linha tenua do grafico
        lineDataSet.setHighlightLineWidth(0.5f);// espessura da linha em fomra de cruz que que linka os dados do eixo y ao eixo x
        lineDataSet.setHighLightColor(Color.RED); // cor da linha em fomra de cruz
        lineDataSet.setCircleHoleColor(Color.parseColor("#d32f2f")); // cor da bolinha
        lineDataSet.setCircleRadius(4f); //Diametro de cada nó da linha tenua do grafico
        lineDataSet.setCircleColor(Color.parseColor("#d32f2f")); //Cor de cada nó da linha tenua do grafico
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); //Seta o tipo de curva que o grafico fara em cada no
        lineDataSet.setCubicIntensity(0.2f); // para o CUBIC seta o arredondamento da curva do grafico - quanto maior mais redondinho
        lineDataSet.setColor(Color.parseColor("#8F99A1")); //Cor da linha tenua e da label do grafico
        // Gradiente (preenchimento) da linha tenua do grafico
        lineDataSet.setFillDrawable(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{Color.parseColor("#075FAC"), Color.parseColor("#3176B3"), Color.parseColor("#00FA5544")}));
        lineDataSet.setLineWidth(0); //Espessura da linha tenua do grafico
        //Incicia um novo LineData passando o lineDataSet
        LineData lineData = new LineData(lineDataSet);
        lineData.setValueFormatter(formatValue());
        chartGlobal.setData(lineData);//Seta o lineData no chartGlobal(grafico)
        chartGlobal.animateX(1000); //Animação sentido X da linha tenua do grafico passando o tempo em milesegundos
        chartGlobal.setDoubleTapToZoomEnabled(false); //Desabilita o double toque para zoom do grafico
        chartGlobal.invalidate(); //Inicia e atualiza o grafico
        chartGlobal.setScaleEnabled(true); //Desabilita o zoom total do grafico
        chartGlobal.setDescription(sDescription(valueList.get(0).getName())); // Seta uma descrição dentro do grafico atraves do metodo sDescription
        chartGlobal.getDescription().setXOffset(10f); //Seta margin X da descrição
        chartGlobal.getDescription().setYOffset(5f); //Seta a margin Y da descrição
        chartGlobal.setDrawBorders(false); //Remove as bordas do grafico
        chartGlobal.setTouchEnabled(true); //Habilita o toque no grafico (pode usar o setOnClickListener() para colocar ações no toque)
        chartGlobal.zoom(1.5f, 1f, -1.2f, 1f); //Seta um zoon neste caso apenas no eixo X
        chartGlobal.setExtraBottomOffset(10f); //MarginBottom da label do grafico
        chartGlobal.setNoDataText("Sem dadoss"); //Texto padrão caso nao haja dados para mostrar no grafico
        chartGlobal.setNoDataTextColor(Color.GRAY); //Cor do texto padrao caso nao haja dados para mostrar no grafico
        CustomMarkerView mk = new CustomMarkerView(context, R.layout.layout_teste);
        mk.setChartView(chartGlobal);
        chartGlobal.setMarker(mk);
    }

    //Metodo que formata os numeros nos valores de cada nó da linha tenua do grafico ou do eixo Y
    private ValueFormatter formatValue() {
        final Locale locale = new Locale("pt", "BR");
        final NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        return new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return format.format(value);
            }
        };
    }

    //Metodo que configura um retorno do tipo description para a descrição dentro do grafico
    private Description sDescription(String value) {
        Description description = new Description();
        description.setTextSize(10);
        description.setText(value);
        return description;
    }

    //Metodo que substitui valor do eixo X em Entry por dados escritos em uma Lista de String (neste caso os dias da semana)
    private ValueFormatter formatData(final List<String> value) {
        return new ValueFormatter() {
            @Override
            public String getAxisLabel(float v, AxisBase axisBase) {
                int i = (int) v;
                return value.get(i);
            }
        };
    }

    public void makeGhraphBar() {
        final List<String> mxData = new ArrayList<>();
        mxData.add(convertDataService.convertDayMonth(valueList.get(13).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(12).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(11).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(10).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(9).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(8).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(7).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(6).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(5).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(4).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(3).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(2).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(1).getTimestamp().substring(0, 10)));
        mxData.add(convertDataService.convertDayMonth(valueList.get(0).getTimestamp().substring(0, 10)));
        BarChart barchart = context.findViewById(R.id.chart);
        ArrayList<BarEntry> values = new ArrayList<>();
        values.add(new BarEntry(0, Float.parseFloat(valueList.get(13).getBid())));
        values.add(new BarEntry(1, Float.parseFloat(valueList.get(12).getBid())));
        values.add(new BarEntry(2, Float.parseFloat(valueList.get(11).getBid())));
        values.add(new BarEntry(3, Float.parseFloat(valueList.get(10).getBid())));
        values.add(new BarEntry(4, Float.parseFloat(valueList.get(9).getBid())));
        values.add(new BarEntry(5, Float.parseFloat(valueList.get(8).getBid())));
        values.add(new BarEntry(6, Float.parseFloat(valueList.get(7).getBid())));
        values.add(new BarEntry(7, Float.parseFloat(valueList.get(6).getBid())));
        values.add(new BarEntry(8, Float.parseFloat(valueList.get(5).getBid())));
        values.add(new BarEntry(9, Float.parseFloat(valueList.get(4).getBid())));
        values.add(new BarEntry(10, Float.parseFloat(valueList.get(3).getBid())));
        values.add(new BarEntry(11, Float.parseFloat(valueList.get(2).getBid())));
        values.add(new BarEntry(12, Float.parseFloat(valueList.get(1).getBid())));
        values.add(new BarEntry(13, Float.parseFloat(valueList.get(0).getBid())));
        XAxis xAxis = barchart.getXAxis(); //Inicia o eixo X
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //Dados(labels) do eixo X no Bottom do grafico
        xAxis.setTextColor(Color.parseColor("#328635")); //Seta a cor dos dados(labels) do eixo X
        xAxis.setTextSize(10f); //Tanho dos dados(labels) do eixo X
        xAxis.setYOffset(5f);
        xAxis.setXOffset(-5);
        xAxis.setGranularity(1f);
        xAxis.setAxisLineWidth(0); //espessura da linha superior que separa os dados do eixo x
        xAxis.setDrawGridLines(false); // desabilita as linhas na vertical do grafico
        xAxis.setGridLineWidth(0f);
        xAxis.setValueFormatter(formatData(mxData));
        YAxis yr = barchart.getAxisRight();//Inicia o eixo Y do lado direito do grafico
        yr.setEnabled(false); //Desabilita a visualização dos dados no eixo Y do lado direito do grafico
        YAxis yl = barchart.getAxisLeft(); //Inicia o eixo Y do lado esquerdo do grafico
        yl.setEnabled(false); //Desabilita a visualização dos dados no eixo Y do lado direito do grafico
        yl.setDrawAxisLine(true);
        BarDataSet bardataSet = new BarDataSet(values, "Ultimos fechamentos");
        bardataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        bardataSet.setValueTextColor(Color.BLACK);
        bardataSet.setValueTextSize(10f);
        bardataSet.setValueFormatter(formatData(mxData));
        BarData barData = new BarData(bardataSet);
        barchart.setFitBars(true);
        barchart.setData(barData);
        barchart.getDescription().setText("");
        barchart.animateX(1500);
        barchart.setPinchZoom(false);
        barchart.setExtraBottomOffset(10f); //MarginBottom da label do grafico
        barchart.zoom(3f, 1f, 1f, 1f); //Seta um zoon neste caso apenas no eixo X
    }
}
