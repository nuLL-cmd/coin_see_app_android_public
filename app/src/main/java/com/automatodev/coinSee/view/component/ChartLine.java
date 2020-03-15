package com.automatodev.coinSee.view.component;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.automatodev.coinSee.controller.entity.CoinChildr;
import com.automatodev.coinSee.controller.service.ConvertData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChartLine {
    private Activity context;
    private LineChart chartGlobal;
    private List<CoinChildr> valueList;
    private ConvertData convertData;


    public ChartLine(Activity context, LineChart chartGlobal, List<CoinChildr> valueList) {
        this.context = context;
        this.chartGlobal = chartGlobal;
        this.valueList = valueList;
        convertData = new ConvertData();
    }


    @RequiresApi(api = Build.VERSION_CODES.P)
    public void makeGraph() {

        //Lista do tipo Entry que recebe um entry passando como pramatro os valores do eixo X e eixo y
        List<Entry> entradaTestes = new ArrayList<>();
        entradaTestes.add(new Entry(0f, 4.36f));
        entradaTestes.add(new Entry(1f, 4.46f));
        entradaTestes.add(new Entry(2f, 4.29f));
        entradaTestes.add(new Entry(3f, 4.37f));
        entradaTestes.add(new Entry(4f, 4.37f));
        entradaTestes.add(new Entry(5f, 4.42f));
        entradaTestes.add(new Entry(6f, 4.34f));
        //Lista contendo as datas que substituira os valores de X no objeto Entry dentro do grafico
        final List<String> mxData = new ArrayList<>();
        mxData.add(convertData.convertDayMonth(valueList.get(6).getTimestamp()));
        mxData.add(convertData.convertDayMonth(valueList.get(5).getTimestamp()));
        mxData.add(convertData.convertDayMonth(valueList.get(4).getTimestamp()));
        mxData.add(convertData.convertDayMonth(valueList.get(3).getTimestamp()));
        mxData.add(convertData.convertDayMonth(valueList.get(2).getTimestamp()));
        mxData.add(convertData.convertDayMonth(valueList.get(1).getTimestamp()));
        mxData.add(convertData.convertDayMonth(valueList.get(0).getTimestamp()));

        //Configuração de dados do eixo X
        XAxis xAxis = chartGlobal.getXAxis(); //Inicia o eixo X
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //Dados(labels) do eixo X no Bottom do grafico
        xAxis.setTextColor(Color.parseColor("#1E88E5")); //Seta a cor dos dados(labels) do eixo X
        xAxis.setTextSize(10); //Tanho dos dados(labels) do eixo X
        xAxis.setYOffset(10f);
        xAxis.setXOffset(-10f);
        xAxis.setGranularity(1f);
        xAxis.setAxisLineWidth(1);
        xAxis.setValueFormatter(formatData(mxData));

        //Configuração de dados do eixo Y
        YAxis yr = chartGlobal.getAxisRight();//Inicia o eixo Y do lado direito do grafico
        yr.setEnabled(false); //Desabilita a visualização dos dados no eixo Y do lado direito do grafico
        YAxis yl = chartGlobal.getAxisLeft(); //Inicia o eixo Y do lado esquerdo do grafico
        yl.setEnabled(false); //Desabilita a visualização dos dados no eixo Y do lado direito do grafico
        //Incicia um novo LineDataSet passando a lista de Entrys e uma label para o grafico
        LineDataSet lineDataSet = new LineDataSet(entradaTestes, "Periodo de 7 dias");
        lineDataSet.setDrawFilled(true); //Preenchimento de cor a baixo da linha tenua do grafico
        lineDataSet.setDrawValues(true); //Valores do eixo Y em cada nó da linha tenua do grafico
        lineDataSet.setValueTextColor(Color.BLACK); //Cor do texto do valor em cada nó da linha tenua do grafico
        lineDataSet.setValueTextSize(10); //Tamanho do texto do valor em cada nó da linha tenua do grafico
        lineDataSet.setValueFormatter(formatValue()); //Formata o valor em cada nó da linha tenua do grafico
        lineDataSet.setCircleHoleColor(Color.parseColor("#d32f2f")); // cor da bolinha
        lineDataSet.setCircleRadius(4f); //Diametro de cada nó da linha tenua do grafico
        lineDataSet.setCircleColor(Color.parseColor("#d32f2f")); //Cor de cada nó da linha tenua do grafico
        lineDataSet.setColor(Color.parseColor("#1E88E5")); //Cor da linha tenua e da label do grafico
        // Gradiente (preenchimento) da linha tenua do grafico
        lineDataSet.setFillDrawable(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{Color.parseColor("#1E88E5"), Color.parseColor("#00FA5544")}));
        lineDataSet.setLineWidth(5f); //Espessura da linha tenua do grafico
        //Incicia um novo LineData passando o lineDataSet
        LineData lineData = new LineData(lineDataSet);
        chartGlobal.setData(lineData);//Seta o lineData no chartGlobal(grafico)
        chartGlobal.animateX(1000); //Animação sentido X da linha tenua do grafico passando o tempo em milesegundos
        chartGlobal.setDoubleTapToZoomEnabled(false); //Desabilita o double toque para zoom do grafico
        chartGlobal.invalidate(); //Inicia e atualiza o grafico
        chartGlobal.setScaleEnabled(true); //Desabilita o zoom total do grafico
        chartGlobal.setDescription(sDescription("Dolar Comercial")); // Seta uma descrição dentro do grafico atraves do metodo sDescription
        chartGlobal.getDescription().setXOffset(10f); //Seta margin X da descrição
        chartGlobal.getDescription().setYOffset(10F); //Seta a margin Y da descrição
        chartGlobal.setDrawBorders(false); //Remove as bordas do grafico
        chartGlobal.setTouchEnabled(true); //Habilita o toque no grafico (pode usar o setOnClickListener() para colocar ações no toque)
        chartGlobal.zoom(1.5f, 1f, -1.2f, 1f); //Seta um zoon neste caso apenas no eixo X
        chartGlobal.setExtraBottomOffset(10f); //MarginBottom da label do grafico
        chartGlobal.setNoDataText("Sem dadoss"); //Texto padrão caso nao haja dados para mostrar no grafico
        chartGlobal.setNoDataTextColor(Color.GRAY); //Cor do texto padrao caso nao haja dados para mostrar no grafico
    }

    //Metodo que formata os numeros nos valores de cada nó da linha tenua do grafico ou do eixo Y
    private ValueFormatter formatValue() {
        final Locale locale = new Locale("pt", "BR");
        final NumberFormat df = NumberFormat.getCurrencyInstance(locale);
        return new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return df.format(value);
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


}
