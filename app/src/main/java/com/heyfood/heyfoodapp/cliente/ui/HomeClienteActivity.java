package com.heyfood.heyfoodapp.cliente.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.avaliacao.dominio.AvaliacaoRestaurante;
import com.heyfood.heyfoodapp.avaliacao.persistencia.AvaliacaoRestauranteDAO;
import com.heyfood.heyfoodapp.chatbot.ChatArrayAdapter;
import com.heyfood.heyfoodapp.chatbot.ChatMessage;
import com.heyfood.heyfoodapp.cliente.dominio.Cliente;
import com.heyfood.heyfoodapp.cliente.negocio.ClienteServices;
import com.heyfood.heyfoodapp.infra.Sessao;
import com.heyfood.heyfoodapp.prato.ui.CadastrarPratoActivity;
import com.heyfood.heyfoodapp.prato.ui.ListarPratosActivity;
import com.heyfood.heyfoodapp.recomendacao.Recomendacao;
import com.heyfood.heyfoodapp.restaurante.dominio.Restaurante;
import com.heyfood.heyfoodapp.restaurante.ui.AdapterRestaurante;
import com.heyfood.heyfoodapp.restaurante.ui.AtualizarRestauranteActivity;
import com.heyfood.heyfoodapp.restaurante.ui.ListarRestaurantes;
import com.heyfood.heyfoodapp.usuario.ui.LoginActivity;
import com.heyfood.heyfoodapp.util.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeClienteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView nomeMenu;
    TextView emailMenu;
    Cliente cliente;
    public static Context contexto;
    private ListView listView;
    private EditText chatText;
    private ImageButton send;
    private JSONObject conversationContext;
    private ChatArrayAdapter chatArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cliente);
        contexto = this;
        cliente = Sessao.instance.getCliente();
        chatText = findViewById(R.id.editText);
        send = findViewById(R.id.send);
        configureListView();
        getResponse();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = chatText.getText().toString();
                // print on the outputTextView what the user types
                chatArrayAdapter.add(new ChatMessage(true, input));
                getResponse();
                chatText.setText("");
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        nomeMenu = headerView.findViewById(R.id.nomeMenuId);
        nomeMenu.setText(cliente.getUsuario().getPessoa().getNome());
        emailMenu = headerView.findViewById(R.id.emailMenuId);
        emailMenu.setText(cliente.getUsuario().getPessoa().getContato().getEmail());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Sessao.instance.reset();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_cliente_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.perfilId) {
            Intent novaTela = new Intent(this, PerfilClienteActivity.class);
            startActivity(novaTela);
        } else if (id == R.id.categoriaId) {
            Intent novaTela = new Intent(this, PreferenciaClienteActivity.class);
            startActivity(novaTela);

        } else if (id == R.id.listarRestauranteId) {
            Intent novaTela = new Intent(this, ListarRestaurantes.class);
            startActivity(novaTela);

        } else if (id == R.id.recomendacoesId){
            Intent novaTela = new Intent(this, RecomendacoesActivity.class);
            startActivity(novaTela);

        } else if (id == R.id.sairId) {
            ClienteServices clienteServices = new ClienteServices(this);
            clienteServices.logout();
            Intent novaTela = new Intent(this, LoginActivity.class);
            startActivity(novaTela);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configureListView(){
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.my_message);
        listView = findViewById(R.id.messages_view);
        listView.setAdapter(chatArrayAdapter);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }

    private void getResponse() {
        String workspaceId = "a7436f08-a414-41ab-9906-fa9e6324be46";
        String urlAssistant = "https://gateway.watsonplatform.net/assistant/api/v1/workspaces/" +
                workspaceId +
                "/message?version=2019-02-28";
        String authentication = "YXBpa2V5OlNqOWdOeHdNOF9CS2Naamd1eW1jOE5DeUxseVpUSXU2YmlzdzdteUM4Ukh3";
        AndroidNetworking.post(urlAssistant)
                .addHeaders("Content-Type", "application/json")
                .addHeaders("Authorization", "Basic " + authentication)
                .addJSONObjectBody(createJsonObjectBody())
                .setPriority(Priority.HIGH)
                .setTag(R.string.app_name)
                .build()
                .getAsJSONObject(getOutputMessage());
    }

    private JSONObject createJsonObjectBody(){
        JSONObject inputJsonObject = new JSONObject();
        JSONObject jsonBody = new JSONObject();
        try {
            inputJsonObject.put("text", chatText.getText().toString());
            // put the text Json in the main JSONObject
            jsonBody.put("input", inputJsonObject);
            // put the conversation context Json in the main JSONObject
            jsonBody.put("context", conversationContext);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonBody;
    }

    private JSONObjectRequestListener getOutputMessage(){
        return new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray outputJsonObject;
                try {
                    // Get the response text from Watson
                    outputJsonObject = response.getJSONObject("output").getJSONArray("text");
                    // Refresh the conversation context
                    conversationContext = response.getJSONObject("context");
                    /* Sometimes Watson can return more then one string
                     *  These strings are in a JSONArray that is iterated by the for bellow
                     */
                    for(int index=0; index<outputJsonObject.length(); index++){
                        // Print the messages in the outputTextView
                        String mensagem = outputJsonObject.get(index).toString();
                        if (mensagem.substring(0,4).equals("Vou ") || mensagem.substring(0,4).equals("Irei") || mensagem.substring(0,4).equals("I wi")){
                            Intent novaTela = new Intent(contexto, RecomendacoesActivity.class);
                            startActivity(novaTela);
                        }
                        chatArrayAdapter.add(new ChatMessage(false, outputJsonObject.get(index).toString()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(ANError anError) {
                // Shows a message of error in the case of the connection fails
                Toast.makeText(getApplicationContext(), "connection error", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
