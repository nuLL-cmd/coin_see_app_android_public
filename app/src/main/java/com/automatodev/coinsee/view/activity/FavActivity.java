package com.automatodev.coinsee.view.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.automatodev.coinsee.R;
import com.automatodev.coinsee.controller.entidade.CoinEntity;
import com.automatodev.coinsee.view.adapter.FavAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FavActivity extends AppCompatActivity {

    public static boolean status;
    private ArrayList<CoinEntity> coinEntityList;
    private RecyclerView recyclerFav_fav;
    private FavAdapter favAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        recyclerFav_fav = findViewById(R.id.recyclerFav_fav);

        showData();
        sClick();
    }
    @Override
    protected void onStart() {
        super.onStart();
        status = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        status = false;
    }
    private void showData(){
        coinEntityList = new ArrayList<>();
        recyclerFav_fav.hasFixedSize();
        recyclerFav_fav.setLayoutManager(new LinearLayoutManager(this));

        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));
        coinEntityList.add(new CoinEntity("USA","Dolar Comercial",null));

        favAdapter = new FavAdapter(this, coinEntityList);
        recyclerFav_fav.setAdapter(favAdapter);

    }

    public void actFavMain(View view){
        NavUtils.navigateUpFromSameTask(this);
    }

    private void sClick(){
        favAdapter.setOnItemClickListener(new FavAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onIntemClick(int position) {
                favAdapter.notifyItemChanged(position);
                final BottomSheetDialog bt = new BottomSheetDialog(FavActivity.this, R.style.BottomSheetDialogTheme);
                View view = getLayoutInflater().inflate(R.layout.layout_bottom_bar_fav,null);
                Button btnClose_btFav = view.findViewById(R.id.btnClose_btFav);
                LineChart chart = view.findViewById(R.id.chart_btFav);
                showGraphBottom(chart);
                btnClose_btFav.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        bt.dismiss();

                    }
                });
                bt.setContentView(view);
                bt.setCancelable(true);
                bt.show();
            }

            @Override
            public void onDeleteClick(int position) {
                coinEntityList.remove(position);
                favAdapter.notifyItemRemoved(position);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    private void showGraphBottom(LineChart chart) {

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
        mxData.add("Dom");
        mxData.add("Seg");
        mxData.add("Ter");
        mxData.add("Qua");
        mxData.add("Qui");
        mxData.add("Sext");
        mxData.add("Sab");

        //Configuração de dados do eixo X
        XAxis xAxis = chart.getXAxis(); //Inicia o eixo X
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //Dados(labels) do eixo X no Bottom do grafico
        xAxis.setTextColor(Color.parseColor("#43a047")); //Seta a cor dos dados(labels) do eixo X
        xAxis.setTextSize(10); //Tanho dos dados(labelss) do eixo X
        xAxis.setYOffset(10f);
        xAxis.setXOffset(-10f);
        xAxis.setValueFormatter(formatData(mxData));

        //Configuração de dados do eixo Y
        YAxis yr = chart.getAxisRight();//Inicia o eixo Y do lado direito do grafico
        yr.setEnabled(false); //Desabilita a visualização dos dados no eixo Y do lado direito do grafico
        YAxis yl = chart.getAxisLeft(); //Inicia o eixo Y do lado esquerdo do grafico
        yl.setEnabled(false); //Desabilita a visualização dos dados no eixo Y do lado direito do grafico

        //Incicia um novo LineDataSet passando a lista de Entrys e uma label para o grafico
        LineDataSet lineDataSet = new LineDataSet(entradaTestes, "Periodo de 7 dias");
        lineDataSet.setDrawFilled(true); //Preenchimento de cor a baixo da linha tenua do grafico
        lineDataSet.setDrawValues(true); //Valores do eixo Y em cada nó da linha tenua do grafico
        lineDataSet.setValueTextColor(Color.BLACK); //Cor do texto do valor em cada nó da linha tenua do grafico
        lineDataSet.setValueTextSize(12); //Tamanho do texto do valor em cada nó da linha tenua do grafico
        lineDataSet.setValueFormatter(formatValue()); //Formata o valor em cada nó da linha tenua do grafico
        lineDataSet.setCircleHoleColor(Color.parseColor("#d32f2f")); // cor da bolinha
        lineDataSet.setCircleRadius(4f); //Diametro de cada nó da linha tenua do grafico
        lineDataSet.setCircleColor(Color.parseColor("#d32f2f")); //Cor de cada nó da linha tenua do grafico
        lineDataSet.setColor(Color.parseColor("#4caf50")); //Cor da linha tenua e da label do grafico
        // Gradiente (preenchimento) da linha tenua do grafico
        lineDataSet.setFillDrawable(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{Color.parseColor("#4caf50"), Color.parseColor("#00FA5544")}));
        lineDataSet.setLineWidth(5f); //Espessura da linha tenua do grafico


        //Incicia um novo LineData passando o lineDataSet
        LineData lineData = new LineData(lineDataSet);
        chart.setData(lineData);//Seta o lineData no chart(grafico)
        chart.animateX(1000); //Animação sentido X da linha tenua do grafico passando o tempo em milesegundos
        chart.setDoubleTapToZoomEnabled(false); //Desabilita o double toque para zoom do grafico
        chart.invalidate(); //Inicia e atualiza o grafico
        chart.setScaleEnabled(false); //Desabilita o zoom total do grafico
        chart.setDescription(sDescription("Dolar Comercial")); // Seta uma descrição dentro do grafico atraves do metodo sDescription
        chart.getDescription().setXOffset(10f); //Seta margin X da descrição
        chart.getDescription().setYOffset(10F); //Seta a margin Y da descrição
        chart.setDrawBorders(false); //Remove as bordas do grafico
        chart.setTouchEnabled(true); //Habilita o toque no grafico (pode usar o setOnClickListener() para colocar ações no toque)
        chart.zoom(1.8f,1f,1f,1f); //Seta um zoon neste caso apenas no eixo X
        chart.setExtraBottomOffset(10f); //MarginBottom da label do grafico
        chart.setNoDataText("Sem dadoss"); //Texto padrão caso nao haja dados para mostrar no grafico
        chart.setNoDataTextColor(Color.GRAY); //Cor do texto padrao caso nao haja dados para mostrar no grafico


    }
    //Metodo que configura um retorno do tipo description para a descrição dentro do grafico
    private Description sDescription(String value){
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
    //Metodo que formata os numeros nos valores de cada nó da linha tenua do grafico ou do eixo Y
    private ValueFormatter formatValue(){
        final Locale locale = new Locale("pt", "BR");
        final NumberFormat df = NumberFormat.getCurrencyInstance(locale);
        return new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return df.format(value);
            }
        };
    }
}
